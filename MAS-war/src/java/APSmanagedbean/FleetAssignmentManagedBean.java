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
import org.primefaces.extensions.model.timeline.TimelineGroup;
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
    ;
    private Aircraft aircraft;
    private FlightInstance flightInstance;
    private FlightInstance fi;
    private long zoomMax;
    private Date start;
    private Date end;
    private Date startDate;
    private Date endDate;
    private TimeZone timeZone = TimeZone.getTimeZone("Asia/Singapore");
    private String deleteMessage;

    private String flightNo;
    private Date flightDate;
    private List<FlightInstance> unassignedFlight;
    private Long taskId;
    private String taskAircraftSerial;

    public FleetAssignmentManagedBean() {
    }

    @PostConstruct
    public void initialize() {
        List<Aircraft> acList = new ArrayList<Aircraft>();
//        List<Aircraft> aircraftList = new ArrayList<Aircraft>();
//        for (Aircraft temp : fpb.getAllAircraft()) {
//            if (!temp.getRegistrationNo().equals("9V-000")) {
//                aircraftList.add(temp);
//            }
//        }
//        aircraftList = fpb.getAllAircraft();

// initial zooming is ca. one month to avoid hiding of event details (due to wide time range of events)  
        zoomMax = 1000L * 60 * 60 * 24 * 30;

        // set initial start / end dates for the axis of the timeline (just for testing)  
//        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
//        cal.set(2015, Calendar.OCTOBER, 9, 0, 0, 0);
        start = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startPlanDate");
        end = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endPlanDate");
        System.out.println("FAMB: pass start and end date from FIMB to FAMB: " + start + "-----" + end);
        model = new TimelineModel();
        Date startDate;
        Date endDate;
        acList = fpb.getAllAircraft();

        System.out.println(acList);
        for (Aircraft ac : acList) {
            if (!ac.getRegistrationNo().equals("9V-000")) {
                System.out.println(ac.getRegistrationNo());
                List<FlightInstance> fiList = ac.getFlightInstance();
                TimelineGroup group = new TimelineGroup(ac.getRegistrationNo(), ac);
                model.addGroup(group);
                //---------------Add dummy event for aircraft group to show---------------
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                cal.set(2014, Calendar.FEBRUARY, 9, 0, 0, 0);
                Date dummyStart = cal.getTime();
                cal.set(2014, Calendar.FEBRUARY, 10, 0, 0, 0);
                Date dummyEnd = cal.getTime();
                TimelineEvent flightTaskEvent = new TimelineEvent(fsb.getDummyFi(), dummyStart, dummyEnd, true, ac.getRegistrationNo());
                System.out.println("FAMB: init(): event " + flightTaskEvent);
                model.add(flightTaskEvent);
                System.out.println("FAMB: init(): group " + group);
                for (FlightInstance fi : fiList) {
                    System.out.println("FAMB: init(): fi= " + fi.getDate() + fi.getFlightFrequency().getFlightNo());
                    System.out.println("FAMB: init(): starttime " + start);
                    startDate = fi.getStandardDepTimeDateType();
                    System.out.println("FAMB: init(): endtime " + end);
                    endDate = fi.getStandardArrTimeDateType();
                    // create an event with content, start / end dates, editable flag, group name and custom style class  
                    flightTaskEvent = new TimelineEvent(fi, startDate, endDate, true, ac.getRegistrationNo());
                    System.out.println("FAMB: init(): event " + flightTaskEvent);
                    model.add(flightTaskEvent);
                }
            }
        }
    }

    public void onAdd(TimelineAddEvent e) {
        System.out.println("-------------------------------aaaabbbbbbbbbbb");
        // get TimelineEvent to be added  
        event = new TimelineEvent(new FlightInstance(), e.getStartDate(), e.getEndDate(), true, e.getGroup());
        // add the new event to the model in case if user will close or cancel the "Add dialog"  
        // without to update details of the new event. Note: the event is already added in UI.  
        model.add(event);
    }

    public void onDelete(TimelineModificationEvent e) {
        System.out.println("-------------------------------aaaacccccccccccc");
        event = e.getTimelineEvent();
    }

    public void delete() {
        System.out.println("event ???" + event.toString());
        FlightInstance f = (FlightInstance) event.getData();
        Aircraft a = f.getAircraft();
        if (a.getFlightInstance().contains(f)) {
             System.out.println("remove ac from fi !!!!" );
            fsb.deleteAcFromFi(a, f);
        }
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
        model.delete(event, timelineUpdater);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight task " + f.getFlightFrequency().getFlightNo() + " on "
                + f.getDate() + " of " + a.getSerialNo() + " has been deleted", null);
    }

    public void addTask() {
        System.out.println("FAMB: ------------------------ahhahahha");
        try {
            aircraft = fsb.findAircraft(taskAircraftSerial);
            fi = fsb.findFlight(taskId);
            System.out.println("TaskID: " + taskId);
            System.out.println("AircraftID: " + taskAircraftSerial);
//            start = fi.getStandardDepTimeDateType();
//            end = fi.getStandardArrTimeDateType();
            if (fsb.addAcToFi(aircraft, fi)) {
                System.out.println("CHECK 1");
                event = new TimelineEvent(fi, fi.getStandardDepTimeDateType(), fi.getStandardArrTimeDateType(), true, taskAircraftSerial);
                System.out.println("event created!!");
                model.add(event);
                System.out.println("event added!!");
                TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":mainForm:timeline");
                model.update(event, timelineUpdater);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate() + " has been assigned to " + taskAircraftSerial, ""));
            } else {
                System.out.println("CHECK 2");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aircraft " + taskAircraftSerial + "cannot fly " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate(), ""));
                System.out.println("Error meesage" + "addTaskError");
            }
        } catch (Exception ex) {
            System.out.println("Error meesage: " + ex.getMessage());

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
        aircraftList = new ArrayList<Aircraft>();
        System.out.println("FAMB: SIZE: " + fpb.getAllAircraft().size());
        for (Aircraft temp : fpb.getAllAircraft()) {
            if (!temp.getRegistrationNo().equals("9V-000")) {
                System.out.println("FAMB: " + temp.getRegistrationNo());
                aircraftList.add(temp);
            }
        }
        System.out.println("FAMB: SIZE2: " + aircraftList.get(0).getRegistrationNo());
//        aircraftList = fpb.getAllAircraft();
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
        return fsb.getUnplannedFiWithinPeriod(start, end);
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
        System.out.println("aircradt serial read ? " + taskAircraftSerial);
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
