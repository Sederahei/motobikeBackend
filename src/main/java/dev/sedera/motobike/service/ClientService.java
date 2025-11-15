package dev.sedera.motobike.service;

import dev.sedera.motobike.entity.Client;
import dev.sedera.motobike.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    public Client getClientByEmail(String email) {
        return clientRepository.findByEmail(email);
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
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
