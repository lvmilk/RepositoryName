/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.APS.Airport;
import Entity.APS.Route;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface RoutePlanningBeanLocal {

    public void addAirport(String IATA, String airportName, String cityName, String countryName, String spec, String timeZone, String opStatus, String strategicLevel, String airspace, Double latitude, Double longitude) throws Exception;

//    public void addAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception;
   
    public void editAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception;

    public void deleteAirport(Airport airport) throws Exception;

    public void deleteAirportList(List<Airport> airportList) throws Exception;

    public Airport viewAirport(String IATA) throws Exception;

    public List<Airport> viewAllAirport();

    public void addRoute(String originIATA, String destIATA, Double distance, Double blockhour) throws Exception;

    public void editRouteBasic(String originIATA, String destIATA, Double distance, AircraftType acType, Double blockhour) throws Exception;

    public void editRouteFare(String originIATA, String destIATA, Double basicScFare, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception;

    public void deleteRoute(String originIATA, String destIATA) throws Exception;

    public List<Route> viewAllRoute();

    public Route viewRoute(String originIATA, String destIATA) throws Exception;

    public List<Route> viewApAsOriginRoute(Airport airport);

    public List<Route> viewApAsDestRoute(Airport airport);

    public void tryDeleteAirport(Airport airport) throws Exception;

    public boolean tryDeleteAirportList(List<Airport> airportList) throws Exception;

    public List<String> viewAllRouteString();

    public List<Airport> canDeleteAirportList();

    public List<Airport> cannotDeleteAirportList();

    public Route findRoute(Long routeID);

    public List<Route> canDeleteRouteList();

    public void deleteRouteList(List<Route> routeList) throws Exception;

    public void checkRouteExist(String originIATA, String destIATA) throws Exception;

    public List<AircraftType> checkFeasibleAcByDis(Route route);

    public List<AircraftType> checkFeasibleAcByAsp(Route route);

    public List<AircraftType> feasibleAc(Route route);

    public Airport findAirport(String IATA);

    public List<Airport> viewHubAirport();

    public boolean isHubAirport(String IATA);

    public Double calRouteDistance(String originIATA, String destIATA);

}
