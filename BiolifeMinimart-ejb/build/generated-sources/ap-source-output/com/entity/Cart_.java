package com.entity;

import com.entity.Customer;
import com.entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-04-26T08:32:13")
@StaticMetamodel(Cart.class)
public class Cart_ { 

    public static volatile SingularAttribute<Cart, Product> productID;
    public static volatile SingularAttribute<Cart, Integer> cartID;
    public static volatile SingularAttribute<Cart, Customer> customerUsername;
    public static volatile SingularAttribute<Cart, Integer> cartQuantity;

}