package com.groupeAziz.demo.dto;

import com.groupeAziz.demo.entity.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UtilisateurDTO {
    private Long id;

    @NotBlank(message = "CIN is required")
    private String cin;

    @NotBlank(message = "Nom is required")
    private String nom;

    @NotBlank(message = "Prenom is required")
    private String prenom;

    @NotBlank(message = "Adresse is required")
    private String adresse;

    @NotNull(message = "Role is required")
    private Role role;

    @NotBlank(message = "Password is required")
    private String password;
}
