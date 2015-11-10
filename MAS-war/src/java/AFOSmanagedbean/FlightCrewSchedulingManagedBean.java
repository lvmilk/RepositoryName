/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "FCSMB")
@ViewScoped
public class FlightCrewSchedulingManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;
    @EJB
    private FlightSchedulingBeanLocal fsb;

    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private Date startViewDate;
    private Date endViewDate;
    private Date startViewScheduleDate;
    private Date endViewScheduleDate;

    public FlightCrewSchedulingManagedBean() {
    }

    @PostConstruct
    public void init() {
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = df.format(startDate);
        endDateString = df.format(endDate);
    }

//    public void flightCrewSchdulingForPeriod() throws Exception {
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//        startDateString = df.format(startDate);
//        endDateString = df.format(endDate);
//        csb.scheduleFlightCrew(startDate, endDate);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDateString", startDateString);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDateString", endDateString);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDate", startDate);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDate", endDate);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("./scheduleFlightCrewSuccess.xhtml");
//    }
    
    public void viewFiCrewArrangement() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewDate", startDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewDate", endDate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrew.xhtml");
    }

    public void viewFiCrewArrangementEnterPeriod() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewDate", startViewDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewDate", endViewDate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFiCrew.xhtml");
    }

    public void viewFlightCrewSchedule() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewScheduleDate", startDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewScheduleDate", endDate);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrewSchedule.xhtml");
    }

//    public void viewFlightCrewScheduleEnterPeriod() throws Exception {
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startViewScheduleDate", startViewScheduleDate);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endViewScheduleDate", endViewScheduleDate);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrewSchedule.xhtml");
//    }

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
