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

    private Double optimalRev = new Double(0);
    private Double currentRev = new Double(0);

    List<FlightFrequency> allFrequency = new ArrayList<>();
    List<FlightFrequency> frequencyList = new ArrayList<>();

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

    private Date selectedDate = new Date();

    private ArrayList<ArrayList<BookingClassInstance>> listByCabin = new ArrayList<ArrayList<BookingClassInstance>>();

    private List<FlightCabin> cabinList = new ArrayList<>();

    private ArrayList<BookingClassInstance> subList = new ArrayList<>();

    public SeatAllocate1Bean() {
        this.listByCabin = new ArrayList<>();
    }

    @PostConstruct
    public void init() {

        optimalRev = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("optimalRev");

        seatUnallocated = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seatUnallocated");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");
        selectedFlightInstance = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlightInstance");
//        cabinList = (List<FlightCabin>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");

    }

    public Integer newUnallocated(BookingClassInstance bki) {
        System.out.println("getting into newUnallocated");
        Integer unallocated;
        if (bki.getSeatNo() != null) {
            unallocated = bki.getFlightCabin().getCabinClass().getSeatCount();
            
            System.out.println("size of bookingclasslist is "+bookClassInstanceList.size());
            for (int i = 0; i < bookClassInstanceList.size(); i++) {
                
                if (!bookClassInstanceList.get(i).equals(bki) && bookClassInstanceList.get(i).getSeatNo() != null) {
                    unallocated -= bookClassInstanceList.get(i).getSeatNo();
                }
            }
            
//            unallocated -= bki.getSeatNo();

        } else {
            
            unallocated = seatUnallocated;

        }
        System.out.println("Unallocated="+unallocated);
        return unallocated;
    }

    public void onFlightChange() {
        if (!flightNo.equals("1")) {
            cabinList = sa.getCabinList(flightNo);
        } else {
            cabinList = new ArrayList<>();

        }

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

        seatUnallocated = selectedCabin.getSeatCount();
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getSeatNo() != null) {
                seatUnallocated -= bookClassInstanceList.get(i).getSeatNo();
            }
        }

        boolean inputFull = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getAvgDemand() == null || bookClassInstanceList.get(i).getStd() == null) {
                inputFull = false;
                break;
            }
        }

        if (inputFull) {
            System.out.println("seatAllocate1Bean:findBookClassInstance(): required input is fulfilled");
            optimalRev = sa.computeOptimalRev(bookClassInstanceList);
        } else {
            System.out.println("seatAllocate1Bean:findBookClassInstance(): some require input is null");
            optimalRev = 0.0;
        }

        if (inputFull) {
            frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);

//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listByCabin", listByCabin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatUnallocated", seatUnallocated);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", bookClassInstanceList);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlightInstance", selectedFlightInstance);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("frequency", frequency);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocate2.xhtml");
    }

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bInstance = (BookingClassInstance) event.getObject();

        if (bInstance.getSeatNo() <= seatUnallocated) {
            sa.editDemandInfo(bInstance);

            if (bInstance.getSeatNo() != null) {
                System.out.println("seatAllocate1Bean1.onRowEdit(): seatNo=" + bInstance.getSeatNo());
                seatUnallocated -= bInstance.getSeatNo();
            }
            FacesMessage msg = new FacesMessage("BookingClass Instance" + bInstance.getBookingClass().getAnnotation() + " Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seat No entered for bookingclass " + bInstance.getBookingClass().getAnnotation() + " exceeds unallocated seatNo ", ""));

        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editing of BookingClass Instance" + ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void computeOptimalSeat() throws IOException {
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");
        frequency = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("frequency");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");

        bookClassInstanceList = sa.getBkiList(selectedCabin.getCabinName(), selectedFlightInstance);
        System.out.println("size of booking class instance list is " + bookClassInstanceList.size());

        boolean noNull = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getAvgDemand() != null && bookClassInstanceList.get(i).getStd() != null) {
                System.out.println("demand is " + bookClassInstanceList.get(i).getAvgDemand());
                System.out.println("std is " + bookClassInstanceList.get(i).getStd());
            } else {
                noNull = false;
                break;
            }

        }

        if (noNull) {

            bookClassInstanceList = (List<BookingClassInstance>) sa.computeOptimalSeat(bookClassInstanceList);

            optimalRev = sa.computeOptimalRev(bookClassInstanceList);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listInstance", listInstance);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookClassInstanceList", bookClassInstanceList);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in all required inputs(avg demand and std) for all bookingclass ", ""));
        }
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

    public Double getOptimalRev() {

        return optimalRev;
    }

    public void setOptimalRev(Double optimalRev) {
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

    public Integer getSeatUnallocated() {
        return seatUnallocated;
    }

    public void setSeatUnallocated(Integer seatUnallocated) {
        this.seatUnallocated = seatUnallocated;
    }

    public Double getCurrentRev() {
        return currentRev;
    }

    public void setCurrentRev(Double currentRev) {
        this.currentRev = currentRev;
    }

}