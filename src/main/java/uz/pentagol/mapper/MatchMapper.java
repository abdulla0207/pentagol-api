package uz.pentagol.mapper;

import uz.pentagol.dto.MatchResponseDTO;
import uz.pentagol.entity.MatchEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MatchMapper {

    public static MatchResponseDTO toDTO(MatchEntity entity){
        MatchResponseDTO responseDTO = new MatchResponseDTO();
        responseDTO.setId(entity.getId());
        responseDTO.setMatchDate(entity.getMatchDate());
        responseDTO.setClubBScore(entity.getClubBScore());
        responseDTO.setClubAScore(entity.getClubAScore());
        responseDTO.setClubBName(entity.getClubB().getName());
        responseDTO.setClubAName(entity.getClubA().getName());

        return responseDTO;
    }

    public static List<MatchResponseDTO> toDtoList(List<MatchEntity> matchEntities) {
        List<MatchResponseDTO> matchDtos = new ArrayList<>();
        for (MatchEntity matchEntity : matchEntities) {
            matchDtos.add(toDTO(matchEntity));
        }
        return matchDtos;
    }
}
