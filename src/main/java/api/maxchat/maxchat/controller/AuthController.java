package api.maxchat.maxchat.controller;

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
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO registration) {
        return ResponseEntity.ok().body(authService.register(registration));
    }

    @GetMapping("/register/verification/{profileId}")
    public ResponseEntity<String> regVerification(@PathVariable("profileId") Integer profileId) {
        return ResponseEntity.ok().body(authService.regVerification(profileId));
    }
}
