package uz.pentagol.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.service.ClubService;
import uz.pentagol.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/club")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getClubsPagination(){
        List<ClubDTO> dtos = clubService.getClubs();

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createClub( @RequestBody ClubDTO clubDTO, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        ClubDTO response = clubService.createClub(clubDTO, jwtDTO);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateClub(@RequestBody ClubDTO clubDTO, @PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        int result = clubService.updateClub(clubDTO, id, jwtDTO);

        if(result == 1)
            return ResponseEntity.ok(clubDTO);

        return ResponseEntity.badRequest().build();
    }
    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        boolean response = clubService.deleteById(id, jwtDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/leagues/{leagueId}")
    public ResponseEntity<?> getClubsByLeagueId(@PathVariable int leagueId){
        List<ClubDTO> getClubsByLeagueId = clubService.getClubsByLeagueId(leagueId);

        return ResponseEntity.ok(getClubsByLeagueId);
    }
}
