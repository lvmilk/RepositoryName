/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import SessionBean.ADS.BookerBeanLocal;
import SessionBean.ADS.PassengerBeanLocal;
import SessionBean.ADS.RsvConfirmationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "tkMB")
@ViewScoped
public class TicketManagedBean implements Serializable {

    @EJB
    private PassengerBeanLocal psgSBlocal;
    @EJB
    private BookerBeanLocal msblocal;
    @EJB
    private RsvConfirmationBeanLocal rsvCflocal;

    private ArrayList<BookingClassInstance> BookClassInstanceList = new ArrayList<>();
    private Long bookerId;
    private String firstName;
    private String lastName;
    private String email;

    private Booker booker = new Booker();
    private ArrayList<Passenger> passengerList = new ArrayList<>();
    private Passenger person = new Passenger();
    private Integer psgCount;

    private ArrayList<FlightInstance> departSelected = new ArrayList<>();
    private ArrayList<FlightInstance> returnSelected = new ArrayList<>();
    private Double totalPrice;

    private String origin;
    private String dest;
    private Boolean returnTrip;
    private Boolean visiMember;
    
    private ArrayList<Passenger> psgList;

    @PostConstruct
    public void init() {
        try {

            booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");

            visiMember = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("visiMember");

            origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
            dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
            returnTrip = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnTrip");

            departSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSelected");
            returnSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSelected");
            totalPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalPrice");

            passengerList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
            setPsgCount((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson"));

            BookClassInstanceList = (ArrayList<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstanceList");

            System.out.println("in the ticketManagedBean init passengerlist size is: " + passengerList.size());
            System.out.println("in the ticketManagedBean init first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void rsvConfirm() throws IOException {
        System.out.println("in the rsvConfirmation passengerlist size is: " + passengerList.size());
        System.out.println("in the first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());
        psgSBlocal.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, psgCount, origin, dest, returnTrip);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./payment.xhtml");

    }

    /**
     * @return the passengerList
     */
    public ArrayList<Passenger> getPassengerList() {
        return passengerList;
    }

    /**
     * @param passengerList the passengerList to set
     */
    public void setPassengerList(ArrayList<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    /**
     * @return the person
     */
    public Passenger getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Passenger person) {
        this.person = person;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the booker
     */
    public Booker getBooker() {
        return booker;
    }

    /**
     * @param member the booker to set
     */
    public void setBooker(Booker member) {
        this.booker = member;
    }

    /**
     * @return the psgCount
     */
    public Integer getPsgCount() {
        return psgCount;
    }

    /**
     * @param psgCount the psgCount to set
     */
    public void setPsgCount(Integer psgCount) {
        this.psgCount = psgCount;
    }

    public ArrayList<FlightInstance> getDepartSelected() {
        return departSelected;
    }

    public void setDepartSelected(ArrayList<FlightInstance> departSelected) {
        this.departSelected = departSelected;
    }

    public ArrayList<FlightInstance> getReturnSelected() {
        return returnSelected;
    }

    public void setReturnSelected(ArrayList<FlightInstance> returnSelected) {
        this.returnSelected = returnSelected;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
