/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import Entity.aisEntity.FlightCabin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Xu/Lu Xi
 */
@Entity
public class FlightInstance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected

    //will be modified later
    private String estimatedDepTime;
    private String estimatedArrTime;
    private String actualDepTime;
    private String actualArrTime;

    @ManyToOne
    private Aircraft aircraft = new Aircraft();

    @ManyToOne
    private FlightFrequency flightFrequency;

    @ManyToOne
    private FlightPackage flightPackage;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "flightInstance")
    private List<FlightCabin> flightCabins = new ArrayList<>();

    public void create(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, String actualDepTime, String actualArrTime) {
        this.flightFrequency = flightFrequency;
        this.date = date;
        this.flightStatus = "Scheduled";
        this.estimatedDepTime = estimatedDepTime;
        this.estimatedArrTime = estimatedArrTime;
        this.actualDepTime = actualDepTime;
        this.actualArrTime = actualArrTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<FlightCabin> getFlightCabins() {
        return flightCabins;
    }

    public void setFlightCabins(List<FlightCabin> flightCabins) {
        this.flightCabins = flightCabins;
    }

    public FlightFrequency getFlightFrequency() {
        return flightFrequency;
    }

    public void setFlightFrequency(FlightFrequency flightFrequency) {
        this.flightFrequency = flightFrequency;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

}
