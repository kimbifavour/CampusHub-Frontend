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

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/create-category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        Category category = modelMapper.map(categoryRequestDTO, Category.class);
        Category createdCategory = categoryService.createCategory(category);
        CategoryResponseDTO categoryResponseDTO = modelMapper.map(createdCategory, CategoryResponseDTO.class);
        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete-category/{id}")
    public ResponseEntity<CategoryResponseDTO> deleteCategory(@PathVariable UUID id) {
        Category deletedCategory = categoryService.deleteCategoryById(id);

        CategoryResponseDTO categoryResponseDTO = modelMapper.map(deletedCategory, CategoryResponseDTO.class);

        return new ResponseEntity<>(categoryResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        List<CategoryResponseDTO> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategoryResponseDTO.class))
                .toList();
        return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
    }

}
