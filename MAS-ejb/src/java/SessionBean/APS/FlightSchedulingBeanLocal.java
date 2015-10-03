/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Route;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface FlightSchedulingBeanLocal {

    public void addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust, boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString)  throws Exception;

    public void validateFlightNo(String flightNo) throws Exception;
    
}
