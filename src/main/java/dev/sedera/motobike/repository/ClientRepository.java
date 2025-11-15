package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Recherche par email (unique)
    Client findByEmail(String email);

    // Recherche par nom (plusieurs clients peuvent avoir le même nom)
    List<Client> findByNomIgnoreCase(String nom);

    // Recherche par téléphone
    List<Client> findByTelephone(String telephone);
}
