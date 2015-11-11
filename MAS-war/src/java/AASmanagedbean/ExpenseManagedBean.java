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
public class ExpenseManagedBean implements Serializable{

    /**
     * Creates a new instance of ExpenseManagedBean
     */
     @EJB
    private FinancialTrackingBeanLocal ftb;
     
    private List<Long> yearList = new ArrayList<>();
    long currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private long year;
    private String quarter;
    private List<Expense> fixedCostList = new ArrayList<>();
    private List<Expense> sunkCostList = new ArrayList<>();
    
    public ExpenseManagedBean() {
    }
    
    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            yearList.add(currentYear);
            currentYear--;
        }
        fixedCostList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fixedCostList");
        sunkCostList = (List<Expense>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sunkCostList");
    }
    
    public void calculateFixedCost() throws IOException {
        if (quarter.equals("0") || year == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            fixedCostList = ftb.getAllExpense("Fixed Operation");
            sunkCostList = ftb.getAllExpense("Sunk");
            }
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

    public List<Expense> getFixedCostList() {
        return fixedCostList;
    }

    public void setFixedCostList(List<Expense> fixedCostList) {
        this.fixedCostList = fixedCostList;
    }

    public List<Expense> getSunkCostList() {
        return sunkCostList;
    }

    public void setSunkCostList(List<Expense> sunkCostList) {
        this.sunkCostList = sunkCostList;
    }
    
    
}
