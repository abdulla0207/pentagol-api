package uz.pentagol.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MatchResponseDTO {
    private int id;
    private String clubAName;
    private String clubBName;
    private int clubAScore;
    private int clubBScore;
    private int leagueId;
    private LocalDateTime matchDate;
}
