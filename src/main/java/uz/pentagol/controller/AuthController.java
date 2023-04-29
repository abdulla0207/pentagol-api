package uz.pentagol.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.RegistrationDTO;
import uz.pentagol.dto.autherization.AuthLoginDTO;
import uz.pentagol.dto.autherization.AuthResponseDTO;
import uz.pentagol.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationDTO registrationDTO){
        String response = authService.register(registrationDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthLoginDTO authLoginDTO){
        AuthResponseDTO authResponseDTO = authService.login(authLoginDTO);

        return ResponseEntity.ok(authResponseDTO);
    }
}
