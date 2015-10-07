/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.aisEntity.BookingClassInstance;
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
public class SeatAssignBean implements SeatAssignBeanLocal {

    @PersistenceContext
    EntityManager em;

    private List<FlightFrequency> flightList;
    private FlightFrequency flightFrequency = new FlightFrequency();

    
    
    public List<FlightFrequency> getFlightList(String dateString) {
        flightList = new ArrayList<FlightFrequency>();
        System.out.println("seatAssignBean: getFlightList(): date is " + dateString);

        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate");
        query.setParameter("fdate", dateString);
        System.out.println("seatAssignBean: getFlightList(): flights are " + query.getResultList().toString());
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
    
    

}
