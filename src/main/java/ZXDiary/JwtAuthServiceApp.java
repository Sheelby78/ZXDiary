package ZXDiary;

import java.util.ArrayList;
import java.util.Arrays;

import ZXDiary.model.*;
import ZXDiary.service.EmailSenderService;
import ZXDiary.service.ParentService;
import ZXDiary.service.TherapistService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ZXDiary.service.UserService;
import org.springframework.context.annotation.Lazy;

import javax.validation.constraints.Email;

@SpringBootApplication
@RequiredArgsConstructor
public class JwtAuthServiceApp implements CommandLineRunner {


  UserService userService;

  ParentService parentService;

  TherapistService therapistService;

  EmailSenderService emailSenderService;

  public static void main(String[] args) {
    SpringApplication.run(JwtAuthServiceApp.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Override
  public void run(String... params) throws Exception {


  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setParentService(ParentService parentService) {
    this.parentService = parentService;
  }

  @Autowired
  public void setTherapistService(TherapistService therapistService) { this.therapistService = therapistService; }

  @Autowired
  public void setEmailSenderService(EmailSenderService emailSenderService) { this.emailSenderService = emailSenderService; }

}
