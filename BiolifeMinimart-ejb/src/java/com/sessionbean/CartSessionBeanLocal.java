/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/SessionLocal.java to edit this template
 */
package com.sessionbean;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author PC
 */
@Local
public interface CartSessionBeanLocal {

    void addCart(int id, int quantity);

    LinkedHashMap<Integer, Integer> showCart();

    int countCart();

    void removeCart(int id);

    void emptyCart();
    
    void updateCart(int id, boolean flag, int max);
}
