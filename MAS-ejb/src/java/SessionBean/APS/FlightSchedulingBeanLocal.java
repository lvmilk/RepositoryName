/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface FlightSchedulingBeanLocal {

    public FlightFrequency addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString,String sDate, String fDate)  throws Exception;

    public void validateFlightNo(String flightNo) throws Exception;

    public List<FlightFrequency> getAllFlightFrequency();

    public void editFlightFrequency(String flightNo, String depTime, String arrTime, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate);

    public List<FlightFrequency> canDeleteFlightFreqList();

    public void deleteFlightFreqList(List<FlightFrequency> flightFreqList);

    public void editFlightInstance(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, String actualDepTime, String actualArrTime) throws Exception;

    public List<FlightInstance> getAllFlightInstance();

    public List<FlightFrequency> getFlightOfRoute(Route route);

    public Aircraft getAircraft(String registrationNo);

    public void setCheckDate(Long id, String sDate, String fDate);

    public List<FlightInstance> getUnplannedFlightInstance(Aircraft ac);

    public List<Aircraft> getAllAircraft();

    public void scheduleAcToFi(Date startDate, Date endDate) throws ParseException;

    public List<FlightInstance> getThisFlightInstance(String flightNo);

    public void addFlightInstance(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust, String actualDepTime, String actualArrTime, Integer actualDateAdjust) throws Exception;
    
}
