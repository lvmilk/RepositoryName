/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Member;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.ADS.Ticket;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LI HAO
 */
@Stateless
public class RsvConfirmationBean implements RsvConfirmationBeanLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    MemberBeanLocal msblocal;

    private Ticket ticket;
    private Passenger psg;

    private String depCity;
    private String arrCity;
    private String depTime;
    private String arrTime;
    private String flightNo;

    private String bookSystem = "ARS";

    @Override
    public void setupPsg_Ticket(ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<Passenger> passengerList, Long memberId, ArrayList<BookingClassInstance> BookClassInstanceList, int psgCount, String origin, String dest, Boolean returnTrip) {
        Ticket depTicket;
        Ticket arrTicket;
        ArrayList<Ticket> tkList = new ArrayList<Ticket>();
        Date temp;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("^^^^^^^^^^^^^^^Size of passengerList is: " + passengerList.size());

        for (int i = 0; i < departSelected.size(); i++) {
//            depTicket=new Ticket();
            depCity = departSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity = departSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp = departSelected.get(i).getStandardDepTimeDateType();
            depTime = df.format(temp);
            temp = departSelected.get(i).getStandardArrTimeDateType();
            arrTime = df.format(temp);
            flightNo = departSelected.get(i).getFlightFrequency().getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                depTicket = new Ticket();
                depTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bookSystem);
                psg = passengerList.get(j);
                System.out.println("*************Passenger Id is :" + psg.getId());
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl != null) {
                    depTicket.setPassenger(psgl);
                    em.persist(depTicket);
//                    em.flush();
                    psgl.getTickets().add(depTicket);
                    em.merge(psgl);
                    em.flush();
                    tkList.add(depTicket);
                }
            }

        }

        for (int i = 0; i < returnSelected.size(); i++) {
//            arrTicket=new Ticket();
            depCity = returnSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity = returnSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp = returnSelected.get(i).getStandardDepTimeDateType();
            depTime = df.format(temp);
            temp = returnSelected.get(i).getStandardArrTimeDateType();
            arrTime = df.format(temp);
            flightNo = returnSelected.get(i).getFlightFrequency().getFlightNo();

            for (int j = 0; j < passengerList.size(); j++) {
                arrTicket = new Ticket();
                arrTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bookSystem);
                psg = passengerList.get(j);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl != null) {
                    arrTicket.setPassenger(psgl);
                    em.persist(arrTicket);
//                    em.flush();

                    psgl.getTickets().add(arrTicket);
                    em.merge(psgl);
                    em.flush();
                    tkList.add(arrTicket);
                }
            }

        }

        setupTicket_Reservation(memberId, tkList, departSelected, returnSelected, BookClassInstanceList, psgCount, origin, dest, returnTrip);

    }

    private void setupTicket_Reservation(Long memberId, ArrayList<Ticket> tkList, ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<BookingClassInstance> BookClassInstanceList, int psgCount, String origin, String dest, Boolean returnTrip) {
        Member member = new Member();
        member = msblocal.retrieveMember(memberId);

        Reservation rsv = new Reservation();
        rsv.createReservation(member.getFirstName(), member.getLastName(), member.getEmail(), origin, dest, returnTrip);
        rsv.setTickets(tkList);
        em.persist(rsv);
        em.flush();
        
        for (int i = 0; i < tkList.size(); i++) {
            System.out.println("@@@@@@This is in setupTicket_Reservation:" + tkList.get(i));
            tkList.get(i).setRsv(rsv);
            em.merge(tkList.get(i));
            em.flush();
        }
        setupBookingClassInstace_Reservation(rsv,BookClassInstanceList, psgCount);
 
    }
    
    
    private void setupBookingClassInstace_Reservation( Reservation reservation, ArrayList<BookingClassInstance> BookClassInstanceList, int psgCount){
       Reservation rsv=em.find(Reservation.class, reservation.getId());
       BookingClassInstance instance=new BookingClassInstance();
       if(rsv!=null){
        for(int i=0; i<BookClassInstanceList.size(); i++){ 
        instance=em.find(BookingClassInstance.class,BookClassInstanceList.get(i).getId());
        instance.setBookedSeatNo(instance.getBookedSeatNo()+psgCount);
        instance.getReservation().add(rsv);
        rsv.getBkcInstance().add(instance);
        }
        em.merge(rsv);
        em.flush();
        
        for(int i=0; i<rsv.getBkcInstance().size(); i++){
         System.out.println("i="+i+" flightInstance is "+rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
        
      
        }
        
       }

    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    /**
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the psg
     */
    public Passenger getPsg() {
        return psg;
    }

    /**
     * @param psg the psg to set
     */
    public void setPsg(Passenger psg) {
        this.psg = psg;
    }

    /**
     * @return the depCity
     */
    public String getDepCity() {
        return depCity;
    }

    /**
     * @param depCity the depCity to set
     */
    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    /**
     * @return the arrCity
     */
    public String getArrCity() {
        return arrCity;
    }

    /**
     * @param arrCity the arrCity to set
     */
    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    /**
     * @return the depTime
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * @param depTime the depTime to set
     */
    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    /**
     * @return the arrTime
     */
    public String getArrTime() {
        return arrTime;
    }

    /**
     * @param arrTime the arrTime to set
     */
    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    /**
     * @return the flightNo
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * @param flightNo the flightNo to set
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

}
