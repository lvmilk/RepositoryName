/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AASmanagedbean;

import Entity.AAS.Payroll;
import SessionBean.AAS.FinancialTrackingBeanLocal;
import SessionBean.AAS.ResourceTrackingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xi
 */
@Named(value = "PPMB")
@ViewScoped
public class PersonalPayrollManagedBean implements Serializable {

    /**
     * Creates a new instance of PersonalPayrollManagedBean
     */
    @EJB
    private ResourceTrackingBeanLocal rtb;

    long currentYear = Calendar.getInstance().get(Calendar.YEAR);
    private String name;

    private Payroll payroll;
    private Double salary;
    private Double bonus;
    private Integer leave;
    private Double total;
    private String totalString;

    public PersonalPayrollManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("name") != null) {
            name = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("name");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Staff Id", "Please enter an existing staff ID !"));
        }
        salary = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("salary");
        bonus = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bonus");
        leave = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leave");
        total = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("total");
        totalString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalString");
    }

    public void viewOnePayroll() throws IOException, Exception {
        if (name == null) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please enter staff ID ! "));
        } else {
            try {
                payroll = rtb.getOnePayroll(name);
                salary = payroll.getSalary();
                bonus = payroll.getBonus();
                Double totalBonus = 0.0;
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                Calendar cal = new GregorianCalendar((int) year, month, 1);
                int dayOfMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

                leave = rtb.getLeaveAmount(name, year, month + 1);
                totalBonus = rtb.getTotalBonus(name, year, month + 1);
                total = (1 - ((double) leave / (double) dayOfMonth)) * salary + totalBonus;

                NumberFormat formatter = new DecimalFormat("#0.00");
                totalString = formatter.format(total);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("name", name);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("salary", salary);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bonus", bonus);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leave", leave);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("total", total);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalString", totalString);

                Map<String, Object> options = new HashMap<String, Object>();
                options.put("resizable", true);
                options.put("draggable", false);
                options.put("modal", true);
                RequestContext.getCurrentInstance().openDialog("dialogPayroll", options, null);
            
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
            }
        }

    }

//    public void exporter() {
//        Map<String, Object> options = new HashMap<String, Object>();
//        options.put("resizable", true);
//        options.put("draggable", false);
//        options.put("modal", true);
//        options.put("width", 700);
//        options.put("height", 500);
//        RequestContext.getCurrentInstance().openDialog("dialogPayroll", options, null);
//    }
    public long getCurrentYear() {
        return currentYear;
    }

    public void setCurrentYear(long currentYear) {
        this.currentYear = currentYear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    public Payroll getPayroll() {
        return payroll;
    }

    public void setPayroll(Payroll payroll) {
        this.payroll = payroll;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Double getBonus() {
        return bonus;
    }

    public void setBonus(Double bonus) {
        this.bonus = bonus;
    }

    public Integer getLeave() {
        return leave;
    }

    public void setLeave(Integer leave) {
        this.leave = leave;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

}
