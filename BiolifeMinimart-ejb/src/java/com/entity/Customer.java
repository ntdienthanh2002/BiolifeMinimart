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
import javax.persistence.Id;
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
@Table(name = "Customer")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"),
    @NamedQuery(name = "Customer.findByCustomerUsername", query = "SELECT c FROM Customer c WHERE c.customerUsername = :customerUsername"),
    @NamedQuery(name = "Customer.findByCustomerFullname", query = "SELECT c FROM Customer c WHERE c.customerFullname = :customerFullname"),
    @NamedQuery(name = "Customer.findByCustomerGender", query = "SELECT c FROM Customer c WHERE c.customerGender = :customerGender"),
    @NamedQuery(name = "Customer.findByCustomerEmail", query = "SELECT c FROM Customer c WHERE c.customerEmail = :customerEmail"),
    @NamedQuery(name = "Customer.findByCustomerPassword", query = "SELECT c FROM Customer c WHERE c.customerPassword = :customerPassword"),
    @NamedQuery(name = "Customer.findByCustomerPhone", query = "SELECT c FROM Customer c WHERE c.customerPhone = :customerPhone"),
    @NamedQuery(name = "Customer.findByCustomerAvatar", query = "SELECT c FROM Customer c WHERE c.customerAvatar = :customerAvatar"),
    @NamedQuery(name = "Customer.findByCustomerStatus", query = "SELECT c FROM Customer c WHERE c.customerStatus = :customerStatus")})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Username")
    @Size(min = 1, max = 50)
    @Column(name = "CustomerUsername")
    private String customerUsername;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Fullname")
    @Size(min = 1, max = 100)
    @Column(name = "CustomerFullname")
    private String customerFullname;
    
    @Basic(optional = false)
    @NotNull(message = "Please choose Gender")
    @Column(name = "CustomerGender")
    private int customerGender;
    
    @Size(max = 70)
    @NotEmpty(message = "Please enter Email")
    @Pattern(regexp="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",message="Email is invalidate. Ex: info@123.gmail.com")  
    @Column(name = "CustomerEmail")
    private String customerEmail;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Password")
    @Size(min = 1, max = 50)
    @Column(name = "CustomerPassword")
    private String customerPassword;
    
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Phone")
    @Size(min = 1, max = 20)
    @Pattern(regexp = "(0[3|5|7|8|9])+([0-9]{8})\\b", message = "Phone is invalidate. Must be a 10 digit number. Ex: 0388888888")
    @Column(name = "CustomerPhone")
    private String customerPhone;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "CustomerAvatar")
    private String customerAvatar;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CustomerStatus")
    private int customerStatus;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerUsername")
    private Collection<Receiver> receiverCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerUsername")
    private Collection<Feedback> feedbackCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customerUsername")
    private Collection<Cart> cartCollection;

    public Customer() {
    }

    public Customer(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public Customer(String customerUsername, String customerFullname, int customerGender, String customerPassword, String customerPhone, String customerAvatar, int customerStatus) {
        this.customerUsername = customerUsername;
        this.customerFullname = customerFullname;
        this.customerGender = customerGender;
        this.customerPassword = customerPassword;
        this.customerPhone = customerPhone;
        this.customerAvatar = customerAvatar;
        this.customerStatus = customerStatus;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public String getCustomerFullname() {
        return customerFullname;
    }

    public void setCustomerFullname(String customerFullname) {
        this.customerFullname = customerFullname;
    }

    public int getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(int customerGender) {
        this.customerGender = customerGender;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getCustomerPassword() {
        return customerPassword;
    }

    public void setCustomerPassword(String customerPassword) {
        this.customerPassword = customerPassword;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAvatar() {
        return customerAvatar;
    }

    public void setCustomerAvatar(String customerAvatar) {
        this.customerAvatar = customerAvatar;
    }

    public int getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(int customerStatus) {
        this.customerStatus = customerStatus;
    }

    @XmlTransient
    public Collection<Receiver> getReceiverCollection() {
        return receiverCollection;
    }

    public void setReceiverCollection(Collection<Receiver> receiverCollection) {
        this.receiverCollection = receiverCollection;
    }

    @XmlTransient
    public Collection<Feedback> getFeedbackCollection() {
        return feedbackCollection;
    }

    public void setFeedbackCollection(Collection<Feedback> feedbackCollection) {
        this.feedbackCollection = feedbackCollection;
    }

    @XmlTransient
    public Collection<Cart> getCartCollection() {
        return cartCollection;
    }

    public void setCartCollection(Collection<Cart> cartCollection) {
        this.cartCollection = cartCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerUsername != null ? customerUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerUsername == null && other.customerUsername != null) || (this.customerUsername != null && !this.customerUsername.equals(other.customerUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entity.Customer[ customerUsername=" + customerUsername + " ]";
    }
    
}
