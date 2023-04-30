package uz.pentagol.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.LeagueDTO;
import uz.pentagol.entity.ClubEntity;
import uz.pentagol.entity.LeagueEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppBadRequest;
import uz.pentagol.exceptions.AppForbiddenException;
import uz.pentagol.exceptions.ItemNotFound;
import uz.pentagol.repository.ClubRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository){
        this.clubRepository = clubRepository;
    }
    public List<ClubDTO> getClubs() {
        List<ClubEntity> clubEntityPage = clubRepository.findAll();
        List<ClubDTO> dtos = toDtoList(clubEntityPage);

        return dtos;
    }

    private List<ClubDTO> toDtoList(List<ClubEntity> entities) {
        List<ClubDTO> clubDTOS = new ArrayList<>();

        entities.forEach(entity ->{
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setId(entity.getId());
            clubDTO.setName(entity.getName());
            clubDTO.setPoint(entity.getPoint());
            clubDTO.setGamesPlayed(entity.getGamesPlayed());
            clubDTO.setImage(entity.getImage());
            clubDTO.setLeague(toLeagueDto(entity.getLeague()));

            clubDTOS.add(clubDTO);
        });

        return clubDTOS;
    }

    private LeagueDTO toLeagueDto(LeagueEntity entity){
        LeagueDTO leagueDTO = new LeagueDTO();
        leagueDTO.setName(entity.getName());
        leagueDTO.setId(entity.getId());
        return leagueDTO;
    }

    public ClubDTO createClub(ClubDTO clubDTO, JwtDTO jwtDTO) {
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");

        Optional<ClubEntity> findByName = clubRepository.findByName(clubDTO.getName());

        if(findByName.isPresent())
            throw new AppBadRequest("This club already exists");

        ClubEntity save = clubRepository.save(toEntity(clubDTO));
        clubDTO.setId(save.getId());
        return clubDTO;
    }



    private ClubEntity toEntity(ClubDTO clubDTO) {
        ClubEntity clubEntity = new ClubEntity();
        clubEntity.setGamesPlayed(clubDTO.getGamesPlayed());
        clubEntity.setName(clubDTO.getName());
        clubEntity.setPoint(clubDTO.getPoint());
        clubEntity.setImage(clubDTO.getImage());
        clubEntity.setLeagueId(clubDTO.getLeagueId());
        return clubEntity;
    }

    public int updateClub(ClubDTO clubDTO, int id, JwtDTO jwtDTO) {
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");
        int result = clubRepository.updateClub(clubDTO.getName(), clubDTO.getPoint(), clubDTO.getLeagueId(), clubDTO.getImage(),
                clubDTO.getGamesPlayed(), id);

        return result;
    }

    public Boolean deleteById(int id, JwtDTO jwtDTO){
        Optional<ClubEntity> byId = clubRepository.findById(id);
        if(byId.isEmpty())
            return false;

        clubRepository.deleteById(id);
        return true;
    }

    public List<ClubDTO> getClubsByLeagueId(int leagueId){
        List<ClubEntity> clubEntities = clubRepository.findClubEntitiesByLeagueId(leagueId);

        if(clubEntities.isEmpty())
            throw new ItemNotFound("Clubs with this league ID does not exist");

        List<ClubDTO> response = new ArrayList<>();

        clubEntities.forEach(clubEntity -> {
            System.out.println(clubEntity.getLeagueId());
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setName(clubEntity.getName());
            clubDTO.setImage(clubEntity.getImage());
            clubDTO.setGamesPlayed(clubEntity.getGamesPlayed());
            clubDTO.setPoint(clubEntity.getPoint());
            clubDTO.setLeagueId(clubEntity.getLeagueId());
            clubDTO.setId(clubEntity.getId());
            response.add(clubDTO);
        });
        return response;
    }
}
