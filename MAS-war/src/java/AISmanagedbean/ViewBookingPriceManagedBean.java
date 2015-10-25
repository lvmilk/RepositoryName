/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.AIS.BookingClassInstance;
import SessionBean.AIS.ViewBookingClassPriceBeanLocal;
import java.io.IOException;
import javax.inject.Named;
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
import javax.faces.view.ViewScoped;

import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author wang
 */
@Named(value = "viewBookingPriceManagedBean")
@ViewScoped
public class ViewBookingPriceManagedBean implements Serializable {

    @EJB
    private ViewBookingClassPriceBeanLocal mpb;

    private String dateString;
    private Date date;
    private List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
    private String flightNo;
    //private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();
    private Double price;

    /**
     * Creates a new instance of ModifyPriceManagedBean
     */
    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("flightNo")) {
            flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
            dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
          
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
        bkiList = mpb.getBkiList(flightNo, dateString);
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
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("MBPB:OnDateChange run");
         dateString = df1.format(date);
        if (dateString != null && !dateString.equals("")) {
            flightList = mpb.getFlightList(dateString);
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
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewBookingClassPrice2.xhtml");
        } else {
            System.out.println("MBMP: No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }



    public Date getDate() {
        System.out.println("MPMB:get Date");
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        onDateChange();
        System.out.println("MPMB:set Date: " + date);
    }

    public void goBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", "");

        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewBookingClassPrice1.xhtml");
    }

    /**
     * Creates a new instance of ViewBookingPriceManagedBean
     */
    public ViewBookingPriceManagedBean() {
    }

}
