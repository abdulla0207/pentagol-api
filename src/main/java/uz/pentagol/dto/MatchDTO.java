package uz.pentagol.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MatchDTO {
    private int id;
    private int clubAId;
    private int clubBId;
    private int clubAScore;
    private int clubBScore;
    private LocalDateTime matchDate;
}
