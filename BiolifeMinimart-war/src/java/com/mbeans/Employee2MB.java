/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Employee;
import com.entity.Employee2;
import com.sessionbean.Employee2FacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author OS
 */
@Named(value = "employee2MB")
@SessionScoped
public class Employee2MB implements Serializable {

    @EJB
    private Employee2FacadeLocal employee2Facade;

    private Employee2 employee2;
    private String usernameMessage;
    private String fullnameMessage;
    private String genderMessage;
    private String phoneMessage;
    private String emailMessage;

    public Employee2MB() {
        employee2 = new Employee2();
    }

    public List<Employee> showAllEmployees() {
        return employee2Facade.findEmployee();
    }

    public String formatGender(String username) {
        employee2 = employee2Facade.find(username);
        switch (employee2.getEmployeeGender()) {
            case 0:
                return "Male";
            case 1:
                return "Female";
            default:
                return "Other";
        }
    }

    public String formatStatus(String username) {
        employee2 = employee2Facade.find(username);
        if (employee2.getEmployeeStatus() == 1) {
            return "Inventory Manager";
        } else {
            return "Customer Manager";
        }
    }

    public String buttonStatus(String username) {
        employee2 = employee2Facade.find(username);
        if (employee2.getEmployeeStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-info";
        }
    }

    public String showInsertEmployeeForm() {
        employee2 = new Employee2();
        usernameMessage = "";
        fullnameMessage = "";
        phoneMessage = "";
        genderMessage = "";
        return "employee_create";
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (employee2.getEmployeeUsername().equals("")) {
            usernameMessage = "Username cannot be empty";
            valid = false;
        } else if (employee2Facade.checkUsernameExist(employee2.getEmployeeUsername())) {
            usernameMessage = "Username already exists";
            valid = false;
        } else {
            usernameMessage = "";
        }

        if (employee2.getEmployeeFullname().equals("")) {
            fullnameMessage = "Full Name cannot be empty";
            valid = false;
        } else {
            fullnameMessage = "";
        }

        if (employee2.getEmployeePhone().equals("")) {
            phoneMessage = "Phone Number cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^(\\(0\\d{1,3}\\)\\d{7})|(0\\d{9,10})$", employee2.getEmployeePhone())) {
            phoneMessage = "Phone Number must be a 10 to 11 digit number. Ex: 0358126687";
            valid = false;
        } else if (employee2Facade.checkPhoneExist(employee2.getEmployeePhone())) {
            phoneMessage = "Phone Number already exists";
            valid = false;
        } else {
            phoneMessage = "";
        }

        if (employee2.getEmployeeEmail().equals("")) {
            emailMessage = "Email cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$", employee2.getEmployeeEmail())) {
            emailMessage = "Email is incorrect. Ex: user123@gmail.com";
            valid = false;
        } else if (employee2Facade.checkEmailExist(employee2.getEmployeeEmail())) {
            emailMessage = "Email already exists";
            valid = false;
        } else {
            emailMessage = "";
        }

        return valid;
    }

    public void insertEmployee() {
        try {
            if (checkInsertForm()) {
                employee2.setEmployeePassword("5565B8E7BF495890EE95B3A0345D2C43");
                employee2.setEmployeeAvatar("https://res.cloudinary.com/dk6tfexdn/image/upload/v1680096409/Biolife/Employee/Employee_default.jpg");
                employee2Facade.create(employee2);
                FacesContext.getCurrentInstance().getExternalContext().redirect("employee_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(Employee2MB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updateEmployeeStatus(String username) {
        employee2 = employee2Facade.find(username);
        if (employee2.getEmployeeStatus() == 1) {
            employee2.setEmployeeStatus(2);
            employee2Facade.edit(employee2);
        } else {
            employee2.setEmployeeStatus(1);
            employee2Facade.edit(employee2);
        }
        return "employee_all";
    }

    public String deleteEmployee(String username) {
        employee2Facade.remove(employee2Facade.find(username));
        return "employee_all";
    }

    public Employee2 getEmployee2() {
        return employee2;
    }

    public void setEmployee2(Employee2 employee2) {
        this.employee2 = employee2;
    }

    public String getUsernameMessage() {
        return usernameMessage;
    }

    public void setUsernameMessage(String usernameMessage) {
        this.usernameMessage = usernameMessage;
    }

    public String getFullnameMessage() {
        return fullnameMessage;
    }

    public void setFullnameMessage(String fullnameMessage) {
        this.fullnameMessage = fullnameMessage;
    }

    public String getGenderMessage() {
        return genderMessage;
    }

    public void setGenderMessage(String genderMessage) {
        this.genderMessage = genderMessage;
    }

    public String getPhoneMessage() {
        return phoneMessage;
    }

    public void setPhoneMessage(String phoneMessage) {
        this.phoneMessage = phoneMessage;
    }

    public String getEmailMessage() {
        return emailMessage;
    }

    public void setEmailMessage(String emailMessage) {
        this.emailMessage = emailMessage;
    }

}
