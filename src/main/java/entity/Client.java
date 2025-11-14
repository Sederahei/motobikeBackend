package entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "client")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;

    @Column(unique = true, nullable = false)
    private String email;

    private String telephone;

    @Column(columnDefinition = "TEXT")
    private String adresse;
}

