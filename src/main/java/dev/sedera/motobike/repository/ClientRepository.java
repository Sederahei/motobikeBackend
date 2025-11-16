package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Client;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByEmail(String email);
    List<Client> findByEmailContainingIgnoreCase(String email);
    List<Client> findByNomContainingIgnoreCase(String nom);
    List<Client> findByTelephoneContainingIgnoreCase(String telephone);
}

