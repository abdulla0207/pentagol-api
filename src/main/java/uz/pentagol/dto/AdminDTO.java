package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AdminDTO {
    private int id;
    @NotBlank @Size(min = 5, max = 25, message = "Username is required")
    private String username;
    @NotBlank @Size(min = 8, message = "Password must contain at least 8 characters")
    private String password;
}
