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
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "dynamicReallocateBean")
@ViewScoped
public class DynamicReallocateBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fs;
    @EJB
    private SeatAssignBeanLocal sa;

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
    private Integer currentAllocated;

    private Integer bookedSeatCount;
    private Integer totalAvailable;

    private Date selectedDate = new Date();

    private List<FlightCabin> cabinList = new ArrayList<>();

    private ArrayList<BookingClassInstance> subList = new ArrayList<>();

    public DynamicReallocateBean() {

    }

    @PostConstruct
    public void init() {

        totalAvailable = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalAvailable");
        seatUnallocated = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seatUnallocated");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");
        selectedFlightInstance = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlightInstance");
//        cabinList = (List<FlightCabin>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");

    }

//    public boolean ifStartSale(List<BookingClassInstance> instanceList) {
//        boolean startSale = false;
//        for (int i = 0; i < instanceList.size(); i++) {
//            if (instanceList.get(i).getBookedSeatNo() > 0) {
//                startSale = true;
//                break;
//            }
//
//        }
//        return startSale;
//
//    }
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

        totalAvailable = selectedCabin.getSeatCount();

        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getBookedSeatNo() != null) {
                totalAvailable -= bookClassInstanceList.get(i).getBookedSeatNo();
            }
        }

        System.out.println("dynamicReallocateBean: findBookClassInstance(): seat available in this cabin now=" + totalAvailable);

        boolean inputFull = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getAvgDemand() == null || bookClassInstanceList.get(i).getStd() == null) {
                inputFull = false;
                break;
            }
        }

        if (inputFull) {
            frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatUnallocated", seatUnallocated);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("totalAvailable", totalAvailable);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", bookClassInstanceList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedCabin", selectedCabin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlightInstance", selectedFlightInstance);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("frequency", frequency);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./DynamicReallocateDemo2.xhtml");

    }

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bInstance = (BookingClassInstance) event.getObject();
        System.out.println("seat no in bki now is " + bInstance.getSeatNo());

        if (bookedSeatCount <= bInstance.getSeatNo()) {

//        totalAvailable = selectedCabin.getSeatCount();
            System.out.println("totalAvailable is " + totalAvailable);

            totalAvailable = totalAvailable + bInstance.getBookedSeatNo();

            totalAvailable = totalAvailable - bookedSeatCount;
            bInstance.setBookedSeatNo(bookedSeatCount);
            sa.changeBookdSeatCount(bInstance);
            Integer seatAvailable;

            for (int i = 0; i < bookClassInstanceList.size(); i++) {
                if (bookClassInstanceList.get(i).getId().equals(bInstance.getId())) {
                    if (bookClassInstanceList.get(i + 1).getAvgDemand() != null && bookClassInstanceList.get(i).getBookedSeatNo() > bookClassInstanceList.get(i + 1).getAvgDemand()) {

                        seatAvailable = bookClassInstanceList.get(i + 1).getSeatNo() - bookClassInstanceList.get(i + 1).getBookedSeatNo();
                        bookClassInstanceList.get(i + 1).setSeatNo(bookClassInstanceList.get(i + 1).getBookedSeatNo());
                        bookClassInstanceList.get(i).setSeatNo(bookClassInstanceList.get(i).getSeatNo() + seatAvailable);
                        System.out.println("seat no for bookingclass "+bookClassInstanceList.get(i).getBookingClass().getAnnotation()+"is: "+bookClassInstanceList.get(i).getSeatNo());
                        System.out.println("seat no for bookingclass "+bookClassInstanceList.get(i+1).getBookingClass().getAnnotation()+"is: "+bookClassInstanceList.get(i+1).getSeatNo());
                        sa.changeSeatNo(bookClassInstanceList.get(i));
                        sa.changeSeatNo(bookClassInstanceList.get(i + 1));
                    }

                }

            }

            FacesMessage msg = new FacesMessage("Booked Seat No for BookingClass Instance" + bInstance.getBookingClass().getAnnotation() + " Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Booked Seat No entered for bookingclass " + bInstance.getBookingClass().getAnnotation() + " exceeds total seat count for this bookingclass ", ""));
        }

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editing of BookingClass Instance" + ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
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

    public Integer getCurrentAllocated() {
        return currentAllocated;
    }

    public void setCurrentAllocated(Integer currentAllocated) {
        this.currentAllocated = currentAllocated;
    }

    public Integer getTotalAvailable() {
        return totalAvailable;
    }

    public void setTotalAvailable(Integer totalAvailable) {
        this.totalAvailable = totalAvailable;
    }

    public Integer getBookedSeatCount() {
        return bookedSeatCount;
    }

    public void setBookedSeatCount(Integer bookedSeatCount) {
        this.bookedSeatCount = bookedSeatCount;
    }

}
