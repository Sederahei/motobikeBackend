package dev.sedera.motobike.repository;


import dev.sedera.motobike.entity.CommandeLigne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeLineRepository extends JpaRepository<CommandeLigne, Long> {
}

