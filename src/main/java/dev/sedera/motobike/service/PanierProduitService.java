package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.repository.PanierProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PanierProduitService {

    private final PanierProduitRepository panierProduitRepository;

    public PanierProduitService(PanierProduitRepository panierProduitRepository) {
        this.panierProduitRepository = panierProduitRepository;
    }

    public List<PanierProduit> getAllPanierProduits() {

        return panierProduitRepository.findAll();
    }

    public Optional<PanierProduit> getPanierProduitById(Long id) {

        return panierProduitRepository.findById(id);
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

    public PanierProduit savePanierProduit(PanierProduit panierProduit) throws Exception {
        try{
        List<PanierProduit> existants = panierProduitRepository
                .findByPanierClientId(panierProduit.getPanier().getClient().getId());

        for (PanierProduit pp : existants) {
            if (pp.getProduit().getId().equals(panierProduit.getProduit().getId())) {
                pp.setQuantite(pp.getQuantite() + panierProduit.getQuantite());
                return panierProduitRepository.save(pp);
            }
        }

        return panierProduitRepository.save(panierProduit);
    }catch (Exception e){
        }throw new Exception("ererue de calss ");
    }


    public void deletePanierProduit(Long id) {

        panierProduitRepository.deleteById(id);
    }
    public List<PanierProduit> getPanierProduitsByClientId(Long clientId) {
        return panierProduitRepository.findByPanierClientId(clientId);
    }
}

