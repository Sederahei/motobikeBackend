package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Panier;
import dev.sedera.motobike.entity.Produit;
import dev.sedera.motobike.repository.PanierRepository;
import dev.sedera.motobike.service.PanierService;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import javax.management.RuntimeErrorException;
import java.util.List;

@RestController
@RequestMapping("/api/paniers")
public class PanierController {

    private final PanierService panierService;
    private final PanierRepository panierRepository;

    public PanierController(PanierService panierService, PanierRepository panierRepository) {
        this.panierService = panierService;
        this.panierRepository = panierRepository;
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

