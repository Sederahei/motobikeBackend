package dev.sedera.motobike.controller;

import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        return ResponseEntity.ok(clientService.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping("/search/by-email")
    public ResponseEntity<Client> getClientByEmail(@RequestParam String email) {
        return ResponseEntity.ok(clientService.getClientByEmail(email));
    }

    @GetMapping("/search/by-nom")
    public ResponseEntity<List<Client>> getClientsByNom(@RequestParam String nom) {
        return ResponseEntity.ok(clientService.getClientsByNom(nom));
    }
    @GetMapping("/nom/{nom}")
    public ResponseEntity<List<Client>> getClientsByNomPath(@PathVariable String nom) {
        return ResponseEntity.ok(clientService.getClientsByNom(nom));
    }

    @GetMapping("/search/by-telephone")
    public ResponseEntity<List<Client>> getClientsByTelephone(@RequestParam String telephone) {
        return ResponseEntity.ok(clientService.getClientsByTelephone(telephone));
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        Client saved = clientService.saveClient(client);
        return ResponseEntity.created(URI.create("/api/clients/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client) {
        Client existing = clientService.getClientById(id);
        existing.setNom(client.getNom());
        existing.setEmail(client.getEmail());
        existing.setTelephone(client.getTelephone());
        existing.setAdresse(client.getAdresse());
        return ResponseEntity.ok(clientService.saveClient(existing));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
