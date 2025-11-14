package repository;


import entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
    // Exemple : rechercher par email
    Client findByEmail(String email);
}

