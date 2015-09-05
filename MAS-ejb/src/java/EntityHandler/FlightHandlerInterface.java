/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityHandler;

import Entity.Airport;
import Entity.Flight;
import Entity.GenericFlight;
import Entity.ODPair;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public interface FlightHandlerInterface {
    
    //For airport
    public void addAirport (Airport ap);
    public boolean deleteAirport (Airport ap);
    public void updateAirport (Airport ap);
    public ArrayList<Airport> findAirport (String IATA);
    public ArrayList<Airport> listAirport ();
    public boolean hasAirport(String IATA);
    
    //For ODPairs
    public void addODPair (ODPair od);
    public boolean deleteODPair (ODPair od);
    public void updateODPair (ODPair od);
    public ArrayList<ODPair> findODPair (String orgIATA, String dstIATA);
    public ArrayList<ODPair> listODPair ();
    public boolean hasODPair(String orgIATA, String dstIATA);
    
    //For GenericFlight
    public void addGenericFlight (GenericFlight gf);
    public boolean deleteGenericFlight (GenericFlight gf);
    public void updateGenericFlight (GenericFlight gf);
    public ArrayList<GenericFlight> findGenericFlight (String flightNumber);
    public ArrayList<GenericFlight> findGenericFlight (String orgIATA, String dstIATA);
    public ArrayList<GenericFlight> listGenericFligth ();
    public boolean hasGenericFlight (String flightNumber);
    public boolean hasGenericFlight (String orgIATA, String dstIATA);

    //For Flight
    public void addFlight(Flight f);
    public boolean deleteFlight(Flight f);
    public void updateFlight (Flight f);
    public ArrayList<Flight> findFlight (String flightNumber, String date);
    public ArrayList<Flight> findFlight (String orgIATA, String dstIATA, String date);
    public ArrayList<Flight> listFlight ();
    public boolean hasFlight (String flightNumber, String date);
    public boolean hasFlight (String orgIATA, String dstIATA, String date);   
}
