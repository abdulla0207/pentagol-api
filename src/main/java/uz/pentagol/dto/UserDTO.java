package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import uz.pentagol.enums.UserRoleEnum;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private int id;
    private String username;
    private String password;
    private String email;
    private UserRoleEnum roleEnum;
}
