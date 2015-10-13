/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import Entity.aisEntity.FlightCabin;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author Xu/Lu Xi
 */
@Entity
public class FlightInstance implements Serializable, Comparable<FlightInstance> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String date;
    private String startDate;
    private String finishDate;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected

    //will be modified later
    private String estimatedDepTime;
    private String estimatedArrTime;
    private Integer estimatedDateAdjust;
    private String actualDepTime;
    private String actualArrTime;
    private Integer actualDateAdjust;
    private String standardDepTime;
    private String standardArrTime;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date standardDepTimeDateType;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date standardArrTimeDateType;

    @ManyToOne
    private Aircraft aircraft = new Aircraft();

    @ManyToOne
    private FlightFrequency flightFrequency = new FlightFrequency();

//    @ManyToOne
//    private FlightPackage flightPackage;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "flightInstance")
    private List<FlightCabin> flightCabins = new ArrayList<>();

    public void create(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust,
            String actualDepTime, String actualArrTime, Integer actualDateAdjust) {
        this.flightFrequency = flightFrequency;

//        this.startDate=startDate;
//        this.finishDate=finishDate;
        this.date = date;
        this.flightStatus = "Scheduled";
        this.estimatedDepTime = estimatedDepTime;
        this.estimatedArrTime = estimatedArrTime;
        this.estimatedDateAdjust = estimatedDateAdjust;
        this.actualDepTime = actualDepTime;
        this.actualArrTime = actualArrTime;
        this.actualDateAdjust = actualDateAdjust;
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

//    public FlightPackage getFlightPackage() {
//        return flightPackage;
//    }
//
//    public void setFlightPackage(FlightPackage flightPackage) {
//        this.flightPackage = flightPackage;
//    }
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getStandardDepTime() {
        return standardDepTime;
    }

    public void setStandardDepTime(String standardDepTime) {
        this.standardDepTime = standardDepTime;
    }

    public String getStandardArrTime() {
        return standardArrTime;
    }

    public void setStandardArrTime(String standardArrTime) {
        this.standardArrTime = standardArrTime;
    }

    @Override
    public int compareTo(FlightInstance fi) {
        LocalTime thisTime = LocalTime.parse(this.standardDepTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        LocalTime fiTime = LocalTime.parse(fi.getStandardDepTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        // thisTime<fiTime return -1
        return thisTime.compareTo(fiTime);
    }

    public Date getStandardDepTimeDateType() {
        return standardDepTimeDateType;
    }

    public void setStandardDepTimeDateType(Date standardDepTimeDateType) {
        this.standardDepTimeDateType = standardDepTimeDateType;
    }

    public Date getStandardArrTimeDateType() {
        return standardArrTimeDateType;
    }

    public void setStandardArrTimeDateType(Date standardArrTimeDateType) {
        this.standardArrTimeDateType = standardArrTimeDateType;
    }

}
