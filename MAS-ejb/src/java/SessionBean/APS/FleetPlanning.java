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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author victor
 */
@Stateless
public class FleetPlanning implements FleetPlanningInterface {

// Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName="MAS-ejbPU")
    private EntityManager em;
    private AircraftHandler handler;
    @Override
    public void start(){
        handler = new AircraftHandler();
        handler.setEm(em);
    }

    public AircraftHandler getAircraftHandler (){
        return handler;
    }


    @Override
    public AircraftType createAircraftType (String type, String manufacturer, 
        Double MaxDis, Double cruiseSpeed, Double cruiseAltitude,
        Double AircraftLength, Double wingspan, String airspaceReq) throws MASException{
        if (handler.hasAircraftType(type)){
            throw new MASException("AT01");
        }
        AircraftType at = new AircraftType();
        if (type.length() > 0)
            at.setType(type);
        if (manufacturer.length() > 0)
            at.setManufacturerName(manufacturer.toUpperCase());
        if (MaxDis > 0)
            at.setMaxDistance(MaxDis);
        if (cruiseSpeed > 0)
            at.setCruiseSpeed(cruiseSpeed);
        if (cruiseAltitude > 0)
            at.setCruiseAltitude(cruiseAltitude);
        if (AircraftLength > 0)
            at.setAircraftLength(AircraftLength);
        if (wingspan > 0)
            at.setWingspan(wingspan);  
        if (airspaceReq.length() > 0)
            at.setMinAirspaceClassReq(airspaceReq);
        handler.addAircraftType(at);
        return at;  
    }

    public void setSeatforAircraftType (long id){
    }
    
    public void linkSeatMap (String seatMapId){
    }
    
    @Override
    public void deleteAircraftType (long id) throws MASException{
        AircraftType at = handler.findAircraftType(id);
        handler.deleteAircraftType(at);
        System.out.println("Aircraft type deleted!");
    }
    
    @Override
    public AircraftType updateAircraftType (long id, String type, String manufacturer, 
        Double MaxDis, Double cruiseSpeed, Double cruiseAltitude,
        Double AircraftLength, Double wingspan,String airspaceReq) throws MASException {
        AircraftType at = handler.findAircraftType(id);
        if (type.length() > 0)
            at.setType(type);
        if (manufacturer.length() > 0)
            at.setManufacturerName(manufacturer.toUpperCase());
        if (MaxDis > 0)
            at.setMaxDistance(MaxDis);
        if (cruiseSpeed > 0)
            at.setCruiseSpeed(cruiseSpeed);
        if (cruiseAltitude > 0)
            at.setCruiseAltitude(cruiseAltitude);
        if (AircraftLength > 0)
            at.setAircraftLength(AircraftLength);
        if (wingspan > 0)
            at.setWingspan(wingspan);
        if (airspaceReq.length() > 0)
            at.setMinAirspaceClassReq(airspaceReq);
        handler.updateAircraftType(at);
        System.out.println("Aircraft updated!");
        return at;  
    }
    
    @Override
    public ArrayList<String> listAircraftType (){
        List<AircraftType> ats = handler.listAircraftType();
        ArrayList result = new ArrayList<String>();
        for (AircraftType at:ats ){
            
            //for testing
            System.out.println(at.toString());
            
            result.add(at.toString());
        }
        return result;
    }
   
    
    @Override
    public Aircraft createAircraft (String registrationNumber, String serialNo, String status, 
            String firstFlyDate, String deliveryDate, String retireDate, long aircraftTypeId) throws MASException{
        if (handler.hasAircraft(registrationNumber)){
            throw new MASException("AF01");
        }
        Aircraft af = new Aircraft ();
        if (registrationNumber.length() > 0)
            af.setRegistrationNo(registrationNumber);
        if (serialNo.length() > 0)
            af.setSerialNo(serialNo);
        if (status.length() > 0)
            af.setStatus(status);
        if (firstFlyDate.length() > 0)
            af.setFirstFlyDate(firstFlyDate);
        if (deliveryDate.length() > 0)
            af.setDeliveryDate(deliveryDate);
        if (retireDate.length() > 0)
            af.setRetireDate(retireDate);
        AircraftType at = handler.findAircraftType(aircraftTypeId);
        if (at != null)
            af.setAircraftType(at);
        handler.addAircraft(af);
        System.out.println("Aircraft added!");
        return af;
    }
    
    @Override
    public Aircraft createAircraft (String registrationNumber, String serialNo, String status, 
            String firstFlyDate, String deliveryDate, String retireDate, String aircraftTypeName) throws MASException{
        if (handler.hasAircraft(registrationNumber)){
            throw new MASException("AF01");
        }
        Aircraft af = new Aircraft ();
        if (registrationNumber.length() > 0)
            af.setRegistrationNo(registrationNumber);
        if (serialNo.length() > 0)
            af.setSerialNo(serialNo);
        if (status.length() > 0)
            af.setStatus(status);
        if (firstFlyDate.length() > 0)
            af.setFirstFlyDate(firstFlyDate);
        if (deliveryDate.length() > 0)
            af.setDeliveryDate(deliveryDate);
        if (retireDate.length() > 0)
            af.setRetireDate(retireDate);
        
        AircraftType at = handler.findAircraftType(aircraftTypeName).get(0);
        
        if (at != null)
            af.setAircraftType(at);
        handler.addAircraft(af);
        System.out.println("Aircraft added!");
        return af;
    }
    
    @Override
    public void deleteAircraft (long id) throws MASException{
        Aircraft af = handler.findAircraft(id);
        handler.deleteAircraft(af);
        System.out.println("Aircraft deleted!");
    }
    
    @Override
    public Aircraft updateAircraft (long id, String registrationNumber, String serialNo, String status, 
            String firstFlyDate, String deliveryDate, String retireDate, long aircraftTypeId) throws MASException{

        Aircraft af = handler.findAircraft(id);
        
        if (registrationNumber.length() > 0)
            af.setRegistrationNo(registrationNumber);
        if (serialNo.length() > 0)
            af.setSerialNo(serialNo);
        if (status.length() > 0)
            af.setStatus(status);
        if (firstFlyDate.length() > 0)
            af.setFirstFlyDate(firstFlyDate);
        if (deliveryDate.length() > 0)
            af.setDeliveryDate(deliveryDate);
        if (retireDate.length() > 0)
            af.setRetireDate(retireDate);
        AircraftType at = handler.findAircraftType(aircraftTypeId);
        if (at != null)
            af.setAircraftType(at);
        
        handler.updateAircraft(af);
        System.out.println("Aircraft updated!");
        return af;
    }
    
    @Override
    public ArrayList<String> listAircraft (){
        List<Aircraft> afs = handler.listAircraft();
        ArrayList result = new ArrayList<String>();
        for (Aircraft af:afs ){
            
            //for testing
            System.out.println(af.toString());
            System.out.println(af.printFlightList());
            
            result.add(af.toString());
        }
        return result;
    }

    
}
