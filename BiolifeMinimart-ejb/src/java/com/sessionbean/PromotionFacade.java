/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Promotion;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author PC
 */
@Stateless
public class PromotionFacade extends AbstractFacade<Promotion> implements PromotionFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PromotionFacade() {
        super(Promotion.class);
    }
    
    @Override
    public List<Object[]> getTodayPromotions() {
//        List<Object[]> list = new ArrayList<>();
//        Query query = em.createNativeQuery("SELECT pd.ProductID, MAX(pd.PromotionDetailsDiscount) AS PromotionDetailsDiscount FROM PromotionDetails pd JOIN Promotion p ON pd.PromotionID = p.PromotionID GROUP BY pd.ProductID")
        Query query = em.createQuery("SELECT pd.productID, MAX(pd.promotionDetailsDiscount) FROM PromotionDetails pd JOIN pd.promotionID p WHERE p.promotionStart <= :date1 AND p.promotionEnd > :date2 GROUP BY pd.productID");
        Date date = new Date();
        query.setParameter("date1", date, TemporalType.DATE);
        query.setParameter("date2", date, TemporalType.DATE);
//        list = query.getResultList();
        return query.getResultList();
    }
    
}
