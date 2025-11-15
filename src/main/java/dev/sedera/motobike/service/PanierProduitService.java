package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.repository.PanierProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanierProduitService {

    private final PanierProduitRepository panierProduitRepository;

    public PanierProduitService(PanierProduitRepository panierProduitRepository) {
        this.panierProduitRepository = panierProduitRepository;
    }

    public List<PanierProduit> getAllPanierProduits() {
        return panierProduitRepository.findAll();
    }

    public PanierProduit getPanierProduitById(Long id) {
        return panierProduitRepository.findById(id).orElseThrow();
    }

    public List<PanierProduit> getPanierProduitsByNom(String nom) {
        return panierProduitRepository.findByProduitNomContainingIgnoreCase(nom);
    }

    public List<PanierProduit> getPanierProduitsByType(String type) {
        return panierProduitRepository.findByProduitTypeContainingIgnoreCase(type);
    }

    public List<PanierProduit> getPanierProduitsByMarque(String marque) {
        return panierProduitRepository.findByProduitMarqueContainingIgnoreCase(marque);
    }

    public PanierProduit savePanierProduit(PanierProduit panierProduit) {
        return panierProduitRepository.save(panierProduit);
    }

    public void deletePanierProduit(Long id) {
        panierProduitRepository.deleteById(id);
    }
}

