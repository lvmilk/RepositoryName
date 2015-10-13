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
//////////this managed bean contains both edit and view flight instance!!!/////////////////////////////////////////
@Named(value = "editFIMB")
@ViewScoped
public class EditFlightInstanceManagedBean implements Serializable {

    /**
     * Creates a new instance of EditFlightInstanceManagedBean
     */
    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<FlightFrequency> flightFreqList;
    private List<FlightInstance> flightInstList;
    private FlightFrequency flightFreq;
    private FlightInstance flightInst;

    private Date flightDate;
    private String flightDateString;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected
    private Date estimatedDepTime;
    private Date estimatedArrTime;
    private Integer estimatedDateAdjust;
    private Date actualDepTime;
    private Date actualArrTime;
    private Integer actualDateAdjust;

    private String flightNo;
    private Date startDate;
    private Date finishDate;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    private Calendar cal = new GregorianCalendar();
    private Calendar currentCal = new GregorianCalendar();
    private Date currentDate = new Date();

    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df2 = new SimpleDateFormat("HH:mm");

    public EditFlightInstanceManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightFreq");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        flightDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDate");
        flightDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDateString");
        flightStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightStatus");
        estimatedDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDepTime");
        estimatedArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedArrTime");
        estimatedDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDateAdjust");
        actualDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDepTime");
        actualArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualArrTime");
        actualDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDateAdjust");
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        finishDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("finishDate");
        flightInst = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightInst");
        flightInstList = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightInstList");
    }

    public void editFlightInstance(FlightFrequency flightFreq) throws IOException, ParseException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightFreq", flightFreq);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightFreq.getFlightNo());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDepTime", df2.parse(flightFreq.getScheduleDepTime()));
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedArrTime", df2.parse(flightFreq.getScheduleArrTime()));
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDepTime", df2.parse(flightFreq.getScheduleDepTime()));
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualArrTime", df2.parse(flightFreq.getScheduleArrTime()));
        System.out.println("edit flight instance flight No: " + flightFreq.getFlightNo());
        flightNo = flightFreq.getFlightNo();
        flightInstList = fsb.getThisFlightInstance(flightNo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightInstList", flightInstList);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightInstanceDetail.xhtml");
    }

    public void editThisFlightInstance(FlightInstance flightInst) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightInst.getFlightFrequency().getFlightNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightDateString", flightInst.getDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightStatus", flightInst.getFlightStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDepTime", df2.parse(flightInst.getEstimatedDepTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedArrTime", df2.parse(flightInst.getEstimatedArrTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDateAdjust", flightInst.getEstimatedDateAdjust());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDepTime", df2.parse(flightInst.getActualDepTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualArrTime", df2.parse(flightInst.getActualArrTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDateAdjust", flightInst.getActualDateAdjust());
        System.out.println("Edit flight instance with Flight No.: " + flightInst.getFlightFrequency().getFlightNo() + " on " + flightInst.getDate());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightInstanceInfo.xhtml");
    }

    public void editFlightInstCancel() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightInstance.xhtml");
    }

    public void viewFlightInstance(FlightFrequency flightFreq) throws IOException, ParseException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightFreq", flightFreq);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightFreq.getFlightNo());
        System.out.println("view flight instance flight No: " + flightFreq.getFlightNo());
        flightNo = flightFreq.getFlightNo();
        flightInstList = fsb.getThisFlightInstance(flightNo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightInstList", flightInstList);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightInstanceDetail.xhtml");
    }

    public void viewThisFlightInstance(FlightInstance flightInst) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightInst.getFlightFrequency().getFlightNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightDateString", flightInst.getDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightStatus", flightInst.getFlightStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDepTime", df2.parse(flightInst.getEstimatedDepTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedArrTime", df2.parse(flightInst.getEstimatedArrTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDateAdjust", flightInst.getEstimatedDateAdjust());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDepTime", df2.parse(flightInst.getActualDepTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualArrTime", df2.parse(flightInst.getActualArrTime()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDateAdjust", flightInst.getActualDateAdjust());
        System.out.println("view flight instance with Flight No.: " + flightInst.getFlightFrequency().getFlightNo() + " on " + flightInst.getDate());
    }

    public void viewFlightInstCancel() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightInstance.xhtml");
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

    public FlightFrequency getFlightFreq() {
        return flightFreq;
    }

    public void setFlightFreq(FlightFrequency flightFreq) {
        this.flightFreq = flightFreq;
    }

    public FlightInstance getFlightInst() {
        return flightInst;
    }

    public void setFlightInst(FlightInstance flightInst) {
        this.flightInst = flightInst;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Date getEstimatedDepTime() {
        return estimatedDepTime;
    }

    public void setEstimatedDepTime(Date estimatedDepTime) {
        this.estimatedDepTime = estimatedDepTime;
    }

    public Date getEstimatedArrTime() {
        return estimatedArrTime;
    }

    public void setEstimatedArrTime(Date estimatedArrTime) {
        this.estimatedArrTime = estimatedArrTime;
    }

    public Date getActualDepTime() {
        return actualDepTime;
    }

    public void setActualDepTime(Date actualDepTime) {
        this.actualDepTime = actualDepTime;
    }

    public Date getActualArrTime() {
        return actualArrTime;
    }

    public void setActualArrTime(Date actualArrTime) {
        this.actualArrTime = actualArrTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public boolean isOnMon() {
        return onMon;
    }

    public void setOnMon(boolean onMon) {
        this.onMon = onMon;
    }

    public boolean isOnTue() {
        return onTue;
    }

    public void setOnTue(boolean onTue) {
        this.onTue = onTue;
    }

    public boolean isOnWed() {
        return onWed;
    }

    public void setOnWed(boolean onWed) {
        this.onWed = onWed;
    }

    public boolean isOnThu() {
        return onThu;
    }

    public void setOnThu(boolean onThu) {
        this.onThu = onThu;
    }

    public boolean isOnFri() {
        return onFri;
    }

    public void setOnFri(boolean onFri) {
        this.onFri = onFri;
    }

    public boolean isOnSat() {
        return onSat;
    }

    public void setOnSat(boolean onSat) {
        this.onSat = onSat;
    }

    public boolean isOnSun() {
        return onSun;
    }

    public void setOnSun(boolean onSun) {
        this.onSun = onSun;
    }

    public String getFlightDateString() {
        return flightDateString;
    }

    public void setFlightDateString(String flightDateString) {
        this.flightDateString = flightDateString;
    }

    public Integer getEstimatedDateAdjust() {
        return estimatedDateAdjust;
    }

    public void setEstimatedDateAdjust(Integer estimatedDateAdjust) {
        this.estimatedDateAdjust = estimatedDateAdjust;
    }

    public Integer getActualDateAdjust() {
        return actualDateAdjust;
    }

    public void setActualDateAdjust(Integer actualDateAdjust) {
        this.actualDateAdjust = actualDateAdjust;
    }

}
