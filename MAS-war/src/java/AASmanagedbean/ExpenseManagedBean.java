/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Expense;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "EMB")
@ViewScoped
public class ExpenseManagedBean implements Serializable {

    /**
     * Creates a new instance of ExpenseManagedBean
     */
    @EJB
    private FinancialTrackingBeanLocal ftb;

    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private long year;
    private String quarter;
    private List<Expense> expenseList = new ArrayList<>();
    private List<String> categoryList = new ArrayList<>();
    private Map<String, Double> typeMap;
    private Map<String, Double> payableMap;
    private String category;
    private String type;
    private Double payable;
    private Double total;

    public ExpenseManagedBean() {
    }

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        categoryList.add("Fuel Cost");
        categoryList.add("Purchase Cost");
        categoryList.add("Depreciation Cost");
        categoryList.add("Maintenance Cost");
        categoryList.add("Other Cost");
        typeMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("typeMap");
        payableMap = (Map<String, Double>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("payableMap");
        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        expenseList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("expenseList");
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter") != null) {
            year = (long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("year");
            quarter = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("quarter");
        } else {
            year = 0;
            quarter = "0";
        }
    }

    public void calculateFixedCost() throws IOException {
        if (quarter.equals("0") || year == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            typeMap = new HashMap<String, Double>();
            payableMap = new HashMap<String, Double>();
            total = 0.0;
            for (int i = 0; i < categoryList.size(); i++) {
                payable = 0.0;
                category = categoryList.get(i);
                
              
            }
            if (total == 0.0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "There is no record found in Year " + year + " Quarter " + quarter + " ! ", ""));
            } else {
                expenseList = ftb.getExpenseList(year, quarter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("year", year);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("quarter", quarter);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("typeMap", typeMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("payableMap", payableMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("expenseList", expenseList);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./displayExpenseReport.xhtml");
            }
        }
    }

    public List<Expense> getExpenseList() {
        return expenseList;
    }

    public void setExpenseList(List<Expense> expenseList) {
        this.expenseList = expenseList;
    }

    public List<String> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<String> categoryList) {
        this.categoryList = categoryList;
    }

    public Map<String, Double> getTypeMap() {
        return typeMap;
    }

    public void setTypeMap(Map<String, Double> typeMap) {
        this.typeMap = typeMap;
    }

    public Map<String, Double> getPayableMap() {
        return payableMap;
    }

    public void setPayableMap(Map<String, Double> payableMap) {
        this.payableMap = payableMap;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPayable() {
        return payable;
    }

    public void setPayable(Double payable) {
        this.payable = payable;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public long getYear() {
        return year;
    }

    public void setYear(long year) {
        this.year = year;
    }

    public String getQuarter() {
        return quarter;
    }

    public void setQuarter(String quarter) {
        this.quarter = quarter;
    }

    public List<Long> getYearList() {
        return yearList;
    }

    public void setYearList(List<Long> yearList) {
        this.yearList = yearList;
    }

    public long getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(long currentYear) {
        this.currentYear = currentYear;
    }

}
