package com.tek_up.gestionStock.RestController;


import com.tek_up.gestionStock.entities.Category;
import com.tek_up.gestionStock.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/category")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("getAllCategories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("getCategoryById")
    public Category getCategoryById(Long id){
        return categoryService.getCategoryById(id);
    }

    @DeleteMapping("deleteCategory")
    public void deleteCategory(Long id){
        categoryService.deleteCategory(id);
    }

    @PostMapping("createCategory")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }

    @PutMapping("updateCategory")
    public Category updateCategory(@RequestBody Category category, Long id){
        return categoryService.updateCategory(category, id);
    }
}
