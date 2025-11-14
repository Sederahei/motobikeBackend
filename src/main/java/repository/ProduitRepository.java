package repository;

import entity.Produit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProduitRepository extends JpaRepository<Produit, Long> {
    // Exemple de méthode personnalisée
    // List<Produit> findByType(String type);
}

