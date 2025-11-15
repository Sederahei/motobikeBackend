package dev.sedera.motobike.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "commande")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDateTime dateCommande = LocalDateTime.now();

    private String statut = "en_attente";

    private Double total;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<CommandeLigne> lignes;
}

