/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.aisEntity.BookingClassInstance;
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

    List<FlightFrequency> allFrequency = new ArrayList<>();
    List<FlightFrequency> frequencyList = new ArrayList<>();

    private Map<String, FlightFrequency> flightMap;
    private String flightNo;
    ;
    private String flightString;
    private FlightFrequency frequency;
    private String dateString;
    private List<BookingClassInstance> bookClassInstanceList = new ArrayList<>();
    private ArrayList<Integer> avgDemand = new ArrayList<>();
    private ArrayList<Integer> stdDemand = new ArrayList<>();
    private Date selectedDate = new Date();
    
   private List<List<BookingClassInstance>> listByCabin=new ArrayList<>();

    private List<BookingClassInstance> suiteInstance = new ArrayList<>();
    private List<BookingClassInstance> firstInstance = new ArrayList<>();
    private List<BookingClassInstance> bizInstance = new ArrayList<>();
    private List<BookingClassInstance> premiumInstance = new ArrayList<>();
    private List<BookingClassInstance> econInstance = new ArrayList<>();

    public SeatAllocate1Bean() {
    }

    @PostConstruct
    public void init() {
//        flightMap = new HashMap<String, FlightFrequency>();
        bookClassInstanceList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstances");

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

        
        bookClassInstanceList = sa.getBkiList(flightNo, dateString);
         System.out.println("size of bookClassInstanceList is "+bookClassInstanceList.size());

        suiteInstance = sa.listAssign(bookClassInstanceList, "Suite");
        System.out.println("size of suite is "+suiteInstance.size());
        firstInstance = sa.listAssign(bookClassInstanceList, "First Class");
        System.out.println("size of First Class is "+firstInstance.size());
        bizInstance = sa.listAssign(bookClassInstanceList, "Business Class");
        System.out.println("size of Business Class is "+bizInstance.size());
        premiumInstance = sa.listAssign(bookClassInstanceList, "Premium Economy Class");
        System.out.println("size of Premium Economy Class is "+premiumInstance.size());
        econInstance = sa.listAssign(bookClassInstanceList, "Economy Class");
        System.out.println("size of Economy Class is "+econInstance.size());
        
       listByCabin.add(suiteInstance);
       listByCabin.add(firstInstance);
       listByCabin.add(bizInstance);
       listByCabin.add(premiumInstance);
       listByCabin.add(econInstance);
       
        frequency = bookClassInstanceList.get(0).getFlightCabin().getFlightInstance().getFlightFrequency();

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("suiteInstance", suiteInstance);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstInstance", firstInstance);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bizInstance", bizInstance);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("premiumInstance", premiumInstance);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("econInstance", econInstance);
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("listByCabin", listByCabin);
        
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", bookClassInstanceList);

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
        frequency = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("frequency");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
        System.out.println("flight no is " + flightNo + " and date is " + dateString);
        bookClassInstanceList = sa.getBkiList(flightNo, dateString);
        System.out.println("size of booking class instance list is " + bookClassInstanceList.size());

        bookClassInstanceList = sa.computeOptimalSeat(bookClassInstanceList);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocate3.xhtml");

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

    public List<BookingClassInstance> getSuiteInstance() {
        return suiteInstance;
    }

    public void setSuiteInstance(List<BookingClassInstance> suiteInstance) {
        this.suiteInstance = suiteInstance;
    }

    public List<BookingClassInstance> getFirstInstance() {
        return firstInstance;
    }

    public void setFirstInstance(List<BookingClassInstance> firstInstance) {
        this.firstInstance = firstInstance;
    }

    public List<BookingClassInstance> getBizInstance() {
        return bizInstance;
    }

    public void setBizInstance(List<BookingClassInstance> bizInstance) {
        this.bizInstance = bizInstance;
    }

    public List<BookingClassInstance> getPremiumInstance() {
        return premiumInstance;
    }

    public void setPremiumInstance(List<BookingClassInstance> premiumInstance) {
        this.premiumInstance = premiumInstance;
    }

    public List<BookingClassInstance> getEconInstance() {
        return econInstance;
    }

    public void setEconInstance(List<BookingClassInstance> econInstance) {
        this.econInstance = econInstance;
    }

    public List<List<BookingClassInstance>> getListByCabin() {
        return listByCabin;
    }

    public void setListByCabin(List<List<BookingClassInstance>> listByCabin) {
        this.listByCabin = listByCabin;
    }

    
    
}
