/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.OrderDetails;
import com.entity.Orders;
import java.util.ArrayList;
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
public class OrderDetailsFacade extends AbstractFacade<OrderDetails> implements OrderDetailsFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrderDetailsFacade() {
        super(OrderDetails.class);
    }

    @Override
    public List<OrderDetails> findDetailsByOrderID(int id) {
        List<OrderDetails> listOdd= new ArrayList<OrderDetails>();
        try{
            Query query= em.createQuery("SELECT od FROM OrderDetails od WHERE od.orderID.orderID= ?1",OrderDetails.class);
            query.setParameter(1, id);
            listOdd= query.getResultList();
        }catch(Exception e){}
        return listOdd;
    }
    
    @Override
    public List<OrderDetails> findOrderDetailsByOrderID(Orders id) {
        Query query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.orderID = :ordID");
        query.setParameter("ordID", id);
        return query.getResultList();
    }
    
    @Override
    public List<OrderDetails> showOrderDetailsByOrder(Orders order) {
        Query query = em.createQuery("SELECT od FROM OrderDetails od WHERE od.orderID = :orderID");
        query.setParameter("orderID", order);
        return query.getResultList();
    }
    
}
