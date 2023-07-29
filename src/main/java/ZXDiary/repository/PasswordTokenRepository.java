package ZXDiary.repository;

import ZXDiary.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    PasswordResetToken findByUsername(String username);
    PasswordResetToken findByToken(String token);
}
