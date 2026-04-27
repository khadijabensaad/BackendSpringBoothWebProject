package com.groupeAziz.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class ChercheurDTO {
    private Long id;

    @NotBlank(message = "CIN is required")
    private String cin;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Adresse is required")
    private String adresse;

    private List<Long> domaineIds; // IDs des domaines auxquels le chercheur appartient
    private List<String> domainesNoms; // Noms des domaines (pour l'affichage)
}