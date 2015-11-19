/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "PSMB")
@ViewScoped
public class PersonalScheduleManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    private String username;

    public PersonalScheduleManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null) {
            username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
        }
    }

    public void viewSchedule() throws IOException {
        if (csb.getCrewType(username).equalsIgnoreCase("Cockpit") || csb.getCrewType(username).equalsIgnoreCase("Cabin")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCrewScheduleCC.xhtml");

        } else if (csb.getCrewType(username).equalsIgnoreCase("Ground")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCrewScheduleGS.xhtml");
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    
}
