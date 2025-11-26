package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Produit;
import dev.sedera.motobike.service.ProduitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService) {
        this.produitService = produitService;
    }

    @GetMapping
    public ResponseEntity<List<Produit>> getAllProduits() {
        return ResponseEntity.ok(produitService.getAllProduits());
    }

    @GetMapping("/stock/faible")
    public ResponseEntity<List<Produit>> getProduitsStockFaible(@RequestParam(defaultValue = "5") int seuil) {
        return ResponseEntity.ok(produitService.getProduitsStockFaible(seuil));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Produit> getProduitById(@PathVariable Long id) {
        Produit p = produitService.getProduitById(id);
        return ResponseEntity.ok(p);
    }

    @PostMapping
    public ResponseEntity<Produit> createProduit(@RequestBody Produit produit) {
        Produit saved = produitService.saveProduit(produit);
        return ResponseEntity.created(URI.create("/api/produits/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produit> updateProduit(@PathVariable Long id, @RequestBody Produit produit) {
        Produit existing = produitService.getProduitById(id);
        existing.setNom(produit.getNom());
        existing.setType(produit.getType());
        existing.setMarque(produit.getMarque());
        existing.setPrix(produit.getPrix());
        existing.setStock(produit.getStock());
        existing.setDescription(produit.getDescription());
        existing.setImageUrl(produit.getImageUrl());
        return ResponseEntity.ok(produitService.saveProduit(existing));
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<String> updateStock(@PathVariable Long id, @RequestParam Integer stock) {
        Produit existing = produitService.getProduitById(id);
        existing.setStock(stock);
        return ResponseEntity.ok(produitService.saveProduit(existing) + "stock a jour ");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduit(@PathVariable Long id) {
        produitService.deleteProduit(id);
        return ResponseEntity.noContent().build().ok("votre produit a ete supprimer");
    }
}
