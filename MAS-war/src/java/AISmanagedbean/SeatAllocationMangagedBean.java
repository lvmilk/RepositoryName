/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.AIS.BookingClassInstance;
import SessionBean.AIS.SeatAllocationBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private String dateString;
    private Date date;
    private List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
    private String flightNo;
    private List<BookingClassInstance> bkiList;
    //added 
    private List<CabinClass> cabinList = new ArrayList<CabinClass>();
    private String cabinName;
    private Integer allocateSeatNo;
    private Integer totalAllocatedSeat = 0;
    private Integer totalAvailableSeat = 0;
    boolean flag = true;

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("flightNo")) {
            flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
            dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
            cabinName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinName");
            System.out.println("SAMB: flight passed in viewscoped: " + flightNo);

        }
    }

//    public Integer getRequiredCount(){
//        int temp=0;
//       for (int i = 0; i < bkiList.size(); i++) {
//            temp = temp + (bkiList.get(i).getSeatNo() - bkiList.get(i).getBookedSeatNo());
//        }
//    }
//    
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
        Integer totalAllocatedSeat = 0;
        System.out.println("getCurrentTotalAvail:size(): " + bkiList.size());
        for (int i = 0; i < bkiList.size(); i++) {
            totalAllocatedSeat = totalAllocatedSeat + (bkiList.get(i).getSeatNo() - bkiList.get(i).getBookedSeatNo());
        }
        System.out.println("getCurrentTotalAvail:RETURN: " + totalAllocatedSeat);
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
        bkiList = new ArrayList<BookingClassInstance>();
        bkiList = sab.getBkiList(flightNo, dateString, cabinName);

        System.out.println("SAMB: getBkiList().size: " + bkiList.size());
        System.out.println("Flag: initial: " + this.flag);

            if (flag) {
            // System.out.println("SAMB: getBkiList(): bki" + bkiList.get(0).toString());
                // System.out.println("SAMB: getBkiList(): bkiList(0).flightCabin " + bkiList.get(0).getFlightCabin().toString());
                //  System.out.println("SAMB: getBkiList(): bkiList(0).flightCabin.CabinClass.Name " + bkiList.get(0).getFlightCabin().getCabinClass().getCabinName());
               if (!bkiList.isEmpty())
                totalAvailableSeat = bkiList.get(0).getFlightCabin().getCabinClass().getSeatCount() - bkiList.get(0).getFlightCabin().getBookedSeat();
//            for (FlightCabin cc : bkiList.get(0).getFlightCabin().getFlightInstance().getFlightCabins()) {
//                totalAvailableSeat = totalAvailableSeat + cc.getCabinClass().getSeatCount() - cc.getBookedSeat();
//            }
                this.flag = false;
            }
            return bkiList;
         
    }

//       public List<BookingClassInstance> getBkiList() {
//
//        bkiList = sab.getBkiList(flightNo, date);
//        System.out.println("SAMB: getBkiList().size: " + bkiList.size());
//        System.out.println("Flag: initial: " + this.flag);
//        //totalAvailableSeat=bkiList.get(0).getFlightCabin().getCabinClass().getSeatCount()-bkiList.get(0).getFlightCabin().getBookedSeat();
//        for (int i = 0; i < bkiList.size(); i++) {
//            System.out.println("Flag " + i + " = " + this.flag);
//            if (flag) {
//                System.out.println("SAMB: getBkiList():bkiList.get(i).getSeatNo() :" + bkiList.get(i).getFlightCabin().getCabinClass().getSeatCount());
//                System.out.println("SAMB: getBkiList():bkiList.get(i).getBookedSeatNo() :" + bkiList.get(i).getFlightCabin().getBookedSeat());
//                totalAvailableSeat = totalAvailableSeat + (bkiList.get(i).getFlightCabin().getCabinClass().getSeatCount() - bkiList.get(i).getFlightCabin().getBookedSeat());
//            }
//        }
//
//      
//        this.flag = false;
//
//        return bkiList;
//    }
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

    public List<CabinClass> getCabinList() {
        if (!cabinList.isEmpty()) {
            System.out.println("SAMB: getCabinList(): " + cabinList.toString());
        }
        return cabinList;
    }

    public void setCabinList(List<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        System.out.println("SAMB: FLIGHTNO SET");
        this.flightNo = flightNo;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        System.out.println("SAMB: cabinName SET");
        this.cabinName = cabinName;
    }

    public void onDateChange() {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("SAMB:OnDateChange run");
        dateString = df1.format(date);
        if (dateString != null && !date.equals("")) {
            flightList = sab.getFlightList(dateString);
            System.out.println("SAMB: Got flight list: size is " + flightList.size());

            System.out.println("SAMB:OnDateChange run result: " + flightList.toString());
        } else {
            flightList = new ArrayList<FlightFrequency>();
        }

    }

    public void onFlightChange() {
        System.out.println("SAMB: Got cabin list");

        cabinList = sab.getCabinList(flightNo);

    }

    public void checkFlight() throws IOException {
        System.out.println("SAMB: any flight selected?  ");
        if (flightNo != null && !flightNo.equals("")) {
            System.out.println("SAMB: Flight is selected:  " + flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", cabinName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocation2.xhtml");
        } else {
            System.out.println("SAMB: No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }

    public void checkFlight2() throws IOException {
        System.out.println("SAMB: any flight selected?  ");
        if (flightNo != null && !flightNo.equals("")) {
            System.out.println("SAMB: Flight is selected:  " + flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", cabinName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewSeatAvailability2.xhtml");
        } else {
            System.out.println("SAMB: No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }

    public void onRowEdit(RowEditEvent event) {
        System.out.println("SAMB:onRowEdit():ENTER!!! ");

        BookingClassInstance bki = (BookingClassInstance) event.getObject();
        Integer currentAvailable = this.getCurrentTotalAvailableSeat() - bki.getSeatNo() + bki.getBookedSeatNo() + allocateSeatNo;
        System.out.println("SAMB:onRowEdit(): currentAvailable: " + currentAvailable);
        System.out.println("SAMB:onRowEdit(): totalAvailableSeat: " + this.totalAvailableSeat);
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

    public Date getDate() {
        System.out.println("SAMB:get Date");
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        onDateChange();
        System.out.println("MPMB:set Date: " + date);
    }

    public void goBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", "");

        FacesContext.getCurrentInstance().getExternalContext().redirect("./SeatAllocation1.xhtml");
    }

    public void goBack2() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", "");

        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewSeatAvailability1.xhtml");
    }

    /**
     * Creates a new instance of SeatAllocationMangagedBean
     */
    public SeatAllocationMangagedBean() {
    }

}
