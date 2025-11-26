package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.entity.Produit;
import dev.sedera.motobike.repository.ProduitRepository;
import dev.sedera.motobike.service.PanierProduitService;
import dev.sedera.motobike.service.PanierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;
    private final ProduitRepository produitRepository;
    private final PanierProduitService panierProduitService;

    public PanierController(PanierService panierService,
                            ProduitRepository produitRepository,
                            PanierProduitService panierProduitService) {
        this.panierService = panierService;
        this.produitRepository = produitRepository;
        this.panierProduitService = panierProduitService;
    }

    @PostMapping
    public ResponseEntity<Panier> createPanier(@RequestBody Panier panier) {
        return ResponseEntity.ok(panierService.savePanier(panier));
    }

    @GetMapping
    public ResponseEntity<List<Panier>> getAllPaniers() {
        return ResponseEntity.ok(panierService.getAllPaniers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Panier> getPanierById(@PathVariable Long id) {
        return ResponseEntity.ok(panierService.getPanierById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<Panier> getPanierByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(panierService.getOrCreatePanier(clientId));
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

        Panier panier = panierService.getOrCreatePanier(clientId);
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
        List<PanierProduit> produits = panierProduitService.getPanierProduitsByClientId(clientId);
        if (produits.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(produits);
    }
    @DeleteMapping("/client/{clientId}/retirer/{produitId}")
    public ResponseEntity<Void> retirerProduitDuPanier(@PathVariable Long clientId, @PathVariable Long produitId) {
        panierService.retirerProduit(clientId, produitId);
        return ResponseEntity.noContent().build();
    }

}
