/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.AIS.BookingClassInstance;
import SessionBean.AIS.AssignPriceBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
        bkiList = (List<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkiList");
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
        System.out.println("APMB: checkFightInstance " + flightNo);
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

    public void generateBookingClass(ActionEvent event) throws IOException {
        try {
            flightInst = (FlightInstance) event.getComponent().getAttributes().get("fi");
            System.out.println("flightInsantance: " + flightInst.getDate());
            apb.generateBookingClass(flightInst);
            System.out.println("fiList size before Remove: " + fiList.size());
            //fiList.remove(flightInst);
            int size = fiList.size();
            List<FlightInstance> fiListCopy = new ArrayList<FlightInstance>();
            for (Iterator<FlightInstance> it = fiList.iterator(); it.hasNext();) {
                FlightInstance temp = it.next();
                fiListCopy.add(temp);
            }

            for (int i = 0; i < size; i++) {
                System.out.print("Two compare element: " + fiListCopy.get(i).getId() + " " + flightInst.getId());
                if (fiListCopy.get(i).getId() == flightInst.getId()) {
                    System.out.println("REMOVED");
                    fiList.remove(i);
                }
            }

            System.out.println("fiList size after Remove: " + fiList.size());
            this.setFiList(fiList);
            this.setBkiList(apb.getBkiList(flightInst));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fiList", fiList);
            System.out.println("PUT SESSION MAP? " + fiList.size());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightInst", flightInst);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bkiList", bkiList);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./AssignPriceSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }

    }

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
