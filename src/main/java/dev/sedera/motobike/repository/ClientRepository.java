package dev.sedera.motobike.repository;

import dev.sedera.motobike.entity.Client;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByEmailContainingIgnoreCase(String email);
    List<Client> findByNomContainingIgnoreCase(String nom);
    List<Client> findByTelephoneContainingIgnoreCase(String telephone);
    Optional<Client> findByEmail(String email);

}

