/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.aisEntity.BookingClassInstance;
import SessionBean.AirlineInventory.AssignPriceBeanLocal;
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

/**
 *
 * @author wang
 */
@Named(value = "assignPriceManagedBean")
@ViewScoped
public class AssignPriceManagedBean implements Serializable {

    @EJB
    AssignPriceBeanLocal apb;

    private List<Route> routeList = new ArrayList<Route>();
    private Long routeID;
    private List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
    private String flightNo;
    private List<FlightInstance> fiList = new ArrayList<FlightInstance>();
    private FlightInstance flightInst = new FlightInstance();

    private List<BookingClassInstance> bkiList = new ArrayList<BookingClassInstance>();

    @PostConstruct
    public void init() {
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        routeID = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("routeID");
        fiList = (List<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fiList");
        flightInst = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightInst");
        bkiList =(List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkiList");
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<Route> getRouteList() {
        System.out.println("APMB: getRouteList");
        return apb.getRouteList();
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        System.out.println("APMB: set Route! :" + routeID);
        this.routeID = routeID;
    }

    public List<FlightFrequency> getFfList() {
        System.out.println("APMB:getFFList");

        return ffList;
    }

    public void setFfList(List<FlightFrequency> ffList) {
        this.ffList = ffList;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public List<FlightInstance> getFiList() {
        return apb.getFlightInstanceList(flightNo);
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public void checkFlightInstance() throws IOException {
        System.out.println("APMB: checkFightInstance "+flightNo);
        this.setFiList(apb.getFlightInstanceList(flightNo));
        System.out.println("APMB: any flight instance  selected?  " + getFiList().size());
        if (flightNo != null && !flightNo.equals("")) {
            System.out.println("APMB: Flight is selected:  " + flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("routeID", routeID);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fiList", fiList);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./AssignPrice2.xhtml");
        } else {
            System.out.println("APMB: No Flight instance is chosen");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Error", "No flight chosen is chosen.");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //FacesContext.getCurrentInstance().addMessage(null, message);  
        }

    }

    public void onRouteChange() {
        this.setFfList(apb.getFlightFrequencyList(routeID));
    }

    public void generateBookingClass(FlightInstance fi) throws IOException  {
        apb.generateBookingClass(fi);
        flightInst = fi;
        fiList.remove(fi);
        this.setBkiList(apb.getBkiList(fi));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fiList", fiList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightInst", flightInst);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bkiList", bkiList);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./AssignPriceSuccess.xhtml");
    }

//    public List<BookingClassInstance> getBkiList(FlightInstance fi) {
//        bkiList = apb.getBkiList(fi);
//        return bkiList;
//    }

    public FlightInstance getFlightInst() {
        return flightInst;
    }

    public void setFi(FlightInstance flightInst) {
        this.flightInst = flightInst;
    }

    public List<BookingClassInstance> getBkiList() {
        return bkiList;
    }

    public void setBkiList(List<BookingClassInstance> bkiList) {
        this.bkiList = bkiList;
    }

    public void goBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("routeID", new Long(0));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fiList", new ArrayList<FlightInstance>());

        FacesContext.getCurrentInstance().getExternalContext().redirect("./AssignPrice1.xhtml");
    }

    public void goBacktoEdit() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("routeID", routeID);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fiList", fiList);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./AssignPrice2.xhtml");
    }

    /**
     * Creates a new instance of AssignPriceManagedBean
     */
    public AssignPriceManagedBean() {
    }

}
