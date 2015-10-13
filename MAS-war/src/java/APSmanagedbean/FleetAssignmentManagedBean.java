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

    private List<TimelineModel> modelList;
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

        modelList = new ArrayList<TimelineModel>();
        model = new TimelineModel();

        for (Aircraft ac : fpb.getAllAircraft()) {
            List<FlightInstance> fiList = ac.getFlightInstance();
            for (FlightInstance fi : fiList) {
                start = fi.getStandardDepTimeDateType();
                end = fi.getStandardArrTimeDateType();
                //  String flightDetail = fi.getFlightFrequency().getFlightNo() + " " + fi.getFlightFrequency().getRoute();
                // create an event with content, start / end dates, editable flag, group name and custom style class  
                TimelineEvent flightTaskEvent = new TimelineEvent(fi, start, end, false, ac.getAircraftType().getType());
                model.add(flightTaskEvent);
            }
            modelList.add(model);
        }
    }

    public void onAdd(Aircraft a) {
        flightInstance = new FlightInstance();

        // add the new event to the model in case if user will close or cancel the "Add dialog"  
        // without to update details of the new event. Note: the event is already added in UI.  
        model.add(event);
    }

    public void onDelete(FlightInstance fi) {
        // get clone of the TimelineEvent to be deleted  
        aircraft = fi.getAircraft();
    }

    public void delete() {
        // delete booking in DB...  
        // if everything was ok, delete the TimelineEvent in the model and update UI with the same response.  
        // otherwise no server-side delete is necessary (see timelineWdgt.cancelDelete() in the p:ajax onstart).  
        // we assume, delete in DB was successful  
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
        model.delete(event, timelineUpdater);
        FlightInstance flightTask = (FlightInstance) event.getData();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "The flight task " + ((FlightInstance) event.getData()).getFlightFrequency().getFlightNo() + " on "
                + ((FlightInstance) event.getData()).getDate() + " has been deleted", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveDetails() {
        // save the updated booking in DB...  

        // if everything was ok, update the TimelineEvent in the model and update UI with the same response.  
        // otherwise no server-side update is necessary because UI is already up-to-date.  
        // we assume, save in DB was successful  
        if (addTask()) {
            //------------------------------How to know which aircraft is it now?
 //           fsb.addAcToFi(fi, aircraft);
            TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
            model.update(event, timelineUpdater);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight task has been assigned", ""));
        }
    }

    public boolean addTask() {
        try {
            fi = fsb.findFlight(flightNo, flightDate);
            return true;
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
            return false;
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
        return aircraftList;
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
        return unassignedFlight;
    }

    public void setUnassignedFlight(List<FlightInstance> unassignedFlight) {
        this.unassignedFlight = unassignedFlight;
    }

//-------------------------------uncompleted----------------------------
    public String getDeleteMessage() {
        return "Do you want to delete the flight task ?";
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

}
