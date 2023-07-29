package ZXDiary.repository;

import ZXDiary.model.Parent;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ParentRepository extends JpaRepository<Parent, Integer> {

    Parent findByUsername(String username);
}
