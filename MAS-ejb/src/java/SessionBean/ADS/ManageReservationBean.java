/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Payment;
import Entity.ADS.Reservation;
import Entity.ADS.Ticket;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
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

    @EJB
    PassengerBeanLocal psgLocal;

    public void cancelFlight(Reservation selectedRsv, List<Passenger> selectedPsgList, List<FlightInstance> departed, List<FlightInstance> returned, List<BookingClassInstance> BookClassInstanceList, String origin, String dest, Boolean returnTrip, Double penalty, String bkSystem) {
  Booker booker = selectedRsv.getBooker();
        System.out.println("in rescheduleRsv()");

        System.out.println("origin is " + origin);
        System.out.println("dest is " + dest);
        System.out.println("returnTrip is " + returnTrip);

        for (int i = 0; i < departed.size(); i++) {
            System.out.println(departed.get(i));
        }

        for (int i = 0; i < returned.size(); i++) {
            System.out.println(returned.get(i));
        }
        for (int i = 0; i < BookClassInstanceList.size(); i++) {
            System.out.println(BookClassInstanceList.get(i));
        }

        ArrayList<Passenger> oldPsgList = getPassengerList(selectedRsv);
        if (oldPsgList != null && oldPsgList.size() == selectedPsgList.size()) {
            removeOldRsv(selectedRsv, oldPsgList);
        }

        em.flush();
        
        
        
    }

    public void rescheduleRsv(Reservation selectedRsv, ArrayList<Passenger> passengerList, ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, String origin, String dest, Boolean returnTrip, Double totalPenalty, String bkSystem) {
        Booker booker = selectedRsv.getBooker();
        System.out.println("in rescheduleRsv()");

        System.out.println("origin is " + origin);
        System.out.println("dest is " + dest);
        System.out.println("returnTrip is " + returnTrip);

        for (int i = 0; i < departSelected.size(); i++) {
            System.out.println(departSelected.get(i));
        }

        for (int i = 0; i < returnSelected.size(); i++) {
            System.out.println(returnSelected.get(i));
        }
        for (int i = 0; i < BookClassInstanceList.size(); i++) {
            System.out.println(BookClassInstanceList.get(i));
        }

        ArrayList<Passenger> oldPsgList = getPassengerList(selectedRsv);
        if (oldPsgList != null && oldPsgList.size() == passengerList.size()) {
            removeOldRsv(selectedRsv, oldPsgList);
        }
        Double totalPrice = computeTotalPrice(BookClassInstanceList, passengerList.size(), totalPenalty);
        em.flush();
        psgLocal.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, passengerList.size(), origin, dest, returnTrip, bkSystem);

    }

    public Double computeTotalPrice(ArrayList<BookingClassInstance> BookClassInstanceList, Integer psgCount, Double penalty) {
        Double totalPrice = 0.0;
        for (int i = 0; i < BookClassInstanceList.size(); i++) {
            totalPrice += BookClassInstanceList.get(i).getPrice();
        }
        totalPrice *= psgCount;
        totalPrice += penalty;
        return totalPrice;
    }

    public void removeOldRsv(Reservation rsv, ArrayList<Passenger> psgList) {
        Booker booker = em.find(Booker.class, rsv.getBooker().getId());
        rsv = em.find(Reservation.class, rsv.getId());

        Payment payment = em.find(Payment.class, rsv.getPayment().getPaymentID());
        rsv.setPayment(null);
        em.merge(rsv);
        em.remove(payment);
        em.flush();

        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < psgList.size(); i++) {
            Passenger psg = em.find(Passenger.class, psgList.get(i).getId());
            Query query = em.createQuery("SELECT t FROM Ticket t WHERE t.passenger=:psg").setParameter("psg", psg);
            tickets = query.getResultList();
            System.out.println("ticket size is " + tickets.size());
            List<Ticket> ticketsCopy = query.getResultList();

            System.out.println("ticket size for copy is " + ticketsCopy.size());
            for (int j = 0; j < ticketsCopy.size(); j++) {
                Ticket ticket = em.find(Ticket.class, ticketsCopy.get(j).getTicketID());

                if (ticket != null) {
                    System.out.println(ticket);
                } else {
                    System.out.println(" ticket is null ");
                }
                ticket.setPassenger(null);
                ticket.setRsv(null);

                List<Ticket> psgTickets = psg.getTickets();
                psgTickets.remove(ticket);
                psg.setTickets(psgTickets);

                List<Ticket> rsvTickets = rsv.getTickets();
                rsvTickets.remove(ticket);
                rsv.setTickets(rsvTickets);

                em.merge(psg);
                em.merge(rsv);
                em.remove(ticket);
            }
            em.flush();
        }

        for (int i = 0; i < psgList.size(); i++) {
            Passenger psg = em.find(Passenger.class, psgList.get(i).getId());
            em.remove(psg);
            em.flush();
        }

        rsv = em.find(Reservation.class, rsv.getId());
        List<BookingClassInstance> bookList = rsv.getBkcInstance();
        for (int i = 0; i < bookList.size(); i++) {
            BookingClassInstance bookInstance = em.find(BookingClassInstance.class, bookList.get(i).getId());

            List<BookingClassInstance> bkList = rsv.getBkcInstance();
            bkList.remove(bookInstance);
            rsv.setBkcInstance(bkList);

            Collection<Reservation> rsvList = bookInstance.getReservation();
            rsvList.remove(rsv);
            bookInstance.setReservation(rsvList);

            em.merge(bookInstance);
            em.flush();

        }

        em.remove(rsv);
        em.flush();
//
//        em.remove(rsv);
//        em.flush();
    }

    public Double getChangeDatePenalty(ArrayList<FlightInstance> oldDepart, ArrayList<FlightInstance> oldReturn, ArrayList<FlightInstance> newDepart, ArrayList<FlightInstance> newReturn, List<BookingClassInstance> oldInstance) {

        ArrayList<FlightInstance> changeList = new ArrayList<>();
        Route newRoute = null;
        Double penalty = 0.0;

        Collections.sort(oldDepart);
        Collections.sort(oldReturn);
        Collections.sort(newDepart);
        Collections.sort(newReturn);

        if (!oldDepart.get(0).getDate().equals(newDepart.get(0).getDate())) {

            changeList.add(oldDepart.get(0));
        }

        if (oldReturn != null && !oldReturn.isEmpty()) {

            if (newReturn != null && !newReturn.isEmpty()) {
                if (!oldReturn.get(0).getDate().equals(newReturn.get(0).getDate())) {
                    changeList.add(oldReturn.get(0));
                }
            } else {
                changeList.add(oldReturn.get(0));
            }
        }

        for (int i = 0; i < changeList.size(); i++) {
            for (int j = 0; j < oldInstance.size(); j++) {
                if (oldInstance.get(j).getFlightCabin().getFlightInstance().equals(changeList.get(i))) {
                    penalty += oldInstance.get(j).getPrice() * oldInstance.get(j).getBookingClass().getChange_date_percentage();

                }
            }
        }

        return penalty;
    }

    public Double getChangeRoutePenalty(ArrayList<FlightInstance> oldDepart, ArrayList<FlightInstance> oldReturn,
            ArrayList<FlightInstance> newDepart, ArrayList<FlightInstance> newReturn, List<BookingClassInstance> oldInstance, ArrayList<BookingClassInstance> newInstance) {
        ArrayList<FlightInstance> changeList = new ArrayList<>();
        Route newRoute = null;
        Double penalty = 0.0;

        for (int i = 0; i < oldDepart.size(); i++) {
            Route oldRoute = oldDepart.get(i).getFlightFrequency().getRoute();
            for (int j = 0; j < newDepart.size(); j++) {
                if (newDepart.get(j).getFlightFrequency().getRoute().equals(oldRoute)) {
                    newRoute = newDepart.get(j).getFlightFrequency().getRoute();
                    break;
                }
            }
            if (newRoute != null) {
                newRoute = null;
            } else {
                System.out.println("change of route for flight " + oldDepart.get(i).toString());
                changeList.add(oldDepart.get(i));
            }
        }

        newRoute = null;
        for (int i = 0; i < oldReturn.size(); i++) {
            Route oldRoute = oldReturn.get(i).getFlightFrequency().getRoute();
            for (int j = 0; j < newReturn.size(); j++) {
                if (newReturn.get(j).getFlightFrequency().getRoute().equals(oldRoute)) {
                    newRoute = newReturn.get(j).getFlightFrequency().getRoute();
                    break;
                }
            }
            if (newRoute != null) {
                newRoute = null;
            } else {
                changeList.add(oldReturn.get(i));
                System.out.println("change of route for flight " + oldReturn.get(i).toString());
            }
        }

        for (int i = 0; i < changeList.size(); i++) {
            for (int j = 0; j < oldInstance.size(); j++) {
                if (oldInstance.get(j).getFlightCabin().getFlightInstance().equals(changeList.get(i))) {
                    penalty += oldInstance.get(j).getPrice() * oldInstance.get(j).getBookingClass().getChange_route_percentage();

                }
            }
        }

        return penalty;
    }

    public Double computePriceDiff(Double newPrice, Double oldPrice) {
        if (newPrice > oldPrice) {
            return (newPrice - oldPrice);
        } else {
            return 0.0;
        }

    }

    public ArrayList<Passenger> getPassengerList(Reservation rsv) {
        List<Ticket> tickets = rsv.getTickets();
        List<Passenger> psgList = new ArrayList<>();
        for (int i = 0; i < tickets.size(); i++) {
            if (!psgList.contains(tickets.get(i).getPassenger())) {
                psgList.add(tickets.get(i).getPassenger());
            }
        }
        ArrayList<Passenger> psgs = new ArrayList<>();
        for (int i = 0; i < psgList.size(); i++) {
            psgs.add(psgList.get(i));
        }
        return psgs;
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
