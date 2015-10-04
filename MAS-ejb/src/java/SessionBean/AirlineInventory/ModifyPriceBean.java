/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
public class ModifyPriceBean implements ModifyPriceBeanLocal {

    @PersistenceContext
    EntityManager em;

    List<FlightFrequency> flightList;

    private FlightFrequency flightFrequency = new FlightFrequency();
    //private LocalDate date;

// Add business logic below. (Right-click in editor and choose
    public List<FlightFrequency> getFlightList(String dateString) {
        flightList = new ArrayList<FlightFrequency>();
        System.out.println("MPB: getFlightList(): date is "+dateString);
    
        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate");
         query.setParameter("fdate", dateString);
        System.out.println("MPB: getFlightList(): flights are "+query.getResultList().toString());
        List<FlightInstance> resultList = query.getResultList();
        for (FlightInstance temp : resultList) {
            if (!flightList.contains(temp.getFlightFrequency())) {
                flightList.add(temp.getFlightFrequency());
            }
        }
        return flightList;
    }

    
// "Insert Code > Add Business Method")
}
