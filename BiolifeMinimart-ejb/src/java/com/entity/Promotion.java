/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author OS
 */
@Entity
@Table(name = "Promotion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Promotion.findAll", query = "SELECT p FROM Promotion p"),
    @NamedQuery(name = "Promotion.findByPromotionID", query = "SELECT p FROM Promotion p WHERE p.promotionID = :promotionID"),
    @NamedQuery(name = "Promotion.findByPromotionName", query = "SELECT p FROM Promotion p WHERE p.promotionName = :promotionName"),
    @NamedQuery(name = "Promotion.findByPromotionStart", query = "SELECT p FROM Promotion p WHERE p.promotionStart = :promotionStart"),
    @NamedQuery(name = "Promotion.findByPromotionEnd", query = "SELECT p FROM Promotion p WHERE p.promotionEnd = :promotionEnd")})
public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "PromotionID")
    private Integer promotionID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PromotionName")
    private String promotionName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PromotionStart")
    @Temporal(TemporalType.DATE)
    private Date promotionStart;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PromotionEnd")
    @Temporal(TemporalType.DATE)
    private Date promotionEnd;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "promotionID")
    private Collection<PromotionDetails> promotionDetailsCollection;

    public Promotion() {
    }

    public Integer getPromotionID() {
        return promotionID;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public Date getPromotionStart() {
        return promotionStart;
    }

    public void setPromotionStart(Date promotionStart) {
        this.promotionStart = promotionStart;
    }

    public Date getPromotionEnd() {
        return promotionEnd;
    }

    public void setPromotionEnd(Date promotionEnd) {
        this.promotionEnd = promotionEnd;
    }

    @XmlTransient
    public Collection<PromotionDetails> getPromotionDetailsCollection() {
        return promotionDetailsCollection;
    }

    public void setPromotionDetailsCollection(Collection<PromotionDetails> promotionDetailsCollection) {
        this.promotionDetailsCollection = promotionDetailsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (promotionID != null ? promotionID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Promotion)) {
            return false;
        }
        Promotion other = (Promotion) object;
        if ((this.promotionID == null && other.promotionID != null) || (this.promotionID != null && !this.promotionID.equals(other.promotionID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybean.Promotion[ promotionID=" + promotionID + " ]";
    }
    
}
