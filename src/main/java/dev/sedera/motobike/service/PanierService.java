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

    public Panier getPanierByClientId(Long clientId) {
        try {
            return panierRepository.findByClientId(clientId);
        }catch (Exception e){
            throw new RuntimeException(" erreur de recuperation de cle");
        }

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



    @Autowired
    private ClientRepository clientRepository;

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
