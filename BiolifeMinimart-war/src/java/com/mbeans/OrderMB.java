/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Customer;
import com.entity.Invoice;
import com.entity.OrderDetails;
import com.entity.Orders;
import com.entity.Product;
import com.entity.Receiver;
import com.entity.Revenue;
import com.sessionbean.OrderDetailsFacadeLocal;
import com.sessionbean.OrdersFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author OS
 */
@Named(value = "orderMB")
@SessionScoped
public class OrderMB implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private OrderDetailsFacadeLocal orderDetailsFacade;

    @EJB
    private OrdersFacadeLocal ordersFacade;

    private BarChartModel barModel;

    private String duration = "month";

    private String typeReport = "chart";

    private String strImage;

    private String strDate = "04/22/2023 - 04/24/2023";

    private Orders order;
    private List<OrderDetails> listOdd;
    private double total = 0;
    private int orderID;

    @PostConstruct
    public void init() {
        createBarModel();
    }

    public OrderMB() {
    }

    public void showOrderDetails(int id) {
        orderID = id;
        order = ordersFacade.find(id);
        listOdd = orderDetailsFacade.findDetailsByOrderID(id);
    }

    public String showDetails(int id) {
        total = 0;
        order = ordersFacade.find(id);
        listOdd = orderDetailsFacade.findDetailsByOrderID(id);
        for (OrderDetails i : listOdd) {
            total += i.getOrderDetailsQuantity() * Double.parseDouble(i.getOrderDetailsUnitPrice() + "") * (1 - i.getOrderDetailsDiscount());
        }
        return "order_details";
    }

    public List<Orders> showAllOrder(String uname) {
        return ordersFacade.showOrder(uname);
    }

    public void showFormInsertFeedback(int id) {
        orderID = id;
        order = ordersFacade.find(id);
    }

    public void cancelOrder(int id) {
        Product p;
        Orders o = ordersFacade.find(id);
        List<OrderDetails> listOd = orderDetailsFacade.findDetailsByOrderID(o.getOrderID());
        for (OrderDetails odd : listOd) {
            p = productFacade.find(odd.getProductID().getProductID());
            p.setProductQuantity(p.getProductQuantity() + odd.getOrderDetailsQuantity());
            productFacade.edit(p);
        }
        o.setOrderStatus(5);
        ordersFacade.edit(o);
    }

    public void receivedOrder(int id) {
        Orders o = ordersFacade.find(id);
        o.setOrderStatus(4);
        ordersFacade.edit(o);
    }

    public int findLastOrder(Customer customer) {
        List<Orders> listOrder = ordersFacade.findLastOrder(customer);
        System.out.println("Order ID: " + listOrder.get(listOrder.size() - 1).getOrderID());
        return listOrder.get(listOrder.size() - 1).getOrderID();
    }

    public HashMap<String, Object> getOrderInfo(int orderID) {
        Orders o = ordersFacade.find(orderID);
        Receiver r = o.getReceiverID();
        Customer c = r.getCustomerUsername();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");

        HashMap<String, Object> info = new HashMap<String, Object>();
        info.put("orderId", o.getOrderID());
        info.put("orderDate", formatter.format(o.getOrderDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()));
        info.put("orderTotal", o.getOrderTotal());
        info.put("customerName", c.getCustomerFullname().toUpperCase());
        info.put("customerPhone", c.getCustomerPhone());
        info.put("email", c.getCustomerEmail());
        info.put("receiver", r.getReceiverName().toUpperCase());
        info.put("phone", r.getReceiverPhone());
        info.put("address", r.getReceiverAddress());

        return info;
    }

    public ArrayList<Invoice> getProductDetails(int orderID) {
        List<OrderDetails> listOrderDetails = new ArrayList<>();
        Orders o = ordersFacade.find(orderID);
        listOrderDetails = orderDetailsFacade.showOrderDetailsByOrder(o);

        ArrayList<Invoice> list = new ArrayList<>();
        if (!listOrderDetails.isEmpty()) {
            int no = 1;
            for (OrderDetails od : listOrderDetails) {
                Invoice i = new Invoice();
                Product p = od.getProductID();
                i.setNo(no++);
                i.setItemName(p.getProductName());
                i.setQty(od.getOrderDetailsQuantity());
                i.setUnitPrice("$" + od.getOrderDetailsUnitPrice().doubleValue());
                i.setDiscount((od.getOrderDetailsDiscount() * 100) + "%");
                i.setTotal(String.format("$%.2f", od.getOrderDetailsQuantity() * od.getOrderDetailsUnitPrice().doubleValue() * (1 - od.getOrderDetailsDiscount())));
                list.add(i);
            }
        }
        return list;
    }

    public void invoice(int orderID) {
        HashMap<String, Object> info = getOrderInfo(orderID);
        ArrayList<Invoice> list = getProductDetails(orderID);
        try {
            FacesContext facesContext = FacesContext.getCurrentInstance(); //Get the context ONCE
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            InputStream input = new FileInputStream(new File(((HttpServletRequest) facesContext.getExternalContext().getRequest()).getServletContext().getRealPath("") + "/invoice/bill.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);

            /*compiling jrxml with help of JasperReport class*/
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("cusName", info.get("customerName").toString());
            parameters.put("cusPhone", info.get("customerPhone").toString());
            parameters.put("cusEmail", info.get("email").toString());
            parameters.put("invoiceNo", "#" + info.get("orderId").toString());
            parameters.put("invoiceDate", info.get("orderDate").toString());
            parameters.put("totalAmount", "$" + String.format("%.2f", info.get("orderTotal")));
            parameters.put("receiver", info.get("receiver").toString());
            parameters.put("phone", info.get("phone").toString());
            parameters.put("address", info.get("address").toString());
            parameters.put("imageBio", ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getServletContext().getRealPath("") + "/resources/images/organic-3-green.png");
            parameters.put("ProductDetailParam", itemsJRBean);

            /* Using jasperReport object to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* Write content to PDF file */
            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                response.addHeader("Content-disposition", "filename=bill#" + orderID + ".pdf");
                facesContext.responseComplete();

                /* Write content to PDF file */
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("File Generated");
        } catch (FileNotFoundException | JRException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createBarModel() {
        barModel = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();

        List<Number> values = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        List<String> bgColor = new ArrayList<>();

        barDataSet.setLabel("Total amount ($)");
        List<String> borderColor = new ArrayList<>();
        DecimalFormat df2 = new DecimalFormat("###.##");
        List<Double> list = getListProfitByDay();
        String[] dateArr = strDate.split("-");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[0].trim());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[1].trim());
        } catch (ParseException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date currentDate = startDate;
        for (int i = 0; i < list.size(); i++) {
            Date d1 = currentDate;
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            c.add(Calendar.DATE, 1);
            Date d2 = c.getTime();
            labels.add(((currentDate.getMonth() + 1) > 9 ? "" : "0") + (currentDate.getMonth() + 1) + "/" + (currentDate.getDate() > 9 ? "" : "0") +  + currentDate.getDate());
            currentDate = d2;

            bgColor.add("rgb(127, 175, 81)");
            borderColor.add("rgb(127, 175, 81)");

            values.add(Double.valueOf(df2.format(list.get(i))));
        }

        barDataSet.setData(values);

        barDataSet.setBackgroundColor(bgColor);

        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        data.setLabels(labels);
        barModel.setData(data);
        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

        barModel.setOptions(options);
    }

//    public Long countTodayOrders() {
//        createBarModel();
//        
//        Date date = new Date();
//
//        date.setHours(0);
//        date.setMinutes(0);
//        date.setSeconds(0);
//
//        Long todayOrders = ordersFacade.countOrder(date);
//        return todayOrders;
//    }
    public List<Double> getListProfitByMonth() {
        List<Double> listProfitByMonth = new ArrayList<>();

        Date today = new Date();
        int month = today.getMonth() + 1;

        for (int i = 0; i < month; i++) {
            Date fisrtDateofMonth = new Date(today.getYear(), i, 1);
            Date fisrtDateofNextMonth = new Date(today.getYear(), i + 1, 1);

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByMonth(fisrtDateofMonth, fisrtDateofNextMonth);

            double todayProfit = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                todayProfit = todayProfit + calculateTotalAmount(listOrder.get(j).getOrderID());
            }
            listProfitByMonth.add(todayProfit);
        }

        return listProfitByMonth;
    }

    public List<Double> getListProfitByDay() {
        List<Double> listProfitByDay = new ArrayList<>();
        String[] dateArr = strDate.split("-");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[0].trim());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[1].trim());
        } catch (ParseException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Date currentDate = startDate;
        for (int i = 0; i < daysBetween + 1; i++) {
            Date d1 = currentDate;
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            c.add(Calendar.DATE, 1);
            Date d2 = c.getTime();
            currentDate = d2;

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByMonth(d1, d2);

            double todayProfit = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                todayProfit = todayProfit + (calculateTotalAmount(listOrder.get(j).getOrderID()) - calculateTotalDiscount(listOrder.get(j).getOrderID()));
            }
            listProfitByDay.add(todayProfit);
        }

        return listProfitByDay;
    }

    public double calculateTotalAmount(int id) {
        List<OrderDetails> listOrderDetails = orderDetailsFacade.findAll();
        double totalAmount = 0;

        for (int i = 0; i < listOrderDetails.size(); i++) {
            OrderDetails o = listOrderDetails.get(i);
            if (o.getOrderID().getOrderID() == id) {
                totalAmount = totalAmount + (o.getOrderDetailsQuantity() * o.getOrderDetailsUnitPrice().doubleValue());
            }
        }

        return totalAmount;
    }

    public double calculateTotalDiscount(int id) {
        List<OrderDetails> listOrderDetails = orderDetailsFacade.findAll();
        double totalDiscount = 0;

        for (int i = 0; i < listOrderDetails.size(); i++) {
            OrderDetails o = listOrderDetails.get(i);
            if (o.getOrderID().getOrderID() == id) {
                totalDiscount = totalDiscount + (o.getOrderDetailsQuantity() * o.getOrderDetailsUnitPrice().doubleValue() * o.getOrderDetailsDiscount());
            }
        }

        return totalDiscount;
    }

    public String changeDuration() {
        createBarModel();
        return "revenue_statistics";
    }

    public String changeTypeReport() {
        System.out.println(typeReport);
        return "revenue_statistics";
    }

    public boolean checkType() {
        if (typeReport.equals("chart")) {
            return true;
        } else {
            return false;
        }
    }

    public List<Revenue> showListRevenue() {
        if (duration.equals("year")) {
            return getListRevenueByMonth();
        } else {
            return getListRevenueByDay();
        }
    }

    public List<Revenue> getListRevenueByMonth() {
        List<Revenue> listRevenueByMonth = new ArrayList<>();

        Date today = new Date();
        int month = today.getMonth() + 1;

        for (int i = 0; i < month; i++) {
            Revenue r = new Revenue();

            Date fisrtDateofMonth = new Date(today.getYear(), i, 1);
            Date fisrtDateofNextMonth = new Date(today.getYear(), i + 1, 1);

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByMonth(fisrtDateofMonth, fisrtDateofNextMonth);
            double monthProfit = 0;
            double monthDiscount = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                monthProfit = monthProfit + calculateTotalAmount(listOrder.get(j).getOrderID());
                monthDiscount = monthDiscount + calculateTotalDiscount(listOrder.get(j).getOrderID());
            }

            String m;

            if ((i + 1) < 10) {
                m = "0" + (i + 1);
            } else {
                m = "" + (i + 1);
            }

            r.setTime("" + m + "/" + (today.getYear() + 1900));
            r.setTotalOrder(listOrder.size());
            r.setTotalAmount("$" + String.format("%.2f", monthProfit));
            r.setTotalDiscount("$" + String.format("%.2f", monthDiscount));
            r.setRevenue("$" + String.format("%.2f", (monthProfit - monthDiscount)));

            listRevenueByMonth.add(r);
        }
        return listRevenueByMonth;
    }

    public List<Revenue> getListRevenueByDay() {
        List<Revenue> listRevenueByDay = new ArrayList<>();
        
        String[] dateArr = strDate.split("-");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[0].trim());
            endDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateArr[1].trim());
        } catch (ParseException ex) {
            Logger.getLogger(PromotionMB.class.getName()).log(Level.SEVERE, null, ex);
        }

        long daysBetween = ChronoUnit.DAYS.between(startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(), endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        Date currentDate = startDate;

        Date today = new Date();
        int day = today.getDate();
        int month = today.getMonth();
        for (int i = 0; i < daysBetween + 1; i++) {
            Revenue r = new Revenue();
            Date d1 = currentDate;
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            c.add(Calendar.DATE, 1);
            Date d2 = c.getTime();

            String m;
            if ((currentDate.getMonth() + 1) < 10) {
                m = "0" + (currentDate.getMonth() + 1);
            } else {
                m = "" + (currentDate.getMonth() + 1);
            }

            String d;
            if (currentDate.getDate() < 10) {
                d = "0" + (currentDate.getDate());
            } else {
                d = "" + (currentDate.getDate());
            }
            currentDate = d2;

            List<Orders> listOrder = new ArrayList<>();
            listOrder = ordersFacade.getOrdersByMonth(d1, d2);

            double todayProfit = 0;
            double todayDiscount = 0;
            for (int j = 0; j < listOrder.size(); j++) {
                todayProfit = todayProfit + calculateTotalAmount(listOrder.get(j).getOrderID());
                todayDiscount = todayDiscount + calculateTotalDiscount(listOrder.get(j).getOrderID());
            }

            r.setTime("" + m + "/" + d + "/" + (today.getYear() + 1900));
            r.setTotalOrder(listOrder.size());
            r.setTotalAmount("$" + String.format("%.2f", todayProfit));
            r.setTotalDiscount("$" + String.format("%.2f", todayDiscount));
            r.setRevenue("$" + String.format("%.2f", (todayProfit - todayDiscount)));

            listRevenueByDay.add(r);
        }
        return listRevenueByDay;
    }

    public void resetRevenueReport() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        String dateStart = new SimpleDateFormat("MM/dd/yyyy").format(c.getTime());
        String dateEnd = new SimpleDateFormat("MM/dd/yyyy").format(new Date());

        strDate = dateStart + " - " + dateEnd;
        System.out.println(strDate);
        duration = "month";
        typeReport = "chart";
        createBarModel();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("revenue_statistics.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportReport() {
        byte[] decodedBytes = Base64
                .getDecoder()
                .decode(strImage);

        ArrayList<Revenue> list = (ArrayList<Revenue>) showListRevenue();
        String title;
        String chartTitle;
        String date = "From " + list.get(0).getTime() + " to " + list.get(list.size() - 1).getTime();

        double totalRevenue = 0;

        for (int i = 0; i < list.size(); i++) {
            double revenue = Double.valueOf(list.get(i).getRevenue().substring(1));
            totalRevenue = totalRevenue + revenue;
        }

        if (duration.equals("year")) {
            title = "Revenue report by year";
            chartTitle = "Revenue chart by year";
        } else {
            title = "Revenue report";
            chartTitle = "Revenue chart";
        }

        try {
            FacesContext facesContext = FacesContext.getCurrentInstance(); //Get the context ONCE
            HttpServletResponse response = (HttpServletResponse) facesContext.getExternalContext().getResponse();
            InputStream input = new FileInputStream(new File(((HttpServletRequest) facesContext.getExternalContext().getRequest()).getServletContext().getRealPath("") + "/invoice/report.jrxml"));
            JasperDesign jasperDesign = JRXmlLoader.load(input);

            /*compiling jrxml with help of JasperReport class*/
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);

            /* Convert List to JRBeanCollectionDataSource */
            JRBeanCollectionDataSource itemsJRBean = new JRBeanCollectionDataSource(list);

            /* Map to hold Jasper report Parameters */
            Map<String, Object> parameters = new HashMap<String, Object>();
            parameters.put("totalRevenue", "$" + String.format("%.2f", totalRevenue));
            parameters.put("imageBio", new ByteArrayInputStream(decodedBytes));
            parameters.put("title", title);
            parameters.put("chartTitle", chartTitle);
            parameters.put("date", date);
            parameters.put("ProductDetailParam", itemsJRBean);

            /* Using jasperReport object to generate PDF */
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            /* Write content to PDF file */
            try {
                ServletOutputStream servletOutputStream = response.getOutputStream();
                response.addHeader("Content-disposition", "filename=report.pdf");
                facesContext.responseComplete();

                /* Write content to PDF file */
                JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

                servletOutputStream.flush();
                servletOutputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
            }

            System.out.println("File Generated");
        } catch (FileNotFoundException | JRException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Long countTodayOrders() {
        createBarModel();
        
        Date date = new Date();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        Long todayOrders = ordersFacade.countOrder(date);
        return todayOrders;
    }

    public double calculateTodayProfit() {
        Date date = new Date();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        List<Orders> listOrder = new ArrayList<>();
        listOrder = ordersFacade.getOrdersByDate(date, new Date(date.getTime() + (1000 * 60 * 60 * 24)));
        System.out.println(listOrder.size());
        double todayProfit = 0;

        for (int i = 0; i < listOrder.size(); i++) {
            todayProfit = todayProfit + calculateTotalAmount(listOrder.get(i).getOrderID());
        }

        return todayProfit;
    }
    
    public double calculateTodayImport() {
        Date date = new Date();

        date.setHours(0);
        date.setMinutes(0);
        date.setSeconds(0);

        List<Orders> listOrder = new ArrayList<>();
        listOrder = ordersFacade.getOrdersByDate(date, new Date(date.getTime() + (1000 * 60 * 60 * 24)));
        System.out.println(listOrder.size());
        double todayProfit = 0;

        for (int i = 0; i < listOrder.size(); i++) {
            todayProfit = todayProfit + calculateImport(listOrder.get(i).getOrderID());
        }

        return todayProfit;
    }
    
    public double calculateImport(int id) {
        List<OrderDetails> listOrderDetails = orderDetailsFacade.findAll();
        double totalImport = 0;

        for (int i = 0; i < listOrderDetails.size(); i++) {
            OrderDetails o = listOrderDetails.get(i);
            if (o.getOrderID().getOrderID() == id) {
                totalImport = totalImport + (o.getOrderDetailsQuantity() * o.getProductID().getProductImportPrice().doubleValue());
            }
        }

        return totalImport;
    }

    public List<Orders> showAllOrders() {
        return ordersFacade.findAll();
    }

    public String formatStatus(int id) {
        order = ordersFacade.find(id);
        switch (order.getOrderStatus()) {
            case 0:
                return "Unconfirmed";
            case 1:
                return "Confirmed";
            case 2:
                return "Delivering";
            case 3:
                return "Delivered";
            case 4:
                return "Received";
            default:
                return "Cancelled";
        }
    }

    public String buttonStatus(int id) {
        order = ordersFacade.find(id);
        switch (order.getOrderStatus()) {
            case 0:
                return "badge-secondary";
            case 1:
                return "badge-info";
            case 2:
                return "badge-warning";
            case 3:
                return "badge-danger";
            case 4:
                return "badge-success";
            default:
                return "badge-dark";
        }
    }

    public boolean disableButtonStatus(int id) {
        order = ordersFacade.find(id);
        if (order.getOrderStatus() == 3 || order.getOrderStatus() == 4 || order.getOrderStatus() == 5) {
            return true;
        }
        return false;
    }

    public void updateOrderStatus(int id) {
        try {
            order = ordersFacade.find(id);
            switch (order.getOrderStatus()) {
                case 0:
                    order.setOrderStatus(1);
                    ordersFacade.edit(order);
                    break;
                case 1:
                    order.setOrderStatus(2);
                    ordersFacade.edit(order);
                    break;
                case 2:
                    order.setOrderStatus(3);
                    order.setOrderDeliveryDate(new Date());
                    ordersFacade.edit(order);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("order_all.xhtml");
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(OrderMB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void autoUpdateOrderStatus() {
        Date startDate;
        Date endDate = new Date();
        List<Orders> listOrders = ordersFacade.findOrdersByStatus();
        for (Orders orders : listOrders) {
            startDate = orders.getOrderDeliveryDate();

            long startTime = startDate.getTime();
            long endTime = endDate.getTime();
            long diffTime = endTime - startTime;

            // Chuyển đổi khoảng cách thời gian sang đơn vị ngày
            long diffDays = diffTime / 86400000;

            if (diffDays >= 3) {
                orders.setOrderStatus(4);
                ordersFacade.edit(orders);
            }
        }
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public BarChartModel getBarModel() {
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTypeReport() {
        return typeReport;
    }

    public void setTypeReport(String typeReport) {
        this.typeReport = typeReport;
    }

    public String getStrImage() {
        return strImage;
    }

    public void setStrImage(String strImage) {
        this.strImage = strImage;
    }

    public List<OrderDetails> getListOdd() {
        return listOdd;
    }

    public void setListOdd(List<OrderDetails> listOdd) {
        this.listOdd = listOdd;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

}
