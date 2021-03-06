/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.ADS.Seat;
import Entity.AIS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.AIS.BookingClass;
import Entity.AIS.BookingClassInstance;
import Entity.AIS.FlightCabin;
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
public class AssignPriceBean implements AssignPriceBeanLocal,AssignPriceBeanRemote {

    @PersistenceContext
    EntityManager em;

    @Override
    public List<Route> getRouteList() {
        System.out.println("APB: getRouteList");

        List<Route> routeList = new ArrayList<Route>();
        Query query = em.createQuery("SELECT r FROM Route r");
        List<Route> newList = new ArrayList<Route>();
        routeList = query.getResultList();
        if (routeList.size() != 0) {
            for (Route r : routeList) {
                if (!r.getOrigin().equals(r.getDest())) {
                    newList.add(r);
                }

            }
            routeList = newList;
        }

        return routeList;
    }

    @Override
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
    @Override
    public List<FlightInstance> getFlightInstanceList(String flightNo) {
        System.out.println("APB: flightNo " + flightNo);
        List<FlightInstance> fiListAll = new ArrayList<FlightInstance>();
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();
        System.out.println("APB: fiList.size " + fiList.size());
//        FlightFrequency ff = new FlightFrequency();

        Query query = em.createQuery("SELECT f FROM FlightInstance f WHERE f.flightFrequency.flightNo=:fflightNo");
        query.setParameter("fflightNo", flightNo);
//         ff = em.find(FlightFrequency.class, flightNo);
        fiListAll = query.getResultList();

        for (FlightInstance temp : fiListAll) {
            System.out.println("APB:temp is ? " + temp.getFlightFrequency().getFlightNo());
            em.flush();
            if (temp.getFlightCabins().size() == 0) {
                System.out.println("APB:temp instance flightCabins list size: " + temp.getFlightCabins().size());
                fiList.add(temp);
                System.out.println("APB: fiList.size after add " + fiList.size());
            }
        }
        System.out.println("APB:getFlightInstanceList: final list size: " + fiList.size());
        return fiList;
    }

    @Override
    public void generateBookingClass(FlightInstance fi) throws Exception {

        for (CabinClass temp : fi.getFlightFrequency().getRoute().getAcType().getCabinList()) {
            if (fi.getFlightFrequency().getRoute().getBasicScFare() != null || fi.getFlightFrequency().getRoute().getBasicFcFare() != null || fi.getFlightFrequency().getRoute().getBasicBcFare() != null || fi.getFlightFrequency().getRoute().getBasicPecFare() != null || fi.getFlightFrequency().getRoute().getBasicEcFare() != null) {

                if (temp.getRowCount() != null && temp.getRowSeatCount() != null && temp.getSeatChart() != null) {

                    System.out.println("Caibin class is " + temp.getCabinName());
                    FlightCabin flightCabin = new FlightCabin();
                    flightCabin.setCabinClass(temp);
                    flightCabin.setFlightInstance(fi);
                    em.persist(flightCabin);
                    em.flush();
                    // update Flightintance
                    List<FlightCabin> flightCabins = fi.getFlightCabins();
                    flightCabins.add(flightCabin);
                    fi.setFlightCabins(flightCabins);
                    em.merge(fi);
                    em.flush();
                    //  generate seat entity instsance
                    int seatNo = temp.getSeatCount();
                    List<String> seatNoList = new ArrayList<String>();
                    for (int i = 0; i < temp.getSeatChart().length; i++) {
                        for (int j = 0; j < temp.getSeatChart()[i].length; j++) {
                          if (!temp.getSeatChart()[i][j].isEmpty() && temp.getSeatChart()[i][j]!= null);
                            seatNoList.add(temp.getSeatChart()[i][j]);
                        }
                    }
                    System.out.println("APB: Print seat number list! " + seatNoList);
                    System.out.println("APB: seat number! " + seatNo);

                    for (int i = 0; i < seatNoList.size(); i++) {
                       if(!seatNoList.get(i).isEmpty()&&seatNoList.get(i)!=null){
                            Seat seat = new Seat();
                            seat.setFlightCabin(flightCabin);
                            seat.setStatus("Unoccupied");
                            seat.setSeatNumberToPassenger(seatNoList.get(i));
                            em.persist(seat);
                       }
                       
                    }

                    List<BookingClass> bookingClassList = new ArrayList<BookingClass>();
                    List<BookingClassInstance> biList = new ArrayList<BookingClassInstance>();
                    Query queryBclass = em.createQuery("SELECT b FROM BookingClass b WHERE b.cabinName=:fcabinName");
                    queryBclass.setParameter("fcabinName", temp.getCabinName());
                    bookingClassList = queryBclass.getResultList();
                    for (BookingClass temp2 : bookingClassList) {
                        BookingClassInstance bki = new BookingClassInstance();
                        bki.setBookingClass(temp2);
                        bki.setFlightCabin(flightCabin);
                        bki.setSeatNo(0);
                        System.out.println("APB: Cabin Name :" + temp.getCabinName());
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
                        biList.add(bki);
                        em.persist(bki);
                        em.flush();

                    }
                    flightCabin.setBookingClassInstances(biList);
                    em.merge(flightCabin);

                } else {
                    throw new Exception("Cabin Configuration is not complete for the aircraft serving this flight task");
                }
            } else {
                throw new Exception("Basic fare has not been determined yet.");

            }

            em.flush();

        }
    }

    @Override
    public List<BookingClassInstance> getBkiList(FlightInstance fi) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        System.out.println("APB: getBkiLIst(): " + bkiList.size());
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.id=:fid");
        query.setParameter("fid", fi.getId());
        bkiList = query.getResultList();
        return bkiList;
    }

}
