/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.FlightInstance;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
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

    /**
     * Creates a new instance of FleetAssignmentManagedBean
     */
    private TimelineModel model;
    private TimelineEvent event; // current event to be changed, edited, deleted or added  

    private List<Aircraft> aircraftList;
    private Aircraft aircraft;
    private FlightInstance flightInstance;
    private long zoomMax;
    private Date start;
    private Date end;
    private TimeZone timeZone = TimeZone.getTimeZone("UTC");
    private boolean timeChangeable = true;
    private String deleteMessage;

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

        // create timeline model  
        model = new TimelineModel();

        // Server-side dates should be in UTC. They will be converted to a local dates in UI according to provided TimeZone.  
        // Submitted local dates in UI are converted back to UTC, so that server receives all dates in UTC again.  
    }

    public void onChange(Aircraft a) {
        // get clone of the TimelineEvent to be changed with new start / end dates  
        flightInstance = a.getFlightInstance().get(0);

        // update booking in DB...  
        // if everything was ok, no UI update is required. Only the model should be updated  
        model.update(event);

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "The booking dates " + " have been updated", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);

        // otherwise (if DB operation failed) a rollback can be done with the same response as follows:  
        // TimelineEvent oldEvent = model.getEvent(model.getIndex(event));  
        // TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");  
        // model.update(oldEvent, timelineUpdater);  
    }

//    public void onEdit(Aircraft a) {
//        // get clone of the TimelineEvent to be edited  
//        flightInstance = a.getFlightInstance().get(0);
//    }

    public void onAdd(Aircraft a) {
        // get TimelineEvent to be added  
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

        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "The booking " + " has been deleted", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void saveDetails() {
        // save the updated booking in DB...  

        // if everything was ok, update the TimelineEvent in the model and update UI with the same response.  
        // otherwise no server-side update is necessary because UI is already up-to-date.  
        // we assume, save in DB was successful  
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
        model.update(event, timelineUpdater);

        FacesMessage msg
                = new FacesMessage(FacesMessage.SEVERITY_INFO, "The booking details " + " have been saved", null);
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public boolean isTimeChangeable() {
        return timeChangeable;
    }

    public void toggleTimeChangeable() {
        timeChangeable = !timeChangeable;
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
//-------------------------------uncompleted----------------------------
    public String getDeleteMessage() {
        return "Do you want to delete the flight task ?";
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

}
