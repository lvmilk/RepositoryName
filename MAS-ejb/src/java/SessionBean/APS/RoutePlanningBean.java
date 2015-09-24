/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

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
    public void addAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport != null) {
            System.out.println("rpb.addAirport(): Airport " + airport.getIATA() + " exists.");
            throw new Exception("Airport " + airport.getIATA() + " exists.");
        }
        airport = new Airport();
        airport.create(IATA, airportName, cityName, countryCode, spec, timeZone, opStatus, strategicLevel, airspace);
        em.persist(airport);
        em.flush();
        System.out.println("rpb.addAirport(): Airport " + airport.getIATA() + " added!");
    }

    @Override
    public void editAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport == null) {
            throw new Exception("Airport does not exist.");
        }
        airport.setAirportName(airportName);
        airport.setCityName(cityName);
        airport.setCountryCode(countryCode);
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
    public boolean addRoute(String originIATA, String destIATA, Double distance, Double blockhour) throws Exception {
        // Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare
        System.out.println("Origin IATA is: " + originIATA);
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        if (origin == null) {
            throw new Exception("Origin airport does not exist.");
        }
        if (dest == null) {
            throw new Exception("Destination airport does not exist.");
        }
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Route has already been added.");
        }
        route = new Route();
        route.create(origin, dest, distance, blockhour);
        em.persist(route);
        em.flush();
        return true;
//        Query q0 = em.createQuery("SELECT a FROM Airport a where a.IATA =:originIATA");
//        q0.setParameter("originIATA", originIATA);
//        Query q2 = em.createQuery("SELECT a FROM Airport a where a.IATA =:destIATA");
//        q2.setParameter("destIATA", destIATA);
//        if (q0.getResultList().isEmpty()) {
//            throw new Exception("Origin airport does not exist.");
//        }
//        Airport origin = (Airport)q0.getResultList().get(0);
//        if (q2.getResultList().isEmpty()) {
//            throw new Exception("Destination airport does not exist.");
//        }
//        Airport dest = (Airport)q2.getResultList().get(0);
    }

    @Override
    public void editRoute(String originIATA, String destIATA, Double distance) throws Exception {
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
        em.merge(route);
        em.flush();
    }

    @Override
    public void editRouteFare(String originIATA, String destIATA, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.originIATA =:originIATA and r.destIATA =:destIATA");
        q1.setParameter("originIATA", originIATA);
        q1.setParameter("destIATA", destIATA);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        route.setBasicBcFare(basicBcFare);
        route.setBasicEcFare(basicEcFare);
        route.setBasicFcFare(basicFcFare);
        route.setBasicPecFare(basicPecFare);
        em.merge(route);
        em.flush();
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
