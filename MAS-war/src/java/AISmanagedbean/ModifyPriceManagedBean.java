/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.aisEntity.BookingClassInstance;
import SessionBean.AirlineInventory.ModifyPriceBeanLocal;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author wang
 */
@Named(value = "modifyPriceManagedBean")
@ViewScoped
public class ModifyPriceManagedBean implements Serializable {

    @EJB
    private ModifyPriceBeanLocal mpb;

    private String date;
    private List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
    private String flightNo;
    //private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    private Double price;

    /**
     * Creates a new instance of ModifyPriceManagedBean
     */
    public ModifyPriceManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("flightNo")) {
            flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
            date = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
        }
        System.out.println("MPMB: flight passed in viewscoped: " + flightNo);
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
        System.out.println("MPMB: price entered: " + price);
    }

    public List<BookingClassInstance> getBkiList() {
        bkiList = mpb.getBkiList(flightNo, date);
        return bkiList;
    }

    public void setBkiList(List<BookingClassInstance> bkiList) {
        this.bkiList = bkiList;
    }

    public List<FlightFrequency> getFlightList() {
        if (!flightList.isEmpty()) {
            System.out.println("MPMB: getFlightList(): " + flightList.toString());
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
        System.out.println("MPMB: FLIGHTNO SET");
        this.flightNo = flightNo;
    }

    public void onDateChange() {
        System.out.println("MBPB:OnDateChange run");
        if (date != null && !date.equals("")) {
            flightList = mpb.getFlightList(date);
            System.out.println("MB:OnDateChange run result: " + flightList.toString());
        } else {
            flightList = new ArrayList<FlightFrequency>();
        }
    }

    public void checkFlight() throws IOException {
        System.out.println("MPMB: any flight selected?  ");
        if (flightNo != null && !flightNo.equals("")) {
            System.out.println("MPMB: Flight is selected:  " + flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./ModifyPrice2.xhtml");
        } else {
            System.out.println("MBMP: No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }

    public void onRowEdit(RowEditEvent event) {
        BookingClassInstance bki = (BookingClassInstance) event.getObject();
        mpb.editPrice(bki, price);
        FacesMessage msg = new FacesMessage("Fare Edited", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class  Edited");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class Edit Cancelled");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public String getDate() {
        System.out.println("MPMB:get Date");
        return date;
    }

    public void setDate(String date) {
        this.date = date;
        onDateChange();
        System.out.println("MPMB:set Date: " + date);
    }

    public void goBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", "");

        FacesContext.getCurrentInstance().getExternalContext().redirect("./ModifyPrice1.xhtml");
    }
}
