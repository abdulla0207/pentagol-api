package uz.pentagol.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.dto.LeagueDTO;
import uz.pentagol.service.LeagueService;

import java.util.List;

@RestController
@RequestMapping("/league")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService){
        this.leagueService = leagueService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/create")
    public ResponseEntity<?> createLeague(@Valid @RequestBody LeagueDTO leagueDTO){
        String response = leagueService.createLeague(leagueDTO);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/list")
    public ResponseEntity<?> getList(){
        List<LeagueDTO> leagueList = leagueService.getLeagueList();

        return ResponseEntity.ok(leagueList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateLeague(@Valid @RequestBody LeagueDTO leagueDTO, @PathVariable int id){
        int response = leagueService.updateLeague(leagueDTO, id);

        if(response == 1)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteLeague(@PathVariable int id){
        boolean response = leagueService.deleteById(id);

        return ResponseEntity.ok(response);
    }
}
