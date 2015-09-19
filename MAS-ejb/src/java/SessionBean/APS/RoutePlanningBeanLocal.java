///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package SessionBean.APS;
//
//import Entity.APS.Airport;
//import Entity.APS.Route;
//import java.util.List;
//import javax.ejb.Local;
//
///**
// *
// * @author Xu
// */
//@Local
//public interface RoutePlanningBeanLocal {
//
//    public void addAirport(String IATA, String airportName, String cityName, String countryCode, String timeZone) throws Exception;
//
//    public void editAirport(String IATA, String airportName, String cityName, String countryCode, String timeZone) throws Exception;
//
//    public void deleteAirport(String IATA) throws Exception;
//
//    public Airport viewAirport(String IATA) throws Exception;
//
//    public List<Airport> viewAllAirport();
//
//    public void addRoute(String originIATA, String destIATA, Double distance, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception;
//
//    public void editRouteFare(String originIATA, String destIATA, Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare) throws Exception;
//
//    public void deleteRoute(String originIATA, String destIATA) throws Exception;
//
//    public List<Route> viewAllRoute();
//
//    public Route viewRoute(String originIATA, String destIATA) throws Exception;
//
//}
