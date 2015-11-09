/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.DCS;

import Entity.ADS.Passenger;
import Entity.ADS.Ticket;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
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

    public List<Ticket> getAllTicket(String passportID, String firstName, String lastName) throws Exception {

        List<Ticket> ticketList = new ArrayList<Ticket>();
        Passenger passenger = new Passenger();
        Query query1 = em.createQuery("SELECT p FROM Passenger p where p.passport=:ppassport AND p.firstName=:pfirstname AND p.lastName=:plastname");
        query1.setParameter("ppassport", passportID);
        query1.setParameter("pfirstname", firstName);
        query1.setParameter("plastname", lastName);
        if (!query1.getResultList().isEmpty()) {
            passenger = (Passenger) query1.getSingleResult();
            Query query2 = em.createQuery("SELECT t FROM Ticket t where t.passenger.id=:pid");
            query2.setParameter("pid", passenger.getId());
            ticketList = query2.getResultList();
            if (ticketList.isEmpty()) {
                return ticketList;
            } else {
                throw new Exception("No Ticket Not Found!");
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
   
    
    

    private List<Ticket> getAllStandbyTickets(String flightNo, String date) {
        List<Ticket> ticketList = new ArrayList<Ticket>();

        return ticketList;
    }

}
