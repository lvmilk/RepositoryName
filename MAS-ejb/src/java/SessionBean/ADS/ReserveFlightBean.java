/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.AIS.BookingClassInstance;
import Entity.AIS.FlightCabin;
import Entity.AIS.CabinClass;
import Entity.APS.Airport;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
public class ReserveFlightBean implements ReserveFlightBeanLocal {

    @PersistenceContext
    EntityManager em;
    @EJB
    FlightSchedulingBeanLocal fs;
    
    
    public Airport findAirport(String name){
    Airport airport;
     Query query = em.createQuery("SELECT a FROM Airport a WHERE a.airportName=:name");
        query.setParameter("name", name);
        List<Airport> resultList = query.getResultList();
        return resultList.get(0);
    }

    public CabinClass findCabinClass(String cabinName) {
        CabinClass select;
        Query query = em.createQuery("SELECT c FROM CabinClass c WHERE c.cabinName=:cabinName");
        query.setParameter("cabinName", cabinName);
        List<CabinClass> resultList = query.getResultList();

        return resultList.get(0);
    }

    public ArrayList<ArrayList<FlightInstance>> findResultInstanceList(String origin, String dest, Date departDate, CabinClass selectedCabin, int countPerson, String manageStatus, List<FlightInstance> bookedFlights) {
     System.out.println("#########################3getting into session bean findResultInstanceList() ");
        
        ArrayList<ArrayList<FlightInstance>> resultByDay = new ArrayList<>();
        ArrayList<FlightInstance> resultOptionTrue = new ArrayList<>();
        ArrayList<FlightInstance> resultOptionFalse = new ArrayList<>();
        ArrayList<ArrayList<FlightInstance>> tempUncomplete = new ArrayList<>();
        ArrayList<ArrayList<FlightInstance>> tempComplete = new ArrayList<>();
        List<FlightInstance> allFlightInstance = fs.getAllFlightInstance();

        Calendar c = Calendar.getInstance();
        c.setTime(departDate);

        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        String departString = s.format(departDate);

        System.out.println("origin is " + origin);
        System.out.println("dest is " + dest);
        System.out.println("departDate is " + departDate);

        for (int i = 0; i < 3; i++) {
            if (i == 0) {
                System.out.println("tempUncomplete is Empty");
                for (int k = 0; k < allFlightInstance.size(); k++) {

                    if (allFlightInstance.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(origin) && allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest) && allFlightInstance.get(k).getDate().equals(departString)) {
                        System.out.println("%%%%%%%%%%%%%%%getting into 1st step");
                        if (this.whetherAvailable(allFlightInstance.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
                            resultOptionTrue.add(allFlightInstance.get(k));
                            System.out.println("flight " + allFlightInstance.get(k).getFlightFrequency().getFlightNo() + " on date " + allFlightInstance.get(k).getDate() + " fulfills criteria");
                            tempComplete.add(resultOptionTrue);
                            resultOptionTrue = new ArrayList<>();
                        }
                    } else if (allFlightInstance.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(origin) && !(allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && allFlightInstance.get(k).getDate().equals(departString)) {
                        System.out.println("%%%%%%%%%%%%%%%getting into 2nd step");
                        if (this.whetherAvailable(allFlightInstance.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
                            System.out.println("flight " + allFlightInstance.get(k).getFlightFrequency().getFlightNo() + " on date " + allFlightInstance.get(k).getDate() + " fulfills intermediate criteria");
                            resultOptionFalse.add(allFlightInstance.get(k));
                            tempUncomplete.add(resultOptionFalse);
                            resultOptionFalse = new ArrayList<>();
                        }
                    }
                }

            } else if (i > 0 && !(tempUncomplete.isEmpty())) {

                System.out.println("size of tempUncomplete is " + tempUncomplete.size());

                for (int f = 0; f < tempUncomplete.size(); f++) {
                    c = Calendar.getInstance();
                    c.setTime(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType());
                    c.add(Calendar.HOUR, 24);
                    Date maxLimit = c.getTime();

                    for (int k = 0; k < allFlightInstance.size(); k++) {

                        Boolean checkDuplicate = findDuplicateInstance(tempUncomplete.get(f), allFlightInstance.get(k));

                        if (!checkDuplicate && !(allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(origin)) && !(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest) && allFlightInstance.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName()) && allFlightInstance.get(k).getStandardDepTimeDateType().after(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType()) && allFlightInstance.get(k).getStandardDepTimeDateType().before(maxLimit)) {
                            System.out.println("%%%%%%%%%%%%%%%getting into 3rd step");
                            if (!tempUncomplete.get(f).contains(allFlightInstance.get(k)) && this.whetherAvailable(allFlightInstance.get(k), selectedCabin, countPerson, manageStatus, bookedFlights)) {
                                System.out.println("Final leg is " + allFlightInstance.get(k));
                                System.out.println("Last leg's destination is " + tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName());
                                System.out.println("New leg's dest is " + dest);

                                ArrayList<FlightInstance> temp = tempUncomplete.get(f);
                                temp.add(allFlightInstance.get(k));
                                tempComplete.add(temp);
                                tempUncomplete.set(f, temp);
                            }
//                            tempUncomplete.remove(temp2);
                        } else if (i < 2 && this.whetherAvailable(allFlightInstance.get(k), selectedCabin, countPerson, manageStatus, bookedFlights) && !(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && !(allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(origin)) && !(tempUncomplete.get(f).contains(allFlightInstance.get(k))) && !(allFlightInstance.get(k).getFlightFrequency().getRoute().getDest().getAirportName().equals(dest)) && allFlightInstance.get(k).getFlightFrequency().getRoute().getOrigin().getAirportName().equals(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getFlightFrequency().getRoute().getDest().getAirportName()) && allFlightInstance.get(k).getStandardDepTimeDateType().after(tempUncomplete.get(f).get(tempUncomplete.get(f).size() - 1).getStandardArrTimeDateType()) && allFlightInstance.get(k).getStandardDepTimeDateType().before(maxLimit)) {

                            System.out.println("%%%%%%%%%%%%%%%getting into 4th step");
                            ArrayList<FlightInstance> temp = tempUncomplete.get(f);
                            temp.add(allFlightInstance.get(k));
                            tempUncomplete.set(f, temp);
                        }
                    }
                }
            }
        }

        if (!tempComplete.isEmpty()) {
            System.out.println("size of tempComplete is " + tempComplete.size());
            for (int i = 0; i < tempComplete.size(); i++) {
                System.out.println("size of flightlegs for option " + i + " is " + tempComplete.get(i).size());
            }
        }

        return tempComplete;

    }

    public Boolean whetherAvailable(FlightInstance flight, CabinClass cabin, int countPerson, String manageStatus, List<FlightInstance> bookedFlights) {
        boolean available = false;
        int countAvailable = 0;
        FlightCabin fCabin;
        System.out.println("flightinstance is " + flight);
        if (flight.getFlightCabins() != null) {
            System.out.println("~~~~~~~~~getflightcabin size is " + flight.getFlightCabins().size());
            for (int i = 0; i < flight.getFlightCabins().size(); i++) {
                System.out.println("~~~~~~flight.getFlightCabins() " + flight.getFlightCabins().get(i).getCabinClass());
                System.out.println("~~~~~~cabin is  " + cabin);

                if (flight.getFlightCabins().get(i).getCabinClass().equals(cabin)) {
                    fCabin = flight.getFlightCabins().get(i);
                    for (int k = fCabin.getBookingClassInstances().size() - 1; k >= 0; k--) {
                        if (fCabin.getBookingClassInstances().get(k).getSeatNo() != null && fCabin.getBookingClassInstances().get(k).getBookedSeatNo() != null) {
                            countAvailable = fCabin.getBookingClassInstances().get(k).getSeatNo() - fCabin.getBookingClassInstances().get(k).getBookedSeatNo();
                            if(manageStatus.equals("rebook") && bookedFlights.contains(flight)){
                            countAvailable+=countPerson;
                            }
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~for flightInstance " + flight);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~for flightCabin " + fCabin);
                            System.out.println("~~~~~~~~~~~~~~~~~~~~~for bookingclassinstance " + fCabin.getBookingClassInstances().get(k));

                        }
                        if (countAvailable >= countPerson) {
                            available = true;
                            break;
                        }
                    }
                }
                if (available) {
                    break;
                }
            }
        }

        return available;
    }

    public BookingClassInstance findCheapestAvailable(FlightInstance flight, CabinClass cabin, int countPerson) {
        FlightCabin fCabin;
        Boolean available = false;
        int countAvailable = 0;
        BookingClassInstance bookInstance = new BookingClassInstance();

        if (flight.getFlightCabins() != null) {
            for (int i = 0; i < flight.getFlightCabins().size(); i++) {

                if (flight.getFlightCabins().get(i).getCabinClass().equals(cabin)) {
                    fCabin = flight.getFlightCabins().get(i);
                    for (int k = fCabin.getBookingClassInstances().size() - 1; k >= 0; k--) {
                        if (fCabin.getBookingClassInstances().get(k).getSeatNo() != null && fCabin.getBookingClassInstances().get(k).getBookedSeatNo() != null) {
                            countAvailable = fCabin.getBookingClassInstances().get(k).getSeatNo() - fCabin.getBookingClassInstances().get(k).getBookedSeatNo();
                        }
                        if (countAvailable >= countPerson) {
                            bookInstance = fCabin.getBookingClassInstances().get(k);
                            available = true;
                            break;
                        }
                    }
                    break;
                }
            }
        }
        return bookInstance;
    }
    
    

    public Double getLowestPrice(ArrayList<FlightInstance> option, CabinClass cabin, int countPerson) {
        Double price = 0.0;
        FlightCabin fCabin;
        Boolean available = false;
        BookingClassInstance bookInstance = new BookingClassInstance();
        int countAvailable = 0;

        for (int j = 0; j < option.size(); j++) {
            FlightInstance flight = option.get(j);

            if (flight.getFlightCabins() != null) {
                for (int i = 0; i < flight.getFlightCabins().size(); i++) {
                    if (available == true) {
                        break;
                    }

                    if (flight.getFlightCabins().get(i).getCabinClass().equals(cabin)) {
                        fCabin = flight.getFlightCabins().get(i);
                        for (int k = fCabin.getBookingClassInstances().size() - 1; k >= 0; k--) {
                            if (fCabin.getBookingClassInstances().get(k).getSeatNo() != null && fCabin.getBookingClassInstances().get(k).getBookedSeatNo() != null) {
                                countAvailable = fCabin.getBookingClassInstances().get(k).getSeatNo() - fCabin.getBookingClassInstances().get(k).getBookedSeatNo();
                            }
                            if (countAvailable >= countPerson) {
                                bookInstance = fCabin.getBookingClassInstances().get(k);
                                available = true;
                                break;
                            }
                        }
                        if (available) {
                            price += bookInstance.getPrice();
                            available = false;
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("price is " + price);
        return price;
    }

    public Boolean findDuplicateInstance(ArrayList<FlightInstance> tempList, FlightInstance instance) {
        if (tempList.contains(instance)) {
            return true;
        } else {
            return false;
        }
    }

    public Double computeTotalPrice(ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, CabinClass cabin, Integer countPerson) {
        Double totalPrice = 0.0;
        System.out.println("getting into computeTotalPrice");
        System.out.println("cabin class is " + cabin);

        List<BookingClassInstance> bookList = new ArrayList<>();
        if (departSelected != null && !departSelected.isEmpty()) {
            System.out.println("departedSelected is not null or empty");
            for (int i = 0; i < departSelected.size(); i++) {
                FlightInstance flight = departSelected.get(i);
                Query query = em.createQuery("SELECT b FROM BookingClassInstance b WHERE b.flightCabin.cabinClass=:cabin AND b.flightCabin.flightInstance=:flight");
                query.setParameter("cabin", cabin);
                query.setParameter("flight", flight);
                bookList = query.getResultList();
                if (!bookList.isEmpty()) {
                    System.out.println("size of bookingClassInstancelist is " + bookList.size());

                }
                for (int k = 0; k < bookList.size(); k++) {
                    System.out.println(bookList.get(k).getPrice());
                }

            }
        }
        return totalPrice;
    }

    public List<FlightFrequency> findFrequencies(String origin, String dest) {

        Query query = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.route.origin.airportName=:origin AND f.route.dest.airportName=:dest");
        query.setParameter("origin", origin);
        query.setParameter("dest", dest);

        List<FlightFrequency> resultList = query.getResultList();

        System.out.println("In ReserveFlightBean: findFrequencies():flightfrequency size returned is " + resultList.size());
        return resultList;

    }

    public List<FlightFrequency> getAllFlightFrequency() {

        Query query = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightList IS NOT EMPTY GROUP BY f.route.origin");

        List<FlightFrequency> resultList = query.getResultList();

        System.out.println("flightFrequency size returned is " + resultList.size());
        return resultList;

    }

    public List<Airport> getDestList(String origin) {
        Query query = em.createQuery("SELECT a FROM Airport a where a.airportName!=:origin  ");
        query.setParameter("origin", origin);

        List<Airport> resultList = query.getResultList();

        System.out.println("flightFrequency size returned is " + resultList.size());
        return resultList;

    }

}
