package uz.pentagol.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import uz.pentagol.enums.UserRoleEnum;

@Entity
@Table(name = "users")
@Getter
@Setter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String username;
    @Column
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "user_role")
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum userRoleEnum;
}
