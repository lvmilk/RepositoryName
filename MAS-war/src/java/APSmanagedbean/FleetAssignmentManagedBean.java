/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.FlightInstance;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.extensions.component.timeline.TimelineUpdater;
import org.primefaces.extensions.event.timeline.TimelineAddEvent;
import org.primefaces.extensions.event.timeline.TimelineModificationEvent;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;

/**
 *
 * @author Xi
 */
@Named(value = "FAMB")
@ViewScoped
public class FleetAssignmentManagedBean implements Serializable {

    @EJB
    FleetPlanningBeanLocal fpb;

    @EJB
    FlightSchedulingBeanLocal fsb;

//    private List<TimelineModel> modelList;
    private TimelineModel model;
    private TimelineEvent event; // current event to be changed, edited, deleted or added  

    private List<Aircraft> aircraftList;
    private Aircraft aircraft;
    private FlightInstance flightInstance;
    private FlightInstance fi;
    private long zoomMax;
    private Date start;
    private Date end;
    private TimeZone timeZone = TimeZone.getTimeZone("UTC");
    private String deleteMessage;

    private String flightNo;
    private Date flightDate;
    private List<FlightInstance> unassignedFlight;
    private Long taskId;
    private String taskAircraftSerial;

    public FleetAssignmentManagedBean() {
    }

    @PostConstruct
    protected void initialize() {
        aircraftList = fpb.getAllAircraft();

// initial zooming is ca. one month to avoid hiding of event details (due to wide time range of events)  
        zoomMax = 1000L * 60 * 60 * 24 * 30;

        // set initial start / end dates for the axis of the timeline (just for testing)  
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.set(2015, Calendar.OCTOBER, 9, 0, 0, 0);
        start = cal.getTime();
        cal.set(2016, Calendar.DECEMBER, 10, 0, 0, 0);
        end = cal.getTime();

        model = new TimelineModel();
        for (Aircraft ac : fpb.getAllAircraft()) {
            List<FlightInstance> fiList = ac.getFlightInstance();
            for (FlightInstance fi : fiList) {
                start = fi.getStandardDepTimeDateType();
                end = fi.getStandardArrTimeDateType();
                // create an event with content, start / end dates, editable flag, group name and custom style class  
//                TimelineEvent flightTaskEvent = new TimelineEvent(fi, start, end, true);
                TimelineEvent flightTaskEvent = new TimelineEvent(fi, start, end, true, ac.getSerialNo());
                model.add(flightTaskEvent);
            }
//            modelList.add(model);
        }
    }

    public void onAdd(TimelineAddEvent e) {
//        // get TimelineEvent to be added  
//        event = new TimelineEvent(new FlightInstance(), e.getStartDate(), e.getEndDate(), true, e.getGroup());  
//        // add the new event to the model in case if user will close or cancel the "Add dialog"  
//        // without to update details of the new event. Note: the event is already added in UI.  
//        model.add(event);  
    }

    public void onDelete(TimelineModificationEvent e) {
        event = e.getTimelineEvent();
    }

    public void delete() {
        FlightInstance f = (FlightInstance) event.getData();
        Aircraft a = f.getAircraft();
        fsb.deleteAcFromFi(a, f);
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
        model.delete(event, timelineUpdater);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight task " + f.getFlightFrequency().getFlightNo() + " on "
                + f.getDate() + " of " + a.getSerialNo() + " has been deleted", null);
    }

    public void addTask() {
        try {
            aircraft = fsb.findAircraft(taskAircraftSerial);
            fi = fsb.findFlight(taskId);
            start = fi.getStandardDepTimeDateType();
            end = fi.getStandardArrTimeDateType();
            if(fsb.addAcToFi(aircraft, fi)){
                event = new TimelineEvent(fi, start, end, true, taskAircraftSerial);
                TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
                model.update(event, timelineUpdater);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate() + " has been assigned to " + taskAircraftSerial, ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aircraft " + taskAircraftSerial + "cannot fly " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate(), ""));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public TimelineModel getModel() {
        return model;
    }

    public TimelineEvent getEvent() {
        return event;
    }

    public void setEvent(TimelineEvent event) {
        this.event = event;
    }

    public long getZoomMax() {
        return zoomMax;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFligntInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

    public List<Aircraft> getAircraftList() {
        return fpb.getAllAircraft();
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public List<FlightInstance> getUnassignedFlight() {
        return fsb.getUnassignedFlight();
    }

    public void setUnassignedFlight(List<FlightInstance> unassignedFlight) {
        this.unassignedFlight = unassignedFlight;
    }

    public FlightInstance getFi() {
        return fi;
    }

    public void setFi(FlightInstance fi) {
        this.fi = fi;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskAircraftSerial() {
        return taskAircraftSerial;
    }

    public void setTaskAircraftSerial(String taskAircraftSerial) {
        this.taskAircraftSerial = taskAircraftSerial;
    }

    public String getDeleteMessage() {
        FlightInstance fi = ((FlightInstance) event.getData());
        return "Do you want to delete the flight task " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate() + " ?";
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

}
