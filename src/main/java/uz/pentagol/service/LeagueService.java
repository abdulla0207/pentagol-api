package uz.pentagol.service;

import org.springframework.stereotype.Service;
import uz.pentagol.dto.LeagueDTO;
import uz.pentagol.entity.LeagueEntity;
import uz.pentagol.exceptions.AppBadRequest;
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
    public String createLeague(LeagueDTO leagueDTO) {
        Optional<LeagueEntity> findByName = leagueRepository.findByName(leagueDTO.getName());

        if(findByName.isPresent())
            throw new AppBadRequest("This league already exists");

        LeagueEntity entity = new LeagueEntity();
        entity.setName(leagueDTO.getName());
        entity.setImage(leagueDTO.getImage());

        leagueRepository.save(entity);
        return "League saved";
    }

    public List<LeagueDTO> getLeagueList(){
        List<LeagueEntity> entities = leagueRepository.findAll();

        List<LeagueDTO> response = new ArrayList<>();

        entities.forEach(leagueEntity -> {
            LeagueDTO temp = new LeagueDTO();

            temp.setImage(leagueEntity.getImage());
            temp.setName(leagueEntity.getName());
            response.add(temp);
        });

        return response;
    }

    public int updateLeague(LeagueDTO leagueDTO, int id) {
        int res = leagueRepository.updateLeague(leagueDTO.getName(), leagueDTO.getImage(), id);

        return res;
    }

    public boolean deleteById(int id){
        Optional<LeagueEntity> leagueById = leagueRepository.findById(id);

        if(leagueById.isEmpty())
            return false;

        leagueRepository.deleteById(id);
        return true;
    }
}
