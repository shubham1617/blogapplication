package com.learning.blogappapis.service;

import com.learning.blogappapis.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService
{

    public CategoryDTO createCatetory(CategoryDTO categoryDTO);
    public CategoryDTO updateCatetory(CategoryDTO categoryDTO, int id);
    public CategoryDTO getSingleCategory(int id);
    public List<CategoryDTO> getAllCategories();
    public void deleteCatetory(int id);
}
