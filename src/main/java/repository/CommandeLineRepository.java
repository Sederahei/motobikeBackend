package repository;


import entity.CommandeLigne;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommandeLineRepository extends JpaRepository<CommandeLigne, Long> {
}

