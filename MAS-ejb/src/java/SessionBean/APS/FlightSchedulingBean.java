/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.Aircraft;
import Entity.APS.Airport;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateless
public class FlightSchedulingBean implements FlightSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    FlightFrequency flightFreq;
    FlightInstance flightInst;
    Aircraft aircraft;

    public FlightSchedulingBean() {
    }

    @Override
    public FlightFrequency addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString,
            String sDate, String fDate) throws Exception {
//        LocalDate startDate = startDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endDate = endDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        checkScheduleTime(route, depTimeString, arrTimeString, dateAdjust);
        checkOperationDate(startDateString, endDateString);

        flightFreq = new FlightFrequency();
        flightFreq.create(route, flightNo, depTimeString, arrTimeString, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDateString, endDateString, sDate, fDate);
        em.persist(flightFreq);
        em.flush();
        Route r = em.find(Route.class, route.getId());
        r.setStatus("Serving");
        List<FlightFrequency> freqList = r.getFlightFreqList();
        freqList.add(flightFreq);
        r.setFlightFreqList(freqList);
        em.merge(r);
        em.flush();
        return flightFreq;
    }

    public void checkScheduleTime(Route route, String depTimeString, String arrTimeString, Integer dateAdjust) throws Exception {
        LocalTime depTime = LocalTime.parse(depTimeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime arrTime = LocalTime.parse(arrTimeString, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate depDate = LocalDate.of(2000, 1, 10);
        LocalDate arrDate = LocalDate.of(2000, 1, 10).plusDays(dateAdjust);
        LocalDateTime depDateTime = LocalDateTime.of(depDate, depTime);
        LocalDateTime arrDateTime = LocalDateTime.of(arrDate, arrTime);
//        ZoneId depZone = ZoneId.of(route.getOrigin().getTimeZone());
//        System.out.println("fsb.checkScheduleTime(): origin airport time zone id is " + depZone);
//        ZoneId arrZone = ZoneId.of(route.getDest().getTimeZone());
//        ZonedDateTime zonedDep = ZonedDateTime.of(depDateTime, depZone);
//        System.out.println("fsb.checkScheduleTime(): departure time is " + zonedDep);
//        ZonedDateTime zonedArr = ZonedDateTime.of(arrDateTime, arrZone);
//        System.out.println("fsb.checkScheduleTime(): arrival time is " + zonedDep);

        if (depDateTime.isAfter(arrDateTime)) {
            System.out.println("Departure time should before arrival time.");
            throw new Exception("Departure time should before arrival time.");
        }
    }

    public void checkOperationDate(String startDateString, String endDateString) throws Exception {
        LocalDate startDate = LocalDate.parse(startDateString, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        LocalDate endDate = LocalDate.parse(endDateString, DateTimeFormatter.ofPattern("uuuu-MM-dd"));
        LocalDate today = LocalDate.now();
        System.out.println(today);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayString = today.format(dtf);
        if (startDate.isBefore(today)) {
            throw new Exception("Start operation date should be after today " + todayString);
        }
        if (startDate.isAfter(endDate)) {
            throw new Exception("Start operation date should be before end operation date.");
        }

    }

    @Override
    public void validateFlightNo(String flightNo) throws Exception {
        Query q1 = em.createQuery("select f from FlightFrequency f where f.flightNo =:flightNo").setParameter("flightNo", flightNo);
        if (!q1.getResultList().isEmpty()) {
            throw new Exception("Flight No. has already been used.");
        }
    }

    @Override
    public List<FlightFrequency> getAllFlightFrequency() {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f");
        List<FlightFrequency> flightFreqList = q1.getResultList();
        if (flightFreqList.isEmpty()) {
            System.out.println("fsb.viewAllRoute(): No flight frequency has been added.");
        } else {
            System.out.println("fsb.viewAllRoute(): Return flight frequency list.");
        }
        return flightFreqList;
    }

    @Override
    public void editFlightFrequency(String flightNo, String depTime, String arrTime, Integer dateAdjust, boolean onMon, boolean onTue,
            boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate) {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightNo =:flightNo").setParameter("flightNo", flightNo);
        flightFreq = (FlightFrequency) q1.getSingleResult();
        flightFreq.setScheduleDepTime(depTime);
        flightFreq.setScheduleArrTime(arrTime);
        flightFreq.setDateAdjust(dateAdjust);
        flightFreq.setOnMon(onMon);
        flightFreq.setOnTue(onTue);
        flightFreq.setOnWed(onWed);
        flightFreq.setOnThu(onThu);
        flightFreq.setOnFri(onFri);
        flightFreq.setOnSat(onSat);
        flightFreq.setOnSat(onSat);
        flightFreq.setOnSun(onSun);
        flightFreq.setStartDate(startDate);
        flightFreq.setEndDate(endDate);

        em.merge(flightFreq);
        em.flush();
        System.out.println("fsb.editFlightFrequency(): Flight frequency updated!");
    }

    @Override
    public List<FlightFrequency> canDeleteFlightFreqList() {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f");
        List<FlightFrequency> fList = q1.getResultList();
        List<FlightFrequency> fListCopy = q1.getResultList();
        FlightFrequency f = new FlightFrequency();
        for (int i = 0; i < fList.size(); i++) {
            f = fList.get(i);
            Query q2 = em.createQuery("SELECT fi FROM FlightInstance fi where fi.flightFrequency=:flightFrequency").setParameter("flightFrequency", f);
            if (!q2.getResultList().isEmpty()) {
//                System.out.println(ap.toString());
                fListCopy.remove(f);
            }
        }
        return fListCopy;
    }

    @Override
    public void deleteFlightFreqList(List<FlightFrequency> flightFreqList) {
        for (FlightFrequency f : flightFreqList) {
            Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.id=:id").setParameter("id", f.getId());
            em.remove(q1.getSingleResult());
        }
        em.flush();
    }

    @Override
    public List<FlightFrequency> getFlightOfRoute(Route route) {
        Query q2 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.route=:route").setParameter("route", route);
        return (List<FlightFrequency>) q2.getResultList();
    }

//    @override
//    public void addFlightFreqToPackage(FlightFrequency flightFreq) {
//        
//    }
//    
//    @override
//    public void generateFlightPackage() {
//        
//    }
//////////////////////////////////////////////////////////////////////////////////////added by luxixi///////////////////////////////////////////////////////////////////////////
    @Override
    public void addFlightInstance(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust,
            String actualDepTime, String actualArrTime, Integer actualDateAdjust) throws Exception {
        flightInst = new FlightInstance();
        Aircraft ac = em.find(Aircraft.class, "9V-000");    //default testing 
        flightInst.setAircraft(ac);
        flightInst.create(flightFrequency, date, flightStatus, estimatedDepTime, estimatedArrTime, estimatedDateAdjust, actualDepTime, actualArrTime, actualDateAdjust);

        String standardDepTime = flightFrequency.getScheduleArrTime();
        String standardArrTime = flightFrequency.getScheduleDepTime();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate stdDate = LocalDate.parse(date, dateFormat);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime depTime = LocalTime.parse(standardDepTime, timeFormat);
        LocalTime arrTime = LocalTime.parse(standardArrTime, timeFormat);

        LocalDateTime depDateTime = LocalDateTime.of(stdDate, depTime);
        LocalDateTime arrDateTime = LocalDateTime.of(stdDate, arrTime);

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("Combined departure time: " + depDateTime.format(sdf) + " and Combined arrival time: " + arrDateTime.format(sdf));
        flightInst.setStandardDepTime(depDateTime.format(sdf));
        flightInst.setStandardArrTime(arrDateTime.format(sdf));
//        System.out.println("flight scheduling bean: local departure date time: " + depDateTime+" and local arrival date time: "+arrDateTime);
//        ZonedDateTime stdDep = depDateTime.atZone(ZoneId.of("Europe/Berlin"));
//        ZonedDateTime stdArr = arrDateTime.atZone(ZoneId.of("UTC"));
//        System.out.println("flight scheduling bean: Zoned departure date time: Europe/Berlin " + stdDep+" and Zoned arrival date time: "+stdArr);
        em.persist(flightInst);
        em.flush();
        flightFrequency.getFlightList().add(flightInst);
        em.merge(flightInst);
        em.flush();
    }

    @Override
    public void editFlightInstance(FlightFrequency flightFrequency, String flightDate, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust,
            String actualDepTime, String actualArrTime, Integer actualDateAdjust) throws Exception {
        Query q = em.createQuery("SELECT fi FROM FlightInstance fi where fi.date =:flightDate");
        q.setParameter("flightDate", flightDate);
        if (q.getResultList().isEmpty()) {
            System.out.println("edit flight instance: Fight Instance does not exist.");
            throw new Exception("edit flight instance: Fight Instance does not exist.");
        }
        List<FlightInstance> flightInstList = q.getResultList();
        flightInst = flightInstList.get(0);
        flightInst.setFlightStatus(flightStatus);
        flightInst.setEstimatedDepTime(estimatedDepTime);
        flightInst.setEstimatedArrTime(estimatedArrTime);
        flightInst.setEstimatedDateAdjust(estimatedDateAdjust);
        flightInst.setActualDepTime(actualDepTime);
        flightInst.setActualArrTime(actualArrTime);
        flightInst.setActualDateAdjust(actualDateAdjust);
        em.merge(flightInst);
        em.flush();
    }

    @Override
    public Aircraft getAircraft(String registrationNo) {
        aircraft = em.find(Aircraft.class, registrationNo);
        return aircraft;
    }

    @Override
    public List<FlightInstance> getAllFlightInstance() {
        Query q1 = em.createQuery("SELECT fi FROM FlightInstance fi");
        List<FlightInstance> flightInstList = q1.getResultList();
        if (flightInstList.isEmpty()) {
            System.out.println("flightInstList: No flight instance.");
        } else {
            System.out.println("flightInstList got");
        }
        return flightInstList;
    }

    @Override
    public List<FlightInstance> getThisFlightInstance(String flightNo) {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightNo =:flightNo");
        q1.setParameter("flightNo", flightNo);
        if (q1.getResultList().isEmpty()) {
            System.out.println("This flight frequency does not exist.");
        }
        flightFreq = (FlightFrequency) q1.getResultList().get(0);
        Query q2 = em.createQuery("SELECT fi FROM FlightInstance fi where fi.flightFrequency=:flightFrequency").setParameter("flightFrequency", flightFreq);
        if (q2.getResultList().isEmpty()) {
            System.out.println("This flight instance deos not exist.");
        }
        return q2.getResultList();
    }

    @Override
    public void setCheckDate(Long id, String sDate, String fDate) {
        flightFreq = em.find(FlightFrequency.class, id);
        flightFreq.setsDate(sDate);
        flightFreq.setfDate(fDate);
    }

    ////////////////////////---------------------------------------------------------Hanyu Added-----------------------------------------------------------------
    @Override
    public List<FlightInstance> getUnplannedFlightInstance(Aircraft ac) {
        List<FlightInstance> flightInstList = getAllFlightInstance();
        List<FlightInstance> flightInstListCopy = new ArrayList<FlightInstance>();
        for (FlightInstance temp : flightInstList) {
            System.out.println("FSB: getUnplannedFlightInstance(): tempInfo: " + temp.getFlightFrequency().getFlightNo() + " " + temp.getDate());
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 1 :"+temp.getAircraft().getRegistrationNo().equals("9V-000"));
//            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :"+(temp.getAircraft() != null));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 3 :"+(temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType())));
            if ((temp.getAircraft().getRegistrationNo().equals("9V-000")) && (temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType()))) {
                System.out.println("FSB： getUnplannedFlightInstance(): ADDED " + temp.getFlightFrequency().getFlightNo() + " " + temp.getDate());
                flightInstListCopy.add(temp);

            }
        }
        System.out.println("FSB： getUnplannedFlightInstance(): return " + flightInstListCopy.toString());
        return flightInstListCopy;
    }

    @Override
    public List<Aircraft> getAllAircraft() {
        Query q1 = em.createQuery("SELECT ac FROM Aircraft ac");
        List<Aircraft> aircraftList = q1.getResultList();
        if (aircraftList.isEmpty()) {
            System.out.println("aircraftList: No aircraf.");
        } else {
            System.out.println("aircraftList got");
        }
        return aircraftList;
    }

    @Override
    public void scheduleAcToFi(Date startDate, Date endDate) throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        for (Aircraft acTemp : getAllAircraft()) {
            System.out.println("FSB: acTemp is "+acTemp.getAircraftType().toString());
            Date currentTime = startDate;    //the current available time of the aircraft
            Airport currentAirport = em.find(Airport.class, acTemp.getCurrentAirport());//need to add the new attribute:  currentAirport
            System.out.println("FSB: currentAirport: "+ currentAirport.getIATA());
            List<FlightInstance> unplannedFi = getUnplannedFlightInstance(acTemp);
            Collections.sort(unplannedFi);
            System.out.println("FSB:Sorted unplannedFi : "+  unplannedFi.toString());
            for (FlightInstance fiTemp : unplannedFi) {
                System.out.println("FSB: scheduleAcToFi(): currentTime is " + currentTime.toString());
                System.out.println("FSB: scheduleAcToFi(): endTime is " + endDate.toString());
                if (currentTime.after(endDate)||df1.parse(fiTemp.getStandardDepTime()).after(endDate)) {
                    break;
                } else {
                    //getDate(): should be replaced by getDepartureTime() and getArrivalTime()
                    if (currentTime.before(df1.parse(fiTemp.getStandardDepTime())) && currentAirport.equals(fiTemp.getFlightFrequency().getRoute().getOrigin())) {
                        System.out.println("FSB: Enter assignment process "+ fiTemp.getFlightFrequency().getFlightNo()+ " " + fiTemp.getDate());
                        fiTemp.setAircraft(acTemp);
                        currentTime = df1.parse(fiTemp.getStandardArrTime());
                        currentAirport = fiTemp.getFlightFrequency().getRoute().getDest();
                        acTemp.setCurrentAirport(currentAirport.getIATA());
                        List<FlightInstance> flightTemp = acTemp.getFlightInstance();
                        flightTemp.add(fiTemp);
                        acTemp.setFlightInstance(flightTemp);
                        em.merge(fiTemp);
                        em.merge(acTemp);
                        em.flush();
                    }
                }
            }
        }

    }
}
