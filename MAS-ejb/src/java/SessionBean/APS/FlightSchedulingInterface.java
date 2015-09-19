/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Exception.MASException;
import java.util.ArrayList;
import javax.ejb.Remote;

/**
 *
 * @author victor
 */
@Remote
public interface FlightSchedulingInterface {

    public void start();

//    public void createFlight (long orgId, long dstId, String flightNo, int StopoverSequenceNo, 
//            String ScheduledDepartureTime, String ScheduledArrivalTime, int dateAdjust, 
//            String startDate, String endDate, 
//            boolean Mon, boolean Tue, boolean Wed, boolean Thu, boolean Fri, boolean Sat, boolean Sun, 
//            String Operator) throws MASException;

//    public void updateGenericFlight(long gfId, long orgId, long dstId, 
//            String flightNo, int StopoverSequenceNo, 
//            String ScheduledDepartureTime, String ScheduledArrivalTime, int dateAdjust, 
//            String startDate, String endDate, 
//            boolean Mon, boolean Tue, boolean Wed, boolean Thu, boolean Fri, boolean Sat, boolean Sun, 
//            String Operator)  throws MASException;

    public ArrayList<String> listFlight();

    public ArrayList<String> listGenericFlight();

    public void createFlight(String orgIATA, String dstIATA, String OBflightNo, String IBflightNo,
            String stopoverIATA,
            String OBScheduledDepartureTime, String OBScheduledArrivalTime,
            String OBstopoverArrivingTime, String OBstopoverLeavingTime,
            int OBdateAdjustStopover, int OBfinaldateAdjust,
            String IBScheduledDepartureTime, String IBScheduledArrivalTime,
            String IBstopoverArrivingTime, String IBstopoverLeavingTime,
            int IBdateAdjustStopover, int IBfinaldateAdjust, boolean stopover,
            String startDate, String endDate,
            boolean OBMon, boolean OBTue, boolean OBWed, boolean OBThu, boolean OBFri, boolean OBSat, boolean OBSun,
            boolean IBMon, boolean IBTue, boolean IBWed, boolean IBThu, boolean IBFri, boolean IBSat, boolean IBSun,
            String Operator, String AircraftType) throws MASException;

    public void createFlight() throws MASException;

    public void assignFlightToAircraft(long flid, long acid) throws MASException;
}
