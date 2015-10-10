/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import SessionBean.AirlineInventory.PricingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author wang
 */
@Named(value = "viewPricingManagedBean")
@SessionScoped
public class ViewPricingManagedBean implements Serializable {

    @EJB
    private PricingBeanLocal pb;

    private Route route;
    //Direct Cost
    private String type;

    //Construct a hashmap<String, Integer> to record <Cabinclass,SeatNo> 
    private Map<String, Integer> cabinInfo = new HashMap<String, Integer>();

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

    private Integer ecSeatNo;
    private Integer bcSeatNo;
    private Integer suiteNo;
    private Integer fcSeatNo;
    private Integer pecSeatNo;
    private Integer totalSeatNo;

    private Double expectedRev;
    private Double profitMargin;// profit margin=(revenue-cost)/cost
    private Map<String, Double> fareMap = new HashMap<String, Double>();
    private List<String> keyList = new ArrayList<String>();

    /**
     * Creates a new instance of ViewManagedBean
     */
    public ViewPricingManagedBean() {

    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
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
            fareMap = pb.getAllFare(route);
            retrieveAircraftTypeInfo(aircraftType);
            // sessionmap~~~~         
            //         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("keyList", keyList);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewPricing2.xhtml");
        } else {
            System.out.println("No route is chosen");
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
        route = pb.getRouteInfo(ID);

        routeName = route.getOrigin().getCityName() + "-" + route.getDest().getCityName();

        System.out.println("MB: route name: " + routeName);
        distance = route.getDistance();
        blockHour = route.getBlockhour();
        aircraftType = route.getAcType();
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
//        ownershipCost = aircraftType.getLeaseCost() * 12;
        if (aircraftType.getSuiteNo() > 0) {
            cabinInfo.put("Suite", aircraftType.getSuiteNo());
//            loadfactorMap.put("Suite", 0.0);
            suiteNo = aircraftType.getSuiteNo();
        }
        if (aircraftType.getFcSeatNo() > 0) {
            cabinInfo.put("First Class", aircraftType.getFcSeatNo());
//            loadfactorMap.put("First Class", 0.0);
            fcSeatNo = aircraftType.getFcSeatNo();
        }
        if (aircraftType.getEcSeatNo() > 0) {
            cabinInfo.put("Economy Class", aircraftType.getEcSeatNo());
//            loadfactorMap.put("Economy Class", 0.0);
            ecSeatNo = aircraftType.getEcSeatNo();
        }
        if (aircraftType.getPecSeatNo() > 0) {
            cabinInfo.put("Premier Economy Class", aircraftType.getPecSeatNo());
//            loadfactorMap.put("Premier Economy Class", 0.0);
            pecSeatNo = aircraftType.getPecSeatNo();
        }
        if (aircraftType.getBcSeatNo() > 0) {
            cabinInfo.put("Business Class", aircraftType.getBcSeatNo());
//            loadfactorMap.put("Business Class", 0.0);
            bcSeatNo = aircraftType.getBcSeatNo();
        }
        totalSeatNo = suiteNo + fcSeatNo + ecSeatNo + pecSeatNo + bcSeatNo;
        System.out.println("MB: AircraftType Seat No: " + totalSeatNo);
//        crewNo = pb.calculateCrewNo(totalSeatNo);
//        System.out.println("MB: AircraftType Crew No: " + crewNo);
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

    //Override setCrewCost
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
        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewPricing.xhtml");
    }
}
