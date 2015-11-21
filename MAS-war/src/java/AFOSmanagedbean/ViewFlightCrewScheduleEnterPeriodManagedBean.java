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
@Named(value = "VFCSEPMB")
@ViewScoped
public class ViewFlightCrewScheduleEnterPeriodManagedBean implements Serializable {

    private Date startViewScheduleDate;
    private Date endViewScheduleDate;

    public ViewFlightCrewScheduleEnterPeriodManagedBean() {
    }

    public void viewFlightCrewScheduleEnterPeriod() throws Exception {
//        if (startViewScheduleDate.before(new Date())) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : Start date should be after today.", ""));
//        } else 
        if (endViewScheduleDate.before(startViewScheduleDate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : End date should be after start date.", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewScheduleDate", startViewScheduleDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewScheduleDate", endViewScheduleDate);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrewSchedule.xhtml");
        }
    }

    public Date getStartViewScheduleDate() {
        return startViewScheduleDate;
    }

    public void setStartViewScheduleDate(Date startViewScheduleDate) {
        this.startViewScheduleDate = startViewScheduleDate;
    }

    public Date getEndViewScheduleDate() {
        return endViewScheduleDate;
    }

    public void setEndViewScheduleDate(Date endViewScheduleDate) {
        this.endViewScheduleDate = endViewScheduleDate;
    }

}
