package com.pathfinder.controller;

import com.pathfinder.dto.LoginRequestDTO;
import com.pathfinder.dto.LoginResponseDTO;
import com.pathfinder.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO req) {
        return ResponseEntity.ok(authService.login(req));
    }
}
