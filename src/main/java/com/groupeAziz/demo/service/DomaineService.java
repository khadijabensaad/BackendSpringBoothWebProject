package com.groupeAziz.demo.service;

import com.groupeAziz.demo.dto.DomaineDTO;
import com.groupeAziz.demo.entity.Domaine;
import com.groupeAziz.demo.repository.DomaineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomaineService {

    private final DomaineRepository domaineRepository;

    @Transactional
    public Domaine createDomaine(DomaineDTO domaineDTO) {
        if (domaineRepository.existsByNom(domaineDTO.getNom())) {
            throw new RuntimeException("Domaine existe déjà avec le nom: " + domaineDTO.getNom());
        }

        Domaine domaine = new Domaine();
        domaine.setNom(domaineDTO.getNom());

        return domaineRepository.save(domaine);
    }

    public List<Domaine> getAllDomaines() {
        return domaineRepository.findAll();
    }

    public Domaine getDomaineById(Long id) {
        return domaineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Domaine non trouvé avec l'ID: " + id));
    }

    @Transactional
    public Domaine updateDomaine(Long id, DomaineDTO domaineDTO) {
        Domaine domaine = getDomaineById(id);
        domaine.setNom(domaineDTO.getNom());
        return domaineRepository.save(domaine);
    }

    @Transactional
    public void deleteDomaine(Long id) {
        Domaine domaine = getDomaineById(id);
        // Vérifier si des chercheurs sont associés à ce domaine
        if (!domaine.getChercheurs().isEmpty()) {
            throw new RuntimeException("Impossible de supprimer ce domaine car il est associé à des chercheurs");
        }
        domaineRepository.delete(domaine);
    }
}
