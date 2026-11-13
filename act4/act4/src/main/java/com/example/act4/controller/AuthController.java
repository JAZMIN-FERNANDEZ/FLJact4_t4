package com.example.act4.controller;

import com.example.act4.dto.LoginRequest;
import com.example.act4.dto.RegisterRequest;
import com.example.act4.dto.UsuarioResponse;
import com.example.act4.entity.Usuario;
import com.example.act4.repository.UsuarioRepository;
import com.example.act4.security.JwtService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UsuarioRepository repo;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;

    public AuthController(UsuarioRepository repo, PasswordEncoder encoder, JwtService jwtService) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        if (repo.existsByEmail(req.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(Map.of("error", "Ese email ya está registrado"));
        }
        Usuario u = new Usuario();
        u.setNombre(req.nombre());
        u.setEmail(req.email());
        u.setPassword(encoder.encode(req.password()));
        repo.save(u);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new UsuarioResponse(u.getId(), u.getNombre(), u.getEmail()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest req) {
        Usuario u = repo.findByEmail(req.email()).orElse(null);

        if (u == null || !encoder.matches(req.password(), u.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email o contraseña incorrectos"));
        }
        String token = jwtService.generateToken(u.getEmail());
        return ResponseEntity.ok(Map.of("token", token));
    }
}