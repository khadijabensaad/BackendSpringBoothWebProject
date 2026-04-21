package com.groupeAziz.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class PublicationDTO {
    private Long id;

    @NotBlank(message = "Titre is required")
    private String titre;

    private LocalDateTime date;

    @NotBlank(message = "Description is required")
    private String description;

    private Long chercheurId;
    private String chercheurNom; // Pour l'affichage
    private String chercheurPrenom;

    private List<Long> consultationsUtilisateurIds; // IDs des utilisateurs qui ont consulté
    private Long consultationCount; // Nombre de consultations
}
