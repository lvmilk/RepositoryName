/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

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
import org.primefaces.context.RequestContext;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "flightCancel")
@ViewScoped
public class FlightCancelManagedBean implements Serializable {

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

    private String emailOrigin;
    private String manageStatus;
    private String stfType;
    private String bkSystem;
    private String companyName;

    private Double totalRefund;

    private Boolean allRsv;
    private String email;
    private Long bookRef;

    public FlightCancelManagedBean() {
    }

    @PostConstruct
    public void init() {

        selectedPsgList = (List<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
        companyName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyName");
        bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");

//        rsvList = mr.getAllReservations();
//        rsvList = mr.getCompanyReservations(companyName);
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
        stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");
//        bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");
//        System.out.println("CancelFlightManagedBean:ini: print booking system"+bkSystem);
        allRsv = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allRsv");
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");
//        bookRef = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bookRef");

        if (allRsv != null) {
            if (allRsv) {
                System.out.println("email is " + email);
                rsvList = mr.searchAllRsv(email);
                System.out.println("rsvList is " + rsvList);

            } else {
                bookRef = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bookRef");
                Reservation rsv = mr.searchOneRsv(email, bookRef);
                rsvList = new ArrayList<>();
                rsvList.add(rsv);
            }
        } else {
            allRsv = true;
        }

    }

    public void onChooseConfirm() {
        totalRefund = mr.computeCancelRefund(selectedRsv.getBkcInstance(), selectedPsgList.size());
        RequestContext context = RequestContext.getCurrentInstance();
        context.execute("PF('dlgGrd').show()");
    }

    public void rsvConfirm() throws IOException {

        System.out.println("in the rsvConfirmation passengerlist size is: " + selectedPsgList.size());
        System.out.println("in the first rsvConfirmation passenge ID is: " + selectedPsgList.get(0).getId());
//        if (stfType.equals("agency")) {
//            this.bkSystem = "DDS";
//        } else {
//            this.bkSystem = "ARS";
//        }

        mr.cancelFlight(selectedRsv, selectedPsgList, departed, returned, selectedRsv.getBkcInstance(), origin, dest, selectedRsv.getReturnTrip(), 0.0, bkSystem);

//        psgSBlocal.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, psgCount, origin, dest, returnTrip);
        if (stfType.equals("agency")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Cancel flight successfully."));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", new ArrayList<>());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsWorkspace.xhtml");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Congratulations!", "Cancel flight successfully."));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", new ArrayList<>());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returned", new ArrayList<>());

        }

    }

    public void onSelectPsg() throws IOException {

        System.out.println("Selected passenger list is " + selectedPsgList.size());

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookedFlights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", selectedPsgList);
        manageStatus = "rebook";
        System.out.println("onSelectPsg(): manageStatus is " + manageStatus);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);

        if (stfType.equals("agency")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsConfirmCancelFlight.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./cancelFlightConfirm.xhtml");
        }
    }

    public void onSelectCancelRsv(Reservation rsv) throws IOException {
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

        if (stfType.equals("agency")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsCancelation2.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./cancelFlight2.xhtml");
        }

    }

    public void onSelectRsv(Reservation rsv) throws IOException {
        int index = 0;
        selectedRsv = rsv;
        flights = new ArrayList<>();
        origin = rsv.getOrigin();
        dest = rsv.getDest();
        bkSystem = selectedRsv.getTickets().get(0).getBookSystem();

        manageStatus = "rebook";

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
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bkSystem", bkSystem);

//        FacesContext.getCurrentInstance().getExternalContext().redirect("./updateReservation2.xhtml");
    }

    public List<Reservation> getRsvList() {

        if (allRsv != null) {
            if (allRsv) {
                System.out.println("email is " + email);
                rsvList = mr.searchAllRsv(email);
                System.out.println("rsvList is " + rsvList);

            } else {
                bookRef = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bookRef");
                Reservation rsv = mr.searchOneRsv(email, bookRef);
                rsvList = new ArrayList<>();
                rsvList.add(rsv);
            }
        } else {
            allRsv = true;
        }

        System.out.println("in getRsvList(); rsvList is " + rsvList);
//        allRsv = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allRsv");
//        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");

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

    public Double getTotalRefund() {
        return totalRefund;
    }

    public void setTotalRefund(Double totalRefund) {
        this.totalRefund = totalRefund;
    }

}
