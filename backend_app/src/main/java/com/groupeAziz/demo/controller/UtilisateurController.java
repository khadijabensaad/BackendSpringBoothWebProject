package com.groupeAziz.demo.controller;

import com.groupeAziz.demo.dto.ChangePasswordDTO;
import com.groupeAziz.demo.dto.UpdateUtilisateurDTO;
import com.groupeAziz.demo.entity.Utilisateur;
import com.groupeAziz.demo.service.UtilisateurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    // ==================== CONSULTATION ====================

    // Consulter tous les utilisateurs (Admin seulement)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(utilisateurService.getAllUtilisateurs());
    }

    // Consulter un utilisateur par ID (Admin seulement)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utilisateur> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurById(id));
    }

    // Consulter un utilisateur par CIN (Admin seulement)
    @GetMapping("/cin/{cin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utilisateur> getUserByCin(@PathVariable String cin) {
        return ResponseEntity.ok(utilisateurService.getUtilisateurByCin(cin));
    }

    // Consulter son propre profil (Authentifié)
    @GetMapping("/me")
    public ResponseEntity<Utilisateur> getMyProfile() {
        return ResponseEntity.ok(utilisateurService.getCurrentUserProfile());
    }

    // Vérifier si un utilisateur existe (Public)
    @GetMapping("/exists/{cin}")
    public ResponseEntity<Map<String, Boolean>> checkUserExists(@PathVariable String cin) {
        boolean exists = utilisateurService.utilisateurExists(cin);
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return ResponseEntity.ok(response);
    }



    // Modifier son propre profil
    @PutMapping("/me")
    public ResponseEntity<Utilisateur> updateMyProfile(@Valid @RequestBody UpdateUtilisateurDTO updateDTO) {
        return ResponseEntity.ok(utilisateurService.updateMyProfile(updateDTO));
    }

    // Modifier un utilisateur par ID (Admin seulement)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Utilisateur> updateUserById(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUtilisateurDTO updateDTO) {
        return ResponseEntity.ok(utilisateurService.updateUtilisateurById(id, updateDTO));
    }

    // Changer son mot de passe
    @PutMapping("/me/password")
    public ResponseEntity<Map<String, String>> changePassword(@Valid @RequestBody ChangePasswordDTO passwordDTO) {
        utilisateurService.changePassword(
                passwordDTO.getCurrentPassword(),
                passwordDTO.getNewPassword(),
                passwordDTO.getConfirmPassword()
        );

        Map<String, String> response = new HashMap<>();
        response.put("message", "Mot de passe changé avec succès");
        return ResponseEntity.ok(response);
    }



    // Supprimer son propre compte
    @DeleteMapping("/me")
    public ResponseEntity<Map<String, String>> deleteMyAccount() {
        utilisateurService.deleteMyAccount();

        Map<String, String> response = new HashMap<>();
        response.put("message", "Compte supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    // Supprimer un utilisateur par ID (Admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUserById(@PathVariable Long id) {
        utilisateurService.deleteUtilisateurById(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur supprimé avec succès");
        return ResponseEntity.ok(response);
    }

    // Supprimer un utilisateur par CIN (Admin seulement)
    @DeleteMapping("/cin/{cin}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteUserByCin(@PathVariable String cin) {
        utilisateurService.deleteUtilisateurByCin(cin);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur supprimé avec succès");
        return ResponseEntity.ok(response);
    }
}