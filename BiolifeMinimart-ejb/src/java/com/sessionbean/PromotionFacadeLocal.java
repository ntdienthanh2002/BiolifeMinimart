/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Promotion;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface PromotionFacadeLocal {

    void create(Promotion promotion);

    void edit(Promotion promotion);

    void remove(Promotion promotion);

    Promotion find(Object id);

    List<Promotion> findAll();

    List<Promotion> findRange(int[] range);

    int count();
    
    public List<Object[]> getTodayPromotions();
    
    
}
