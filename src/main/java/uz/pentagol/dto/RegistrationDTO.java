package uz.pentagol.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationDTO {
    @NotBlank @Size(min = 5, message = "Username is required")
    private String username;
    @NotBlank @Size(min = 8, message = "Password should be at least 8 characters")
    private String password;
    @Email(message = "Email is wrong")
    private String email;
}
