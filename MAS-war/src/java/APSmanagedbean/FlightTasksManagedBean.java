/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "FTMB")
@ViewScoped
public class FlightTasksManagedBean implements Serializable {

    /**
     * Creates a new instance of FlightTasksManagedBean
     */
    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<FlightFrequency> flightFreqList;
    private List<FlightInstance> flightInstList;
    private List<Aircraft> aircraftList;
    private FlightFrequency flightFreq;
    private Aircraft aircraft;
    private Aircraft defaultAircraft;
    private List<String> registrationList = new ArrayList<String>();
    private String registrationNo;
    private String type;
    private Long id; //flight instance ID
    private List<FlightFrequency> selectedList;

    private String flightDate;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected
    private String flightNo;
    private String depTime;
    private String arrTime;
    private Integer dateAdjust;
    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df2 = new SimpleDateFormat("HH:mm");

    public FlightTasksManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
        flightInstList = fsb.getAllFlightInstance();
        aircraftList = fpb.getAllAircraft();
        defaultAircraft = fpb.getAircraft("9V-000");
        aircraftList.remove(defaultAircraft);
        for (int i = 0; i < aircraftList.size(); i++) {
            registrationList.add(aircraftList.get(i).getRegistrationNo());
        }
        selectedList = new ArrayList<FlightFrequency>();
        aircraft = (Aircraft) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraft");
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightFreq");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        flightDate = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDate");
        flightStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightStatus");
        depTime = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("depTime");
        depTime = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("arrTime");
    }

    public void viewAircraftFlightTask(Aircraft aircraft) throws IOException, Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraft", aircraft);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registrationNo", aircraft.getRegistrationNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("type", aircraft.getAircraftType().getType());
        System.out.println("flightTasksManagedBean: viewAircraft: registrationNo: " + aircraft.getRegistrationNo());
        registrationNo = aircraft.getRegistrationNo();
        flightInstList = fpb.getThisFlightInstance(registrationNo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightInstList", flightInstList);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAircraftFlightTasksDetail.xhtml");
    }

    public void viewThisAircraftTasks(FlightInstance flightInst) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightInst.getFlightFrequency().getFlightNo());
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightDate", flightInst.getDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightStatus", flightInst.getFlightStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("depTime", flightInst.getFlightFrequency().getScheduleDepTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("depTime", flightInst.getFlightFrequency().getScheduleArrTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateAdjust", flightInst.getFlightFrequency().getDateAdjust());
        System.out.println("flightTasksManagedBean: viewAircraft: viewAircraftTask with Flight No.: " + flightInst.getFlightFrequency().getFlightNo() + " on " + flightInst.getDate());
    }

    public void viewFlightTaskCancel() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAircraftFightTasks.xhtml");
    }

    public List<FlightFrequency> getFlightFreqList() {
        return flightFreqList;
    }

    public void setFlightFreqList(List<FlightFrequency> flightFreqList) {
        this.flightFreqList = flightFreqList;
    }

    public List<FlightInstance> getFlightInstList() {
        return flightInstList;
    }

    public void setFlightInstList(List<FlightInstance> flightInstList) {
        this.flightInstList = flightInstList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public FlightFrequency getFlightFreq() {
        return flightFreq;
    }

    public void setFlightFreq(FlightFrequency flightFreq) {
        this.flightFreq = flightFreq;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public List<String> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<String> registrationList) {
        this.registrationList = registrationList;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FlightFrequency> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<FlightFrequency> selectedList) {
        this.selectedList = selectedList;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
