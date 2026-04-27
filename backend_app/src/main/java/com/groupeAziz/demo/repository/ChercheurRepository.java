package com.groupeAziz.demo.repository;

import com.groupeAziz.demo.entity.Chercheur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChercheurRepository extends JpaRepository<Chercheur, Long> {
    Optional<Chercheur> findByCin(String cin);
    boolean existsByCin(String cin);

    @Query("SELECT c FROM Chercheur c JOIN c.domaines d WHERE d.id = :domaineId")
    List<Chercheur> findChercheursByDomaineId(@Param("domaineId") Long domaineId);

    @Query("SELECT c FROM Chercheur c WHERE SIZE(c.publications) > 0")
    List<Chercheur> findChercheursWithPublications();
}
