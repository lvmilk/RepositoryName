/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.Airport;
import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "editRouteManagedBean")
@ViewScoped
public class EditRouteManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private Route route;
    private Double distance;
    private Double blockhour;
    private Double basicScFare;
    private Double basicFcFare;
    private Double basicBcFare;
    private Double basicPecFare;
    private Double basicEcFare;
    private String status;

    private String originIATA;
    private String destIATA;
    private String aircraftType;

    private AircraftType acType;
    private Airport origin;
    private Airport dest;

    public EditRouteManagedBean() {
    }

    @PostConstruct
    public void init() {
        route = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route");
        originIATA = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.origin.IATA");
        destIATA = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.dest.IATA");
        distance = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.distance");
        aircraftType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.aircraftType");
        blockhour = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.blockhour");
        basicScFare = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.basicScFare");
        basicFcFare = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.basicFcFare");
        basicBcFare = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.basicBcFare");
        basicPecFare = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.basicPecFare");
        basicEcFare = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route.basicEcFare");
    }

    public void editRouteBasic() throws Exception{ 
        rpb.editRouteBasic(originIATA, destIATA, distance, aircraftType, blockhour);
        
    }
    
    public void editRouteFare() throws Exception{
        try {
            rpb.editRouteFare(originIATA, destIATA, basicScFare, basicFcFare, basicBcFare, basicPecFare, basicEcFare);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }
    
}
