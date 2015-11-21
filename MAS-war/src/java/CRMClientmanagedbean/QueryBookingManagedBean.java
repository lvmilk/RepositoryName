/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

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
@Named(value = "queryBooking")
@ViewScoped
public class QueryBookingManagedBean implements Serializable {

    @EJB
    ManageReservationBeanLocal mr;
    @EJB
    private BookerBeanLocal mbsbl;

    private Boolean allRsv = true;
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
    private String manageStatus;

    private Booker booker;
    private Passenger selectedPsg;
    private Passenger newPsg = new Passenger();

    private List<FlightInstance> flights = new ArrayList<>();
    private String origin;
    private String dest;
    private List<Passenger> selectedPsgList = new ArrayList<>();

    private BookingClassInstance chosenBkInstance;
    private List<CabinClass> cabinList;
    private String cabinName;
    private List<FlightInstance> allFlights;
    private List<BookingClassInstance> allBookClassList;
    private FlightInstance selectedFlight;

    private CabinClass cabin;
    private Double priceIncrease;

    private Map<FlightInstance, BookingClassInstance> flightToBkInstance = new HashMap<>();

    public QueryBookingManagedBean() {
    }

    @PostConstruct
    public void init() {
//        rsvList = (List<Reservation>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsvList");
        allRsv = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allRsv");
        email = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("email");

        if (allRsv != null) {
            if (allRsv) {
                System.out.println("In init(): email is " + email);
                rsvList = mr.searchAllRsv(email);
                System.out.println("In init(): rsvList is " + rsvList);

            } else {
                bookRef = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bookRef");
                Reservation rsv = mr.searchOneRsv(email, bookRef);
                rsvList = new ArrayList<>();
                rsvList.add(rsv);
            }
        } else {
            allRsv = true;
        }

        selectedPsg = (Passenger) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedPsg");
        if (selectedPsg != null) {
            newPsg.setFirstName(selectedPsg.getFirstName());
            newPsg.setLastName(selectedPsg.getLastName());
            newPsg.setPassport(selectedPsg.getPassport());
            newPsg.setTitle(selectedPsg.getTitle());
            newPsg.setFfpName(selectedPsg.getFfpName());
            newPsg.setFfpNo(selectedPsg.getFfpNo());
        }
        manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manageStatus");
        booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");
        selectedRsv = (Reservation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedRsv");
        selectedPsgList = (List<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");

        chosenBkInstance = (BookingClassInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("chosenBkInstance");
        cabinList = (List<CabinClass>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");

//        chosenCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("chosenCabin");
        cabinName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinName");

        allFlights = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allFlights");
        allBookClassList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allBookClassList");
        flightToBkInstance = (Map<FlightInstance, BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightToBkInstance");

        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("upgSelectedFlight") != null) {
            selectedFlight = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("upgSelectedFlight");
        }

        flights = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flights");
        origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
        dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
        departed = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departed");
        returned = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returned");
        psgList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("psgList");

        booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");
        manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manageStatus");
        bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");

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
            FacesContext.getCurrentInstance().addMessage("Message", new FacesMessage("No Change of Cabin Class!"));
        }

    }

    public void onChooseUpgradePsg() throws IOException {
        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): flightToBkInstance map is " + flightToBkInstance);
        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): selectedRsv.bookClassInstanceList is " + selectedRsv.getBkcInstance());

        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): selectedFlight is " + selectedFlight);
        System.out.println("xxxxxxxxxxxxxxxxxxx in onChooseUpgradePsg(): selectedPsgList is " + selectedPsgList);

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
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("upgSelectedFlight", selectedFlight);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", cabinName);
//                 FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("chosenCabin", chosenCabin);

                FacesContext.getCurrentInstance().getExternalContext().redirect("./upgradeCabin2.xhtml");

            } else {
                FacesContext.getCurrentInstance().addMessage("ERROR", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one or more passengers for for upgrade of cabin ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one flight leg for for upgrade of cabin ", ""));
        }

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

    public void confirmUpgradeCabin() {

        System.out.println("in onChooseUpgradePsg(): chosenBkInstance is " + chosenBkInstance);
        System.out.println("in onChooseUpgradePsg(): selectedRsv.bcInstanceList is " + selectedRsv.getBkcInstance());
        if (selectedRsv.getBkcInstance().contains(chosenBkInstance)) {
            System.out.println("!!!!!!!!!!!!!!!!!!!!rsv bookList contains chosen List");
        } else {
            System.out.println("!!!!!!!!!!!!!!!!!!!!rsv bookList DOES NOT contain chosen List");
        }
               
            bkSystem="ARS";
            mr.upgradeCabinClass(selectedPsgList, selectedRsv, chosenBkInstance, cabinName, bkSystem, companyName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allFlights", new ArrayList<>());

            FacesContext.getCurrentInstance().addMessage("message:", new FacesMessage("Cabin Class Upgraded successfully!"));
   
    }

    public void onSavePsgChange() throws IOException {

        System.out.println("onSavePsgChange(): selectedPsg is " + selectedPsg);
        mr.ChangePassenger(selectedPsg, newPsg);
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!1after psg change!");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvList", new ArrayList<>());

        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Change passenger Successfully"));

    }

    public void onSavePsgChangeCancel() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./updatePerson.xhtml");
    }

    public void onSelectPsgBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./reschedule1.xhtml");
    }

    public void onSelectPsg() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedPsgList.isEmpty()) {
            context.execute("alert('Please select passenger(s) for rescheduling flight(s).');");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select passenger(s) ", "Please select passenger(s) for rescheduling flight(s) "));
        } else {
            System.out.println("Selected passenger list is " + selectedPsgList.size());

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookedFlights", flights);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", selectedPsgList);
            manageStatus = "rebook";
            System.out.println("onSelectPsg(): manageStatus is " + manageStatus);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./reschedule3.xhtml");
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
        System.out.println("selectedRsv.getBkcInstance() is " + selectedRsv.getBkcInstance());

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", selectedRsv.getBkcInstance().get(0).getFlightCabin().getCabinClass().getCabinName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flights", flights);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departed", departed);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returned", returned);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgList", psgList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./reschedule2.xhtml");

    }

    public void onSelectBooker() throws IOException {

        manageStatus = "rebook";
        booker = selectedRsv.getBooker();
        manageStatus = "rebook";

        System.out.println("booker selected is " + booker);
        email = booker.getEmail();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", email);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./updateBookerInfo.xhtml");

    }

    public void editBookerAccount() throws IOException {
        if (!mbsbl.checkEmailDuplicate(booker.getEmail(), email)) {
            mbsbl.editThisBooker(booker);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
              manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("",manageStatus);
            
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
        }
    }

    public void editBookerAccountBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./updatePerson.xhtml");
    }

    public void onUpdatePsg(Passenger psg) throws IOException {
        selectedPsg = psg;
        System.out.println("onSavePsgChange(): selectedPsg is " + selectedPsg);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedPsg", selectedPsg);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedRsv", selectedRsv);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./updatePsg1.xhtml");

    }

    public void onSelectSearchType() {
        System.out.println("allRsv is " + allRsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allRsv", allRsv);
    }

    public void searchRsv() throws IOException {
        if (allRsv) {
            rsvList = mr.searchAllRsv(email);
            if (rsvList != null && !rsvList.isEmpty()) {
                System.out.println("rsvList is " + rsvList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", email);

            }

        } else {
            Reservation rsv = mr.searchOneRsv(email, bookRef);
            if (rsv != null) {
                System.out.println("rsv is " + rsv);
                rsvList = new ArrayList<>();
                rsvList.add(rsv);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("email", email);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookRef", bookRef);

            }
        }
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvList", rsvList);
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

        System.out.println("In onSelectRsv(): departed is " + departed);

        flightToBkInstance = new HashMap<>();

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

        System.out.println("in getRsvList(): " + rsvList);
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

    public Booker getBooker() {
        return booker;
    }

    public void setBooker(Booker booker) {
        this.booker = booker;
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

    public List<Passenger> getSelectedPsgList() {
        return selectedPsgList;
    }

    public void setSelectedPsgList(List<Passenger> selectedPsgList) {
        this.selectedPsgList = selectedPsgList;
    }

    public FlightInstance getSelectedFlight() {
        return selectedFlight;
    }

    public void setSelectedFlight(FlightInstance selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    public List<FlightInstance> getAllFlights() {
        return allFlights;
    }

    public void setAllFlights(List<FlightInstance> allFlights) {
        this.allFlights = allFlights;
    }

    public List<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public Double getPriceIncrease() {
        return priceIncrease;
    }

    public void setPriceIncrease(Double priceIncrease) {
        this.priceIncrease = priceIncrease;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

}
