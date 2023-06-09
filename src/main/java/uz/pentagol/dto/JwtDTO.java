package uz.pentagol.dto;

import lombok.Getter;
import lombok.Setter;
import uz.pentagol.enums.UserRoleEnum;

@Getter
@Setter
public class JwtDTO {
    private int id;
    private String username;
    private UserRoleEnum roleEnum;

    public JwtDTO(int adminId, String username, UserRoleEnum roleEnum) {
        this.id = adminId;
        this.username = username;
        this.roleEnum = roleEnum;
    }
}
