/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Xu
 */
@Named(value = "flightManagedBean")
@Dependent
public class FlightManagedBean {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private RoutePlanningBeanLocal rpb;
    
    private FlightFrequency flightFreq;
    private FlightInstance flightInst;
    private Route route;
    private ArrayList<Route> routeList;
     private Map<String, Long> routeInfo = new HashMap<String, Long>();

    private String flightNo;
    private Integer stopoverNo;

    private String scheduleDepTime;
    private String scheduleArrTime;
    private Integer dateAdjust;

    private String startDate;
    private String endDate;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    // for code share flights
    private String operator;
    private ArrayList<String> codeshare;

    public FlightManagedBean() {
    }

    @PostConstruct
    public void init() {

    }

    public void addFlightFrequency() throws Exception {
        try {
            fsb.addFlightFrequency(route, flightNo, scheduleDepTime, scheduleArrTime, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDate, endDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("successFlightNumber", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightFrequencySuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public ArrayList<Route> getRouteList() {
        return (ArrayList<Route>)rpb.viewAllRoute();
    }

    public void setRouteList(ArrayList<Route> routeList) {
        this.routeList = routeList;
    }

    public Map<String, Long> getRouteInfo() {
        for(Route r: routeList) {
             routeInfo.put(r.toString(), r.getId());
        }
        return routeInfo;
    }

    public void setRouteInfo(Map<String, Long> routeInfo) {
        this.routeInfo = routeInfo;
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

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Integer getStopoverNo() {
        return stopoverNo;
    }

    public void setStopoverNo(Integer stopoverNo) {
        this.stopoverNo = stopoverNo;
    }

    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
    }

    public String getScheduleDepTime() {
        return scheduleDepTime;
    }

    public void setScheduleDepTime(String scheduleDepTime) {
        this.scheduleDepTime = scheduleDepTime;
    }

    public String getScheduleArrTime() {
        return scheduleArrTime;
    }

    public void setScheduleArrTime(String scheduleArrTime) {
        this.scheduleArrTime = scheduleArrTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ArrayList<String> getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(ArrayList<String> codeshare) {
        this.codeshare = codeshare;
    }

}
