/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface ManageReservationBeanLocal {
    
    public void rescheduleRsv(Reservation selectedRsv, ArrayList<Passenger> passengerList, ArrayList<FlightInstance>departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, String origin, String dest, Boolean returnTrip, Double totalPenalty, String bkSystem);

    public Double getChangeDatePenalty(ArrayList<FlightInstance> oldDepart, ArrayList<FlightInstance> oldReturn, ArrayList<FlightInstance> newDepart, ArrayList<FlightInstance> newReturn, List<BookingClassInstance> oldInstance);

    public Double getChangeRoutePenalty(ArrayList<FlightInstance> oldDepart, ArrayList<FlightInstance> oldReturn, ArrayList<FlightInstance> newDepart, ArrayList<FlightInstance> newReturn, List<BookingClassInstance> oldInstance, ArrayList<BookingClassInstance> newInstance);

    public Double computePriceDiff(Double newPrice, Double oldPrice);

    public List<Reservation> findReservation(Long code, String email);

    public List<Reservation> getAllReservations();

    public List<FlightInstance> getFlightPackage(List<FlightInstance> flights, String origin, String dest, int index);

    public ArrayList<Passenger> getPassengerList(Reservation rsv);
    
    public void cancelFlight(Reservation selectedRsv, List<Passenger> selectedPsgList, List<FlightInstance> departed, List<FlightInstance> returned, List<BookingClassInstance>BookClassInstanceList, String origin, String dest, Boolean returnTrip, Double penalty, String bkSystem);
    

}