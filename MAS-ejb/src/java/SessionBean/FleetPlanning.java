/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean;

import Entity.Aircraft;
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
    
    private AircraftHandlerInterface ah;

    public AircraftHandlerInterface getAircraftHandler (){
        return ah;
    }
    
    public void createAircraftType (String type) throws MASException{
    	AircraftType at = new AircraftType();
        if (ah.hasAircraftType(type)){
            MASException e = new MASException("AT01");
            throw e;
        }
        at.setAircraftType(type);
    }

    public void setAircraftType (long id, String manufacturer, 
        float MaxDis, float cruiseSpeed, float cruiseAltitude,
        float AircraftLength, float wingspan) throws MASException{
        AircraftType af = ah.findAircraftType(id);
        if (manufacturer.length() > 0)
            af.setManufacturerName(manufacturer.toUpperCase());
        if (MaxDis > 0)
            af.setMaxDistance(new Double(MaxDis));
        if (cruiseSpeed > 0)
            af.setCruiseSpeed(new Double (cruiseSpeed));
        if (cruiseAltitude > 0)
            af.setCruiseAltitude(new Double (cruiseAltitude));
        if (AircraftLength > 0)
            af.setAircraftLength(new Double (AircraftLength));
        if (wingspan > 0)
            af.setWingspan(new Double (wingspan));
        
    }

    public void setSeatforAircraftType (long id){
    }
    
    public void createAircraft (String registrationNumber){
        Aircraft af = new Aircraft ();
    }

    
}
