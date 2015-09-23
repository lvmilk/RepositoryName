/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface PricingBeanLocal {
      public AircraftType getAircraftType(String type);
      public Integer calculateCrewNo(Integer seatNo);
      public Double calculateCrewCost(Integer crewNo,Double crewCost,Double blockHour,Integer annualDepartures);
      public List<Route> getRouteList();
     public Route getRouteInfo(Long ID);
}
