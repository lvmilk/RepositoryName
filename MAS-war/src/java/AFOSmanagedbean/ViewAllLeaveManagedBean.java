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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author wang
 */
@Named(value = "valmb")
@ViewScoped
public class ViewAllLeaveManagedBean implements Serializable {

    @EJB
    LeaveBeanLocal lb;
    private List<StaffLeave> allLeaves = new ArrayList<StaffLeave>();

    @PostConstruct
    public void init() {
        this.checkAccessRight();
        this.displayAllLeaves();
    }

    public void checkAccessRight() {
        try {
            String username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
            System.out.println("StaffLevel: " + username);
            String staffLevel = lb.getStaffLevel(username);
            System.out.println("StaffLevel: " + staffLevel);
            if (!staffLevel.equals("HR")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./errorPage.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void displayAllLeaves() {
        try {
            allLeaves = lb.getAllLeaves();
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public List<StaffLeave> getAllLeaves() {
        return allLeaves;
    }

    public void setAllLeaves(List<StaffLeave> allLeaves) {
        this.allLeaves = allLeaves;
    }

    public void onApproveLeave(ActionEvent event) {
        try {
            StaffLeave leave = (StaffLeave) event.getComponent().getAttributes().get("lv");
            lb.approveLeave(leave);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./leaveStatusChangeSuccess.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

    }

    public void onRejectLeave(ActionEvent event) {
        try {
            StaffLeave leave = (StaffLeave) event.getComponent().getAttributes().get("lv");
            lb.rejectLeave(leave);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./leaveStatusChangeSuccess.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

    }

    public void goBack() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAllLeave.xhtml");

    }

//    public void goBack2() throws IOException {
//
//        FacesContext.getCurrentInstance().getExternalContext().redirect("./AFOSworkspace.xhtml");
//
//    }

    /**
     * Creates a new instance of ViewAllLeaveManagedBean
     */
    public ViewAllLeaveManagedBean() {
    }

}
