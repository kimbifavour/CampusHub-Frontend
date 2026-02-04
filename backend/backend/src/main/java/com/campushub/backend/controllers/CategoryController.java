package com.campushub.backend.controllers;

import com.campushub.backend.dtos.category.CategoryRequestDTO;
import com.campushub.backend.dtos.category.CategoryResponseDTO;
import com.campushub.backend.models.listings.Category;
import com.campushub.backend.services.listings.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.togglz.core.manager.FeatureManager;

import java.util.List;
import java.util.UUID;

import static com.campushub.backend.configurations.togglz.Features.*;


@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FeatureManager featureManager;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        if (!featureManager.isActive(CREATE_CATEGORY)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        Category createdCategory = categoryService.createCategory(category);
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(createdCategory, CategoryResponseDTO.class);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-category-by-id/{categoryId}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable UUID categoryId) {
        if (!featureManager.isActive(DELETE_CATEGORY_BY_ID)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category deletedCategory = categoryService.deleteCategoryById(categoryId);

        CategoryResponseDTO categoryResponseDTO = modelMapper.map(deletedCategory, CategoryResponseDTO.class);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete-category-by-name/{categoryName}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable String categoryName) {
        if (!featureManager.isActive(DELETE_CATEGORY_BY_NAME)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        Category deletedCategory = categoryService.deleteCategoryByName(categoryName);

        CategoryResponseDTO categoryResponseDTO = modelMapper.map(deletedCategory, CategoryResponseDTO.class);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/get-all-categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        if (!featureManager.isActive(GET_ALL_CATEGORIES)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryResponseDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

}
