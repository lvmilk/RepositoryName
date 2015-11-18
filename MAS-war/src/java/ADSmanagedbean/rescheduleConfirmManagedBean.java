/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import SessionBean.ADS.BookerBeanLocal;
import SessionBean.ADS.ManageReservationBeanLocal;
import SessionBean.ADS.PassengerBeanLocal;
import SessionBean.ADS.RsvConfirmationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LI HAO
 */
@Named(value = "rescheduleConfirm")
@ViewScoped
public class rescheduleConfirmManagedBean implements Serializable {

    @EJB
    private PassengerBeanLocal psgSBlocal;
    @EJB
    private BookerBeanLocal msblocal;
    @EJB
    private RsvConfirmationBeanLocal rsvCflocal;

    @EJB
    private ManageReservationBeanLocal mrLocal;

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

    private ArrayList<FlightInstance> departed = new ArrayList<>();
    private ArrayList<FlightInstance> returned = new ArrayList<>();

    private Double totalPrice;
    private Double priceDiff = 0.0;
    private Double changeRoutePenalty = 0.0;
    private Double changeDatePenalty = 0.0;
    private Double totalPenalty = 0.0;

    private String origin;
    private String dest;
    private Boolean returnTrip;
    private Boolean visiMember;

    private ArrayList<Passenger> psgList;
    private String stfType;

    private Reservation selectedRsv;
    private String manageStatus;

    private String bkSystem;
    private String cabinName;
    private String companyName;

    @PostConstruct
    public void init() {
        try {

            cabinName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinName");
            manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manageStatus");
            selectedRsv = (Reservation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedRsv");
            bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");
            companyName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyName");

            visiMember = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("visiMember");
            stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");
            origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
            dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
            returnTrip = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnTrip");

            departed = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departed");
            returned = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returned");

            departSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSelected");
            returnSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSelected");
            totalPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalPrice");

            passengerList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
            setPsgCount((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson"));

            BookClassInstanceList = (ArrayList<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstanceList");

            System.out.println("in the ticketManagedBean init passengerlist size is: " + passengerList.size());
            System.out.println("in the ticketManagedBean init first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());

            priceDiff = mrLocal.computePriceDiff(totalPrice, selectedRsv.getPayment().getTotalPrice());

            changeRoutePenalty = mrLocal.getChangeRoutePenalty(departed, returned, departSelected, returnSelected, selectedRsv.getBkcInstance(), BookClassInstanceList);

            changeDatePenalty = mrLocal.getChangeDatePenalty(departed, returned, departSelected, returnSelected, selectedRsv.getBkcInstance());

            totalPenalty = psgCount * (priceDiff + changeRoutePenalty + changeDatePenalty);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onChooseConfirm() {
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgGrd').show()");

    }

    public void rsvConfirm() throws IOException {
        System.out.println("in the rsvConfirmation passengerlist size is: " + passengerList.size());
        System.out.println("in the first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());
        if (stfType.equals("agency")) {
            this.setBkSystem("DDS");
        } else {
            this.setBkSystem("ARS");
        }

        mrLocal.rescheduleRsv(selectedRsv, passengerList, departSelected, returnSelected, BookClassInstanceList, origin, dest, returnTrip, totalPenalty, bkSystem, companyName);

//        psgSBlocal.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, psgCount, origin, dest, returnTrip);
        if (stfType.equals("agency")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Book flight successfully."));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
            
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsWorkspace.xhtml");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Book flight successfully."));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addRsvSuccess.xhtml");

        }

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

    /**
     * @return the stfType
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param stfType the stfType to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
    }

    public ArrayList<FlightInstance> getDeparted() {
        return departed;
    }

    public void setDeparted(ArrayList<FlightInstance> departed) {
        this.departed = departed;
    }

    public ArrayList<FlightInstance> getReturned() {
        return returned;
    }

    public void setReturned(ArrayList<FlightInstance> returned) {
        this.returned = returned;
    }

    public Reservation getSelectedRsv() {
        return selectedRsv;
    }

    public void setSelectedRsv(Reservation selectedRsv) {
        this.selectedRsv = selectedRsv;
    }

    public Double getPriceDiff() {
        return priceDiff;
    }

    public void setPriceDiff(Double priceDiff) {
        this.priceDiff = priceDiff;
    }

    public Double getChangeRoutePenalty() {
        return changeRoutePenalty;
    }

    public void setChangeRoutePenalty(Double changeRoutePenalty) {
        this.changeRoutePenalty = changeRoutePenalty;
    }

    public Double getChangeDatePenalty() {
        return changeDatePenalty;
    }

    public void setChangeDatePenalty(Double changeDatePenalty) {
        this.changeDatePenalty = changeDatePenalty;
    }

    public Double getTotalPenalty() {
        return totalPenalty;
    }

    public void setTotalPenalty(Double totalPenalty) {
        this.totalPenalty = totalPenalty;
    }

    /**
     * @return the manageStatus
     */
    public String getManageStatus() {
        return manageStatus;
    }

    /**
     * @param manageStatus the manageStatus to set
     */
    public void setManageStatus(String manageStatus) {
        this.manageStatus = manageStatus;
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

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

}
