package uz.pentagol.mapper;

import uz.pentagol.dto.MatchResponseDTO;
import uz.pentagol.dto.MatchResultDTO;
import uz.pentagol.entity.MatchEntity;

import java.util.ArrayList;
import java.util.List;

public class MatchResultMapper {

    public static MatchResultDTO map(MatchEntity entity){
        MatchResultDTO dto = new MatchResultDTO();

        dto.setClubAId(entity.getClubAId());
        dto.setClubBId(entity.getClubBId());
        dto.setClubAScore(entity.getClubAScore());
        dto.setClubBScore(entity.getClubBScore());

        return dto;
    }

    public static List<MatchResultDTO> toDtoList(List<MatchEntity> matchEntities) {
        List<MatchResultDTO> matchDtos = new ArrayList<>();
        for (MatchEntity matchEntity : matchEntities) {
            matchDtos.add(map(matchEntity));
        }
        return matchDtos;
    }
}
