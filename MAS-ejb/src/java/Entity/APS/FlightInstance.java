/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Xu
 */
public class FlightInstance implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private FlightSchedule flightSchedule;
    private String date;
    private String opStatus;
    private String flightStatus;
    private String estimatedDepTime;
    private String estimatedArrTime;
    private String actualDepTime;
    private String actualArrTime;
    
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Aircraft aircraft;
    
//    private FlightPackage flightPackage;

    public void create (FlightSchedule flightSchedule, String date){
        this.flightSchedule = flightSchedule;
        this.date = date;
}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightSchedule getFlightSchedule() {
        return flightSchedule;
    }

    public void setFlightSchedule(FlightSchedule flightSchedule) {
        this.flightSchedule = flightSchedule;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public String getEstimatedDepTime() {
        return estimatedDepTime;
    }

    public void setEstimatedDepTime(String estimatedDepTime) {
        this.estimatedDepTime = estimatedDepTime;
    }

    public String getEstimatedArrTime() {
        return estimatedArrTime;
    }

    public void setEstimatedArrTime(String estimatedArrTime) {
        this.estimatedArrTime = estimatedArrTime;
    }

    public String getActualDepTime() {
        return actualDepTime;
    }

    public void setActualDepTime(String actualDepTime) {
        this.actualDepTime = actualDepTime;
    }

    public String getActualArrTime() {
        return actualArrTime;
    }

    public void setActualArrTime(String actualArrTime) {
        this.actualArrTime = actualArrTime;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }
    
    
    
}
