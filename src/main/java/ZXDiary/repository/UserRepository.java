package ZXDiary.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import ZXDiary.model.AppUser;

import java.util.Optional;


public interface UserRepository extends JpaRepository<AppUser, Integer> {

  boolean existsByUsername(String username);

  AppUser findByUsername(String username);

  Optional<AppUser> findById(Integer id);

  AppUser findByEmail(String email);

  @Transactional
  void deleteByUsername(String username);

}
