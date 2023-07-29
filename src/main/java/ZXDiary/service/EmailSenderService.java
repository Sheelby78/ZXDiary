package ZXDiary.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String email, String token) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setFrom("zxdiary.test@gmail.com");
        message.setSubject("Complete Password Reset!");
        message.setText("To complete the password reset process, please click here: "
                        + "http://localhost:8080/users/confirmReset?token=" + token);
        mailSender.send(message);
    }
}
