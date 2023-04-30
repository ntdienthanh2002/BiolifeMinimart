/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Product;
import com.entity.Promotion;
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
import javax.faces.context.FacesContext;

/**
 *
 * @author OS
 */
@Named(value = "promotionMB")
@SessionScoped
public class PromotionMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private PromotionDetailsFacadeLocal promotionDetailsFacade;

    @EJB
    private PromotionFacadeLocal promotionFacade;

    private Promotion promotion;

    private String name;

    private String date;

    private String nameMessage;

    private String dateMessage;

    public PromotionMB() {
        promotion = new Promotion();
    }

//    public List<Promotion> showTodayPromotions() {
//        return promotionFacade.getTodayPromotions();
//    }

    public List<PromotionDetails> showTodayPromotionDetails() {
        List<Object[]> list = promotionFacade.getTodayPromotions();
        List<Product> listProduct = new ArrayList<>();
        List<PromotionDetails> listPromotionDetails = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Object[] obj = list.get(i);
            Product p = (Product) obj[0];
            double discountPro = (double) obj[1];
            listPromotionDetails.add(promotionDetailsFacade.findPromotionDetails(p, discountPro));
        }

//        System.out.println("Count Promotion: " + listPromotion.size());
//        List<PromotionDetails> listPromotionDetails = new ArrayList<>();
//
//        for (int i = 0; i < listPromotion.size(); i++) {
//            System.out.println(listPromotion.get(i).getPromotionDetailsCollection().size());
//            List<PromotionDetails> temp = (List<PromotionDetails>) promotionFacade.find(listPromotion.get(i).getPromotionID()).getPromotionDetailsCollection();
//            listPromotionDetails.addAll(temp);
//        }

        return listPromotionDetails;
    }

    public String convertDateString(Date date) {
        System.out.println((date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate());
        return (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + date.getDate();
    }

    public double discountPrice(int id) {
        List<PromotionDetails> list = promotionDetailsFacade.getTodayDiscountProduct(productFacade.find(id));
        double unitPrice;
        if (list.size() > 0) {
            unitPrice = list.get(0).getProductID().getProductUnitPrice().doubleValue();
            return unitPrice * (1 - list.get(0).getPromotionDetailsDiscount());
        } else {
            unitPrice = productFacade.find(id).getProductUnitPrice().doubleValue();
            return unitPrice;
        }
    }

    public Object noDiscountPrice(int id) {
        List<PromotionDetails> list = promotionDetailsFacade.getTodayDiscountProduct(productFacade.find(id));
        double unitPrice;
        if (list.size() > 0) {
            unitPrice = list.get(0).getProductID().getProductUnitPrice().doubleValue();
            return unitPrice;
        } else {
            return null;
        }
    }

    public List<Promotion> showAllPromotion() {
        return promotionFacade.findAll();
    }

    public int countPromotion(int index) {
        return promotionFacade.findAll().size() - index;
    }

    public void resetMessage() {
        dateMessage = "";
        nameMessage = "";
    }

    public void loadInsertForm() {
        promotion = new Promotion();
        resetMessage();

        String dateStart = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        String dateEnd = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        name = "";
        date = dateStart + " - " + dateEnd;

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotion_create.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String insertPromotion() {
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);

        if (!checkPromotionForm()) {
            return "promotion_create";
        }

        String[] dateArr = date.split("-");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[0].trim());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[1].trim());
        } catch (ParseException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        promotion.setPromotionName(name);
        promotion.setPromotionStart(startDate);
        promotion.setPromotionEnd(endDate);

        promotionFacade.create(promotion);

        promotion = new Promotion();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotion_all.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String loadUpdateForm(int id) {
        promotion = promotionFacade.find(id);
        resetMessage();

        String dateStart = new SimpleDateFormat("MM/dd/yyyy").format(promotion.getPromotionStart());
        String dateEnd = new SimpleDateFormat("MM/dd/yyyy").format(promotion.getPromotionEnd());

        name = promotion.getPromotionName();
        date = dateStart + " - " + dateEnd;

        return "promotion_update";
    }

    public boolean checkPromotionForm() {
        boolean valid = true;
        if (name.equals("")) {
            nameMessage = "This field is required";
            valid = false;
        } else {
            nameMessage = "";
        }

        return valid;
    }

    public String updatePromotion() {
        System.out.println("Name: " + name);
        System.out.println("Date: " + date);

        if (!checkPromotionForm()) {
            return "promotion_update";
        }

        String[] dateArr = date.split("-");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[0].trim());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[1].trim());
        } catch (ParseException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        promotion.setPromotionName(name);
        promotion.setPromotionStart(startDate);
        promotion.setPromotionEnd(endDate);

        promotionFacade.edit(promotion);

        promotion = new Promotion();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("promotion_all.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public String deletePromotion(int id) {
        List<PromotionDetails> list = promotionDetailsFacade.findAll();
        List<PromotionDetails> listPromotionDetails = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getPromotionID().getPromotionID() == id) {
                promotionDetailsFacade.remove(list.get(i));
            }
        }
        promotionFacade.remove(promotionFacade.find(id));
        return "promotion_all";
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNameMessage() {
        return nameMessage;
    }

    public void setNameMessage(String nameMessage) {
        this.nameMessage = nameMessage;
    }

    public String getDateMessage() {
        return dateMessage;
    }

    public void setDateMessage(String dateMessage) {
        this.dateMessage = dateMessage;
    }
}
