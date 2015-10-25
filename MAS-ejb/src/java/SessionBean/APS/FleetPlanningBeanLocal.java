/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.APS.FlightInstance;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author Lu Xi
 */
@Local
public interface FleetPlanningBeanLocal {

    public List<AircraftType> getAllAircraftType();

    public List<Aircraft> getAllAircraft();

    public Aircraft getAircraft(String registrationNo);

    public AircraftType getAircraftType(String type);

    public boolean checkDuplicate(String type);

    public void tryDeleteAircraftType(AircraftType aircraftType) throws Exception;

    public boolean tryDeleteTypeList(List<AircraftType> typeList) throws Exception;

    public List<AircraftType> canDeleteTypeList();

    public List<AircraftType> cannotDeleteTypeList();

    public void deleteAircraftType(AircraftType aircraftType) throws Exception;

    public void deleteAircraftTypeList(List<AircraftType> typeList) throws Exception;

    public void deleteAircraft(ArrayList<Aircraft> selectedList) throws Exception;

    public Map<String, Integer> getAllSize(String type);

    public Map<String, String> getAllManufacturer(String type);

    public Map<String, List<String>> getAllNum(String type);

    public List<Aircraft> getThisTypeAircraft(String type);

    public void editAircraftType(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double aircraftLength, Double wingspan, String minAirspace, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo, Integer stewardess, Integer steward, Integer purser, Integer captain, Integer pilot) throws Exception;

    public void addAircraftType(String type, String manufacturer, Double maxDistance, Double leaseCost, Double fuelCost, Double aircraftLength, Double wingspan, String minAirspace, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo, Integer stewardess, Integer steward, Integer purser, Integer captain, Integer pilot) throws Exception;

    public void addAircraft(String type, String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate) throws Exception;

    public void editAircraft(String type, String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate) throws Exception;

    public List<FlightInstance> getThisFlightInstance(String registrationNo) throws Exception;

    public void editMtStandard(AircraftType acType, Integer dailycDu, Integer dailycMH, Integer weeklycDu, Integer weeklycMH, Integer acInH, Integer acInD, Integer acDu, Integer acMH, Integer ccInH, Integer ccInD, Integer ccDu, Integer ccMH, Integer dcInH, Integer dcInD, Integer dcDu, Integer dcMH);

    
}
