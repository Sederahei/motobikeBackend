package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Commande;
import dev.sedera.motobike.service.CommandeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController   // ✅ seulement RestController
@RequestMapping("/api/commandes")
public class CommandeController {
    private final CommandeService commandeService;

    public CommandeController(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    @PostMapping("/valider/{clientId}")
    public ResponseEntity<Commande> validerCommande(@PathVariable Long clientId) {

        return ResponseEntity.ok(commandeService.validerCommande(clientId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {

        return ResponseEntity.ok(commandeService.getCommandeById(id));
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Commande>> getCommandesByClient(@PathVariable Long clientId) {

        return ResponseEntity.ok(commandeService.getCommandesByClient(clientId));
    }
    @GetMapping
    public ResponseEntity<List<Commande>> getAllCommandes() {

        return ResponseEntity.ok(commandeService.getAllCommandes());
    }
    @PutMapping("/{id}/statut")
    public ResponseEntity<Commande> updateStatut(@PathVariable Long id, @RequestParam String statut) {

        return ResponseEntity.ok(commandeService.updateStatut(id, statut));
    }


    @GetMapping("/statut/{statut}")
    public ResponseEntity<List<Commande>> getCommandesByStatut(@PathVariable String statut) {

        return ResponseEntity.ok(commandeService.getCommandesByStatut(statut));
    }


}
