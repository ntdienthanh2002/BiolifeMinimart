package com.entity;

import com.entity.Cart;
import com.entity.Feedback;
import com.entity.Receiver;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-04-26T08:32:13")
@StaticMetamodel(Customer.class)
public class Customer_ { 

    public static volatile SingularAttribute<Customer, Integer> customerStatus;
    public static volatile CollectionAttribute<Customer, Cart> cartCollection;
    public static volatile SingularAttribute<Customer, String> customerPhone;
    public static volatile SingularAttribute<Customer, String> customerAvatar;
    public static volatile SingularAttribute<Customer, Integer> customerGender;
    public static volatile SingularAttribute<Customer, String> customerEmail;
    public static volatile SingularAttribute<Customer, String> customerPassword;
    public static volatile CollectionAttribute<Customer, Feedback> feedbackCollection;
    public static volatile SingularAttribute<Customer, String> customerUsername;
    public static volatile CollectionAttribute<Customer, Receiver> receiverCollection;
    public static volatile SingularAttribute<Customer, String> customerFullname;

}