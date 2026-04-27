package com.groupeAziz.demo.service;

import com.groupeAziz.demo.dto.ChercheurDTO;
import com.groupeAziz.demo.entity.Chercheur;
import com.groupeAziz.demo.entity.Domaine;
import com.groupeAziz.demo.repository.ChercheurRepository;
import com.groupeAziz.demo.repository.DomaineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChercheurService {

    private final ChercheurRepository chercheurRepository;
    private final DomaineRepository domaineRepository;

    @Transactional
    public Chercheur createChercheur(ChercheurDTO chercheurDTO) {
        if (chercheurRepository.existsByCin(chercheurDTO.getCin())) {
            throw new RuntimeException("Chercheur existe déjà avec le CIN: " + chercheurDTO.getCin());
        }

        Chercheur chercheur = new Chercheur();
        chercheur.setCin(chercheurDTO.getCin());
        chercheur.setNom(chercheurDTO.getNom());
        chercheur.setPrenom(chercheurDTO.getPrenom());
        chercheur.setAdresse(chercheurDTO.getAdresse());

        // Ajouter les domaines
        if (chercheurDTO.getDomaineIds() != null) {
            for (Long domaineId : chercheurDTO.getDomaineIds()) {
                Domaine domaine = domaineRepository.findById(domaineId)
                        .orElseThrow(() -> new RuntimeException("Domaine non trouvé avec l'ID: " + domaineId));
                chercheur.addDomaine(domaine);
            }
        }

        return chercheurRepository.save(chercheur);
    }

    public List<Chercheur> getAllChercheurs() {
        return chercheurRepository.findAll();
    }

    public Chercheur getChercheurById(Long id) {
        return chercheurRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chercheur non trouvé avec l'ID: " + id));
    }

    public Chercheur getChercheurByCin(String cin) {
        return chercheurRepository.findByCin(cin)
                .orElseThrow(() -> new RuntimeException("Chercheur non trouvé avec le CIN: " + cin));
    }

    public List<Chercheur> getChercheursByDomaine(Long domaineId) {
        return chercheurRepository.findChercheursByDomaineId(domaineId);
    }

    @Transactional
    public Chercheur updateChercheur(Long id, ChercheurDTO chercheurDTO) {
        Chercheur chercheur = getChercheurById(id);

        chercheur.setNom(chercheurDTO.getNom());
        chercheur.setPrenom(chercheurDTO.getPrenom());
        chercheur.setAdresse(chercheurDTO.getAdresse());

        // Mettre à jour les domaines
        if (chercheurDTO.getDomaineIds() != null) {
            // Clear existing domaines
            chercheur.getDomaines().clear();

            // Add new domaines
            for (Long domaineId : chercheurDTO.getDomaineIds()) {
                Domaine domaine = domaineRepository.findById(domaineId)
                        .orElseThrow(() -> new RuntimeException("Domaine non trouvé avec l'ID: " + domaineId));
                chercheur.addDomaine(domaine);
            }
        }

        return chercheurRepository.save(chercheur);
    }

    @Transactional
    public void deleteChercheur(Long id) {
        Chercheur chercheur = getChercheurById(id);

        // Vérifier si le chercheur a des publications
        if (!chercheur.getPublications().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer ce chercheur car il a des publications associées");
        }

        chercheurRepository.delete(chercheur);
    }

    public List<Chercheur> getChercheursWithPublications() {
        return chercheurRepository.findChercheursWithPublications();
    }
}