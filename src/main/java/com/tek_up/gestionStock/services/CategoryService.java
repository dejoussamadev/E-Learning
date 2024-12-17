package com.tek_up.gestionStock.services;

import com.tek_up.gestionStock.dao.CategoryRepository;
import com.tek_up.gestionStock.entities.Category;
import com.tek_up.gestionStock.entities.Cours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.getReferenceById(id);
    }

    public List<Category> getCategoriesByName(String keyword) {
        return categoryRepository.getCategoriesByName(keyword);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category updateCategory(Category category, Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category id not found");
        }
        Category categoryToUpdate = categoryRepository.getReferenceById(id);

        categoryToUpdate.setName(category.getName());

        return categoryRepository.save(categoryToUpdate);
    }
}
