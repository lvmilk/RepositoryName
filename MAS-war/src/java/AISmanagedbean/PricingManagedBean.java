/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.AIS.PricingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import java.lang.*;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author wang
 */
@Named(value = "pricingManagedBean")
@SessionScoped
public class PricingManagedBean implements Serializable {

    @EJB
    private PricingBeanLocal pb;

    private Route route;
    //Direct Cost
    private String type;

    //Construct a hashmap<String, Integer> to record <Cabinclass,SeatNo> 
    private Map<String, Integer> cabinInfo = new HashMap<String, Integer>();

    private Integer crewNo;
    private Double crewUnitCost;
    private Double crewCost;       //Per pax per block hour
    private Double fuelCost;       // per block hour 
    private Double ownershipCost;  //per block hour
    private Double maintenance;

    //per block hour
    //Indirect cost
    private Double adminCost; //per block hour
    private Double otherCost; //per block hour

    private Double totalCost;

    //Route Info
    private List<Route> routeList;
    private Map<String, Long> routeInfo = new HashMap<String, Long>();
    private String routeName;
    private Long routeID;
    private Double blockHour;   ///test 
    private Double distance;
    private Integer annualDepartures;
    private AircraftType aircraftType;

    //private Double econFactor;
    //private Double bizFactor;
    //private Double suiteFactor;
    //private Double firstFactor;
    //private Double pEconFactor; 
    private Map<String, Double> loadfactorMap = new HashMap<String, Double>();

            
    private Integer ecSeatNo;
    private Integer bcSeatNo;
    private Integer suiteNo;
    private Integer fcSeatNo;
    private Integer pecSeatNo;
    private Integer totalSeatNo;

    //Fare
//    private Integer ecFare;
//    private Integer bcFare;
//    private Integer suiteFare;
//    private Integer fcFare;
//    private Integer pecSFare;
    private Double expectedRev;
    private Double profitMargin;// profit margin=(revenue-cost)/cost
    private Map<String, Double> fareMap = new HashMap<String, Double>();
    private List<String> keyList = new ArrayList<String>();

    public PricingManagedBean() {

    }

    public Double getCrewUnitCost() {
        return crewUnitCost;
    }

    public void setCrewUnitCost(Double crewUnitCost) {
        this.crewUnitCost = crewUnitCost;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

 

    //test init()
//     @PostConstruct
//     public void init() {
//     getRouteList();
//     System.out.print("TEST: init run\n");
//    }
//    
    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Map<String, Double> getLoadfactorMap() {
        return loadfactorMap;
    }

    public void setLoadfactorMap(Double loadFactor) {
        this.loadfactorMap = loadfactorMap;
    }

//    public void addLoadFactor(String key, Double factor){
//        loadfactorMap.put()
//        
//    }
    public void checkFinal() throws IOException {   // set fare

        totalCost = pb.getCrewCost(crewNo, crewUnitCost, blockHour, annualDepartures) + getMaintenance() + getOwnershipCost() + getFuelCost() + getAdminCost() * blockHour * annualDepartures + getOtherCost() * blockHour * annualDepartures;
        setExpectedRev(totalCost * (profitMargin + 1));
        System.out.println("MB:calculated total cost: " + totalCost);
        int count = 0;
        //set every fare in Route database
        for (int i = 0; i < loadfactorMap.size(); i++) {
            String cabin = new String(keyList.get(i));
            //System.out.println("MB:Load factor for " + cabin + " read: " + loadfactorMap.get(cabin));
            //System.out.println(loadfactorMap.get(cabin) instanceof Double);
            //  System.out.println("MB:Data is of " + (loadfactorMap.get(cabin).getClass().getName()));
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!  "+expectedRev+" "+loadfactorMap.get(cabin)+" "+annualDepartures+" "+cabin);

            pb.calculateFare(expectedRev, totalSeatNo, loadfactorMap.get(cabin), annualDepartures, cabin);
            fareMap.put(cabin, pb.getFare());
            System.out.println("MB:Add in fare map!");
            count++;
        }
        //crewCost:pb.getCrewCost(Integer crewNo,Double crewUnitCost,Double blockHour,Integer annualDepartures)
        //maintenance:getMaintenance(); (annual cost already) 
        //ownershipCost:getOwnershipCost(); (annual cost already) 
        //FuelCost:getFuelCost(); (annual cost already) 
        //admin Cost getAdminCost()*blockHour*annualDepartures
        //other cost: getOtherCost()*blockHour*annualDepartures
        if (count == loadfactorMap.size()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./PricingSuccess.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please provide all information:  ", ""));
        }
    }

    public Map<String, Double> getFareMap() {
        return fareMap;
    }

    public void setFareMap(Map<String, Double> fareMap) {
        this.fareMap = fareMap;
    }

    public void checkRoute() throws IOException {
        if (routeID != null) {
            System.out.println(" Route selected is " + routeID);
            retrieveRouteInfo(routeID);
            retrieveAircraftTypeInfo(aircraftType);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./PricingAttribute2.xhtml");
        } else {
            System.out.println("No route is chosen");
        }

    }

    public void checkFactor() throws IOException {
        fuelCost = aircraftType.getFuelCost() * blockHour * annualDepartures;
        int count = 0;
        System.out.println("MB:Print loadFactor: " + loadfactorMap.toString());

        for (int i = 0; i < loadfactorMap.size(); i++) {
            System.out.println("MB:keyList.get(i): " + keyList.get(i) + "=" + loadfactorMap.get(keyList.get(i)));
            // System.out.println(String.valueOf(loadfactorMap.get(keyList.get(i))));
            // System.out.println(Double.valueOf(String.valueOf(loadfactorMap.get(i)))instanceof Double);
            System.out.println(Double.valueOf(String.valueOf(loadfactorMap.get(keyList.get(i)))));

            if (loadfactorMap.get(keyList.get(i)) != Double.valueOf(0.0)) {
                Double factor = Double.valueOf(String.valueOf(loadfactorMap.get(keyList.get(i))));
                loadfactorMap.remove(keyList.get(i));
                loadfactorMap.put(keyList.get(i), factor);
                count++;
                System.out.println("MB:count = " + count);
            }
        }
        if (count == loadfactorMap.size()) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./PricingAttribute3.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please provide all load factors:  ", ""));
        }

    }

    public void getRouteList() {
        routeList = pb.getRouteList();
        System.out.println("MB:getRouteList: list size: " + routeList.size());
        for (int i = 0; i < routeList.size(); i++) {
            routeInfo.put(routeList.get(i).getOrigin().getCityName() + "-" + routeList.get(i).getDest().getCityName(), routeList.get(i).getId());
            System.out.print("TEST: routeInfo added\n");
        }
    }

    public Map<String, Long> getRouteInfo() {
        getRouteList();
        System.out.print("TEST: getRouteInfo run\n");
        return routeInfo;
    }

    public void setRouteInfo(Map<String, Long> routeInfo) {
        this.routeInfo = routeInfo;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public void retrieveRouteInfo(Long ID) {
        annualDepartures = 0;
        route = pb.getRouteInfo(ID);
        //Set route Name

        routeName = route.getOrigin().getCityName() + "-" + route.getDest().getCityName();

        System.out.println("MB: route name: " + routeName);
        distance = route.getDistance();
        blockHour = route.getBlockhour();
        aircraftType = route.getAcType();
        System.out.println("AnnualDeaprture First: " + this.annualDepartures);
        System.out.println("size of FlightFrequency of this route: " + route.getFlightFreqList().size());
        for (FlightFrequency f : route.getFlightFreqList()) {
            annualDepartures = annualDepartures + f.getWeekFreq() * 52;
        }
        System.out.println("AnnualDeaprture Calculated: " + this.annualDepartures);
        System.out.println("routeInfo Retrieved!");
        System.out.println("routeInfo Retrieved! distance: " + distance);
        System.out.println("routeInfo Retrieved! Type: " + aircraftType.getManufacturer());
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        this.routeID = routeID;
    }

    public void retrieveAircraftTypeInfo(AircraftType aircraftType) {

        System.out.println("MB: Enter retrieveAircraftTypeInfo");
        ownershipCost = aircraftType.getPurchaseCost();
        if (aircraftType.getSuiteNo() > 0) {
            cabinInfo.put("Suite", aircraftType.getSuiteNo());
            loadfactorMap.put("Suite", 0.0);
            suiteNo = aircraftType.getSuiteNo();
        }
        if (aircraftType.getFcSeatNo() > 0) {
            cabinInfo.put("First Class", aircraftType.getFcSeatNo());
            loadfactorMap.put("First Class", 0.0);
            fcSeatNo = aircraftType.getFcSeatNo();
        }
        if (aircraftType.getBcSeatNo() > 0) {
            cabinInfo.put("Business Class", aircraftType.getBcSeatNo());
            loadfactorMap.put("Business Class", 0.0);
            bcSeatNo = aircraftType.getBcSeatNo();
        }
        if (aircraftType.getPecSeatNo() > 0) {
            cabinInfo.put("Premier Economy Class", aircraftType.getPecSeatNo());
            loadfactorMap.put("Premier Economy Class", 0.0);
            pecSeatNo = aircraftType.getPecSeatNo();
        }

        if (aircraftType.getEcSeatNo() > 0) {
            cabinInfo.put("Economy Class", aircraftType.getEcSeatNo());
            loadfactorMap.put("Economy Class", 0.0);
            ecSeatNo = aircraftType.getEcSeatNo();
        }

        totalSeatNo = suiteNo + fcSeatNo + ecSeatNo + pecSeatNo + bcSeatNo;
        System.out.println("MB: AircraftType Seat No: " + totalSeatNo);
        //  crewNo = pb.calculateCrewNo(totalSeatNo);
        Double crewNum = aircraftType.getCabinCrew()*totalSeatNo;
        Double purserNum = aircraftType.getCabinLeader()*totalSeatNo;
        crewNo = aircraftType.getCaptain()+aircraftType.getPilot() + purserNum.intValue() + crewNum.intValue();
        System.out.println("MB: AircraftType Crew No: " + crewNo);
        keyList = new ArrayList<String>(cabinInfo.keySet());
        System.out.println("MB: AircraftType key List Size: " + keyList.size());
        System.out.println("AircraftInfo Retrieved!");

    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Map<String, Integer> getCabinInfo() {
        return cabinInfo;
    }

    public void setCabinInfo(Map<String, Integer> cabinInfo) {
        this.cabinInfo = cabinInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getCrewCost() {
        return crewCost;
    }

    public void setCrewCost(Double crewCost) {
        this.crewCost = crewCost;
    }

    //Override setCrewCost
    public void setCrewCost(Integer crewNo, Double crewUnitCost, Double blockHour, Integer annualDepartures) {
        crewCost = pb.getCrewCost(crewNo, crewUnitCost, blockHour, annualDepartures);
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public Double getOwnershipCost() {
        return ownershipCost;
    }

    public void setOwnershipCost(Double ownershipCost) {
        this.ownershipCost = ownershipCost;
    }

    public Double getMaintenance() {
        return maintenance;
    }

    public void setMaintenance(Double maintenance) {
        this.maintenance = maintenance;
    }

    public Double getAdminCost() {
        return adminCost;
    }

    public void setAdminCost(Double adminCost) {
        this.adminCost = adminCost;
    }

    public Double getOtherCost() {
        return otherCost;
    }

    public void setOtherCost(Double otherCost) {
        this.otherCost = otherCost;
    }

    public Double getBlockHour() {
        return blockHour;
    }

    public void setBlockHour(Double blockHour) {
        this.blockHour = blockHour;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Integer getAnnualDepartures() {
        return annualDepartures;
    }

    public void setAnnualDepartures(Integer annualDepartures) {
        this.annualDepartures = annualDepartures;
    }

//    public Double getEconFactor() {
//        return econFactor;
//    }
//
//    public void setEconFactor(Double econFactor) {
//        this.econFactor = econFactor;
//    }
//
//    public Double getBizFactor() {
//        return bizFactor;
//    }
//
//    public void setBizFactor(Double bizFactor) {
//        this.bizFactor = bizFactor;
//    }
//
//    public Double getSuiteFactor() {
//        return suiteFactor;
//    }
//
//    public void setSuiteFactor(Double suiteFactor) {
//        this.suiteFactor = suiteFactor;
//    }
//
//    public Double getFirstFactor() {
//        return firstFactor;
//    }
//
//    public void setFirstFactor(Double firstFactor) {
//        this.firstFactor = firstFactor;
//    }
//
//    public Double getpEconFactor() {
//        return pEconFactor;
//    }
//
//    public void setpEconFactor(Double pEconFactor) {
//        this.pEconFactor = pEconFactor;
//    }
    public Integer getSuiteNo() {
        return suiteNo;
    }

    public void setSuiteNo(Integer suiteNo) {
        this.suiteNo = suiteNo;
    }

    public Integer getEcSeatNo() {
        return ecSeatNo;
    }

    public void setEcSeatNo(Integer ecSeatNo) {
        this.ecSeatNo = ecSeatNo;
    }

    public Integer getBcSeatNo() {
        return bcSeatNo;
    }

    public void setBcSeatNo(Integer bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    public Integer getFcSeatNo() {
        return fcSeatNo;
    }

    public void setFcSeatNo(Integer fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    public Integer getPecSeatNo() {
        return pecSeatNo;
    }

    public void setPecSeatNo(Integer pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    public Integer getTotalSeatNo() {
        return totalSeatNo;
    }

    public void setTotalSeatNo(Integer totalSeatNo) {
        this.totalSeatNo = totalSeatNo;
    }

    public Double getExpectedRev() {
        return expectedRev;
    }

    public void setExpectedRev(Double expectedRev) {
        this.expectedRev = expectedRev;
    }

    public void goBack() throws IOException {
        routeID = null;
        this.setProfitMargin(0.0);
        this.setAdminCost(0.0);
        this.setCrewUnitCost(0.0);
        this.setOtherCost(0.0);
        this.setMaintenance(0.0);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./PricingAttribute1.xhtml");
    }

}
