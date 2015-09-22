/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ZHANG TIANCI
 */
@Local
public interface FleetPlanningBeanLocal {

       public List<AircraftType> getAllAircraftType();

    public void addAircraft(String type, String registrationNo, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate, Long flightLogId, Long maintenanceLogId) throws Exception;

    public void editAircraft(String type, String registrationNo, String serialNo, String status, String firstFlyDate, String deliveryDate, String retireDate, Long flightLogId, Long maintenanceLogId, Long transactionLogId) throws Exception;

    public void deleteAircraft(String type, String registrationNo) throws Exception;

    public List<Aircraft> getAllAircraft();

    public Aircraft getAircraft(String registrationNo);

    public AircraftType getAircraftType(String type);

    public boolean checkDuplicate(String type);

    public void addAircraftType(String type, String manufacturer, Double maxDistance, Double aircraftLength, Double wingspan, String minAirspace,Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) throws Exception;
  
    public void editAircraftType(String type, String manufacturer, Double maxDistance, Double aircraftLength, Double wingspan, String minAirspace, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo) throws Exception;

    public void deleteAircraftType(ArrayList<AircraftType> selectedClass) throws Exception;

    
}
