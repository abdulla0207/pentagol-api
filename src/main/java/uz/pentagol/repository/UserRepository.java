package uz.pentagol.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.pentagol.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
}
