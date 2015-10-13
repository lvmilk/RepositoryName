/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.aisEntity.FlightCabin;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "editFIIMB")
@ViewScoped
public class EditFlightInstanceInfoManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<FlightFrequency> flightFreqList;
    private List<FlightInstance> flightInstList;
    private FlightFrequency flightFreq;
    private FlightInstance flightInst;

    //private Date flightDate;
    private String flightDateString;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected
    private Date estimatedDepTime;
    private Date estimatedArrTime;
    private Integer estimatedDateAdjust;
    private Date actualDepTime;
    private Date actualArrTime;
    private Integer actualDateAdjust;

    private String flightNo;

    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df2 = new SimpleDateFormat("HH:mm");

    public EditFlightInstanceInfoManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightFreq");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        //flightDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDate");
        flightDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDateString");
        flightStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightStatus");
        estimatedDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDepTime");
        estimatedArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedArrTime");
        estimatedDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDateAdjust");
        actualDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDepTime");
        actualArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualArrTime");
        actualDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDateAdjust");
        flightInst = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightInst");
        flightInstList = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightInstList");

//        FacesContext.getCurrentInstance().getExternalContext()
        if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flightFrequency") != null) {
            flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flightFrequency");
            flightInst = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flightInstance");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "This flight " + flightFreq.getFlightNo() + " on " + flightInst.getDate() + " has reserved seates. Please handle affected customers! ", ""));
        }
    }

    public void beforePhaseListener(PhaseEvent event) {

    }

    public void editFlightInstanceInfo() {
        try {
            String ed = df2.format(estimatedDepTime);
            String ea = df2.format(estimatedArrTime);
            String ad = df2.format(actualDepTime);
            String aa = df2.format(actualArrTime);
            System.out.println("editFlightInstanceInfo: flight date is " + flightDateString);
            System.out.println("edit flight info managed bean: edit flight instance: flight frequency: " + flightFreq);
            flightInst = fsb.findFlight(flightFreq.getFlightNo(), flightDateString);
            System.out.println("edit flight info managed bean: edit flight instance: flight instance: " + flightInst);
            ///////////////////////////////////////////////////////////////////////
            Integer bookedSeat = 0;
            List<FlightCabin> flightCabinList = new ArrayList<>();
            flightCabinList = flightInst.getFlightCabins();
            Integer cabinListSize = flightCabinList.size();
            System.out.println("edit flight info managed bean: edit flight instance with cabins : " + flightCabinList);
            for (int i = 0; i < cabinListSize; i++) {
                bookedSeat = bookedSeat + flightCabinList.get(i).getBookedSeat();
            }
            System.out.println("edit flight info managed bean: edit flight instance: number of booked seats: " + bookedSeat);
            
            if ((bookedSeat != 0) && flightStatus.equals("Cancelled")) {
                System.out.println("edit flight info managed bean: edit flight instance: number of booked seats: WARNING!!! " + bookedSeat);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "This flight " + flightFreq.getFlightNo() + " on " + flightInst.getDate() + " has reserved seates. Please handle affected customers! ", ""));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("flightFrequency", flightFreq);
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("flightInstance", flightInst);
            }
            ///////////////////////////////////////////////////////////////////////
            fsb.editFlightInstance(flightFreq, flightDateString, flightStatus, ed, ea, estimatedDateAdjust, ad, aa, actualDateAdjust);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightInstanceDone.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editFlightInstBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editFlightInstanceDetail.xhtml");
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

//    public Date getFlightDate() {
//        return flightDate;
//    }
//
//    public void setFlightDate(Date flightDate) {
//        this.flightDate = flightDate;
//    }
    public String getFlightDateString() {
        return flightDateString;
    }

    public void setFlightDateString(String flightDateString) {
        this.flightDateString = flightDateString;
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
