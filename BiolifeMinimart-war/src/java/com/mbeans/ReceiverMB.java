/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Customer;
import com.entity.Receiver;
import com.sessionbean.CustomerFacadeLocal;
import com.sessionbean.ReceiverFacadeLocal;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author OS
 */
@Named(value = "receiverMB")
@SessionScoped
public class ReceiverMB implements Serializable {

    @EJB
    private CustomerFacadeLocal customerFacade;

    @EJB
    private ReceiverFacadeLocal receiverFacade;

    private Receiver receiver;

    @NotEmpty
    private String city;

    @NotEmpty
    private String district;

    @NotEmpty
    private String ward;

    @NotNull
    private String address;

    private String mess;

    private String usernameCustomer;

    public ReceiverMB() {
        receiver = new Receiver();
        receiver.setReceiverStatus(2);
    }

    public void createReciver(String uname) {
            receiver.setCustomerUsername(customerFacade.find(uname));
            if(receiverFacade.showAllReceiver(uname).size()==0){
                receiver.setReceiverStatus(1);
            }else{
                receiver.setReceiverStatus(2);
            }
            receiverFacade.create(receiver);
            mess = "success";
            receiver= new Receiver();
    }

    public List<Receiver> showAllReceiver(String username) {
        return receiverFacade.showAllReceiver(username);
    }

    public void deleteReceiver(int id) {
        Receiver r = receiverFacade.find(id);
        r.setReceiverStatus(0);
        receiverFacade.edit(r);
    }

    public void setDefaultReceiver(int id) {
        receiverFacade.setDefault(id);
    }
    public List<Receiver> showEnableReceiversByCustomer(String customer) {
        return receiverFacade.showEnableReceiversByCustomer(customerFacade.find(customer));
    }
    
    public int countReceiver(String customer) {
        List<Receiver> listReceiver = showEnableReceiversByCustomer(customer);
        return listReceiver.size();
    }

    public String showReceiverPage(String username) {
        Customer customer = customerFacade.find(username);
        usernameCustomer = customer.getCustomerUsername();
        return "receiver_all";
    }

    public List<Receiver> showReceiverByCustomer() {
        Customer customer = customerFacade.find(usernameCustomer);
        return receiverFacade.findReceiverByCustomer(customer);
    }

    public String formatStatus(int id) {
        receiver = receiverFacade.find(id);
        switch (receiver.getReceiverStatus()) {
            case 0:
                return "Disable";
            case 1:
                return "Default";
            default:
                return "Enable";
        }
    }

    public String buttonStatus(int id) {
        receiver = receiverFacade.find(id);
        switch (receiver.getReceiverStatus()) {
            case 0:
                return "badge-warning";
            case 1:
                return "badge-info";
            default:
                return "badge-success";
        }
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getUsernameCustomer() {
        return usernameCustomer;
    }

    public void setUsernameCustomer(String usernameCustomer) {
        this.usernameCustomer = usernameCustomer;
    }

}
