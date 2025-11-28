package dev.sedera.motobike.service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.repository.ClientRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    public ClientService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("id introuvable avec cette id :" + id));
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Client introuvable avec email: " + email));
    }

    public List<Client> getClientsByMail(String email) {
        return clientRepository.findByEmailContainingIgnoreCase(email);
    }

    public List<Client> getClientsByTelephone(String telephone) {
        return clientRepository.findByTelephoneContainingIgnoreCase(telephone);
    }

    public List<Client> getClientsByNom(String nom) {
        return clientRepository.findByNomContainingIgnoreCase(nom);
    }

    public Client saveClient(Client client) {
        client.setMotDePasse(passwordEncoder.encode(client.getMotDePasse()));
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
