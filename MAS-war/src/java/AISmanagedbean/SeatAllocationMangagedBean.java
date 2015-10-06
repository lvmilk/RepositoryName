/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.aisEntity.BookingClassInstance;
import SessionBean.AirlineInventory.SeatAllocationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author wang
 */
@Named(value = "seatAllocationMangagedBean")
@ViewScoped
public class SeatAllocationMangagedBean implements Serializable {

    @EJB
    private SeatAllocationBeanLocal sab;

    private String date;
    private List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
    private String flightNo;
    //private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    //private Double price;
    private Integer allocateSeatNo;
    private Integer totalAllocatedSeat=0;
    private Integer totalAvailableSeat=0;

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("flightNo")) {
            flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
            date = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
        }
        System.out.println("SAMB: flight passed in viewscoped: " + flightNo);
    }

    public Integer getAllocateSeatNo() {
        return allocateSeatNo;
    }

    public void setAllocateSeatNo(Integer allocateSeatNo) {
        this.allocateSeatNo = allocateSeatNo;
    }

    public Integer getTotalAllocatedSeat() {
        return totalAllocatedSeat;
    }

    public Integer getCurrentTotalAvailableSeat() {
        Integer totalAllocatedSeat=0;
        System.out.println("getCurrentTotalAvail:size()"+bkiList.size());
        for (int i=0;i<bkiList.size();i++) {
            totalAllocatedSeat = totalAllocatedSeat + (bkiList.get(i).getSeatNo() - bkiList.get(i).getBookedSeatNo());
        }
        System.out.println("getCurrentTotalAvail:RETURN: "+totalAllocatedSeat);
        return totalAllocatedSeat;
    }

    public void setTotalAllocatedSeat(Integer totalAllocatedSeat) {
        this.totalAllocatedSeat = totalAllocatedSeat;
    }

    public Integer getTotalAvailableSeat() {
        return totalAvailableSeat;
    }

    public void setTotalAvailableSeat(Integer totalAvailableSeat) {
        this.totalAvailableSeat = totalAvailableSeat;
    }

    public List<BookingClassInstance> getBkiList() {
        totalAvailableSeat=0;
        bkiList = sab.getBkiList(flightNo, date);
        System.out.println("SAMB: getBkiList().size: " + bkiList.size());
        for(int i=0;i<bkiList.size();i++) {
            
            System.out.println("SAMB: getBkiList():bkiList.get(i).getSeatNo() :"+bkiList.get(i).getSeatNo());
            System.out.println("SAMB: getBkiList():bkiList.get(i).getBookedSeatNo() :"+bkiList.get(i).getBookedSeatNo());
            totalAvailableSeat = totalAvailableSeat + (bkiList.get(i).getSeatNo() - bkiList.get(i).getBookedSeatNo());
        };
        return bkiList;
    }

    public void setBkiList(List<BookingClassInstance> bkiList) {
        this.bkiList = bkiList;
    }

    public List<FlightFrequency> getFlightList() {
        if (!flightList.isEmpty()) {
            System.out.println("SAMB: getFlightList(): " + flightList.toString());
        }
        return flightList;
    }

    public void setFlightList(List<FlightFrequency> flightList) {
        this.flightList = flightList;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        System.out.println("SAMB: FLIGHTNO SET");
        this.flightNo = flightNo;
    }

    public void onDateChange() {
        System.out.println("SAMB:OnDateChange run");
        if (date != null && !date.equals("")) {
            flightList = sab.getFlightList(date);
            System.out.println("SAMB:OnDateChange run result: " + flightList.toString());
        } else {
            flightList = new ArrayList<FlightFrequency>();
        }

    }

    public void checkFlight() throws IOException {
        System.out.println("SAMB: any flight selected?  ");
        if (flightNo != null && !flightNo.equals("")) {
            System.out.println("SAMB: Flight is selected:  " + flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocation2.xhtml");
        } else {
            System.out.println("SAMP: No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bki = (BookingClassInstance) event.getObject();
        Integer currentAvailable = this.getCurrentTotalAvailableSeat()-bki.getSeatNo()+bki.getBookedSeatNo()+allocateSeatNo;
        System.out.println("SAMB:onRowEdit(): currentAvailable: "+currentAvailable);
        System.out.println("SAMB:onRowEdit(): totalAvailableSeat: "+this.totalAvailableSeat);
        if (currentAvailable > this.totalAvailableSeat) {
            FacesMessage msg2 = new FacesMessage("Error", "Allocated seat count excceeds total available seat count!");
            FacesContext.getCurrentInstance().addMessage(null, msg2);
        } else {
            sab.editSeatNo(bki, bki.getBookedSeatNo() + allocateSeatNo);
            FacesMessage msg = new FacesMessage("Seat Number Edited", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class Seat Allocation Edited");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getDate() {
        System.out.println("SAMB:get Date");
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        onDateChange();
        System.out.println("MPMB:set Date: " + date);
    }

    /**
     * Creates a new instance of SeatAllocationMangagedBean
     */
    public SeatAllocationMangagedBean() {
    }

}
