package APSmanagedbean;

import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

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

    public RouteManagedBean() {
    }

    @PostConstruct
    public void init() {
        routeList = rpb.viewAllRoute();
//        canDeleteRoute = ;
    }

    public void addRoute() throws Exception {
        try {
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

    public void editRoute(Route route) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("route", route);
        System.out.println("rmb.editRoute(): Route " + route.getId() + " information will be updated. ");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteDetail.xhtml");
    }

//    public boolean checkRouteFeasibility(Route route) {
//       
//       
//       
//    }
    
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

}
