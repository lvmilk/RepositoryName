/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import Entity.AIS.FlightCabin;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date standardDepTimeDateType;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date standardArrTimeDateType;
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
//    private Date localDepTime;
//    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
//    private Date localArrTime;

    private String depGate;

    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "flightinstance_cabincrew")
    private List<CabinCrew> cabinList;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "flightinstance_cockpitcrew")
    private List<CockpitCrew> cockpitList;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "flightinstance_cabincrewstandby")
    private List<CabinCrew> cabinStandByList;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    @JoinTable(name = "flightinstance_cockpitcrewstandby")
    private List<CockpitCrew> cockpitStandByList;

    @ManyToOne
    private Aircraft aircraft = new Aircraft();

    @ManyToOne
    private FlightFrequency flightFrequency = new FlightFrequency();

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
        this.depGate = "Not assigned";
//        this.setLocalDepTime(getLocalDepTime());
//        this.setLocalArrTime(getLocalArrTime());
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

//    public Date getLocalDepTime() {
//        String depTZ = this.getFlightFrequency().getRoute().getOrigin().getTimeZone();
//        System.out.println("############################ #########################   depTZ : " + depTZ);
//        Date depLocal = new Date();
//        Integer depOffset = 0;
//        System.out.println("############################ #########################   depTZ.charAt(3) : " + depTZ.charAt(3));
//        switch (depTZ.charAt(3)) {
//            case '-': {
//                String offset = depTZ.substring(4, 6);
//                System.out.println("############################ #########################   offset : " + offset);
//                depOffset = 0 - Integer.valueOf(offset);
//                System.out.println("############################ #########################   depOffset : " + depOffset);
//                Calendar c = Calendar.getInstance();
//                c.setTime(this.getStandardDepTimeDateType());
//                System.out.println("############################ #########################   this.getStandardDepTimeDateType() : " + this.getStandardDepTimeDateType());
//                c.add(Calendar.HOUR, depOffset);
//                depLocal = c.getTime();
//                System.out.println("############################ #########################   depLocal : " + depLocal);
//                break;
//            }
//            case '+': {
//                String offset = depTZ.substring(4, 6);
//                depOffset = Integer.valueOf(offset);
//                Calendar c = Calendar.getInstance();
//                c.setTime(this.getStandardDepTimeDateType());
//                c.add(Calendar.HOUR, depOffset);
//                depLocal = c.getTime();
//                break;
//            }
//        }
//        return depLocal;
//    }
//
//    public void setLocalDepTime(Date localDepTime) {
//        this.localDepTime = localDepTime;
//    }
//    public Date getLocalArrTime() {
//        String arrTZ = this.getFlightFrequency().getRoute().getDest().getTimeZone();
//        System.out.println("############################ #########################   arrTZ " + arrTZ);
//        String offset = arrTZ.substring(4, 6);
//        Date arrLocal = new Date();
//        Integer arrOffset = 0;
//        switch (arrTZ.charAt(3)) {
//            case '-': {
//                arrOffset = 0 - Integer.valueOf(offset);
//                Calendar c = Calendar.getInstance();
//                c.setTime(this.getStandardArrTimeDateType());
//                c.add(Calendar.HOUR, arrOffset);
//                arrLocal = c.getTime();
//                break;
//            }
//            case '+': {
//                arrOffset = Integer.valueOf(offset);
//                Calendar c = Calendar.getInstance();
//                c.setTime(this.getStandardArrTimeDateType());
//                c.add(Calendar.HOUR, arrOffset);
//                arrLocal = c.getTime();
//                break;
//            }
//        }
//        return arrLocal;
//    }
//
//    public void setLocalArrTime(Date localArrTime) {
//        this.localArrTime = localArrTime;
//    }
    public List<CabinCrew> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinCrew> cabinList) {
        this.cabinList = cabinList;
    }

    public List<CockpitCrew> getCockpitList() {
        return cockpitList;
    }

    public void setCockpitList(List<CockpitCrew> cockpitList) {
        this.cockpitList = cockpitList;
    }

    public List<CabinCrew> getCabinStandByList() {
        return cabinStandByList;
    }

    public void setCabinStandByList(List<CabinCrew> cabinStandByList) {
        this.cabinStandByList = cabinStandByList;
    }

    public List<CockpitCrew> getCockpitStandByList() {
        return cockpitStandByList;
    }

    public void setCockpitStandByList(List<CockpitCrew> cockpitStandByList) {
        this.cockpitStandByList = cockpitStandByList;
    }

    public String getDepGate() {
        return depGate;
    }

    public void setDepGate(String depGate) {
        this.depGate = depGate;
    }

//    @Override
//    public int compareTo(FlightInstance fi) {
//        LocalTime thisTime = LocalTime.parse(this.standardDepTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        LocalTime fiTime = LocalTime.parse(fi.getStandardDepTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
//        // thisTime<fiTime return -1
//        return thisTime.compareTo(fiTime);
//    }
    //   @Override
//    public int compareTo(FlightInstance fi) {
//        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date thisTime = new Date();
//         Date fiTime = new Date();
//        try {
//            thisTime = df1.parse(this.standardDepTime);
//            fiTime = df1.parse(fi.standardDepTime);  
//        } catch (ParseException ex) {
//            Logger.getLogger(FlightInstance.class.getName()).log(Level.SEVERE, null, ex);
//        }       
//        // thisTime<fiTime return -1
//       return thisTime.compareTo(fiTime);
//    }
    @Override
    public int compareTo(FlightInstance fi) {
        // System.err.println("**********Enter compare");
        int result = 0;
        if (this.getStandardDepTimeDateType().before(fi.getStandardDepTimeDateType())) {
            result = -1;
        } else if (this.getStandardDepTimeDateType().after(fi.getStandardDepTimeDateType())) {
            result = 1;
        }
        // System.err.println("********** compare 1 : " + this.getStandardDepTimeDateType().toString()+" to "+ fi.getStandardDepTimeDateType().toString());
        // System.err.println("********** compareTo: " + result);
        return result;
    }

    public String toString() {
        return this.getFlightFrequency().getFlightNo() + " : " + this.getStandardDepTimeDateType() + "-" + this.getStandardArrTimeDateType();

    }

}
