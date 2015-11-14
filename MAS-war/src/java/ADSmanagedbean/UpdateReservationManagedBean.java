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
import Entity.AIS.CabinClass;
import Entity.APS.FlightInstance;
import SessionBean.ADS.BookerBeanLocal;
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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "updateReservation")
@ViewScoped
public class UpdateReservationManagedBean implements Serializable {

    @EJB
    private ManageReservationBeanLocal mr;
    @EJB
    private BookerBeanLocal mbsbl;

    private List<Passenger> selectedPsgList = new ArrayList<>();
    private List<Reservation> rsvList = new ArrayList<>();
    private List<Reservation> resultRsvList = new ArrayList<>();

    private List<FlightInstance> departed = new ArrayList<>();
    private List<FlightInstance> returned = new ArrayList<>();
    private Reservation selectedRsv = new Reservation();
    private ArrayList<Passenger> psgList = new ArrayList<>();
    private List<FlightInstance> flights = new ArrayList<>();
    private String origin;
    private String dest;
    private Booker booker;

    private String emailOrigin;

    private String manageStatus;
    private Passenger selectedPsg;
    private Passenger newPsg = new Passenger();

    private List<BookingClassInstance> allBookClassList = selectedRsv.getBkcInstance();
    private List<FlightInstance> allFlights = new ArrayList<>();

    private Map<FlightInstance, BookingClassInstance> flightToBkInstance = new HashMap<>();

    private FlightInstance selectedFlight;
    private CabinClass cabin;
    private String cabinName;
    private CabinClass chosenCabin;
    private List<CabinClass> cabinList;

    private String bkSystem;
    private String companyName;

    private Double priceIncrease;
    private BookingClassInstance chosenBkInstance;

    public UpdateReservationManagedBean() {

    }

    @PostConstruct
    public void init() {

        chosenBkInstance = (BookingClassInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("chosenBkInstance");
        cabinList = (List<CabinClass>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");

//        chosenCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("chosenCabin");
        cabinName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinName");

        allFlights = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allFlights");
        allBookClassList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allBookClassList");
        flightToBkInstance = (Map<FlightInstance, BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightToBkInstance");

        selectedFlight = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlight");
        selectedPsg = (Passenger) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPsg");
        selectedPsgList = (List<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
        bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");
        companyName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyName");

        rsvList = mr.getCompanyReservations(companyName);
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

    public void onChooseUpgradeCabin() {
        Double oldPrice = 0.0;
        Double newPrice = 0.0;
//        ArrayList<BookingClassInstance> bookList = new ArrayList<>();
//        for (int i = 0; i < selectedRsv.getBkcInstance().size(); i++) {
//            bookList.add(selectedRsv.getBkcInstance().get(i));
//
//        }
        if (!cabinName.equals(chosenBkInstance.getFlightCabin().getCabinClass().getCabinName())) {

            List<BookingClassInstance> bookList = new ArrayList<>();
            for (int i = 0; i < selectedRsv.getBkcInstance().size(); i++) {
                bookList.add(selectedRsv.getBkcInstance().get(i));

            }
            oldPrice = mr.computeAllFlightsPrice(bookList);

            BookingClassInstance newInstance = mr.findLowestBkInstance(selectedFlight, cabinName, selectedPsgList.size());
            bookList.set(bookList.indexOf(chosenBkInstance), newInstance);
            newPrice = mr.computeAllFlightsPrice(bookList);

            priceIncrease = mr.computePriceDiff(newPrice, oldPrice);

            RequestContext context = RequestContext.getCurrentInstance();
            context.execute("PF('dlgGrd').show()");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("No Change of Cabin Class!"));
        }

    }

    public void onChooseUpgradePsg() throws IOException {
        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): flightToBkInstance map is " + flightToBkInstance);
        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): selectedRsv.bookClassInstanceList is " + selectedRsv.getBkcInstance());

        if (selectedFlight != null) {
            if (selectedPsgList != null && !selectedPsgList.isEmpty()) {
                chosenBkInstance = flightToBkInstance.get(selectedFlight);
                System.out.println("in onChooseUpgradePsg(): chosenBkInstance is " + chosenBkInstance);

                cabin = chosenBkInstance.getFlightCabin().getCabinClass();
                cabinList = mr.getUpgradeCabinList(chosenBkInstance, selectedPsgList.size());

                System.out.println("in onChooseUpgradePsg(): cabinList is " + cabinList);
                System.out.println("in onChooseUpgradePsg(): selectedPsgList is " + selectedPsgList);
                System.out.println("in onChooseUpgradePsg(): selectedFlight is " + selectedFlight);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("chosenBkInstance", chosenBkInstance);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", selectedPsgList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlight", selectedFlight);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", cabinName);
//                 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("chosenCabin", chosenCabin);

                FacesContext.getCurrentInstance().getExternalContext().redirect("./upgradeCabinClass2.xhtml");

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one or more passengers for for upgrade of cabin ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one flight leg for for upgrade of cabin ", ""));
        }

    }

    public void confirmUpgradeCabin() {

        System.out.println("in onChooseUpgradePsg(): chosenBkInstance is " + chosenBkInstance);
        System.out.println("in onChooseUpgradePsg(): selectedRsv.bcInstanceList is " + selectedRsv.getBkcInstance());
        if (selectedRsv.getBkcInstance().contains(chosenBkInstance)) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!rsv bookList contains chosen List");
        } else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!rsv bookList DOES NOT contain chosen List");
        }
        mr.upgradeCabinClass(selectedPsgList, selectedRsv, chosenBkInstance, cabinName, bkSystem, companyName);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Cabin Class Upgraded successfully!"));

    }

    public void onSelectUpgradeRsv(Reservation rsv) throws IOException {
        selectedRsv = rsv;
        List<BookingClassInstance> temp = new ArrayList<>();

        for (int i = 0; i < selectedRsv.getBkcInstance().size(); i++) {
            temp.add(selectedRsv.getBkcInstance().get(i));
        }
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@in onSelectUpgradeRsv(): selectedRsv.bookClassInstanceList is " + temp);
        allBookClassList = selectedRsv.getBkcInstance();
        allFlights = new ArrayList<>();

        psgList = mr.getPassengerList(rsv);

        System.out.println("size of psgList is " + psgList.size());

        for (int i = 0; i < allBookClassList.size(); i++) {
            FlightInstance flight = allBookClassList.get(i).getFlightCabin().getFlightInstance();
            allFlights.add(flight);
        }
        Collections.sort(allFlights);
        flightToBkInstance = new HashMap();

        for (int i = 0; i < allBookClassList.size(); i++) {
            BookingClassInstance bookInstance = allBookClassList.get(i);
            FlightInstance flight = allBookClassList.get(i).getFlightCabin().getFlightInstance();
            System.out.println("flight is " + flight);
            System.out.println("BookInstance is " + allBookClassList.get(i));
            flightToBkInstance.put(flight, allBookClassList.get(i));

        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allFlights", allFlights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allBookClassList", allBookClassList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgList", psgList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightToBkInstance", flightToBkInstance);

//        FacesContext.getCurrentInstance().getExternalContext().redirect("./upgradeCabinClass2.xhtml");
    }

    public void onUpdatePsg(Passenger psg) throws IOException {
        selectedPsg = psg;
        System.out.println("onSavePsgChange(): selectedPsg is " + selectedPsg);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPsg", selectedPsg);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./updatePassenger1.xhtml");

    }

    public void onSavePsgChange() throws IOException {

        System.out.println("onSavePsgChange(): selectedPsg is " + selectedPsg);
        mr.ChangePassenger(selectedPsg, newPsg);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./updatePsgSuccess.xhtml");
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

        if (bkSystem.equals("ARS")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./reScheduleFlight3.xhtml");
        } else if (bkSystem.equals("DDS")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsRescheduling3.xhtml");
        }

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
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", selectedRsv.getBkcInstance().get(0).getFlightCabin().getCabinClass().getCabinName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", departed);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returned", returned);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgList", psgList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);

        if (bkSystem.equals("ARS")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./reScheduleFlight2.xhtml");
        } else if (bkSystem.equals("DDS")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsRescheduling2.xhtml");
        }

    }

    public void onSelectBooker() throws IOException {

        booker = selectedRsv.getBooker();
        manageStatus = "rebook";

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

    public Passenger getSelectedPsg() {
        return selectedPsg;
    }

    public void setSelectedPsg(Passenger selectedPsg) {
        this.selectedPsg = selectedPsg;
    }

    public Passenger getNewPsg() {
        return newPsg;
    }

    public void setNewPsg(Passenger newPsg) {
        this.newPsg = newPsg;
    }

    public List<FlightInstance> getFlights() {
        return flights;
    }

    public void setFlights(List<FlightInstance> flights) {
        this.flights = flights;
    }

    public Map<FlightInstance, BookingClassInstance> getFlightToBkInstance() {
        return flightToBkInstance;
    }

    public void setFlightToBkInstance(Map<FlightInstance, BookingClassInstance> flightToBkInstance) {
        this.flightToBkInstance = flightToBkInstance;
    }

    public List<BookingClassInstance> getAllBookClassList() {
        return allBookClassList;
    }

    public void setAllBookClassList(List<BookingClassInstance> allBookClassList) {
        this.allBookClassList = allBookClassList;
    }

    public List<FlightInstance> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(List<FlightInstance> allFlights) {
        this.allFlights = allFlights;
    }

    public FlightInstance getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(FlightInstance selectedFlight) {
        this.selectedFlight = selectedFlight;
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

    public CabinClass getChosenCabin() {
        return chosenCabin;
    }

    public void setChosenCabin(CabinClass chosenCabin) {
        this.chosenCabin = chosenCabin;
    }

    public CabinClass getCabin() {
        return cabin;
    }

    public void setCabin(CabinClass cabin) {
        this.cabin = cabin;
    }

    public List<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public Double getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Double priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public BookingClassInstance getChosenBkInstance() {
        return chosenBkInstance;
    }

    public void setChosenBkInstance(BookingClassInstance chosenBkInstance) {
        this.chosenBkInstance = chosenBkInstance;
    }

}
