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
    public void addAirport(String IATA, String airportName, String cityName, String countryName, String spec, String timeZone, String opStatus, String strategicLevel, String airspace, Double latitude, Double longitude) throws Exception {
        airport = em.find(Airport.class, IATA);
        if (airport != null) {
            System.out.println("rpb.addAirport(): Airport " + airport.getIATA() + " exists.");
            throw new Exception("Airport " + airport.getIATA() + " exists.");
        }
        boolean checkIATA = true;
        if (IATA.length() != 3) {
            checkIATA = false;
        } else {
            for (int i = 0; i < IATA.length(); i++) {
                char c = IATA.charAt(i);
                if (!Character.isLetter(c)) {
                    checkIATA = false;
                } else if (!Character.isUpperCase(c)) {
                    checkIATA = false;
                }
            }
        }
        if (!checkIATA) {
            throw new Exception("Airport IATA: should be three UPPERCASE alphabets.");
        }
        boolean checkLat = true;
        if (!(latitude <= 90 && latitude >= -90)) {
            checkLat = false;
        }
        if (!checkLat) {
            throw new Exception("Latitude: please enter a latitude degree with in range -90 to 90.");
        }
        boolean checkLon = true;
        if (!(longitude <= 180 && longitude >= -180)) {
            checkLon = false;
        }
        if (!checkLon) {
            throw new Exception("Longitude: please enter a longitude degree with in range -180 to 180.");
        }

        airport = new Airport();
        airport.create(IATA, airportName, cityName, countryName, spec, timeZone, opStatus, strategicLevel, airspace, latitude, longitude);
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
    public List<Airport> viewHubAirport() {
        Query q1 = em.createQuery("SELECT a FROM Airport a");
        List<Airport> airportList = (List) q1.getResultList();
        List<Airport> hubList = new ArrayList<>();
        for (Airport ap : airportList) {
            if (ap.getStrategicLevel().equalsIgnoreCase("Hub")) {
                hubList.add(ap);
            }
        }
        String hub = "";
        for (Airport a : hubList) {
            hub += a.getIATA() + " ";
        }
        System.out.println("rpb.viewHubAirport(): hub airport(s) " + hub);
        return hubList;
    }

    @Override
    public boolean isHubAirport(String IATA) {
        Airport ap = em.find(Airport.class, IATA);
        if (viewHubAirport().contains(ap)) {
            System.out.println("rpb.isHubAirport(): " + IATA + " is hub airport");
            return true;
        }
        System.out.println("rpb.isHubAirport(): " + IATA + " is not hub airport");
        return false;
    }

    //must ensure the airport exist, for flight managed bean to get singapore airport
    @Override
    public Airport findAirport(String IATA) {
        Airport ap = em.find(Airport.class, IATA);
        return ap;
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
    public void addRoute(String originIATA, String destIATA, Double distance, Double blockhour) throws Exception {
        // Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare
        System.out.println("rpb.addRoute(): Add route " + originIATA + "-" + destIATA);
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest").setParameter("origin", origin).setParameter("dest", dest);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Route already exists.");
        }
        route = new Route();
        route.create(origin, dest, distance, blockhour);
        em.persist(route);
        em.flush();
    }

    @Override
    public Double maxBlockHour(Double distance) {
        return distance / 700;
    }

    @Override
    public Double minBlockHour(Double distance) {
        return distance / 1000;
    }

    @Override
    public Double calRouteDistance(String originIATA, String destIATA) {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Double distance = pointDistance(origin.getLat(), origin.getLon(), dest.getLat(), dest.getLon(), 'K');
        System.out.println("rpb.calRouteDistance(): distance between Airport " + origin.getIATA() + " and Airport " + dest.getIATA() + " is " + distance);
        return distance;
    }

    public double pointDistance(Double lat1, Double lon1, Double lat2, Double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return dist;
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    @Override
    public void editRouteBasic(String originIATA, String destIATA, Double distance, AircraftType acType, Double blockhour) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest");
        q1.setParameter("origin", origin);
        q1.setParameter("dest", dest);
        if (q1.getResultList().isEmpty()) {
            System.out.println("RoutePlanningBean: editRouteBasic(): Route does not exist.");
            throw new Exception("RoutePlanningBean: editRouteBasic(): Route does not exist.");
        }
        route = (Route) q1.getResultList().get(0);
        if (!checkFeasibleAcByDis(route).contains(acType)) {
            throw new Exception("Infeasible Aircraft Type: The aircraft type max distance is shorter than the route distance.");
        } else if (!checkFeasibleAcByAsp(route).contains(acType)) {
            throw new Exception("Infeasible Aircraft Type: The aircraft type minimum airspace is higher than origin/destination airport of the route.");
        } else {
            route.setDistance(distance);
            route.setAcType(acType);
            route.setBlockhour(blockhour);
            em.merge(route);
            em.flush();
        }
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
    public List<AircraftType> checkFeasibleAcByDis(Route route) {
        Double distance = route.getDistance();
        Query q1 = em.createQuery("SELECT ac FROM AircraftType ac WHERE ac.maxDistance >=:distance").setParameter("distance", distance);
        List<AircraftType> actList = q1.getResultList();
//        if (actList.isEmpty()) {
//            return actList;
//        } else {
//            List<AircraftType> actListCopy = q1.getResultList();
//            for (AircraftType a : actList) {
//                if (a.getAircraft().isEmpty()) {
//                    actListCopy.remove(a);
//                }
//            }
//            return actListCopy;
//        }
        return actList;
    }

    @Override
    public List<AircraftType> checkFeasibleAcByAsp(Route route) {
        Query q1 = em.createQuery("SELECT ac FROM AircraftType ac");
        List<AircraftType> acList = q1.getResultList();
        List<AircraftType> acListCopy = q1.getResultList();
        String aspOrg = route.getOrigin().getAirspace();
        String aspDest = route.getDest().getAirspace();

        for (AircraftType ac : acList) {
            String aspAc = ac.getMinAirspace();
            if (aspAc.charAt(1) > aspOrg.charAt(1) || aspAc.charAt(1) > aspDest.charAt(1) || aspAc.charAt(0) > aspOrg.charAt(0) || aspAc.charAt(0) > aspDest.charAt(0)) {
                acListCopy.remove(ac);
            }
        }
        return acListCopy;
    }

    @Override
    public List<AircraftType> feasibleAc(Route route) {
        List<AircraftType> list1 = checkFeasibleAcByAsp(route);
        List<AircraftType> list2 = checkFeasibleAcByDis(route);
        List<AircraftType> list = new ArrayList<>();
        for (AircraftType ac : list1) {
            if (list2.contains(ac)) {
                list.add(ac);
            }
        }
        return list;
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
    public void deleteRoute(String originIATA, String destIATA) throws Exception {
        Airport origin = em.find(Airport.class, originIATA);
        Airport dest = em.find(Airport.class, destIATA);
        Query q1 = em.createQuery("SELECT r FROM Route r where r.origin =:origin and r.dest =:dest").setParameter("origin", origin).setParameter("dest", dest);
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
