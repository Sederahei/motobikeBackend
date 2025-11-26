package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.repository.ClientRepository;
import dev.sedera.motobike.repository.PanierRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanierService {

    private final PanierRepository panierRepository;
    private final ClientRepository clientRepository;

    public PanierService(PanierRepository panierRepository, ClientRepository clientRepository) {
        this.panierRepository = panierRepository;
        this.clientRepository = clientRepository;
    }

    // ✅ retourne le panier existant ou en crée un nouveau rah tsy  mbola misy
    public Panier getOrCreatePanier(Long clientId) {
        return panierRepository.findByClientId(clientId)
                .orElseGet(() -> {
                    Client client = clientRepository.findById(clientId)
                            .orElseThrow(() -> new RuntimeException("Client introuvable"));
                    Panier panier = new Panier();
                    panier.setClient(client);
                    return panierRepository.save(panier);
                });
    }

    public List<Panier> getAllPaniers() {
        return panierRepository.findAll();
    }

    public Panier getPanierById(Long id) {
        return panierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Panier introuvable avec id " + id));
    }

    public Panier savePanier(Panier panier) {
        Client client = clientRepository.findById(panier.getClient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable"));
        panier.setClient(client);
        return panierRepository.save(panier);
    }

    public void deletePanier(Long id) {
        panierRepository.deleteById(id);
    }
    public void retirerProduit(Long clientId, Long produitId) {
        Panier panier = getOrCreatePanier(clientId);
        panier.getProduits().removeIf(pp -> pp.getProduit().getId().equals(produitId));
        panierRepository.save(panier);
    }

}
