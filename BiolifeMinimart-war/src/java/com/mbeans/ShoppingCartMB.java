/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Cart;
import com.entity.CartShopping;
import com.entity.Customer;
import com.entity.OrderDetails;
import com.entity.Orders;
import com.entity.Product;
import com.entity.PromotionDetails;
import com.entity.Receiver;
import com.sessionbean.CartFacadeLocal;
import com.sessionbean.CartSessionBeanLocal;
import com.sessionbean.CustomerFacadeLocal;
import com.sessionbean.OrderDetailsFacadeLocal;
import com.sessionbean.OrdersFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import com.sessionbean.PromotionDetailsFacadeLocal;
import com.sessionbean.ReceiverFacadeLocal;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;

/**
 *
 * @author PC
 */
@Named(value = "shoppingCartMB")
@SessionScoped
public class ShoppingCartMB implements Serializable {

    @EJB
    private CartFacadeLocal cartFacade;

    @EJB
    private CustomerFacadeLocal customerFacade;

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    @EJB
    private ReceiverFacadeLocal receiverFacade;

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private PromotionDetailsFacadeLocal promotionDetailsFacade;

    @EJB
    private CartSessionBeanLocal cartSessionBean;

    private double totalAmount;

    private int totalQuantity;

    private double totalDealAmount;

    private String payment;

    private String note;

    private Orders order;

    List<Integer> numCart = new ArrayList<>();

    private Receiver selectedReceiver;

    private Customer c;

    private int quantity;

    public ShoppingCartMB() {
        order = new Orders();
    }

    @PreDestroy
    public void destroy() {
        saveCart();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCartMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addCartProduct(int proID, int quantity, String username) {
        if (!username.equals("") || !(username.isEmpty())) {
            cartSessionBean.addCart(proID, quantity);
            calculateCart();
        }
    }

    public void calculateCart() {
        totalAmount = 0;
        totalQuantity = 0;
        totalDealAmount = 0;
        List<CartShopping> listCart = new ArrayList<>();
        Set<Map.Entry<Integer, Integer>> setCart = cartSessionBean.showCart().entrySet();
        if (setCart.size() > 0) {
            for (Map.Entry<Integer, Integer> e : setCart) {
                int id = e.getKey();
                Integer q = e.getValue();
                Product p = productFacade.find(id);
                double unitPrice = p.getProductUnitPrice().doubleValue();
                totalAmount += q * unitPrice;
                totalQuantity += q;
                totalDealAmount += q * unitPrice * (1 - dealDiscount(id));
            }
        }
    }

    public List<CartShopping> showCart() {
        List<CartShopping> listCart = new ArrayList<>();
        Set<Map.Entry<Integer, Integer>> setCart = cartSessionBean.showCart().entrySet();
        totalAmount = 0;
        totalQuantity = 0;
        totalDealAmount = 0;
        for (Map.Entry<Integer, Integer> e : setCart) {
            int id = e.getKey();
            Integer q = e.getValue();
            Product p = productFacade.find(id);
            double unitPrice = p.getProductUnitPrice().doubleValue();
            CartShopping cShop = new CartShopping(id, p.getProductName(), q, unitPrice, q * unitPrice, p.getProductImage());
            listCart.add(cShop);
            numCart.add(q);
            totalAmount += q * unitPrice;
            totalQuantity += q;
            totalDealAmount += q * unitPrice * (1 - dealDiscount(id));
        }
        return listCart;
    }

    public double dealDiscount(int id) {
        List<PromotionDetails> list = promotionDetailsFacade.getTodayDiscountProduct(productFacade.find(id));
        double discount;
        if (list.size() > 0) {
            discount = list.get(0).getPromotionDetailsDiscount();
        } else {
            discount = 0;
        }
        return discount;
    }

    public Object dealTotalAmount(Object dealprice, int quantity) {
        if (dealprice == null) {
            return null;
        } else {
            return Double.parseDouble(dealprice.toString()) * quantity;
        }
    }

    public int countCart() {
        return cartSessionBean.countCart();
    }

    public void updateCart(int id, boolean flag) {
        cartSessionBean.updateCart(id, flag, productFacade.find(id).getProductQuantity());
        calculateCart();
    }

    public String removeCart(int id) {
        cartSessionBean.removeCart(id);
        calculateCart();
        System.out.println(totalQuantity);
        return "shoppingcart";
    }

    public String emptyCart() {
        cartSessionBean.emptyCart();
        calculateCart();
        return "shoppingcart";
    }

    public void loadCheckOut(Customer customer) {
        payment = "cash";
        selectedReceiver = receiverFacade.showDefaultReceiver(customer);
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("checkout.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCartMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadShoppingcart() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("shoppingcart.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCartMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String formatPrice(double price) {
        return String.format("$%.2f", price);
    }

    public void placeOrder() {
        order = new Orders();
        order.setReceiverID(selectedReceiver);
        System.out.println("Default receiver: " + selectedReceiver.getReceiverID());
        order.setOrderDate(new Date());
        order.setOrderStatus(0);
        order.setOrderNote(note);
        order.setOrderDeliveryDate(null);
        order.setOrderTotal(BigDecimal.valueOf(totalDealAmount));
        ordersFacade.create(order);

        List<CartShopping> listCart = showCart();
        for (int i = 0; i < listCart.size(); i++) {
            OrderDetails details = new OrderDetails();
            details.setOrderID(order);
            details.setProductID(productFacade.find(listCart.get(i).getProID()));
            details.setOrderDetailsQuantity(listCart.get(i).getQuantity());
            details.setOrderDetailsUnitPrice(BigDecimal.valueOf(listCart.get(i).getUnitPrice()));
            details.setOrderDetailsDiscount(dealDiscount(listCart.get(i).getProID()));

            orderDetailsFacade.create(details);

            Product p = productFacade.find(listCart.get(i).getProID());
            p.setProductQuantity(p.getProductQuantity() - listCart.get(i).getQuantity());
            productFacade.edit(p);
        }

        cartSessionBean.emptyCart();
        calculateCart();

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("thankyou.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ShoppingCartMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveCart() {
        System.out.println("save cart");
        List<CartShopping> listCart = new ArrayList<>();
        listCart = showCart();
        for (int i = 0; i < listCart.size(); i++) {
            Cart cart = new Cart();
            cart.setCartQuantity(listCart.get(i).getQuantity());
            cart.setCustomerUsername(customerFacade.find(c.getCustomerUsername()));
            cart.setProductID(productFacade.find(listCart.get(i).getProID()));

            cartFacade.create(cart);
        }

        cartSessionBean.emptyCart();
        showCart();
    }

    public void loadCart(Customer customer) {
        c = customer;
        System.out.println(customer.getCustomerUsername());
        if (customer.getCustomerUsername() != null) {
            List<Cart> listCart = cartFacade.showCartByCustomerID(customerFacade.find(customer.getCustomerUsername()));
            for (int i = 0; i < listCart.size(); i++) {
                Cart cart = listCart.get(i);
                cartSessionBean.addCart(cart.getProductID().getProductID(), cart.getCartQuantity());
                cartFacade.remove(cart);
            }
            showCart();
        }
    }

    public String changeAddress() {
        return "checkout";
    }
    
    public void resetQuantity() {
        quantity = 1;
    }

    public void updateQuantity(int id, boolean flag) {
        int max = productFacade.find(id).getProductQuantity();
        if (flag == true) {
            if (quantity < max) {
                quantity = quantity + 1;
            }
        } else {
            if (quantity > 1) {
                quantity = quantity - 1;
            }
        }
    }
    
    public void addCart(int proID, String username) {
        if (!username.equals("") || !(username.isEmpty())) {
            cartSessionBean.addCart(proID, quantity);
            calculateCart();
        }
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public double getTotalDealAmount() {
        return totalDealAmount;
    }

    public void setTotalDealAmount(double totalDealAmount) {
        this.totalDealAmount = totalDealAmount;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Receiver getSelectedReceiver() {
        return selectedReceiver;
    }

    public void setSelectedReceiver(Receiver selectedReceiver) {
        this.selectedReceiver = selectedReceiver;
    }

    public Customer getC() {
        return c;
    }

    public void setC(Customer c) {
        this.c = c;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
