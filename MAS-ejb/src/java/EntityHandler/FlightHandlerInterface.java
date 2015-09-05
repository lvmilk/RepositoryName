/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityHandler;

import Entity.Airport;
import Entity.ODPair;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public interface FlightHandlerInterface {
    
    //For airport
    public void addAirport (Airport ap);
    public void deleteAirport (Airport ap);
    public ArrayList<Airport> findAirport (String IATA);
    public ArrayList<Airport> listAirport ();
    public boolean hasAirport(String IATA);
    
    //For ODPairs
    public void addODPair (ODPair od);
    public void deleteODPair (ODPair od);
    public ArrayList<ODPair> findODPair (String orgIATA, String dstIATA);
    public ArrayList<ODPair> listODPair ();
    public boolean hasODPair(String orgIATA, String dstIATA);
    
    //For GenericFlight
    public 
    
}
