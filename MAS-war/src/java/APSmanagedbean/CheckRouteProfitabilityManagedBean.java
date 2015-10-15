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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    private Double marketPrice;
    private Integer passVolumn;
    private boolean acToAssign = false;
    private List<AircraftType> acList = new ArrayList<>();
    private List<String> acKeyList = new ArrayList<>();
    private Map<String, String> fuelCostMap = new HashMap<>();
    private Map<String, String> revenueMap = new HashMap<>();
    private Map<String, String> leaseCostMap = new HashMap<>();
    private Map<String, String> crewCostMap = new HashMap<>();
    private Map<String, String> resultMap = new HashMap<>();
    private Map<String, String> profitMap = new HashMap<>();
    private Map<String, String> mtCostMap = new HashMap<>();
    private Map<String, String> otherCostMap = new HashMap<>();
    private Map<String, String> totalCostMap = new HashMap<>();
    private Map<String, String> profitMarginMap = new HashMap<>();

//    private List<String> profResult; // profitability result per aircraft
    public CheckRouteProfitabilityManagedBean() {

    }

    @PostConstruct
    public void init() {
        route = (Route) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("routeCheck");
        passVolumn = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("passVolumn");
        setPassVolumn(passVolumn);
        marketPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("marketPrice");
        setMarketPrice(marketPrice);
        acList = rpb.feasibleAc(route);
        checkRouteCost();
    }

    public void checkRouteProfit() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkRouteProfitabilityResult.xhtml");
    }

    public void checkRouteCost() {
        passVolumn = getPassVolumn();
        marketPrice = getMarketPrice();
        for (AircraftType a : acList) {
            Integer seatNo = a.getBcSeatNo() + a.getEcSeatNo() + a.getFcSeatNo() + a.getPecSeatNo() + a.getSuiteNo();
            System.out.println("CRFMB.checkRouteCost(): aircraft seat number " + seatNo);
            Integer flightTimes = passVolumn / seatNo;
            System.out.println("CRFMB.checkRouteCost(): annual flight times " + flightTimes);
            Double revenue = flightTimes * seatNo * marketPrice / 1000.0;
            Double flightHr = flightTimes * route.getBlockhour();
            System.out.println("CRFMB.checkRouteCost(): annual total flight hours " + flightHr);
            Double fuel = flightHr * a.getFuelCost() / 1000.0;
            System.out.println("CRFMB.checkRouteCost(): fuel cost thousand SGD " + fuel);
            Double lease = 12 * a.getLeaseCost() / 1000.0; // leaseCost is monthly
            Double crew = ((a.getStewardess() * 20 + a.getSteward() * 20 + a.getPurser() * 30 + a.getPilot() * 100 + a.getCaptain() * 200) * flightHr) / 1000.0;
            Double mtCost = 3640.0;
            Double totalCost = fuel + lease + crew + mtCost;
            Double profit = revenue - totalCost;
            acKeyList.add(a.getType());
            fuelCostMap.put(a.getType(), fuel.toString());
            leaseCostMap.put(a.getType(), lease.toString());
            revenueMap.put(a.getType(), revenue.toString());
            crewCostMap.put(a.getType(), crew.toString());
            totalCostMap.put(a.getType(), totalCost.toString());
            profitMap.put(a.getType(), profit.toString());
            mtCostMap.put(a.getType(), mtCost.toString());
            Double profitMargin = profit / totalCost;
            DecimalFormat df = new DecimalFormat("#.00%");
            if (profit < 0) {
//                profitMarginMap.put(a.getType(), String.format("%.0f%%", profitMargin));
//                resultMap.put(a.getType(), "Not profitable");
//                System.out.println(df.format(profitMargin));
                profitMarginMap.put(a.getType(), df.format(profitMargin));
            } else {
                profitMarginMap.put(a.getType(), df.format(profitMargin));
                //  resultMap.put(a.getType(), "Profitable");
                acToAssign = true;
            }
        }
    }

    public void assignAcToRoute() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("route", route);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editRouteDetail.xhtml");
    }

    public void checkProfitabilityBack(ActionEvent event) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./checkRouteProfitability.xhtml");
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
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

    public boolean isAcToAssign() {
        return acToAssign;
    }

    public void setAcToAssign(boolean acToAssign) {
        this.acToAssign = acToAssign;
    }

    public Double getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(Double marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getPassVolumn() {
        return passVolumn;
    }

    public void setPassVolumn(Integer passVolumn) {
        this.passVolumn = passVolumn;
    }

    public Map<String, String> getMtCostMap() {
        return mtCostMap;
    }

    public void setMtCostMap(Map<String, String> mtCostMap) {
        this.mtCostMap = mtCostMap;
    }

    public Map<String, String> getOtherCostMap() {
        return otherCostMap;
    }

    public void setOtherCostMap(Map<String, String> otherCostMap) {
        this.otherCostMap = otherCostMap;
    }

    public Map<String, String> getTotalCostMap() {
        return totalCostMap;
    }

    public void setTotalCostMap(Map<String, String> totalCostMap) {
        this.totalCostMap = totalCostMap;
    }

    public Map<String, String> getProfitMarginMap() {
        return profitMarginMap;
    }

    public void setProfitMarginMap(Map<String, String> profitMarginMap) {
        this.profitMarginMap = profitMarginMap;
    }

}
