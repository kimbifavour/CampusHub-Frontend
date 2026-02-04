package com.campushub.backend.controllers;

import com.campushub.backend.dtos.category.CategoryRequestDTO;
import com.campushub.backend.dtos.category.CategoryResponseDTO;
import com.campushub.backend.models.listings.Category;
import com.campushub.backend.services.listings.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/category")
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
}
