package com.groupeAziz.demo.service;

import com.groupeAziz.demo.dto.UpdateUtilisateurDTO;
import com.groupeAziz.demo.dto.UtilisateurDTO;
import com.groupeAziz.demo.entity.Role;
import com.groupeAziz.demo.entity.Utilisateur;
import com.groupeAziz.demo.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    // Consulter tous les utilisateurs (Admin seulement)
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Consulter un utilisateur par ID
    public Utilisateur getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + id));
    }

    // Consulter un utilisateur par CIN
    public Utilisateur getUtilisateurByCin(String cin) {
        return utilisateurRepository.findByCin(cin)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec le CIN: " + cin));
    }

    // Consulter le profil de l'utilisateur connecté
    public Utilisateur getCurrentUserProfile() {
        String currentCin = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUtilisateurByCin(currentCin);
    }

    // Vérifier si un utilisateur existe
    public boolean utilisateurExists(String cin) {
        return utilisateurRepository.existsByCin(cin);
    }

    // Modifier son propre profil
    @Transactional
    public Utilisateur updateMyProfile(UpdateUtilisateurDTO updateDTO) {
        String currentCin = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur utilisateur = getUtilisateurByCin(currentCin);

        // Mettre à jour les champs
        if (updateDTO.getNom() != null) {
            utilisateur.setNom(updateDTO.getNom());
        }
        if (updateDTO.getPrenom() != null) {
            utilisateur.setPrenom(updateDTO.getPrenom());
        }
        if (updateDTO.getAdresse() != null) {
            utilisateur.setAdresse(updateDTO.getAdresse());
        }

        // Mettre à jour le mot de passe si fourni
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    // Modifier un utilisateur par ID (Admin seulement)
    @Transactional
    public Utilisateur updateUtilisateurById(Long id, UpdateUtilisateurDTO updateDTO) {
        Utilisateur utilisateur = getUtilisateurById(id);

        // Mettre à jour les champs
        if (updateDTO.getNom() != null) {
            utilisateur.setNom(updateDTO.getNom());
        }
        if (updateDTO.getPrenom() != null) {
            utilisateur.setPrenom(updateDTO.getPrenom());
        }
        if (updateDTO.getAdresse() != null) {
            utilisateur.setAdresse(updateDTO.getAdresse());
        }

        // Seul un admin peut changer le rôle
        if (updateDTO.getRole() != null) {
            utilisateur.setRole(updateDTO.getRole());
        }

        // Mettre à jour le mot de passe si fourni
        if (updateDTO.getPassword() != null && !updateDTO.getPassword().isEmpty()) {
            utilisateur.setPassword(passwordEncoder.encode(updateDTO.getPassword()));
        }

        return utilisateurRepository.save(utilisateur);
    }

    // Changer son mot de passe
    @Transactional
    public void changePassword(String currentPassword, String newPassword, String confirmPassword) {
        // Vérifier que les nouveaux mots de passe correspondent
        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Les nouveaux mots de passe ne correspondent pas");
        }

        String currentCin = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur utilisateur = getUtilisateurByCin(currentCin);

        // Vérifier l'ancien mot de passe
        if (!passwordEncoder.matches(currentPassword, utilisateur.getPassword())) {
            throw new RuntimeException("Mot de passe actuel incorrect");
        }

        // Mettre à jour le mot de passe
        utilisateur.setPassword(passwordEncoder.encode(newPassword));
        utilisateurRepository.save(utilisateur);
    }

    // Supprimer son propre compte
    @Transactional
    public void deleteMyAccount() {
        String currentCin = SecurityContextHolder.getContext().getAuthentication().getName();
        Utilisateur utilisateur = getUtilisateurByCin(currentCin);

        // Vérifier que ce n'est pas le dernier admin (optionnel)
        if (utilisateur.getRole() == Role.ADMIN) {
            long adminCount = utilisateurRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();
            if (adminCount <= 1) {
                throw new RuntimeException("Impossible de supprimer le dernier administrateur");
            }
        }

        utilisateurRepository.delete(utilisateur);
    }

    // Supprimer un utilisateur par ID (Admin seulement)
    @Transactional
    public void deleteUtilisateurById(Long id) {
        Utilisateur utilisateur = getUtilisateurById(id);

        // Empêcher la suppression du dernier admin
        if (utilisateur.getRole() == Role.ADMIN) {
            long adminCount = utilisateurRepository.findAll().stream()
                    .filter(u -> u.getRole() == Role.ADMIN)
                    .count();
            if (adminCount <= 1) {
                throw new RuntimeException("Impossible de supprimer le dernier administrateur");
            }
        }

        utilisateurRepository.delete(utilisateur);
    }

    // Supprimer un utilisateur par CIN (Admin seulement)
    @Transactional
    public void deleteUtilisateurByCin(String cin) {
        Utilisateur utilisateur = getUtilisateurByCin(cin);
        deleteUtilisateurById(utilisateur.getId());
    }
}
