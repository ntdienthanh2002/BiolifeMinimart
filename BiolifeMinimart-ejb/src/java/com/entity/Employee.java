/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author OS
 */
@Entity
@Table(name = "Employee")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Employee.findAll", query = "SELECT e FROM Employee e"),
    @NamedQuery(name = "Employee.findByEmployeeUsername", query = "SELECT e FROM Employee e WHERE e.employeeUsername = :employeeUsername"),
    @NamedQuery(name = "Employee.findByEmployeeFullname", query = "SELECT e FROM Employee e WHERE e.employeeFullname = :employeeFullname"),
    @NamedQuery(name = "Employee.findByEmployeeGender", query = "SELECT e FROM Employee e WHERE e.employeeGender = :employeeGender"),
    @NamedQuery(name = "Employee.findByEmployeeEmail", query = "SELECT e FROM Employee e WHERE e.employeeEmail = :employeeEmail"),
    @NamedQuery(name = "Employee.findByEmployeeAddress", query = "SELECT e FROM Employee e WHERE e.employeeAddress = :employeeAddress"),
    @NamedQuery(name = "Employee.findByEmployeePassword", query = "SELECT e FROM Employee e WHERE e.employeePassword = :employeePassword"),
    @NamedQuery(name = "Employee.findByEmployeePhone", query = "SELECT e FROM Employee e WHERE e.employeePhone = :employeePhone"),
    @NamedQuery(name = "Employee.findByEmployeeAvatar", query = "SELECT e FROM Employee e WHERE e.employeeAvatar = :employeeAvatar"),
    @NamedQuery(name = "Employee.findByEmployeeStatus", query = "SELECT e FROM Employee e WHERE e.employeeStatus = :employeeStatus")})
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 50)
    @Column(name = "EmployeeUsername")
    private String employeeUsername;
    @Basic(optional = false)
    @NotEmpty(message = "Please enter Fullname")
    @Size(min = 1, max = 100)
    @Column(name = "EmployeeFullname")
    private String employeeFullname;
    @Basic(optional = false)
    @NotNull(message = "Please choose Gender")
    @Column(name = "EmployeeGender")
    private int employeeGender;
    @Size(max = 70)
    @Column(name = "EmployeeEmail")
    private String employeeEmail;
    @Size(max = 255)
    @Column(name = "EmployeeAddress")
    private String employeeAddress;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "EmployeePassword")
    private String employeePassword;
    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
    @Column(name = "EmployeePhone")
    private String employeePhone;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2147483647)
    @Column(name = "EmployeeAvatar")
    private String employeeAvatar;
    @Basic(optional = false)
    @NotNull
    @Column(name = "EmployeeStatus")
    private int employeeStatus;

    public Employee() {
    }

    public Employee(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public Employee(String employeeUsername, String employeeFullname, int employeeGender, String employeePassword, String employeePhone, String employeeAvatar, int employeeStatus) {
        this.employeeUsername = employeeUsername;
        this.employeeFullname = employeeFullname;
        this.employeeGender = employeeGender;
        this.employeePassword = employeePassword;
        this.employeePhone = employeePhone;
        this.employeeAvatar = employeeAvatar;
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }

    public String getEmployeeFullname() {
        return employeeFullname;
    }

    public void setEmployeeFullname(String employeeFullname) {
        this.employeeFullname = employeeFullname;
    }

    public int getEmployeeGender() {
        return employeeGender;
    }

    public void setEmployeeGender(int employeeGender) {
        this.employeeGender = employeeGender;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        this.employeeAddress = employeeAddress;
    }

    public String getEmployeePassword() {
        return employeePassword;
    }

    public void setEmployeePassword(String employeePassword) {
        this.employeePassword = employeePassword;
    }

    public String getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String employeePhone) {
        this.employeePhone = employeePhone;
    }

    public String getEmployeeAvatar() {
        return employeeAvatar;
    }

    public void setEmployeeAvatar(String employeeAvatar) {
        this.employeeAvatar = employeeAvatar;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeStatus(int employeeStatus) {
        this.employeeStatus = employeeStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeUsername != null ? employeeUsername.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeUsername == null && other.employeeUsername != null) || (this.employeeUsername != null && !this.employeeUsername.equals(other.employeeUsername))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.entitybean.Employee[ employeeUsername=" + employeeUsername + " ]";
    }

}
