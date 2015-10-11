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

    
    
    public double computeOptimalRev(List<BookingClassInstance> listInstance) {
        double totalRev = 0;
        for (int i = 0; i < listInstance.size(); i++) {
            totalRev += listInstance.get(i).getOptimalSeatNo() * listInstance.get(i).getPrice();

        }
        System.out.println("Total revenue is "+totalRev);
        return totalRev;

    }

    public List<BookingClassInstance> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList) {
        double optimalValue;
        int optimalSeatNo;
        int totalAllocated = 0;
        NormalDistribution distribution;
        BookingClassInstance bInstance;
        double avgDemand;
        double std;
        
        System.out.println("in seatAssignBean: computeOptimalSeat():size of bookingClassInstanceList is"+bookClassInstanceList.size());
        int totalSeat = bookClassInstanceList.get(0).getFlightCabin().getCabinClass().getSeatCount();

        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (i != (bookClassInstanceList.size() - 1)) {

                if (totalAllocated < totalSeat) {
                 
                  avgDemand=(double)bookClassInstanceList.get(i).getAvgDemand();
                  std=(double)bookClassInstanceList.get(i).getStd();
                  
                   System.out.println("in seatAssignBean: computeOptimalSeat(): avg demand is "+avgDemand+" and std is "+std);
                  
                  distribution = new NormalDistribution(avgDemand,std);
                    optimalValue = distribution.inverseCumulativeProbability((Double) bookClassInstanceList.get(i + 1).getPrice() / bookClassInstanceList.get(i).getPrice());

                    optimalSeatNo = (int) optimalValue;

                    if (optimalSeatNo > totalSeat - totalAllocated) {
                        optimalSeatNo = totalSeat - totalAllocated;
                    }

                    totalAllocated += optimalValue;

                } else {
                    optimalSeatNo = 0;
                }

            } else {
                if (totalAllocated < totalSeat) {
                    optimalSeatNo = bookClassInstanceList.get(i).getFlightCabin().getCabinClass().getSeatCount() - totalAllocated;
                } else {
                    optimalSeatNo = 0;
                }
            }
            bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
            bInstance.setOptimalSeatNo(optimalSeatNo);
            em.merge(bInstance);
            bookClassInstanceList.set(i, bInstance);

        }

        return bookClassInstanceList;
    }

       public FlightInstance findFlightInstance(String flightNo, String dateString){
       FlightInstance selected;
       Query query = em.createQuery("SELECT f FROM FlightInstance f where f.flightFrequency.flightNo=:flightNum AND f.date=:dateString ");
        query.setParameter("flightNum", flightNo);
         query.setParameter("dateString", dateString);
    
        List<FlightInstance> resultList = query.getResultList();

        System.out.println("flightInstance returned is "+resultList.get(0));
        return resultList.get(0);
    
       
       }
    
     public List<FlightCabin> getCabinList(String flightNo){
     
    cabinList = new ArrayList<FlightCabin>();
        System.out.println("seatAssignBean: getCabinList(): flight no is " + flightNo);

        Query query = em.createQuery("SELECT f FROM FlightCabin f where f.flightInstance.flightFrequency.flightNo=:fname");
        query.setParameter("fname", flightNo);
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
        System.out.println("seatAssignBean: flightInstance selected is "+flightInstance);
        System.out.println("seatAssignBean: cabin selected is "+cabinName);
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.cabinClass.cabinName=:cabinName AND b.flightCabin.flightInstance=:flightInstance");
        query.setParameter("cabinName", cabinName);
         query.setParameter("flightInstance", flightInstance);
        bkiList = (List<BookingClassInstance>) query.getResultList();
        System.out.println("seatAssignBean: getBkiList size()= "+query.getResultList().size());
        return bkiList;
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
