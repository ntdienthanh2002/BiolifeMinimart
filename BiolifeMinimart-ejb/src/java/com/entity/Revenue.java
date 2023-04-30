/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.entity;

/**
 *
 * @author PC
 */
public class Revenue {
    private String time;
    private int totalOrder;
    private String totalAmount;
    private String totalDiscount;
    private String revenue;

    public Revenue() {
    }

    public Revenue(String time, int totalOrder, String totalAmount, String totalDiscount, String revenue) {
        this.time = time;
        this.totalOrder = totalOrder;
        this.totalAmount = totalAmount;
        this.totalDiscount = totalDiscount;
        this.revenue = revenue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getTotalOrder() {
        return totalOrder;
    }

    public void setTotalOrder(int totalOrder) {
        this.totalOrder = totalOrder;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(String totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    
}
