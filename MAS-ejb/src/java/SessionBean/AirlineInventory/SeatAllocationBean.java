/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.aisEntity.BookingClassInstance;
import Entity.aisEntity.FlightCabin;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateful
public class SeatAllocationBean implements SeatAllocationBeanLocal {
 @PersistenceContext
    EntityManager em;

    private List<FlightFrequency> flightList;

    private FlightFrequency flightFrequency = new FlightFrequency();
    //private LocalDate date;

// Add business logic below. (Right-click in editor and choose
    public List<FlightFrequency> getFlightList(String date) {
        flightList = new ArrayList<FlightFrequency>();
        System.out.println("SAB: getFlightList(): date is " + date);
        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate");
        query.setParameter("fdate", date);
        System.out.println("SAB: getFlightList(): flights are " + query.getResultList().toString());
        List<FlightInstance> resultList = query.getResultList();
        for (FlightInstance temp : resultList) {
            if (!flightList.contains(temp.getFlightFrequency())) {
                flightList.add(temp.getFlightFrequency());
            }
        }
        return flightList;
    }

    public List<BookingClassInstance> getBkiList(String flightNo, String date) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.date=:fdate AND b.flightCabin.flightInstance.flightFrequency.flightNo=:fflightNo");
        query.setParameter("fdate", date);
        query.setParameter("fflightNo", flightNo);
        bkiList = query.getResultList();
        return bkiList;
    }
  
    public void editSeatNo(BookingClassInstance bki,Integer seatNo) {
        bki.setSeatNo(seatNo);
        System.out.println("SAB: get seat count is " + bki.getSeatNo());
        em.merge(bki);
        System.out.println("SAB: Seat count edited!");
        em.flush();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
