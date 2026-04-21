package com.groupeAziz.demo.controller;

import com.groupeAziz.demo.dto.DomaineDTO;
import com.groupeAziz.demo.entity.Domaine;
import com.groupeAziz.demo.service.DomaineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/domaines")
@RequiredArgsConstructor
public class DomaineController {

    private final DomaineService domaineService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Domaine> createDomaine(@Valid @RequestBody DomaineDTO domaineDTO) {
        return ResponseEntity.ok(domaineService.createDomaine(domaineDTO));
    }

    @GetMapping
    public ResponseEntity<List<Domaine>> getAllDomaines() {
        return ResponseEntity.ok(domaineService.getAllDomaines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Domaine> getDomaineById(@PathVariable Long id) {
        return ResponseEntity.ok(domaineService.getDomaineById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Domaine> updateDomaine(
            @PathVariable Long id,
            @Valid @RequestBody DomaineDTO domaineDTO) {
        return ResponseEntity.ok(domaineService.updateDomaine(id, domaineDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> deleteDomaine(@PathVariable Long id) {
        domaineService.deleteDomaine(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Domaine supprimé avec succès");
        return ResponseEntity.ok(response);
    }
}