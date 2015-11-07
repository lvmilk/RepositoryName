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
import static Entity.ADS.Ticket_.rsv;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class ManageReservationBean implements ManageReservationBeanLocal {

    @PersistenceContext
    EntityManager em;

    
    
    public List<Passenger> getPassengerList(Reservation rsv) {
        List<Ticket> tickets = rsv.getTickets();
        List<Passenger> psgList = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            if (!psgList.contains(tickets.get(i).getPassenger())) {
                psgList.add(tickets.get(i).getPassenger());
            }
        }
        return psgList;
    }
    
    

    public List<FlightInstance> getFlightPackage(List<FlightInstance> flights, String origin, String dest, int index) {
        List<FlightInstance> departed = new ArrayList<>();

        for (int i = index; i < flights.size(); i++) {
            if (flights.get(i).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) {
                departed.add(flights.get(i));
                break;
            } else {
                departed.add(flights.get(i));
            }
        }
        return departed;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> rsvList = new ArrayList<>();
        Query query = em.createQuery("SELECT r FROM Reservation r ");
        List<Reservation> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            resultList = new ArrayList<>();
            return resultList;
        }
    }

    public List<Reservation> findReservation(Long code, String email) {
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.id=:code AND r.bkEmail=:email");
        query.setParameter("code", code);
        query.setParameter("email", email);
        List<Reservation> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            resultList = new ArrayList<>();
            return resultList;
        }
    }
}
