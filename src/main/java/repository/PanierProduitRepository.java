package repository;


import entity.PanierProduit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PanierProduitRepository extends JpaRepository<PanierProduit, Long> {
}

