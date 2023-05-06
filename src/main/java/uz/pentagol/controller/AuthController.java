package uz.pentagol.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pentagol.dto.authentication.RegistrationDTO;
import uz.pentagol.dto.authentication.RegistrationResponseDTO;
import uz.pentagol.dto.authorization.AuthLoginDTO;
import uz.pentagol.dto.authorization.AuthResponseDTO;
import uz.pentagol.service.AuthService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationDTO registrationDTO){
        RegistrationResponseDTO response = authService.register(registrationDTO);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthLoginDTO authLoginDTO){
        AuthResponseDTO authResponseDTO = authService.login(authLoginDTO);

        return ResponseEntity.ok(authResponseDTO);
    }

    @GetMapping("/verify/email/{jwtToken}")
    public ResponseEntity<?> emailVerification(@PathVariable("jwtToken") String token){
        String response = authService.verifyEmail(token);

        return ResponseEntity.ok(response);
    }
}
