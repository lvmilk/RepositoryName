/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import ADS.ReserveFlightBeanLocal;
import Entity.APS.Airport;
import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.aisEntity.BookingClassInstance;
import Entity.aisEntity.FlightCabin;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.AirlineInventory.SeatAssignBeanLocal;
import java.io.IOException;
import java.io.Serializable;
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

    private Calendar cal = new GregorianCalendar();
    private Calendar currentCal = new GregorianCalendar();

    private Boolean returnTrip = false;
    private Integer countPerson;

    private String origin;
    private String dest;
    private List<Route> routeList;
    private List<FlightFrequency> initialFrequency;
    private List<FlightFrequency> secondFrequency; 
    private List<FlightInstance> resultInstances;
    private List<FlightFrequency>resultFrequencies;

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

        currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        currentCal.add(Calendar.DATE, 1);
        currentDate = currentCal.getTime();

        initialFrequency = rf.getAllFlightFrequency();

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

    public void onSelectReturn() {
        System.out.println("geting into onSelectReturn!");
        System.out.println("return trip is " + returnTrip);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("returnTrip", returnTrip);

    }

    public void onOriginChange() {
        secondFrequency = rf.getSecondFrequency(origin);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("secondFrequency", secondFrequency);

    }

    public void onFlightChange() {
        if (!dest.equals("1")) {
//            dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
//            System.out.println("onFlightChange: dateString is " + dateString + " and flightNo is " + flightNo);
//            cabinList = sa.getCabinList(flightNo, dateString);
            resultFrequencies=rf.findFrequencies(origin,dest);
             resultInstances=rf.findResultInstanceList(origin,dest,departDate);
             System.out.println("in onFlightChange(): size of resultFrequencies is "+resultFrequencies.size());
             cabinList=resultFrequencies.get(0).getRoute().getAcType().getCabinList();
            
        } else {
          cabinList=new ArrayList<>();
         
        }

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList", cabinList);

    }

//    public void onDateChange() {
//        System.out.println("geting into onDateChange()");
//        System.out.println("SELECTED DATE IS " + departDate);
//        dateString = new String();
//        frequencyList = new ArrayList<>();
//        System.out.println("Getting in to onDateChange()");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        dateString = formatter.format(departDate);
//        System.out.println(dateString);
//
//        if (dateString != null && !dateString.equals("")) {
//            frequencyList = sa.getFlightList(dateString);
//            System.out.println("size of frequencyList is " + frequencyList.size());
//            if (frequencyList.size() > 0) {
//                for (int i = 0; i < frequencyList.size(); i++) {
//                    System.out.println(frequencyList.get(i).getFlightNo() + " " + frequencyList.get(i).getRoute().toString());
//                }
//            }
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", dateString);
//
//        }
//    }
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

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public Calendar getCurrentCal() {
        return currentCal;
    }

    public void setCurrentCal(Calendar currentCal) {
        this.currentCal = currentCal;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public List<FlightInstance> getResultInstances() {
        return resultInstances;
    }

    public void setResultInstances(List<FlightInstance> resultInstances) {
        this.resultInstances = resultInstances;
    }

    public List<FlightFrequency> getResultFrequencies() {
        return resultFrequencies;
    }

    public void setResultFrequencies(List<FlightFrequency> resultFrequencies) {
        this.resultFrequencies = resultFrequencies;
    }

  

    
    
    
}
