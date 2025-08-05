/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.htw.services;

import com.htw.pojo.Category;
import java.util.List;

/**
 *
 * @author nguye
 */
public interface CategoryService {

    List<Category> getCates();


    Category getCategoryById(int id);
    Category addOrUpdateCategory(Category category);
    void deleteCategory(int id);

}
