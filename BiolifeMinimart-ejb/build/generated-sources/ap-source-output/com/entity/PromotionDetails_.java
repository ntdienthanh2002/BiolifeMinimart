package com.entity;

import com.entity.Product;
import com.entity.Promotion;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2023-04-26T08:32:13")
@StaticMetamodel(PromotionDetails.class)
public class PromotionDetails_ { 

    public static volatile SingularAttribute<PromotionDetails, Integer> promotionDetailsID;
    public static volatile SingularAttribute<PromotionDetails, Product> productID;
    public static volatile SingularAttribute<PromotionDetails, Double> promotionDetailsDiscount;
    public static volatile SingularAttribute<PromotionDetails, Promotion> promotionID;

}