package uz.pentagol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.LeagueDTO;
import uz.pentagol.service.LeagueService;
import uz.pentagol.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/league")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService){
        this.leagueService = leagueService;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/admin/create")
    public ResponseEntity<?> createLeague(@RequestBody LeagueDTO leagueDTO, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        LeagueDTO response = leagueService.createLeague(leagueDTO, jwtDTO);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/list")
    public ResponseEntity<?> getList(){
        List<LeagueDTO> leagueList = leagueService.getLeagueList();

        return ResponseEntity.ok(leagueList);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateLeague(@RequestBody LeagueDTO leagueDTO, @PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        int response = leagueService.updateLeague(leagueDTO, id, jwtDTO);

        if(response == 1)
            return ResponseEntity.ok(leagueDTO);

        return ResponseEntity.badRequest().build();
    }

    //@PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteLeague(@PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        boolean response = leagueService.deleteById(id, jwtDTO);

        return ResponseEntity.ok(response);
    }
}
