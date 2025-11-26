package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Commande;
import dev.sedera.motobike.entity.CommandeLigne;
import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.repository.CommandeRepository;
import dev.sedera.motobike.repository.PanierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final PanierRepository panierRepository;

    public CommandeService(CommandeRepository commandeRepository, PanierRepository panierRepository) {
        this.commandeRepository = commandeRepository;
        this.panierRepository = panierRepository;
    }

    // ✅ récupérer une commande par ID
    public Commande getCommandeById(Long id) {
        return commandeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Commande introuvable avec id " + id));
    }

    // ✅ récupérer toutes les commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // ✅ récupérer les commandes d’un client
    public List<Commande> getCommandesByClient(Long clientId) {
        return commandeRepository.findByClientId(clientId);
    }


    public List<Commande> getCommandesByStatut(String statut) {
        return commandeRepository.findByStatut(statut);
    }


    public Commande updateStatut(Long id, String statut) {
        Commande commande = getCommandeById(id);
        commande.setStatut(statut);
        return commandeRepository.save(commande);
    }

    // ✅ valider un panier en commande
    public Commande validerCommande(Long clientId) {
        Panier panier = panierRepository.findByClientId(clientId)
                .orElseThrow(() -> new IllegalStateException("Panier inexistant pour client " + clientId));

        if (panier.getProduits().isEmpty()) {
            throw new IllegalStateException("Panier vide pour client " + clientId);
        }

        Commande commande = new Commande();
        commande.setClient(panier.getClient());
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut("en_attente");

        double total = 0.0;

        for (PanierProduit pp : panier.getProduits()) {
            CommandeLigne ligne = new CommandeLigne();
            ligne.setCommande(commande);
            ligne.setProduit(pp.getProduit());
            ligne.setQuantite(pp.getQuantite());

            double prixUnitaire = pp.getProduit().getPrix();
            ligne.setPrixUnitaire(prixUnitaire);

            double sousTotal = prixUnitaire * pp.getQuantite();
            ligne.setSousTotal(sousTotal);

            total += sousTotal;
            commande.getLignes().add(ligne);
        }

        commande.setTotal(total);
        Commande saved = commandeRepository.save(commande);


        panier.getProduits().clear();
        panierRepository.save(panier);

        return saved;
    }
}
