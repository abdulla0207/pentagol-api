package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.*;
import uz.pentagol.entity.ClubEntity;
import uz.pentagol.entity.LeagueEntity;
import uz.pentagol.entity.MatchEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppBadRequestException;
import uz.pentagol.exceptions.AppForbiddenException;
import uz.pentagol.mapper.MatchResultMapper;
import uz.pentagol.repository.ClubRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ClubService {

    private final ClubRepository clubRepository;

    private final MatchService matchService;

    private final LeagueService leagueService;

    public ClubService(ClubRepository clubRepository, MatchService matchService, LeagueService leagueService){
        this.clubRepository = clubRepository;
        this.matchService = matchService;
        this.leagueService = leagueService;
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
            throw new AppBadRequestException("This club already exists");

        ClubEntity save = clubRepository.save(toEntity(clubDTO));
        clubDTO.setId(save.getId());
        clubDTO.setLeagueId(save.getLeagueId());
        LeagueDTO dto = new LeagueDTO();
        dto.setName(save.getLeague().getName());
        dto.setId(save.getLeague().getId());
        clubDTO.setLeague(dto);
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
            return new ArrayList<>();

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

    public String updateStat() {
        List<MatchEntity> entities = matchService.getPrevGames(LocalDateTime.now());
        List<MatchResultDTO> resultGames = MatchResultMapper.toDtoList(entities);


        resultGames.forEach(game->{
            Optional<ClubEntity> getClub1 = clubRepository.findById(game.getClubAId());
            Optional<ClubEntity> getClub2 = clubRepository.findById(game.getClubBId());
            ClubEntity clubEntity2 = getClub2.get();
            ClubEntity clubEntity1 = getClub1.get();

            if(game.getClubAScore()==game.getClubBScore()){
                int first = clubEntity2.getPoint()+1;
                int second = clubEntity1.getPoint()+1;
                clubRepository.updateStat(first, clubEntity1.getId());
                clubRepository.updateStat(second, clubEntity2.getId());
            }else if(game.getClubBScore() > game.getClubAScore()){
                int winner = clubEntity2.getPoint()+3;
                clubRepository.updateStat(winner, clubEntity2.getId());
            }else{
                int winner = clubEntity1.getPoint()+3;
                clubRepository.updateStat(winner, clubEntity1.getId());
            }
        });


        return "Clubs Stat updated";
    }
}
