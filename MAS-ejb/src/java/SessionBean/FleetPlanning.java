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
    
    public void createAircraftType (String type) throws MASException{
    	AircraftType at = new AircraftType();
        if (ah.hasAircraftType(type)){
            MASException e = new MASException("AT01");
            throw e;
        }
        at.setAircraftType(type);
    }

    public void setAircraftType (AircraftType at, String manufacturer, 
        float MaxDis, float cruiseSpeed, float curiseAltitude,
        float AircraftLength, float wingspan){

    }

    public void setSeatInformationforAircraftType (AircraftType at, 
        int fcSeatNo, int bcSeatNo, int pecSeatNo, int ecSeatNo){
        
    }
    
    public void createAircraft (){}

    
}
