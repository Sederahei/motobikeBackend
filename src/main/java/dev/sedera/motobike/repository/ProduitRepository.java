package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {
    List<Produit> findByTypeIgnoreCase(String type);
    List<Produit> findByMarqueIgnoreCase(String marque);
    List<Produit> findByPrixLessThanEqual(Double maxPrix);
    List<Produit> findByTypeIgnoreCaseAndMarqueIgnoreCaseAndPrixLessThanEqual(String type, String marque, Double maxPrix);
}
