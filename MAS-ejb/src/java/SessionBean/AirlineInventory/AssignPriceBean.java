/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.aisEntity.BookingClass;
import Entity.aisEntity.BookingClassInstance;
import Entity.aisEntity.FlightCabin;
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
public class AssignPriceBean implements AssignPriceBeanLocal {

    @PersistenceContext
    EntityManager em;

    public List<Route> getRouteList() {
        System.out.println("APB: getRouteList");

        List<Route> routeList = new ArrayList<Route>();
        Query query = em.createQuery("SELECT r FROM Route r");
        routeList = query.getResultList();
        return routeList;
    }

    public List<FlightFrequency> getFlightFrequencyList(Long routeID) {
        System.out.println("APB: getFFList");
        Route route = new Route();
        List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
        if (routeID != null) {
            route = em.find(Route.class, routeID);

            Query query = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.route.id=:frouteID");
            query.setParameter("frouteID", routeID);
            ffList = query.getResultList();
        }
        return ffList;
    }

    // get the list of flight instance that have no asoociated flightCabin and bookingClassInstance
    public List<FlightInstance> getFlightInstanceList(String flightNo) {
        List<FlightInstance> fiListAll = new ArrayList<FlightInstance>();
         List<FlightInstance> fiList = new ArrayList<FlightInstance>();
//        FlightFrequency ff = new FlightFrequency();
        
         Query query = em.createQuery("SELECT f FROM FlightInstance f WHERE f.flightFrequency.flightNo=:fflightNo"); 
         query.setParameter("fflightNo",flightNo);
//         ff = em.find(FlightFrequency.class, flightNo);
        fiListAll = query.getResultList();
        
        for (FlightInstance temp : fiListAll) {
            if (temp.getFlightCabins().isEmpty()) {
                fiList.add(temp);
            }
        }
        
        return fiList;
    }

    public void generateBookingClass(FlightInstance fi) {
        for (CabinClass temp : fi.getFlightFrequency().getRoute().getAcType().getCabinList()) {
            FlightCabin flightCabin = new FlightCabin();
            flightCabin.setCabinClass(temp);
            flightCabin.setFlightInstance(fi);
            em.persist(flightCabin);
            em.flush();
            List<BookingClass> bookingClassList = new ArrayList<BookingClass>();
            Query queryBclass = em.createQuery("SELECT b FROM BookingClass b WHERE b.cabinName=:fcabinName");
            queryBclass.setParameter("fcabinName", temp.getCabinName());
            bookingClassList = queryBclass.getResultList();
            for (BookingClass temp2 : bookingClassList) {
                BookingClassInstance bki = new BookingClassInstance();
                bki.setBookingClass(temp2);
                bki.setFlightCabin(flightCabin);
                bki.setSeatNo(0);
                if (temp.getCabinName().equals("Suite")) {
                    bki.setPrice(fi.getFlightFrequency().getRoute().getBasicScFare() * temp2.getPrice_percentage());
                } else if (temp.getCabinName().equals("First Class")) {
                    bki.setPrice(fi.getFlightFrequency().getRoute().getBasicFcFare() * temp2.getPrice_percentage());
                } else if (temp.getCabinName().equals("Business Class")) {
                    bki.setPrice(fi.getFlightFrequency().getRoute().getBasicBcFare() * temp2.getPrice_percentage());
                } else if (temp.getCabinName().equals("Premium Economy Class")) {
                    bki.setPrice(fi.getFlightFrequency().getRoute().getBasicPecFare() * temp2.getPrice_percentage());
                } else if (temp.getCabinName().equals("Economy Class")) {
                    bki.setPrice(fi.getFlightFrequency().getRoute().getBasicEcFare() * temp2.getPrice_percentage());
                } else {
                    System.out.print("It not gonna happen!!!!!!!");
                }
                em.persist(bki);
                em.flush();

            }

        }

    }

    public List<BookingClassInstance> getBkiList(FlightInstance fi) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        System.out.println("APB: getBkiLIst(): "+bkiList.size());
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.id=:fid");
        query.setParameter("fid", fi.getId());
        bkiList = query.getResultList();
        return bkiList;
    }

}
