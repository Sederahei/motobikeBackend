package dev.sedera.motobike.entity;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@Table(name = "produit")
@NoArgsConstructor @AllArgsConstructor
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(length = 20)
    private String type;
    private String marque;
    private Double prix;
    private Integer stock;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String imageUrl;
}

