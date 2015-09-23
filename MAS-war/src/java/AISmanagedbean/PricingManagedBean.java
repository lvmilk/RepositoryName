/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import SessionBean.AirlineInventory.PricingBeanLocal;
import java.io.Serializable;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

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

    //Construct a hashtable<String, Integer> to record <Cabinclass,SeatNo> 
    private Hashtable<String, Integer> cabinInfo;
    private String routeName;   
            
    private Integer crewNo;
    private Double crewUniteCost;
    private Double crewCost;       //Per pax per block hour
    private Double fuelCost;       // per block hour 
    private Double ownershipCost;  //per block hour
    private Double maintenance;    //per block hour
    //Indirect cost
    private Double adminCost; //per block hour
    private Double otherCost; //per block hour
    //Route Info
    private List<Route>routeList;
    private Map<Long, String> routeInfo;
    private Double blockHour;
    private Double distance;
    private Integer annualDepartures;

    private Double econFactor;
    private Double bizFactor;
    private Double suiteFactor;
    private Double firstFactor;
    private Double pEconFactor;

    private Integer ecSeatNo;
    private Integer bcSeatNo;
    private Integer suiteNo;
    private Integer fcSeatNo;
    private Integer pecSeatNo;
    private Integer totalSeatNo;

    private Double expectedRev;
    private Double profitMargin;// profit margin=(revenue-cost)/cost
    
    public PricingManagedBean() {

    }
    public void getRouteList(){
    routeList=pb.getRouteList();
    for(int i=0;i<routeList.size();i++){
       routeInfo.put(routeList.get(i).getId(), routeList.get(i).getOrigin().getCityName()+"-"+routeList.get(i).getDest().getCityName());
    }
    }
    
    
    public void retrieveRouteInfo(Long ID){
    route=pb.getRouteInfo(ID);
    distance=route.getDistance();
    //blockHour=route.getBlockHour();              //Rightnow  block Hour is not added
    
    }
    public void retrieveAircraftTypeInfo(String type) {
        AircraftType aircraftType = pb.getAircraftType(type);
        fuelCost=aircraftType.getFuelCost()*blockHour*annualDepartures;
        ownershipCost=aircraftType.getLeaseCost();
//        if (aircraftType.getSuiteNo() > 0) {
//            cabinInfo.put("Suite", aircraftType.getSuiteNo());
//            suiteNo = aircraftType.getSuiteNo();
//        }
//        if (aircraftType.getFcSeatNo() > 0) {
//            cabinInfo.put("First Class", aircraftType.getFcSeatNo());
//            fcSeatNo = aircraftType.getFcSeatNo();
//        }
//        if (aircraftType.getEcSeatNo() > 0) {
//            cabinInfo.put("Economy Class", aircraftType.getEcSeatNo());
//            ecSeatNo = aircraftType.getEcSeatNo();
//        }
//        if (aircraftType.getPecSeatNo() > 0) {
//            cabinInfo.put("Premier Economy Class", aircraftType.getPecSeatNo());
//            pecSeatNo = aircraftType.getPecSeatNo();
//        }
//        if (aircraftType.getBcSeatNo() > 0) {
//            cabinInfo.put("Business Class", aircraftType.getBcSeatNo());
//            bcSeatNo = aircraftType.getBcSeatNo();
//        }
        totalSeatNo=suiteNo+fcSeatNo+ecSeatNo+pecSeatNo+bcSeatNo;
        crewNo=pb.calculateCrewNo(totalSeatNo);
    }
    
    
   
    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Hashtable<String, Integer> getCabinInfo() {
        return cabinInfo;
    }

    public void setCabinInfo(Hashtable<String, Integer> cabinInfo) {
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
    public void setCrewCost(Integer crewNo,Double crewUnitCost,Double blockHour,Integer annualDepartures) {
       crewCost=pb.calculateCrewCost(crewNo,crewUnitCost,blockHour,annualDepartures);
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

    public Double getEconFactor() {
        return econFactor;
    }

    public void setEconFactor(Double econFactor) {
        this.econFactor = econFactor;
    }

    public Double getBizFactor() {
        return bizFactor;
    }

    public void setBizFactor(Double bizFactor) {
        this.bizFactor = bizFactor;
    }

    public Double getSuiteFactor() {
        return suiteFactor;
    }

    public void setSuiteFactor(Double suiteFactor) {
        this.suiteFactor = suiteFactor;
    }

    public Double getFirstFactor() {
        return firstFactor;
    }

    public void setFirstFactor(Double firstFactor) {
        this.firstFactor = firstFactor;
    }

    public Double getpEconFactor() {
        return pEconFactor;
    }

    public void setpEconFactor(Double pEconFactor) {
        this.pEconFactor = pEconFactor;
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

}
