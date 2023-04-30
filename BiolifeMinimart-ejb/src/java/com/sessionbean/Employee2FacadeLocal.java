/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Employee;
import com.entity.Employee2;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface Employee2FacadeLocal {

    void create(Employee2 employee2);

    void edit(Employee2 employee2);

    void remove(Employee2 employee2);

    Employee2 find(Object id);

    List<Employee2> findAll();

    List<Employee2> findRange(int[] range);

    int count();
    
    List<Employee> findEmployee();
    
    boolean checkUsernameExist(String username);
    
    boolean checkPhoneExist(String phone);
    
    boolean checkEmailExist(String email);
    
}
