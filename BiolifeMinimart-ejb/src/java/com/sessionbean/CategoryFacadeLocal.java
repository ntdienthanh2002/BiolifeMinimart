/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Category;
import com.entity.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CategoryFacadeLocal {

    void create(Category category);

    void edit(Category category);

    void remove(Category category);

    Category find(Object id);

    List<Category> findAll();

    List<Category> findRange(int[] range);

    int count();
    
    List<Category> findEnableCategories();
    
    void updateProductStatusByCategory(Category id, int status);

    boolean checkProductByCategory(Category id);
    
    boolean checkCategoryExist(String name);
    
    boolean checkUpdateCategoryExist(String name, int id);
    
    int findFirstCategory();
    
}
