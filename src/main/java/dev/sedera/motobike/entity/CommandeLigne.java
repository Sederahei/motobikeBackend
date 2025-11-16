package dev.sedera.motobike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "commande_ligne")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class CommandeLigne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonIgnore   // ✅ évite boucle infinie Commande ↔ CommandeLigne
    @JoinColumn(name = "commande_id", nullable = false)
    private Commande commande;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    private Integer quantite;
    private Double prixUnitaire;
    private Double sousTotal;
}
