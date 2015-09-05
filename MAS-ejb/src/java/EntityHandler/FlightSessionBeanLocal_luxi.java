/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import Entity.Flight;
import java.util.Collection;
import javax.ejb.Local;

/**
 *
 * @author Xi
 */
@Local
public interface FlightSessionBeanLocal {

    public void addFlight(String registrationNo, String flightStatus, String date, Integer flightNo, Integer stopoverSNo) throws Exception;

    public Collection<Flight> getFlightList();

    public void deleteFlight(String date, Integer flightNo, String registrationNo) throws Exception;
    
}
