package ZXDiary.service;

import ZXDiary.exception.CustomException;
import ZXDiary.model.Child;
import ZXDiary.model.Therapist;
import ZXDiary.repository.ChildRepository;
import ZXDiary.repository.TherapistRepository;
import ZXDiary.repository.UserRepository;
import ZXDiary.security.JwtTokenProvider;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
public class TherapistService extends UserService{
    private final TherapistRepository therapistRepository;
    private final ChildRepository childRepository;
    private final ModelMapper modelMapper;

    public TherapistService(PasswordEncoder passwordEncoder,
                         JwtTokenProvider jwtTokenProvider, AuthenticationManager authenticationManager,
                            TherapistRepository therapistRepository, ChildRepository childRepository, @Lazy ModelMapper modelMapper) {
        super( passwordEncoder, jwtTokenProvider, authenticationManager, modelMapper);
        this.therapistRepository = therapistRepository;
        this.childRepository = childRepository;
        this.modelMapper = modelMapper;
    }

    public Child addChild(HttpServletRequest req, Integer ID) {
        Therapist therapist = getTherapistFromRequest(req);
        Optional<Child> child = childRepository.findById(ID);

        if(!child.isPresent())
        {
            throw new CustomException("Dziecko o podanym ID nie istnieje w systemie", HttpStatus.NOT_FOUND);
        }
        if (therapist.childIsSigned(child.get())) {
            throw new CustomException("Therapist already has this child signed", HttpStatus.CONFLICT);
        }

        therapist.addChild(child.get());
        childRepository.save(child.get());
        return child.get();
    }

    public Child removeChild(HttpServletRequest req, Integer ID) {
        Therapist therapist = getTherapistFromRequest(req);
        Optional<Child> child = childRepository.findById(ID);

        if(!child.isPresent())
        {
            throw new CustomException("Dziecko o podanym ID nie istnieje w systemie", HttpStatus.NOT_FOUND);
        }
        if (!therapist.childIsSigned(child.get())) {
            throw new CustomException("Therapist does not have the specified child signed", HttpStatus.CONFLICT);
        }

        therapist.removeChild(child.get());
        childRepository.save(child.get());
        return child.get();
    }

    public Therapist getTherapistFromRequest(HttpServletRequest req) {
        return therapistRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
    }

    public Child getChild(HttpServletRequest req, Integer ID) {
        Therapist therapist = getTherapistFromRequest(req);
        Optional<Child> child = childRepository.findById(ID);

        if(!child.isPresent())
        {
            throw new CustomException("Dziecko o podanym ID nie istnieje w systemie", HttpStatus.NOT_FOUND);
        }
        if (!therapist.childIsSigned(child.get())) {
            throw new CustomException("Therapist does not have the specified child signed", HttpStatus.CONFLICT);
        }
        return child.get();
    }

}
