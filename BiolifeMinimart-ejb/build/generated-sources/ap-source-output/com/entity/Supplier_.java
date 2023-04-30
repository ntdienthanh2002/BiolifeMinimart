package com.entity;

import com.entity.Product;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-04-26T08:32:13")
@StaticMetamodel(Supplier.class)
public class Supplier_ { 

    public static volatile SingularAttribute<Supplier, String> supplierName;
    public static volatile SingularAttribute<Supplier, String> supplierAddress;
    public static volatile SingularAttribute<Supplier, Integer> supplierID;
    public static volatile SingularAttribute<Supplier, String> supplierEmail;
    public static volatile CollectionAttribute<Supplier, Product> productCollection;
    public static volatile SingularAttribute<Supplier, Integer> supplierStatus;
    public static volatile SingularAttribute<Supplier, String> supplierPhone;

}