package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.*;
import dev.sedera.motobike.repository.CommandeRepository;
import dev.sedera.motobike.repository.PanierRepository;
import dev.sedera.motobike.repository.ProduitRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommandeService {

    private final CommandeRepository commandeRepository;
    private final PanierRepository panierRepository;
    private final ProduitRepository produitRepository;

    public CommandeService(CommandeRepository commandeRepository, PanierRepository panierRepository, ProduitRepository produitRepository) {
        this.commandeRepository = commandeRepository;
        this.panierRepository = panierRepository;
        this.produitRepository = produitRepository;
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

        if ("valider".equalsIgnoreCase(statut)) {
            for (CommandeLigne ligne : commande.getLignes()) {
                Produit produit = ligne.getProduit();


                if (produit.getStock() < ligne.getQuantite()) {
                    commande.setStatut("epuiser");
                    return commandeRepository.save(commande);
                }

                // décrémenter le stock réel
                produit.setStock(produit.getStock() - ligne.getQuantite());

                // libérer la réservation
                int reserveActuel = (produit.getStockReserve() == null ? 0 : produit.getStockReserve());
                produit.setStockReserve(reserveActuel - ligne.getQuantite());

                produitRepository.save(produit);
            }
        }

        if ("annuler".equalsIgnoreCase(statut)) {
            for (CommandeLigne ligne : commande.getLignes()) {
                Produit produit = ligne.getProduit();
                int reserveActuel = (produit.getStockReserve() == null ? 0 : produit.getStockReserve());
                produit.setStockReserve(reserveActuel - ligne.getQuantite());
                produitRepository.save(produit);
            }
        }

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


            Produit produit = pp.getProduit();
            int reserveActuel = (produit.getStockReserve() == null ? 0 : produit.getStockReserve());
            produit.setStockReserve(reserveActuel + pp.getQuantite());
            produitRepository.save(produit);

        }

        commande.setTotal(total);
        Commande saved = commandeRepository.save(commande);


        panier.getProduits().clear();
        panierRepository.save(panier);

        return saved;
    }
}
