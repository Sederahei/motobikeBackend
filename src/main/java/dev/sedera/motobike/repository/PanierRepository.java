package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {
    // Permet de retrouver le panier d’un client
    Panier findByClientId(Long clientId);
}

