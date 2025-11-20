package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Commande;
import dev.sedera.motobike.entity.CommandeLigne;
import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.repository.CommandeRepository;
import dev.sedera.motobike.repository.PanierRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandeService {
    private final CommandeRepository commandeRepository;
    private final PanierRepository panierRepository;

    public CommandeService(CommandeRepository commandeRepository, PanierRepository panierRepository) {
        this.commandeRepository = commandeRepository;
        this.panierRepository = panierRepository;
    }


    public Commande validerCommande(Long clientId) {
        Panier panier = panierRepository.findByClientId(clientId);
        if (panier == null || panier.getProduits().isEmpty()) {
            throw new IllegalStateException("Panier vide ou inexistant pour client " + clientId);
        }

        Commande commande = new Commande();
        commande.setClient(panier.getClient());
        commande.setDateCommande(LocalDateTime.now());
        commande.setStatut("en_attente");

        commande.setLignes(
                panier.getProduits().stream().map(pp -> {
                    CommandeLigne ligne = new CommandeLigne();
                    ligne.setCommande(commande);
                    ligne.setProduit(pp.getProduit());
                    ligne.setQuantite(pp.getQuantite());
                    ligne.setPrixUnitaire(pp.getProduit().getPrix());
                    ligne.setSousTotal(pp.getQuantite() * pp.getProduit().getPrix());
                    return ligne;
                }).collect(Collectors.toList())
        );

        double total = commande.getLignes().stream()
                .mapToDouble(CommandeLigne::getSousTotal)
                .sum();
        commande.setTotal(total);
        Commande savedCommande = commandeRepository.save(commande);


        panier.getProduits().clear();
        panierRepository.save(panier);

        return savedCommande;
    }

    public Commande getCommandeById(Long id) {

        return commandeRepository.findById(id).orElseThrow();
    }
    public List <Commande> getAllCommandes(){
        return commandeRepository.findAll();
    }
    public List<Commande> getCommandesByClient(Long clientId) {

        return commandeRepository.findByClientId(clientId);
    }

    public Commande updateStatut(Long commandeId, String statut) {
        Commande commande = commandeRepository.findById(commandeId)
                .orElseThrow(() -> new IllegalArgumentException("Commande introuvable"));
        commande.setStatut(statut);
        return commandeRepository.save(commande);
    }

    public List<Commande> getCommandesByStatut(String statut) {

        return commandeRepository.findByStatut(statut);

    }

}
