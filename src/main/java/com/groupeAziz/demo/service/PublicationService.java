package com.groupeAziz.demo.service;

import com.groupeAziz.demo.dto.PublicationDTO;
import com.groupeAziz.demo.entity.Chercheur;
import com.groupeAziz.demo.entity.Publication;
import com.groupeAziz.demo.entity.Utilisateur;
import com.groupeAziz.demo.repository.ChercheurRepository;
import com.groupeAziz.demo.repository.PublicationRepository;
import com.groupeAziz.demo.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicationService {

    private final PublicationRepository publicationRepository;
    private final ChercheurRepository chercheurRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Transactional
    public Publication createPublication(PublicationDTO publicationDTO) {
        Chercheur chercheur = chercheurRepository.findById(publicationDTO.getChercheurId())
                .orElseThrow(() -> new RuntimeException("Chercheur non trouvé avec l'ID: " + publicationDTO.getChercheurId()));

        Publication publication = new Publication();
        publication.setTitre(publicationDTO.getTitre());
        publication.setDescription(publicationDTO.getDescription());
        publication.setDate(LocalDateTime.now());
        publication.setChercheur(chercheur);

        return publicationRepository.save(publication);
    }

    public List<Publication> getAllPublications() {
        return publicationRepository.findAll();
    }

    public Publication getPublicationById(Long id) {
        return publicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Publication non trouvée avec l'ID: " + id));
    }

    public List<Publication> getPublicationsByChercheur(Long chercheurId) {
        return publicationRepository.findByChercheurId(chercheurId);
    }

    @Transactional
    public Publication updatePublication(Long id, PublicationDTO publicationDTO) {
        Publication publication = getPublicationById(id);

        publication.setTitre(publicationDTO.getTitre());
        publication.setDescription(publicationDTO.getDescription());

        // Si le chercheur change
        if (publicationDTO.getChercheurId() != null &&
                !publicationDTO.getChercheurId().equals(publication.getChercheur().getId())) {
            Chercheur chercheur = chercheurRepository.findById(publicationDTO.getChercheurId())
                    .orElseThrow(() -> new RuntimeException("Chercheur non trouvé avec l'ID: " + publicationDTO.getChercheurId()));
            publication.setChercheur(chercheur);
        }

        return publicationRepository.save(publication);
    }

    @Transactional
    public void deletePublication(Long id) {
        Publication publication = getPublicationById(id);
        publicationRepository.delete(publication);
    }

    @Transactional
    public Publication consulterPublication(Long publicationId, Long utilisateurId) {
        Publication publication = getPublicationById(publicationId);
        Utilisateur utilisateur = utilisateurRepository.findById(utilisateurId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé avec l'ID: " + utilisateurId));

        // Ajouter la consultation si elle n'existe pas déjà
        if (!publication.getConsultations().contains(utilisateur)) {
            publication.addConsultation(utilisateur);
            publication = publicationRepository.save(publication);
        }

        return publication;
    }

    public Long getConsultationCount(Long publicationId) {
        return publicationRepository.countConsultationsByPublicationId(publicationId);
    }

    public List<Publication> getPublicationsConsulteesByUtilisateur(Long utilisateurId) {
        return publicationRepository.findPublicationsConsulteesByUtilisateur(utilisateurId);
    }

    public List<Publication> getRecentPublications() {
        return publicationRepository.findRecentPublications();
    }
}