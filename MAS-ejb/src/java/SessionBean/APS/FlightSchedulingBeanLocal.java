
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
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

    public FlightFrequency addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString, String sDate, String fDate, String depTerminal, String arrTerminal) throws Exception;

    public void validateFlightNo(String flightNo) throws Exception;

    public List<FlightFrequency> getAllFlightFrequency();

    public void editFlightFrequency(String flightNo, String depTime, String arrTime, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate, String depTerminal, String arrTerminal) throws Exception;

    public List<FlightFrequency> canDeleteFlightFreqList();

    public void deleteFlightFreqList(List<FlightFrequency> flightFreqList);

    public List<FlightInstance> getAllFlightInstance();

    public List<FlightFrequency> getFlightOfRoute(Route route);

    public Aircraft getAircraft(String registrationNo);

    public List<FlightInstance> getUnplannedFlightInstance(Aircraft ac);

    public List<Aircraft> getAllAircraft();

    public void scheduleAcToFi(Date startDate, Date endDate) throws Exception;

    public List<FlightInstance> getThisFlightInstance(String flightNo);

    public void addFlightInstance(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust, String actualDepTime, String actualArrTime, Integer actualDateAdjust) throws Exception;

    public void deleteAcFromFi(Aircraft ac, FlightInstance fi);

    public String getFirstInstDate();

    public List<FlightInstance> getUnplannedFiWithinPeriod(Date startDate, Date endDate);

    public void editFlightInstance(FlightFrequency flightFrequency, String flightDate, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust, String actualDepTime, String actualArrTime, Integer actualDateAdjust, String depGate) throws Exception;

    public FlightInstance findFlight(Long flightId);

    public Aircraft findAircraft(String serialNo);

    public List<FlightInstance> getUnassignedFlight();

    public FlightInstance findFlight(String flightNo, String flightDate) throws Exception;

    public long getFlightAccumMinute(FlightFrequency ff);

    public boolean addMtToAc(Aircraft ac, String obj, Date mtStart, Date mtEnd, Integer manhour) throws Exception;

    public List<FlightInstance> getAllUnplannedFi();

    public void deleteMtFromAc(Aircraft ac, Maintenance mt);

    public FlightInstance getDummyFi(String outOrIn);

    public List<FlightInstance> getSortedFiWithinPeriod(Date startDate, Date endDate);

    public boolean addAcToFi(Aircraft ac, List<Long> fiId) throws Exception;

    public long calPeriodTotalFlightHour(Date startDate, Date endDate);

    public long calPeriodTotalMtManHour(Date startDate, Date endDate);

    public void setCheckDate(Long id, String sDate, String fDate) throws Exception;

}
