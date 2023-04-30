/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sessionbean;

import com.entity.Employee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PC
 */
@Stateless
public class EmployeeFacade extends AbstractFacade<Employee> implements EmployeeFacadeLocal {

    @PersistenceContext(unitName = "BiolifeMinimart-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public EmployeeFacade() {
        super(Employee.class);
    }

    @Override
    public Employee login(String uname, String pword) {
        Employee employee = new Employee();
        try {
            Query query = em.createQuery("SELECT e FROM Employee e WHERE (e.employeePhone= ?1 OR e.employeeEmail =?1 OR e.employeeUsername =?1) AND e.employeePassword= ?2", Employee.class);
            query.setParameter(1, uname);
            query.setParameter(2, pword);
            employee = (Employee) query.getSingleResult();
        } catch (Exception e) {
        }
        return employee;
    }
    
    @Override
    public boolean checkEmailExist(String email) {
        Query query = em.createQuery("SELECT e FROM Employee e WHERE e.employeeEmail= ?1");
        query.setParameter(1, email);
        if (query.getResultList().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void forgot_pass(String email, String pword) {
    try {
            Query query = em.createQuery("UPDATE Employee c set c.employeePassword =?1 WHERE c.employeeEmail= ?2");
            query.setParameter(2, email);
            query.setParameter(1, pword );            
            query.executeUpdate();
        } catch (Exception e) {
        }  
    }

}
