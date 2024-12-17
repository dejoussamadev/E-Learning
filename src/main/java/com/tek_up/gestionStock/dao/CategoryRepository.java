package com.tek_up.gestionStock.dao;

import com.tek_up.gestionStock.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE LOWER(c.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Category> getCategoriesByName(String keyword);
}
