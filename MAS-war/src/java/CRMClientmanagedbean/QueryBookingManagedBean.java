/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import SessionBean.ADS.ManageReservationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "queryBooking")
@ViewScoped
public class QueryBookingManagedBean implements Serializable {

    @EJB
    ManageReservationBeanLocal mr;

    private Boolean allRsv=true;
    private String email;
    private Long bookRef;
   
    private List<Reservation> rsvList = new ArrayList<>();
    private List<Reservation> resultRsvList = new ArrayList<>();

    private List<FlightInstance> departed = new ArrayList<>();
    private List<FlightInstance> returned = new ArrayList<>();
    private Reservation selectedRsv = new Reservation();
    private List<Passenger> psgList = new ArrayList<>();

    private String bkSystem;
    private String companyName;

    private Map<FlightInstance, BookingClassInstance> flightToBkInstance = new HashMap<>();


    public QueryBookingManagedBean() {
    }
    
    
    @PostConstruct
    public void init() {
     rsvList=(List<Reservation>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsvList");

    }

    public void onSelectSearchType() {
        System.out.println("allRsv is " + allRsv);
    }

    public void searchRsv() throws IOException {
        if (allRsv) {
            rsvList = mr.searchAllRsv(email);
            if (rsvList != null && !rsvList.isEmpty()) {
                System.out.println("rsvList is " + rsvList);
            }

        } else {
            Reservation rsv = mr.searchOneRsv(email, bookRef);
            if (rsv != null) {
                System.out.println("rsv is " + rsv);
                rsvList = new ArrayList<>();
                rsvList.add(rsv);

            }     
        }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvList", rsvList);
           FacesContext.getCurrentInstance().getExternalContext().redirect("./QueryReservationResult.xhtml");       
    }
    
    
     public void onSelectRsv(Reservation rsv) {
        int index = 0;
        selectedRsv = rsv;
        List<FlightInstance> flights = new ArrayList<>();
        String origin = rsv.getOrigin();
        String dest = rsv.getDest();
        List<BookingClassInstance> bookList = rsv.getBkcInstance();

        for (int i = 0; i < rsv.getBkcInstance().size(); i++) {
            System.out.println("i=" + i + " flightInstance is " + rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
            flights.add(rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance());

        }
        Collections.sort(flights);

        psgList = mr.getPassengerList(rsv);

        departed = mr.getFlightPackage(flights, origin, dest, index);
        
        System.out.println("In onSelectRsv(): departed is "+departed);

        for (int i = 0; i < bookList.size(); i++) {
            for (int j = 0; j < departed.size(); j++) {
                if (bookList.get(i).getFlightCabin().getFlightInstance().equals(departed.get(j))) {
                    flightToBkInstance.put(departed.get(j), bookList.get(i));
                    break;
                }
            }
        }

        if (rsv.getReturnTrip()) {
            returned = mr.getFlightPackage(flights, dest, origin, departed.size());

            for (int i = 0; i < bookList.size(); i++) {
                for (int j = 0; j < returned.size(); j++) {
                    if (bookList.get(i).getFlightCabin().getFlightInstance().equals(returned.get(j))) {
                        flightToBkInstance.put(returned.get(j), bookList.get(i));
                        break;
                    }
                }
            }
        }

    }


    public Boolean getAllRsv() {
        return allRsv;
    }

    public void setAllRsv(Boolean allRsv) {
        this.allRsv = allRsv;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getBookRef() {
        return bookRef;
    }

    public void setBookRef(Long bookRef) {
        this.bookRef = bookRef;
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

    public String getBkSystem() {
        return bkSystem;
    }

    public void setBkSystem(String bkSystem) {
        this.bkSystem = bkSystem;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Map<FlightInstance, BookingClassInstance> getFlightToBkInstance() {
        return flightToBkInstance;
    }

    public void setFlightToBkInstance(Map<FlightInstance, BookingClassInstance> flightToBkInstance) {
        this.flightToBkInstance = flightToBkInstance;
    }
    
    

}
