/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import Entity.AircraftType;
import EntityHandler.AircraftHandlerInterface;
import Exception.MASException;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;

/**
 *
 * @author victor
 */
@Stateless
@LocalBean
public class FleetPlanning {

// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    AircraftHandlerInterface ah;
    
    public void createAircraftType (String type, String manufacturer) throws MASException{
    	AircraftType at = new AircraftType();
        if (ah.hasAircraftType(type)){
            MASException e = new MASException("AT01");
            throw e;
        }
        at.setAircraftType(type);
        at.setManufacturerName(manufacturer);
    }
    
    public void createAircraft (){}

    
}
