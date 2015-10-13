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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.commons.math3.distribution.NormalDistribution;

/**
 *
 * @author LIU YUQI'
 */
@Stateless
public class SeatAssignBean implements SeatAssignBeanLocal {

    @PersistenceContext
    EntityManager em;

    private List<FlightFrequency> flightList;
    private List<FlightCabin> cabinList;
    private FlightFrequency flightFrequency = new FlightFrequency();
    private List<BookingClassInstance> suiteInstance = new ArrayList<>();
    private List<BookingClassInstance> firstInstance = new ArrayList<>();
    private List<BookingClassInstance> bizInstance = new ArrayList<>();
    private List<BookingClassInstance> premiumInstance = new ArrayList<>();
    private List<BookingClassInstance> econInstance = new ArrayList<>();

    public void changeBookdSeatCount(BookingClassInstance bInstance) {
        BookingClassInstance selected = em.find(BookingClassInstance.class, bInstance.getId());
        selected.setBookedSeatNo(bInstance.getBookedSeatNo());
        em.merge(selected);
    }

    public void changeSeatNo(BookingClassInstance bInstance) {
        BookingClassInstance selected = em.find(BookingClassInstance.class, bInstance.getId());
        selected.setSeatNo(bInstance.getSeatNo());
        selected.setBookedSeatNo(bInstance.getBookedSeatNo());
        em.merge(selected);
        System.out.println("in seatAssignBean: changeSeatNo(): seat no for booking class " + selected.getBookingClass().getAnnotation() + " is " + selected.getSeatNo());
    }

    public double computeCurrentRev(List<BookingClassInstance> listInstance) {
        double totalRev = 0;
        double avgDemand;
        double std;
        NormalDistribution distribution;
        for (int i = 0; i < listInstance.size(); i++) {
            avgDemand = (double) listInstance.get(i).getAvgDemand();
            std = (double) listInstance.get(i).getStd();
            distribution = new NormalDistribution(avgDemand, std);

//            System.out.println("for class "+listInstance.get(i).getBookingClass().getAnnotation());
            for (int k = 1; k < (listInstance.get(i).getSeatNo() + 1); k++) {
                double expectedRev;
                expectedRev = listInstance.get(i).getPrice() * (1 - distribution.cumulativeProbability(k));
//                System.out.println("expected MR from seat "+k+" is "+expectedRev);
                totalRev += listInstance.get(i).getPrice() * (1 - distribution.cumulativeProbability(k));
            }
        }
//        System.out.println("Total revenue is " + totalRev);
        return totalRev;

    }

    public double computeOptimalRev(List<BookingClassInstance> listInstance) {
        double totalRev = 0;
        double avgDemand;
        double std;
        NormalDistribution distribution;
        for (int i = 0; i < listInstance.size(); i++) {
            avgDemand = (double) listInstance.get(i).getAvgDemand();
            std = (double) listInstance.get(i).getStd();
            distribution = new NormalDistribution(avgDemand, std);

            for (int k = 1; k < (listInstance.get(i).getOptimalSeatNo() + 1); k++) {
                totalRev += listInstance.get(i).getPrice() * (1 - distribution.cumulativeProbability(k));
            }
        }
//        System.out.println("Total optimal revenue is " + totalRev);
        return totalRev;

    }

   // emsr-b
    public List<BookingClassInstance> computeOptimalSeat3(List<BookingClassInstance> bookClassInstanceList) {
        Double optimalValue;
        int optimalSeatNo;
        int totalAllocated = 0;
        NormalDistribution distribution;
        BookingClassInstance bInstance;

        double aggFare = 0;
        double aggDemand = 0;
        double aggStd = 0;
        double aggVr = 0;

        double avgDemand = 0;
        double std;
        int numItem=0;

        Integer protectLvl1 = bookClassInstanceList.get(0).getFlightCabin().getCabinClass().getSeatCount();
        Integer protectLvl2 = 0;
        for (int i = bookClassInstanceList.size() - 1; i >= 0; i--) {

            System.out.println("i=" + i);
            for (int k = 0; k < i; k++) {

//                System.out.println("k=" + k + " and seat protected for " + bookClassInstanceList.get(k).getBookingClass().getAnnotation() + " is " + (int) distribution.inverseCumulativeProbability(1 - (double) bookClassInstanceList.get(i).getPrice() / bookClassInstanceList.get(k).getPrice()));
                aggFare += bookClassInstanceList.get(k).getPrice()*bookClassInstanceList.get(k).getAvgDemand();
                aggDemand += bookClassInstanceList.get(k).getAvgDemand();
                aggVr += Math.pow(bookClassInstanceList.get(k).getStd(), 2);
               numItem++;
            }
            
            if(numItem>0){
            aggFare=aggFare/aggDemand;
            aggStd = Math.sqrt(aggVr);
            System.out.println("aggDmd is "+aggDemand+" and aggStd is "+aggStd);
            distribution = new NormalDistribution(aggDemand, aggStd);
            protectLvl2 = (int) distribution.inverseCumulativeProbability(1 - (double) bookClassInstanceList.get(i).getPrice() / aggFare);
           System.out.println("Optimal Value is "+ distribution.inverseCumulativeProbability(1 - (double) bookClassInstanceList.get(i).getPrice() / aggFare));
            
            }
            
            System.out.println("protection level2 in loop "+i+" is "+protectLvl2);
            if (protectLvl2 <= protectLvl1) {
                optimalSeatNo = protectLvl1 - protectLvl2;

            } else {
                optimalSeatNo = 0;
            }
            bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
            bInstance.setOptimalSeatNo(optimalSeatNo);
            em.merge(bInstance);
            bookClassInstanceList.set(i, bInstance);

//            System.out.println("protection level above booking class "+bookClassInstanceList.get(i).getBookingClass().getAnnotation()+" is "+protectLvl2);
            protectLvl1 = protectLvl2;
            protectLvl2 = 0;
            aggFare = 0;
            aggDemand = 0;
            aggStd = 0;
            aggVr = 0;
            numItem=0;

        }

        return bookClassInstanceList;
    }

    
    
    
    
    
    //emsr- a
    public List<BookingClassInstance> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList) {
        Double optimalValue;
        int optimalSeatNo;
        int totalAllocated = 0;
        NormalDistribution distribution;
        BookingClassInstance bInstance;
        double avgDemand;
        double std;

        Integer protectLvl1 = bookClassInstanceList.get(0).getFlightCabin().getCabinClass().getSeatCount();
        Integer protectLvl2 = 0;

        for (int i = bookClassInstanceList.size() - 1; i >= 0; i--) {

            System.out.println("i=" + i);
            for (int k = 0; k < i; k++) {
                distribution = new NormalDistribution(bookClassInstanceList.get(k).getAvgDemand(), bookClassInstanceList.get(k).getStd());
                System.out.println("k=" + k + " and seat protected for " + bookClassInstanceList.get(k).getBookingClass().getAnnotation() + " is " + (int) distribution.inverseCumulativeProbability(1 - (double) bookClassInstanceList.get(i).getPrice() / bookClassInstanceList.get(k).getPrice()));
                protectLvl2 += (int) distribution.inverseCumulativeProbability(1 - (double) bookClassInstanceList.get(i).getPrice() / bookClassInstanceList.get(k).getPrice());

            }

            if (protectLvl2 <= protectLvl1) {
                optimalSeatNo = protectLvl1 - protectLvl2;

            } else {
                optimalSeatNo = 0;
            }
            bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
            bInstance.setOptimalSeatNo(optimalSeatNo);
            em.merge(bInstance);
            bookClassInstanceList.set(i, bInstance);

//            System.out.println("protection level above booking class "+bookClassInstanceList.get(i).getBookingClass().getAnnotation()+" is "+protectLvl2);
            protectLvl1 = protectLvl2;
            protectLvl2 = 0;

        }

        return bookClassInstanceList;

    }
    //first version of optimal seat allocation
//    public List<BookingClassInstance> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList) {
//        double optimalValue;
//        int optimalSeatNo;
//        int totalAllocated = 0;
//        NormalDistribution distribution;
//        BookingClassInstance bInstance;
//        double avgDemand;
//        double std;
//
//        System.out.println("in seatAssignBean: computeOptimalSeat():size of bookingClassInstanceList is" + bookClassInstanceList.size());
//        int totalSeat = bookClassInstanceList.get(0).getFlightCabin().getCabinClass().getSeatCount();
//
//        for (int i = 0; i < bookClassInstanceList.size(); i++) {
//            if (i != (bookClassInstanceList.size() - 1)) {
//
//                if (totalAllocated < totalSeat) {
//
//                    avgDemand = (double) bookClassInstanceList.get(i).getAvgDemand();
//                    std = (double) bookClassInstanceList.get(i).getStd();
//
//                    System.out.println("in seatAssignBean: computeOptimalSeat(): avg demand is " + avgDemand + " and std is " + std);
//
//                    distribution = new NormalDistribution(avgDemand, std);
//                    optimalValue = distribution.inverseCumulativeProbability(1 - ((Double) bookClassInstanceList.get(i + 1).getPrice() / bookClassInstanceList.get(i).getPrice()));
//                    System.out.println("optimal value = " + optimalValue);
//
//                    optimalSeatNo = (int) optimalValue;
//
//                    if (optimalSeatNo > totalSeat - totalAllocated) {
//                        optimalSeatNo = totalSeat - totalAllocated;
//                    }
//
//                    totalAllocated += optimalValue;
//
//                } else {
//                    optimalSeatNo = 0;
//                }
//
//            } else {
//                if (totalAllocated < totalSeat) {
//                    optimalSeatNo = bookClassInstanceList.get(i).getFlightCabin().getCabinClass().getSeatCount() - totalAllocated;
//                } else {
//                    optimalSeatNo = 0;
//                }
//            }
//            bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
//            bInstance.setOptimalSeatNo(optimalSeatNo);
//            em.merge(bInstance);
//            bookClassInstanceList.set(i, bInstance);
//
//        }
//
//        return bookClassInstanceList;
//    }
    
    
    public FlightInstance findFlightInstance(String flightNo, String dateString) {
        FlightInstance selected;
        Query query = em.createQuery("SELECT f FROM FlightInstance f where f.flightFrequency.flightNo=:flightNum AND f.date=:dateString ");
        query.setParameter("flightNum", flightNo);
        query.setParameter("dateString", dateString);

        List<FlightInstance> resultList = query.getResultList();

        System.out.println("flightInstance returned is " + resultList.get(0));
        return resultList.get(0);

    }

    public List<FlightCabin> getCabinList(String flightNo, String dateString) {

        cabinList = new ArrayList<FlightCabin>();
        System.out.println("seatAssignBean: getCabinList(): flight no is " + flightNo);

        Query query = em.createQuery("SELECT f FROM FlightCabin f where f.flightInstance.flightFrequency.flightNo=:fname AND f.flightInstance.date=:fdate");
        query.setParameter("fname", flightNo);
        query.setParameter("fdate", dateString);
        System.out.println("seatAssignBean: getFlightList(): flights are " + query.getResultList().toString());
        List<FlightCabin> resultList = query.getResultList();
//        for (FlightCabin temp : resultList) {
//            if (!flightList.contains(temp.getFlightFrequency())) {
//                flightList.add(temp.getFlightFrequency());
//            }
//        }
        return resultList;

    }

    public ArrayList<BookingClassInstance> listAssign(List<BookingClassInstance> bookClassInstanceList, String cabinName) {
        ArrayList<BookingClassInstance> instanceList = new ArrayList<>();
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getFlightCabin().getCabinClass().getCabinName().equals(cabinName)) {
                instanceList.add(bookClassInstanceList.get(i));
            }

        }
        return instanceList;
    }

    public void editDemandInfo(BookingClassInstance bInstance) {
        BookingClassInstance edited = em.find(BookingClassInstance.class, bInstance.getId());
        edited = bInstance;
        em.merge(edited);
    }

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

    public List<BookingClassInstance> getBkiList(String cabinName, FlightInstance flightInstance) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        System.out.println("seatAssignBean: flightInstance selected is " + flightInstance);
        System.out.println("seatAssignBean: cabin selected is " + cabinName);
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.cabinClass.cabinName=:cabinName AND b.flightCabin.flightInstance=:flightInstance");
        query.setParameter("cabinName", cabinName);
        query.setParameter("flightInstance", flightInstance);
        bkiList = (List<BookingClassInstance>) query.getResultList();

        Collections.sort(bkiList);
        Collections.reverse(bkiList);

        System.out.println("seatAssignBean: getBkiList size()= " + bkiList.size());
        return bkiList;
    }

    public List<BookingClassInstance> sortbkiList(List<BookingClassInstance> bkiList) {
        Double maxPrice = bkiList.get(0).getPrice();
        List<BookingClassInstance> newList = new ArrayList<>();

        for (int i = 0; i < bkiList.size(); i++) {
            if (bkiList.get(i).getPrice() > maxPrice) {
                maxPrice = bkiList.get(i).getPrice();
                newList.add(bkiList.get(i));
                bkiList.get(i).setPrice(0.0);
            }
        }

        System.out.println("SeatAssignBean: sortBkiList():size of new list is " + newList.size());
        for (int i = 0; i < newList.size(); i++) {
            System.out.println(newList.get(i).getPrice());
        }

        return newList;

    }

    public List<BookingClassInstance> getSuiteInstance() {
        return suiteInstance;
    }

    public void setSuiteInstance(List<BookingClassInstance> suiteInstance) {
        this.suiteInstance = suiteInstance;
    }

    public List<BookingClassInstance> getFirstInstance() {
        return firstInstance;
    }

    public void setFirstInstance(List<BookingClassInstance> firstInstance) {
        this.firstInstance = firstInstance;
    }

    public List<BookingClassInstance> getBizInstance() {
        return bizInstance;
    }

    public void setBizInstance(List<BookingClassInstance> bizInstance) {
        this.bizInstance = bizInstance;
    }

    public List<BookingClassInstance> getPremiumInstance() {
        return premiumInstance;
    }

    public void setPremiumInstance(List<BookingClassInstance> premiumInstance) {
        this.premiumInstance = premiumInstance;
    }

    public List<BookingClassInstance> getEconInstance() {
        return econInstance;
    }

    public void setEconInstance(List<BookingClassInstance> econInstance) {
        this.econInstance = econInstance;
    }

}
