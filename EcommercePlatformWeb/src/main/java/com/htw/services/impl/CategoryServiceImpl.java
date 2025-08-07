/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.services.impl;

import com.htw.pojo.Category;
import com.htw.repositories.CategoryRepository;
import com.htw.services.CategoryService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nguye
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository cateRepo;

    @Override
    public List<Category> getCates() {
        return this.cateRepo.getCates();
    }

    @Override
    public Category getCategoryById(int id) {
        return this.cateRepo.getCategoryById(id);
    }

    @Override
    public Category addOrUpdateCategory(Category category) {
        return this.cateRepo.addOrUpdateCategory(category);
    }

    @Override
    public void deleteCategory(int id) {
        this.cateRepo.deleteCategory(id);
    }
}
