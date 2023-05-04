package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.LeagueDTO;
import uz.pentagol.entity.LeagueEntity;
import uz.pentagol.enums.UserRoleEnum;
import uz.pentagol.exceptions.AppBadRequestException;
import uz.pentagol.exceptions.AppForbiddenException;
import uz.pentagol.exceptions.LeagueCreateException;
import uz.pentagol.repository.LeagueRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;

    public LeagueService(LeagueRepository leagueRepository){
        this.leagueRepository = leagueRepository;
    }
    public LeagueDTO createLeague(LeagueDTO leagueDTO, JwtDTO jwtDTO) {
        if (!jwtDTO.getRoleEnum().equals(UserRoleEnum.ADMIN))
            throw new AppForbiddenException("Method not Allowed");


        if(leagueDTO.getName().isBlank() || leagueDTO.getName().isEmpty())
            throw new LeagueCreateException("Name of the league cannot be empty");

        Optional<LeagueEntity> findByName = leagueRepository.findByName(leagueDTO.getName());

        if(findByName.isPresent())
            throw new AppBadRequestException("This league already exists");

        LeagueEntity entity = new LeagueEntity();
        entity.setNameEn(leagueDTO.getName());

        LeagueDTO dto = new LeagueDTO();
        dto.setName(entity.getNameEn());
        LeagueEntity newent = leagueRepository.save(entity);
        dto.setId(newent.getId());
        return dto;
    }

    public List<LeagueDTO> getLeagueList(){
        List<LeagueEntity> entities = leagueRepository.findAll();

        List<LeagueDTO> response = new ArrayList<>();

        entities.forEach(leagueEntity -> {
            LeagueDTO temp = new LeagueDTO();

            temp.setName(leagueEntity.getNameEn());
            temp.setId(leagueEntity.getId());
            response.add(temp);
        });

        return response;
    }

    public int updateLeague(LeagueDTO leagueDTO, int id, JwtDTO jwtDTO) {
        int res = leagueRepository.updateLeague(leagueDTO.getName(), id);

        return res;
    }

    public boolean deleteById(int id, JwtDTO jwtDTO){
        Optional<LeagueEntity> leagueById = leagueRepository.findById(id);

        if(leagueById.isEmpty())
            return false;

        leagueRepository.deleteById(id);
        return true;
    }
}
