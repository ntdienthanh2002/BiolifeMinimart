/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Product;
import com.entity.Promotion;
import com.entity.PromotionDetails;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Stateless
public class PromotionDetailsFacade extends AbstractFacade<PromotionDetails> implements PromotionDetailsFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PromotionDetailsFacade() {
        super(PromotionDetails.class);
    }

    @Override
    public List<PromotionDetails> getTodayDiscountProduct(Product p) {
        Query query = em.createQuery("SELECT pd FROM PromotionDetails pd JOIN pd.promotionID p WHERE pd.productID = :productID AND p.promotionStart <= :date1 AND p.promotionEnd > :date2 ORDER BY pd.promotionDetailsDiscount DESC");
        Date date = new Date();
        query.setParameter("productID", p);
        query.setParameter("date1", date, TemporalType.DATE);
        query.setParameter("date2", date, TemporalType.DATE);
//        try {
            return query.getResultList();
//        } catch (NoResultException ex) {
//            return null;
//        }
    }
    
    @Override
    public PromotionDetails findPromotionDetails(Product p, double discount) {
        Query query = em.createQuery("SELECT pd FROM PromotionDetails pd JOIN pd.promotionID p WHERE pd.productID = :productID AND p.promotionStart <= :date1 AND p.promotionEnd > :date2 AND pd.promotionDetailsDiscount = :discount");
        Date date = new Date();
        query.setParameter("productID", p);
        query.setParameter("date1", date, TemporalType.DATE);
        query.setParameter("date2", date, TemporalType.DATE);
        query.setParameter("discount", discount);
//        try {
            return (PromotionDetails) query.getSingleResult();
//        } catch (NoResultException ex) {
//            return null;
//        }
    }

    @Override
    public List<PromotionDetails> showshowPromotionDetailsByPromotion(Promotion promotionID) {
        Query query = em.createQuery("SELECT pd FROM PromotionDetails pd WHERE pd.promotionID = :promotionID");
        query.setParameter("promotionID", promotionID);
        return query.getResultList();
    }

    @Override
    public List<Product> showNoPromotionProduct(List<Integer> listProduct) {
        Query query;
        System.out.println("Size product: " + listProduct.size());
        System.out.println(listProduct.isEmpty());
        if (listProduct.isEmpty()) {
            query = em.createQuery("SELECT p FROM Product p");
        } else {
            query = em.createQuery("SELECT p FROM Product p WHERE p NOT IN :listProduct");
            query.setParameter("listProduct", listProduct);
        }
        return query.getResultList();
    }
}
