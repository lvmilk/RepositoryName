/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.AircraftType;
import Entity.APS.Route;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateful
public class PricingBean implements PricingBeanLocal {

    @PersistenceContext
    EntityManager em;

    private AircraftType aircraftType = new AircraftType();
    private Route route = new Route();
    private List<Route> routeList = new ArrayList<>();
    private Double crewCost;
    private Double calculatedFare;
    private Map<String, Double> allFare = new HashMap<String, Double>();

    public void setAircraftType(String type) {
        Query query = em.createQuery("SELECT a FROM AircraftType a where a.type=:atype ");
        query.setParameter("atype", type);
        List<AircraftType> resultList = query.getResultList();
        if (resultList.size() != 0) {
            aircraftType = resultList.get(0);
        }
    }

    @Override
    public AircraftType getAircraftType(String type) {
        setAircraftType(type);
        return aircraftType;
    }

    public void setRouteList() {
        Query query = em.createQuery("SELECT r FROM Route r");
        List<Route> resultList = query.getResultList();
        if (resultList.size() != 0) {
            this.routeList = resultList;
        }
        System.out.println("SessionBean:setRouteList: size " + this.routeList.size());
    }

    public List<Route> getRouteList() {
        setRouteList();
        System.out.println("SessionBean:getRouteList " + routeList.size());
        return routeList;
    }

    public void setRouteInfo(Long ID) {
        Query query = em.createQuery("SELECT r FROM Route r where r.id=:rID ");
        query.setParameter("rID", ID);
        List<Route> resultList = query.getResultList();
        if (!resultList.isEmpty()) {
            route = resultList.get(0);
        }
        System.out.println("SessionBean:setRouteInfo: size " + resultList.size());
    }

    @Override
    public Route getRouteInfo(Long ID) {
        setRouteInfo(ID);
        return route;
    }

//    @Override
//    public Integer calculateCrewNo(Integer seatNo) {
//        Integer crewNo;
//        if (seatNo >= 19 && seatNo <= 50) {
//            crewNo = 3;     //one cabin crew and two cockpit crew
//        } else if (seatNo > 50 && seatNo <= 100) {
//            crewNo = 4;
//        } else if (seatNo > 100) {
//            crewNo = Double.valueOf(Math.ceil(seatNo / 50)).intValue() + 4;
//        } else {
//            crewNo = 2;  //if seatNo<19:No need cabin crew
//        }
//        return crewNo;
//    }

    public void calculateCrewCost(Integer crewNo, Double crewUnitCost, Double blockHour, Integer annualDepartures) {
        if (crewNo * crewUnitCost * blockHour * annualDepartures > 0) {
            crewCost = crewNo * crewUnitCost * blockHour * annualDepartures;
        }

    }

    public Double getCrewCost(Integer crewNo, Double crewUnitCost, Double blockHour, Integer annualDepartures) {
        calculateCrewCost(crewNo, crewUnitCost, blockHour, annualDepartures);
        return crewCost;
    }

    //NEW!!!!!!!
    public void calculateFare(Double expectedRev, Integer totalSeatNo, Double loadFactor, Integer annualDepartures, String cabin) {
        Double fare = expectedRev / (totalSeatNo * loadFactor * annualDepartures);
        System.out.println("SessionBean: fare calculated: for " + cabin + " = " + fare);
        if (cabin.equals("Suite")) {
            route.setBasicScFare(fare);  //notice here!!!!!
        } else if (cabin.equals("First Class")) {
            route.setBasicFcFare(fare);
        } else if (cabin.equals("Economy Class")) {
            route.setBasicEcFare(fare);
        } else if (cabin.equals("Premier Economy Class")) {
            route.setBasicPecFare(fare);
        } else if (cabin.equals("Business Class")) {
            route.setBasicBcFare(fare);
        } else {
            System.out.println("Invalid!");
        }
        System.out.println("Sessionbean: Fcfare? " + route.getBasicFcFare());
        System.out.println("Sessionbean: Scfare? " + route.getBasicScFare());
        System.out.println("Sessionbean: Ecfare? " + route.getBasicEcFare());
        System.out.println("Sessionbean: Pecfare? " + route.getBasicPecFare());
        System.out.println("Sessionbean: Bcfare? " + route.getBasicBcFare());
        calculatedFare = fare;
        em.merge(route);
    }

    public Double getFare() {
        return calculatedFare;
    }

    public Map<String, Double> getAllFare(Route route) {
        Route r = route;
        if (r.getBasicBcFare()!=null) {
            allFare.put("Business Class", r.getBasicBcFare());
        }
        if (r.getBasicFcFare()!=null) {
           allFare.put("First Class", r.getBasicFcFare());
        } 
        if (r.getBasicEcFare()!=null) {
           allFare.put("Economy Class", r.getBasicEcFare());
        } 
        if (r.getBasicPecFare()!=null) {
           allFare.put("Premier Economy Class", r.getBasicPecFare());
        } 
        if (r.getBasicScFare()!=null) {
           allFare.put("Suite", r.getBasicScFare());
        }
        return allFare;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
