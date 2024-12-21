package com.tek_up.elearning.dao;

import com.tek_up.elearning.entities.Cours;
import com.tek_up.elearning.entities.User;
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

    List<Cours> findByOwner(User owner);
}
