package com.groupeAziz.demo.dto;

import com.groupeAziz.demo.entity.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateUtilisateurDTO {

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Adresse is required")
    private String adresse;

    private Role role; // Optionnel, seulement pour les admins

    private String password; // Optionnel, pour changer le mot de passe
}