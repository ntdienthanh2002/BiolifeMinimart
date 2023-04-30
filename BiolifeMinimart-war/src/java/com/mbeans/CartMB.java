/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author OS
 */
@Named(value = "cartMB")
@SessionScoped
public class CartMB implements Serializable {

    /**
     * Creates a new instance of CartMB
     */
    public CartMB() {
    }
    
}
