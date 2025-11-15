package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.PanierProduit;
import dev.sedera.motobike.service.PanierProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/panier-produits")
public class PanierProduitController {

    private final PanierProduitService panierProduitService;

    public PanierProduitController(PanierProduitService panierProduitService) {
        this.panierProduitService = panierProduitService;
    }

    @GetMapping
    public ResponseEntity<List<PanierProduit>> getAllPanierProduits() {
        return ResponseEntity.ok(panierProduitService.getAllPanierProduits());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PanierProduit> getPanierProduitById(@PathVariable Long id) {
        return ResponseEntity.ok(panierProduitService.getPanierProduitById(id));
    }

    @GetMapping("/search/by-nom")
    public ResponseEntity<List<PanierProduit>> getPanierProduitsByNom(@RequestParam String nom) {
        return ResponseEntity.ok(panierProduitService.getPanierProduitsByNom(nom));
    }

    @GetMapping("/search/by-type")
    public ResponseEntity<List<PanierProduit>> getPanierProduitsByType(@RequestParam String type) {
        return ResponseEntity.ok(panierProduitService.getPanierProduitsByType(type));
    }

    @GetMapping("/search/by-marque")
    public ResponseEntity<List<PanierProduit>> getPanierProduitsByMarque(@RequestParam String marque) {
        return ResponseEntity.ok(panierProduitService.getPanierProduitsByMarque(marque));
    }

    @PostMapping
    public ResponseEntity<PanierProduit> createPanierProduit(@RequestBody PanierProduit panierProduit) {
        PanierProduit saved = panierProduitService.savePanierProduit(panierProduit);
        return ResponseEntity.created(URI.create("/api/panier-produits/" + saved.getId())).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanierProduit(@PathVariable Long id) {
        panierProduitService.deletePanierProduit(id);
        return ResponseEntity.noContent().build();
    }
}
