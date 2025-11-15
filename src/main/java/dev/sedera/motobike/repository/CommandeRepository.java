package dev.sedera.motobike.repository;


import dev.sedera.motobike.entity.Commande;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {
    // Exemple : trouver toutes les commandes d’un client
    List<Commande> findByClientId(Long clientId);
}

