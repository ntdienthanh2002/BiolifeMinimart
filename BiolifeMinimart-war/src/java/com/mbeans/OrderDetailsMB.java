/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.OrderDetails;
import com.entity.Orders;
import com.sessionbean.OrderDetailsFacadeLocal;
import com.sessionbean.OrdersFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author OS
 */
@Named(value = "orderDetailsMB")
@SessionScoped
public class OrderDetailsMB implements Serializable {

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;
    
    private int idOrder;

    public OrderDetailsMB() {
    }

    public String showOrderDetailsPage(int id) {
        Orders orders = ordersFacade.find(id);
        idOrder = orders.getOrderID();
        return "orderdetails_all";
    }

    public List<OrderDetails> showOrderDetailsByOrder() {
        Orders orders = ordersFacade.find(idOrder);
        return orderDetailsFacade.findOrderDetailsByOrderID(orders);
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

}
