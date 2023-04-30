package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LeagueDTO {
    private int id;
    @NotBlank @Size(min = 5, message = "League name is required")
    private String name;
    //@NotBlank(message = "Image of league is required")
    //private byte[] image;
}
