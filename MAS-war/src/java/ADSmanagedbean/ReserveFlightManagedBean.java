/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import SessionBean.ADS.ReserveFlightBeanLocal;
import Entity.APS.Airport;
import Entity.APS.CabinClass;
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
@Named(value = "reserveFlightManagedBean")
@ViewScoped
public class ReserveFlightManagedBean implements Serializable {

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

    private String selectedIndex;
    private String selectedIndex2;
    private Map<String, ArrayList<Integer>> dayToSelectIndex = new HashMap();
    private Map<String, ArrayList<Integer>> dayToSelectIndex2 = new HashMap();
    private Map<String, Boolean> departDayToCheck = new HashMap<>();
    private Map<String, Boolean> returnDayToCheck = new HashMap<>();
    private Map<String, String> departDayOfWeek = new HashMap<>();
    private Map<String, String> returnDayOfWeek = new HashMap<>();

    private Map<String, ArrayList<ArrayList<FlightInstance>>> departMap = new HashMap<>();
    private Map<String, ArrayList<ArrayList<FlightInstance>>> returnMap = new HashMap<>();

    private Boolean returnTrip = false;
    private Boolean dateSpecific;

    private Integer countPerson;
    private Integer availableSeat;

    private String origin;
    private String dest;
    private List<Route> routeList;
    private List<FlightFrequency> initialFrequency;
    private List<FlightFrequency> secondFrequency;
    private ArrayList<FlightInstance> departInstances;
    private ArrayList<FlightInstance> returnInstances;
    private List<FlightFrequency> resultFrequencies;

    private ArrayList<ArrayList<FlightInstance>> departsByDay = new ArrayList<>();
    private ArrayList<ArrayList<FlightInstance>> returnsByDay = new ArrayList<>();

    private ArrayList<String> dateOfWeek = new ArrayList<>();
    private ArrayList<String> dateOfWeek2 = new ArrayList<>();

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

    private Date currentDate = new Date();
    private Date departDate = new Date();
    private Date returnDate = new Date();

    private List<CabinClass> cabinList = new ArrayList<>();

    public ReserveFlightManagedBean() {
    }

    @PostConstruct
    public void init() {

        initialFrequency = rf.getAllFlightFrequency();

        returnDayToCheck = (Map<String, Boolean>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnDayToCheck");
        returnDayOfWeek = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnDayOfWeek");

        departDayToCheck = (Map<String, Boolean>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departDayToCheck");
        departDayOfWeek = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departDayOfWeek");

        dateOfWeek2 = (ArrayList<String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateOfWeek2");
        returnMap = (Map<String, ArrayList<ArrayList<FlightInstance>>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnMap");

        departMap = (Map<String, ArrayList<ArrayList<FlightInstance>>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departMap");
        dateOfWeek = (ArrayList<String>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateOfWeek");

        returnTrip = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnTrip");
        dateSpecific = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateSpecific");
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

    }

    public void onDepartOptionChange(int index, String day) {
        System.out.println("Getting into onDepartOptionChange");
        System.out.println("dateSpecific is " + dateSpecific);
        Boolean duplicate = false;
        Integer i = (Integer) index;

        if (!dateSpecific) {
            if (i != null) {

                ArrayList<Integer> departSelectIndex = new ArrayList<>();
                Boolean dayFound = false;
                if (!dayToSelectIndex.isEmpty()) {
                    for (Map.Entry<String, ArrayList<Integer>> entry : dayToSelectIndex.entrySet()) {
                        System.out.println(entry.getKey() + "/" + entry.getValue());
                        if (entry.getKey().equals(day)) {
                            departSelectIndex = entry.getValue();
                            if (!departSelectIndex.isEmpty()) {
                                if (departSelectIndex.contains((Integer) i)) {
                                    departSelectIndex.remove((Integer) i);
                                } else {
                                    departSelectIndex.add((Integer) i);
                                }
                            } else {
                                departSelectIndex.add((Integer) i);
                            }
                            dayFound = true;
                            break;
                        }
                    }
                    if (!dayFound) {
                        departSelectIndex = new ArrayList<>();
                        departSelectIndex.add((Integer) i);
                        dayToSelectIndex.put(day, departSelectIndex);
                    }
                } else {
                    departSelectIndex = new ArrayList<>();
                    departSelectIndex.add((Integer) i);
                    dayToSelectIndex.put(day, departSelectIndex);
                }
            }
            System.out.println("size of daytoSelectIndex map is " + dayToSelectIndex.size());
            for (Map.Entry<String, ArrayList<Integer>> entry : dayToSelectIndex.entrySet()) {
                ArrayList<Integer> indexList = entry.getValue();
                System.out.println("for day " + entry.getKey());
                for (int f = 0; f < indexList.size(); f++) {
                    System.out.println("index chosen is " + indexList.get(f));

                }
            }
        } else {

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
    }

    public void onReturnOptionChange(int index, String day) {
        System.out.println("Getting into onDepartOptionChange");
        Boolean duplicate = false;
        Integer i = (Integer) index;

        if (!dateSpecific) {
            if (i != null) {

                ArrayList<Integer> returnSelectIndex = new ArrayList<>();
                Boolean dayFound = false;
                if (!dayToSelectIndex2.isEmpty()) {
                    for (Map.Entry<String, ArrayList<Integer>> entry : dayToSelectIndex2.entrySet()) {
                        System.out.println(entry.getKey() + "/" + entry.getValue());
                        if (entry.getKey().equals(day)) {
                            returnSelectIndex = entry.getValue();
                            if (!returnSelectIndex.isEmpty()) {
                                if (returnSelectIndex.contains((Integer) i)) {
                                    returnSelectIndex.remove((Integer) i);
                                } else {
                                    returnSelectIndex.add((Integer) i);
                                }
                            } else {
                                returnSelectIndex.add((Integer) i);
                            }
                            dayFound = true;
                            break;
                        }
                    }
                    if (!dayFound) {
                        returnSelectIndex = new ArrayList<>();
                        returnSelectIndex.add((Integer) i);
                        dayToSelectIndex2.put(day, returnSelectIndex);
                    }
                } else {
                    returnSelectIndex = new ArrayList<>();
                    returnSelectIndex.add((Integer) i);
                    dayToSelectIndex2.put(day, returnSelectIndex);
                }
            }
            System.out.println("size of daytoSelectIndex2 map is " + dayToSelectIndex2.size());
            for (Map.Entry<String, ArrayList<Integer>> entry : dayToSelectIndex2.entrySet()) {
                ArrayList<Integer> indexList = entry.getValue();
                System.out.println("for return day " + entry.getKey());
                for (int f = 0; f < indexList.size(); f++) {
                    System.out.println("return index chosen is " + indexList.get(f));

                }
            }
        } else {
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
    }

    public void onSelectOption() throws IOException {
        int checkCount = 0;
        String selectedDay = "";

        if (dateSpecific) {
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

                    totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);
                    totalPrice *= countPerson;
                    System.out.println("Total price is " + totalPrice);

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);

                    FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");

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

                        totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);
                        totalPrice += rf.getLowestPrice(returnSelected, selectedCabin, countPerson);

                        totalPrice *= countPerson;
                        System.out.println("Total price is " + totalPrice);

                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);

                        FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");

                    }
                }
            }
        } else {
            for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                System.out.println(entry.getKey() + "/" + entry.getValue());
                if (entry.getValue() == true) {
                    selectedDay = entry.getKey();
                    checkCount++;
                }
            }

            if (checkCount == 0) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one day for departure trip ", ""));
            } else if (checkCount > 1) {
                for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                    System.out.println(entry.getKey() + "/" + entry.getValue());
                    if (entry.getValue() == true) {
                        selectedDay = entry.getKey();
                        dayToSelectIndex.remove(selectedDay);
                    }
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You cannot select more than one day for departure trip ", ""));
            } else {

                ArrayList<Integer> selectedByIndex = new ArrayList<>();
                selectedByIndex = dayToSelectIndex.get(selectedDay);

                if (selectedByIndex != null && !selectedByIndex.isEmpty()) {
                    if (selectedByIndex.size() > 1) {
                        for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                            System.out.println(entry.getKey() + "/" + entry.getValue());
                            if (entry.getValue() == true) {
                                selectedDay = entry.getKey();
                                dayToSelectIndex.remove(selectedDay);
                            }
                        }
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have select more than one option for departure trip on " + selectedDay, ""));
                    } else {
                        if (!returnTrip) {
                            ArrayList<FlightInstance> departSelected = departMap.get(selectedDay).get(selectedByIndex.get(0));
                            System.out.println("selection for departure trip is correct");
                            System.out.println("departure package chosen is " + departSelected);
                            ArrayList<FlightInstance> returnSelected = new ArrayList<>();

                            totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);

                            totalPrice *= countPerson;
                            System.out.println("Total price is " + totalPrice);

                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);
                            
                             FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");

                        }
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one option ", ""));
                }
                if (returnTrip) {
                    int count2;
                    count2 = checkReturnDaySelect();
                    if (count2 == 0) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one day for return trip ", ""));
                    } else if (count2 > 1) {
                        for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                            System.out.println(entry.getKey() + "/" + entry.getValue());
                            if (entry.getValue() == true) {
                                selectedDay = entry.getKey();
                                dayToSelectIndex.remove(selectedDay);
                            }
                        }

                        for (Map.Entry<String, Boolean> entry : returnDayToCheck.entrySet()) {
                            System.out.println(entry.getKey() + "/" + entry.getValue());
                            if (entry.getValue() == true) {
                                selectedDay = entry.getKey();
                                dayToSelectIndex2.remove(selectedDay);
                            }
                        }

                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You cannot select more than one day for return trip ", ""));
                    } else {

                        ArrayList<Integer> selectedByIndex2 = new ArrayList<>();
                        String selectedDay2 = this.getSelectedReturnDay();
                        selectedByIndex2 = dayToSelectIndex2.get(selectedDay2);

                        if (selectedByIndex2 != null && !selectedByIndex2.isEmpty()) {
                            if (selectedByIndex2.size() > 1) {
                                for (Map.Entry<String, Boolean> entry2 : returnDayToCheck.entrySet()) {
                                    System.out.println(entry2.getKey() + "/" + entry2.getValue());
                                    if (entry2.getValue() == true) {
                                        selectedDay2 = entry2.getKey();
                                        dayToSelectIndex2.remove(selectedDay2);
                                    }

                                    for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                                        System.out.println(entry.getKey() + "/" + entry.getValue());
                                        if (entry.getValue() == true) {
                                            selectedDay = entry.getKey();
                                            dayToSelectIndex.remove(selectedDay);
                                        }
                                    }
                                }
                                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "You have select more than one option for return trip on " + selectedDay, ""));
                            } else {
                                System.out.println("selectedDay is " + selectedDay);
                                ArrayList<FlightInstance> departSelected = departMap.get(selectedDay).get(selectedByIndex.get(0));
                                System.out.println("selection for departure trip is correct");
                                System.out.println("departure package chosen is " + departSelected);

                                ArrayList<FlightInstance> returnSelected = returnMap.get(selectedDay2).get(selectedByIndex2.get(0));
                                System.out.println("selection for return trip is correct");
                                System.out.println("return package chosen is " + returnSelected);

                                totalPrice += rf.getLowestPrice(departSelected, selectedCabin, countPerson);
                                totalPrice += rf.getLowestPrice(returnSelected, selectedCabin, countPerson);

                                totalPrice *= countPerson;
                                System.out.println("Total price is " + totalPrice);

                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSelected", departSelected);
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSelected", returnSelected);
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);
                                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalPrice", totalPrice);
                                
                                 FacesContext.getCurrentInstance().getExternalContext().redirect("./createMemberGuest.xhtml");
                            }
                        } else {
                            for (Map.Entry<String, Boolean> entry : departDayToCheck.entrySet()) {
                                System.out.println(entry.getKey() + "/" + entry.getValue());
                                if (entry.getValue() == true) {
                                    selectedDay = entry.getKey();
                                    dayToSelectIndex.remove(selectedDay);
                                }
                            }
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select one option for return trip ", ""));
                        }
                    }
                }
            }
        }
    }

    public int checkReturnDaySelect() {
        int checkCount2 = 0;
        String selectedDay2 = "";
        for (Map.Entry<String, Boolean> entry : returnDayToCheck.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            if (entry.getValue() == true) {
                selectedDay2 = entry.getKey();
                checkCount2++;
            }
        }

        return checkCount2;

    }

    public String getSelectedReturnDay() {
        String selectedDay2 = "";
        for (Map.Entry<String, Boolean> entry : returnDayToCheck.entrySet()) {
            System.out.println(entry.getKey() + "/" + entry.getValue());
            if (entry.getValue() == true) {
                selectedDay2 = entry.getKey();
                break;
            }
        }
        return selectedDay2;
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
        secondFrequency = rf.getSecondFrequency(origin);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("secondFrequency", secondFrequency);

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
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateSpecific", dateSpecific);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departDate", departDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnDate", returnDate);
            System.out.println("in managed bean findFlightInstance(): cabin selected is " + selectedCabin.getCabinName());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", countPerson);

            if (dateSpecific) {

                departSpecificList = rf.findResultInstanceList(origin, dest, departDate, selectedCabin, countPerson);

                if (!departSpecificList.isEmpty()) {
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departSpecificList", departSpecificList);

                    if (returnTrip) {
                        returnSpecificList = rf.findResultInstanceList(dest, origin, returnDate, selectedCabin, countPerson);

                        if (!returnSpecificList.isEmpty()) {
                            System.out.println("in findFlightInstance(): returnSpecificList size is " + returnSpecificList.size());
                            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnSpecificList", returnSpecificList);
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");
                        } else {
                            System.out.println("in findFlightInstance(): returnSpecificList is empty");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No flight found for Returned date specified ", ""));
                        }
                    } else {
                        FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");
                    }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No flight found for departure date specified ", ""));
                }
            } //        FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");
            else {
                Calendar c = Calendar.getInstance();
                c.setTime(departDate);

                // Set the calendar to monday of the current week
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

// Print dates of the current week starting on Monday
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                dateOfWeek = new ArrayList<String>();
                departMap = new HashMap<String, ArrayList<ArrayList<FlightInstance>>>();
                departsByDay = new ArrayList<>();
                departDayToCheck = new HashMap<>();
                departDayOfWeek = new HashMap<>();

                for (int i = 0; i < 7; i++) {
                    departsByDay = new ArrayList<>();

                    departsByDay = rf.findResultInstanceList(origin, dest, c.getTime(), selectedCabin, countPerson);

                    df = new SimpleDateFormat("dd MMM yyyy");
                    String oneDate = df.format(c.getTime());

                    System.out.println("one date is " + oneDate);
                    dateOfWeek.add(oneDate);
                    String dayOfWeek = new SimpleDateFormat("EE").format(c.getTime());
                    departDayToCheck.put(oneDate, false);
                    departDayOfWeek.put(oneDate, dayOfWeek);

                    if (departsByDay != null && !departsByDay.isEmpty()) {
                        departMap.put(oneDate, departsByDay);
                    }
                    c.add(Calendar.DATE, 1);

                }

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departMap", departMap);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateOfWeek", dateOfWeek);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departsByDay", departsByDay);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departDayToCheck", departDayToCheck);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("departDayOfWeek", departDayOfWeek);

                if (!returnTrip) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");
                } else {
                    c = Calendar.getInstance();
                    c.setTime(returnDate);

                    // Set the calendar to monday of the current week
                    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

// Print dates of the current week starting on Monday
                    df = new SimpleDateFormat("yyyy-MM-dd");
                    dateOfWeek2 = new ArrayList<String>();
                    returnMap = new HashMap<String, ArrayList<ArrayList<FlightInstance>>>();
                    returnsByDay = new ArrayList<>();
                    returnDayToCheck = new HashMap<>();
                    returnDayOfWeek = new HashMap<>();

                    for (int i = 0; i < 7; i++) {
                        returnsByDay = new ArrayList<>();
                        returnsByDay = rf.findResultInstanceList(dest, origin, c.getTime(), selectedCabin, countPerson);

                        df = new SimpleDateFormat("dd MMM yyyy");
                        String oneDate = df.format(c.getTime());

                        System.out.println("one date is " + oneDate);
                        dateOfWeek2.add(oneDate);
                        String dayOfWeek = new SimpleDateFormat("EE").format(c.getTime());
                        returnDayToCheck.put(oneDate, false);
                        returnDayOfWeek.put(oneDate, dayOfWeek);

                        if (returnsByDay != null && !returnsByDay.isEmpty()) {
                            returnMap.put(oneDate, returnsByDay);
                        }
                        c.add(Calendar.DATE, 1);
                    }

                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnsByDay", returnsByDay);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateOfWeek2", dateOfWeek2);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnMap", returnMap);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnDayToCheck", returnDayToCheck);
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnDayOfWeek", returnDayOfWeek);

                    FacesContext.getCurrentInstance().getExternalContext().redirect("./ReserveFlight2.xhtml");
                }

            }

        }
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

    public List<FlightFrequency> getSecondFrequency() {
        return secondFrequency;
    }

    public void setSecondFrequency(List<FlightFrequency> secondFrequency) {
        this.secondFrequency = secondFrequency;
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

    public Boolean getDateSpecific() {
        return dateSpecific;
    }

    public void setDateSpecific(Boolean dateSpecific) {
        this.dateSpecific = dateSpecific;
    }

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

    public ArrayList<String> getDateOfWeek() {
        return dateOfWeek;
    }

    public void setDateOfWeek(ArrayList<String> dateOfWeek) {
        this.dateOfWeek = dateOfWeek;
    }

    public Map<String, ArrayList<ArrayList<FlightInstance>>> getDepartMap() {
        return departMap;
    }

    public void setDepartMap(Map<String, ArrayList<ArrayList<FlightInstance>>> departMap) {
        this.departMap = departMap;
    }

    public ArrayList<ArrayList<FlightInstance>> getDepartsByDay() {
        return departsByDay;
    }

    public void setDepartsByDay(ArrayList<ArrayList<FlightInstance>> departsByDay) {
        this.departsByDay = departsByDay;
    }

    public ArrayList<ArrayList<FlightInstance>> getReturnsByDay() {
        return returnsByDay;
    }

    public void setReturnsByDay(ArrayList<ArrayList<FlightInstance>> returnsByDay) {
        this.returnsByDay = returnsByDay;
    }

    public Map<String, ArrayList<ArrayList<FlightInstance>>> getReturnMap() {
        return returnMap;
    }

    public void setReturnMap(Map<String, ArrayList<ArrayList<FlightInstance>>> returnMap) {
        this.returnMap = returnMap;
    }

    public ArrayList<String> getDateOfWeek2() {
        return dateOfWeek2;
    }

    public void setDateOfWeek2(ArrayList<String> dateOfWeek2) {
        this.dateOfWeek2 = dateOfWeek2;
    }

    public Map<String, Boolean> getDepartDayToCheck() {
        return departDayToCheck;
    }

    public void setDepartDayToCheck(Map<String, Boolean> departDayToCheck) {
        this.departDayToCheck = departDayToCheck;
    }

    public Map<String, Boolean> getReturnDayToCheck() {
        return returnDayToCheck;
    }

    public void setReturnDayToCheck(Map<String, Boolean> returnDayToCheck) {
        this.returnDayToCheck = returnDayToCheck;
    }

    public Map<String, String> getDepartDayOfWeek() {
        return departDayOfWeek;
    }

    public void setDepartDayOfWeek(Map<String, String> departDayOfWeek) {
        this.departDayOfWeek = departDayOfWeek;
    }

    public Map<String, String> getReturnDayOfWeek() {
        return returnDayOfWeek;
    }

    public void setReturnDayOfWeek(Map<String, String> returnDayOfWeek) {
        this.returnDayOfWeek = returnDayOfWeek;
    }

    public Map<String, ArrayList<Integer>> getDayToSelectIndex() {
        return dayToSelectIndex;
    }

    public void setDayToSelectIndex(Map<String, ArrayList<Integer>> dayToSelectIndex) {
        this.dayToSelectIndex = dayToSelectIndex;
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

    public Map<String, ArrayList<Integer>> getDayToSelectIndex2() {
        return dayToSelectIndex2;
    }

    public void setDayToSelectIndex2(Map<String, ArrayList<Integer>> dayToSelectIndex2) {
        this.dayToSelectIndex2 = dayToSelectIndex2;
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

}
