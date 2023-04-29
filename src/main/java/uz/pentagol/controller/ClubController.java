package uz.pentagol.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.ClubDTO;
import uz.pentagol.service.ClubService;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    private final ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/list")
    public ResponseEntity<?> getClubsPagination(@RequestParam int page, @RequestParam int size){
        List<ClubDTO> dtos = clubService.getClubs(page, size);

        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<?> createClub(@Valid @RequestBody ClubDTO clubDTO){
        String response = clubService.createClub(clubDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<?> updateClub(@Valid @RequestBody ClubDTO clubDTO, @PathVariable int id){
        int result = clubService.updateClub(clubDTO, id);

        if(result == 1)
            return ResponseEntity.ok().build();

        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteById(@PathVariable int id){
        boolean response = clubService.deleteById(id);

        return ResponseEntity.ok(response);
    }

}
