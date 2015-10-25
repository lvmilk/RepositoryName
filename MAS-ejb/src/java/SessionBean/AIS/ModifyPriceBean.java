/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.AIS.BookingClassInstance;
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

    private List<FlightFrequency> flightList;

    private FlightFrequency flightFrequency = new FlightFrequency();
    //private LocalDate date;

// Add business logic below. (Right-click in editor and choose
    @Override
    public List<FlightFrequency> getFlightList(String dateString) {
        flightList = new ArrayList<FlightFrequency>();
        System.out.println("MPB: getFlightList(): date is " + dateString);

        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate");
        query.setParameter("fdate", dateString);
        System.out.println("MPB: getFlightList(): flights are " + query.getResultList().toString());
        List<FlightInstance> resultList = query.getResultList();
        for (FlightInstance temp : resultList) {
            if (!flightList.contains(temp.getFlightFrequency())) {
                flightList.add(temp.getFlightFrequency());
            }
        }
        return flightList;
    }

    @Override
    public List<BookingClassInstance> getBkiList(String flightNo, String date) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.date=:fdate AND b.flightCabin.flightInstance.flightFrequency.flightNo=:fflightNo");
        query.setParameter("fdate", date);
        query.setParameter("fflightNo", flightNo);
        bkiList = query.getResultList();
        return bkiList;
    }

    @Override
    public void editPrice(BookingClassInstance bki, Double price) {
        bki.setPrice(price);
        System.out.println("MPB: get price is " + bki.getPrice());
        em.merge(bki);
        System.out.println("MPB: Price edited!");
        em.flush();
    }

}
