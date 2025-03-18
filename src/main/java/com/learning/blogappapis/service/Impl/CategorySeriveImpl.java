package com.learning.blogappapis.service.Impl;

import com.learning.blogappapis.exceptions.ResourceNotFoundException;
import com.learning.blogappapis.model.Category;
import com.learning.blogappapis.model.User;
import com.learning.blogappapis.payloads.CategoryDTO;
import com.learning.blogappapis.payloads.UserDTO;
import com.learning.blogappapis.repository.CategoryRepo;
import com.learning.blogappapis.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategorySeriveImpl implements CategoryService {

    @Autowired private CategoryRepo categoryRepo;
    @Autowired private ModelMapper modelMapper;

    @Override
    public CategoryDTO createCatetory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        this.categoryRepo.save(category);
        return modelMapper.map(category, CategoryDTO.class);

    }

    @Override
    public CategoryDTO updateCatetory(CategoryDTO categoryDTO, int id) {
        Category existingCategory = categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryID", "Category", id));
        existingCategory.setCategoryTitle(categoryDTO.getCategoryTitle());
        existingCategory.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category savedCategory = this.categoryRepo.save(existingCategory);
        this.categoryRepo.save(savedCategory);
        CategoryDTO savedCategoryDTO = modelMapper.map(savedCategory, CategoryDTO.class);
        return savedCategoryDTO;
    }

    @Override
    public CategoryDTO getSingleCategory(int id) {
        Category singleCategory = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryID", "Category", id));
        CategoryDTO convertFromCategoryToDTO = modelMapper.map(singleCategory, CategoryDTO.class);
        return convertFromCategoryToDTO;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> allResult = this.categoryRepo.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        allResult.forEach(category -> categoryDTOList.add(modelMapper.map(category, CategoryDTO.class)) );
        return categoryDTOList;
    }

    @Override
    public void deleteCatetory(int id) {
        Category singleCategory = this.categoryRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("CategoryID", "Category", id));
        this.categoryRepo.delete(singleCategory);
    }


}
