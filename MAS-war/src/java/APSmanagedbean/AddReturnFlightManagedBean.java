/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
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
@Named(value = "ARFMB")
@ViewScoped
public class AddReturnFlightManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private RoutePlanningBeanLocal rpb;

    private String destAirportString;
    private String oriAirportString;
    private Date inDepTime;
    private Date inArrTime;
    private String inDepTimeString;
    private String inArrTimeString;
    private String inDateAdjustString;
    private Integer inDateAdjust;
    private Date inStartDate;
    private Date inEndDate;
    private String inStartDateString;
    private String inEndDateString;
    private boolean inOnMon;
    private boolean inOnTue;
    private boolean inOnWed;
    private boolean inOnThu;
    private boolean inOnFri;
    private boolean inOnSat;
    private boolean inOnSun;

    private String inFlightNo;
    private Route outRoute;
    private Route inRoute;
    private FlightFrequency inBound;

    private String depTerminal;
    private String arrTerminal;

    public AddReturnFlightManagedBean() {
    }

    @PostConstruct
    public void init() {
        oriAirportString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("destAirportString");
        destAirportString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("oriAirportString");
        inStartDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDateString");
        inEndDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDateString");
        outRoute = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("outRoute");
        arrTerminal = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("depTerminal");
        depTerminal = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("arrTerminal");
    }

    public void addReturnFlightFrequency(ActionEvent e) throws Exception {
        try {
            inRoute = rpb.viewRoute(oriAirportString, destAirportString);
            if (inRoute.getAcType() != null) {
            } else {
                inRoute.setAcType(outRoute.getAcType());
            }
            Format formatter2 = new SimpleDateFormat("HH:mm");
            fsb.validateFlightNo(inFlightNo);
            if (inFlightNo.length() != 5 || inFlightNo.charAt(0) != 'M' || inFlightNo.charAt(1) != 'R' || !Character.isDigit(inFlightNo.charAt(2)) || !Character.isDigit(inFlightNo.charAt(3)) || !Character.isDigit(inFlightNo.charAt(4))) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Flight Number: Please enter a flight No. in format of MRxxx, x is a digit, e.g. MR123", ""));
            }
            if (!(inOnMon || inOnTue || inOnWed || inOnThu || inOnFri || inOnSat || inOnSun)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operation Day of the Week: Please select as least one day of the week for flight.", ""));
            } else {
                inDateAdjust = Integer.parseInt(inDateAdjustString);

                inDepTimeString = formatter2.format(inDepTime);
                inArrTimeString = formatter2.format(inArrTime);

                inBound = fsb.addFlightFrequency(inRoute, inFlightNo, inDepTimeString, inArrTimeString, inDateAdjust, inOnMon, inOnTue, inOnWed, inOnThu, inOnFri, inOnSat, inOnSun, inStartDateString, inEndDateString, "", "", depTerminal, arrTerminal);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("successReturnFlightNo", inFlightNo);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightFrequencySuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

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

    public Date getInDepTime() {
        return inDepTime;
    }

    public void setInDepTime(Date inDepTime) {
        this.inDepTime = inDepTime;
    }

    public Date getInArrTime() {
        return inArrTime;
    }

    public void setInArrTime(Date inArrTime) {
        this.inArrTime = inArrTime;
    }

    public String getInDepTimeString() {
        return inDepTimeString;
    }

    public void setInDepTimeString(String inDepTimeString) {
        this.inDepTimeString = inDepTimeString;
    }

    public String getInArrTimeString() {
        return inArrTimeString;
    }

    public void setInArrTimeString(String inArrTimeString) {
        this.inArrTimeString = inArrTimeString;
    }

    public String getInDateAdjustString() {
        return inDateAdjustString;
    }

    public void setInDateAdjustString(String inDateAdjustString) {
        this.inDateAdjustString = inDateAdjustString;
    }

    public Integer getInDateAdjust() {
        return inDateAdjust;
    }

    public void setInDateAdjust(Integer inDateAdjust) {
        this.inDateAdjust = inDateAdjust;
    }

    public Date getInStartDate() {
        return inStartDate;
    }

    public void setInStartDate(Date inStartDate) {
        this.inStartDate = inStartDate;
    }

    public Date getInEndDate() {
        return inEndDate;
    }

    public void setInEndDate(Date inEndDate) {
        this.inEndDate = inEndDate;
    }

    public String getInStartDateString() {
        return inStartDateString;
    }

    public void setInStartDateString(String inStartDateString) {
        this.inStartDateString = inStartDateString;
    }

    public String getInEndDateString() {
        return inEndDateString;
    }

    public void setInEndDateString(String inEndDateString) {
        this.inEndDateString = inEndDateString;
    }

    public boolean isInOnMon() {
        return inOnMon;
    }

    public void setInOnMon(boolean inOnMon) {
        this.inOnMon = inOnMon;
    }

    public boolean isInOnTue() {
        return inOnTue;
    }

    public void setInOnTue(boolean inOnTue) {
        this.inOnTue = inOnTue;
    }

    public boolean isInOnWed() {
        return inOnWed;
    }

    public void setInOnWed(boolean inOnWed) {
        this.inOnWed = inOnWed;
    }

    public boolean isInOnThu() {
        return inOnThu;
    }

    public void setInOnThu(boolean inOnThu) {
        this.inOnThu = inOnThu;
    }

    public boolean isInOnFri() {
        return inOnFri;
    }

    public void setInOnFri(boolean inOnFri) {
        this.inOnFri = inOnFri;
    }

    public boolean isInOnSat() {
        return inOnSat;
    }

    public void setInOnSat(boolean inOnSat) {
        this.inOnSat = inOnSat;
    }

    public boolean isInOnSun() {
        return inOnSun;
    }

    public void setInOnSun(boolean inOnSun) {
        this.inOnSun = inOnSun;
    }

    public String getInFlightNo() {
        return inFlightNo;
    }

    public void setInFlightNo(String inFlightNo) {
        this.inFlightNo = inFlightNo;
    }

    public Route getInRoute() {
        return inRoute;
    }

    public void setInRoute(Route inRoute) {
        this.inRoute = inRoute;
    }

    public Route getOutRoute() {
        return outRoute;
    }

    public void setOutRoute(Route outRoute) {
        this.outRoute = outRoute;
    }

    public FlightFrequency getInBound() {
        return inBound;
    }

    public void setInBound(FlightFrequency inBound) {
        this.inBound = inBound;
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

}
