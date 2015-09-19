/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBeanMAS.APS;

import EntityMAS.APS.Aircraft;
import EntityMAS.APS.AircraftType;
import EntityMAS.APS.Airport;
import EntityMAS.APS.Flight;
import EntityMAS.APS.FlightPackage;
import EntityMAS.APS.GenericFlight;
import EntityMAS.APS.Route;
import Exception.MASException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author victor
 */
@Stateless
public class FlightScheduling implements FlightSchedulingInterface {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext(unitName="MAS-ejbPU")
    private EntityManager em;
    private FlightHandler fh;
    private RouteHandler rh;
    private AircraftHandler ah;
    private DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
    @Override
    public void start(){
        fh = new FlightHandler();
        rh = new RouteHandler();
        ah = new AircraftHandler();
        fh.setEm(em);
        rh.setEm(em);
        ah.setEm(em);
    }

    @Override
    public void createFlight() throws MASException{
        //for test
            String orgIATA = "SIN";
            String dstIATA = "LHR";
            String OBflightNo = "MR91";
            String IBflightNo = "MR92";
            String stopoverIATA = "PEK";
            String OBScheduledDepartureTime = "15:00"+":00.000";
            String OBScheduledArrivalTime = "07:00"+":00.000";
            String OBstopoverArrivingTime = "22:00"+":00.000";
            String OBstopoverLeavingTime = "00:10"+":00.000";
            int OBdateAdjustStopover = 0;
            int OBfinaldateAdjust = 0;
            String IBScheduledDepartureTime = "08:00"+":00.000";
            String IBScheduledArrivalTime = "11:00"+":00.000";
            String IBstopoverArrivingTime = "04:00"+":00.000";
            String IBstopoverLeavingTime ="06:00"+":00.000";
            int IBdateAdjustStopover = 0;
            int IBfinaldateAdjust = 1;
            boolean stopover = true;
            String startDate = "2015-09-17";
            String endDate = "2015-10-17";
            boolean OBMon = true;
            boolean OBTue = false;
            boolean OBWed = false;
            boolean OBThu = false;
            boolean OBFri = false;
            boolean OBSat = false;
            boolean OBSun = false;
            boolean IBMon = true;
            boolean IBTue = false;
            boolean IBWed = false;
            boolean IBThu = false;
            boolean IBFri = false;
            boolean IBSat = false;
            boolean IBSun = false;
            String Operator = "Merlion";
            String AircraftType = "777-300ER";
            createFlight(orgIATA, dstIATA, OBflightNo, IBflightNo, stopoverIATA, OBScheduledDepartureTime, OBScheduledArrivalTime, OBstopoverArrivingTime, OBstopoverLeavingTime, OBdateAdjustStopover, OBfinaldateAdjust, IBScheduledDepartureTime, IBScheduledArrivalTime, IBstopoverArrivingTime, IBstopoverLeavingTime, IBdateAdjustStopover, IBfinaldateAdjust, stopover, startDate, endDate, OBMon, OBTue, OBWed, OBThu, OBFri, OBSat, OBSun, IBMon, IBTue, IBWed, IBThu, IBFri, IBSat, IBSun, Operator, AircraftType);
    }
    
    @Override
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
            String Operator, String AircraftType) throws MASException{
        
        AircraftType at = ah.findAircraftType(AircraftType).get(0);
        
        Airport org = rh.findAirport(orgIATA).get(0);
        Airport dst = rh.findAirport(dstIATA).get(0);
        
        String startServingDate = startDate+"T"+OBScheduledDepartureTime + org.getTimeZone();
        String endServingDate = endDate+"T"+OBScheduledDepartureTime + org.getTimeZone();
        
        if (stopover){
            
            Airport stp = rh.findAirport(stopoverIATA).get(0);
            
            //Outbound Flight First Part #1
            GenericFlight obgf1 = new GenericFlight();
            Route obrt1 = new Route();
            try{
                obrt1= rh.findRoute(org.getId(), stp.getId()).get(0);
            }catch(Exception e){
                obrt1.setOrigin(org);
                obrt1.setDestination(stp);
                rh.addRoute(obrt1);
            }
            obgf1.setRoute(obrt1);
            if (OBflightNo.length() > 0)
                obgf1.setFlightNo(OBflightNo);
            obgf1.setStopoverSequenceNo(1);
            if (OBScheduledDepartureTime.length() > 0)
                obgf1.setScheduledDepartureTime(OBScheduledDepartureTime + org.getTimeZone());
            if (OBstopoverArrivingTime.length() > 0)
                obgf1.setScheduledArrivalTime(OBstopoverArrivingTime + stp.getTimeZone());
            obgf1.setDateAdjust(OBdateAdjustStopover);
            
            obgf1.setStartDate(startServingDate);
            obgf1.setEndDate(endServingDate);
            
            obgf1.setMon(OBMon);
            obgf1.setTue(OBTue);
            obgf1.setWed(OBWed);
            obgf1.setThu(OBThu);
            obgf1.setFri(OBFri);
            obgf1.setSat(OBSat);
            obgf1.setSat(OBSat);
            obgf1.setSun(OBSun);
            obgf1.setAircraftType(at);
            if (Operator.length() > 0)
                obgf1.setOperator(Operator);
            fh.addGenericFlight(obgf1);
            
            //for test
            System.out.println("Create Flight: " + obgf1.toString());
            
            //Outbound Flight Second Part #2
            GenericFlight obgf2 = new GenericFlight();
            Route obrt2 = new Route();
            try{
                obrt2 = rh.findRoute(stp.getId(), dst.getId()).get(0);
            }catch (Exception e){
                obrt2.setOrigin(stp);
                obrt2.setDestination(dst);
                rh.addRoute(obrt2);
            }
            obgf2.setRoute(obrt2);
            if (OBflightNo.length() > 0)
                obgf2.setFlightNo(OBflightNo);
            obgf2.setStopoverSequenceNo(2);
            if (OBstopoverLeavingTime.length() > 0)
                obgf2.setScheduledDepartureTime(OBstopoverLeavingTime + stp.getTimeZone());
            if (OBScheduledArrivalTime.length() > 0)
                obgf2.setScheduledArrivalTime(IBScheduledArrivalTime + dst.getTimeZone());
            obgf2.setDateAdjust(OBfinaldateAdjust);
            
            obgf2.setStartDate(startServingDate);
            obgf2.setEndDate(endServingDate);
            
            obgf2.setMon(OBMon);
            obgf2.setTue(OBTue);
            obgf2.setWed(OBWed);
            obgf2.setThu(OBThu);
            obgf2.setFri(OBFri);
            obgf2.setSat(OBSat);
            obgf2.setSat(OBSat);
            obgf2.setSun(OBSun);
            obgf2.setAircraftType(at);
            if (Operator.length() > 0)
                obgf2.setOperator(Operator);
            fh.addGenericFlight(obgf2);
            
            //for test
            System.out.println("Create Flight: " + obgf2.toString());
            
            //Inbound Flight First Part #3
            GenericFlight ibgf1 = new GenericFlight();
            Route ibrt1 = new Route();
            try{
                ibrt1= rh.findRoute(dst.getId(), stp.getId()).get(0);
            }catch(Exception e){
                ibrt1.setOrigin(dst);
                ibrt1.setDestination(stp);
                rh.addRoute(ibrt1);
            }
            ibgf1.setRoute(ibrt1);
            if (IBflightNo.length() > 0)
                ibgf1.setFlightNo(IBflightNo);
            ibgf1.setStopoverSequenceNo(1);
            if (IBScheduledDepartureTime.length() > 0)
                ibgf1.setScheduledDepartureTime(IBScheduledDepartureTime + dst.getTimeZone());
            if (IBstopoverArrivingTime.length() > 0)
                ibgf1.setScheduledArrivalTime(IBstopoverArrivingTime + stp.getTimeZone());
            ibgf1.setDateAdjust(IBfinaldateAdjust);
            
            ibgf1.setStartDate(startServingDate);
            ibgf1.setEndDate(endServingDate);
            
            ibgf1.setMon(IBMon);
            ibgf1.setTue(IBTue);
            ibgf1.setWed(IBWed);
            ibgf1.setThu(IBThu);
            ibgf1.setFri(IBFri);
            ibgf1.setSat(IBSat);
            ibgf1.setSat(IBSat);
            ibgf1.setSun(IBSun);
            ibgf1.setAircraftType(at);
            if (Operator.length() > 0)
                ibgf1.setOperator(Operator);
            fh.addGenericFlight(ibgf1);
            
            //for test
            System.out.println("Create Flight: " + ibgf1.toString());
            
            //Outbound Flight Second Part #4
            GenericFlight ibgf2 = new GenericFlight();
            Route ibrt2 = new Route();
            try{
                ibrt2 = rh.findRoute(stp.getId(), org.getId()).get(0);
            }catch(Exception e){
                ibrt2.setOrigin(stp);
                ibrt2.setDestination(org);
                rh.addRoute(ibrt2);
            }
            ibgf2.setRoute(ibrt2);
            if (IBflightNo.length() > 0)
                ibgf2.setFlightNo(IBflightNo);
            ibgf2.setStopoverSequenceNo(2);
            if (IBstopoverLeavingTime.length() > 0)
                ibgf2.setScheduledDepartureTime(IBstopoverLeavingTime + stp.getTimeZone());
            if (IBScheduledArrivalTime.length() > 0)
                ibgf2.setScheduledArrivalTime(IBScheduledArrivalTime + org.getTimeZone());
            ibgf2.setDateAdjust(IBfinaldateAdjust);
            
            ibgf2.setStartDate(startServingDate);
            ibgf2.setEndDate(endServingDate);
            
            ibgf2.setMon(IBMon);
            ibgf2.setTue(IBTue);
            ibgf2.setWed(IBWed);
            ibgf2.setThu(IBThu);
            ibgf2.setFri(IBFri);
            ibgf2.setSat(IBSat);
            ibgf2.setSat(IBSat);
            ibgf2.setSun(IBSun);
            ibgf2.setAircraftType(at);
            if (Operator.length() > 0)
                ibgf2.setOperator(Operator);
            fh.addGenericFlight(ibgf2);
            
            //for test
            System.out.println("Create Flight: " + obgf2.toString());
            
            //Add Flights during Serving Period
            DateTime startOperatingDate = new DateTime (startDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            DateTime endOperatingDate = new DateTime(endDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            
            DateTime OBDep = new DateTime(startDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            DateTime OBStpArr = new DateTime(startDate + "T" + OBstopoverArrivingTime + stp.getTimeZone());
            DateTime OBStpDep = new DateTime(startDate + "T" + OBstopoverLeavingTime + stp.getTimeZone());
            DateTime OBArr = new DateTime(startDate + "T" + OBScheduledArrivalTime + dst.getTimeZone());
            DateTime IBDep = new DateTime(startDate + "T" + IBScheduledDepartureTime + dst.getTimeZone());
            DateTime IBStpArr = new DateTime(startDate + "T" + IBstopoverArrivingTime + stp.getTimeZone());
            DateTime IBStpDep = new DateTime(startDate + "T" + IBstopoverLeavingTime + stp.getTimeZone());
            DateTime IBArr = new DateTime(startDate + "T" + IBScheduledArrivalTime + org.getTimeZone());
            while (OBStpArr.compareTo(OBDep) <= 0){
                OBStpArr = OBStpArr.plusDays(1);
                System.out.println("Date Adjusting...1");
            }
            while (OBStpDep.compareTo(OBStpArr) <= 0){
                OBStpDep = OBStpDep.plusDays(1);
                System.out.println("Date Adjusting...2");
            }
            while (OBArr.compareTo(OBStpDep) <= 0){
                OBArr = OBArr.plusDays(1);
                System.out.println("Date Adjusting...3");
            }
            while (IBDep.compareTo(OBArr) <= 0){
                IBDep = IBDep.plusDays(1);
                System.out.println("Date Adjusting...4");
            }
            while (IBStpArr.compareTo(IBDep) <= 0){
                IBStpArr = IBStpArr.plusDays(1);
                System.out.println("Date Adjusting...5");
            }
            while (IBStpDep.compareTo(IBStpArr) <= 0){
                IBStpDep = IBStpDep.plusDays(1);
                System.out.println("Date Adjusting...6");
            }
            while (IBArr.compareTo(IBStpDep) <= 0){
                IBArr = IBArr.plusDays(1);
                System.out.println("Date Adjusting...7");
            }
           
            while (startOperatingDate.compareTo(endOperatingDate) <= 0){
                
                if (checkWeekDay(OBDep, OBMon, OBTue, OBWed, OBThu, OBFri, OBSat, OBSun)){
                    
                    Flight ob1 = new Flight();
                    ob1.setDate(OBDep.toString());
                    ob1.setEstimatedDepartureTime(toLocalTime(org, OBDep));
                    ob1.setEstimatedArrivalTime(toLocalTime(stp, OBStpArr));
                    ob1.setOperationStatus("PLANNED");
                    fh.addFlight(ob1);
                    obgf1.addFlight(ob1);
                    fh.updateGenericFight();
                    
                    Flight ob2 = new Flight();
                    ob2.setDate(OBStpDep.toString());
                    ob2.setEstimatedDepartureTime(toLocalTime(stp, OBStpDep));
                    ob2.setEstimatedArrivalTime(toLocalTime(dst, OBArr));
                    ob2.setOperationStatus("PLANNED");
                    fh.addFlight(ob2);
                    obgf2.addFlight(ob2);
                    fh.updateGenericFight();
                    
                    Flight ib1 = new Flight();
                    ib1.setDate(IBDep.toString());
                    ib1.setEstimatedDepartureTime(toLocalTime(dst, IBDep));
                    ib1.setEstimatedArrivalTime(toLocalTime(stp, IBStpArr));
                    ib1.setOperationStatus("PLANNED");
                    fh.addFlight(ib1);
                    ibgf1.addFlight(ib1);
                    fh.updateGenericFight();
                    
                    Flight ib2 = new Flight();
                    ib2.setDate(IBStpDep.toString());
                    ib2.setEstimatedDepartureTime(toLocalTime(stp, IBStpDep));
                    ib2.setEstimatedArrivalTime(toLocalTime(org, IBArr));
                    ib2.setOperationStatus("PLANNED");
                    fh.addFlight(ib2);
                    ibgf2.addFlight(ib2);
                    fh.updateGenericFight();
                    
                    //for test
                    System.out.println("Outbounding Flight 1: " + ob1.toString());
                    System.out.println("Outbounding Flight 2: " + ob2.toString());
                    System.out.println("Inbounding Flight 1: " + ib1.toString());
                    System.out.println("Inbounding Flight 2: " + ib2.toString());
                    
                    FlightPackage fp = new FlightPackage();
                    fh.addFlightPackage(fp);
                    fp.addFlight(ob1);
                    fp.addFlight(ob2);
                    fp.addFlight(ib1);
                    fp.addFlight(ib2);
                    fh.updateFlight();

                    //for test
                    System.out.println("Flight Package created:\n" + fp.toString());
                }
                
                OBDep = OBDep.plusDays(1);
                OBStpArr = OBStpArr.plusDays(1);
                OBStpDep = OBStpDep.plusDays(1);
                OBArr = OBArr.plusDays(1);
                IBDep = IBDep.plusDays(1);
                IBStpArr = IBStpArr.plusDays(1);
                IBStpDep = IBStpDep.plusDays(1);
                IBArr = IBArr.plusDays(1);
                
                startOperatingDate = startOperatingDate.plusDays(1);
            }
            
        }else{
            //For outbounding genericflight and flights
            GenericFlight obgf = new GenericFlight();
            Route obrt = rh.findRoute(orgIATA, dstIATA).get(0);
            if (obrt == null){
                //throw new MASException("RT02");
                obrt.setOrigin(org);
                obrt.setDestination(dst);
                rh.addRoute(obrt);
            }
            obgf.setRoute(obrt);
            if (OBflightNo.length() > 0)
                obgf.setFlightNo(OBflightNo);
            obgf.setStopoverSequenceNo(0);
            if (OBScheduledDepartureTime.length() > 0)
                obgf.setScheduledDepartureTime(OBScheduledDepartureTime + org.getTimeZone());
            if (OBScheduledArrivalTime.length() > 0)
                obgf.setScheduledArrivalTime(OBScheduledArrivalTime + dst.getTimeZone());
            obgf.setDateAdjust(OBfinaldateAdjust);
            
            obgf.setStartDate(startServingDate);
            obgf.setEndDate(endServingDate);
            
            obgf.setMon(OBMon);
            obgf.setTue(OBTue);
            obgf.setWed(OBWed);
            obgf.setThu(OBThu);
            obgf.setFri(OBFri);
            obgf.setSat(OBSat);
            obgf.setSat(OBSat);
            obgf.setSun(OBSun);
            obgf.setAircraftType(at);
            if (Operator.length() > 0)
                obgf.setOperator(Operator);
            fh.addGenericFlight(obgf);
            
            //For inbounding genericflight and flights
            GenericFlight ibgf = new GenericFlight();
            Route ibrt = rh.findRoute(dstIATA, orgIATA).get(0);
            if (ibrt == null){
                //throw new MASException("RT02");
                ibrt.setOrigin(org);
                ibrt.setDestination(dst);
                rh.addRoute(ibrt);
            }
            ibgf.setRoute(ibrt);
            if (IBflightNo.length() > 0)
                ibgf.setFlightNo(IBflightNo);
            ibgf.setStopoverSequenceNo(0);
            
            if (IBScheduledDepartureTime.length() > 0)
                ibgf.setScheduledDepartureTime(IBScheduledDepartureTime + dst.getTimeZone());
            if (IBScheduledArrivalTime.length() > 0)
                ibgf.setScheduledArrivalTime(IBScheduledArrivalTime + org.getTimeZone());
            ibgf.setDateAdjust(IBfinaldateAdjust);
            
            ibgf.setStartDate(startServingDate);
            ibgf.setEndDate(endServingDate);
            
            ibgf.setMon(IBMon);
            ibgf.setTue(IBTue);
            ibgf.setWed(IBWed);
            ibgf.setThu(IBThu);
            ibgf.setFri(IBFri);
            ibgf.setSat(IBSat);
            ibgf.setSat(IBSat);
            ibgf.setSun(IBSun);
            ibgf.setAircraftType(at);
            if (Operator.length() > 0)
                ibgf.setOperator(Operator);
            fh.addGenericFlight(ibgf);
            
            
            //for test
            System.out.println(obgf.toString());
            System.out.println(ibgf.toString());
            
            //Add Flights during Serving Period
            DateTime startOperatingDate = new DateTime (startDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            DateTime endOperatingDate = new DateTime(endDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            DateTime OBDep = new DateTime(startDate + "T" + OBScheduledDepartureTime + org.getTimeZone());
            DateTime OBArr = new DateTime(startDate + "T" + OBScheduledArrivalTime + dst.getTimeZone());
            DateTime IBDep = new DateTime(startDate + "T" + IBScheduledDepartureTime + dst.getTimeZone());
            DateTime IBArr = new DateTime(startDate + "T" + IBScheduledArrivalTime + org.getTimeZone());
            while (OBArr.compareTo(OBDep) <= 0){
                OBArr = OBArr.plusDays(1);
            }
            while (IBDep.compareTo(OBArr) <= 0){
                IBDep = IBDep.plusDays(1);
            }
            while (IBArr.compareTo(IBDep) <= 0){
                IBArr = IBArr.plusDays(1);
            }
            while (startOperatingDate.compareTo(endOperatingDate) <= 0){
                
                if (checkWeekDay(OBDep, OBMon, OBTue, OBWed, OBThu, OBFri, OBSat, OBSun)){
                    
                    Flight ob = new Flight();
                    ob.setDate(OBDep.toString());
                    ob.setEstimatedDepartureTime(toLocalTime(org, OBDep));
                    ob.setEstimatedArrivalTime(toLocalTime(dst, OBArr));
                    ob.setOperationStatus("PLANNED");
                    fh.addFlight(ob);
                    obgf.addFlight(ob);
                    fh.updateGenericFight();
                    
                    Flight ib = new Flight();
                    ib.setDate(IBDep.toString());
                    ib.setEstimatedDepartureTime(toLocalTime(dst, IBDep));
                    ib.setEstimatedArrivalTime(toLocalTime(org, IBArr));
                    ib.setOperationStatus("PLANNED");
                    fh.addFlight(ib);
                    ibgf.addFlight(ib);
                    fh.updateGenericFight();
                    
                    //for test
                    System.out.println("Outbounding Flight: " + ob.toString());
                    System.out.println("Inbounding Flight: " + ib.toString());
                    
                    FlightPackage fp = new FlightPackage();
                    fh.addFlightPackage(fp);
                    fp.addFlight(ob);
                    fp.addFlight(ib);
                    fh.updateFlight();

                    //for test
                    System.out.println("Flight Package created:\n" + fp.toString());
                }

                OBDep = OBDep.plusDays(1);
                OBArr = OBArr.plusDays(1);
                IBDep = IBDep.plusDays(1);
                IBArr = IBArr.plusDays(1);
                
                startOperatingDate = startOperatingDate.plusDays(1);
            }
            
            
            
            
            
        }
    
    }

//    @Override
//    public void updateGenericFlight(long gfId, String orgIATA, String dstIATA, String flightNo, int StopoverSequenceNo, 
//            String ScheduledDepartureTime, String ScheduledArrivalTime, int dateAdjust,
//            String startDate, String endDate,
//            boolean Mon, boolean Tue, boolean Wed, boolean Thu, boolean Fri, boolean Sat, boolean Sun,
//            String Operator) throws MASException{
//        
////        GenericFlight gf = fh.findGenericFlight(gfId);
////        Route rt = rh.findRoute(orgId, dstId).get(0);
////        if (rt == null)
////            throw new MASException("RT02");
////        else
////            //for test
////            System.out.println("Create Flight: route = " + rt.toString());
////        gf.setRoute(rt);
////        if (flightNo.length() > 0)
////            gf.setFlightNo(flightNo);
////        if (StopoverSequenceNo > -1)
////            gf.setStopoverSequenceNo(StopoverSequenceNo);
////        if (ScheduledDepartureTime.length() > 0)
////            gf.setScheduledDepartureTime(ScheduledDepartureTime);
////        if (ScheduledArrivalTime.length() > 0)
////            gf.setScheduledArrivalTime(ScheduledArrivalTime);
////        gf.setDateAdjust(dateAdjust);
////        if (startDate.length() > 0)
////            gf.setStartDate(startDate);
////        if (endDate.length() > 0)
////            gf.setEndDate(endDate);
////        gf.setMon(Mon);
////        gf.setTue(Tue);
////        gf.setWed(Wed);
////        gf.setThu(Thu);
////        gf.setFri(Fri);
////        gf.setSat(Sat);
////        gf.setSat(Sat);
////        gf.setSun(Sun);
////        if (Operator.length() > 0)
////            gf.setOperator(Operator);
////        fh.addGenericFlight(gf);
//        
//            GenericFlight gf = new GenericFlight();
//            Route ibrt = rh.findRoute(dstIATA, orgIATA).get(0);
//            if (ibrt == null){
//                //throw new MASException("RT02");
//                ibrt.setOrigin(org);
//                ibrt.setDestination(dst);
//                rh.addRoute(ibrt);
//            }
//            gf.setRoute(ibrt);
//            if (flightNo.length() > 0)
//                gf.setFlightNo(flightNo);
//            gf.setStopoverSequenceNo(0);
//            if (ScheduledDepartureTime.length() > 0)
//                gf.setScheduledDepartureTime(ScheduledDepartureTime + dst.getTimeZone());
//            if (ScheduledArrivalTime.length() > 0)
//                gf.setScheduledArrivalTime(ScheduledArrivalTime + org.getTimeZone());
//            gf.setDateAdjust(finaldateAdjust);
//            if (startDate.length() > 0)
//                gf.setStartDate(startDate+ScheduledDepartureTime + dst.getTimeZone());
//            if (endDate.length() > 0)
//                gf.setEndDate(endDate+ScheduledArrivalTime + dst.getTimeZone());
//            gf.setMon(Mon);
//            gf.setTue(Tue);
//            gf.setWed(Wed);
//            gf.setThu(Thu);
//            gf.setFri(Fri);
//            gf.setSat(Sat);
//            gf.setSat(Sat);
//            gf.setSun(Sun);
//            if (Operator.length() > 0)
//                gf.setOperator(Operator);
//            fh.addGenericFlight(gf);
//        
//        
//    }
//    public void updateFlightEstTime(){}
//    public void updateFlightActTime(){}
    public void deleteGenericFlight(){}
    public void deleteFlight(){}
    @Override
    public ArrayList<String> listFlight(){
        List<Flight> fls = fh.listFlight();
        Collections.sort(fls);
        ArrayList result = new ArrayList<String>();
        for (Flight fl:fls ){
            
            //for testing
            System.out.println(fl.toString());
            
            result.add(fl.toString());
        }
        return result;
    }
    
    @Override
    public ArrayList<String> listGenericFlight(){
        List<GenericFlight> gfls = fh.listGenericFlight();
        ArrayList result = new ArrayList<String>();
        for (GenericFlight gfl:gfls ){
            
            int i = 1;
            String temp = gfl.toString();
            //for testing
            System.out.println(gfl.toString());
            for (Flight fl: gfl.getFlightList()){
                temp += "\n" + "    " +  i + ". " + fl.toString();
                System.out.println("    " +  i + ". " + fl.toString());
                i++;
            }
            
            result.add(temp);
        }
        return result;
    }
    
    @Override
    public void assignFlightToAircraft(long flid, long acid) throws MASException{
//        if (fl.getFlightStatus().equalsIgnoreCase("PLANNED"));
        Flight fl = fh.findFlight(flid);
        Aircraft ac = ah.findAircraft(acid);
        if (!fl.getGenericFlight().getAircraftType().getType().equalsIgnoreCase(ac.getAircraftType().getType())){
            throw new MASException ("FLATNOTMATCH");
        }
        FlightPackage fk = fl.getFlightPackage();
        DateTime startTime = fk.getStartTime();
        DateTime endTime = fk.getEndTime();
        List<Flight> flightList = ac.getFlight();
        Collections.sort(flightList);
//        
//        if (flightList == null || flightList.size() == 0){
//            List<Flight> fls = fk.getFlightList();
//            for (Flight current : fls){
//                ac.addFlight(current);
//            }
//            return;
//        }
        //for test
        System.out.println("Assign aircraft: size of flight list: " + flightList.size());
        
        if (checkAvailable(startTime, endTime, flightList)){
            List<Flight> fls = fk.getFlightList();
            
            
            for (Flight current : fls){
                ac.addFlight(current);
            }
        } else {
            throw new MASException ("TIMETABLECLASH");
        }
    }
    
    public boolean checkAvailable (DateTime start, DateTime end, List<Flight> fls){
        if (fls == null || fls.size() == 0){
            return true;
        }
        
        DateTime acStart = new DateTime(fls.get(0).getEstimatedDepartureTime());
        
        if (end.compareTo(acStart) <= 0){
            return true;
        }
        int size = fls.size();
        for (int i = 0; i < size - 1; i ++){
            DateTime intervalStart = new DateTime (fls.get(0).getEstimatedArrivalTime());
            DateTime intervalEnd = new DateTime (fls.get(i + 1).getEstimatedDepartureTime());
            if (start.compareTo(intervalStart) > 0 
                    &&end.compareTo(intervalEnd) < 0)
                return true;
        }
        
        return false;
    }
    
    public boolean checkWeekDay (DateTime time, boolean Mon, boolean Tue,
            boolean Wed, boolean Thu, boolean Fri, boolean Sat, boolean Sun){
        int i = time.getDayOfWeek();
        return (i == 1 && Sun) ||
                (i == 2 && Mon) ||
                (i == 3 && Tue) ||
                (i == 4 && Wed) ||
                (i == 5 && Thu) ||
                (i == 6 && Fri) ||
                (i == 7 && Sat);
    }
    
    public String toLocalTime (Airport ap, DateTime dt){
        int offset = new Integer(ap.getTimeZone().substring(0, 3));
        return dt.toDateTime(DateTimeZone.forOffsetHours(offset)).toString(fmt);
    }
    
}
