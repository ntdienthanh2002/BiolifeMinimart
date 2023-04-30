/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Feedback;
import com.entity.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface FeedbackFacadeLocal {

    void create(Feedback feedback);

    void edit(Feedback feedback);

    void remove(Feedback feedback);

    Feedback find(Object id);

    List<Feedback> findAll();

    List<Feedback> findRange(int[] range);

    int count();
    
    List<Feedback> findFeedbackByProduct(Product id);
    
    public Long countFeedbackByProduct(Product id);
    
    public Long sumFeedbackRateByProduct(Product id);
    
    public Long countFeedbackRateByProduct(Product id, int rate);
    
    boolean checkCustomerBuyProduct(String username, int id);
    
    boolean checkCustomerToDeleteFeedback(Customer username, int id);
    
    public List<Feedback> showAllFeedbackByCustomer(String username);
    
}
