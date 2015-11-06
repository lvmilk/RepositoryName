/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.ADS.Ticket;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface PassengerBeanLocal {

    public void makeReservation(Booker booker, ArrayList<Passenger> passengerList, ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, Integer psgCount, String origin, String dest, Boolean returnTrip);

    public boolean checkPassportExist(String passport);

    public boolean checkPassengerExist(Passenger passenger);

    public Booker checkMemberExist(Long memberId, String email);

    public Booker createTempBooker(String title, String firstName, String lastName, String address, String email, String contactNo);



}
