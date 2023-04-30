/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Employee;
import com.entity.Employee2;
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
public class Employee2Facade extends AbstractFacade<Employee2> implements Employee2FacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public Employee2Facade() {
        super(Employee2.class);
    }

    @Override
    public List<Employee> findEmployee() {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeStatus > 0");
        return query.getResultList();
    }
    
    @Override
    public boolean checkUsernameExist(String username) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeUsername = :uname");
        query.setParameter("uname", username);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean checkPhoneExist(String phone) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeePhone = :emplPhone");
        query.setParameter("emplPhone", phone);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }
    
    @Override
    public boolean checkEmailExist(String email) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeEmail = :emplEmail");
        query.setParameter("emplEmail", email);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

}
