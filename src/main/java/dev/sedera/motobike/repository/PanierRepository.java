package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Panier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

    Panier findByClientId(Long clientId);
    @Override
    List<Panier> findAll();
}

