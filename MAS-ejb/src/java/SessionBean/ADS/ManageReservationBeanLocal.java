/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.APS.FlightInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface ManageReservationBeanLocal {

    public List<Reservation> findReservation(Long code, String email);

    public List<Reservation> getAllReservations();

    public List<FlightInstance> getFlightPackage(List<FlightInstance> flights, String origin, String dest, int index);

    public List<Passenger> getPassengerList(Reservation rsv);
    
}
