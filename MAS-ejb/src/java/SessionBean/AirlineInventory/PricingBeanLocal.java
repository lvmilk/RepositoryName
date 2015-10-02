/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface PricingBeanLocal {

    // public void setAircraftType(String type);

    public AircraftType getAircraftType(String type);

    public Integer calculateCrewNo(Integer seatNo);

    // public void calculateCrewCost(Integer crewNo,Double crewUnitCost,Double blockHour,Integer annualDepartures);
    public Double getCrewCost(Integer crewNo, Double crewUnitCost, Double blockHour, Integer annualDepartures);

    // public void setRouteList();
    public List<Route> getRouteList();

    //public void setRouteInfo(Long ID);
    public Route getRouteInfo(Long ID);

    public void calculateFare(Double expectedRev, Integer totalSeatNo, Double loadFactor, Integer annualDepartures, String cabin);

    public Double getFare();

    public Map<String, Double> getAllFare(Route route);
}
