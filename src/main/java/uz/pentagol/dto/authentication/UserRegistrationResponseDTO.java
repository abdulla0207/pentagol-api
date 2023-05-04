package uz.pentagol.dto.authentication;

import lombok.Getter;
import lombok.Setter;
import uz.pentagol.enums.UserRoleEnum;

@Getter
@Setter
public class UserRegistrationResponseDTO {
    private int id;
    private String username;
    private String email;

    public UserRegistrationResponseDTO(int id, String username, String email){
        this.username = username;
        this.email = email;
        this.id = id;
    }
}
