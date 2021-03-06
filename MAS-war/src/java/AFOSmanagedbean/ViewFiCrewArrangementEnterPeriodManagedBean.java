/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import java.io.Serializable;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VFCAEPMB")
@ViewScoped
public class ViewFiCrewArrangementEnterPeriodManagedBean implements Serializable {

    private Date startViewDate;
    private Date endViewDate;

    public ViewFiCrewArrangementEnterPeriodManagedBean() {
    }

    public void viewFiCrewArrangementEnterPeriod() throws Exception {
        if (startViewDate.before(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : Crew scheduling start date should be after today.", ""));
        } else if (endViewDate.before(startViewDate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : Crew scheduling end date should be after start date.", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewDate", startViewDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewDate", endViewDate);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrew.xhtml");
        }
    }

    public Date getStartViewDate() {
        return startViewDate;
    }

    public void setStartViewDate(Date startViewDate) {
        this.startViewDate = startViewDate;
    }

    public Date getEndViewDate() {
        return endViewDate;
    }

    public void setEndViewDate(Date endViewDate) {
        this.endViewDate = endViewDate;
    }

}
