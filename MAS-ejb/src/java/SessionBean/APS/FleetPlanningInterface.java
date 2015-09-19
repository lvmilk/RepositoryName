/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;


import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Exception.MASException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author victor
 */
@Remote
public interface FleetPlanningInterface {
    public void start();
    
    public AircraftType createAircraftType (String type, String manufacturer,Double MaxDis,Double cruiseSpeed, Double cruiseAltitude,Double AircraftLength, Double wingspan,String airspace) throws MASException;
    public void deleteAircraftType (long id) throws MASException;
    

    public ArrayList listAircraftType();

    public AircraftType updateAircraftType(long id, String type, String manufacturer, Double MaxDis, Double cruiseSpeed, Double cruiseAltitude, Double AircraftLength, Double wingspan, String airspace) throws MASException;

    public Aircraft createAircraft(String registrationNumber, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate, long aircraftTypeId) throws MASException;

    public void deleteAircraft(long id) throws MASException;

    public Aircraft updateAircraft(long id, String registrationNumber, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate, long aircraftTypeId) throws MASException;

    public ArrayList<String> listAircraft();

    public Aircraft createAircraft(String registrationNumber, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate, String aircraftTypeName) throws MASException;
    
}
