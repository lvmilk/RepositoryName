/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityHandler;

import Entity.Aircraft_;
import Entity.AircraftType_;
import Exception.MASException;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public interface AircraftHandlerInterface {
    //For AircraftType
    public void addAircraftType(AircraftType_ at);
    public boolean deleteAircraftType(AircraftType_ at);
    public void updateAircraftType(AircraftType_ at);
    public AircraftType_ findAircraftType (long id) throws MASException;
    public ArrayList<AircraftType_> findAircraftType(String type);
    public ArrayList<AircraftType_> listAircraftType ();
    public boolean hasAircraftType(String type);
    
    //For Aircraft
    public void addAircraft(Aircraft_ af);
    public void updateAircraft(Aircraft_ af);
    public boolean deleteAircraft(Aircraft_ af);
    public Aircraft_ findAircraft (long id) throws MASException;
    public ArrayList<AircraftType_> findAircraft(String registrationNumber);
    public boolean hasAircraft(String registrationNumber);
    public ArrayList<AircraftType_> listAircraft ();


}
