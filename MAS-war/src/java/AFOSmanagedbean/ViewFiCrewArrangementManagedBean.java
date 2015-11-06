/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VFCAMB")
@ViewScoped
public class ViewFiCrewArrangementManagedBean implements Serializable {

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
    private List<FlightInstance> fiList;
    private List<CabinCrew> cabinList;
    private List<CockpitCrew> cockpitList;

        public ViewFiCrewArrangementManagedBean() {
    }

    
    @PostConstruct
    public void init() {
        startViewDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startViewDate");
        endViewDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endViewDate");
        fiList = fsb.getSortedFiWithinPeriod(startViewDate, endViewDate);
       
    }

    public void flightCrewSchdulingForPeriod() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = df.format(startDate);
        endDateString = df.format(endDate);
        csb.scheduleFlightCrew(startDate, endDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDateString", startDateString);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDateString", endDateString);
    }

    public void viewCabinCrewSchedule() {
        
    
    }
    
    public void viewCockpitCrewSchedule() {
        
    
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

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public List<CabinCrew> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinCrew> cabinList) {
        this.cabinList = cabinList;
    }

    public List<CockpitCrew> getCockpitList() {
        return cockpitList;
    }

    public void setCockpitList(List<CockpitCrew> cockpitList) {
        this.cockpitList = cockpitList;
    }

}
