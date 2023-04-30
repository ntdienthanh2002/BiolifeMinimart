/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface CustomerFacadeLocal {

    void create(Customer customer);

    void edit(Customer customer);

    void remove(Customer customer);

    Customer find(Object id);

    List<Customer> findAll();

    List<Customer> findRange(int[] range);

    int count();
    
    boolean checkExistUsername(String username);
    boolean checkExistEmail(String email);
    boolean checkExistPhone(String phone);
    Customer login(String uname, String pword);
    void createCustomer(Customer customer);
    void editCustomer(Customer customer);
    void changePassword(String username, String password);
    
    void forgot_pass(String email, String pword);
    
    
}
