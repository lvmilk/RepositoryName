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
import javax.servlet.http.HttpSession;

/**
 *
 * @author wang
 */
@Named(value = "vmlmb")
@ViewScoped
public class ViewMyLeaveManagedBean implements Serializable {

    @EJB
    LeaveBeanLocal lb;
    private List<StaffLeave> allLeaves = new ArrayList<StaffLeave>();
    private String userName;

    @PostConstruct
    public void init() {
        this.displayAllLeaves();
    }

    public void displayAllLeaves() {

        try {
            userName = this.getUserName();
            allLeaves = lb.getOneStaffLeaves(userName);
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public String getUserName() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        return session.getAttribute("username").toString();
    }

    public List<StaffLeave> getAllLeaves() {
        return allLeaves;
    }

    public void setAllLeaves(List<StaffLeave> allLeaves) {
        this.allLeaves = allLeaves;
    }

    public void goBack() throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMyLeave.xhtml");

    }

    /**
     * Creates a new instance of ViewMyLeaveManagedBean
     */
    public ViewMyLeaveManagedBean() {
    }

}
