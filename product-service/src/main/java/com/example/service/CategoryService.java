package com.example.service;

import com.example.DTO.responseDTO.CategoryDTO;
import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.CategoryRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> listAllCategories() {
        return categoryRepository.findAll().stream().map(CategoryDTO::new).collect(Collectors.toList());
    }

    public List<Product> getProductsByCategoryId(String id) {
        return categoryRepository.findById(id)
                .map(Category::getProducts)
                .orElse(Collections.emptyList());
    }

    public Category createCategory(@Valid Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

    public Optional<Category> findById(String id) {
        return categoryRepository.findById(id);
    }
}
