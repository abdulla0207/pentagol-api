package uz.pentagol.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    private String username;
    private String password;
    private String email;
}
