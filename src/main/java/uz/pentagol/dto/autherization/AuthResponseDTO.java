package uz.pentagol.dto.autherization;

import lombok.Getter;
import lombok.Setter;
import uz.pentagol.enums.UserRoleEnum;

@Getter
@Setter
public class AuthResponseDTO {
    private String username;
    private UserRoleEnum roleEnum;
    private String email;
}
