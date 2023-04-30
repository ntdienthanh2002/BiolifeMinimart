package com.entity;

import com.entity.Customer;
import com.entity.Orders;
import com.entity.Product;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-04-26T08:32:13")
@StaticMetamodel(Feedback.class)
public class Feedback_ { 

    public static volatile SingularAttribute<Feedback, String> feedbackContent;
    public static volatile SingularAttribute<Feedback, Integer> feedbackRate;
    public static volatile SingularAttribute<Feedback, Product> productID;
    public static volatile SingularAttribute<Feedback, Orders> orderID;
    public static volatile SingularAttribute<Feedback, Date> feedbackDate;
    public static volatile SingularAttribute<Feedback, Integer> feedbackID;
    public static volatile SingularAttribute<Feedback, Customer> customerUsername;
    public static volatile SingularAttribute<Feedback, Integer> feedbackStatus;

}