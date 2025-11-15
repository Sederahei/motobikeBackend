
//
package dev.sedera.motobike.repository;


import dev.sedera.motobike.entity.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PanierProduitRepository extends JpaRepository<PanierProduit, Long> {

    List<PanierProduit> findByProduitNomContainingIgnoreCase(String nom);
    List<PanierProduit> findByProduitTypeContainingIgnoreCase(String type);
    List<PanierProduit> findByProduitMarqueContainingIgnoreCase(String marque);
}

