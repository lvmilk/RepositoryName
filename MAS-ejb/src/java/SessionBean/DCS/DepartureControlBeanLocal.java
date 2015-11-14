/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.DCS;

import Entity.ADS.Seat;
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


    public List<Seat> getAllUnOccupiedSeats(Ticket tkt) throws Exception;


    public boolean checkLoungeEligibility(Ticket tkt) throws Exception;

    public void accumulateMiles(Ticket ticket) throws Exception;

    public void updateLuggageCount(Seat seat, Integer luggageCount) throws Exception;

    public Seat getSeat(Ticket tkt) throws Exception;

    public boolean changeBoardingStatus(Ticket tkt) throws Exception;

    public void selectSeat(Seat seat, Ticket ticket) throws Exception;

    public boolean checkStandbyEligibility(Ticket tkt) throws Exception;

    public List<Ticket> getAllBoardedTicket(String flightNo, String dateString) throws Exception;

    public List<Ticket> getAllUnBoardedTicket(String flightNo, String dateString) throws Exception;

    public List<Ticket> getAllCheckedInTicket(String flightNo, String dateString) throws Exception;

    public List<Ticket> getAllUnchekedinTicket(String flightNo, String dateString) throws Exception;

    public List<Seat> getAllSeats(String flightNo, String dateString) throws Exception;

    
    
}
