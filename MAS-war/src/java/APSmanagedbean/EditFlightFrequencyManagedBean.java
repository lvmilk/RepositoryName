/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "EFFMB")
@ViewScoped
public class EditFlightFrequencyManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;

    private FlightFrequency flightFreq;
    private Route route;
    private String flightNo;
    private Integer stopoverNo;

    private Date depTime;
    private Date arrTime;
    private String depTimeString;
    private String arrTimeString;
    private String dateAdjustString;
    private Integer dateAdjust;

    private Date startDate;
    private Date endDate;
    private Date endDateOld;
    private String startDateString;
    private String endDateString;
    private String endDateOldString;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    public EditFlightFrequencyManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightFreq");
        flightNo = flightFreq.getFlightNo();
        depTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightDepTime");
        arrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightArrTime");
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightStartDate");
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = formatter.format(startDate);
        endDateOld = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightEndDate");
        endDateOldString = formatter.format(endDateOld);
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editFlightEndDate");
        dateAdjustString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateAdjust");
        onMon = flightFreq.isOnMon();
        onTue = flightFreq.isOnTue();
        onWed = flightFreq.isOnWed();
        onThu = flightFreq.isOnThu();
        onFri = flightFreq.isOnFri();
        onSat = flightFreq.isOnSat();
        onSun = flightFreq.isOnSun();
    }

    public void editFlightFrequencyDetail() throws Exception {
        try {
            if (!(onMon || onTue || onWed || onThu || onFri || onSat || onSun)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Operation Day of the Week: Please select as least one day of the week.", ""));
            } else if (endDate.before(endDateOld)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: End Operation Date: The end operation date cannot be later than the initial value " + endDateOldString, ""));
            } else {
                dateAdjust = Integer.parseInt(dateAdjustString);
                Format formatter = new SimpleDateFormat("yyyy-MM-dd");
                startDateString = formatter.format(startDate);
                endDateString = formatter.format(endDate);
                Format formatter2 = new SimpleDateFormat("HH:mm");
                depTimeString = formatter2.format(depTime);
                arrTimeString = formatter2.format(arrTime);
                fsb.editFlightFrequency(flightNo, depTimeString, arrTimeString, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDateString, endDateString);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightFrequencySuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editFlightFrequencyCancel() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightFrequency.xhtml");
    }

    public FlightFrequency getFlightFreq() {
        return flightFreq;
    }

    public void setFlightFreq(FlightFrequency flightFreq) {
        this.flightFreq = flightFreq;
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

    public String getDateAdjustString() {
        return dateAdjustString;
    }

    public void setDateAdjustString(String dateAdjustString) {
        this.dateAdjustString = dateAdjustString;
    }

    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
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

    public Date getEndDateOld() {
        return endDateOld;
    }

    public void setEndDateOld(Date endDateOld) {
        this.endDateOld = endDateOld;
    }

}
