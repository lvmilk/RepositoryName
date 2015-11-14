/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.DCS;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Seat;
import Entity.ADS.Ticket;
import Entity.AIS.FlightCabin;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateless
public class DepartureControlBean implements DepartureControlBeanLocal {

    private Boolean isCheckedin;
    private Boolean canUseEpass;
    @PersistenceContext
    EntityManager em;

    @Override
    public List<Ticket> getAllTicket(String passportID, String firstName, String lastName) throws Exception {

        List<Ticket> ticketList = new ArrayList<Ticket>();
        Passenger passenger = new Passenger();
        Query query1 = em.createQuery("SELECT p FROM Passenger p where p.passport=:ppassport AND p.firstName=:pfirstname AND p.lastName=:plastname");
        query1.setParameter("ppassport", passportID);
        query1.setParameter("pfirstname", firstName);
        query1.setParameter("plastname", lastName);
        if (!query1.getResultList().isEmpty()) {
            passenger = (Passenger) query1.getSingleResult();
            System.out.println("DCB:getAllTicket: passenger id? " + passenger.getId());
            Query query2 = em.createQuery("SELECT t FROM Ticket t where t.passenger.id=:pid");
            query2.setParameter("pid", passenger.getId());
            ticketList = query2.getResultList();
            if (!ticketList.isEmpty()) {
                return ticketList;
            } else {
                throw new Exception("No Ticket Found!");
            }

        } else {
            throw new Exception("Passenger Not Found");
        }
    }

    @Override
    public List<FlightFrequency> getFlightList(String dateString) throws Exception {
        List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
        System.out.println("MPB: getFlightList(): date is " + dateString);

        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate");
        query.setParameter("fdate", dateString);
        System.out.println("DCB: getFlightList(): flights are " + query.getResultList().toString());
        List<FlightInstance> resultList = query.getResultList();
        for (FlightInstance temp : resultList) {
            if (!flightList.contains(temp.getFlightFrequency())) {
                flightList.add(temp.getFlightFrequency());
            }
        }
        if (flightList.isEmpty()) {
            throw new Exception("No flights on that date! ");
        } else {
            return flightList;
        }
    }

    @Override
    public FlightInstance getRequestFlight(String flightNo, String dateString) throws Exception {
        FlightInstance fi = new FlightInstance();
        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");

        query.setParameter("fdate", dateString);
        query.setParameter("flightNo", flightNo);
        fi = (FlightInstance) query.getSingleResult();
        if (fi != null) {
            return fi;
        } else {
            throw new Exception("Cannot find request flight!");
        }
    }

    @Override
    public List<Ticket> getAllStandbyTickets(String flightNo, String dateString) throws Exception {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        List<Ticket> allTickets = new ArrayList<Ticket>();
        Query query = em.createQuery("SELECT t FROM Ticket t where t.ticketStatus=:tstatus");
        query.setParameter("tstatus", "Standby");
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();

            if (!query.getResultList().isEmpty()) {
                for (Ticket temp : (List<Ticket>) query.getResultList()) {
                    if (temp.getBkInstance().getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getOrigin().equals(fi.getFlightFrequency().getRoute().getOrigin()) && temp.getBkInstance().getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getDest())) {
                        ticketList.add(temp);
                    }
                }
            } else {
                throw new Exception("No Standby ticket found");
            }

        } else {
            throw new Exception("No flight instance found");
        }

        return ticketList;
    }

    @Override
    public boolean changeCheckinStatus(Ticket tkt) throws Exception {
        if (em.find(Ticket.class, tkt.getTicketID()) != null) {
            tkt.setTicketStatus("Checkedin");
            Date date = new Date();
            tkt.setCheckinTime(date);
            em.merge(tkt);
            return true;
        } else {
            throw new Exception("No such ticket exist!");
        }
    }

    @Override
    public boolean changeStandbyStatus(Ticket tkt) throws Exception {
        if (em.find(Ticket.class, tkt.getTicketID()) != null) {

            if (checkStandbyEligibility(tkt)) {
                tkt.setTicketStatus("Standby");
                Date date = new Date();
                tkt.setCheckinTime(date);
                em.merge(tkt);
                return true;
            } else {
                throw new Exception("The passenger is not eligible for standby!");

            }
        } else {
            throw new Exception("No such ticket exist!");
        }
    }

    @Override
    public boolean changeBoardingStatus(Ticket tkt) throws Exception {
        if (em.find(Ticket.class, tkt.getTicketID()) != null) {
            tkt.setTicketStatus("Boarded");
            em.merge(tkt);
            return true;
        } else {
            throw new Exception("No such ticket exist!");
        }
    }

    @Override
    public List<Seat> getAllUnOccupiedSeats(Ticket tkt) throws Exception {
        List<Seat> unOccupiedList = new ArrayList<Seat>();
        List<Seat> newList = new ArrayList<Seat>();
        FlightCabin fc = tkt.getBkInstance().getFlightCabin();
        System.out.println("DCB:getAllUnOccupiedSeats: " + fc.getCabinClass().getCabinName());
        if (fc == null) {
            System.out.println("DCB:getAllUnOccupiedSeats:no such flight cabin");

            throw new Exception("No Flight Cabin Found!");
        } else {
            Query query = em.createQuery("SELECT s FROM Seat s where s.flightCabin.id=:fcid");
            query.setParameter("fcid", fc.getId());
            newList = query.getResultList();
            if (newList.isEmpty()) {
                throw new Exception("No Unoccupied Seat Available!");
            } else {
                for (Seat st : newList) {
                    if (st.getStatus().equals("Unoccupied")) {
                        unOccupiedList.add(st);
                    }
                }
                System.out.println("DCB:getAllUnOccupiedSeats: resultList size  " + unOccupiedList.size());
                return unOccupiedList;
            }

        }

    }

    public void selectSeat(Seat seat, Ticket ticket) throws Exception {
        Seat newSeat = em.find(Seat.class, seat.getId());
        if (newSeat != null && newSeat.getStatus().equals("Unoccupied")) {
            newSeat.setStatus("Occupied");
            em.merge(newSeat);
            ticket.setSeat(seat);
            em.merge(ticket);
        } else {
            throw new Exception("Cannot Select This Seat!");
        }

    }

    @Override
    public boolean checkLoungeEligibility(Ticket tkt) throws Exception {
        String cabinName = tkt.getBkInstance().getBookingClass().getCabinName();
        if (cabinName != null) {
            switch (cabinName) {
                case "Suite":
                case "First Class":
                case "Business Claass":
                    return true;
                default:
                    return false;
            }
        } else {
            throw new Exception("Cabin Does Not Exist!");
        }

    }

    @Override
    public boolean checkStandbyEligibility(Ticket tkt) throws Exception {
        String cabinName = tkt.getBkInstance().getBookingClass().getCabinName();
        if (cabinName != null) {
            switch (cabinName) {
                case "Suite":
                case "First Class":
                case "Business Class":
                    return true;
                default:
                    return false;
            }
        } else {
            throw new Exception("Cabin Does Not Exist!");
        }

    }

    @Override
    public void accumulateMiles(Ticket ticket) throws Exception {
        if (ticket != null) {
            if (ticket.getRsv().getBooker().isMemberStatus()) {
                Booker booker = ticket.getRsv().getBooker();
                Double mileAdded = ticket.getBkInstance().getBookingClass().getEarn_mile_percentage() * ticket.getBkInstance().getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getDistance();
                Double mileOld = booker.getMiles();
                booker.setMiles(mileOld + mileAdded);
                em.merge(booker);
            }
        } else {
            throw new Exception("Ticket Does Not Exist!");
        }

    }

    @Override
    public void updateLuggageCount(Seat seat, Integer luggageCount) throws Exception {
        if (em.find(Seat.class, seat.getId()) != null) {
            seat.setLuggageCount(luggageCount);
            em.merge(seat);
        } else {
            throw new Exception("Seat Does Not Exist!");
        }
    }

    @Override
    public Seat getSeat(Ticket tkt) throws Exception {
        Seat result = new Seat();
        if (em.find(Seat.class, tkt.getSeat().getId()) != null) {
            result = (Seat) em.find(Seat.class, tkt.getSeat().getId());
            return result;
        } else {
            throw new Exception("No selected Seat!");
        }

    }

    @Override
    public List<Ticket> getAllBoardedTicket(String flightNo, String dateString) throws Exception {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        List<Ticket> allTickets = new ArrayList<Ticket>();
        Query query = em.createQuery("SELECT t FROM Ticket t where t.ticketStatus=:tstatus");
        query.setParameter("tstatus", "Boarded");
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();

            if (!query.getResultList().isEmpty()) {
                for (Ticket temp : (List<Ticket>) query.getResultList()) {
                    if (temp.getBkInstance().getFlightCabin().getFlightInstance().getId().equals(fi.getId())) {
                        ticketList.add(temp);
                    }
                }
            } else {
                throw new Exception("No Boarded Passenger Found");
            }

        } else {
            throw new Exception("No Flight Instance Found");
        }

        return ticketList;
    }

    @Override
    public List<Ticket> getAllUnBoardedTicket(String flightNo, String dateString) throws Exception {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        List<Ticket> allTickets = new ArrayList<Ticket>();
        Query query = em.createQuery("SELECT t FROM Ticket t where t.ticketStatus=:tstatus1 or t.ticketStatus=:tstatus2");
        query.setParameter("tstatus1", "Checkedin");
        query.setParameter("tstatus2", "OnlineCheckedin");
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();

            if (!query.getResultList().isEmpty()) {
                for (Ticket temp : (List<Ticket>) query.getResultList()) {
                    if (temp.getBkInstance().getFlightCabin().getFlightInstance().getId().equals(fi.getId())) {
                        ticketList.add(temp);
                    }
                }
            } else {
                throw new Exception("No Unboarded Passenger Found");
            }

        } else {
            throw new Exception("No Flight Instance Found");
        }

        return ticketList;
    }

    @Override
    public List<Ticket> getAllCheckedInTicket(String flightNo, String dateString) throws Exception {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        List<Ticket> allTickets = new ArrayList<Ticket>();
        Query query = em.createQuery("SELECT t FROM Ticket t where t.ticketStatus=:tstatus1 OR t.ticketStatus=:tstatus2 OR t.ticketStatus=:tstatus3");
        query.setParameter("tstatus1", "Checkedin");
        query.setParameter("tstatus2", "Boarded");
        query.setParameter("tstatus3", "OnlineCheckedin");
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();

            if (!query.getResultList().isEmpty()) {
                for (Ticket temp : (List<Ticket>) query.getResultList()) {
                    if (temp.getBkInstance().getFlightCabin().getFlightInstance().getId().equals(fi.getId())) {
                        ticketList.add(temp);
                    }
                }
            } else {
                throw new Exception("No Checked in  Passenger Found");
            }

        } else {
            throw new Exception("No Flight Instance Found");
        }

        return ticketList;
    }

    @Override
    public List<Ticket> getAllUnchekedinTicket(String flightNo, String dateString) throws Exception {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        List<Ticket> allTickets = new ArrayList<Ticket>();
        Query query = em.createQuery("SELECT t FROM Ticket t where t.ticketStatus=:tstatus");
        query.setParameter("tstatus", "Unused");
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();

            if (!query.getResultList().isEmpty()) {
                for (Ticket temp : (List<Ticket>) query.getResultList()) {
                    if (temp.getBkInstance().getFlightCabin().getFlightInstance().getId().equals(fi.getId())) {
                        ticketList.add(temp);
                    }
                }
            } else {
                throw new Exception("No Unchecked in Passenger Found");
            }

        } else {
            throw new Exception("No Flight Instance Found");
        }

        return ticketList;
    }

    @Override
    public List<Seat> getAllSeats(String flightNo, String dateString) throws Exception {
        List<Seat> seatList = new ArrayList<Seat>();
        List<Seat> allSeats = new ArrayList<Seat>();
        FlightInstance fi = new FlightInstance();
        Query query2 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate AND f.flightFrequency.flightNo=:flightNo");
        query2.setParameter("fdate", dateString);
        query2.setParameter("flightNo", flightNo);
        if (!query2.getResultList().isEmpty()) {
            fi = (FlightInstance) query2.getSingleResult();
        } else {
            throw new Exception("No Such Flight Instance Found");
        }

        Query query = em.createQuery("SELECT s FROM Seat s");

        if (!query.getResultList().isEmpty()) {
            allSeats = (List<Seat>) query.getResultList();
            for (Seat temp : allSeats) {
                if (temp.getFlightCabin().getFlightInstance().getId().equals(fi.getId())) {
                    seatList.add(temp);
                }
            }
        } else {
            throw new Exception("No Seat Found");
        }

        return seatList;
    }

}
