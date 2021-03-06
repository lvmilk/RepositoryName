/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.AIS.BookingClassInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface AssignPriceBeanLocal {

    public List<Route> getRouteList();

    public List<FlightFrequency> getFlightFrequencyList(Long routeID);

    public List<FlightInstance> getFlightInstanceList(String flightNo);

    public void generateBookingClass(FlightInstance fi)throws Exception;

    public List<BookingClassInstance> getBkiList(FlightInstance fi);
    
}
