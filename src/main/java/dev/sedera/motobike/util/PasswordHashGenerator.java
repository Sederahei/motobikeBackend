package dev.sedera.motobike.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {

    public static void main(String[] args) {
        // Crée un encodeur BCrypt avec un "strength" de 10 (par défaut)
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Génère les hash pour tes mots de passe
        String adminHash = encoder.encode("admin123");
        String clientHash = encoder.encode("client123");
        String motobikeHash = encoder.encode("motobike"); // pour tes comptes perso

        // Affiche les résultats
        System.out.println("Admin hash:   " + adminHash);
        System.out.println("Client hash:  " + clientHash);
        System.out.println("Motobike hash:" + motobikeHash);
    }
}
