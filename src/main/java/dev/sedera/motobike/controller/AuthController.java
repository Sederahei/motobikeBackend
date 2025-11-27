package dev.sedera.motobike.controller;

import dev.sedera.motobike.dto.LoginRequest;
import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.repository.ClientRepository;
import dev.sedera.motobike.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final ClientRepository clientRepository;
    private final JwtService jwtService;

    public AuthController(ClientRepository clientRepository, JwtService jwtService) {
        this.clientRepository = clientRepository;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Client client = clientRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        if (!client.getMotDePasse().equals(request.getMotDePasse())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Mot de passe incorrect");
        }

        String token = jwtService.generateToken(client);
        return ResponseEntity.ok(Map.of("token", token, "role", client.getRole()));
    }


}
