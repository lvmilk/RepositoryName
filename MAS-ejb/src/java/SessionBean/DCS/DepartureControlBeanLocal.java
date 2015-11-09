/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.DCS;

import Entity.ADS.Ticket;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface DepartureControlBeanLocal {

    public FlightInstance getRequestFlight(String flightNo, String dateString) throws Exception;

    public List<Ticket> getAllTicket(String passportID, String firstName, String lastName) throws Exception;


    public List<FlightFrequency> getFlightList(String dateString) throws Exception;

    public boolean changeStandbyStatus(Ticket tkt) throws Exception;

    public boolean changeCheckinStatus(Ticket tkt) throws Exception;

    public List<Ticket> getAllStandbyTickets(String flightNo, String dateString) throws Exception;

    
    
}
