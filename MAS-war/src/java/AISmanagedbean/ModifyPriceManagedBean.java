/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import SessionBean.AirlineInventory.ModifyPriceBeanLocal;
import java.io.IOException;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author wang
 */
@Named(value = "modifyPriceManagedBean")
@SessionScoped
public class ModifyPriceManagedBean implements Serializable {

    @EJB
    private ModifyPriceBeanLocal mpb;

    private String date;
    private List<FlightFrequency> flightList = new ArrayList<FlightFrequency>();
    private String flightNo;

    /**
     * Creates a new instance of ModifyPriceManagedBean
     */
    public ModifyPriceManagedBean() {
    }

//    @PostConstruct
//    public void init() {
//        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().containsKey("flight")) {
//            flight = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flight");
//        }
//        System.out.println("MB: flight passwd in viewscoped" + flight.getFlightNo());
//    }
    public List<FlightFrequency> getFlightList() {
        if (flightList!=null) {
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
        if (flightNo != null) {
            //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flight", flight);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ModifyPrice2.xhtml");
        } else {
            System.out.println("No Flight is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight is chosen.");

            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }

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

}
