package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

    Panier findByClientId(Long clientId);
}

