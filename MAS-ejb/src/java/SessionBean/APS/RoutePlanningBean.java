/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Airport;
import Entity.APS.Route;
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
    public void addAirport(String IATA, String airportName, String cityName, String countryCode, String timeZone) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport != null) {
            throw new Exception("Airport exists.");
        }
        airport = new Airport();
        airport.create(IATA, airportName, cityName, countryCode, timeZone);
        em.persist(airport);
        em.flush();
    }

    @Override
    public void editAirport(String IATA, String airportName, String cityName, String countryCode, String timeZone) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport == null) {
            throw new Exception("Airport does not exist.");
        }
        airport.setAirportName(airportName);
        airport.setCityName(cityName);
        airport.setCountryCode(countryCode);
        airport.setTimeZone(timeZone);
        em.merge(airport);
        em.flush();
    }

    @Override
    public void deleteAirport(String IATA) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport == null) {
            throw new Exception("Airport does not exist.");
        }
        if (airport.getRouteList().isEmpty()) {
            throw new Exception("Cannot delete. This airport is linked with a route.");
        } else {
            em.remove(airport);
            em.flush();
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
        Query q1 = em.createNamedQuery("SELECT a FROM Airport a");
        List<Airport> airportList = (List) q1.getResultList();
        if (airportList.isEmpty()) {
            System.out.println("No airport has been added.");
        } else {
            System.out.println("Airport list exists");
        }
        return airportList;
    }

    @Override
    public void addRoute(String originIATA, String destIATA, Double distance, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.originIATA :=originIATA and r.destIATA :=destIATA");
        q1.setParameter("originIATA", originIATA);
        q1.setParameter("destIATA", destIATA);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Route exists.");
        }
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        if (origin == null) {
            throw new Exception("Origin airport does not exist.");
        }
        if (dest == null) {
            throw new Exception("Destination airport does not exist.");
        }
        route = new Route();
        route.create(originIATA, destIATA, distance, basicFcFare, basicBcFare, basicPecFare, basicEcFare);
        route.setOrigin(origin);
        route.setDest(dest);
        em.persist(route);
        em.flush();
        origin.getRouteList().add(route);
        dest.getRouteList().add(route);
        em.merge(origin);
        em.flush();
    }

    @Override
    public void editRouteFare(String originIATA, String destIATA, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.originIATA :=originIATA and r.destIATA :=destIATA");
        q1.setParameter("originIATA", originIATA);
        q1.setParameter("destIATA", destIATA);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        route.setBasicBcFare(basicBcFare);
        route.setBasicEcFare(basicEcFare);
        route.setBasicFcFare(basicFcFare);
        route.getBasicPecFare();
        em.merge(route);
        em.flush();
    }

    @Override
    public void deleteRoute(String originIATA, String destIATA) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.originIATA :=originIATA and r.destIATA :=destIATA");
        q1.setParameter("originIATA", originIATA);
        q1.setParameter("destIATA", destIATA);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        origin.getRouteList().remove(route);
        dest.getRouteList().remove(route);
        em.merge(origin);
        em.merge(dest);
        em.remove(route);
        em.flush();
    }

    @Override
    public List<Route> viewAllRoute() {
        Query q1 = em.createNamedQuery("SELECT r FROM Route r");
        List<Route> routeList = (List) q1.getResultList();
        if (routeList.isEmpty()) {
            System.out.println("No route has been added.");
        } else {
            System.out.println("Route list exists");
        }
        return routeList;
    }

    @Override
    public Route viewRoute(String originIATA, String destIATA) throws Exception {
        Query q1 = em.createQuery("SELECT r FROM Route r where r.originIATA :=originIATA and r.destIATA :=destIATA");
        q1.setParameter("originIATA", originIATA);
        q1.setParameter("destIATA", destIATA);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        return route;
    }

}
