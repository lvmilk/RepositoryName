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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "CRPMB")
@ViewScoped
public class CheckRouteProfitabilityManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private Route route;
    private String mPriceString;
    private String pVolumnString;
    private Double mPrice;
    private Integer pVolumn;
    private List<AircraftType> acList = new ArrayList<>();
    private List<String> acKeyList = new ArrayList<>();
    private Map<String, String> fuelCostMap = new HashMap<String, String>();
    private Map<String, String> revenueMap = new HashMap<String, String>();
    private Map<String, String> leaseCostMap = new HashMap<String, String>();
    private Map<String, String> crewCostMap = new HashMap<String, String>();
    private Map<String, String> resultMap = new HashMap<String, String>();
    private Map<String, String> profitMap = new HashMap<String, String>();
//    private List<String> profResult; // profitability result per aircraft

    public CheckRouteProfitabilityManagedBean() {

    }

    @PostConstruct
    public void init() {
        route = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("routeCheck");
        pVolumnString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pVolumnString");
        setpVolumnString(pVolumnString);
        mPriceString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("mPriceString");
        setmPriceString(mPriceString);
        acList = rpb.feasibleAc(route);
        checkRouteCost();

    }

    public void checkRouteProfit() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkRouteProfitabilityResult.xhtml");
    }

    public void checkRouteCost() {
        pVolumn = getpVolumn();
        mPrice = getmPrice();
        for (AircraftType a : acList) {
            Integer seatNo = a.getBcSeatNo() + a.getEcSeatNo() + a.getFcSeatNo() + a.getPecSeatNo() + a.getSuiteNo();
            Integer flightTimes = pVolumn / seatNo;
            Double revenue = flightTimes * seatNo * mPrice;
            Double flightHr = flightTimes * route.getBlockhour();
            Double fuel = flightHr * a.getFuelCost();
            Double lease = 12 * a.getLeaseCost(); // leaseCost is monthly
//            Double crew
            acKeyList.add(a.getType());
            fuelCostMap.put(a.getType(), fuel.toString());
            leaseCostMap.put(a.getType(), lease.toString());
            revenueMap.put(a.getType(), revenue.toString());
            Double profit = revenue - fuel - lease;
            profitMap.put(a.getType(), profit.toString());
            if (profit < 0) {
                resultMap.put(a.getType(), "Not profitable: negative profit margin.");
            } else {
                resultMap.put(a.getType(), "Profitable: positive profit margin.");
            }
        }
    }

    public void assignAcToRoute() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("route", route);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteDetail.xhtml");
    }

    public void checkProfitabilityBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkRouteProfitability.xhtml");
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getmPriceString() {
        return mPriceString;
    }

    public void setmPriceString(String mPriceString) {
        this.mPriceString = mPriceString;
    }

    public String getpVolumnString() {
        return pVolumnString;
    }

    public void setpVolumnString(String pVolumnString) {
        this.pVolumnString = pVolumnString;
    }

    public Double getmPrice() {
        return Double.valueOf(mPriceString);
    }

    public void setmPrice(Double mPrice) {
        this.mPrice = mPrice;
    }

    public Integer getpVolumn() {
        return Integer.valueOf(pVolumnString);
    }

    public void setpVolumn(Integer pVolumn) {
        this.pVolumn = pVolumn;
    }

    public List<AircraftType> getAcList() {
        return acList;
    }

    public void setAcList(List<AircraftType> acList) {
        this.acList = acList;
    }

    public List<String> getAcKeyList() {
        return acKeyList;
    }

    public void setAcKeyList(List<String> acKeyList) {
        this.acKeyList = acKeyList;
    }

    public Map<String, String> getFuelCostMap() {
        return fuelCostMap;
    }

    public void setFuelCostMap(Map<String, String> fuelCostMap) {
        this.fuelCostMap = fuelCostMap;
    }

    public Map<String, String> getRevenueMap() {
        return revenueMap;
    }

    public void setRevenueMap(Map<String, String> revenueMap) {
        this.revenueMap = revenueMap;
    }

    public Map<String, String> getLeaseCostMap() {
        return leaseCostMap;
    }

    public void setLeaseCostMap(Map<String, String> leaseCostMap) {
        this.leaseCostMap = leaseCostMap;
    }

    public Map<String, String> getCrewCostMap() {
        return crewCostMap;
    }

    public void setCrewCostMap(Map<String, String> crewCostMap) {
        this.crewCostMap = crewCostMap;
    }

    public Map<String, String> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, String> resultMap) {
        this.resultMap = resultMap;
    }

    public Map<String, String> getProfitMap() {
        return profitMap;
    }

    public void setProfitMap(Map<String, String> profitMap) {
        this.profitMap = profitMap;
    }

}
