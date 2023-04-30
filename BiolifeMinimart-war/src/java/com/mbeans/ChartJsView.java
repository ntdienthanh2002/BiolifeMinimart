/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package com.mbeans;

import com.entity.Category;
import com.sessionbean.CategoryFacadeLocal;
import com.sessionbean.OrdersFacadeLocal;
import com.sessionbean.ProductFacadeLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
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
 * @author PC
 */
@Named(value = "chartJsView")
@SessionScoped
public class ChartJsView implements Serializable {

    @EJB
    private ProductFacadeLocal productFacade;

    @EJB
    private CategoryFacadeLocal categoryFacade;

    private Category category;

    private BarChartModel inventory;
    
    private int idCategory;
    
    private int selectYear;
    private int selectMonth;
    @EJB
    private OrdersFacadeLocal ordersFacade;

    private BarChartModel deliveryStatus;

    private List<String> listYear;
    private List<String> listMonth;

    @PostConstruct
    public void init() {
        createInventoryStatistic();
        createDeliveryStatusStatistic();
        addMonth();
        addYear();
    }

    public ChartJsView() {
    }

    public void addYear() {
        listYear = new ArrayList<String>();
        Date d = new Date();
        listYear.add("All year");
        for (int i = d.getYear() + 1900; i >= 2000; i--) {
            listYear.add(i + "");
        }
    }

    public void addMonth() {
        listMonth = new ArrayList<String>();
        listMonth.add("All month");
        listMonth.add("January");
        listMonth.add("February");
        listMonth.add("March");
        listMonth.add("April");
        listMonth.add("May");
        listMonth.add("June");
        listMonth.add("July");
        listMonth.add("August");
        listMonth.add("Septemper");
        listMonth.add("October");
        listMonth.add("November");
        listMonth.add("December");
    }

    public BarChartModel getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(BarChartModel deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
    
    public void resetDeliveryStatus() {
        createDeliveryStatusStatistic();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("delivery_status_statistic.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ChartJsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createDeliveryStatusStatistic() {
        ArrayList<String> listDeliveryStaus = new ArrayList<String>();
        listDeliveryStaus.add("Unconfirmed");
        listDeliveryStaus.add("Confirmed");
        listDeliveryStaus.add("Delivering");
        listDeliveryStaus.add("Delivered");
        listDeliveryStaus.add("Received");
        listDeliveryStaus.add("Cancelled");

        deliveryStatus = new BarChartModel();
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Count orders by delivery status");

        List<Number> values = new ArrayList<>();
        if (selectYear == 0) {
            for (int i = 0; i < listDeliveryStaus.size(); i++) {
                values.add(ordersFacade.countOrderByDeliveryStatus(i));
            }
        } else {
            if (selectMonth == 0) {
                for (int i = 0; i < listDeliveryStaus.size(); i++) {
                    values.add(ordersFacade.countDeliveryStatusByYear(i, Integer.parseInt(listYear.get(selectYear))));
                }
            } else {
                for (int i = 0; i < listDeliveryStaus.size(); i++) {
                    values.add(ordersFacade.countDeliveryStatusByMonth(i, Integer.parseInt(listYear.get(selectYear)), selectMonth));
                }
            }
        }
        barDataSet.setData(values);

        ArrayList<String> listColor = new ArrayList<String>();
        listColor.add("#707070");
        listColor.add("#2421DA");
        listColor.add("#FF6D4D");
        listColor.add("#FF4847");
        listColor.add("#2BC155");
        listColor.add("#B1B1B1");

        barDataSet.setBackgroundColor(listColor);

        barDataSet.setBorderColor(listColor);
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.addAll(listDeliveryStaus);
        data.setLabels(labels);
        deliveryStatus.setData(data);

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

        deliveryStatus.setOptions(options);
    }

    public void resetInventory() {
        createInventoryStatistic();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("inventory_statistic.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ChartJsView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void createInventoryStatistic() {
        inventory = new BarChartModel();
        ChartData data = new ChartData();
        
        if (idCategory == 0) {
            idCategory = categoryFacade.findFirstCategory();
        }
        
        Category cate = categoryFacade.find(idCategory);

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Number of products in stocks");

        List<Number> values = new ArrayList<>();
        values.addAll(productFacade.countProductQuantity(cate));
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        for (String background : productFacade.findProductName(cate)) {
            bgColor.add("rgb(102, 163, 43, 1)");
        }
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        for (String border : productFacade.findProductName(cate)) {
            borderColor.add("rgb(102, 163, 43)");
        }
        barDataSet.setBorderWidth(1);
        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.addAll(productFacade.findProductName(cate));
        data.setLabels(labels);
        inventory.setData(data);

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

        inventory.setOptions(options);
    }

    public BarChartModel getInventory() {
        return inventory;
    }

    public void setInventory(BarChartModel inventory) {
        this.inventory = inventory;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }
    
        public List<String> getListYear() {
        return listYear;
    }

    public void setListYear(List<String> listYear) {
        this.listYear = listYear;
    }

    public List<String> getListMonth() {
        return listMonth;
    }

    public void setListMonth(List<String> listMonth) {
        this.listMonth = listMonth;
    }

    public int getSelectYear() {
        return selectYear;
    }

    public void setSelectYear(int selectYear) {
        this.selectYear = selectYear;
    }

    public int getSelectMonth() {
        return selectMonth;
    }

    public void setSelectMonth(int selectMonth) {
        this.selectMonth = selectMonth;
    }

}
