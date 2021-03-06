/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.AIS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.AIS.BookingClassInstance;
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
public class SeatAllocationBean implements SeatAllocationBeanLocal,SeatAllocationBeanRemote {
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

    public List<BookingClassInstance> getBkiList(String flightNo, String date,String cabinName) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.date=:fdate AND b.flightCabin.flightInstance.flightFrequency.flightNo=:fflightNo AND b.flightCabin.cabinClass.cabinName=:fcabinName");
        query.setParameter("fdate", date);
        query.setParameter("fflightNo", flightNo);
        query.setParameter("fcabinName",cabinName);
        bkiList = query.getResultList();
        return bkiList;
    }
    public List<CabinClass> getCabinList(String flightNo){
         List<CabinClass> cabinList = new ArrayList<CabinClass>();
         System.out.println("SAB: getCabinList(): initial flightList size is "+flightList.size());
        for (FlightFrequency f: flightList){
            if(f.getFlightNo().equals(flightNo))
                cabinList=f.getRoute().getAcType().getCabinList();
        }
       System.out.println("SAB: getCabinList(): size is "+cabinList.size());
        return cabinList;
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
