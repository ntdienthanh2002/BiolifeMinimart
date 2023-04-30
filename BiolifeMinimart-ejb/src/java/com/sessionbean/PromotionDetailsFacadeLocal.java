/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Product;
import com.entity.Promotion;
import com.entity.PromotionDetails;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface PromotionDetailsFacadeLocal {

    void create(PromotionDetails promotionDetails);

    void edit(PromotionDetails promotionDetails);

    void remove(PromotionDetails promotionDetails);

    PromotionDetails find(Object id);

    List<PromotionDetails> findAll();

    List<PromotionDetails> findRange(int[] range);

    int count();
    
    public List<PromotionDetails> getTodayDiscountProduct(Product p);
    
    PromotionDetails findPromotionDetails(Product p, double discount);
    
    List<PromotionDetails> showshowPromotionDetailsByPromotion(Promotion promotionID);
    
    List<Product> showNoPromotionProduct(List<Integer> listProduct);
    
}
