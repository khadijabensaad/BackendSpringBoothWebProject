package com.groupeAziz.demo.repository;

import com.groupeAziz.demo.entity.Publication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication, Long> {

    List<Publication> findByChercheurId(Long chercheurId);

    @Query("SELECT p FROM Publication p WHERE p.date BETWEEN :startDate AND :endDate")
    List<Publication> findPublicationsByDateRange(@Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate);

    @Query("SELECT p FROM Publication p ORDER BY p.date DESC")
    List<Publication> findRecentPublications();

    @Query("SELECT p FROM Publication p JOIN p.consultations c WHERE c.id = :utilisateurId")
    List<Publication> findPublicationsConsulteesByUtilisateur(@Param("utilisateurId") Long utilisateurId);

    @Query("SELECT COUNT(c) FROM Publication p JOIN p.consultations c WHERE p.id = :publicationId")
    Long countConsultationsByPublicationId(@Param("publicationId") Long publicationId);
}