/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Feedback;
import com.entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author OS
 */
@Stateless
public class FeedbackFacade extends AbstractFacade<Feedback> implements FeedbackFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FeedbackFacade() {
        super(Feedback.class);
    }

    @Override
    public List<Feedback> findFeedbackByProduct(Product id) {
        Query query = em.createQuery("SELECT f FROM Feedback f WHERE f.productID = :proID AND f.feedbackStatus = 1 ORDER BY f.feedbackID DESC");
        query.setParameter("proID", id);
        return query.getResultList();
    }

    @Override
    public Long countFeedbackByProduct(Product id) {
        Query query = em.createQuery("SELECT COUNT (f.feedbackID) FROM Feedback f WHERE f.productID = :proID AND f.feedbackStatus = 1");
        query.setParameter("proID", id);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long sumFeedbackRateByProduct(Product id) {
        Query query = em.createQuery("SELECT SUM (f.feedbackRate) FROM Feedback f WHERE f.productID = :proID AND f.feedbackStatus = 1");
        query.setParameter("proID", id);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long countFeedbackRateByProduct(Product id, int rate) {
        Query query = em.createQuery("SELECT COUNT (f.feedbackID) FROM Feedback f WHERE f.productID = :proID AND f.feedbackStatus = 1 AND f.feedbackRate = :rate");
        query.setParameter("proID", id);
        query.setParameter("rate", rate);
        return (Long) query.getSingleResult();
    }

    @Override
    public boolean checkCustomerBuyProduct(String username, int id) {
        Query query = em.createQuery("SELECT c.customerFullname FROM OrderDetails od JOIN od.orderID o JOIN o.receiverID r JOIN r.customerUsername c JOIN od.productID p WHERE c.customerUsername = :uname AND p.productID = :proID");
        query.setParameter("uname", username);
        query.setParameter("proID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkCustomerToDeleteFeedback(Customer username, int id) {
        Query query = em.createQuery("SELECT f FROM Feedback f WHERE f.customerUsername = :uname AND f.feedbackID = :feedID");
        query.setParameter("uname", username);
        query.setParameter("feedID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<Feedback> showAllFeedbackByCustomer(String username) {
        List<Feedback> listFeedback = new ArrayList<Feedback>();
        try {
            Query query = em.createQuery("SELECT f FROM Feedback f WHERE f.customerUsername.customerUsername= ?1 ORDER BY f.feedbackID DESC ", Feedback.class);
            query.setParameter(1, username);
            listFeedback = query.getResultList();
        } catch (Exception e) {
        }
        return listFeedback;
    }
}
