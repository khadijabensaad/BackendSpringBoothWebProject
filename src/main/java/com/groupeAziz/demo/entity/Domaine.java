package com.groupeAziz.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "domaines")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Domaine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String nom;

    // Relation Many-to-Many avec Chercheur
    @ManyToMany(mappedBy = "domaines")
    @JsonIgnore
    private List<Chercheur> chercheurs = new ArrayList<>();
}