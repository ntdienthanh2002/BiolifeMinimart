/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Receiver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author OS
 */
@Stateless
public class ReceiverFacade extends AbstractFacade<Receiver> implements ReceiverFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ReceiverFacade() {
        super(Receiver.class);
    }

    @Override
    public List<Receiver> showAllReceiver(String username) {
        List<Receiver> listReceiver = new ArrayList<Receiver>();
        try {
            Query query = em.createQuery("SELECT r FROM Receiver r WHERE r.customerUsername.customerUsername= ?1 AND r.receiverStatus > 0", Receiver.class);
            query.setParameter(1, username);
            listReceiver = query.getResultList();
        } catch (Exception e) {
        }
        return listReceiver;
    }

    @Override
    public void setDefault(int id) {
        Receiver receiver = find(id);
        try {
            Query query = em.createQuery("UPDATE Receiver r SET r.receiverStatus = 2 WHERE r.customerUsername.customerUsername =?1 AND r.receiverStatus =1");
            query.setParameter(1, receiver.getCustomerUsername().getCustomerUsername());
            query.executeUpdate();

            query = em.createQuery("UPDATE Receiver r SET r.receiverStatus = 1 WHERE r.receiverID = ?1");
            query.setParameter(1, id);
            query.executeUpdate();
        } catch (Exception e) {
        }
    }
    
    @Override
    public List<Receiver> showEnableReceiversByCustomer(Customer customer) {
        Query query = em.createQuery("SELECT r FROM Receiver r WHERE r.receiverStatus IN (:status1, :status2) AND r.customerUsername = :customer");
        query.setParameter("status1", 1);
        query.setParameter("status2", 2);
        query.setParameter("customer", customer);
        return query.getResultList();
    }
    
    @Override
    public Receiver showDefaultReceiver(Customer customer) {
        Query query = em.createQuery("SELECT r FROM Receiver r WHERE r.receiverStatus = :receiverStatus AND r.customerUsername = :customer");
        query.setParameter("receiverStatus", 1);
        query.setParameter("customer", customer);

        try {
            return (Receiver) query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public List<Receiver> findReceiverByCustomer(Customer username) {
        Query query = em.createQuery("SELECT r FROM Receiver r WHERE r.customerUsername = :uname");
        query.setParameter("uname", username);
        return query.getResultList();
    }

}
