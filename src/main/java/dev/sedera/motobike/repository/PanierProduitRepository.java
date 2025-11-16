package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanierProduitRepository extends JpaRepository<PanierProduit, Long> {
    List<PanierProduit> findByProduitNomContainingIgnoreCase(String nom);
    List<PanierProduit> findByProduitTypeContainingIgnoreCase(String type);
    List<PanierProduit> findByProduitMarqueContainingIgnoreCase(String marque);

    // ✅ recherche par client via panier
    List<PanierProduit> findByPanierClientId(Long clientId);

}
