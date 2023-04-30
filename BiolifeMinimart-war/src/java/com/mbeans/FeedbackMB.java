/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Customer;
import com.entity.Feedback;
import com.entity.Orders;
import com.entity.Product;
import com.sessionbean.CustomerFacadeLocal;
import com.sessionbean.FeedbackFacadeLocal;
import com.sessionbean.OrdersFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author OS
 */
@Named(value = "feedbackMB")
@SessionScoped
public class FeedbackMB implements Serializable {

    @EJB
    private CustomerFacadeLocal customerFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private FeedbackFacadeLocal feedbackFacade;

    private Feedback feedback;
    private Product product;
    private int idFeedback;
    private int idProduct;
    private String contentMessage;
    private String rateMessage;
    private int star;
    private String mess;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    public FeedbackMB() {
        feedback = new Feedback();
    }

//    Display feedback by product
    public List<Feedback> showFeedbackByProduct() {
        product = productFacade.find(idProduct);
        return feedbackFacade.findFeedbackByProduct(product);
    }

//    Display rate of customer
    public String formatRate(int rate) {
        switch (rate) {
            case 1:
                return "width-20percent";
            case 2:
                return "width-40percent";
            case 3:
                return "width-60percent";
            case 4:
                return "width-80percent";
            default:
                return "width-100percent";
        }
    }

//    Display the number of feedback
    public Long showTotalFeedback() {
        product = productFacade.find(idProduct);
        return feedbackFacade.countFeedbackByProduct(product);
    }

//    Display average rate of feedback
    public Float showRateAverageTotal() {
        try {
            product = productFacade.find(idProduct);
            return (float) feedbackFacade.sumFeedbackRateByProduct(product) / showTotalFeedback();
        } catch (NullPointerException ex) {
            return 0f;
        }
    }
    
    //    Display the number of feedback
    public Long showTotalFeedbackIndex(int id) {
        product = productFacade.find(id);
        return feedbackFacade.countFeedbackByProduct(product);
    }

//    Display average rate of feedback
    public Float showRateAverageTotalIndex(int id) {
        try {
            product = productFacade.find(id);
            return (float) feedbackFacade.sumFeedbackRateByProduct(product) / showTotalFeedbackIndex(id);
        } catch (NullPointerException ex) {
            return 0f;
        }
    }

//    Format average rate of feedback (css)
    public String formatRateTotal(Float rate) {
        if (rate == 0) {
            return "width-0percent";
        } else if (rate > 0 && rate <= 0.5) {
            return "width-10percent";
        } else if (rate > 0.5 && rate <= 1) {
            return "width-20percent";
        } else if (rate > 1 && rate <= 1.5) {
            return "width-30percent";
        } else if (rate > 1.5 && rate <= 2) {
            return "width-40percent";
        } else if (rate > 2 && rate <= 2.5) {
            return "width-50percent";
        } else if (rate > 2.5 && rate <= 3) {
            return "width-60percent";
        } else if (rate > 3 && rate <= 3.5) {
            return "width-70percent";
        } else if (rate > 3.5 && rate <= 4) {
            return "width-80percent";
        } else if (rate > 4 && rate < 5) {
            return "width-90percent";
        }
        return "width-100percent";
    }

//    Display the number of feedback of each rate
    public Long showFeedbackRate(int rate) {
        product = productFacade.find(idProduct);
        return feedbackFacade.countFeedbackRateByProduct(product, rate);
    }

//    Display average rate of each rate
    public Float showRateAverageFeedback(int rate) {
        product = productFacade.find(idProduct);
        return (float) showFeedbackRate(rate) / showTotalFeedback();
    }

//    Format the number of feedback of each rate (css)
    public String formatRateFeedback(Float rate) {
        if (rate == 1) {
            return "percent width-100percent";
        } else if (rate > 0 && rate <= 0.1) {
            return "percent width-10percent";
        } else if (rate > 0.1 && rate <= 0.2) {
            return "percent width-20percent";
        } else if (rate > 0.2 && rate <= 0.3) {
            return "percent width-30percent";
        } else if (rate > 0.3 && rate <= 0.4) {
            return "percent width-40percent";
        } else if (rate > 0.4 && rate <= 0.5) {
            return "percent width-50percent";
        } else if (rate > 0.5 && rate <= 0.6) {
            return "percent width-60percent";
        } else if (rate > 0.6 && rate <= 0.7) {
            return "percent width-70percent";
        } else if (rate > 0.7 && rate <= 0.8) {
            return "percent width-80percent";
        } else if (rate > 0.8 && rate < 1) {
            return "percent width-90percent";
        }
        return "percent width-0percent";
    }

    public boolean disableButtonSend(String username) {
        if (feedbackFacade.checkCustomerBuyProduct(username, idProduct)) {
            return false;
        }
        return true;
    }
    
    public String setTitle(String username) {
        if (feedbackFacade.checkCustomerBuyProduct(username, idProduct)) {
            return "";
        }
        return "Please login and buy this product to write feedback";
    }

    public boolean checkInsertForm() {
        boolean valid = true;

        if (feedback.getFeedbackRate() < 1) {
            rateMessage = "Please choose rate";
            valid = false;
        } else {
            rateMessage = "";
        }

        if (feedback.getFeedbackContent().equals("")) {
            contentMessage = "Feedback's content cannot be empty";
            valid = false;
        } else {
            contentMessage = "";
        }

        return valid;
    }

    public void insertProductFeedback(Customer customer) {
        try {
            if (checkInsertForm()) {
                product = productFacade.find(idProduct);
                feedback.setCustomerUsername(customer);
                feedback.setProductID(product);
                feedback.setFeedbackDate(new Date());
                feedback.setFeedbackStatus(1);
                feedbackFacade.create(feedback);
                feedback.setFeedbackContent("");
                FacesContext.getCurrentInstance().getExternalContext().redirect("productdetails.xhtml");
            }
        } catch (IOException ex) {
            Logger.getLogger(FeedbackMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkDelete(String username, int id) {
        Customer customer = customerFacade.find(username);
        return feedbackFacade.checkCustomerToDeleteFeedback(customer, id);
    }

    public void deleteProductFeedback(int id) {
        feedbackFacade.remove(feedbackFacade.find(id));
    }

    public void insertFeedbackOrder(int id) {
        Orders o = ordersFacade.find(id);
        feedback.setCustomerUsername(o.getReceiverID().getCustomerUsername());
        feedback.setOrderID(o);
        feedback.setFeedbackDate(new Date());
        feedback.setFeedbackStatus(1);
        feedbackFacade.create(feedback);
        System.out.println("Start: "+ star);
        feedback = new Feedback();
        star = 0;
        mess= "success";
    }
    
    public List<Feedback> showAllFeedbackOrder(String uname){
        return feedbackFacade.showAllFeedbackByCustomer(uname);
    }
    public void deleteFeedbackOrder(int id){
        feedbackFacade.remove(feedbackFacade.find(id));
    }

    public List<Feedback> showAllFeedbacks() {
        return feedbackFacade.findAll();
    }

    public String formatStatus(int id) {
        feedback = feedbackFacade.find(id);
        if (feedback.getFeedbackStatus() == 1) {
            return "Enable";
        } else {
            return "Disable";
        }
    }

    public String buttonStatus(int id) {
        feedback = feedbackFacade.find(id);
        if (feedback.getFeedbackStatus() == 1) {
            return "badge-success";
        } else {
            return "badge-warning";
        }
    }

    public String updateFeedbackStatus(int id) {
        feedback = feedbackFacade.find(id);
        if (feedback.getFeedbackStatus() == 0) {
            feedback.setFeedbackStatus(1);
            feedbackFacade.edit(feedback);
        } else {
            feedback.setFeedbackStatus(0);
            feedbackFacade.edit(feedback);
        }
        return "feedback_all";
    }

    public Feedback getFeedback() {
        return feedback;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public int getIdFeedback() {
        return idFeedback;
    }

    public void setIdFeedback(int idFeedback) {
        this.idFeedback = idFeedback;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public String getMess() {
        return mess;
    }

    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getContentMessage() {
        return contentMessage;
    }

    public void setContentMessage(String contentMessage) {
        this.contentMessage = contentMessage;
    }

    public String getRateMessage() {
        return rateMessage;
    }

    public void setRateMessage(String rateMessage) {
        this.rateMessage = rateMessage;
    }

}
