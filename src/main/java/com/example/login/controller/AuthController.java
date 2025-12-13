package com.example.login.controller;

import com.example.login.dto.JwtResponse;
import com.example.login.dto.SignupRequest;
import com.example.login.dto.LoginRequest;
import com.example.login.entity.User;
import com.example.login.service.JwtService;
import com.example.login.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:3001"}) // Next.js + ancien front
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SignupRequest signupRequest) {
        try {
            User user = userService.register(
                    signupRequest.getEmail(),
                    signupRequest.getPassword(),
                    signupRequest.getRole()
            );
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.authenticate(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
            );

            String token = jwtService.generateToken(user.getEmail());

            // adapte JwtResponse pour avoir { token, type }
            JwtResponse response = new JwtResponse(token);

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            e.printStackTrace(); // utile en dev
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/me-test")
    public ResponseEntity<?> meTest() {
        return ResponseEntity.ok("OK");
    }
}
