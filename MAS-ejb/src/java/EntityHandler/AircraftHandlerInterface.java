/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EntityHandler;

import Entity.Aircraft;
import Entity.AircraftType;
import java.util.ArrayList;

/**
 *
 * @author victor
 */
public interface AircraftHandlerInterface {

    public void addAircraftType(AircraftType at);

    public boolean deleteAircraftType(AircraftType at);

    public ArrayList<AircraftType> findAircraftType(String type);

    public boolean hasAircraftType(String type);

    public void addAircraft(Aircraft af);

    public boolean deleteAircraft(Aircraft af);

    public ArrayList<AircraftType> findAircraft(String registrationNumber);

    public boolean hasAircraft(String registrationNumber);

}
