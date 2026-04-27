package com.groupeAziz.demo.controller;

import com.groupeAziz.demo.dto.ChercheurDTO;
import com.groupeAziz.demo.entity.Chercheur;
import com.groupeAziz.demo.service.ChercheurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chercheurs")
@RequiredArgsConstructor
public class ChercheurController {

    private final ChercheurService chercheurService;

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATEUR')")
    public ResponseEntity<Chercheur> createChercheur(@Valid @RequestBody ChercheurDTO chercheurDTO) {
        return ResponseEntity.ok(chercheurService.createChercheur(chercheurDTO));
    }

    @GetMapping
    public ResponseEntity<List<Chercheur>> getAllChercheurs() {
        return ResponseEntity.ok(chercheurService.getAllChercheurs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chercheur> getChercheurById(@PathVariable Long id) {
        return ResponseEntity.ok(chercheurService.getChercheurById(id));
    }

    @GetMapping("/cin/{cin}")
    public ResponseEntity<Chercheur> getChercheurByCin(@PathVariable String cin) {
        return ResponseEntity.ok(chercheurService.getChercheurByCin(cin));
    }

    @GetMapping("/domaine/{domaineId}")
    public ResponseEntity<List<Chercheur>> getChercheursByDomaine(@PathVariable Long domaineId) {
        return ResponseEntity.ok(chercheurService.getChercheursByDomaine(domaineId));
    }

    @GetMapping("/with-publications")
    public ResponseEntity<List<Chercheur>> getChercheursWithPublications() {
        return ResponseEntity.ok(chercheurService.getChercheursWithPublications());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATEUR')")
    public ResponseEntity<Chercheur> updateChercheur(
            @PathVariable Long id,
            @Valid @RequestBody ChercheurDTO chercheurDTO) {
        return ResponseEntity.ok(chercheurService.updateChercheur(id, chercheurDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteChercheur(@PathVariable Long id) {
        chercheurService.deleteChercheur(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Chercheur supprimé avec succès");
        return ResponseEntity.ok(response);
    }
}