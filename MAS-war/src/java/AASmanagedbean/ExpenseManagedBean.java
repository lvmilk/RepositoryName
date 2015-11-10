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
import java.util.HashMap;
import java.util.List;
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
     
    private long year;
    private String quarter;
    private List<Expense> expenseList = new ArrayList<>();
    
    public ExpenseManagedBean() {
    }
    
    public void calculateFixedCost() throws IOException {
        if (quarter.equals("0") || year == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please select year and quarter ! "));
        } else {
            

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
    
    
}
