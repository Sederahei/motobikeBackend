package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.service.PanierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;

    public PanierController(PanierService panierService) {
        this.panierService = panierService;
    }

    // ✅ Créer un panier
    @PostMapping
    public ResponseEntity<Panier> createPanier(@RequestBody Panier panier) {
        return ResponseEntity.ok(panierService.savePanier(panier));
    }

    // ✅ Récupérer un panier par clientId
    @GetMapping("/client/{clientId}")
    public ResponseEntity<Panier> getPanierByClientId(@PathVariable Long clientId) {
        Panier panier = panierService.getPanierByClientId(clientId);
        if (panier == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(panier);
    }

    // ✅ Supprimer un panier
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePanier(@PathVariable Long id) {
        panierService.deletePanier(id);
        return ResponseEntity.noContent().build();
    }
}

