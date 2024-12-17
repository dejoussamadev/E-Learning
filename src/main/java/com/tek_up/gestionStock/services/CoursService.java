package com.tek_up.gestionStock.services;

import com.tek_up.gestionStock.dao.CoursRepository;
import com.tek_up.gestionStock.entities.Category;
import com.tek_up.gestionStock.entities.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {
    @Autowired
    CoursRepository coursRepository;

    public List<Cours> getAllCourses() {
        return coursRepository.findAll();
    }

    public Cours getCoursById(Long id) {
        return coursRepository.getReferenceById(id);
    }

    public List<Cours> getCoursesByKeyword(String keyword) {
        return coursRepository.getCoursesByKeyword(keyword);
    }

    public void deleteCours(Long id) {
        coursRepository.deleteById(id);
    }

    public Cours createCategory(Cours cours) {
        return coursRepository.save(cours);
    }

    public Cours updateCours(Cours cours, Long id) {
        if(!coursRepository.existsById(id)) {
            throw new IllegalArgumentException("Cours id not found");
        }
        Cours coursToUpdate = coursRepository.getReferenceById(id);

        coursToUpdate.setName(cours.getName());
        coursToUpdate.setDescription(cours.getDescription());
        coursToUpdate.setCategory(cours.getCategory());

        return coursRepository.save(coursToUpdate);
    }
}
