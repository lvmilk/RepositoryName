/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeanMAS.APS;

import EntityMAS.APS.Airport;
import EntityMAS.APS.Route;
import Exception.MASException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author victor
 */
@Remote
public interface RoutePlanningInterface {

    public void start();

    public Airport createAirport(String IATA, String AirportName, String CityName, 
            String Spec, String CountryCode, String TimeZone, 
            String OperationalStatus, String StrategicLevel, String Airspace) throws MASException;

    public void deleteAirport(long id)throws MASException;

    public Airport updateAirport(long id, String IATA, String AirportName, String CityName,
            String Spec, String CountryCode, String TimeZone,
            String OperationalStatus, String StrategicLevel, String Airspace) throws MASException;

    public ArrayList<String> listAirport();

    public Route createRoute(long idOrg, long idDst, Double distance) throws MASException;

    public void deleteRoute(long id) throws MASException;



    public ArrayList<String> listRoute();

    public Route updateRoute(long id, long idOrg, long idDst, Double distance) throws MASException;

    public Route createRoute(String orgIATA, String dstIATA, Double distance) throws MASException;
    
}
