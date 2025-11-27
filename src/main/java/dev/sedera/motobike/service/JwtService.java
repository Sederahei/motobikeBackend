package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Client;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService {

    // ✅ Clé secrète pour signer les tokens (à mettre dans application.properties)
    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Durée de validité du token (ex: 24h)
    private final long expirationMs = 24 * 60 * 60 * 1000;

    // ✅ Générer un token JWT
    public String generateToken(Client client) {
        return Jwts.builder()
                .setSubject(client.getEmail()) // identifiant principal
                .claim("role", client.getRole().name()) // rôle (CLIENT ou ADMIN)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    // ✅ Extraire les claims (infos du token)
    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Vérifier si le token est valide
    public boolean validateToken(String token, Client client) {
        String email = extractClaims(token).getSubject();
        return (email.equals(client.getEmail()) && !isTokenExpired(token));
    }

    // ✅ Vérifier expiration
    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
