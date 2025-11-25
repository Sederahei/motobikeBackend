package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.entity.Produit;
import dev.sedera.motobike.repository.ClientRepository;
import dev.sedera.motobike.repository.PanierRepository;
import dev.sedera.motobike.repository.ProduitRepository;
import dev.sedera.motobike.service.PanierProduitService;
import dev.sedera.motobike.service.PanierService;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeErrorException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;
    private final PanierRepository panierRepository;
    private final ClientRepository clientRepository;
    private final ProduitRepository produitRepository;
    private final PanierProduitService panierProduitService;

    public PanierController(PanierService panierService, PanierRepository panierRepository,PanierProduitService panierProduitService, ClientRepository clientRepository, ProduitRepository produitRepository) {
        this.panierService = panierService;
        this.panierRepository = panierRepository;
        this.clientRepository = clientRepository;
        this.produitRepository = produitRepository;
        this.panierProduitService = panierProduitService;
    }
    @PostMapping
    public ResponseEntity<Panier> createPanier(@RequestBody Panier panier) {
        return ResponseEntity.ok(panierService.savePanier(panier));
    }
    @GetMapping
    public final ResponseEntity<List<Panier>> getAllPaniers() {
        try {
            List<Panier> paniers = panierService.getAllPaniers();
            return ResponseEntity.ok(paniers);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Panier> getPanierById(@PathVariable Long id) {

        return panierRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/client/{clientId}")
    public ResponseEntity<Panier> getPanierByClientId(@PathVariable Long clientId) {
        Panier panier = panierService.getPanierByClientId(clientId);
        if (panier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(panier);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        panierService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/client/{clientId}/ajouter/{produitId}")
    public ResponseEntity<PanierProduit> ajouterProduitAuPanier(
            @PathVariable Long clientId,
            @PathVariable Long produitId,
            @RequestBody(required = false) Map<String, Integer> body) {

        int quantite = (body != null) ? body.getOrDefault("quantite", 1) : 1;


        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client introuvable"));


        Panier panier = panierService.getPanierByClientId(clientId);
        if (panier == null) {
            panier = new Panier();
            panier.setClient(client);
            panier = panierService.savePanier(panier);
        }
        Produit produit = produitRepository.findById(produitId)
                .orElseThrow(() -> new RuntimeException("Produit introuvable"));

        PanierProduit panierProduit = new PanierProduit();
        panierProduit.setPanier(panier);
        panierProduit.setProduit(produit);
        panierProduit.setQuantite(quantite);

        try {
            PanierProduit saved = panierProduitService.savePanierProduit(panierProduit);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("/client/{clientId}/produits")
    public ResponseEntity<List<PanierProduit>> getProduitsDuPanier(@PathVariable Long clientId) {
        try {
            List<PanierProduit> produits = panierProduitService.getPanierProduitsByClientId(clientId);
            if (produits == null || produits.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(produits);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}

