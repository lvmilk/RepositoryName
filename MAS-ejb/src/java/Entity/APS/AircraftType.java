package Entity.APS;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author lucy
 */
@Entity
public class AircraftType implements Serializable {

    // private static final long serialVersionUID = 1L;
    @Id
    private String type;
    private String manufacturer;
    private Double maxDistance;
    private Double leaseCost;
    private Double fuelCost;
    private Double aircraftLength;
    private Double wingspan;
    private String minAirspace;

//    private Integer suiteNo;                //number of seat in suite
//    private Integer fcSeatNo;               //number of seat in first class
//    private Integer bcSeatNo;               //number of seat in business class
//    private Integer pecSeatNo;              //number of seat in premium economy class
//    private Integer ecSeatNo;               //number of seat in economy class
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "aircraftType")
    private Collection<Aircraft> aircraft = new ArrayList<Aircraft>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "aircraftType")
    private List<FlightFrequency> flightMatchList = new ArrayList<> ();
    
    
    public void create(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double aircraftLength, Double wingspan, String minAirspace) {
        /*Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo*/
        this.setType(type);
        this.setManufacturer(manufacturer);
        this.setMaxDistance(maxDistance);
        this.setLeaseCost(leaseCost);
        this.setFuelCost(fuelCost);
        this.setAircraftLength(aircraftLength);
        this.setWingspan(wingspan);
        this.setMinAirspace(minAirspace);
//        this.setSuiteNo(suiteNo);
//        this.setFcSeatNo(fcSeatNo);
//        this.setBcSeatNo(bcSeatNo);
//        this.setPecSeatNo(pecSeatNo);
//        this.setEcSeatNo(ecSeatNo);
    }

    public Collection<Aircraft> getAircraft() {
        return aircraft;
    }

    public void setAircraft(Collection<Aircraft> aircraft) {
        this.aircraft = aircraft;
    }

    public List<FlightFrequency> getFlightMatchList() {
        return flightMatchList;
    }

    public void setFlightMatchList(List<FlightFrequency> flightMatchList) {
        this.flightMatchList = flightMatchList;
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

    public Double getLeaseCost() {
        return leaseCost;
    }

    public void setLeaseCost(Double leaseCost) {
        this.leaseCost = leaseCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
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
//
//    public Integer getSuiteNo() {
//        return suiteNo;
//    }
//
//    public void setSuiteNo(Integer suiteNo) {
//        this.suiteNo = suiteNo;
//    }
//
//    public Integer getFcSeatNo() {
//        return fcSeatNo;
//    }
//
//    public void setFcSeatNo(Integer fcSeatNo) {
//        this.fcSeatNo = fcSeatNo;
//    }
//
//    public Integer getBcSeatNo() {
//        return bcSeatNo;
//    }
//
//    public void setBcSeatNo(Integer bcSeatNo) {
//        this.bcSeatNo = bcSeatNo;
//    }
//
//    public Integer getPecSeatNo() {
//        return pecSeatNo;
//    }
//
//    public void setPecSeatNo(Integer pecSeatNo) {
//        this.pecSeatNo = pecSeatNo;
//    }
//
//    public Integer getEcSeatNo() {
//        return ecSeatNo;
//    }
//
//    public void setEcSeatNo(Integer ecSeatNo) {
//        this.ecSeatNo = ecSeatNo;
//    }

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
        return "Entity.APS.AircraftType[ id=" + type + " ]";
    }

}
