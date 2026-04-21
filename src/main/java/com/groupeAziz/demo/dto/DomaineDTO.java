package com.groupeAziz.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DomaineDTO {
    private Long id;

    @NotBlank(message = "Nom du domaine is required")
    private String nom;
}
