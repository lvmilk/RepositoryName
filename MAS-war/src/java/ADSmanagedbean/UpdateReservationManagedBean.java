/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.APS.FlightInstance;
import SessionBean.ADS.BookerBeanLocal;
import SessionBean.ADS.ManageReservationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "updateReservation")
@ViewScoped
public class UpdateReservationManagedBean implements Serializable {

    @EJB
    ManageReservationBeanLocal mr;
    @EJB
    private BookerBeanLocal mbsbl;

    List<Passenger> selectedPsgList = new ArrayList<>();
    List<Reservation> rsvList = new ArrayList<>();
    List<Reservation> resultRsvList = new ArrayList<>();

    List<FlightInstance> departed = new ArrayList<>();
    List<FlightInstance> returned = new ArrayList<>();
    Reservation selectedRsv = new Reservation();
    ArrayList<Passenger> psgList = new ArrayList<>();
    List<FlightInstance> flights = new ArrayList<>();
    String origin;
    String dest;
    Booker booker;

    String emailOrigin;

    String manageStatus;

    public UpdateReservationManagedBean() {
    }

    @PostConstruct
    public void init() {

        selectedPsgList = (List<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
        rsvList = mr.getAllReservations();
        selectedRsv = (Reservation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedRsv");
        flights = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flights");
        origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
        dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
        departed = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departed");
        returned = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returned");
        psgList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("psgList");
        emailOrigin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("emailOrigin");
        booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");
        manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manageStatus");
    }

    public void onSelectPsg() throws IOException {

        System.out.println("Selected passenger list is " + selectedPsgList.size());

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookedFlights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", selectedPsgList);
        manageStatus="rebook";
        System.out.println("onSelectPsg(): manageStatus is " + manageStatus);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./reScheduleFlight3.xhtml");

    }

    public void onSelectRescheduleRsv(Reservation rsv) throws IOException {
        int index = 0;
        selectedRsv = rsv;
        flights = new ArrayList<>();
        origin = rsv.getOrigin();
        dest = rsv.getDest();
        


        for (int i = 0; i < rsv.getBkcInstance().size(); i++) {
            System.out.println("i=" + i + " flightInstance is " + rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
            flights.add(rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance());

        }
        Collections.sort(flights);

        psgList = mr.getPassengerList(rsv);

        departed = mr.getFlightPackage(flights, origin, dest, index);
        if (rsv.getReturnTrip()) {
            returned = mr.getFlightPackage(flights, dest, origin, departed.size());
        }
        System.out.println("booker found is " + selectedRsv.getBooker());

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", departed);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returned", returned);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgList", psgList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./reScheduleFlight2.xhtml");

    }

    public void onSelectBooker() throws IOException {

        booker = selectedRsv.getBooker();
        manageStatus="rebook";

        System.out.println("booker selected is " + booker);
        emailOrigin = booker.getEmail();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("emailOrigin", emailOrigin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editBookerPage.xhtml");

    }

    public void editBookerAccount() throws IOException {
        if (!mbsbl.checkEmailDuplicate(booker.getEmail(), emailOrigin)) {
            mbsbl.editThisBooker(booker);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
        }
    }

    public void onSelectRsv(Reservation rsv) throws IOException {
        int index = 0;
        selectedRsv = rsv;
        flights = new ArrayList<>();
        origin = rsv.getOrigin();
        dest = rsv.getDest();
        
        manageStatus="rebook";

        for (int i = 0; i < rsv.getBkcInstance().size(); i++) {
            System.out.println("i=" + i + " flightInstance is " + rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo());
            flights.add(rsv.getBkcInstance().get(i).getFlightCabin().getFlightInstance());

        }
        Collections.sort(flights);

        psgList = mr.getPassengerList(rsv);

        departed = mr.getFlightPackage(flights, origin, dest, index);
        if (rsv.getReturnTrip()) {
            returned = mr.getFlightPackage(flights, dest, origin, departed.size());
        }
        System.out.println("booker found is " + selectedRsv.getBooker());

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", departed);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returned", returned);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgList", psgList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);

//        FacesContext.getCurrentInstance().getExternalContext().redirect("./updateReservation2.xhtml");
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

    public ArrayList<Passenger> getPsgList() {
        return psgList;
    }

    public void setPsgList(ArrayList<Passenger> psgList) {
        this.psgList = psgList;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Booker getBooker() {
        return booker;
    }

    public void setBooker(Booker booker) {
        this.booker = booker;
    }

    public List<Passenger> getSelectedPsgList() {
        return selectedPsgList;
    }

    public void setSelectedPsgList(List<Passenger> selectedPsgList) {
        this.selectedPsgList = selectedPsgList;
    }

}
