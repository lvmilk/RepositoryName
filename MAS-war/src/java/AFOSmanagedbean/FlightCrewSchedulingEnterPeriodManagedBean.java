/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Named(value = "FCSEPMB")
@ViewScoped
public class FlightCrewSchedulingEnterPeriodManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;

    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;

    public FlightCrewSchedulingEnterPeriodManagedBean() {
    }

    public void flightCrewSchdulingForPeriod() throws Exception {
        if (startDate.before(new Date())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : Crew scheduling start date should be after today.", ""));
        } else if (endDate.before(startDate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occured : Crew scheduling end date should be after start date.", ""));
        } else {
            csb.scheduleFlightCrew(startDate, endDate);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            startDateString = df.format(startDate);
            endDateString = df.format(endDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDate", startDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDate", endDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDateString", startDateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDateString", endDateString);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./scheduleFlightCrewSuccess.xhtml");
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

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

}
