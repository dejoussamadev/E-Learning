package com.tek_up.gestionStock.dao;

import com.tek_up.gestionStock.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    // Search For Courses By Keyword
    @Query("SELECT c FROM Cours c WHERE c.name LIKE %:keyword% OR c.description LIKE %:keyword%")
    List<Cours> getCoursesByKeyword(@Param("keyword") String keyword);
}
