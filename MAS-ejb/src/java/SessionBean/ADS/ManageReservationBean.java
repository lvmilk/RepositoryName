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

        Double refund = 0.0;
        refund = computeCancelRefund(BookClassInstanceList, selectedPsgList.size());
//        System.out.println("refund is " + refund);
        Payment payment = em.find(Payment.class, selectedRsv.getPayment().getPaymentID());
//        payment.setTotalPrice(payment.getTotalPrice() - refund);
//        System.out.println("payment is " + payment);
//        em.merge(payment);

        ArrayList<Passenger> oldPsgList = getPassengerList(selectedRsv);


        if (oldPsgList != null && oldPsgList.size() == selectedPsgList.size()) {

            removeOldFlt(selectedRsv, oldPsgList,oldPsgList, "cancel", refund);
        } else {
            ArrayList<Passenger> psgList=new ArrayList<>();
            for (int i = 0; i < selectedPsgList.size(); i++) {
                psgList.add(selectedPsgList.get(i));
            }
            removeOldFlt(selectedRsv, oldPsgList, psgList, "cancel", refund);
        }

        em.flush();

    }

    public Double computeCancelRefund(List<BookingClassInstance> bookList, Integer psgCount) {
        Double refund = 0.0;
        for (int i = 0; i < bookList.size(); i++) {
            refund += bookList.get(i).getPrice() * bookList.get(i).getBookingClass().getRefund_percentage();
        }
        refund *= psgCount;

        return refund;
    }

    public void removePartialPsgs(Reservation rsv, List<Passenger> psgList, String action, Double priceDecrease) {
        Booker booker = em.find(Booker.class, rsv.getBooker().getId());
        rsv = em.find(Reservation.class, rsv.getId());

        Payment payment = em.find(Payment.class, rsv.getPayment().getPaymentID());
//        rsv.setPayment(null);
//        em.merge(rsv);
//        em.remove(payment);
//        em.flush();

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
                BookingClassInstance bookInstance = em.find(BookingClassInstance.class, ticket.getBkInstance());

                if (ticket != null) {
                    System.out.println(ticket);
                } else {
                    System.out.println(" ticket is null ");
                }
                ticket.setPassenger(null);
                ticket.setRsv(null);
                ticket.setBkInstance(null);

                List<Ticket> psgTickets = psg.getTickets();
                psgTickets.remove(ticket);
                psg.setTickets(psgTickets);

                List<Ticket> rsvTickets = rsv.getTickets();
                rsvTickets.remove(ticket);
                rsv.setTickets(rsvTickets);

                List<Ticket> bcTickets = bookInstance.getTickets();
                bcTickets.remove(ticket);
                bookInstance.setTickets(bcTickets);

                em.merge(psg);
                em.merge(rsv);
                em.merge(bookInstance);

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
            BookingClassInstance b = em.find(BookingClassInstance.class, bookList.get(i).getId());
            b.setBookedSeatNo(b.getBookedSeatNo() - psgList.size());
            em.merge(b);
            em.flush();
            bookList.set(i, b);
        }
        rsv.setBkcInstance(bookList);
        em.merge(rsv);
        em.flush();

        rsv = em.find(Reservation.class, rsv.getId());

        em.flush();

    }

    public void rescheduleRsv(Reservation selectedRsv, ArrayList<Passenger> passengerList, ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, String origin, String dest, Boolean returnTrip, Double totalPenalty, String bkSystem, String companyName) {
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
        Double totalPrice = computeTotalPrice(selectedRsv, BookClassInstanceList, passengerList.size(), totalPenalty);

        Double pricePax = pricePerPax(selectedRsv.getBkcInstance());
        System.out.println("in reschedule rsv : pricePerPax is " + pricePax);
        Double priceDecrease = pricePax * passengerList.size();

        ArrayList<Passenger> oldPsgList = getPassengerList(selectedRsv);
        if (oldPsgList != null && oldPsgList.size() == passengerList.size()) {
            removeOldFlt(selectedRsv,oldPsgList, passengerList,"rebook",0.0);
        } else {
            removeOldFlt(selectedRsv,oldPsgList, passengerList,"rebook",0.0);
        }

        em.flush();
        System.out.println("Before reschedule: totalPrice is " + totalPrice);

        psgLocal.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, passengerList.size(), origin, dest, returnTrip, bkSystem, totalPrice, "rebook", companyName);

    }

    public Double pricePerPax(List<BookingClassInstance> bookInstanceList) {
        Double price = 0.0;

        for (int i = 0; i < bookInstanceList.size(); i++) {
            price += bookInstanceList.get(i).getPrice();
        }
        return price;

    }

    public Double computeTotalPrice(Reservation rsv, ArrayList<BookingClassInstance> BookClassInstanceList, Integer psgCount, Double penalty) {
        Double totalPrice = 0.0;

        Double totalOldPrice = 0.0;
        List<BookingClassInstance> oldBookInstances = new ArrayList<>();

        oldBookInstances = rsv.getBkcInstance();


        System.out.println("in computeTotalPrice(): list of bookingclassIntance is " + BookClassInstanceList);
        System.out.println("psgCount " + psgCount + " penalty " + penalty + " bookList.size() " + BookClassInstanceList.size());
        for (int i = 0; i < BookClassInstanceList.size(); i++) {
            System.out.println("in computeTotalPrice(): price of bookingclassIntance  " + BookClassInstanceList.get(i) + " is from flightFrequency of " + BookClassInstanceList.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
            System.out.println("in computeTotalPrice(): price of bookingclassIntance  " + BookClassInstanceList.get(i) + " is " + BookClassInstanceList.get(i).getPrice());
            totalPrice += BookClassInstanceList.get(i).getPrice();
        }
        totalPrice *= psgCount;
        System.out.println("Total price without penalty is " + totalPrice);

        System.out.println("in computeTotalPrice(): list of oldBookingClassInstance is " + oldBookInstances);
        for (int i = 0; i < oldBookInstances.size(); i++) {
            System.out.println("in computeTotalPrice(): price of bookingclassIntance  " + oldBookInstances.get(i) + " is from flightFrequency of " + oldBookInstances.get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
            System.out.println("in computeTotalPrice(): price of bookingclassIntance  " + oldBookInstances.get(i) + " is " + oldBookInstances.get(i).getPrice());
            totalOldPrice += oldBookInstances.get(i).getPrice();
        }
        totalOldPrice *= psgCount;

        Double priceDiff = totalPrice - totalOldPrice;
        if (priceDiff < 0) {
            priceDiff = 0.0;
        }

        System.out.println("Total priceDiff plus penalty is " + (priceDiff + penalty));
        return priceDiff + penalty;
    }

    public void removeOldFlt(Reservation rsv,ArrayList<Passenger>oldPsgList, ArrayList<Passenger> psgList, String action, Double refund) {
       Booker booker = em.find(Booker.class, rsv.getBooker().getId());
        rsv = em.find(Reservation.class, rsv.getId());

        if(action.equals("cancel")){
        Payment payment=rsv.getPayment();
        payment.setRefund(refund);
        em.merge(payment);
        em.flush();
        }

   
        
        List<BookingClassInstance> bkInstanceList = rsv.getBkcInstance();

        List<Ticket> tickets = new ArrayList<>();

        for (int i = 0; i < psgList.size(); i++) {
            Passenger psg = em.find(Passenger.class, psgList.get(i).getId());
            Query query = em.createQuery("SELECT t FROM Ticket t WHERE t.passenger=:psg").setParameter("psg", psg);
            tickets = query.getResultList();
            System.out.println("ticket size is " + tickets.size());
            List<Ticket> ticketsCopy = query.getResultList();

            System.out.println("ticket size for copy is " + ticketsCopy.size());
            for (int j = 0; j < ticketsCopy.size(); j++) {
                BookingClassInstance bcInstance = em.find(BookingClassInstance.class, ticketsCopy.get(j).getBkInstance().getId());
                Ticket ticket = em.find(Ticket.class, ticketsCopy.get(j).getTicketID());

                if (ticket != null) {
                    System.out.println(ticket);
                } else {
                    System.out.println(" ticket is null ");
                }
                ticket.setPassenger(null);
                ticket.setRsv(null);
                ticket.setBkInstance(null);

                List<Ticket> psgTickets = psg.getTickets();
                psgTickets.remove(ticket);
                psg.setTickets(psgTickets);

                List<Ticket> rsvTickets = rsv.getTickets();
                rsvTickets.remove(ticket);
                rsv.setTickets(rsvTickets);

                List<Ticket> bcTickets = bcInstance.getTickets();
                bcTickets.remove(ticket);
                bcInstance.setTickets(bcTickets);

                em.merge(psg);
                em.merge(rsv);
                em.merge(bcInstance);
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

//        em.remove(rsv);
        if(oldPsgList.size()==psgList.size()){
        rsv.setRsvStatus("Cancelled");
        }
        
        
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

        System.out.println("changeList is " + changeList);
        System.out.println("oldInstance is " + oldInstance);

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
        String status = "Reserved";
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.rsvStatus=:status").setParameter("status", status);
        List<Reservation> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            return resultList;
        } else {
            resultList = new ArrayList<>();
            return resultList;
        }
    }

    @Override
    public List<Reservation> getCompanyReservations(String companyName) {
                List<Reservation> rsvList = new ArrayList<>();
        String status = "Reserved";
        Query query = em.createQuery("SELECT r FROM Reservation r WHERE r.rsvStatus=:status and r.companyName=:inCompanyName");
        query.setParameter("inCompanyName", companyName);
        query.setParameter("status", status);
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
