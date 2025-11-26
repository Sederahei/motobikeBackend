package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Produit;
import dev.sedera.motobike.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final ProduitRepository produitRepository;

    public ProduitService(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    public List<Produit> searchProduits(String type, String marque, Double maxPrix) {
        if (type != null && marque != null && maxPrix != null) {
            return produitRepository.findByTypeIgnoreCaseAndMarqueIgnoreCaseAndPrixLessThanEqual(type, marque, maxPrix);
        } else if (type != null) {
            return produitRepository.findByTypeIgnoreCase(type);
        } else if (marque != null) {
            return produitRepository.findByMarqueIgnoreCase(marque);
        } else if (maxPrix != null) {
            return produitRepository.findByPrixLessThanEqual(maxPrix);
        } else {
            return produitRepository.findAll();
        }
    }


    public Produit getProduitById(Long id) {
        return produitRepository.findById(id).orElseThrow();
    }

    public Produit saveProduit(Produit produit) {
        return produitRepository.save(produit);
    }

    public void deleteProduit(Long id) {
        produitRepository.deleteById(id);
    }


    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    public List<Produit> getProduitsStockFaible(int seuil) {
        return produitRepository.findByStockLessThan(seuil);
    }
}

