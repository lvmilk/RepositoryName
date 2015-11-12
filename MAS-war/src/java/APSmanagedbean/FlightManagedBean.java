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
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "flightManagedBean")
@ViewScoped
public class FlightManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private RoutePlanningBeanLocal rpb;

    private FlightFrequency flightFreq;
    private FlightFrequency inBound; // flight XXX-SIN
    private FlightFrequency outBound; // flight SIN-XXX
    private FlightInstance flightInst;
    private Route route;
    private Long routeID;
    private List<Route> routeList;
    private Map<String, Long> routeInfo = new HashMap<String, Long>();

    private Integer stopoverNo;
    private String flightNo;

    private Date depTime;
    private Date arrTime;
    private String depTimeString;
    private String arrTimeString;
    private String dateAdjustString;
    private Integer dateAdjust;

    private Date firstGenerationDate;
    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

//    private List<Airport> airportWoSg;
    private String destAirportString;
    private String oriAirportString;
//    private List<String> airportWoSgString;
    // for code share flights
//    private String operator;
//    private List<String> codeshare;
    private List<FlightFrequency> flightFreqList;
    private List<FlightFrequency> filteredFlightFreqList;

    private String depTerminal;
    private String arrTerminal;
    private String depGate;

    public FlightManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
    }

    public void addFlightFrequency(ActionEvent e) throws Exception {
        try {
            route = rpb.findRoute(routeID);
            oriAirportString = route.getOrigin().getIATA();
            destAirportString = route.getDest().getIATA();
            Format formatter = new SimpleDateFormat("yyyy-MM-dd");
            Format formatter2 = new SimpleDateFormat("HH:mm");
            firstGenerationDate = new Date();
            System.out.println("fmb.addFlightFrequency(): date of setting the first generation date " + firstGenerationDate);
            fsb.validateFlightNo(flightNo);
            if (rpb.feasibleAc(route).isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Cannot add flight frequency. No aircraft types are feasible for route " + route, ""));
            } else if (flightNo.length() != 5 || flightNo.charAt(0) != 'M' || flightNo.charAt(1) != 'R' || !Character.isDigit(flightNo.charAt(2)) || !Character.isDigit(flightNo.charAt(3)) || !Character.isDigit(flightNo.charAt(4))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Flight Number: Please enter a flight No. in format of MRxxx, x is a digit, e.g. MR123", ""));
            } else if (!(onMon || onTue || onWed || onThu || onFri || onSat || onSun)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Operation Day of the Week: Please select as least one day of the week for flight.", ""));
            } else {
                if (route.getAcType() != null) {
//if(rpb.viewRoute(destAirportString, oriAirportString)) {}
                    dateAdjust = Integer.parseInt(dateAdjustString);
                    startDateString = formatter.format(startDate);
                    endDateString = formatter.format(endDate);
                    //default value for checking
                    String sd = "";
                    String fd = "";
                    depTimeString = formatter2.format(depTime);
                    System.out.println("fmb.addFlightFrequency(): depTimeString: " + depTimeString);
                    arrTimeString = formatter2.format(arrTime);
                    System.out.println("fmb.addFlightFrequency(): arrTimeString: " + arrTimeString);
                    fsb.addFlightFrequency(route, flightNo, depTimeString, arrTimeString, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDateString, endDateString, sd, fd, depTerminal, arrTerminal);

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("successFlightNo", flightNo);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("oriAirportString", oriAirportString);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("destAirportString", destAirportString);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDateString", startDateString);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDateString", endDateString);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("outRoute", route);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("depTerminal", depTerminal);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("arrTerminal", arrTerminal);

                    FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightFrequencyReturn.xhtml");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Route " + route + " has not set serving aircraft type, please set the aircraft type before adding flight for the route.", ""));
                }
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editFlightFrequency(FlightFrequency flightFreq) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editFlightFreq", flightFreq);
        System.out.println("FlightManagedBean.editFlightFrequency(): flightFreq to be passed to EFFMB is: " + flightFreq);
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        depTime = formatter.parse(flightFreq.getScheduleDepTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editFlightDepTime", depTime);
        System.out.println("FlightManagedBean.editFlightFrequency(): depTime to be passed to EFFMB is: " + depTime);
        arrTime = formatter.parse(flightFreq.getScheduleArrTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editFlightArrTime", arrTime);
        DateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
        startDate = formatter2.parse(flightFreq.getStartDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editFlightStartDate", startDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstGenerationDate", firstGenerationDate);
        endDate = formatter2.parse(flightFreq.getEndDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editFlightEndDate", endDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateAdjust", flightFreq.getDateAdjust().toString());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("depTerminal", depTerminal);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("arrTerminal", arrTerminal);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightFrequencyDetail.xhtml");
    }

    public List<FlightFrequency> getFlightFreqList() {
        return flightFreqList;
    }

    public void setFlightFreqList(List<FlightFrequency> flightFreqList) {
        this.flightFreqList = flightFreqList;
    }

    public List<FlightFrequency> getFilteredFlightFreqList() {
        return filteredFlightFreqList;
    }

    public void setFilteredFlightFreqList(List<FlightFrequency> filteredFlightFreqList) {
        this.filteredFlightFreqList = filteredFlightFreqList;
    }

    public List<Route> getRouteList() {
        return rpb.viewAllRoute();
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public Map<String, Long> getRouteInfo() {
        routeList = getRouteList();
        for (Route r : routeList) {
            routeInfo.put(r.toString(), r.getId());
        }
        System.out.println(routeInfo.toString());
        return routeInfo;
    }

    public void setRouteInfo(Map<String, Long> routeInfo) {
        this.routeInfo = routeInfo;
    }

    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        this.routeID = routeID;
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

    public String getDateAdjustString() {
        return dateAdjustString;
    }

    public void setDateAdjustString(String dateAdjustString) {
        this.dateAdjustString = dateAdjustString;
    }

    public Date getDepTime() {
        return depTime;
    }

    public void setDepTime(Date depTime) {
        this.depTime = depTime;
    }

    public Date getArrTime() {
        return arrTime;
    }

    public void setArrTime(Date arrTime) {
        this.arrTime = arrTime;
    }

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
    }

    public String getArrTimeString() {
        return arrTimeString;
    }

    public void setArrTimeString(String arrTimeString) {
        this.arrTimeString = arrTimeString;
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

//    public String getOperator() {
//        return operator;
//    }
//
//    public void setOperator(String operator) {
//        this.operator = operator;
//    }
//
//    public List<String> getCodeshare() {
//        return codeshare;
//    }
//
//    public void setCodeshare(List<String> codeshare) {
//        this.codeshare = codeshare;
//    }
    public FlightFrequency getInBound() {
        return inBound;
    }

    public void setInBound(FlightFrequency inBound) {
        this.inBound = inBound;
    }

    public FlightFrequency getOutBound() {
        return outBound;
    }

    public void setOutBound(FlightFrequency outBound) {
        this.outBound = outBound;
    }

//    public List<Airport> getAirportWoSg() {
//        List<Airport> apList = rpb.viewAllAirport();
//        Airport sinAp = rpb.findAirport("SIN");
//        apList.remove(sinAp);
//        return apList;
//    }
//
//    public void setAirportWoSg(List<Airport> airportWoSg) {
//        this.airportWoSg = airportWoSg;
//    }
//    public List<String> getAirportWoSgString() {
//        List<Airport> apList = getAirportWoSg();
//        List<String> apString = new ArrayList<>();
//        for (Airport ap : apList) {
//            apString.add(ap.toString());
//        }
//        return apString;
//    }
//
//    public void setAirportWoSgString(List<String> airportWoSgString) {
//        this.airportWoSgString = airportWoSgString;
//    }
    public String getDestAirportString() {
        return destAirportString;
    }

    public void setDestAirportString(String destAirportString) {
        this.destAirportString = destAirportString;
    }

    public String getOriAirportString() {
        return oriAirportString;
    }

    public void setOriAirportString(String oriAirportString) {
        this.oriAirportString = oriAirportString;
    }

    public Date getFirstGenerationDate() {
        return firstGenerationDate;
    }

    public void setFirstGenerationDate(Date firstGenerationDate) {
        this.firstGenerationDate = firstGenerationDate;
    }

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
    }

    public String getDepGate() {
        return depGate;
    }

    public void setDepGate(String depGate) {
        this.depGate = depGate;
    }

}
