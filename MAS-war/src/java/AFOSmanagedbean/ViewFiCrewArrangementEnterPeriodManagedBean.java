/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "viewFiCrewArrangementEnterPeriodManagedBean")
@ViewScoped
public class ViewFiCrewArrangementEnterPeriodManagedBean implements Serializable {

    private Date startViewDate;
    private Date endViewDate;

    public ViewFiCrewArrangementEnterPeriodManagedBean() {
    }

    public void viewFiCrewArrangementEnterPeriod() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewDate", startViewDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewDate", endViewDate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrew.xhtml");
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
