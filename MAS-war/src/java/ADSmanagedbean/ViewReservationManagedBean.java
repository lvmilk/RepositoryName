/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.APS.FlightInstance;
import SessionBean.ADS.ManageReservationBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "viewReservation")
@ViewScoped
public class ViewReservationManagedBean implements Serializable {

    @EJB
    private ManageReservationBeanLocal mr;
    private List<Reservation> rsvList = new ArrayList<>();
    private List<Reservation> resultRsvList = new ArrayList<>();

    private List<FlightInstance> departed = new ArrayList<>();
    private List<FlightInstance> returned = new ArrayList<>();
    private Reservation selectedRsv=new Reservation();
    private List<Passenger> psgList=new ArrayList<>();
    
    private String bkSystem;
    private String companyName;
    
    public ViewReservationManagedBean() {
    }

    @PostConstruct
    public void init() {
        companyName=(String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyName");
        
        rsvList = mr.getCompanyReservations(companyName);

    }

    public void onSelectRsv(Reservation rsv) {      
        int index=0;
        selectedRsv=rsv;
        List<FlightInstance> flights=new ArrayList<>();
        String origin=rsv.getOrigin();
        String dest=rsv.getDest();
        
        for(int i=0; i<rsv.getBkcInstance().size(); i++){
        System.out.println("i="+i+" flightInstance is "+rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
        flights.add(rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance());
        
        }
        Collections.sort(flights);
        
        psgList=mr.getPassengerList(rsv);
        
        departed=mr.getFlightPackage(flights, origin, dest, index);
        if (rsv.getReturnTrip()) {
         returned=mr.getFlightPackage(flights, dest, origin, departed.size());
        }
        
 

    }

    public List<Reservation> getRsvList() {
        return rsvList;
    }

    public void setRsvList(List<Reservation> rsvList) {
        this.rsvList = rsvList;
    }

    public List<Reservation> getResultRsvList() {
        return resultRsvList;
    }

    public void setResultRsvList(List<Reservation> resultRsvList) {
        this.resultRsvList = resultRsvList;
    }

    public List<FlightInstance> getDeparted() {
        return departed;
    }

    public void setDeparted(List<FlightInstance> departed) {
        this.departed = departed;
    }

    public List<FlightInstance> getReturned() {
        return returned;
    }

    public void setReturned(List<FlightInstance> returned) {
        this.returned = returned;
    }

    public Reservation getSelectedRsv() {
        return selectedRsv;
    }

    public void setSelectedRsv(Reservation selectedRsv) {
        this.selectedRsv = selectedRsv;
    }

    public List<Passenger> getPsgList() {
        return psgList;
    }

    public void setPsgList(List<Passenger> psgList) {
        this.psgList = psgList;
    }

    /**
     * @return the bkSystem
     */
    public String getBkSystem() {
        return bkSystem;
    }

    /**
     * @param bkSystem the bkSystem to set
     */
    public void setBkSystem(String bkSystem) {
        this.bkSystem = bkSystem;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    
    
}
