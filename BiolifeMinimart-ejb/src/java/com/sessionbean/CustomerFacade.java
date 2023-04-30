/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author OS
 */
@Stateless
public class CustomerFacade extends AbstractFacade<Customer> implements CustomerFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CustomerFacade() {
        super(Customer.class);
    }
    
    @Override
    public boolean checkExistUsername(String username) {
        boolean flag = false;
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.customerUsername= ?1", Customer.class);
            query.setParameter(1, username);
            if (!query.getResultList().isEmpty()) {
                flag = true;
            }
        } catch (Exception e) {

        }
        return flag;
    }

    @Override
    public boolean checkExistEmail(String email) {
        boolean flag = false;
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.customerEmail= ?1", Customer.class);
            query.setParameter(1, email);
            if (!query.getResultList().isEmpty()) {
                flag = true;
            }
        } catch (Exception e) {

        }
        return flag;
    }

    @Override
    public boolean checkExistPhone(String phone) {
        boolean flag = false;
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE c.customerPhone= ?1", Customer.class);
            query.setParameter(1, phone);
            if (!query.getResultList().isEmpty()) {
                flag = true;
            }
        } catch (Exception e) {

        }
        return flag;
    }

    //Duy Khanh end
    @Override
    public Customer login(String uname, String pword) {
        Customer customer = new Customer();
        try {
            Query query = em.createQuery("SELECT c FROM Customer c WHERE (c.customerPhone= ?1 OR c.customerEmail =?1 OR c.customerUsername =?1) AND c.customerPassword= ?2 AND c.customerStatus=1", Customer.class);
            query.setParameter(1, uname);
            query.setParameter(2, pword);
            customer = (Customer) query.getSingleResult();
        } catch (Exception e) {

        }
        return customer;
    }

    @Override
    public void createCustomer(Customer customer) {
        try {
            Query query = em.createQuery("INSERT INTO Customer c (c.CustomerUsername, c.CustomerFullname, c.CustomerGender, c.CustomerEmail, "
                    + "c.CustomerPassword, c.CustomerPhone, c.CustomerAvatar,c.CustomerStatus) VALUES"
                    + "(?1,?2,?3,?4,?5,?6,?7,?8,?9)", Customer.class);
            query.setParameter(1, customer.getCustomerUsername());
            query.setParameter(2, customer.getCustomerFullname());
            query.setParameter(3, customer.getCustomerGender());
            query.setParameter(4, customer.getCustomerEmail());
            query.setParameter(5, customer.getCustomerPassword());
            query.setParameter(6, customer.getCustomerPhone());
            query.setParameter(7, customer.getCustomerAvatar());
            query.setParameter(8, customer.getCustomerStatus());
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("LOIIIII:   " + e.getMessage());
        }
    }

    @Override
    public void editCustomer(Customer customer) {
        try {
            Query query = em.createQuery("UPDATE Customer c set c.customerAvatar= ?1, c.customerFullname= ?2, c.customerGender= ?3 WHERE c.customerUsername= ?4");
            query.setParameter(1, customer.getCustomerAvatar());
            query.setParameter(2, customer.getCustomerFullname());
            query.setParameter(3, customer.getCustomerGender());
            query.setParameter(4, customer.getCustomerUsername());

            query.executeUpdate();
        } catch (Exception e) {

        }
    }

    @Override
    public void changePassword(String username,String password) {
        try {
            Query query = em.createQuery("UPDATE Customer c set c.customerPassword =?1 WHERE c.customerUsername= ?2");
            query.setParameter(2, username);
            query.setParameter(1, password );
            
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("LOIIII: "+ e.getMessage());
        }
    }
    
    @Override
    public void forgot_pass(String email, String pword) {
    try {
            Query query = em.createQuery("UPDATE Customer c set c.customerPassword =?1 WHERE c.customerEmail= ?2");
            query.setParameter(2, email);
            query.setParameter(1, pword );            
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("LOIIII: "+ e.getMessage());
        }  
    }
    
}
