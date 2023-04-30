/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import java.io.Serializable;
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
@Table(name = "PromotionDetails")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PromotionDetails.findAll", query = "SELECT p FROM PromotionDetails p"),
    @NamedQuery(name = "PromotionDetails.findByPromotionDetailsID", query = "SELECT p FROM PromotionDetails p WHERE p.promotionDetailsID = :promotionDetailsID"),
    @NamedQuery(name = "PromotionDetails.findByPromotionDetailsDiscount", query = "SELECT p FROM PromotionDetails p WHERE p.promotionDetailsDiscount = :promotionDetailsDiscount")})
public class PromotionDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PromotionDetailsID")
    private Integer promotionDetailsID;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PromotionDetailsDiscount")
    private double promotionDetailsDiscount;
    @JoinColumn(name = "ProductID", referencedColumnName = "ProductID")
    @ManyToOne(optional = false)
    private Product productID;
    @JoinColumn(name = "PromotionID", referencedColumnName = "PromotionID")
    @ManyToOne(optional = false)
    private Promotion promotionID;

    public PromotionDetails() {
    }

    public Integer getPromotionDetailsID() {
        return promotionDetailsID;
    }

    public double getPromotionDetailsDiscount() {
        return promotionDetailsDiscount;
    }

    public void setPromotionDetailsDiscount(double promotionDetailsDiscount) {
        this.promotionDetailsDiscount = promotionDetailsDiscount;
    }

    public Product getProductID() {
        return productID;
    }

    public void setProductID(Product productID) {
        this.productID = productID;
    }

    public Promotion getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(Promotion promotionID) {
        this.promotionID = promotionID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionDetailsID != null ? promotionDetailsID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PromotionDetails)) {
            return false;
        }
        PromotionDetails other = (PromotionDetails) object;
        if ((this.promotionDetailsID == null && other.promotionDetailsID != null) || (this.promotionDetailsID != null && !this.promotionDetailsID.equals(other.promotionDetailsID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybean.PromotionDetails[ promotionDetailsID=" + promotionDetailsID + " ]";
    }
    
}
