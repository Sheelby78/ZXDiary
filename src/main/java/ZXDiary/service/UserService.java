package ZXDiary.service;

import javax.servlet.http.HttpServletRequest;

import ZXDiary.model.*;
import ZXDiary.repository.ParentRepository;
import ZXDiary.repository.PasswordTokenRepository;
import ZXDiary.repository.TherapistRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ZXDiary.exception.CustomException;
import ZXDiary.repository.UserRepository;
import ZXDiary.security.JwtTokenProvider;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  @Autowired
  protected UserRepository userRepository;
  @Autowired
  protected PasswordTokenRepository passwordTokenRepository;
  protected TherapistRepository therapistRepository;
  protected ParentRepository parentRepository;
  private final PasswordEncoder passwordEncoder;
  protected final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  protected final ModelMapper modelMapper;

  @Autowired
  public void setTherapistRepository(TherapistRepository therapistRepository) {this.therapistRepository = therapistRepository; }

  @Autowired
  public void setParentRepository(ParentRepository parentRepository) {this.parentRepository = parentRepository; }

  public String signin(String username, String password) {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
      return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
    } catch (AuthenticationException e) {
      throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public String signup(AppUser appUser) {
    if (!userRepository.existsByUsername(appUser.getUsername())) {
      appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
      if (appUser.getAppUserRoles().contains(AppUserRole.ROLE_PARENT)) {
        parentRepository.save(modelMapper.map(appUser, Parent.class));
      } else if (appUser.getAppUserRoles().contains(AppUserRole.ROLE_THERAPIST)) {
        therapistRepository.save(modelMapper.map(appUser, Therapist.class));
      } else {
        userRepository.save(appUser);
      }
      return jwtTokenProvider.createToken(appUser.getUsername(), appUser.getAppUserRoles());
    } else {
      throw new CustomException("Username is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
    }
  }

  public void delete(String username) {
    userRepository.deleteByUsername(username);
  }

  public AppUser search(String username) {
    AppUser appUser = userRepository.findByUsername(username);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }

  public AppUser findUserByEmail(String email) {
    AppUser appUser = userRepository.findByEmail(email);
    if (appUser == null) {
      throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
    }
    return appUser;
  }

  public AppUser whoami(HttpServletRequest req) {
    return userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
  }

  public String refresh(String username) {
    return jwtTokenProvider.createToken(username, userRepository.findByUsername(username).getAppUserRoles());
  }

  public void createPasswordResetTokenForUser(AppUser appUser, String token) {
    PasswordResetToken myToken = new PasswordResetToken(token, appUser.getUsername());
    PasswordResetToken newToken = passwordTokenRepository.findByUsername(appUser.getUsername());
    if(newToken != null){
      newToken.setToken(token);
      passwordTokenRepository.save(newToken);
    } else {
      passwordTokenRepository.save(myToken);
    }
  }

  public String confirmReset(String confirmationToken){
      PasswordResetToken token = passwordTokenRepository.findByToken(confirmationToken);
    if (token != null) {
      if(token.getExpiryDate().isBefore(LocalDateTime.now())){
        throw new CustomException("Link expired", HttpStatus.NOT_FOUND);
      } else {
        return confirmationToken;
      }
    } else {
      throw new CustomException("Incorrect link", HttpStatus.NOT_FOUND);
    }
  }

  public void resetPassword(String confirmationToken, String password){
    PasswordResetToken token = passwordTokenRepository.findByToken(confirmationToken);
    if (token != null) {
      AppUser appUser = userRepository.findByUsername(token.getUsername());
      if(token.getExpiryDate().isBefore(LocalDateTime.now())){
        throw new CustomException("Link expired", HttpStatus.NOT_FOUND);
      } else {
        appUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(appUser);
      }
    } else {
      throw new CustomException("Incorrect link", HttpStatus.NOT_FOUND);
    }
  }

}
