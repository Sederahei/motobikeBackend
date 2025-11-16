package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.repository.PanierRepository;
import org.springframework.stereotype.Service;

@Service
public class PanierService {

    private final PanierRepository panierRepository;

    public PanierService(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    public Panier getPanierByClientId(Long clientId) {
        return panierRepository.findByClientId(clientId);
    }

    public Panier savePanier(Panier panier) {
        return panierRepository.save(panier);
    }

    public void deletePanier(Long id) {
        panierRepository.deleteById(id);
    }
}
