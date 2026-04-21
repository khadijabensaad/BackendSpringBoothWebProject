package com.groupeAziz.demo.controller;

import com.groupeAziz.demo.dto.PublicationDTO;
import com.groupeAziz.demo.entity.Publication;
import com.groupeAziz.demo.service.PublicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publications")
@RequiredArgsConstructor
public class PublicationController {

    private final PublicationService publicationService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATEUR')")
    public ResponseEntity<Publication> createPublication(@Valid @RequestBody PublicationDTO publicationDTO) {
        return ResponseEntity.ok(publicationService.createPublication(publicationDTO));
    }

    @GetMapping
    public ResponseEntity<List<Publication>> getAllPublications() {
        return ResponseEntity.ok(publicationService.getAllPublications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable Long id) {
        return ResponseEntity.ok(publicationService.getPublicationById(id));
    }

    @GetMapping("/chercheur/{chercheurId}")
    public ResponseEntity<List<Publication>> getPublicationsByChercheur(@PathVariable Long chercheurId) {
        return ResponseEntity.ok(publicationService.getPublicationsByChercheur(chercheurId));
    }

    @GetMapping("/recent")
    public ResponseEntity<List<Publication>> getRecentPublications() {
        return ResponseEntity.ok(publicationService.getRecentPublications());
    }

    @PostMapping("/{id}/consulter")
    public ResponseEntity<Publication> consulterPublication(@PathVariable Long id) {
        String currentCin = SecurityContextHolder.getContext().getAuthentication().getName();

        // Récupérer l'utilisateur par son CIN (vous devrez avoir un service pour ça)
        // Pour l'instant, supposons que vous avez un UtilisateurService

        return ResponseEntity.ok(publicationService.consulterPublication(id, 1L)); // À modifier avec l'ID réel
    }

    @GetMapping("/{id}/consultations/count")
    public ResponseEntity<Map<String, Long>> getConsultationCount(@PathVariable Long id) {
        Long count = publicationService.getConsultationCount(id);

        Map<String, Long> response = new HashMap<>();
        response.put("consultationCount", count);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATEUR')")
    public ResponseEntity<Publication> updatePublication(
            @PathVariable Long id,
            @Valid @RequestBody PublicationDTO publicationDTO) {
        return ResponseEntity.ok(publicationService.updatePublication(id, publicationDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deletePublication(@PathVariable Long id) {
        publicationService.deletePublication(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Publication supprimée avec succès");
        return ResponseEntity.ok(response);
    }
}
