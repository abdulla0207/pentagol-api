package uz.pentagol.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponseDTO {
    private UserRegistrationResponseDTO userResponse;
    private String token;
}
