package dev.sedera.motobike.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "panier")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Panier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    private LocalDateTime dateCreation = LocalDateTime.now();

    @OneToMany(mappedBy = "panier", cascade = CascadeType.ALL)
    private List<PanierProduit> produits;
}

