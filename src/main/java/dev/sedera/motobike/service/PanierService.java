package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.repository.ClientRepository;
import dev.sedera.motobike.repository.PanierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PanierService {

    private final PanierRepository panierRepository;

    public PanierService(PanierRepository panierRepository) {
        this.panierRepository = panierRepository;
    }

    @Autowired
    private ClientRepository clientRepository;

    public Panier getPanierByClientId(Long clientId) {
        Panier panier = panierRepository.findByClientId(clientId);
        if (panier == null) {
            Client client = clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client introuvable"));
            panier = new Panier();
            panier.setClient(client);
            panier = panierRepository.save(panier);
        }
        return panier;
    }

    public  List<Panier> getAllPaniers() {
        try {
            return panierRepository.findAll();
        }catch (Exception e) {
            throw new RuntimeException("Erreur de recuperation des paniers");
        }
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
}
