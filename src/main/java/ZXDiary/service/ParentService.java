package ZXDiary.service;

import ZXDiary.exception.CustomException;
import ZXDiary.model.Child;
import ZXDiary.model.Parent;
import ZXDiary.repository.ChildRepository;
import ZXDiary.repository.ParentRepository;
import ZXDiary.repository.UserRepository;
import ZXDiary.security.JwtTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class ParentService extends UserService{
    private ParentRepository parentRepository;
    private final ChildRepository childRepository;
    private final ModelMapper modelMapper;



    public ParentService( PasswordEncoder passwordEncoder,
                         JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                         ParentRepository parentRepository, ChildRepository childRepository, @Lazy ModelMapper modelMapper) {
        super( passwordEncoder, jwtTokenProvider, authenticationManager, modelMapper);
        this.parentRepository = parentRepository;
        this.childRepository = childRepository;
        this.modelMapper = modelMapper;
    }


    public void addChild(HttpServletRequest req, Child child) {
        Parent parent = getParentFromRequest(req);
        if (parent.hasChild()) {
            throw new CustomException("Parent can only have 1 child!", HttpStatus.CONFLICT);
        }
        parent.addChild(child);
        childRepository.save(child);
    }

    public Child updateChild(HttpServletRequest req, Child child) {
        Parent parent = getParentFromRequest(req);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(child, parent.getChild());
        childRepository.save(parent.getChild());
        return parent.getChild();
    }

    public Parent getParentFromRequest(HttpServletRequest req) {
        return parentRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public Child getChild(HttpServletRequest req) {
        return getParentFromRequest(req).getChild();
    }


}
