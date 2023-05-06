package uz.pentagol.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.pentagol.entity.UserEntity;
import uz.pentagol.enums.UserStatusEnum;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<UserEntity, Integer> {

    @Query("select u from UserEntity as u where u.username = ?1")
    Optional<UserEntity> findByUsername(String username);

    @Query("select a from UserEntity as a where a.email = ?1")
    Optional<UserEntity> findByEmail(String email);

    @Query("select u from UserEntity as u where u.username = ?1 and u.password = ?2")
    Optional<UserEntity> findByUsernameAndPassword(String username, String encode);

    @Modifying
    @Transactional
    @Query("update UserEntity as u set u.userStatusEnum= ?2 where u.id = ?1")
    int updateUserStatus(Integer userId, UserStatusEnum userStatusEnum);
}
