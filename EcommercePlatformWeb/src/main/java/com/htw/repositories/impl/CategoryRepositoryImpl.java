/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.repositories.impl;


import com.htw.pojo.Category;
import com.htw.repositories.CategoryRepository;
import jakarta.persistence.Query;
import java.util.List;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author nguye
 */
@Repository
@Transactional
public class CategoryRepositoryImpl implements CategoryRepository{
    @Autowired
    private LocalSessionFactoryBean factory;


    @Override
    public List<Category> getCates(){
       Session s = this.factory.getObject().getCurrentSession();
        Query q = s.createQuery("FROM Category", Category.class);
        return q.getResultList();
         
        
    }

    @Override
    public Category getCategoryById(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        return s.get(Category.class, id);
    }
    
    @Override
    public Category addOrUpdateCategory(Category category) {
        Session s = this.factory.getObject().getCurrentSession();
        if (category.getId() == null) {
            s.persist(category);
        } else {
            s.merge(category);
        }
        return category;
    }
    
    @Override
    public void deleteCategory(int id) {
        Session s = this.factory.getObject().getCurrentSession();
        Category category = s.get(Category.class, id);
        if (category != null) {
            s.remove(category);
        }
    }
}
