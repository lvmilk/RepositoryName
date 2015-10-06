/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.APS.Airport;
import Entity.APS.Route;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateless
public class RoutePlanningBean implements RoutePlanningBeanLocal {

    @PersistenceContext
    EntityManager em;

    Airport airport;
    Route route;

    public RoutePlanningBean() {
    }

    @Override
    public void addAirport(String IATA, String airportName, String cityName, String countryName, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport != null) {
            System.out.println("rpb.addAirport(): Airport " + airport.getIATA() + " exists.");
            throw new Exception("Airport " + airport.getIATA() + " exists.");
        }
        airport = new Airport();
        airport.create(IATA, airportName, cityName, countryName, spec, timeZone, opStatus, strategicLevel, airspace);
        em.persist(airport);
        em.flush();
        System.out.println("rpb.addAirport(): Airport " + airport.getIATA() + " added!");
    }

    @Override
    public void editAirport(String IATA, String airportName, String cityName, String countryName, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport == null) {
            throw new Exception("Airport does not exist.");
        }
        airport.setAirportName(airportName);
        airport.setCityName(cityName);
        airport.setCountryName(countryName);
        airport.setSpec(spec);
        airport.setTimeZone(timeZone);
        airport.setOpStatus(opStatus);
        airport.setStrategicLevel(strategicLevel);
        airport.setAirspace(airspace);
        em.merge(airport);
        System.out.println("rpb.editAirport(): Airport updated!");
        em.flush();
    }

    @Override
    public void tryDeleteAirport(Airport airport) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:airport or r.dest =:airport");
        q1.setParameter("airport", airport);
        if (!q1.getResultList().isEmpty()) {
            System.out.println("rpb.deleteAirport(): Cannot delete. The airport " + airport.getIATA() + " is linked with a route.");
            throw new Exception("Cannot delete. The airport " + airport.getIATA() + " is linked with a route.");
        } else {
            System.out.println("rpb.deleteAirport(): Airport " + airport.getIATA() + " can be deleted!");
        }
    }

    @Override
    public boolean tryDeleteAirportList(List<Airport> airportList) throws Exception {
        for (Airport a : airportList) {
            tryDeleteAirport(a);
        }
        return true;
    }

    @Override
    public List<Airport> canDeleteAirportList() {
        Query q1 = em.createQuery("SELECT a FROM Airport a");
        List<Airport> apList = q1.getResultList();
        List<Airport> listCopy = q1.getResultList();
        Airport ap = new Airport();
        for (int i = 0; i < apList.size(); i++) {
            ap = apList.get(i);
            Query q2 = em.createQuery("SELECT r FROM Route r where r.origin =:airport or r.dest =:airport").setParameter("airport", ap);
            if (!q2.getResultList().isEmpty()) {
//                System.out.println(ap.toString());
                listCopy.remove(ap);
            }
        }
        return listCopy;
    }

    @Override
    public List<Airport> cannotDeleteAirportList() {
        Query q1 = em.createQuery("SELECT a FROM Airport a");
        List<Airport> apList = (List<Airport>) q1.getResultList();
        List<Airport> apList2 = new ArrayList<>();
        for (Airport ap : apList) {
            Query q2 = em.createQuery("SELECT r FROM Route r where r.origin =:airport or r.dest =:airport");
            q2.setParameter("airport", ap);
            if (!q2.getResultList().isEmpty()) {
                apList2.add(ap);
            }
        }
        return apList2;
    }

    @Override
    public void deleteAirport(Airport airport) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:airport or r.dest =:airport");
        q1.setParameter("airport", airport);
        if (!q1.getResultList().isEmpty()) {
            System.out.println("rpb.deleteAirport(): Cannot delete. The airport " + airport.getIATA() + " is linked with a route.");
            throw new Exception("Cannot delete. The airport " + airport.getIATA() + " is linked with a route.");
        } else {
            airport = em.merge(airport);
            em.remove(airport);
            em.flush();
            System.out.println("rpb.deleteAirport(): Airport " + airport.getIATA() + " is deleted!");
            System.out.println("1");
        }
    }

    @Override
    public void deleteAirportList(List<Airport> airportList) throws Exception {
        for (Airport a : airportList) {
            deleteAirport(a);
        }
    }

    @Override
    public Airport viewAirport(String IATA) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport == null) {
            throw new Exception("Airport does not exist.");
        }
        return airport;
    }

    @Override
    public List<Airport> viewAllAirport() {
        Query q1 = em.createQuery("SELECT a FROM Airport a");
        List<Airport> airportList = (List) q1.getResultList();
        if (airportList.isEmpty()) {
            System.out.println("rpb.viewAllAirport(): No airport has been added.");
        } else {
            System.out.println("rpb.viewAllAirport(): Airport list exists.");
        }
        return airportList;
    }

    @Override
    public List<Route> viewApAsOriginRoute(Airport airport) {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:airport");
        q1.setParameter("airport", airport);
        return q1.getResultList();
    }

    @Override
    public List<Route> viewApAsDestRoute(Airport airport) {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.dest =:airport");
        q1.setParameter("airport", airport);
        return q1.getResultList();
    }

    @Override
    public void checkRouteExist(String originIATA, String destIATA) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest").setParameter("origin", origin).setParameter("dest", dest);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Route " + origin.getIATA() + " - " + dest.getIATA() + " already exist.");
        }
    }

    @Override
    public void addRoute(String originIATA, String destIATA, Double distance) throws Exception {
        // Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare
        System.out.println("rpb.addRoute(): Add route " + originIATA + "-" + destIATA);
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        route = new Route();
        route.create(origin, dest, distance);
        em.persist(route);
        em.flush();
    }

    @Override
    public void editRouteBasic(String originIATA, String destIATA, Double distance, AircraftType acType, Double blockhour) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        route.setDistance(distance);
        route.setAcType(acType);
        route.setBlockhour(blockhour);
        em.merge(route);
        em.flush();
    }

    @Override
    public void editRouteFare(String originIATA, String destIATA, Double basicScFare, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception {
        System.out.println("rpb.editRouteFare(): passed parameter in.");
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        route.setBasicScFare(basicScFare);
        route.setBasicBcFare(basicBcFare);
        route.setBasicEcFare(basicEcFare);
        route.setBasicFcFare(basicFcFare);
        route.setBasicPecFare(basicPecFare);
        em.merge(route);
        em.flush();
    }

    @Override
    public void editRoute(String originIATA, String destIATA, Double distance, AircraftType acType, Double blockhour, Double basicScFare, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception {
        System.out.println("rpb.editRoute(): passed parameter in.");
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        route.setDistance(distance);
        route.setAcType(acType);
        route.setBlockhour(blockhour);
        route.setBasicScFare(basicScFare);
        route.setBasicBcFare(basicBcFare);
        route.setBasicEcFare(basicEcFare);
        route.setBasicFcFare(basicFcFare);
        route.setBasicPecFare(basicPecFare);
        em.merge(route);
        em.flush();
    }

    public List<AircraftType> checkFeasibleAc(Route route) {
        Double distance = route.getDistance();
        Query q1 = em.createQuery("SELECT ac FROM AircraftType ac WHERE ac.maxDistance >=:distance").setParameter("distance", distance);
        List<AircraftType> actList = q1.getResultList();
        if (actList.isEmpty()) {
            return actList;
        } else {
            List<AircraftType> actListCopy = q1.getResultList();
            for (AircraftType a : actList) {
                if (a.getAircraft().isEmpty()) {
                    actListCopy.remove(a);
                }
            }
            return actListCopy;
        }

    }

//    public boolean checkAirportFeasible(Route route, List<AircraftType> actList) {
//        
//    }
    @Override
    public List<Route> viewAllRoute() {
        Query q1 = em.createQuery("SELECT r FROM Route r");
        List<Route> routeList = (List) q1.getResultList();
        if (routeList.isEmpty()) {
            System.out.println("rpb.viewAllRoute(): No route has been added.");
        } else {
            System.out.println("rpb.viewAllRoute(): Return route list.");
        }
        return routeList;
    }

    @Override
    public Route viewRoute(String originIATA, String destIATA) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        return route;
    }

    @Override
    public void deleteRoute(String originIATA, String destIATA) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        em.remove(route);
        em.flush();
    }

    @Override
    public void deleteRouteList(List<Route> routeList) throws Exception {
        for (Route r : routeList) {
            Query q1 = em.createQuery("SELECT r FROM Route r WHERE r.id=:id").setParameter("id", r.getId());
            em.remove(q1.getSingleResult());
        }
        em.flush();
    }

    @Override
    public List<Route> canDeleteRouteList() {
        Query q1 = em.createQuery("SELECT r FROM Route r");
        List<Route> rList = q1.getResultList();
        List<Route> rListCopy = q1.getResultList();
        Route r = new Route();
        for (int i = 0; i < rList.size(); i++) {
            r = rList.get(i);
            Query q2 = em.createQuery("SELECT f FROM FlightFrequency f where f.route=:route").setParameter("route", r);
            if (!q2.getResultList().isEmpty()) {
                rListCopy.remove(r);
            }
        }
        return rListCopy;
    }

    @Override
    public Route findRoute(Long routeID) {
//        em.refresh(route);
        System.out.println("rpb.findRoute(): routeID = " + routeID);
        System.out.println("rpb.findRoute(): " + em.find(Route.class, routeID).toString());
        return em.find(Route.class, routeID);
    }

    @Override
    public List<String> viewAllRouteString() {
        Query q1 = em.createQuery("SELECT r FROM Route r");
        List<Route> routeList = (List) q1.getResultList();
        List<String> routeListString = new ArrayList<String>();
        if (routeList.isEmpty()) {
            return null;
        } else {
            for (Route aroute : routeList) {
                String routeString = aroute.getOrigin() + " - " + aroute.getDest();
                routeListString.add(routeString);
            }
            return routeListString;
        }
    }

}
