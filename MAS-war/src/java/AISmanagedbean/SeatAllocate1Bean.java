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
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

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

    List<FlightFrequency> allFrequency=new ArrayList<>();
    List<FlightFrequency> frequencyList = new ArrayList<>();
    private Map<String, Map<String, String>> showDate = new HashMap<String, Map<String, String>>();
    private Map<String, String> odPair;
    private Map<String, FlightFrequency> flightMap;
    ;
    private String flightString;
    private FlightFrequency frequency;
    private String dateString;
    private List<BookingClassInstance> BookClassInstanceList = new ArrayList<>();
    private Date selectedDate = new Date();
    private Map<Date, Date> dateMap = new HashMap<Date, Date>();

    public SeatAllocate1Bean() {
    }

    @PostConstruct
    public void init() {
        flightMap = new HashMap<String, FlightFrequency>();;
    }

    public void onDateChange() {
        flightMap=new HashMap<String,FlightFrequency>();
        System.out.println("Getting in to onDateChange()");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = formatter.format(selectedDate);
        System.out.println(dateString);

        if (dateString != null && !dateString.equals("")) {
            frequencyList = sa.getFlightList(dateString);
            for (int i = 0; i < frequencyList.size(); i++) {
//            flightString = frequencyList.get(i).getFlightNo() + " " + frequencyList.get(i).getRoute().getOrigin().getIATA() + "-" + frequencyList.get(i).getRoute().getDest().getIATA();
//                flightMap.put(flightString, frequency
                System.out.println(frequencyList.get(i).getFlightNo()+" "+frequencyList.get(i).getRoute().toString());
            }
//                flightString = frequencyList.get(i).getFlightNo() + " " + frequencyList.get(i).getRoute().getOrigin().getIATA() + "-" + frequencyList.get(i).getRoute().getDest().getIATA();
//                flightMap.put(flightString, frequency);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", dateString);

            
        }
    }

    public void findBookClassInstance() {
        BookClassInstanceList = sa.getBkiList(frequency.getFlightNo(), dateString);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClassInstances", BookClassInstanceList);

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
        return BookClassInstanceList;
    }

    public void setBookClassInstanceList(List<BookingClassInstance> BookClassInstanceList) {
        this.BookClassInstanceList = BookClassInstanceList;
    }

    public Date getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(Date selectedDate) {
        this.selectedDate = selectedDate;
    }

    public Map<Date, Date> getDateMap() {
        return dateMap;
    }

    public void setDateMap(Map<Date, Date> dateMap) {
        this.dateMap = dateMap;
    }

    public List<FlightFrequency> getAllFrequency() {
        return allFrequency;
    }

    public void setAllFrequency(List<FlightFrequency> allFrequency) {
        this.allFrequency = allFrequency;
    }

   
    
}
