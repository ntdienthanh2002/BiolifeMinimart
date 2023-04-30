/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Category;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
@Stateless
public class CategoryFacade extends AbstractFacade<Category> implements CategoryFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CategoryFacade() {
        super(Category.class);
    }
    
    @Override
    public List<Category> findEnableCategories() {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.categoryStatus = :categoryStatus");
        query.setParameter("categoryStatus", 1);
        return query.getResultList();
    }
    
     @Override
    public void updateProductStatusByCategory(Category id, int status) {
        Query query = em.createQuery("UPDATE Product p SET p.productStatus = :proStatus WHERE p.categoryID = :cateID");
        query.setParameter("proStatus", status);
        query.setParameter("cateID", id);
        query.executeUpdate();
    }

    @Override
    public boolean checkProductByCategory(Category id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.categoryID = :cateID");
        query.setParameter("cateID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkCategoryExist(String name) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :cateName");
        query.setParameter("cateName", name);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkUpdateCategoryExist(String name, int id) {
        Query query = em.createQuery("SELECT c FROM Category c WHERE c.categoryName = :cateName AND c.categoryID != :cateID");
        query.setParameter("cateName", name);
        query.setParameter("cateID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public int findFirstCategory() {
        Query query = em.createQuery("SELECT c.categoryID FROM Category c WHERE c.categoryStatus = 1");
        query.setMaxResults(1);
        return (Integer) query.getResultList().get(0);
    }
}
