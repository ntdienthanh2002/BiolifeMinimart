/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author OS
 */
@Entity
@Table(name = "OrderDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "OrderDetails.findAll", query = "SELECT o FROM OrderDetails o"),
    @NamedQuery(name = "OrderDetails.findByOrderDetailsID", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsID = :orderDetailsID"),
    @NamedQuery(name = "OrderDetails.findByOrderDetailsQuantity", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsQuantity = :orderDetailsQuantity"),
    @NamedQuery(name = "OrderDetails.findByOrderDetailsUnitPrice", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsUnitPrice = :orderDetailsUnitPrice"),
    @NamedQuery(name = "OrderDetails.findByOrderDetailsDiscount", query = "SELECT o FROM OrderDetails o WHERE o.orderDetailsDiscount = :orderDetailsDiscount")})
public class OrderDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "OrderDetailsID")
    private Integer orderDetailsID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderDetailsQuantity")
    private int orderDetailsQuantity;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderDetailsUnitPrice")
    private BigDecimal orderDetailsUnitPrice;
    @Basic(optional = false)
    @NotNull
    @Column(name = "OrderDetailsDiscount")
    private double orderDetailsDiscount;
    @JoinColumn(name = "OrderID", referencedColumnName = "OrderID")
    @ManyToOne(optional = false)
    private Orders orderID;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;

    public OrderDetails() {
    }

    public Integer getOrderDetailsID() {
        return orderDetailsID;
    }

    public int getOrderDetailsQuantity() {
        return orderDetailsQuantity;
    }

    public void setOrderDetailsQuantity(int orderDetailsQuantity) {
        this.orderDetailsQuantity = orderDetailsQuantity;
    }

    public BigDecimal getOrderDetailsUnitPrice() {
        return orderDetailsUnitPrice;
    }

    public void setOrderDetailsUnitPrice(BigDecimal orderDetailsUnitPrice) {
        this.orderDetailsUnitPrice = orderDetailsUnitPrice;
    }

    public double getOrderDetailsDiscount() {
        return orderDetailsDiscount;
    }

    public void setOrderDetailsDiscount(double orderDetailsDiscount) {
        this.orderDetailsDiscount = orderDetailsDiscount;
    }

    public Orders getOrderID() {
        return orderID;
    }

    public void setOrderID(Orders orderID) {
        this.orderID = orderID;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderDetailsID != null ? orderDetailsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetails)) {
            return false;
        }
        OrderDetails other = (OrderDetails) object;
        if ((this.orderDetailsID == null && other.orderDetailsID != null) || (this.orderDetailsID != null && !this.orderDetailsID.equals(other.orderDetailsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybean.OrderDetails[ orderDetailsID=" + orderDetailsID + " ]";
    }
    
}
