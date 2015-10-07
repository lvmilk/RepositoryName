/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VRMB")
@ViewScoped
public class ViewRouteManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    @EJB
    private FlightSchedulingBeanLocal fsb;
    
    private UIComponent uIComponent;
private Route route;
    private List<Route> routeList;
    private List<Route> filteredRouteList;
    private List<FlightFrequency> flightOfRoute;

    public ViewRouteManagedBean() {
    }

    @PostConstruct
    public void init() {
        routeList = rpb.viewAllRoute();
    }

    public void viewRoute(ActionEvent event) throws IOException {
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewRoute", route);
        route = (Route) event.getComponent().getAttributes().get("route");
//FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightOfRoute", getFlightOfRoute(route));
        
        System.out.println("rmb.viewRoute(): Route " + route.getOrigin() + " - " + route.getDest() + " detail is displayed.");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewRouteDetail.xhtml");
    }

    public void viewRouteBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewRoute.xhtml");
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<Route> getRouteList() {
        routeList = rpb.viewAllRoute();
        System.out.println("rmb.getAirportList(): Route list size is " + routeList.size());
        return routeList;
    }

    public void setRouteList(List<Route> routeList) {
        this.routeList = routeList;
    }

    public List<Route> getFilteredRouteList() {
        return filteredRouteList;
    }

    public void setFilteredRouteList(List<Route> filteredRouteList) {
        this.filteredRouteList = filteredRouteList;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

//    public List<FlightFrequency> getFlightOfRoute(Route route) {
//        return fsb.getFlightOfRoute(route);
//    }

    public void setFlightOfRoute(List<FlightFrequency> flightOfRoute) {
        this.flightOfRoute = flightOfRoute;
    }
    
}
