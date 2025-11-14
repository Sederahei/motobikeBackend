package entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "panier_produit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class PanierProduit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "panier_id", nullable = false)
    private Panier panier;

    @ManyToOne
    @JoinColumn(name = "produit_id", nullable = false)
    private Produit produit;

    private Integer quantite;
}

