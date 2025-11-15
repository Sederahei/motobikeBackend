package dev.sedera.motobike.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.antlr.v4.runtime.misc.NotNull;

public record ProduitRequest(
        @NotBlank String nom,
        @NotBlank String type,         // "moto", "bicyclette", "accessoire"
        @NotBlank String marque,
        @NotNull @Positive Double prix,
        @NotNull @PositiveOrZero Integer stock,
        @Size(max = 5000) String description,
        @Size(max = 255) String imageUrl
) {}
