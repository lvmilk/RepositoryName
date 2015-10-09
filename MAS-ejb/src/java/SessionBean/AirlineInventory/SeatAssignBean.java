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
    private FlightFrequency flightFrequency = new FlightFrequency();
    private List<BookingClassInstance> suiteInstance = new ArrayList<>();
    private List<BookingClassInstance> firstInstance = new ArrayList<>();
    private List<BookingClassInstance> bizInstance = new ArrayList<>();
    private List<BookingClassInstance> premiumInstance = new ArrayList<>();
    private List<BookingClassInstance> econInstance = new ArrayList<>();

  
    
    public List<BookingClassInstance> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList) {
        double optimalValue;
        int optimalSeatNo;
        int totalAllocated = 0;
        NormalDistribution distribution;
        BookingClassInstance bInstance;

        System.out.println("HAHAHAHHAA");
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (i != (bookClassInstanceList.size() - 1)) {
                System.out.println("for booking class " + bookClassInstanceList.get(i).getBookingClass().getAnnotation() + " avg demand is  " + bookClassInstanceList.get(i).getAvgDemand() + " and std is " + bookClassInstanceList.get(i).getStd());
                distribution = new NormalDistribution(bookClassInstanceList.get(i).getAvgDemand(), bookClassInstanceList.get(i).getStd());
                optimalValue = distribution.inverseCumulativeProbability((Double) bookClassInstanceList.get(i + 1).getPrice() / bookClassInstanceList.get(i).getPrice());

                optimalSeatNo = (int) optimalValue;
                System.out.println("optimal seat no for bookingclass " + bookClassInstanceList.get(i).getBookingClass().getAnnotation() + " is : " + optimalSeatNo);
                totalAllocated += optimalValue;
                bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
                bInstance.setOptimalSeatNo(optimalSeatNo);

                em.merge(bInstance);
                bookClassInstanceList.set(i, bInstance);

            } else {
                optimalSeatNo = bookClassInstanceList.get(i).getFlightCabin().getCabinClass().getSeatCount() - totalAllocated;
                bInstance = em.find(BookingClassInstance.class, bookClassInstanceList.get(i).getId());
                bInstance.setOptimalSeatNo(optimalSeatNo);

                em.merge(bInstance);
                bookClassInstanceList.set(i, bInstance);

            }

        }
        return bookClassInstanceList;
    }

    
    
    
    public List<BookingClassInstance> listAssign (List<BookingClassInstance> bookClassInstanceList, String cabinName) {
        List<BookingClassInstance> instanceList = new ArrayList<>();
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if(bookClassInstanceList.get(i).getFlightCabin().getCabinClass().getCabinName().equals(cabinName))
                instanceList.add(bookClassInstanceList.get(i));

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

    public List<BookingClassInstance> getBkiList(String flightNo, String date) {
        List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
        Query query = em.createQuery("SELECT b FROM BookingClassInstance b where b.flightCabin.flightInstance.date=:fdate AND b.flightCabin.flightInstance.flightFrequency.flightNo=:fflightNo");
        query.setParameter("fdate", date);
        query.setParameter("fflightNo", flightNo);
        bkiList = query.getResultList();
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
