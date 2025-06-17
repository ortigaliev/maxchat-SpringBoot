package api.maxchat.maxchat.controller;

import api.maxchat.maxchat.dto.AuthDTO;
import api.maxchat.maxchat.dto.ProfileDTO;
import api.maxchat.maxchat.dto.RegistrationDTO;
import api.maxchat.maxchat.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO dto) {
        System.out.println("Register dto=====" + dto);
        return ResponseEntity.ok().body(authService.register(dto));
    }

    @GetMapping("/register/verification/{token}")
    public ResponseEntity<String> regVerification(@PathVariable("token") String token) {
        return ResponseEntity.ok().body(authService.regVerification(token));
    }

    @PostMapping("/login")
    public ResponseEntity<ProfileDTO> login(@Valid @RequestBody AuthDTO dto) {
        System.out.println("LOGIN dto=====" + dto);
        return ResponseEntity.ok().body(authService.login(dto));
    }
}
