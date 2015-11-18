/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.StaffLeave;
import SessionBean.AFOS.LeaveBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

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
    private String userName;
  
    @EJB
    LeaveBeanLocal lb;

    public void onSubmitPreference() {
        try {
            userName = this.getUserName();
            lb.addLeave(firstDate, userName);
            lb.addLeave(secondDate, userName);
            if (thirdDate != null) {
                lb.addLeave(thirdDate, userName);
            }
            FacesContext.getCurrentInstance().getExternalContext().redirect("./applyLeaveSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

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

    /**
     *
     * Creates a new instance of applyLeaveManagedBean
     */
    public ApplyLeaveManagedBean() {
    }

}
