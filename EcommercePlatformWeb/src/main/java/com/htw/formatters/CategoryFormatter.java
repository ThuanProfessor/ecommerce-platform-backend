/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.htw.formatters;

import com.htw.pojo.Category;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author nguye
 */
public class CategoryFormatter implements Formatter<Category>{

    @Override
    public String print(Category c, Locale locale) {
        return String.valueOf(c.getId());
    }

    @Override
    public Category parse(String cateId, Locale locale) throws ParseException {
        Category c = new Category(Integer.valueOf(cateId));
        
        return c;
    }  
}
