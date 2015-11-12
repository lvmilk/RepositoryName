/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author Xu
 */
@Named(value = "VCPCSDMB")
@ViewScoped
public class ViewCockpitCrewScheduleDetailManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;

    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private CockpitCrew viewCockpit;
    private List<FlightInstance> fiList = new ArrayList<>();

    private ScheduleModel eventModel;
//    private ScheduleEvent event = new DefaultScheduleEvent();

    public ViewCockpitCrewScheduleDetailManagedBean() {
    }

    @PostConstruct
    public void init() {
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startViewScheduleDate");
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endViewScheduleDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = df.format(startDate);
        endDateString = df.format(endDate);
        viewCockpit = (CockpitCrew) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("viewScheduleCP");
        fiList = csb.getCockpitCrewFlightForPeriod(viewCockpit, startDate, endDate);

        eventModel = new DefaultScheduleModel();
        for (FlightInstance fi : fiList) {
            eventModel.addEvent(new DefaultScheduleEvent("Flight Task " + fi.getFlightFrequency().getFlightNo(), fi.getStandardDepTimeDateType(), fi.getStandardArrTimeDateType()));
        }
    }

    public void viewFlightCrewScheduleBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrewSchedule.xhtml");
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

    public CockpitCrew getViewCockpit() {
        return viewCockpit;
    }

    public void setViewCockpit(CockpitCrew viewCockpit) {
        this.viewCockpit = viewCockpit;
    }

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

}
