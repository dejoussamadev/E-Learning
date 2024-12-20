package com.tek_up.gestionStock.RestController;

import com.tek_up.gestionStock.entities.Category;
import com.tek_up.gestionStock.entities.Cours;
import com.tek_up.gestionStock.services.CategoryService;
import com.tek_up.gestionStock.services.CoursService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/courses")
public class CoursRestController {

    @Autowired
    CoursService coursService;

    @GetMapping("getAllCategories")
    public List<Cours> getAllCategories(){
        return coursService.getAllCourses();
    }

    @GetMapping("getCoursById")
    public Cours getCategoryById(Long id){
        return coursService.getCoursById(id);
    }

    @DeleteMapping("deleteCours")
    public void deleteCategory(Long id){
        coursService.deleteCours(id);
    }

    @PostMapping("createCours")
    public Cours createCategory(@RequestBody Cours cours){
        return coursService.createCategory(cours);
    }

    @PutMapping("updateCours")
    public Cours updateCategory(@RequestBody Cours cours, Long id){
        return coursService.updateCours(cours, id);
    }
}
