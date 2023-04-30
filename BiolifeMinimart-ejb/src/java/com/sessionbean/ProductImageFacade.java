/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Product;
import com.entity.ProductImage;
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
public class ProductImageFacade extends AbstractFacade<ProductImage> implements ProductImageFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProductImageFacade() {
        super(ProductImage.class);
    }
    
    @Override
    public List<ProductImage> findProductImageByProductID(Product id) {
        Query query = em.createQuery("SELECT pi FROM ProductImage pi WHERE pi.productID = :proID");
        query.setParameter("proID", id);
        return query.getResultList();
    }
    
}
