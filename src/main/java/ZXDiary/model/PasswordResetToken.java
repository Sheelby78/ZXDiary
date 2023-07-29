package ZXDiary.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Data // Create getters and setters
public class PasswordResetToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String token;

    private String username;

    private LocalDateTime expiryDate;

    public PasswordResetToken(String token, String username){
        this.username = username;
        this.token = token;
        expiryDate = LocalDateTime.now().plusHours(24);
    }
}
