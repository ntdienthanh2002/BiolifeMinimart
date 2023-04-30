/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Supplier;
import com.sessionbean.SupplierFacadeLocal;
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
@Named(value = "supplierMB")
@SessionScoped
public class SupplierMB implements Serializable {

    @EJB
    private SupplierFacadeLocal supplierFacade;

    private Supplier supplier;
    private int idSupplier;
    private String nameMessage;
    private String addressMessage;
    private String phoneMessage;
    private String emailMessage;

    public SupplierMB() {
        supplier = new Supplier();
    }

    public List<Supplier> showAllSuppliers() {
        return supplierFacade.findAll();
    }

    public List<Supplier> showEnableSuppliers() {
        return supplierFacade.findEnableSuppliers();
    }

    public String formatStatus(int id) {
        supplier = supplierFacade.find(id);
        if (supplier.getSupplierStatus() == 1) {
            return "Enable";
        } else {
            return "Disable";
        }
    }

    public String buttonStatus(int id) {
        supplier = supplierFacade.find(id);
        if (supplier.getSupplierStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-warning";
        }
    }

    public String showInsertSupplierForm() {
        supplier = new Supplier();
        nameMessage = "";
        addressMessage = "";
        phoneMessage = "";
        emailMessage = "";
        return "supplier_create";
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (supplier.getSupplierName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (supplierFacade.checkSupplierExist(supplier.getSupplierName())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (supplier.getSupplierAddress().equals("")) {
            addressMessage = "Address cannot be empty";
            valid = false;
        } else {
            addressMessage = "";
        }

        if (supplier.getSupplierPhone().equals("")) {
            phoneMessage = "Phone Number cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^(\\(0\\d{1,3}\\)\\d{7})|(0\\d{9,10})$", supplier.getSupplierPhone())) {
            phoneMessage = "Phone Number must be a 10 to 11 digit number. Ex: 0358126687";
            valid = false;
        } else {
            phoneMessage = "";
        }

        if (supplier.getSupplierEmail().equals("")) {
            emailMessage = "Email cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$", supplier.getSupplierEmail())) {
            emailMessage = "Email is incorrect. Ex: user123@gmail.com";
            valid = false;
        } else {
            emailMessage = "";
        }

        return valid;
    }

    public void insertSupplier() {
        try {
            if (checkInsertForm()) {
                supplier.setSupplierStatus(1);
                supplierFacade.create(supplier);
                FacesContext.getCurrentInstance().getExternalContext().redirect("supplier_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(SupplierMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String showUpdateSupplierForm(int id) {
        supplier = supplierFacade.find(id);
        idSupplier = supplier.getSupplierID();
        nameMessage = "";
        addressMessage = "";
        phoneMessage = "";
        emailMessage = "";
        return "supplier_update";
    }

    public boolean checkUpdateForm() {
        boolean valid = true;

        if (supplier.getSupplierName().equals("")) {
            nameMessage = "Name cannot be empty";
            valid = false;
        } else if (supplierFacade.checkUpdateSupplierExist(supplier.getSupplierName(), supplier.getSupplierID())) {
            nameMessage = "Name already exists";
            valid = false;
        } else {
            nameMessage = "";
        }

        if (supplier.getSupplierAddress().equals("")) {
            addressMessage = "Address cannot be empty";
            valid = false;
        } else {
            addressMessage = "";
        }

        if (supplier.getSupplierPhone().equals("")) {
            phoneMessage = "Phone Number cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^(\\(0\\d{1,3}\\)\\d{7})|(0\\d{9,10})$", supplier.getSupplierPhone())) {
            phoneMessage = "Phone Number must be a 10 to 11 digit number. Ex: 0358126687";
            valid = false;
        } else {
            phoneMessage = "";
        }

        if (supplier.getSupplierEmail().equals("")) {
            emailMessage = "Email cannot be empty";
            valid = false;
        } else if (!Pattern.matches("^[a-zA-Z]\\w*(\\.\\w+)*\\@\\w+(\\.\\w{2,3})+$", supplier.getSupplierEmail())) {
            emailMessage = "Email is incorrect. Ex: user123@gmail.com";
            valid = false;
        } else {
            emailMessage = "";
        }

        return valid;
    }

    public void updateSupplier() {
        try {
            if (checkUpdateForm()) {
                Supplier su = supplierFacade.find(idSupplier);
                su.setSupplierName(supplier.getSupplierName());
                su.setSupplierAddress(supplier.getSupplierAddress());
                su.setSupplierEmail(supplier.getSupplierEmail());
                su.setSupplierPhone(supplier.getSupplierPhone());
                supplierFacade.edit(su);
                FacesContext.getCurrentInstance().getExternalContext().redirect("supplier_all.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(SupplierMB.class.getName()).log(Level.SEVERE, null, ex);
        }

//        return "supplier_all";
    }

    public String updateSupplierStatus(int id) {
        supplier = supplierFacade.find(id);
        if (supplier.getSupplierStatus() == 0) {
            supplier.setSupplierStatus(1);
            supplierFacade.edit(supplier);
            supplierFacade.updateProductStatusBySupplier(supplier, 1);
        } else {
            supplier.setSupplierStatus(0);
            supplierFacade.edit(supplier);
            supplierFacade.updateProductStatusBySupplier(supplier, 0);
        }
        return "supplier_all";
    }

    public String disableSupplier(int id) {
        supplier = supplierFacade.find(id);
        supplier.setSupplierStatus(0);
        supplierFacade.edit(supplier);
        supplierFacade.updateProductStatusBySupplier(supplier, 0);
        return "category_all";
    }

    public String deleteSupplier(int id) {
        supplier = supplierFacade.find(id);
        if (supplierFacade.checkProductBySupplier(supplier)) {
            disableSupplier(supplier.getSupplierID());
            return "supplier_all";
        }
        supplierFacade.remove(supplier);
        return "supplier_all";
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public int getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(int idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getAddressMessage() {
        return addressMessage;
    }

    public void setAddressMessage(String addressMessage) {
        this.addressMessage = addressMessage;
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
