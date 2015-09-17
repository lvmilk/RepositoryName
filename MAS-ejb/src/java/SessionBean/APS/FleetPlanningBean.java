/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Lu Xi
 */
@Stateless
public class FleetPlanningBean implements FleetPlanningBeanLocal {
    @PersistenceContext
    EntityManager em;
    AircraftType aircraftType;
    Aircraft aircraft;

    public FleetPlanningBean(){    
    }

    @Override
    public void addAircraftType(String type, String manufacturer, Double maxDistance, Double cruiseSpeed, Double cruiseAltitude, Double aircraftLength, Double wingspan, 
                        Integer suiteNo,Integer fcSeatNo,Integer bcSeatNo,Integer pecSeatNo,Integer ecSeatNo)throws Exception{
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType != null) {
            throw new Exception("AircraftType exists.");
        }
        aircraftType =new AircraftType();
        aircraftType.create(type, manufacturer, maxDistance, cruiseSpeed, cruiseAltitude, aircraftLength, wingspan, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo);
        em.persist(aircraftType); 
        em.flush();
    }
    
    @Override
    public void editAircraftType(String type, String manufacturer, Double maxDistance, Double cruiseSpeed, Double cruiseAltitude, Double aircraftLength, Double wingspan, 
                        Integer suiteNo,Integer fcSeatNo,Integer bcSeatNo,Integer pecSeatNo,Integer ecSeatNo)throws Exception{
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist..");
        }
        aircraftType.setManufacturer(manufacturer);
        aircraftType.setMaxDistance(maxDistance);
        aircraftType.setCruiseSpeed(cruiseSpeed);
        aircraftType.setCruiseAltitude(cruiseAltitude);
        aircraftType.setAircraftLength(aircraftLength);
        aircraftType.setWingspan(wingspan);
        aircraftType.setSuiteNo(suiteNo);
        aircraftType.setFcSeatNo(fcSeatNo);
        aircraftType.setBcSeatNo(bcSeatNo);
        aircraftType.setPecSeatNo(pecSeatNo);
        aircraftType.setEcSeatNo(ecSeatNo);
        em.merge(aircraftType);
        em.flush();
    }
    
    @Override
    public void deleteAircraftType(String type)throws Exception{
        aircraftType = em.find(AircraftType.class, type);
        if (aircraftType == null) {
            throw new Exception("AircraftType does not exist.");
        }
        if(aircraftType.getAircraft().isEmpty())
            em.remove(aircraftType);
        else
            throw new Exception("Cannot delete.This aircraft type is linked with an aircraft.");
        em.flush();
    }
    
    @Override
    public List<AircraftType> getAllAircraftType(){
        Query query = em.createQuery("SELECT at FROM AircraftType at ");
        List<AircraftType> resultList=(List)query.getResultList();
        if(resultList.isEmpty())
        System.out.println("List is empty");
        else
        System.out.println("List data exists");
        
        return resultList;
    }
    
    @Override
    public AircraftType aircraftType(String type){
        aircraftType = em.find(AircraftType.class, type);
        return aircraftType;
    }
    
    
    @Override
    public void addAircraft(String type,String registrationNo,String serialNo,String status,String firstFlyDate,String deliveryDate,String retireDate,
            Long flightLogId,Long maintenanceLogId,Long transactionLogId)throws Exception{       
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft != null) {
            throw new Exception("Aircraft exists.");
        }
        aircraftType=em.find(AircraftType.class, type);
        if(aircraftType==null){
            throw new Exception("AircraftType does not exist.");
        }
        aircraft=new Aircraft();
        aircraft.create(registrationNo, serialNo, status, firstFlyDate, deliveryDate, retireDate, flightLogId, maintenanceLogId, transactionLogId);
        aircraft.setAircraftType(aircraftType);
        em.persist(aircraft);
        em.flush();
        aircraftType.getAircraft().add(aircraft);
        em.merge(aircraftType);
        em.flush();
    }
    
    @Override
    public void editAircraft(String type,String registrationNo,String serialNo,String status,String firstFlyDate,String deliveryDate,String retireDate,
            Long flightLogId,Long maintenanceLogId,Long transactionLogId)throws Exception{
        aircraft = em.find(Aircraft.class, registrationNo);
        if (aircraft == null) {
            throw new Exception("Aircraft does not exist.");
        }
        aircraftType=em.find(AircraftType.class, type);
        if(aircraftType==null){
            throw new Exception("AircraftType does not exist.");
        }
        
        aircraft.setSerialNo(serialNo);
        aircraft.setStatus(status);
        aircraft.setFirstFlyDate(firstFlyDate);
        aircraft.setDeliveryDate(deliveryDate);
        aircraft.setRetireDate(retireDate);
        aircraft.setFlightLogId(flightLogId);
        aircraft.setMaintenanceLogId(maintenanceLogId);
        aircraft.setTransactionLogId(transactionLogId);
        aircraft.setAircraftType(aircraftType);
        em.merge(aircraft);
        em.flush();

    }
    
    @Override
    public void deleteAircraft(String type,String registrationNo)throws Exception{
        aircraft = em.find(Aircraft.class, registrationNo);
        if (registrationNo == null) {
            throw new Exception("Aircraft does not exist.");
        }
        aircraftType=em.find(AircraftType.class, type);
        if(type==null){
            throw new Exception("AircraftType does not exist.");
        }
        aircraftType.getAircraft().remove(aircraft);
        em.remove(aircraft);
        em.flush();       
    }
    
    @Override
    public List<Aircraft> getAllAircraft(){
        Query query = em.createQuery("SELECT a FROM Aircraft a ");
        List<Aircraft> resultList=(List)query.getResultList();
        if(resultList.isEmpty())
        System.out.println("List is empty");
        else
        System.out.println("List data exists");   
        return resultList;
    }
    
    @Override
    public Aircraft getAircraft(String registrationNo){
        aircraft = em.find(Aircraft.class, registrationNo);
                return aircraft;
    }
}
