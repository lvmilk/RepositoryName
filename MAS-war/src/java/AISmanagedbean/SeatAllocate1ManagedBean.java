
package AISmanagedbean;

import Entity.AIS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.AIS.BookingClassInstance;
import Entity.AIS.FlightCabin;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.AIS.SeatAssignBeanLocal;
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
@Named(value = "seatAllocate1Bean")
@ViewScoped
public class SeatAllocate1ManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fs;
    @EJB
    private SeatAssignBeanLocal sa;

    private Double optimalRev;
    private Double currentRev;

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

    private Date selectedDate = new Date();

    private List<FlightCabin> cabinList = new ArrayList<>();

    private ArrayList<BookingClassInstance> subList = new ArrayList<>();

    public SeatAllocate1ManagedBean() {

    }

    @PostConstruct
    public void init() {

        optimalRev = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("optimalRev");
        currentRev = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentRev");

        seatUnallocated = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seatUnallocated");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");
        selectedFlightInstance = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFlightInstance");
//        cabinList = (List<FlightCabin>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");

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

    public void onFlightChange() {
        if (!flightNo.equals("1")) {
            dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
            System.out.println("onFlightChange: dateString is " + dateString + " and flightNo is " + flightNo);
            cabinList = sa.getCabinList(flightNo, dateString);
        } else {
            cabinList = new ArrayList<>();

        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

    }

    public void onDateChange() {
        System.out.println("geting into onDateChange()");
        System.out.println("SELECTED DATE IS " + selectedDate);
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

        System.out.println("in findBookClassInstance(): size of bookclassInstanceList is " + bookClassInstanceList.size());

        if (!this.ifStartSale(bookClassInstanceList)) {

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
                currentRev = sa.computeCurrentRev(bookClassInstanceList);
            } else {
                System.out.println("seatAllocate1Bean:findBookClassInstance(): some require input is null");
                optimalRev = 0.0;
                currentRev = 0.0;
            }

            if (inputFull) {
                frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();
            }

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentRev", currentRev);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);
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
       if(currentAllocated!=null) {
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

    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Editing of BookingClass Instance" + ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void computeExpRev() {
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");
        bookClassInstanceList = sa.getBkiList(selectedCabin.getCabinName(), selectedFlightInstance);
        System.out.println("size of booking class instance list is " + bookClassInstanceList.size());

        boolean noNull = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getAvgDemand() != null && bookClassInstanceList.get(i).getStd() != null && bookClassInstanceList.get(i).getSeatNo() != null) {
                System.out.println("demand is " + bookClassInstanceList.get(i).getAvgDemand());
                System.out.println("std is " + bookClassInstanceList.get(i).getStd());
            } else {
                noNull = false;
                break;
            }
        }

        if (noNull) {
            currentRev = sa.computeCurrentRev(bookClassInstanceList);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listInstance", listInstance);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookClassInstanceList", bookClassInstanceList);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);
        } else {
            System.out.println("required field not fulfilled");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please fill in all required inputs(avg demand and std) for all bookingclass ", ""));
        }

    }

    public void computeOptimalSeat() throws IOException {
        selectedCabin = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedCabin");
        frequency = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("frequency");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");

        bookClassInstanceList = sa.getBkiList(selectedCabin.getCabinName(), selectedFlightInstance);
//        System.out.println("size of booking class instance list is " + bookClassInstanceList.size());

        boolean noNull = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getAvgDemand() != null && bookClassInstanceList.get(i).getStd() != null) {
//                System.out.println("demand is " + bookClassInstanceList.get(i).getAvgDemand());
//                System.out.println("std is " + bookClassInstanceList.get(i).getStd());
            } else {
                noNull = false;
                break;
            }

        }

        Boolean priceExist = true;
        for (int i = 0; i < bookClassInstanceList.size(); i++) {
            if (bookClassInstanceList.get(i).getPrice() > 0) {

            } else {
                priceExist = false;
                break;
            }

        }

        if (noNull) {

            if (priceExist) {
                bookClassInstanceList = (List<BookingClassInstance>) sa.computeOptimalSeat4(bookClassInstanceList);

                optimalRev = sa.computeOptimalRev(bookClassInstanceList);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listInstance", listInstance);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bookClassInstanceList", bookClassInstanceList);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("optimalRev", optimalRev);
            } else {
                System.out.println("required field not fulfilled");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please set price for all booking classes (price>0)", ""));
            }

        } else {
            System.out.println("required field not fulfilled");
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

    public Integer getCurrentAllocated() {
        return currentAllocated;
    }

    public void setCurrentAllocated(Integer currentAllocated) {
        this.currentAllocated = currentAllocated;
    }

}
