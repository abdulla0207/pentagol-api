package uz.pentagol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtDTO {
    private int adminId;
    private String username;

    public JwtDTO(int adminId, String username) {
        this.adminId = adminId;
        this.username = username;
    }
}
