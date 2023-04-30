package uz.pentagol.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.entity.ClubEntity;
import uz.pentagol.exceptions.AppBadRequest;
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
    public Page<ClubDTO> getClubs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ClubEntity> clubEntityPage = clubRepository.findAll(pageable);

        List<ClubEntity> clubEntities = clubEntityPage.stream().toList();
        List<ClubDTO> dtos = toDtoList(clubEntities);

        return new PageImpl<>(dtos, pageable, clubEntityPage.getTotalElements());
    }

    private List<ClubDTO> toDtoList(List<ClubEntity> entities) {
        List<ClubDTO> clubDTOS = new ArrayList<>();

        entities.forEach(entity ->{
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setName(entity.getName());
            clubDTO.setPoint(entity.getPoint());
            clubDTO.setGamesPlayed(entity.getGamesPlayed());
            clubDTO.setImage(entity.getImage());

            clubDTOS.add(clubDTO);
        });

        return clubDTOS;
    }

    public String createClub(ClubDTO clubDTO) {
        Optional<ClubEntity> findByName = clubRepository.findByName(clubDTO.getName());

        if(findByName.isPresent())
            throw new AppBadRequest("This club already exists");

        clubRepository.save(toEntity(clubDTO));
        return "Club created";
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

    public int updateClub(ClubDTO clubDTO, int id) {
        int result = clubRepository.updateClub(clubDTO.getName(), clubDTO.getPoint(), clubDTO.getLeagueId(), clubDTO.getImage(),
                clubDTO.getGamesPlayed(), id);

        return result;
    }

    public Boolean deleteById(int id){
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
            ClubDTO clubDTO = new ClubDTO();
            clubDTO.setName(clubEntity.getName());
            clubDTO.setImage(clubEntity.getImage());
            clubDTO.setGamesPlayed(clubEntity.getGamesPlayed());
            clubDTO.setPoint(clubEntity.getPoint());
            response.add(clubDTO);
        });

        return response;
    }
}
