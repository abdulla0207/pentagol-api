package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClubDTO {
    private int id;
    private String name;
    private int point;
    private int gamesPlayed;
    private byte[] image;
    private int leagueId;
    private LeagueDTO league;
}
