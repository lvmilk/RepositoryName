/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

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

    public boolean addAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception;

    public void editAirport(String IATA, String airportName, String cityName, String countryCode, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) throws Exception;

    public void deleteAirport(Airport airport) throws Exception;

    public void deleteAirportList(List<Airport> airportList) throws Exception;

    public Airport viewAirport(String IATA) throws Exception;

    public List<Airport> viewAllAirport();

    public boolean addRoute(String originIATA, String destIATA, Double distance) throws Exception;

    public void editRoute(String originIATA, String destIATA, Double distance) throws Exception;

//    public void editRouteFare(String originIATA, String destIATA, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception;
    public void deleteRoute(String originIATA, String destIATA) throws Exception;

    public List<Route> viewAllRoute();

    public Route viewRoute(String originIATA, String destIATA) throws Exception;

}
