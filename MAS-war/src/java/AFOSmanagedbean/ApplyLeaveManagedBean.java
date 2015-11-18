/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import SessionBean.AFOS.LeaveBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author wang
 */
@Named(value = "almb")
@ViewScoped
public class ApplyLeaveManagedBean implements Serializable {

    private Date firstDate;
    private Date secondDate;
    private Date thirdDate;
    private Date startDate;
    private Date endDate;
    
    private String userName;

    private Date minDate=new Date();
    
    @EJB
    LeaveBeanLocal lb;

    public void onSubmitPreference() {
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(firstDate);
            Integer week1 = c1.get(Calendar.WEEK_OF_YEAR);
            
            c1.setTime(secondDate);
            Integer week2 =c1.get(Calendar.WEEK_OF_YEAR);
            c1.setTime(thirdDate);
            Integer week3 = c1.get(Calendar.WEEK_OF_YEAR);
           System.out.println("WEEK 1 2 3: "+week1+"  "+week2+"  "+week3);
            if (week1==week2||week2==week3||week3==week1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "Please check your selections of date should be in different weeks", ""));
            } else {
                userName = this.getUserName();
                lb.addLeave(firstDate, userName);
                lb.addLeave(secondDate, userName);
                if (thirdDate != null) {
                    lb.addLeave(thirdDate, userName);
                }
                FacesContext.getCurrentInstance().getExternalContext().redirect("./applyLeaveSuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

    }
       
        public void onSubmitApplication() {
        try {
            Calendar c1 = Calendar.getInstance();
            c1.setTime(startDate);
            Integer day1 = c1.get(Calendar.DAY_OF_YEAR);
            c1.setTime(endDate);
            Integer day2 =c1.get(Calendar.DAY_OF_YEAR);
           
            Integer diff=day2-day1;
          
            if (diff>3) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "Please check the length of leave should be less than 3 days", ""));
            } else {
                userName = this.getUserName();
                lb.addNormalLeave(startDate,endDate, userName);
                
                FacesContext.getCurrentInstance().getExternalContext().redirect("./applyLeaveSuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

    }
        
      

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserName() {
        return (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");

    }

    public void goBack() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("./applyLeave.xhtml");

    }

    public Date getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(Date firstDate) {
        this.firstDate = firstDate;
    }

    public Date getSecondDate() {
        return secondDate;
    }

    public void setSecondDate(Date secondDate) {
        this.secondDate = secondDate;
    }

    public Date getThirdDate() {
        return thirdDate;
    }

    public void setThirdDate(Date thirdDate) {
        this.thirdDate = thirdDate;
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }
     
    
    /**
     *
     * Creates a new instance of applyLeaveManagedBean
     */
    public ApplyLeaveManagedBean() {
    }

}
