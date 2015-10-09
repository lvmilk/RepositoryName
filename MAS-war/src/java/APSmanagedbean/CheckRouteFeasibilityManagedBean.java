/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "CRFMB")
@ViewScoped
public class CheckRouteFeasibilityManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private Route route;
    private List<AircraftType> feasibleAcDis;
    private List<AircraftType> feasibleAcAsp;
    private List<AircraftType> feasibleAc;
    private String feasibleAcString;
    private boolean feasibility;
    private String feasibilityResult;

    public CheckRouteFeasibilityManagedBean() {
    }

    @PostConstruct
    public void init() {
        route = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("routeCheck");
        feasibleAcDis = rpb.checkFeasibleAcByDis(route);
        feasibleAcAsp = rpb.checkFeasibleAcByAsp(route);
        feasibleAc = rpb.feasibleAc(route);
    }

    public void checkFeasibilityBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkRouteFeasibility.xhtml");
    }

    public String getFeasibleAcString() {
        String s = "";
        for(AircraftType a: getFeasibleAc()) {
           s += a.getType() + " ";
        }
        return s;
    }

    public void setFeasibleAcString(String feasibleAcString) {
        this.feasibleAcString = feasibleAcString;
    }

    public String getFeasibilityResult() {
        if(isFeasibility()) {
            return "Feasible";
        }
        return "Infeasible: No aircraft type are suitable for this route.";
    }

    public void setFeasibilityResult(String feasibilityResult) {
        this.feasibilityResult = feasibilityResult;
    }

    public boolean isFeasibility() {
        return !getFeasibleAc().isEmpty();
    }

    public void setFeasibility(boolean feasibility) {
        this.feasibility = feasibility;
    }

    
    public List<AircraftType> getFeasibleAc() {
        return rpb.feasibleAc(route);
    }

    public void setFeasibleAc(List<AircraftType> feasibleAc) {
        this.feasibleAc = feasibleAc;
    }

    public List<AircraftType> getFeasibleAcAsp() {
        return rpb.checkFeasibleAcByAsp(route);
    }

    public void setFeasibleAcAsp(List<AircraftType> feasibleAcAsp) {
        this.feasibleAcAsp = feasibleAcAsp;
    }

    public List<AircraftType> getFeasibleAcDis() {
        return rpb.checkFeasibleAcByDis(route);
    }

    public void setFeasibleAcDis(List<AircraftType> feasibleAcDis) {
        this.feasibleAcDis = feasibleAcDis;
    }

    public Route getRoute() {
        return (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("routeCheck");
    }

    public void setRoute(Route route) {
        this.route = route;
    }

}
