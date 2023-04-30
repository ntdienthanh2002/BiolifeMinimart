/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Supplier;
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
public class SupplierFacade extends AbstractFacade<Supplier> implements SupplierFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SupplierFacade() {
        super(Supplier.class);
    }
    
    @Override
    public List<Supplier> findEnableSuppliers() {
        Query query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierStatus = :supplierStatus");
        query.setParameter("supplierStatus", 1);
        return query.getResultList();
    }
    
     @Override
    public void updateProductStatusBySupplier(Supplier id, int status) {
        Query query = em.createQuery("UPDATE Product p SET p.productStatus = :proStatus WHERE p.supplierID = :suppID");
        query.setParameter("proStatus", status);
        query.setParameter("suppID", id);
        query.executeUpdate();
    }

    @Override
    public boolean checkProductBySupplier(Supplier id) {
        Query query = em.createQuery("SELECT p FROM Product p WHERE p.supplierID = :suppID");
        query.setParameter("suppID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkSupplierExist(String name) {
        Query query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierName = :suppName");
        query.setParameter("suppName", name);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkUpdateSupplierExist(String name, int id) {
        Query query = em.createQuery("SELECT s FROM Supplier s WHERE s.supplierName = :suppName AND s.supplierID != :suppID");
        query.setParameter("suppName", name);
        query.setParameter("suppID", id);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
}
