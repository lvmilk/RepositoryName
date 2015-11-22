/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testFolder;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import SessionBean.ADS.ReserveFlightBeanLocal;
import Entity.APS.Airport;
import Entity.AIS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.AIS.BookingClassInstance;
import Entity.AIS.FlightCabin;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.AIS.SeatAssignBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "testBean")
@ViewScoped
public class testManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fs;
    @EJB
    private SeatAssignBeanLocal sa;

    @EJB
    private ReserveFlightBeanLocal rf;

    private ArrayList<Integer> departIndexes = new ArrayList<>();
    private ArrayList<Integer> returnIndexes = new ArrayList<>();

    private ArrayList<ArrayList<FlightInstance>> departSpecificList = new ArrayList<>();
    private ArrayList<ArrayList<FlightInstance>> returnSpecificList = new ArrayList<>();

    private Double totalPrice = 0.0;

    private List<Airport> otherPlaces = new ArrayList<>();

    private String selectedIndex;
    private String selectedIndex2;

    private Boolean returnTrip = false;
//    private Boolean dateSpecific;

    private Integer countPerson;
    private Integer availableSeat;

    private String origin;
    private String dest;
    private List<Route> routeList;
    private List<FlightFrequency> initialFrequency;
//    private List<FlightFrequency> secondFrequency;
    private ArrayList<FlightInstance> departInstances;
    private ArrayList<FlightInstance> returnInstances;
    private List<FlightFrequency> resultFrequencies;


    private FlightInstance toInstance;
    private FlightInstance backInstance;

    private Map<String, FlightFrequency> flightMap;
    private String flightNo;
    private CabinClass selectedCabin;
    private String cabinName;
    private Integer seatUnallocated;
    ;
    private String flightString;
    private FlightFrequency frequency;
    private String dateString;
    private List<BookingClassInstance> bookClassInstanceList = new ArrayList<>();

    private FlightInstance selectedFlightInstance;
    private Integer currentAllocated;

    private Integer departDefault;
    private Integer returnDefault;

    private Date currentDate = new Date();
    private Date departDate = new Date();
    private Date returnDate = new Date();

    private String stfType;
    private List<CabinClass> cabinList = new ArrayList<>();
    private String manageStatus;

    private List<FlightInstance> bookedFlights = new ArrayList<>();

    private Booker booker;
    private ArrayList<Passenger> psgList;
    private Reservation selectedRsv;

    private ArrayList<FlightInstance> departed;
    private ArrayList<FlightInstance> returned;

    public testManagedBean() {
    }

    @PostConstruct
    public void init() {
        cabinName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinName");
        departed = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departed");
        returned = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returned");

        booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");
        selectedRsv = (Reservation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedRsv");

        manageStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manageStatus");
        if (manageStatus != null) {
            System.out.println("in ReserveFlightManagedBean: manageStatus is " + manageStatus);
        } else {
            manageStatus = "book";
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~````in ReserveFlightManagedBean: manageStatus Now is " + manageStatus);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
        }
        bookedFlights = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bookedFlights");

        departDefault = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departDefault");
        returnDefault = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnDefault");
        origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
        dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
        stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");

        initialFrequency = rf.getAllFlightFrequency();


        returnTrip = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnTrip");
//        dateSpecific = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateSpecific");
//        departDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departDate");
//        returnDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnDate");
//        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");
        countPerson = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson");

        departSpecificList = (ArrayList<ArrayList<FlightInstance>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSpecificList");
        returnSpecificList = (ArrayList<ArrayList<FlightInstance>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSpecificList");

//        seatUnallocated = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seatUnallocated");
//        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
//        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");
        selectedFlightInstance = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlightInstance");
//        cabinList = (List<FlightCabin>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");

        psgList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
        if (psgList != null) {
            countPerson = psgList.size();
        }

    }


    public void onDepartOptionChange(int index, String day) {
        System.out.println("Getting into onDepartOptionChange");
//        System.out.println("dateSpecific is " + dateSpecific);
        Boolean duplicate = false;
        Integer i = (Integer) index;

        

            System.out.println("size of departIndexes is " + departIndexes.size());
            if (i != null) {
                if (!departIndexes.isEmpty()) {
                    if (departIndexes.contains((Integer) i)) {
                        departIndexes.remove((Integer) i);
                    } else {
                        departIndexes.add((Integer) i);
                    }
                } else {
                    departIndexes.add(i);
                }
            }
            System.out.println("size of departIndexes now is " + departIndexes.size());
        
    }

    
    
    public void onReturnOptionChange(int index, String day) {
        System.out.println("Getting into onDepartOptionChange");
        Boolean duplicate = false;
        Integer i = (Integer) index;

       
            System.out.println("size of returnIndexes is " + returnIndexes.size());
            if (i != null) {
                if (!returnIndexes.isEmpty()) {
                    if (returnIndexes.contains((Integer) i)) {
                        returnIndexes.remove((Integer) i);
                    } else {
                        returnIndexes.add((Integer) i);
                    }
                } else {
                    returnIndexes.add(i);
                }
            }
            System.out.println("size of departIndexes now is " + returnIndexes.size());

        
    }

    public void onSelectOption() throws IOException {
        int checkCount = 0;
        String selectedDay = "";

//        if (dateSpecific) {
            if (departIndexes.isEmpty()) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one option for departure trip ", ""));
            } else if (departIndexes.size() > 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have selected more than one option for departure trip ", ""));
            } else {
                if (!returnTrip) {

                    ArrayList<FlightInstance> departSelected = departSpecificList.get(departIndexes.get(0));
                    ArrayList<FlightInstance> returnSelected = new ArrayList<>();
                    System.out.println("selection for departure trip is correct");
                    System.out.println("departure package chosen is " + departSelected);
                    ArrayList<BookingClassInstance> BookClassInstanceList = new ArrayList<>();

                    for (int i = 0; i < departSelected.size(); i++) {
                        BookClassInstanceList.add(rf.findCheapestAvailable(departSelected.get(i), selectedCabin, countPerson));
                    }

                    BookingClassInstance bInstance;

                    for (int i = 0; i < departSelected.size(); i++) {
                        bInstance = rf.findCheapestAvailable(departSelected.get(i), selectedCabin, countPerson);
                        if (bInstance != null) {
                            BookClassInstanceList.add(bInstance);
                        }
                    }

                    totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);
                    totalPrice *= countPerson;
                    System.out.println("Total price is " + totalPrice);

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstanceList", BookClassInstanceList);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnTrip", returnTrip);

                    if (stfType.equals("agency")) {
                        if (manageStatus.equals("book")) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsCreateBookerGuest.xhtml");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", psgList);
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsReBookConfirmPage.xhtml");
                        }
                    } else {
                        if (manageStatus.equals("book")) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", psgList);
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./reBookConfirmPage.xhtml");
                        }
                    }

                } else {
                    if (returnIndexes.isEmpty()) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one option for return trip ", ""));
                    } else if (returnIndexes.size() > 1) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have selected more than one option for return trip ", ""));
                    } else {
                        ArrayList<FlightInstance> departSelected = departSpecificList.get(departIndexes.get(0));
                        System.out.println("selection for departure trip is correct");
                        System.out.println("departure package chosen is " + departSelected);

                        ArrayList<FlightInstance> returnSelected = returnSpecificList.get(returnIndexes.get(0));
                        System.out.println("selection for return trip is correct");
                        System.out.println("return package chosen is " + returnSelected);

                        ArrayList<BookingClassInstance> BookClassInstanceList = new ArrayList<>();
                        BookingClassInstance bInstance;

                        for (int i = 0; i < departSelected.size(); i++) {
                            bInstance = rf.findCheapestAvailable(departSelected.get(i), selectedCabin, countPerson);
                            if (bInstance != null) {
                                BookClassInstanceList.add(bInstance);
                            }
                        }

                        for (int i = 0; i < returnSelected.size(); i++) {
                            bInstance = rf.findCheapestAvailable(returnSelected.get(i), selectedCabin, countPerson);
                            if (bInstance != null) {
                                BookClassInstanceList.add(bInstance);
                            }
                        }

                        totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);
                        totalPrice += rf.getLowestPrice(returnSelected, selectedCabin, countPerson);

                        totalPrice *= countPerson;
                        System.out.println("Total price is " + totalPrice);

                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstanceList", BookClassInstanceList);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnTrip", returnTrip);

                        if (stfType.equals("agency")) {
                            if (manageStatus.equals("book")) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsCreateBookerGuest.xhtml");
                            } else {
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", psgList);
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsReBookConfirmPage.xhtml");
                            }
                        } else {
                            if (manageStatus.equals("book")) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");
                            } else {
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", psgList);
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./reBookConfirmPage.xhtml");
                            }
                        }

                    }
                }
            }
//        }
        
    }
    
    

    public boolean ifStartSale(List<BookingClassInstance> instanceList) {
        boolean startSale = false;
        for (int i = 0; i < instanceList.size(); i++) {
            if (instanceList.get(i).getBookedSeatNo() > 0) {
                startSale = true;
                break;
            }

        }
        return startSale;

    }

    public void onSelectReturn() {
        System.out.println("geting into onSelectReturn!");
        System.out.println("return trip is " + returnTrip);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnTrip", returnTrip);

    }

    public void onOriginChange() {
//        secondFrequency = rf.getSecondFrequency(origin);
        otherPlaces = rf.getDestList(origin);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("otherPlaces", otherPlaces);

    }

    public void onDestChange() {
        departInstances = new ArrayList<>();
        returnInstances = new ArrayList<>();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

    }

    public void findFlightInstance() throws IOException {

        List<FlightInstance> result = new ArrayList<>();

        if (dest.equals("1") || origin.equals("1")) {
            if (dest.equals("1")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a valid destination ", ""));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select a valid origin ", ""));
            }

        } else {

            selectedCabin = rf.findCabinClass(cabinName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("resultFrequencies", resultFrequencies);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departInstances", departInstances);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnTrip", returnTrip);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateSpecific", dateSpecific);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departDate", departDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnDate", returnDate);
            System.out.println("in managed bean findFlightInstance(): cabin selected is " + selectedCabin.getCabinName());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);

//            if (dateSpecific) {
                if (manageStatus.equals("book")) {
                    bookedFlights = new ArrayList<>();
                }

                departSpecificList = rf.findResultInstanceList(origin, dest, departDate, selectedCabin, countPerson, manageStatus, bookedFlights);

                if (!departSpecificList.isEmpty()) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSpecificList", departSpecificList);

                    if (returnTrip) {
                        if (manageStatus.equals("book")) {
                            bookedFlights = new ArrayList<>();
                        }
                        returnSpecificList = rf.findResultInstanceList(dest, origin, returnDate, selectedCabin, countPerson, manageStatus, bookedFlights);

                        if (!returnSpecificList.isEmpty()) {
                            System.out.println("in findFlightInstance(): returnSpecificList size is " + returnSpecificList.size());
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSpecificList", returnSpecificList);
                            if (stfType.equals("agency")) {
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsBooking2.xhtml");
                            } else {
                                FacesContext.getCurrentInstance().getExternalContext().redirect("./testSearch2.xhtml");
                            }
                        } else {
                            System.out.println("in findFlightInstance(): returnSpecificList is empty");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No flight found for Returned date specified ", ""));
                        }
                    } else {
                        if (stfType.equals("agency")) {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsBooking2.xhtml");
                        } else {
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./testSearch2.xhtml");
                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No flight found for departure date specified ", ""));
                }
            } //        FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");


//        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", dest);
    }

    public void findBookClassInstance() throws IOException {

        selectedFlightInstance = sa.findFlightInstance(flightNo, dateString);
        bookClassInstanceList = sa.getBkiList(cabinName, selectedFlightInstance);
//        System.out.println("size of bookClassInstanceList is " + bookClassInstanceList.size());
//        for (int i = 0; i < cabinList.size(); i++) {
//            if (cabinList.get(i).getCabinClass().getCabinName().equals(cabinName)) {
//                selectedCabin = cabinList.get(i).getCabinClass();
//                break;
//            }
//        }
        System.out.println("selectedCabin is " + selectedCabin.getCabinName());

        System.out.println("in findBookClassInstance(): size of bookclassInstanceList is " + bookClassInstanceList.size());

        if (!this.ifStartSale(bookClassInstanceList)) {

            seatUnallocated = selectedCabin.getSeatCount();
            for (int i = 0; i < bookClassInstanceList.size(); i++) {
                if (bookClassInstanceList.get(i).getSeatNo() != null) {
                    seatUnallocated -= bookClassInstanceList.get(i).getSeatNo();
                }
            }

            frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatUnallocated", seatUnallocated);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", bookClassInstanceList);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlightInstance", selectedFlightInstance);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("frequency", frequency);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocate2.xhtml");

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sale of the tickets for this flight has already started ", ""));

        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manageStatus", manageStatus);
    }
    
    

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bInstance = (BookingClassInstance) event.getObject();
        System.out.println("seat no in bki now is " + bInstance.getSeatNo());

        System.out.println("currentAllocated is " + currentAllocated);

        seatUnallocated = seatUnallocated + bInstance.getSeatNo();

        if (currentAllocated <= seatUnallocated) {
            bInstance.setSeatNo(currentAllocated);
            sa.editDemandInfo(bInstance);

            if (bInstance.getSeatNo() != null) {
                System.out.println("seatAllocate1Bean1.onRowEdit(): seatNo=" + bInstance.getSeatNo());
                seatUnallocated -= bInstance.getSeatNo();
            }
            FacesMessage msg = new FacesMessage("BookingClass Instance" + bInstance.getBookingClass().getAnnotation() + " Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            currentAllocated = bInstance.getSeatNo();
            seatUnallocated -= bInstance.getSeatNo();;
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seat No entered for bookingclass " + bInstance.getBookingClass().getAnnotation() + " exceeds unallocated seatNo ", ""));

        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editing of BookingClass Instance" + ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public FlightFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(FlightFrequency frequency) {
        this.frequency = frequency;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public Map<String, FlightFrequency> getFlightMap() {
        return flightMap;
    }

    public void setFlightMap(Map<String, FlightFrequency> flightMap) {
        this.flightMap = flightMap;
    }

    public String getFlightString() {
        return flightString;
    }

    public void setFlightString(String flightString) {
        this.flightString = flightString;
    }

    public List<BookingClassInstance> getBookClassInstanceList() {
        return bookClassInstanceList;
    }

    public void setBookClassInstanceList(List<BookingClassInstance> BookClassInstanceList) {
        this.bookClassInstanceList = BookClassInstanceList;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
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

    public FlightInstance getSelectedFlightInstance() {
        return selectedFlightInstance;
    }

    public void setSelectedFlightInstance(FlightInstance selectedFlightInstance) {
        this.selectedFlightInstance = selectedFlightInstance;
    }

    public CabinClass getSelectedCabin() {
        return selectedCabin;
    }

    public void setSelectedCabin(CabinClass selectedCabin) {
        this.selectedCabin = selectedCabin;
    }

    public Integer getSeatUnallocated() {
        return seatUnallocated;
    }

    public void setSeatUnallocated(Integer seatUnallocated) {
        this.seatUnallocated = seatUnallocated;
    }

    public Integer getCurrentAllocated() {
        return currentAllocated;
    }

    public void setCurrentAllocated(Integer currentAllocated) {
        this.currentAllocated = currentAllocated;
    }

    public Boolean getReturnTrip() {
        return returnTrip;
    }

    public void setReturnTrip(Boolean returnTrip) {
        this.returnTrip = returnTrip;
    }

    public Date getDepartDate() {
        return departDate;
    }

    public void setDepartDate(Date departDate) {
        this.departDate = departDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getCountPerson() {
        return countPerson;
    }

    public void setCountPerson(Integer countPerson) {
        this.countPerson = countPerson;
    }

    public List<FlightFrequency> getInitialFrequency() {
        return initialFrequency;
    }

    public void setInitialFrequency(List<FlightFrequency> intialFrequency) {
        this.initialFrequency = intialFrequency;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public List<FlightFrequency> getResultFrequencies() {
        return resultFrequencies;
    }

    public void setResultFrequencies(List<FlightFrequency> resultFrequencies) {
        this.resultFrequencies = resultFrequencies;
    }

//    public Boolean getDateSpecific() {
//        return dateSpecific;
//    }
//
//    public void setDateSpecific(Boolean dateSpecific) {
//        this.dateSpecific = dateSpecific;
//    }

    public ArrayList<FlightInstance> getDepartInstances() {
        return departInstances;
    }

    public void setDepartInstances(ArrayList<FlightInstance> departInstances) {
        this.departInstances = departInstances;
    }

    public ArrayList<FlightInstance> getReturnInstances() {
        return returnInstances;
    }

    public void setReturnInstances(ArrayList<FlightInstance> returnInstances) {
        this.returnInstances = returnInstances;
    }

    public FlightInstance getToInstance() {
        return toInstance;
    }

    public void setToInstance(FlightInstance toInstance) {
        this.toInstance = toInstance;
    }

    public FlightInstance getBackInstance() {
        return backInstance;
    }

    public void setBackInstance(FlightInstance backInstance) {
        this.backInstance = backInstance;
    }



    public String getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(String selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public String getSelectedIndex2() {
        return selectedIndex2;
    }

    public void setSelectedIndex2(String selectedIndex2) {
        this.selectedIndex2 = selectedIndex2;
    }

   
    public ArrayList<ArrayList<FlightInstance>> getDepartSpecificList() {
        return departSpecificList;
    }

    public void setDepartSpecificList(ArrayList<ArrayList<FlightInstance>> departSpecificList) {
        this.departSpecificList = departSpecificList;
    }

    public ArrayList<ArrayList<FlightInstance>> getReturnSpecificList() {
        return returnSpecificList;
    }

    public void setReturnSpecificList(ArrayList<ArrayList<FlightInstance>> returnSpecificList) {
        this.returnSpecificList = returnSpecificList;
    }

    public List<Airport> getOtherPlaces() {
        return otherPlaces;
    }

    public void setOtherPlaces(List<Airport> otherPlaces) {
        this.otherPlaces = otherPlaces;
    }

    public Integer getDepartDefault() {
        return departDefault;
    }

    public void setDepartDefault(Integer departDefault) {
        this.departDefault = departDefault;
    }

    public Integer getReturnDefault() {
        return returnDefault;
    }

    public void setReturnDefault(Integer returnDefault) {
        this.returnDefault = returnDefault;
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

    public String getManageStatus() {
        return manageStatus;
    }

    public void setManageStatus(String manageStatus) {
        this.manageStatus = manageStatus;
    }

}
