package com.tek_up.elearning.RestController;


import com.tek_up.elearning.entities.Category;
import com.tek_up.elearning.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("api/category")
public class CategoryRestController {
    @Autowired
    CategoryService categoryService;


    // ADMIN
    @PostMapping("admin/createCategory")
    public Category createCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }
    @DeleteMapping("admin/deleteCategory")
    public void deleteCategory(Long id){
        categoryService.deleteCategory(id);
    }

    @PutMapping("admin/updateCategory")
    public Category updateCategory(@RequestBody Category category,@RequestParam Long id){
        return categoryService.updateCategory(category, id);
    }

    // TEACHER
    // --------------
    // STUDENT
    // --------------

    // User
    @GetMapping("getAllCategories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }

    @GetMapping("getCategoryById")
    public Category getCategoryById(Long id){
        return categoryService.getCategoryById(id);
    }
}
