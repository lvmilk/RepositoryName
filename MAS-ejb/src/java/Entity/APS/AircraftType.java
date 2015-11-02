package Entity.APS;

import Entity.AFOS.FlightCrewTeam;
import Entity.AIS.CabinClass;
import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.OneToOne;

/**
 *
 * @author lucy
 */
@Entity
public class AircraftType implements Serializable {

    @Id
    private String type;
    private String manufacturer;
    private Double maxDistance;
    private Double purchaseCost;
    private Double fuelCost;
   
    private Double aircraftLength;
    private Double wingspan;
    private String minAirspace;

    private Integer suiteNo;                //number of seat in suite
    private Integer fcSeatNo;               //number of seat in first class
    private Integer bcSeatNo;               //number of seat in business class
    private Integer pecSeatNo;              //number of seat in premium economy class
    private Integer ecSeatNo;               //number of seat in economy class

    private Integer totalSeatNum;
     private Double mtCost;  // new added maintenance cost
    //number of cabin crew
    private Double cabinCrew; // new added
    private Double cabinLeader; //cabin master
//    private Integer stewardess; //cabin female
//    private Integer steward; //cabin male
    //number of cockpit crew
    private Integer captain;
    private Integer pilot; //1st officer

    //---------------------after 1st release-------------------------------------------
    private Integer acInH;
    private Integer acInC;
    private Integer acDu;
    private Integer acMH;

    private Integer bcInH;
    private Integer bcInC;
    private Integer bcDu;   
    private Integer bcMH;

    private Integer ccInH;
    private Integer ccInC;
    private Integer ccDu;  // by hour
    private Integer ccMH;

    private Integer dcInH;
    private Integer dcInC;
    private Integer dcDu;  // by hour
    private Integer dcMH;

    @OneToOne(cascade = {CascadeType.PERSIST}, mappedBy = "act")
    private List<FlightCrewTeam> flightTeam = new ArrayList<>();
    
    //---------------------------------------------------------------------------

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "aircraftType")
    private List<Aircraft> aircraft = new ArrayList<>();
//    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "aircraftType")
//    private List<FlightFrequency> flightMatchList = new ArrayList<> ();
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "acType")
    private List<Route> routeMatchList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "aircraftType")
    private List<CabinClass> cabinList = new ArrayList<CabinClass>();

    public void create(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double mtCost, Double aircraftLength, Double wingspan, String minAirspace,
            Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo, Double cabinCrew, Double purser, Integer captain, Integer pilot) {

        this.setType(type);
        this.setManufacturer(manufacturer);
        this.setMaxDistance(maxDistance);
        this.setPurchaseCost(leaseCost);
        this.setFuelCost(fuelCost);
        this.setMtCost(mtCost);
        this.setAircraftLength(aircraftLength);
        this.setWingspan(wingspan);
        this.setMinAirspace(minAirspace);
        this.setSuiteNo(suiteNo);
        this.setFcSeatNo(fcSeatNo);
        this.setBcSeatNo(bcSeatNo);
        this.setPecSeatNo(pecSeatNo);
        this.setEcSeatNo(ecSeatNo);
        this.setCabinCrew(cabinCrew);
        this.setCabinLeader(purser);
        this.setCaptain(captain);
        this.setPilot(pilot);
        totalSeatNum=suiteNo+fcSeatNo+bcSeatNo+pecSeatNo+ecSeatNo;
        this.setTotalSeatNum(totalSeatNum);
    }

    public List<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(List<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public Double getMtCost() {
        return mtCost;
    }

    public void setMtCost(Double mtCost) {
        this.mtCost = mtCost;
    }

    
    public Double getAircraftLength() {
        return aircraftLength;
    }

    public void setAircraftLength(Double aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    public Double getWingspan() {
        return wingspan;
    }

    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    public String getMinAirspace() {
        return minAirspace;
    }

    public void setMinAirspace(String minAirspace) {
        this.minAirspace = minAirspace;
    }

    public Integer getSuiteNo() {
        return suiteNo;
    }

    public void setSuiteNo(Integer suiteNo) {
        this.suiteNo = suiteNo;
    }

    public Integer getFcSeatNo() {
        return fcSeatNo;
    }

    public void setFcSeatNo(Integer fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    public Integer getBcSeatNo() {
        return bcSeatNo;
    }

    public void setBcSeatNo(Integer bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    public Integer getPecSeatNo() {
        return pecSeatNo;
    }

    public void setPecSeatNo(Integer pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    public Integer getEcSeatNo() {
        return ecSeatNo;
    }

    public void setEcSeatNo(Integer ecSeatNo) {
        this.ecSeatNo = ecSeatNo;
    }

    public Double getCabinCrew() {
        return cabinCrew;
    }

    public void setCabinCrew(Double cabinCrew) {
        this.cabinCrew = cabinCrew;
    }

    public Double getCabinLeader() {
        return cabinLeader;
    }

    public void setCabinLeader(Double cabinLeader) {
        this.cabinLeader = cabinLeader;
    }

    public Integer getCaptain() {
        return captain;
    }

    public void setCaptain(Integer captain) {
        this.captain = captain;
    }

    public Integer getPilot() {
        return pilot;
    }

    public void setPilot(Integer pilot) {
        this.pilot = pilot;
    }

    public List<Route> getRouteMatchList() {
        return routeMatchList;
    }

    public void setRouteMatchList(List<Route> routeMatchList) {
        this.routeMatchList = routeMatchList;
    }

//    public List<FlightFrequency> getFlightMatchList() {
//        return flightMatchList;
//    }
//
//    public void setFlightMatchList(List<FlightFrequency> flightMatchList) {
//        this.flightMatchList = flightMatchList;
//    }
    public List<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public Integer getAcInH() {
        return acInH;
    }

    public void setAcInH(Integer acInH) {
        this.acInH = acInH;
    }

    public Integer getAcInC() {
        return acInC;
    }

    public void setAcInC(Integer acInC) {
        this.acInC = acInC;
    }

    public Integer getAcDu() {
        return acDu;
    }

    public void setAcDu(Integer acDu) {
        this.acDu = acDu;
    }

    public Integer getAcMH() {
        return acMH;
    }

    public void setAcMH(Integer acMH) {
        this.acMH = acMH;
    }

    public Integer getBcInH() {
        return bcInH;
    }

    public void setBcInH(Integer bcInH) {
        this.bcInH = bcInH;
    }

    public Integer getBcInC() {
        return bcInC;
    }

    public void setBcInC(Integer bcInC) {
        this.bcInC = bcInC;
    }

    public Integer getBcDu() {
        return bcDu;
    }

    public void setBcDu(Integer bcDu) {
        this.bcDu = bcDu;
    }

    public Integer getBcMH() {
        return bcMH;
    }

    public void setBcMH(Integer bcMH) {
        this.bcMH = bcMH;
    }

    public Integer getCcInC() {
        return ccInC;
    }

    public void setCcInC(Integer ccInC) {
        this.ccInC = ccInC;
    }

    public Integer getCcDu() {
        return ccDu;
    }

    public void setCcDu(Integer ccDu) {
        this.ccDu = ccDu;
    }

    public Integer getCcMH() {
        return ccMH;
    }

    public void setCcMH(Integer ccMH) {
        this.ccMH = ccMH;
    }

    public Integer getDcInC() {
        return dcInC;
    }

    public void setDcInC(Integer dcInC) {
        this.dcInC = dcInC;
    }

    public Integer getDcDu() {
        return dcDu;
    }

    public void setDcDu(Integer dcDu) {
        this.dcDu = dcDu;
    }

    public Integer getDcMH() {
        return dcMH;
    }

    public void setDcMH(Integer dcMH) {
        this.dcMH = dcMH;
    }

    public Integer getCcInH() {
        return ccInH;
    }

    public void setCcInH(Integer ccInH) {
        this.ccInH = ccInH;
    }

    public Integer getDcInH() {
        return dcInH;
    }

    public void setDcInH(Integer dcInH) {
        this.dcInH = dcInH;
    }

    public Integer getTotalSeatNum() {
        return totalSeatNum;
    }

    public void setTotalSeatNum(Integer totalSeatNum) {
        this.totalSeatNum = totalSeatNum;
    }
    

    //    public Double getCruiseSpeed() {
//        return cruiseSpeed;
//    }
//
//    public void setCruiseSpeed(Double cruiseSpeed) {
//        this.cruiseSpeed = cruiseSpeed;
//    }
//
//    public Double getCruiseAltitude() {
//        return cruiseAltitude;
//    }
//
//    public void setCruiseAltitude(Double cruiseAltitude) {
//        this.cruiseAltitude = cruiseAltitude;
//    }

    public List<FlightCrewTeam> getFlightTeam() {
        return flightTeam;
    }

    public void setFlightTeam(List<FlightCrewTeam> flightTeam) {
        this.flightTeam = flightTeam;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (type != null ? type.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AircraftType)) {
            return false;
        }
        AircraftType other = (AircraftType) object;
        if ((this.type == null && other.type != null) || (this.type != null && !this.type.equals(other.type))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return type;
    }

}
