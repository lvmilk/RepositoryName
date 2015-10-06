/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Airport;
import Entity.APS.FlightFrequency;
import Entity.APS.Route;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface FlightSchedulingBeanLocal {

    public void addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString)  throws Exception;

    public void validateFlightNo(String flightNo) throws Exception;

    public List<FlightFrequency> getAllFlightFrequency();

    public void editFlightFrequency(String flightNo, String depTime, String arrTime, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate);

    public List<FlightFrequency> canDeleteFlightFreqList();

    public void deleteFlightFreqList(List<FlightFrequency> flightFreqList);

    public List<FlightFrequency> getFlightOfRoute(Route route);
    
}