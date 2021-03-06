/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import Entity.APS.FlightInstance;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import org.primefaces.context.RequestContext;
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

    private Aircraft aircraft;
    private FlightInstance flightInstance;
    private FlightInstance fi;
    private long zoomMax;
    private Date start;
    private Date end;
    private Date startDate;
    private Date endDate;
    private String startPl;
    private String endPl;
    private TimeZone timeZone = TimeZone.getTimeZone("Asia/Singapore");
    private String deleteMessage;
    private String deleteMtMessage;

    private String flightNo;
    private Date flightDate;
    private List<FlightInstance> unassignedFlight;
    private List<FlightInstance> unassignedFlightAll;
    private Long taskId;
    private String taskAircraftSerial;
    private String taskMtAircraftSerial;

    private List<String> selectedTaskId = new ArrayList<>();
    private List<FlightInstance> selectedTask = new ArrayList<>();

    private String taskType;
    private String mtObj;
    private Date mtStartTime;
    private Date mtEndTime;
    private Integer manhour;
    private Integer mtdu;

//    private boolean selectTypeAlr = false;
    public FleetAssignmentManagedBean() {
    }

    @PostConstruct
    public void initialize() {
        List<Aircraft> acList = new ArrayList<Aircraft>();

        // initial zooming is ca. one month to avoid hiding of event details (due to wide time range of events)  
        zoomMax = 1000L * 60 * 60 * 24 * 30;

        // set initial start / end dates for the axis of the timeline (just for testing)  
        //        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        //        cal.set(2015, Calendar.OCTOBER, 9, 0, 0, 0);
        start = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startPlanDate");
        end = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endPlanDate");
        unassignedFlight = fsb.getUnplannedFiWithinPeriod(start, end);
        unassignedFlightAll = fsb.getAllUnplannedFi();

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startPl = df.format(start);
        endPl = df.format(end);
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
                List<Maintenance> mtList = ac.getMaintenanceList();
                TimelineGroup group = new TimelineGroup(ac.getRegistrationNo(), ac);
                model.addGroup(group);
                //---------------Add dummy event for aircraft group to show---------------
                Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                cal.set(2014, Calendar.FEBRUARY, 9, 0, 0, 0);
                Date dummyStart1 = cal.getTime();
                cal.set(2014, Calendar.FEBRUARY, 10, 0, 0, 0);
                Date dummyEnd1 = cal.getTime();
                cal.set(2014, Calendar.FEBRUARY, 11, 0, 0, 0);
                Date dummyStart2 = cal.getTime();
                cal.set(2014, Calendar.FEBRUARY, 12, 0, 0, 0);
                Date dummyEnd2 = cal.getTime();
                TimelineEvent flightTaskEvent = new TimelineEvent(fsb.getDummyFi("outbound"), dummyStart1, dummyEnd1, true, ac.getRegistrationNo());
                TimelineEvent flightTaskEvent2 = new TimelineEvent(fsb.getDummyFi("inbound"), dummyStart2, dummyEnd2, true, ac.getRegistrationNo());
                //    System.out.println("FAMB: init(): event dummy" + flightTaskEvent);
                model.add(flightTaskEvent);
                model.add(flightTaskEvent2);

                System.out.println("FAMB: init(): group " + group);
                for (FlightInstance fi : fiList) {
                    //        System.out.println("FAMB: init(): fi= " + fi.getDate() + fi.getFlightFrequency().getFlightNo());
                    startDate = fi.getStandardDepTimeDateType();
                    //        System.out.println("FAMB: init(): starttime " + startDate);
                    endDate = fi.getStandardArrTimeDateType();
                    //        System.out.println("FAMB: init(): endtime " + endDate);
                    // create an event with content, start / end dates, editable flag, group name and custom style class  
                    flightTaskEvent = new TimelineEvent(fi, startDate, endDate, true, ac.getRegistrationNo(), "fi");
                    //        System.out.println("FAMB: init(): event " + flightTaskEvent);

                    flightTaskEvent.getClass().getSimpleName();

                    model.add(flightTaskEvent);
                }
                for (Maintenance mt : mtList) {
                    startDate = mt.getStartTime();
                    System.out.println("FAMB: init(): starttime " + startDate);
                    endDate = mt.getEndTime();
                    System.out.println("FAMB: init(): endtime " + endDate);
                    flightTaskEvent = new TimelineEvent(mt, startDate, endDate, true, ac.getRegistrationNo(), "mt");
                    System.out.println("FAMB: init(): event " + flightTaskEvent);
                    model.add(flightTaskEvent);
                }
            }
        }
    }

    public void onDelete(TimelineModificationEvent e) {
        System.out.println("-------------------------------aaaacccccccccccc");
        System.out.println("onDelete(): ");
        event = e.getTimelineEvent();
        System.out.println("onDelete(): " + e.toString());
        System.out.println("onDelete(): " + e.getClass());
        System.out.println("onDelete(): " + e.getTimelineEvent());
    }

    public void onDeleteShowDialog(TimelineModificationEvent e) {
        event = e.getTimelineEvent();
        RequestContext context = RequestContext.getCurrentInstance();
        System.err.println("Is onDeleteShowDialog called ? ");
        if (event.getData().getClass().getSimpleName().equals("FlightInstance")) {
            System.err.println(" onDeleteShowDialog 1->FlightInstance is called ");
            context.execute("PF('deleteTaskWdgt').show();");
            System.err.println("1->FlightInstance ");
        }
        if (event.getData().getClass().getSimpleName().equals("Maintenance")) {
            System.err.println(" onDeleteShowDialog 2->Maintenance called ");
            context.execute("PF('deleteMtTaskWdgt').show();");
            System.err.println("2->Maintenance ");
        }
    }

    public void delete() {
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formMain:timeline");
        System.out.println("--------------------------------->" + event.getData().getClass().getSimpleName());
        if (event != null) {
            if (event.getData().getClass().getSimpleName().equals("FlightInstance")) {
                System.out.println("event ???" + event.toString());
                FlightInstance f = (FlightInstance) event.getData();
                Aircraft a = f.getAircraft();
                if (a.getFlightInstance().contains(f)) {
                    System.out.println("remove ac from fi !!!!");
                    fsb.deleteAcFromFi(a, f);
                }
                model.delete(event, timelineUpdater);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Flight task " + f.getFlightFrequency().getFlightNo() + " on " + f.getDate() + " of " + a.getSerialNo() + " has been deleted", null);
            } else {
                deleteMt();
            }
        } else {
            model.delete(event, timelineUpdater);
        }
    }

    public void deleteMt() {
        TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formMain:timeline");
        if (event != null) {
            System.out.println("event ???" + event.toString());
            Maintenance mt = (Maintenance) event.getData();
            Aircraft a = mt.getAircraft();
            if (a.getMaintenanceList().contains(mt)) {
                System.out.println("remove mt from ac !!!!");
                fsb.deleteMtFromAc(a, mt);
            }
            model.delete(event, timelineUpdater);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully deleted maintenance for " + a.getRegistrationNo() + " from " + mt.getStartTime() + " to " + mt.getStartTime(), null);

        } else {
            model.delete(event, timelineUpdater);
        }
    }

    public void addTask() {
        System.out.println("FAMB: ------------------------ahhahahha");
        try {
            if (taskAircraftSerial.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select an aircraft.", ""));
            } else {
                aircraft = fsb.findAircraft(taskAircraftSerial);
                if (selectedTaskId.isEmpty()) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select flight(s) to assign.", ""));
                } else {
                    System.out.println("AircraftID: " + taskAircraftSerial);
                    List<Long> longIdList = new ArrayList<>();
                    for (String st : selectedTaskId) {
                        long l = Long.parseLong(st);
                        longIdList.add(l);
                    }
                    boolean canAssign = fsb.addAcToFi(aircraft, longIdList);
                    if (canAssign) {
//                    List<FlightInstance> selectedFi = new ArrayList<>();
                        for (Long id : longIdList) {
                            FlightInstance f1 = fsb.findFlight(id);
                            event = new TimelineEvent(f1, f1.getStandardDepTimeDateType(), f1.getStandardArrTimeDateType(), true, taskAircraftSerial);
                            model.add(event);
                            TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formMain:timeline");
                            model.update(event, timelineUpdater);
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully add selected tasks to " + taskAircraftSerial, ""));
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success: Flight " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate() + " has been assigned to " + taskAircraftSerial, ""));
                    } else {
                        System.out.println("cannot add task!!!!");
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : Aircraft " + taskAircraftSerial + " cannot fly " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate(), ""));
                        System.out.println("Error meesage " + "addTaskError");
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error meesage: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void addMtTask() {
        try {
            aircraft = fsb.findAircraft(taskMtAircraftSerial);
            System.out.println("FAMB.addMtTask(): (-_-)aircraft is " + aircraft.getRegistrationNo());
            System.out.println("FAMB.addMtTask(): (-_-)maintenance detail is " + mtObj + " from " + mtStartTime + " to " + mtEndTime);
            if (mtStartTime.after(mtEndTime)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : Maintenance start time should be before end time.", ""));
            } else if (fsb.addMtToAc(aircraft, mtObj, mtStartTime, mtEndTime, manhour)) {
                event = new TimelineEvent(fi, mtStartTime, mtEndTime, true, taskAircraftSerial);
                model.add(event);
                TimelineUpdater timelineUpdater = TimelineUpdater.getCurrentInstance(":formMain:timeline");
                model.update(event, timelineUpdater);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Successfully addedss " + mtObj + " for " + aircraft.getRegistrationNo() + " from " + mtStartTime + " to " + mtEndTime, ""));
            } else {
                System.out.println("Aircraft " + aircraft.getRegistrationNo() + " cannot be scheduled " + mtObj + " from " + mtStartTime + " to " + mtEndTime);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aircraft " + aircraft.getRegistrationNo() + " cannot be scheduled " + mtObj + " from " + mtStartTime + " to " + mtEndTime, ""));
            }
        } catch (Exception ex) {
            System.out.println("Error meesage: " + ex.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred: " + ex.getMessage(), ""));

        }
    }

    public void refresh() throws IOException {
        System.out.println("REFRESH!");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./assignFlightView.xhtml");
    }

//    public void onAcChange() {
//        if (taskMtAircraftSerial != null && !taskMtAircraftSerial.equals("") && mtObj != null && !mtObj.equals("")) {
//            Aircraft ac = fsb.findAircraft(taskMtAircraftSerial);
//            switch (mtObj.charAt(0)) {
//                case 'A':
//                    setMtdu(ac.getAircraftType().getAcMH());
//                    break;
//                case 'B':
//                    setMtdu(ac.getAircraftType().getBcMH());
//                    break;
//                case 'C':
//                    setMtdu(ac.getAircraftType().getCcMH());
//                    break;
//                case 'D':
//                    setMtdu(ac.getAircraftType().getDcMH());
//                    break;
//                default:
//                    setMtdu(0);
//                    break;
//            }
//        }
//    }
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
        //    System.out.println("FAMB: SIZE2: " + aircraftList.get(0).getRegistrationNo());
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

    public List<FlightInstance> getUnassignedFlightAll() {
        return fsb.getAllUnplannedFi();
    }

    public void setUnassignedFlightAll(List<FlightInstance> unassignedFlightAll) {
        this.unassignedFlightAll = unassignedFlightAll;
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

        System.err.println("******************* getDeleteMessage");

        if (event != null) {
            if (event.getData().getClass().getSimpleName().equals("FlightInstance")) {
            FlightInstance fi = ((FlightInstance) event.getData());
            System.err.println("**************************** Here");
            return "Do you want to delete the flight task " + fi.getFlightFrequency().getFlightNo() + " on " + fi.getDate() + " ?";
//            } else {
//                return "";
            }
        }
        return "Do you want to delete the flight task? ";
    }

    public void setDeleteMessage(String deleteMessage) {
        this.deleteMessage = deleteMessage;
    }

    public String getDeleteMtMessage() {

        System.err.println("******************* getDeleteMtMessage");

        if (event != null) {
            if (event.getData().getClass().getSimpleName().equals("Maintenance")) {
            Maintenance mt = ((Maintenance) event.getData());
            System.err.println("**************************** Here MT");
            return "Do you want to delete [" + mt.getObjective() + "] on " + mt.getStartTime() + " ?";
//            } else {
//                return "";
            }
        }
        return "Do you want to delete the maintenance? ";
    }

    public void setDeleteMtMessage(String deleteMtMessage) {
        this.deleteMtMessage = deleteMtMessage;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getMtObj() {
        return mtObj;
    }

    public void setMtObj(String mtObj) {
        this.mtObj = mtObj;
    }

    public Date getMtStartTime() {
        return mtStartTime;
    }

    public void setMtStartTime(Date mtStartTime) {
        this.mtStartTime = mtStartTime;
    }

    public Date getMtEndTime() {
        return mtEndTime;
    }

    public void setMtEndTime(Date mtEndTime) {
        this.mtEndTime = mtEndTime;
    }

//    public String getMtType() {
//        return mtType;
//    }
//
//    public void setMtType(String mtType) {
//        this.mtType = mtType;
//    }
    public String getTaskMtAircraftSerial() {
        return taskMtAircraftSerial;
    }

    public void setTaskMtAircraftSerial(String taskMtAircraftSerial) {
        this.taskMtAircraftSerial = taskMtAircraftSerial;
    }

    public String getStartPl() {
        return startPl;
    }

    public void setStartPl(String startPl) {
        this.startPl = startPl;
    }

    public String getEndPl() {
        return endPl;
    }

    public void setEndPl(String endPl) {
        this.endPl = endPl;
    }

    public List<String> getSelectedTaskId() {
        return selectedTaskId;
    }

    public void setSelectedTaskId(List<String> selectedTaskId) {
        this.selectedTaskId = selectedTaskId;
    }

    public List<FlightInstance> getSelectedTask() {
        return selectedTask;
    }

    public void setSelectedTask(List<FlightInstance> selectedTask) {
        this.selectedTask = selectedTask;
    }

    public Integer getManhour() {
        return manhour;
    }

    public void setManhour(Integer manhour) {
        this.manhour = manhour;
    }

    public Integer getMtdu() {
        Aircraft ac = fsb.findAircraft(taskMtAircraftSerial);
        switch (mtObj.charAt(0)) {
            case 'A':
                mtdu = ac.getAircraftType().getAcMH();
                break;
            case 'B':
                mtdu = ac.getAircraftType().getBcMH();
                break;
            case 'C':
                mtdu = ac.getAircraftType().getCcMH();
                break;
            case 'D':
                mtdu = ac.getAircraftType().getDcMH();
                break;
            default:
                mtdu = 0;
                break;
        }
        return mtdu;
    }

    public void setMtdu(Integer mtdu) {
        this.mtdu = mtdu;
    }

}
