package com.groupeAziz.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publications")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Publication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titre;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime date;

    @Column(columnDefinition = "TEXT")
    private String description;

    // Relation Many-to-One avec Chercheur (un chercheur peut avoir plusieurs publications)
    @ManyToOne
    @JoinColumn(name = "chercheur_id")
    @JsonIgnore
    private Chercheur chercheur;

    // Relation Many-to-Many avec Utilisateur (consultations)
    @ManyToMany
    @JoinTable(
            name = "publication_consultations",
            joinColumns = @JoinColumn(name = "publication_id"),
            inverseJoinColumns = @JoinColumn(name = "utilisateur_id")
    )
    @JsonIgnore
    private List<Utilisateur> consultations = new ArrayList<>();

    // Helper methods pour la consultation
    public void addConsultation(Utilisateur utilisateur) {
        consultations.add(utilisateur);
        utilisateur.getPublicationsConsultees().add(this);
    }

    public void removeConsultation(Utilisateur utilisateur) {
        consultations.remove(utilisateur);
        utilisateur.getPublicationsConsultees().remove(this);
    }
}
