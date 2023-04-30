/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/J2EE/EJB30/StatefulEjbClass.java to edit this template
 */
package com.sessionbean;

import java.util.LinkedHashMap;
import javax.ejb.Stateful;

/**
 *
 * @author PC
 */
@Stateful
public class CartSessionBean implements CartSessionBeanLocal {

    LinkedHashMap<Integer, Integer> myCart = new LinkedHashMap<>();

    @Override
    public void addCart(int id, int quantity) {
        if (myCart.containsKey(id)) {
            myCart.replace(id, myCart.get(id) + quantity);
        } else {
            myCart.put(id, quantity);
        }
    }

    @Override
    public LinkedHashMap<Integer, Integer> showCart() {
        return myCart;
    }

    @Override
    public int countCart() {
        return myCart.size();
    }

    @Override
    public void removeCart(int id) {
        myCart.remove(id);
    }

    @Override
    public void emptyCart() {
        myCart.clear();
    }

    @Override
    public void updateCart(int id, boolean flag, int max) {
        if (flag) {
            if (myCart.get(id) < max) {
                myCart.replace(id, myCart.get(id) + 1);
            }
        } else {
            if (myCart.get(id) > 1) {
                myCart.replace(id, myCart.get(id) - 1);
            }
        }
    }
}
