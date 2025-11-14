package entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "produit")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(length = 20)
    private String type; // moto, bicyclette, accessoire

    private String marque;

    private Double prix;

    private Integer stock;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
}

