/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.Airport;
import Entity.APS.Route;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
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

    @EJB
    private FleetPlanningBeanLocal fpb;

    private UIComponent uIComponent;

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

    private AircraftType acType;
    private String acTypeString;
    private Airport origin;
    private Airport dest;

    private List<AircraftType> acTypeList;
    private List<String> acTypeInfo = new ArrayList();
 
//    private Map<String, String> acTypeInfo = new HashMap<String, String>();

    public EditRouteManagedBean() {
    }

    @PostConstruct
    public void init() {
        route = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("route");
        
        acTypeList = fpb.getAllAircraftType();
        for (int i = 0; i < acTypeList.size(); i++) {
            acTypeString = acTypeList.get(i).getType();
            acTypeInfo.add(acTypeString);
        }
       // acTypeInfo = getAcTypeInfo();
       // acTypeString = route.getAcType().getType();
        originIATA = route.getOrigin().getIATA();
        destIATA = route.getDest().getIATA();
        distance = route.getDistance();
        distance = route.getDistance();
        blockhour = route.getBlockhour();
//        basicScFare = route.getBasicScFare();
//        basicFcFare = route.getBasicFcFare();
//        basicBcFare = route.getBasicBcFare();
//        basicPecFare = route.getBasicPecFare();
//        basicEcFare = route.getBasicEcFare();
    }

    public void editRoute() throws Exception {
        try {
            if (acTypeString != null) {
                acType = fpb.getAircraftType(acTypeString);
            }
            rpb.editRouteBasic(originIATA, destIATA, distance, acType, blockhour);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editRouteCancel() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editRoute.xhtml");
    }

//    public Map<String, String> getAcTypeInfo() {
//        acTypeList = getAcTypeList();
//        for (AircraftType a : acTypeList) {
//            acTypeList.put(a.toString(), a.getIATA());
//        }
//        System.out.println(acTypeInfo.toString());
//        return acTypeInfo;
//    }
//
//    public void setAcTypeInfo(Map<String, String> acTypeInfo) {
//        this.acTypeInfo = acTypeInfo;
//    }
//
    public List<String> getAcTypeInfo() {
        List<AircraftType> typeList = fpb.getAllAircraftType();
        List<String> typeInfo = new ArrayList<>();
        for (AircraftType a : typeList) {
            typeInfo.add(a.getType());
        }
        return typeInfo;
    }

    public void setAcTypeInfo(List<String> acTypeInfo) {
        this.acTypeInfo = acTypeInfo;
    }

    public List<AircraftType> getAcTypeList() {
        return fpb.getAllAircraftType();
    }

    public void setAcTypeList(List<AircraftType> acTypeList) {
        this.acTypeList = acTypeList;
    }

    public String getAcTypeString() {
        return acTypeString;
    }

    public void setAcTypeString(String acTypeString) {
        this.acTypeString = acTypeString;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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

    public Double getBasicScFare() {
        return basicScFare;
    }

    public void setBasicScFare(Double basicScFare) {
        this.basicScFare = basicScFare;
    }

    public Double getBasicFcFare() {
        return basicFcFare;
    }

    public void setBasicFcFare(Double basicFcFare) {
        this.basicFcFare = basicFcFare;
    }

    public Double getBasicBcFare() {
        return basicBcFare;
    }

    public void setBasicBcFare(Double basicBcFare) {
        this.basicBcFare = basicBcFare;
    }

    public Double getBasicPecFare() {
        return basicPecFare;
    }

    public void setBasicPecFare(Double basicPecFare) {
        this.basicPecFare = basicPecFare;
    }

    public Double getBasicEcFare() {
        return basicEcFare;
    }

    public void setBasicEcFare(Double basicEcFare) {
        this.basicEcFare = basicEcFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDest() {
        return dest;
    }

    public void setDest(Airport dest) {
        this.dest = dest;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

}
