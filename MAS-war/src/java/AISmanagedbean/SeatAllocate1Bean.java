/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.aisEntity.BookingClassInstance;
import Entity.aisEntity.FlightCabin;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.AirlineInventory.SeatAssignBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "seatAllocate1Bean")
@ViewScoped
public class SeatAllocate1Bean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fs;
    @EJB
    private SeatAssignBeanLocal sa;

    private double optimalRev = 0;

    List<FlightFrequency> allFrequency = new ArrayList<>();
    List<FlightFrequency> frequencyList = new ArrayList<>();

    private Map<String, FlightFrequency> flightMap;
    private String flightNo;
    private CabinClass selectedCabin;
    private String cabinName;
    ;
    private String flightString;
    private FlightFrequency frequency;
    private String dateString;
    private List<BookingClassInstance> bookClassInstanceList = new ArrayList<>();

    private FlightInstance selectedFlightInstance;

    private Date selectedDate = new Date();

    private ArrayList<ArrayList<BookingClassInstance>> listByCabin = new ArrayList<ArrayList<BookingClassInstance>>();

    private List<FlightCabin> cabinList = new ArrayList<>();

    private ArrayList<BookingClassInstance> subList = new ArrayList<>();

    public SeatAllocate1Bean() {
        this.listByCabin = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
//        flightMap = new HashMap<String, FlightFrequency>();
//        optimalRev = (double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("optimalRev");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");
        listByCabin = (ArrayList<ArrayList<BookingClassInstance>>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listByCabin");
        subList = (ArrayList<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("listInstance");
        selectedFlightInstance = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlightInstance");
//        cabinList = (List<FlightCabin>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");

    }

    public void onFlightChange() {
        if (!flightNo.equals("1")) {
            cabinList = sa.getCabinList(flightNo);
        } else {
            cabinList = new ArrayList<>();
//            System.out.println("flight not selected");
        }

//        System.out.println("cabinlist size is: " + cabinList.size());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

    }

    public void onDateChange() {
        dateString = new String();
        frequencyList = new ArrayList<>();
        System.out.println("Getting in to onDateChange()");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        dateString = formatter.format(selectedDate);
        System.out.println(dateString);

        if (dateString != null && !dateString.equals("")) {
            frequencyList = sa.getFlightList(dateString);
            System.out.println("size of frequencyList is " + frequencyList.size());
            if (frequencyList.size() > 0) {
                for (int i = 0; i < frequencyList.size(); i++) {
                    System.out.println(frequencyList.get(i).getFlightNo() + " " + frequencyList.get(i).getRoute().toString());
                }
            }
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", dateString);

        }
    }

    public void findBookClassInstance() throws IOException {

        selectedFlightInstance = sa.findFlightInstance(flightNo, dateString);
        bookClassInstanceList = sa.getBkiList(cabinName, selectedFlightInstance);
//        System.out.println("size of bookClassInstanceList is " + bookClassInstanceList.size());
        for (int i = 0; i < cabinList.size(); i++) {
            if (cabinList.get(i).getCabinClass().getCabinName().equals(cabinName)) {
                selectedCabin = cabinList.get(i).getCabinClass();
                break;
            }
        }
        System.out.println("selectedCabin is " + selectedCabin.getCabinName());

        frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);

//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listByCabin", listByCabin);
        optimalRev = 0;
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", bookClassInstanceList);
        
           FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlightInstance", selectedFlightInstance);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("frequency", frequency);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocate2.xhtml");
    }

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bInstance = (BookingClassInstance) event.getObject();
        sa.editDemandInfo(bInstance);
        FacesMessage msg = new FacesMessage("BookingClass Instance" + bInstance.getBookingClass().getAnnotation() + " Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editing of BookingClass Instance" + ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void computeOptimalSeat() throws IOException {
        selectedCabin=(CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");
        frequency = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("frequency");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
         System.out.println("In seatAllcate1Bean():computeOptimalSeat(): selectedFlightInstance is " + selectedFlightInstance.toString());
        System.out.println("In seatAllcate1Bean():computeOptimalSeat(): flight no is " + flightNo + " and date is " + dateString);
        System.out.println("n seatAllcate1Bean():computeOptimalSeat(): cabin name is "+selectedCabin.getCabinName());
        bookClassInstanceList = sa.getBkiList(selectedCabin.getCabinName(), selectedFlightInstance);
        System.out.println("size of booking class instance list is " + bookClassInstanceList.size());

        bookClassInstanceList = (List<BookingClassInstance>) sa.computeOptimalSeat(bookClassInstanceList);

        optimalRev = sa.computeOptimalRev(bookClassInstanceList);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listInstance", listInstance);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookClassInstanceList", bookClassInstanceList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);

//        FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocate3.xhtml");
    }

    public List<FlightFrequency> getFrequencyList() {
        return frequencyList;
    }

    public void setFrequencyList(List<FlightFrequency> frequencyList) {
        this.frequencyList = frequencyList;
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

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public List<FlightFrequency> getAllFrequency() {
        return allFrequency;
    }

    public void setAllFrequency(List<FlightFrequency> allFrequency) {
        this.allFrequency = allFrequency;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public ArrayList<ArrayList<BookingClassInstance>> getListByCabin() {
        return listByCabin;
    }

    public void setListByCabin(ArrayList<ArrayList<BookingClassInstance>> listByCabin) {
        this.listByCabin = listByCabin;
    }

    public ArrayList<BookingClassInstance> getSubList() {
        return subList;
    }

    public void setSubList(ArrayList<BookingClassInstance> subList) {
        this.subList = subList;
    }

    public double getOptimalRev() {

        return optimalRev;
    }

    public void setOptimalRev(double optimalRev) {
        this.optimalRev = optimalRev;
    }

    public List<FlightCabin> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<FlightCabin> cabinList) {
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

}
