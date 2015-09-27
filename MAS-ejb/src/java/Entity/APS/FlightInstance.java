/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Xu
 */
@Entity
public class FlightInstance implements Serializable{
     private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private FlightFrequency flightFrequency;
    
    private LocalDate date;
    private String opStatus;
    private String flightStatus;
    private LocalTime estimatedDepTime;
    private LocalTime estimatedArrTime;
    private LocalTime actualDepTime;
    private LocalTime actualArrTime;
    
    @ManyToOne
    private Aircraft aircraft;
    
    @ManyToOne
    private FlightPackage flightPackage;

    public void create (FlightFrequency flightFrequency, LocalDate date){
        this.flightFrequency = flightFrequency;
        this.date = date;
}
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FlightFrequency getFlightFrequency() {
        return flightFrequency;
    }

    public void setFlightFrequency(FlightFrequency flightFrequency) {
        this.flightFrequency = flightFrequency;
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

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public FlightPackage getFlightPackage() {
        return flightPackage;
    }

    public void setFlightPackage(FlightPackage flightPackage) {
        this.flightPackage = flightPackage;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEstimatedDepTime() {
        return estimatedDepTime;
    }

    public void setEstimatedDepTime(LocalTime estimatedDepTime) {
        this.estimatedDepTime = estimatedDepTime;
    }

    public LocalTime getEstimatedArrTime() {
        return estimatedArrTime;
    }

    public void setEstimatedArrTime(LocalTime estimatedArrTime) {
        this.estimatedArrTime = estimatedArrTime;
    }

    public LocalTime getActualDepTime() {
        return actualDepTime;
    }

    public void setActualDepTime(LocalTime actualDepTime) {
        this.actualDepTime = actualDepTime;
    }

    public LocalTime getActualArrTime() {
        return actualArrTime;
    }

    public void setActualArrTime(LocalTime actualArrTime) {
        this.actualArrTime = actualArrTime;
    }
    

}
