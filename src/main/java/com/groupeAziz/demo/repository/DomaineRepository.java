package com.groupeAziz.demo.repository;

import com.groupeAziz.demo.entity.Domaine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DomaineRepository extends JpaRepository<Domaine, Long> {
    Optional<Domaine> findByNom(String nom);
    boolean existsByNom(String nom);
}
