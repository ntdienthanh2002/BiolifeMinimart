/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Orders;
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
 * @author OS
 */
@Stateless
public class OrdersFacade extends AbstractFacade<Orders> implements OrdersFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OrdersFacade() {
        super(Orders.class);
    }
    
    @Override
    public List<Orders> findLastOrder(Customer customer) {
        Query query = em.createQuery("SELECT o FROM Orders o JOIN o.receiverID r WHERE r.customerUsername = :customer");
        query.setParameter("customer", customer);
        return query.getResultList();
    }
    
    @Override
    public Integer countMonthOrder(Date date1, Date date2) {
        Query query = em.createQuery("SELECT COUNT(o.orderID) FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);

        return (int) (long) query.getSingleResult();
    }
    
    @Override
    public List<Orders> getOrdersByMonth(Date date1, Date date2) {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);
        
        return (List<Orders>) query.getResultList();
    } 
    
    @Override
    public List<Orders> getReturnOrdersByMonth(Date date1, Date date2) {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2 AND o.orderStatus = :status");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);
        query.setParameter("status", 5);
        
        return (List<Orders>) query.getResultList();
    } 

    @Override
    public Long countOrder(Date date) {
        Query query = em.createQuery("SELECT COUNT(o.orderID) FROM Orders o WHERE o.orderDate >= :orderDate AND o.orderDate < :nextDate");
        query.setParameter("orderDate", date, TemporalType.DATE);
        query.setParameter("nextDate", new Date(date.getTime() + (1000 * 60 * 60 * 24)), TemporalType.DATE);

        return (Long) query.getSingleResult();
    }
    
    @Override
    public List<Orders> getOrdersByDate(Date date1, Date date2) {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.orderDate >= :date1 AND o.orderDate < :date2");
        query.setParameter("date1", date1, TemporalType.DATE);
        query.setParameter("date2", date2, TemporalType.DATE);
        
        return (List<Orders>) query.getResultList();
    } 
    
    @Override
    public List<Orders> showOrder(String uname) {
        List<Orders> listOrder = new ArrayList<Orders>();
        try{
            Query query = em.createQuery("SELECT o FROM Orders o WHERE o.receiverID.customerUsername.customerUsername=?1", Orders.class);
            query.setParameter(1, uname);
            listOrder= query.getResultList();
        }catch(Exception e){
            System.out.println("Loi: "+ e);
            System.out.println("Mess: "+ e.getMessage());
            System.out.println("stack: "+ e.getStackTrace());
        }
        return listOrder;
    }
    
     @Override
    public int countOrderByDeliveryStatus(int status) {
        Query query = em.createQuery("SELECT COUNT(o.orderID) FROM Orders o WHERE o.orderStatus= ?1");
        query.setParameter(1, status);
        return Integer.parseInt(query.getSingleResult() + "");
    }

    @Override
    public int countDeliveryStatusByYear(int status, int year) {    
        List<Orders> listOrder= findAll();
        int count=0;
        for(Orders o:listOrder){
            if(o.getOrderStatus()==status && o.getOrderDate().getYear()+1900==year){
                count++;
            }
        }
        
        System.out.println("Result: "+ count);
        
        return count;
    }

    @Override
    public int countDeliveryStatusByMonth(int status, int year, int month) {
       List<Orders> listOrder= findAll();
        int count=0;
        for(Orders o:listOrder){
            if(o.getOrderStatus()==status && o.getOrderDate().getYear()+1900==year && o.getOrderDate().getMonth()+1== month){
                count++;
            }
        }
        
        System.out.println("Result: "+ count);
        
        return count;
    }

    @Override
    public List<Orders> findOrdersByStatus() {
        Query query = em.createQuery("SELECT o FROM Orders o WHERE o.orderStatus = 3");
        return query.getResultList();
    }    

}
