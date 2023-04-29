package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubDTO {
    private int id;
    @NotBlank @Size(min = 2, max = 200, message = "Name of the club is required")
    private String name;
    private int point;
    private int gamesPlayed;
    private byte[] image;
    private int leagueId;
}