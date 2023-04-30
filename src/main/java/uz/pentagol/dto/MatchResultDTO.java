package uz.pentagol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchResultDTO {
    private int clubAId;
    private int clubBId;
    private int clubAScore;
    private int clubBScore;
}
