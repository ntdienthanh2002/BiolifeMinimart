/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.sessionbean;

import com.entity.Customer;
import com.entity.Orders;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author OS
 */
@Local
public interface OrdersFacadeLocal {

    void create(Orders orders);

    void edit(Orders orders);

    void remove(Orders orders);

    Orders find(Object id);

    List<Orders> findAll();

    List<Orders> findRange(int[] range);

    int count();
    
    List<Orders> findLastOrder(Customer customer);
    
    public Integer countMonthOrder(Date date1, Date date2);
    
    public List<Orders> getOrdersByMonth(Date date1, Date date2);
    
    public List<Orders> getReturnOrdersByMonth(Date date1, Date date2);
    
    public Long countOrder(Date date);
    
    public List<Orders> getOrdersByDate(Date date1, Date date2);
    
    List<Orders> showOrder(String uname);
    
    public int countOrderByDeliveryStatus(int staus);
    
    public int countDeliveryStatusByYear(int status, int year);
    
    public int countDeliveryStatusByMonth(int status, int year,int month);
    
    List<Orders> findOrdersByStatus();

}
