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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Xu
 */
@Named(value = "PSFCMB")
@ViewScoped
public class PersonalScheduleFlightCrewManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    private String username;
    private String stfType;
    private List<FlightInstance> fiList = new ArrayList<>();
    private List<FlightInstance> newFi = new ArrayList<>();
    private CabinCrew cc;
    private CockpitCrew cp;

    private ScheduleModel eventModel;
    private Date startDate;

    public PersonalScheduleFlightCrewManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null) {
            username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
            stfType = csb.getCrewType(username);
            if (stfType.equalsIgnoreCase("Cockpit")) {
                cp = csb.findCPById(username);
                fiList = cp.getFiList();
                newFi = getNewFi(fiList);
            } else {
                cc = csb.findCCById(username);
                fiList = cc.getFiList();
                newFi = getNewFi(fiList);
            }
            startDate = newFi.isEmpty() ? new Date() : newFi.get(0).getStandardDepTimeDateType();
            eventModel = new DefaultScheduleModel();
            for (FlightInstance fi : newFi) {
                eventModel.addEvent(new DefaultScheduleEvent("Flight Task " + fi.getFlightFrequency().getFlightNo(), fi.getStandardDepTimeDateType(), fi.getStandardArrTimeDateType()));
            }
        }
    }

    public List<FlightInstance> getNewFi(List<FlightInstance> fiList) {
        List<FlightInstance> fi = new ArrayList<>();
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        for (FlightInstance f1 : fiList) {
            Date depDate = f1.getStandardDepTimeDateType();
            cal.setTime(depDate);
            int depMonth = cal.get(Calendar.MONTH);
            int depYear = cal.get(Calendar.YEAR);
            if (!depDate.before(today) || depMonth == month && depYear == year) {
                fi.add(f1);
            }
        }
        return fi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStfType() {
        return stfType;
    }

    public void setStfType(String stfType) {
        this.stfType = stfType;
    }

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public List<FlightInstance> getNewFi() {
        return newFi;
    }

    public void setNewFi(List<FlightInstance> newFi) {
        this.newFi = newFi;
    }

    public CabinCrew getCc() {
        return cc;
    }

    public void setCc(CabinCrew cc) {
        this.cc = cc;
    }

    public CockpitCrew getCp() {
        return cp;
    }

    public void setCp(CockpitCrew cp) {
        this.cp = cp;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    
    
}
