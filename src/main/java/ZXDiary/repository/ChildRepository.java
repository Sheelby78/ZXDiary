package ZXDiary.repository;

import ZXDiary.model.Child;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
public interface ChildRepository extends JpaRepository<Child, Integer> {
    Optional<Child> findById(Integer id);
}
