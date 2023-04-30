package uz.pentagol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.JwtDTO;
import uz.pentagol.exceptions.AppBadRequest;
import uz.pentagol.service.UserService;
import uz.pentagol.util.JwtUtil;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getById(@RequestHeader("Authorization") String headerToken){
        JwtDTO jwtDTO = JwtUtil.decode(JwtUtil.getToken(headerToken));

        if(jwtDTO==null)
            throw new AppBadRequest("Wrong token");
        return ResponseEntity.ok(jwtDTO);
    }
}
