package com.groupeAziz.demo.repository;

import com.groupeAziz.demo.entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByCin(String cin);
    boolean existsByCin(String cin);
    boolean existsById(Long id);
    void deleteByCin(String cin);
}
