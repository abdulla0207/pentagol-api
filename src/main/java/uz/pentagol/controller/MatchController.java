package uz.pentagol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.dto.MatchDTO;
import uz.pentagol.dto.MatchResponseDTO;
import uz.pentagol.entity.MatchEntity;
import uz.pentagol.mapper.MatchMapper;
import uz.pentagol.service.MatchService;
import uz.pentagol.util.JwtUtil;

import java.util.List;

@RestController
@RequestMapping("/match")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService){
        this.matchService = matchService;
    }

    @GetMapping("/prev/{id}")
    public ResponseEntity<?> getMatchListPrev(@PathVariable int id){
        List<MatchEntity> response = matchService.getPreviousWeekGames(id);

        return ResponseEntity.ok(MatchMapper.toDtoList(response));
    }
    @GetMapping("/current/{leagueId}")
    public ResponseEntity<?> getMatchListCurrent(@PathVariable int leagueId){
        List<MatchEntity> response = matchService.getNextWeekGames(leagueId);

        return ResponseEntity.ok(MatchMapper.toDtoList(response));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMatch(@RequestBody MatchDTO matchDTO, @RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));
        MatchDTO res = matchService.create(matchDTO, jwtDTO);

        return ResponseEntity.ok(res);
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMatch(@PathVariable int id){
        boolean res = matchService.delete(id);

        return ResponseEntity.ok(res);
    }
}
