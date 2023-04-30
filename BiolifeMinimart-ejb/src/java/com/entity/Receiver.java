/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Duy Khanh
 */
@Entity
@Table(name = "Receiver")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Receiver.findAll", query = "SELECT r FROM Receiver r"),
    @NamedQuery(name = "Receiver.findByReceiverID", query = "SELECT r FROM Receiver r WHERE r.receiverID = :receiverID"),
    @NamedQuery(name = "Receiver.findByReceiverName", query = "SELECT r FROM Receiver r WHERE r.receiverName = :receiverName"),
    @NamedQuery(name = "Receiver.findByReceiverPhone", query = "SELECT r FROM Receiver r WHERE r.receiverPhone = :receiverPhone"),
    @NamedQuery(name = "Receiver.findByReceiverAddress", query = "SELECT r FROM Receiver r WHERE r.receiverAddress = :receiverAddress"),
    @NamedQuery(name = "Receiver.findByReceiverStatus", query = "SELECT r FROM Receiver r WHERE r.receiverStatus = :receiverStatus")})
public class Receiver implements Serializable {
//Duy Khanh start
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ReceiverID")
    private Integer receiverID;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Receiver Name")
    //@Size(min = 1, max = 100)
    @Column(name = "ReceiverName")
    private String receiverName;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Receiver Phone")
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Phone is invalidate. Must be a 10 digit number. Ex: 0388888888")
    @Size(min = 1, max = 20)
    @Column(name = "ReceiverPhone")
    private String receiverPhone;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Receiver Address")
    @Size(min = 1, max = 255)
    @Column(name = "ReceiverAddress")
    private String receiverAddress;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ReceiverStatus")
    private int receiverStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiverID")
    private Collection<Orders> ordersCollection;
    @JoinColumn(name = "CustomerUsername", referencedColumnName = "CustomerUsername")
    @ManyToOne(optional = false)
    private Customer customerUsername;

    public Receiver() {
    }



    public Integer getReceiverID() {
        return receiverID;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public int getReceiverStatus() {
        return receiverStatus;
    }

    public void setReceiverStatus(int receiverStatus) {
        this.receiverStatus = receiverStatus;
    }

    @XmlTransient
    public Collection<Orders> getOrdersCollection() {
        return ordersCollection;
    }

    public void setOrdersCollection(Collection<Orders> ordersCollection) {
        this.ordersCollection = ordersCollection;
    }

    public Customer getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(Customer customerUsername) {
        this.customerUsername = customerUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (receiverID != null ? receiverID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Receiver)) {
            return false;
        }
        Receiver other = (Receiver) object;
        if ((this.receiverID == null && other.receiverID != null) || (this.receiverID != null && !this.receiverID.equals(other.receiverID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Receiver[ receiverID=" + receiverID + " ]";
    }
    //Duy Khanh end
    
}
