/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeanMAS.APS;

import EntityMAS.APS.Aircraft;
import EntityMAS.APS.Airport;
import EntityMAS.APS.Route;
import Exception.MASException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.ManyToOne;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class RoutePlanning implements RoutePlanningInterface{
    
    @PersistenceContext(unitName="MAS-ejbPU")
    private EntityManager em;
    private RouteHandler handler;
    
    @Override
    public void start(){
        handler = new RouteHandler();
        handler.setEm(em);
    }

    @Override
    public Airport createAirport(String IATA, String AirportName, String CityName,
            String Spec, String CountryCode, String TimeZone,
            String OperationalStatus, String StrategicLevel, String Airspace) throws MASException{
        if (handler.hasAirport(IATA)){
            throw new MASException("AP01");
        }
        
        Airport ap = new Airport();
        if (IATA.length() > 0)
            ap.setIATA(IATA);
        if (AirportName.length() > 0)
            ap.setAirportName(AirportName);
        if (CityName.length() > 0)
            ap.setCityName(CityName);
        if (Spec.length() > 0)
            ap.setSpec(Spec);
        if (CountryCode.length() > 0)
            ap.setCountryCode(CountryCode);
        if (TimeZone.length() > 0)
            ap.setTimeZone(TimeZone);
        if (OperationalStatus.length() > 0)
            ap.setOperationalStatus(OperationalStatus);
        if (StrategicLevel.length() > 0)
            ap.setStrategicLevel(StrategicLevel);
        if (Airspace.length() > 0)
            ap.setAirspace(Airspace);
        
        handler.addAirport(ap);
        System.out.println("Airport Added!");
        return ap;
    }
    
    @Override
    public void deleteAirport(long id)throws MASException{
        Airport ap = handler.findAirport(id);
        handler.deleteAirport(ap);
        System.out.println("Airport Deleted!");
    }
    
    @Override
    public Airport updateAirport(long id, String IATA, String AirportName, String CityName,
            String Spec, String CountryCode, String TimeZone,
            String OperationalStatus, String StrategicLevel, String Airspace) throws MASException{
        
        Airport ap = handler.findAirport(id);
        
        if (IATA.length() > 0)
            ap.setIATA(IATA);
        if (AirportName.length() > 0)
            ap.setAirportName(AirportName);
        if (CityName.length() > 0)
            ap.setCityName(CityName);
        if (Spec.length() > 0)
            ap.setSpec(Spec);
        if (CountryCode.length() > 0)
            ap.setCountryCode(CountryCode);
        if (TimeZone.length() > 0)
            ap.setTimeZone(TimeZone);
        if (OperationalStatus.length() > 0)
            ap.setOperationalStatus(OperationalStatus);
        if (StrategicLevel.length() > 0)
            ap.setStrategicLevel(StrategicLevel);
        if (Airspace.length() > 0)
            ap.setAirspace(Airspace);
        
        handler.updateAirport(ap);
        System.out.println("Airport Updated!");
        return ap;
    }
    
    @Override
    public ArrayList<String> listAirport (){
        List<Airport> aps = handler.listAirport();
        ArrayList result = new ArrayList<String>();
        for (Airport ap:aps ){
            
            //for testing
            System.out.println(ap.toString());
            
            result.add(ap.toString());
        }
        return result;
    }
    
//    @ManyToOne
//    private Airport origin;
//    @ManyToOne
//    private Airport destination;
//    
//    private Double distance;
//    private Double basicFirstClassFare;
//    private Double basicBusinessClassFare;
//    private Double basicPremiumEconomyClassFare;
//    private Double basicEconomyClassFare;
//    
//    private boolean canEBP;
    @Override
    public Route createRoute(long idOrg, long idDst, Double distance) throws MASException{
        Airport org = handler.findAirport(idOrg);
        Airport dst = handler.findAirport(idDst);
        
        //for test
        System.out.println("Create Route: org = " + org);
        System.out.println("Create Route: dst = " + dst);
        if (handler.hasRoute(org.getIATA(), dst.getIATA())){
            throw new MASException("RT01");
        }
        Route rt = new Route();
        rt.setOrigin(org);
        rt.setDestination(dst);
        if (distance > 0)
            rt.setDistance(distance);
        
        handler.addRoute(rt);
        System.out.println("Airport Added!");
        return rt;
    }
    
    @Override
    public Route createRoute(String orgIATA, String dstIATA, Double distance) throws MASException{
        Airport org = handler.findAirport(orgIATA).get(0);
        Airport dst = handler.findAirport(dstIATA).get(0);
        
        //for test
        System.out.println("Create Route: org = " + org);
        System.out.println("Create Route: dst = " + dst);
        if (handler.hasRoute(org.getIATA(), dst.getIATA())){
            throw new MASException("RT01");
        }
        Route rt = new Route();
        rt.setOrigin(org);
        rt.setDestination(dst);
        if (distance > 0)
            rt.setDistance(distance);
        
        handler.addRoute(rt);
        System.out.println("Airport Added!");
        return rt;
    }
    
    @Override
    public void deleteRoute(long id)throws MASException{
        Route rt = handler.findRoute(id);
        handler.deleteRoute(rt);
        System.out.println("Route Deleted!");
    }
    
    @Override
    public Route updateRoute(long id, long idOrg, long idDst, Double distance) throws MASException{
        
        Route rt = handler.findRoute(id);
        Airport org = handler.findAirport(idOrg);
        Airport dst = handler.findAirport(idDst);
        //for test
        System.out.println("Update Route: org = " + org);
        System.out.println("Update Route: dst = " + dst);
        rt.setOrigin(org);
        rt.setDestination(dst);
        if (distance > 0)
            rt.setDistance(distance);
        
        handler.updateRoute(rt);
        System.out.println("Route Updated!");
        return rt;
    }
    
    @Override
    public ArrayList<String> listRoute (){
        List<Route> rts = handler.listRoute();
        ArrayList result = new ArrayList<String>();
        for (Route rt: rts ){
            
            //for testing
            System.out.println(rt.toString());
            
            result.add(rt.toString());
        }
        return result;
    }
}
