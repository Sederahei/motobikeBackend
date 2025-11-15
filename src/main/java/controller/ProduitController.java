package controller;

import entity.Produit;
import repository.ProduitRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitRepository produitRepository;

    public ProduitController(ProduitRepository produitRepository) {
        this.produitRepository = produitRepository;
    }

    // Récupérer tous les produits
    @GetMapping
    public List<Produit> getAllProduits() {
        return produitRepository.findAll();
    }

    // Récupérer un produit par ID
    @GetMapping("/{id}")
    public Produit getProduitById(@PathVariable Long id) {
        return produitRepository.findById(id).orElseThrow();
    }

    // Ajouter un produit
    @PostMapping
    public Produit createProduit(@RequestBody Produit produit) {
        return produitRepository.save(produit);
    }

    // Supprimer un produit
    @DeleteMapping("/{id}")
    public void deleteProduit(@PathVariable Long id) {
        produitRepository.deleteById(id);
    }
}