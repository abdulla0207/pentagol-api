package uz.pentagol.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.UserEntity;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<UserEntity, Integer> {

    @Query("select a from UserEntity as a where a.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity as u where u.email = ?1 and u.password = ?2")
    Optional<UserEntity> findByEmailAndPassword(String email, String encode);
}
