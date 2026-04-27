package com.groupeAziz.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chercheurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chercheur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String cin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String prenom;

    @Column(nullable = false)
    private String adresse;

    // Relation Many-to-Many avec Domaine
    @ManyToMany
    @JoinTable(
            name = "chercheur_domaine",
            joinColumns = @JoinColumn(name = "chercheur_id"),
            inverseJoinColumns = @JoinColumn(name = "domaine_id")
    )
    @JsonIgnore
    private List<Domaine> domaines = new ArrayList<>();

    // Relation One-to-Many avec Publication (un chercheur peut avoir plusieurs publications)
    @OneToMany(mappedBy = "chercheur", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Publication> publications = new ArrayList<>();

    // Helper methods pour gérer la relation Many-to-Many
    public void addDomaine(Domaine domaine) {
        domaines.add(domaine);
        domaine.getChercheurs().add(this);
    }

    public void removeDomaine(Domaine domaine) {
        domaines.remove(domaine);
        domaine.getChercheurs().remove(this);
    }

    // Helper method pour ajouter une publication
    public void addPublication(Publication publication) {
        publications.add(publication);
        publication.setChercheur(this);
    }

    public void removePublication(Publication publication) {
        publications.remove(publication);
        publication.setChercheur(null);
    }
}