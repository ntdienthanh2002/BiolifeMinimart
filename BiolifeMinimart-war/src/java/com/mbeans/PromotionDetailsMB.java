/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Product;
import com.entity.PromotionDetails;
import com.sessionbean.ProductFacadeLocal;
import com.sessionbean.PromotionDetailsFacadeLocal;
import com.sessionbean.PromotionFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author OS
 */
@Named(value = "promotionDetailsMB")
@SessionScoped
public class PromotionDetailsMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private PromotionFacadeLocal promotionFacade;

    @EJB
    private PromotionDetailsFacadeLocal promotionDetailsFacade;

    private int promotionID;

    private PromotionDetails promotionDetails;

    private Product product;

    private String discount = "";

    private String productMessage;

    private String discountMessage;

    private List<Product> productList;

    private String productString;
    
    private String productName;

    public PromotionDetailsMB() {
        promotionDetails = new PromotionDetails();
        product = new Product();
        productList = new ArrayList<>();
    }

    public List<PromotionDetails> showPromotionDetailsByPromotion() {
        return promotionDetailsFacade.showshowPromotionDetailsByPromotion(promotionFacade.find(promotionID));
    }

    public void showPromotionDetails() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        promotionID = Integer.valueOf(ec.getRequestParameterMap().get("promotionID"));
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotiondetails_all.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void resetMessage() {
        productMessage = "";
        discountMessage = "";
    }

    public void loadInsertForm() {
        productList = new ArrayList<>();
        promotionDetails = new PromotionDetails();
        promotionDetails.setPromotionID(promotionFacade.find(promotionID));
        product = new Product();
        discount = null;
        resetMessage();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotiondetails_create.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionDetailsMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Product> showNoPromotionProduct() {
        List<PromotionDetails> list = promotionDetailsFacade.findAll();
        List<PromotionDetails> listPromotionDetails = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPromotionID().getPromotionID() == promotionID) {
                listPromotionDetails.add(list.get(i));
            }
        }

        List<Integer> listProduct = new ArrayList<>();
        for (int i = 0; i < listPromotionDetails.size(); i++) {
            listProduct.add(listPromotionDetails.get(i).getProductID().getProductID());
        }
        return promotionDetailsFacade.showNoPromotionProduct(listProduct);
    }

    public List<Product> showListSelectedProduct() {
        System.out.println("List Pro: " + productList.size());
        List<Product> listProduct = new ArrayList<>();
        if (productList.size() > 0) {
            for (int i = 0; i < productList.size(); i++) {
                System.out.println("List Pro: " + productList.get(i));
                listProduct.add(productFacade.find(productList.get(i).getProductID()));
            }
        }
        return listProduct;
    }

    public void reloadView() {
        System.out.println("Size: " + productString);
    }

    public boolean checkPromotionDetailsForm() {
        boolean valid = true;
        if (productList.isEmpty()) {
            productMessage = "Product must be select";
            valid = false;
        } else {
            productMessage = "";
        }

        if (discount == null || discount.equals("")) {
            discountMessage = "This field is required";
            valid = false;
        } else {
            try {
                Double.parseDouble(discount);
                discountMessage = "";
                if (Double.parseDouble(discount) <= 0) {
                    valid = false;
                    discountMessage = "Discount must greater than 0";
                }
            } catch (NumberFormatException e) {
                valid = false;
                discountMessage = "Discount must be a real number";
            }
        }

        return valid;
    }
    
    public boolean checkUpdateForm() {
        boolean valid = true;
        if (discount == null || discount.equals("")) {
            discountMessage = "This field is required";
            valid = false;
        } else {
            try {
                Double.parseDouble(discount);
                discountMessage = "";
                if (Double.parseDouble(discount) <= 0) {
                    valid = false;
                    discountMessage = "Discount must greater than 0";
                }
            } catch (NumberFormatException e) {
                valid = false;
                discountMessage = "Discount must be a real number";
            }
        }

        return valid;
    }

    public String insertPromotion() {
        if (!checkPromotionDetailsForm()) {
            System.out.println(productMessage);
            System.out.println(discountMessage);
            return "promotiondetails_create";
        }

        for (int i = 0; i < productList.size(); i++) {
            PromotionDetails p = new PromotionDetails();
            p.setProductID(productList.get(i));
            p.setPromotionDetailsDiscount(Double.parseDouble(discount));
            p.setPromotionID(promotionFacade.find(promotionID));
            promotionDetailsFacade.create(p);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotiondetails_all.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionDetailsMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public void loadUpdateForm(int id) {
        promotionDetails = promotionDetailsFacade.find(id);
        resetMessage();

        product = promotionDetails.getProductID();
        discount = promotionDetails.getPromotionDetailsDiscount() + "";

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotiondetails_update.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionDetailsMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String updatePromotionDetails() {
        if (!checkUpdateForm()) {
            return "promotiondetails_update";
        }

        promotionDetails.setPromotionDetailsDiscount(Double.parseDouble(discount));
        promotionDetailsFacade.edit(promotionDetails);

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotiondetails_all.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionDetailsMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    public String deletePromotionDetails(int id) {
        promotionDetailsFacade.remove(promotionDetailsFacade.find(id));
        return "promotion_all";
    }

    public int getPromotionID() {
        return promotionID;
    }

    public void setPromotionID(int promotionID) {
        this.promotionID = promotionID;
    }

    public PromotionDetails getPromotionDetails() {
        return promotionDetails;
    }

    public void setPromotionDetails(PromotionDetails promotionDetails) {
        this.promotionDetails = promotionDetails;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getProductMessage() {
        return productMessage;
    }

    public void setProductMessage(String productMessage) {
        this.productMessage = productMessage;
    }

    public String getDiscountMessage() {
        return discountMessage;
    }

    public void setDiscountMessage(String discountMessage) {
        this.discountMessage = discountMessage;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getProductString() {
        return productString;
    }

    public void setProductString(String productString) {
        this.productString = productString;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
