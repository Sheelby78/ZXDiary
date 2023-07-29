package ZXDiary.repository;

import ZXDiary.model.Parent;
import ZXDiary.model.Therapist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TherapistRepository extends JpaRepository<Therapist, Integer> {
    Therapist findByUsername(String username);
}
