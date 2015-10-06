package APSmanagedbean;

import Entity.APS.Airport;
import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xu
 */
@Named(value = "RouteManagedBean")
@ViewScoped
public class RouteManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private UIComponent uIComponent;

    private Double distance;
    private Double blockhour;
    private String originIATA;
    private String destIATA;
    private boolean addReturnRoute;
    private Route route;
    private List<Route> routeList = new ArrayList<>();
    private List<Route> filteredRouteList = new ArrayList<>();
    private List<Route> selectedRoute = new ArrayList<>();
    private List<Route> canDeleteRoute = new ArrayList<>();
    private List<Route> deletedRoute = new ArrayList<>();
    private List<Airport> airportList = new ArrayList<>();
    private Map<String, String> airportInfo = new HashMap<String, String>();

    public RouteManagedBean() {
    }

    @PostConstruct
    public void init() {
        routeList = rpb.viewAllRoute();
        canDeleteRoute = rpb.canDeleteRouteList();
    }

    public void addRoute() throws Exception {
        try {
            if (originIATA.equals(destIATA)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : origin airport cannot be same as destination airport.", ""));
            } else {
                rpb.checkRouteExist(originIATA, destIATA);
                if (addReturnRoute) {
                    rpb.checkRouteExist(destIATA, originIATA);
                }
                rpb.addRoute(originIATA, destIATA, distance);
                String rt = originIATA + " - " + destIATA;
                String rtNum = "Route ";
                if (addReturnRoute) {
                    rpb.addRoute(destIATA, originIATA, distance);
                    rt += " ," + destIATA + " - " + originIATA;
                    rtNum = "Routes ";
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addRouteSuccessString", rtNum + rt);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./addRouteSuccess.xhtml");
            }
        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Route has already been added.", ""));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void viewRoute(Route route) throws IOException {
        System.out.println("1");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("origin", route.getOrigin());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dest", route.getDest());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("distance", route.getDistance());
        // have not set serving aircraft yet
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("acType", route.getAcType().getType());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("blockhour", route.getBlockhour());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("status", route.getStatus());
        System.out.println("rmb.viewRoute(): Route " + route.getOrigin() + " - " + route.getDest() + " detail is displayed.");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewRouteDetail.xhtml");
    }

    public void viewRouteBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewRoute.xhtml");
    }

    public void editRoute(Route r) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("route", r);
        System.out.println("rmb.editRoute(): Route " + r.getId() + " information will be updated. ");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteDetail.xhtml");
    }

    public void toDeleteRoute() throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedRoute.isEmpty()) {
            System.out.println("RouteManagedBean.toDeleteRoute(): a) error no route selected.");
            context.execute("alert('Please select the route(s) to be deleted.');");
        } else {
            System.out.println("RouteManagedBean.toDeleteRoute(): b) routes will be deleted.");
            context.execute("PF('dlg').show();");
        }
    }

    public void confirmDeleteRoute() throws Exception {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedRoute", selectedRoute);
            rpb.deleteRouteList(selectedRoute);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteRouteSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void deleteRouteBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteRoute.xhtml");
    }

//    public boolean checkRouteFeasibility(Route route) {
//
//
//       
//    }

    public Map<String, String> getAirportInfo() {
        airportList = getAirportList();
        for (Airport r : airportList) {
            airportInfo.put(r.toString(), r.getIATA());
        }
        System.out.println(airportInfo.toString());
        return airportInfo;
    }
    
    public void setAirportInfo(Map<String, String> airportInfo) {
        this.airportInfo = airportInfo;
    }
    
    public List<Airport> getAirportList() {
        return rpb.viewAllAirport();
    }

    public void setAirportList(List<Airport> airportList) {
        this.airportList = airportList;
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

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getBlockhour() {
        return blockhour;
    }

    public void setBlockhour(Double blockhour) {
        this.blockhour = blockhour;
    }

    public String getOriginIATA() {
        return originIATA;
    }

    public void setOriginIATA(String originIATA) {
        this.originIATA = originIATA;
    }

    public String getDestIATA() {
        return destIATA;
    }

    public void setDestIATA(String destIATA) {
        this.destIATA = destIATA;
    }

    public boolean isAddReturnRoute() {
        return addReturnRoute;
    }

    public void setAddReturnRoute(boolean addReturnRoute) {
        this.addReturnRoute = addReturnRoute;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

    public List<Route> getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(List<Route> selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    public List<Route> getDeletedRoute() {
        return deletedRoute;
    }

    public void setDeletedRoute(List<Route> deletedRoute) {
        this.deletedRoute = deletedRoute;
    }

    public List<Route> getCanDeleteRoute() {
        return canDeleteRoute;
    }

    public void setCanDeleteRoute(List<Route> canDeleteRoute) {
        this.canDeleteRoute = canDeleteRoute;
    }

}
