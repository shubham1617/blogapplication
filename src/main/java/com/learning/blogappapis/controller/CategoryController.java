package com.learning.blogappapis.controller;

import com.learning.blogappapis.payloads.ApiResponse;
import com.learning.blogappapis.payloads.CategoryDTO;
import com.learning.blogappapis.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO)
    {
        CategoryDTO catetory = this.categoryService.createCatetory(categoryDTO);
        return new ResponseEntity<>(categoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable int id)
    {
        CategoryDTO catetory = this.categoryService.updateCatetory(categoryDTO,id);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getSingleCategory(@PathVariable int id)
    {
        CategoryDTO singleCategory = categoryService.getSingleCategory(id);
        return new ResponseEntity<>(singleCategory,HttpStatus.FOUND);
    }

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategory()
    {
        List<CategoryDTO> allCategories = this.categoryService.getAllCategories();
        return new ResponseEntity<>(allCategories,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategoryById(@PathVariable int id)
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.categoryService.deleteCatetory(id);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Category Deleted Successfully",true,localDateTime),HttpStatus.OK);
    }

}
