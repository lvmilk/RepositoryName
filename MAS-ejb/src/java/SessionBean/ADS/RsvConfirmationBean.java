/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Passenger;
import Entity.ADS.Ticket;
import Entity.APS.FlightInstance;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private Ticket ticket;
    private Passenger psg;
    
    private String depCity;
    private String arrCity;
    private String depTime;
    private String arrTime;
    private String flightNo;
    
    private String bookSystem="ARS";
    
    @Override
    public void setupPsg_Ticket(ArrayList<FlightInstance> departSelected, ArrayList<FlightInstance> returnSelected, ArrayList<Passenger> passengerList) {
        Ticket depTicket=new Ticket();
        Ticket arrTicket=new Ticket();
        Date temp;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        for (int i = 0; i < departSelected.size(); i++) {
//            depTicket=new Ticket();
            depCity=departSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity=departSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp=departSelected.get(i).getStandardDepTimeDateType();
            depTime=df.format(temp);
            flightNo=departSelected.get(i).getFlightFrequency().getFlightNo();
            depTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bookSystem);
            
            for(int j=0;j<passengerList.size();j++)
            {
                psg=passengerList.get(j);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl!=null)
                {
                    depTicket.setPassenger(psgl);
                }
            }
            em.persist(depTicket);
            em.flush();
            
            for(int k=0;k<passengerList.size();k++)
            {
                psg=passengerList.get(k);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl!=null)
                {
                    psgl.getTicket().add(depTicket);
                }
                em.merge(psgl);
                em.flush();
            }    
        }
        
        for (int i = 0; i < returnSelected.size(); i++) {
//            arrTicket=new Ticket();
            depCity=departSelected.get(i).getFlightFrequency().getRoute().getOrigin().getCityName();
            arrCity=departSelected.get(i).getFlightFrequency().getRoute().getDest().getCityName();
            temp=departSelected.get(i).getStandardDepTimeDateType();
            depTime=df.format(temp);
            flightNo=departSelected.get(i).getFlightFrequency().getFlightNo();
            arrTicket.createTicket(depCity, arrCity, depTime, arrTime, flightNo, bookSystem);
            
            for(int j=0;j<passengerList.size();j++)
            {
                psg=passengerList.get(j);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl!=null)
                {
                    depTicket.setPassenger(psgl);
                }      
            }
            em.persist(arrTicket);
            em.flush();
            
            for(int k=0;k<passengerList.size();k++)
            {
                psg=passengerList.get(k);
                Passenger psgl = em.find(Passenger.class, psg.getId());
                if (psgl!=null)
                {
                    psgl.getTicket().add(depTicket);
                }
                em.merge(psgl);
                em.flush();
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
