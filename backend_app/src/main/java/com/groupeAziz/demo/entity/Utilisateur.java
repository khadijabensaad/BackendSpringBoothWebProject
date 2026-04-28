package com.groupeAziz.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "utilisateurs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Utilisateur implements UserDetails {

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(nullable = false)
    private String password;

    // Relation Many-to-Many avec Publication (consultations)
    @ManyToMany(mappedBy = "consultations")
    @JsonIgnore
    private List<Publication> publicationsConsultees = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return cin;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Helper method pour ajouter une consultation
    public void addConsultation(Publication publication) {
        publicationsConsultees.add(publication);
        publication.getConsultations().add(this);
    }

    public void removeConsultation(Publication publication) {
        publicationsConsultees.remove(publication);
        publication.getConsultations().remove(this);
    }
}
