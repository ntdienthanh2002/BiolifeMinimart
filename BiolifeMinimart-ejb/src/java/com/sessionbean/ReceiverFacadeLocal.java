/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Receiver;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface ReceiverFacadeLocal {

    void create(Receiver receiver);

    void edit(Receiver receiver);

    void remove(Receiver receiver);

    Receiver find(Object id);

    List<Receiver> findAll();

    List<Receiver> findRange(int[] range);

    int count();
    
    List<Receiver> showAllReceiver(String username);
    
    void setDefault(int id);
    
    List<Receiver> showEnableReceiversByCustomer(Customer customer);
    
    Receiver showDefaultReceiver(Customer customer);
    
    List<Receiver> findReceiverByCustomer(Customer username);
}
