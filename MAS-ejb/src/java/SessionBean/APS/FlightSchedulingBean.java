/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateful
public class FlightSchedulingBean implements FlightSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    RoutePlanningBeanLocal rpb;

    FlightFrequency flightFreq;
    FlightInstance flightInst;
    Aircraft aircraft;

    static String firstInstDate;
    static Boolean flag = true;
    private Calendar cal = new GregorianCalendar();

    public FlightSchedulingBean() {
    }

    @Override
    public FlightFrequency addFlightFrequency(Route route, String flightNo, String depTimeString, String arrTimeString, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDateString, String endDateString,
            String sDate, String fDate) throws Exception {
//        LocalDate startDate = startDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endDate = endDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        checkScheduleTime(depTimeString, arrTimeString, dateAdjust);
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

    public void checkScheduleTime(String depTimeString, String arrTimeString, Integer dateAdjust) throws Exception {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date dep = formatter.parse(depTimeString);
        System.out.println("fsb.checkScheduleTime(): " + dep);
        Date arr = formatter.parse(arrTimeString);
        System.out.println("fsb.checkScheduleTime(): " + arr);
        if (dateAdjust == 0) {
            if (dep.after(arr)) {
                System.out.println("Departure time should before arrival time.");
                throw new Exception("Departure time should before arrival time.");
            }
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
            throw new Exception("Flight Number: Flight number " + flightNo + " has already been used.");
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
            boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate) throws Exception {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightNo =:flightNo").setParameter("flightNo", flightNo);
        flightFreq = (FlightFrequency) q1.getSingleResult();
        checkScheduleTime(depTime, arrTime, dateAdjust);
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

//////////////////////////////////////////////////////////////////////////////////////added by luxixi///////////////////////////////////////////////////////////////////////////
    @Override
    public void addFlightInstance(FlightFrequency flightFrequency, String date, String flightStatus, String estimatedDepTime, String estimatedArrTime, Integer estimatedDateAdjust,
            String actualDepTime, String actualArrTime, Integer actualDateAdjust) throws Exception {
        flightInst = new FlightInstance();
        Aircraft ac = em.find(Aircraft.class, "9V-000");    //default testing 
        flightInst.setAircraft(ac);
        flightInst.create(flightFrequency, date, flightStatus, estimatedDepTime, estimatedArrTime, estimatedDateAdjust, actualDepTime, actualArrTime, actualDateAdjust);

        String standardDepTime = flightFrequency.getScheduleDepTime();
        String standardArrTime = flightFrequency.getScheduleArrTime();

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate stdDate = LocalDate.parse(date, dateFormat);

        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime depTime = LocalTime.parse(standardDepTime, timeFormat);
        LocalTime arrTime = LocalTime.parse(standardArrTime, timeFormat);

        LocalDateTime depDateTime = LocalDateTime.of(stdDate, depTime);
        LocalDateTime arrDateTime = LocalDateTime.of(stdDate, arrTime);

        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("Combined departure time: " + depDateTime.format(sdf) + " and Combined arrival time: " + arrDateTime.format(sdf));

        System.out.println("flightSchedulingBean: add flight instance: String type: Combined departure time: " + depDateTime.format(sdf) + " and Combined arrival time: " + arrDateTime.format(sdf));

        flightInst.setStandardDepTime(depDateTime.format(sdf));
        flightInst.setStandardArrTime(arrDateTime.format(sdf));

//        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        flightInst.setStandardDepTimeDateType(formatter.parse((standardDepTime)));
//        flightInst.setStandardArrTimeDateType(formatter.parse((standardArrTime)));
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        flightInst.setStandardDepTimeDateType(formatter.parse(depDateTime.format(sdf)));
        flightInst.setStandardArrTimeDateType(formatter.parse(arrDateTime.format(sdf)));
        System.out.println("flightSchedulingBean: add flight instance: Date type: Combined departure time: " + formatter.parse(depDateTime.format(sdf)) + " and Combined arrival time: " + formatter.parse(arrDateTime.format(sdf)));

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
        DateFormat df = new SimpleDateFormat("HH:mm");
        Date ed = df.parse(estimatedDepTime);
        Date ea = df.parse(estimatedArrTime);
        Date ad = df.parse(actualDepTime);
        Date aa = df.parse(actualArrTime);
        System.out.println("flightSchedulingBean: 4 time types: " + ed + " " + ea + " " + ad + " " + aa);
        Long estimatedDiff = (ea.getTime() + (60 * 60 * 1000 * 24 * estimatedDateAdjust) - ed.getTime()) / (60 * 60 * 1000) % 24; //hours differrence
        Long actualDiff = (aa.getTime() + (60 * 60 * 1000 * 24 * actualDateAdjust) - ad.getTime()) / (60 * 60 * 1000) % 24;
        System.out.println("flightSchedulingBean: time hour difference: estimated diff: " + estimatedDiff + " and actual diff: " + actualDiff);
        if ((ed.before(ea) && estimatedDateAdjust != 1) || (ed.compareTo(ea) == 0 && estimatedDateAdjust != 0) || (ed.after(ea) && estimatedDateAdjust == 1)) {
            if ((ad.before(aa) && actualDateAdjust != 1) || (ad.compareTo(aa) == 0 && actualDateAdjust != 0) || (ad.after(aa) && actualDateAdjust == 1)) {
                if (Math.abs(estimatedDiff - actualDiff) <= 2) {
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
                } else {
                    throw new Exception("The hour difference of estimated dates and actual dates cannot be different by more than 2h!");
                }
            } else {
                throw new Exception("Actual Dates are not valid! Please adjust.");
            }
        } else {
            throw new Exception("Estimated Dates are not valid! Please adjust.");
        }
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
            //    System.out.println("flightInstList got");
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
            System.out.println("This flight instance does not exist.");
        }
        return q2.getResultList();
    }

    @Override
    public void setCheckDate(Long id, String sDate, String fDate) {
        flightFreq = em.find(FlightFrequency.class, id);
        flightFreq.setsDate(sDate);
        flightFreq.setfDate(fDate);
    }

    @Override
    public FlightInstance findFlight(String flightNo, String flightDate) throws Exception {
        Query q1 = em.createQuery("SELECT f FROM FlightFrequency f WHERE f.flightNo =:flightNo");
        q1.setParameter("flightNo", flightNo);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("flightSchedulingBean: findFlight: Flight " + flightNo + " does not exist");
        }
        flightFreq = (FlightFrequency) q1.getResultList().get(0);
        Query q2 = em.createQuery("SELECT fi FROM FlightInstance fi where fi.date=:flightDate and  fi.flightFrequency=:flightFrequency");
        q2.setParameter("flightDate", flightDate);
        q2.setParameter("flightFrequency", flightFreq);
        if (q2.getResultList().isEmpty()) {
            throw new Exception("flightSchedulingBean: findFlight: " + flightNo + " does not operate on " + flightDate);
        }
        return (FlightInstance) q2.getResultList().get(0);
    }

    @Override
    public FlightInstance findFlight(Long flightId) {
        return em.find(FlightInstance.class, flightId);
    }

    @Override
    public Aircraft findAircraft(String serialNo) {
        return em.find(Aircraft.class, serialNo);
    }

    //---------------------------------------------------------Hanyu Added-----------------------------------------------------------------
    @Override
    public List<FlightInstance> getUnplannedFlightInstance(Aircraft ac) {
        List<FlightInstance> flightInstList = getAllFlightInstance();
        List<FlightInstance> flightInstListCopy = new ArrayList<FlightInstance>();
        for (FlightInstance temp : flightInstList) {
//            System.out.println("FSB: getUnplannedFlightInstance(): tempInfo: " + temp.getFlightFrequency().getFlightNo() + " " + temp.getDate());
//            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 1 :" + temp.getAircraft().getRegistrationNo().equals("9V-000"));
//            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType())));
            if ((temp.getAircraft().getRegistrationNo().equals("9V-000")) && (temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType()))) {
                System.out.println("FSB： getUnplannedFlightInstance(): ADDED " + temp.getFlightFrequency().getFlightNo() + " " + temp.getDate());
                flightInstListCopy.add(temp);

            }
        }
        System.out.println("FSB: getUnplannedFlightInstance(): return " + flightInstListCopy.toString());
        return flightInstListCopy;
    }

    // get all unplanned fight instances for all aircraft 
//    @Override
//    public List<FlightInstance> getUnplannedFiWithinPeriod(Date startDate, Date endDate) {
//        List<Aircraft> acList = new ArrayList<Aircraft>();
//        acList = getAllAircraft();
//        List<FlightInstance> newFlightInstList = new ArrayList<FlightInstance>();
//        for (Aircraft ac : acList) {
//            List<FlightInstance> flightInstList = getUnplannedFlightInstance(ac);
//            for (FlightInstance temp : flightInstList) {
//                if (temp.getStandardDepTimeDateType().after(startDate) && temp.getStandardArrTimeDateType().before(endDate)) {
//                    newFlightInstList.add(temp);
//                }
//            }
//        }
//        return newFlightInstList;
//    }
    @Override
    public List<FlightInstance> getUnplannedFiWithinPeriod(Date startDate, Date endDate) {
        List<FlightInstance> newFlightInstList = new ArrayList<FlightInstance>();
        for (FlightInstance temp : this.getAllFlightInstance()) {
            if (temp.getStandardDepTimeDateType().after(startDate) && temp.getStandardArrTimeDateType().before(endDate)) {
                if (temp.getAircraft().getRegistrationNo().equals("9V-000")) {
                    newFlightInstList.add(temp);
                }
            }
        }
        return newFlightInstList;
    }

    @Override
    public List<FlightInstance> getAllUnplannedFi() {
        List<FlightInstance> newFlightInstList = new ArrayList<FlightInstance>();
        for (FlightInstance temp : this.getAllFlightInstance()) {
            if (temp.getAircraft().getRegistrationNo().equals("9V-000")) {
                newFlightInstList.add(temp);
            }
        }
        return newFlightInstList;
    }

    @Override
    public List<Aircraft> getAllAircraft() {
        Query q1 = em.createQuery("SELECT ac FROM Aircraft ac");
        List<Aircraft> aircraftList = q1.getResultList();
        if (aircraftList.isEmpty()) {
            System.out.println("aircraftList: No aircraft.");
        } else {
            System.out.println("aircraftList got");
        }
        return aircraftList;
    }

    public List<Aircraft> getAllAircraft(Date startDate, Date endDate) throws Exception {
        Query q1 = em.createQuery("SELECT ac FROM Aircraft ac");
        List<Aircraft> aircraftList = q1.getResultList();
        List<Aircraft> newList = new ArrayList<Aircraft>();
        if (aircraftList.isEmpty()) {
            System.out.println("aircraftList: No aircraft.");
        } else {
            for (Aircraft ac : aircraftList) {
                newList.add(ac);
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                Date deliveryDate = df.parse(ac.getDeliveryDate());
                Date retireDate = df.parse(ac.getRetireDate());
                if (deliveryDate.after(startDate)) {
                    newList.remove(ac);
                } else if (retireDate.before(endDate)) {
                    newList.remove(ac);
                }
            }
            System.out.println("aircraftList got");
        }
        return newList;
    }

    @Override
    public void scheduleAcToFi(Date startDate, Date endDate) throws ParseException, Exception {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if (!flag) {
            System.out.println("EDIT firstInstDate!!");
            Calendar c = Calendar.getInstance();
            c.setTime(endDate);
            c.add(Calendar.DATE, 0);  // number of days to add
            firstInstDate = df1.format(c.getTime());
            System.out.println("EDIT firstInstDate to " + firstInstDate);
        }

        for (Aircraft acTemp : getAllAircraft()) {
            if (!acTemp.getRegistrationNo().equals("9V-000")) {
                System.out.println("FSB: acTemp is " + acTemp.getRegistrationNo() + " " + acTemp.getAircraftType().getType());

                List<FlightInstance> fiList0 = acTemp.getFlightInstance();
                Collections.sort(fiList0);
                List<Maintenance> mtList0 = acTemp.getMaintenanceList();
                Collections.sort(mtList0);
                Date lastFiEnd0 = (fiList0.size() > 0) ? fiList0.get(fiList0.size() - 1).getStandardArrTimeDateType() : Calendar.getInstance().getTime();
                Date lastMtEnd0 = (mtList0.size() > 0) ? mtList0.get(mtList0.size() - 1).getEndTime() : Calendar.getInstance().getTime();
                Date nextFree0 = (lastFiEnd0.after(lastMtEnd0)) ? lastFiEnd0 : lastMtEnd0;
                Date currentTime = (nextFree0.after(startDate)) ? nextFree0 : startDate;    //the current available time of the aircraft

                Airport currentAirport = em.find(Airport.class, acTemp.getCurrentAirport());//need to add the new attribute:  currentAirport
                System.out.println("FSB: currentAirport: " + currentAirport.getIATA());
                List<FlightInstance> unplannedFi = getUnplannedFlightInstance(acTemp);
                AircraftType acTempType = acTemp.getAircraftType();
                Airport sgAirport = em.find(Airport.class, "SIN");

                Collections.sort(unplannedFi);

                //check whether can add maintenance or not
                long acInH = acTempType.getAcInH();
                long acInC = acTempType.getAcInC();
                Integer acDu = acTempType.getAcDu();
                long bcInH = acTempType.getBcInH();
                long bcInC = acTempType.getBcInC();
                Integer bcDu = acTempType.getBcDu();
                long ccInH = acTempType.getCcInH();
                long ccInC = acTempType.getCcInC();
                Integer ccDu = acTempType.getCcDu();
                long dcInH = acTempType.getDcInH();
                long dcInC = acTempType.getDcInC();
                Integer dcDu = acTempType.getDcDu();

                // unused, want to see if mt is scheduled
                boolean scheduleA = true;
                boolean scheduleB = true;
                boolean scheduleC = true;
                boolean scheduleD = true;

                System.out.println("FSB:Sorted unplannedFi : " + unplannedFi.toString());
                for (FlightInstance fiTemp : unplannedFi) {
                    long acycleFM = acTemp.getAcycleFM();
                    long acycleFC = acTemp.getAcycleFC();
                    long bcycleFM = acTemp.getBcycleFM();
                    long bcycleFC = acTemp.getBcycleFC();
                    long ccycleFM = acTemp.getCcycleFM();
                    long ccycleFC = acTemp.getCcycleFC();
                    long dcycleFM = acTemp.getDcycleFM();
                    long dcycleFC = acTemp.getDcycleFC();

                    System.out.println("FSB: scheduleAcToFi(): currentTime is " + currentTime.toString());
                    System.out.println("FSB: scheduleAcToFi(): endTime is " + endDate.toString());
                    System.out.println("FSB: scheduleAcToFi(): flightInstance scheduled time is " + df1.parse(fiTemp.getStandardDepTime()).toString());
                    System.out.println("FSB: scheduleAcToFi(): Check Boolean1 " + currentTime.after(endDate));
                    System.out.println("FSB: scheduleAcToFi(): Check Boolean2 " + df1.parse(fiTemp.getStandardDepTime()).after(endDate));

                    // idle time: end time of last task/ maintenance
                    List<FlightInstance> fiList = acTemp.getFlightInstance();
                    Collections.sort(fiList);
                    List<Maintenance> mtList = acTemp.getMaintenanceList();
                    Collections.sort(mtList);
                    Date lastFiEnd = (fiList.size() > 0) ? fiList.get(fiList.size() - 1).getStandardArrTimeDateType() : Calendar.getInstance().getTime();
                    Date lastMtEnd = (mtList.size() > 0) ? mtList.get(mtList.size() - 1).getEndTime() : Calendar.getInstance().getTime();
                    Date nextFree = (lastFiEnd.after(lastMtEnd)) ? lastFiEnd : lastMtEnd;
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(nextFree);
                    c1.add(Calendar.HOUR, 1);
                    nextFree = c1.getTime();

                    // A check
                    System.out.println("FSB.scheduleAcToFi(): acyclehour = " + (acycleFM / 60) + " acycles = " + acycleFC);
                    //  System.out.println("FSB.scheduleAcToFi(): check Maintenance A Check boolean 1 is === " + ((acycleFM / 60) >= (acInH * 0.9) && (acycleFM / 60) <= (acInH * 1.1)));
                    //  System.out.println("FSB.scheduleAcToFi(): check Maintenance A Check boolean 2 is === " + (acycleFC >= (acInC * 0.9) && acycleFC <= (acInC * 1.1)));
                    if ((acycleFM / 60) >= acInH * 0.9 && (acycleFM / 60) <= acInH * 1.1 || acycleFC >= acInC * 0.9 && acycleFC <= acInC * 1.1) {
                        c1.setTime(nextFree);
                        c1.add(Calendar.HOUR, acDu);
                        Date thisMtEnd = c1.getTime();
                        Maintenance mta = new Maintenance();
                        mta.create(nextFree, thisMtEnd, "A Check");
                        mta.setAircraft(acTemp);
                        System.out.println("FSB.scheduleAcToFi(): create A check === " + mta);
                        mtList.add(mta);
                        acTemp.setMaintenanceList(mtList);
                        currentTime = thisMtEnd;
                        acTemp.setAcycleFM(0);
                        acTemp.setAcycleFC(0);
                        em.persist(mta);
                        em.merge(acTemp);
                        em.flush();
                        System.out.println("FSB.scheduleAcToFi(): add maintenance A Check detail === " + mta);
                    }

                    // B check
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance B Check boolean 1 is === " + ((acycleFM / 60) >= acInH * 0.9 && (acycleFM / 60) <= acInH * 1.1));
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance B Check boolean 2 is === " + (bcycleFC >= bcInC * 0.97 && bcycleFC <= bcInC * 1.03));
                    if ((bcycleFM / 60) >= bcInH * 0.97 && (bcycleFM / 60) <= bcInH * 1.03 || bcycleFC >= bcInC * 0.97 && bcycleFC <= bcInC * 1.03) {
                        if (currentAirport.equals(sgAirport)) {
                            c1.setTime(nextFree);
                            c1.add(Calendar.HOUR, bcDu);
                            Date thisMtEnd = c1.getTime();
                            Maintenance mtb = new Maintenance();
                            mtb.create(nextFree, thisMtEnd, "B Check");
                            mtb.setAircraft(acTemp);
                            System.out.println("FSB.scheduleAcToFi(): create B check === " + mtb);
                            mtList.add(mtb);
                            acTemp.setMaintenanceList(mtList);
                            currentTime = thisMtEnd;
                            acTemp.setBcycleFM(0);
                            acTemp.setBcycleFC(0);
                            em.persist(mtb);
                            em.merge(acTemp);
                            em.flush();
                            System.out.println("FSB.scheduleAcToFi(): add maintenance B Check detail ===" + mtb);
                        }
                    }

                    // C check
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance C Check boolean 1 is === " + ((ccycleFM / 60) >= ccInH * 0.98 && (ccycleFM / 60) <= ccInH * 1.02));
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance C Check boolean 2 is === " + (ccycleFC >= ccInC * 0.98 && ccycleFC <= ccInC * 1.02));
                    if ((ccycleFM / 60) >= ccInH * 0.98 && (ccycleFM / 60) <= ccInH * 1.02 || ccycleFC >= ccInC * 0.98 && ccycleFC <= ccInC * 1.02) {
                        if (currentAirport.equals(sgAirport)) {
                            c1.setTime(nextFree);
                            c1.add(Calendar.HOUR, ccDu);
                            Date thisMtEnd = c1.getTime();
                            Maintenance mtc = new Maintenance();
                            mtc.create(nextFree, thisMtEnd, "C Check");
                            mtc.setAircraft(acTemp);
                            System.out.println("FSB.scheduleAcToFi(): create C check === " + mtc);
                            mtList.add(mtc);
                            acTemp.setMaintenanceList(mtList);
                            currentTime = thisMtEnd;
                            acTemp.setCcycleFM(0);
                            acTemp.setCcycleFC(0);
                            em.persist(mtc);
                            em.merge(acTemp);
                            em.flush();
                            System.out.println("FSB.scheduleAcToFi(): add maintenance C Check detail ===" + mtc);
                        }
                    }

                    // D check
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance D Check boolean 1 is === " + ((dcycleFM / 60) >= dcInH * 0.99 && (dcycleFM / 60) <= dcInH * 1.01));
                    //    System.out.println("FSB.scheduleAcToFi(): check Maintenance D Check boolean 2 is === " + (dcycleFC >= dcInC * 0.99 && dcycleFC <= dcInC * 1.01));
                    if ((dcycleFM / 60) >= dcInH * 0.99 && (dcycleFM / 60) <= dcInH * 1.01 || dcycleFC >= dcInC * 0.99 && dcycleFC <= dcInC * 1.01) {
                        if (currentAirport.equals(sgAirport)) {
                            c1.setTime(nextFree);
                            c1.add(Calendar.HOUR, dcDu);
                            Date thisMtEnd = c1.getTime();
                            Maintenance mtd = new Maintenance();
                            mtd.create(nextFree, thisMtEnd, "D Check");
                            mtd.setAircraft(acTemp);
                            System.out.println("FSB.scheduleAcToFi(): create D check === " + mtd);
                            mtList.add(mtd);
                            acTemp.setMaintenanceList(mtList);
                            currentTime = thisMtEnd;
                            acTemp.setDcycleFM(0);
                            acTemp.setDcycleFC(0);
                            em.persist(mtd);
                            em.merge(acTemp);
                            em.flush();
                            System.out.println("FSB.scheduleAcToFi(): add maintenance D Check detail ===" + mtd);
                        }
                    }

                    if (currentTime.after(endDate) || df1.parse(fiTemp.getStandardDepTime()).after(endDate)) {
                        System.out.println("FSB: scheduleAcToFi(): Break! ");
                        break;
                    } else {
                        System.out.println("FSB: scheduleAcToFi(): Check Boolean3 " + currentTime.before(df1.parse(fiTemp.getStandardDepTime())));
                        System.out.println("FSB: scheduleAcToFi(): Check Boolean4 " + currentAirport.equals(fiTemp.getFlightFrequency().getRoute().getOrigin()));
                        //check whether currenTime is at least two hours-->one hour earlier that the next departure
                        Date temp = currentTime;
                        Calendar c = Calendar.getInstance();
                        c.setTime(temp);
                        c.add(Calendar.HOUR, 1);  // number of hours to add
                        temp = c.getTime();
                        System.out.println("FSB: scheduleAcToFi(): 1 hours later? " + temp.toString());
                        if (temp.before(df1.parse(fiTemp.getStandardDepTime())) && currentAirport.equals(fiTemp.getFlightFrequency().getRoute().getOrigin())) {
                            System.out.println("FSB: ----------> Enter assignment process " + fiTemp.getFlightFrequency().getFlightNo() + " " + fiTemp.getDate());

                            fiTemp.setAircraft(acTemp);
                            currentTime = df1.parse(fiTemp.getStandardArrTime());
                            currentAirport = fiTemp.getFlightFrequency().getRoute().getDest();
                            acTemp.setCurrentAirport(currentAirport.getIATA());
                            List<FlightInstance> flightTemp = acTemp.getFlightInstance();
                            flightTemp.add(fiTemp);
                            acTemp.setFlightInstance(flightTemp);

                            // add fi duration to ABCD check cycle flight hour
                            long fiMin = getFlightAccumMinute(fiTemp.getFlightFrequency());
                            long min = acTemp.getAcycleFM();
                            min += fiMin;
                            acTemp.setAcycleFM(min);
                            System.out.println("========== ACycleFM " + min);
                            min = acTemp.getBcycleFM();
                            min += fiMin;
                            acTemp.setBcycleFM(min);
                            System.out.println("========== BCycleFM " + min);
                            min = acTemp.getCcycleFM();
                            min += fiMin;
                            acTemp.setCcycleFM(min);
                            System.out.println("========== CCycleFM " + min);
                            min = acTemp.getDcycleFM();
                            min += fiMin;
                            acTemp.setDcycleFM(min);
                            System.out.println("========== DCycleFM " + min);

                            long cycleCount = acTemp.getAcycleFC();
                            acTemp.setAcycleFC(++cycleCount);
                            cycleCount = acTemp.getBcycleFC();
                            acTemp.setBcycleFC(++cycleCount);
                            cycleCount = acTemp.getCcycleFC();
                            acTemp.setCcycleFC(++cycleCount);
                            cycleCount = acTemp.getDcycleFC();
                            acTemp.setDcycleFC(++cycleCount);

//                            setCycleFlightDays(acTemp, "A Check", currentTime);
//                            setCycleFlightDays(acTemp, "B Check", currentTime);
//                            setCycleFlightDays(acTemp, "C Check", currentTime);
//                            setCycleFlightDays(acTemp, "D Check", currentTime);
                            em.merge(fiTemp);
                            em.merge(acTemp);
                            em.flush();
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean addMtToAc(Aircraft act, String obj, Date mtStart, Date mtEnd) throws Exception {
        Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:default").setParameter("default", act.getRegistrationNo());
        Aircraft ac = (Aircraft) q1.getResultList().get(0);
        boolean flag1 = canAssignMt(ac, obj, mtStart, mtEnd);
        List<Maintenance> mtTemp = ac.getMaintenanceList();
        System.out.println("FSB: addMtToAc ");
        System.out.println("FSB: addMtToAc " + flag1);
        if (flag1) {
            Maintenance newMt = new Maintenance();
            newMt.create(mtStart, mtEnd, obj);
            newMt.setAircraft(ac);
            System.err.println("FSB: addMtToAc " + "finish creating mt " + newMt.toString());

            em.persist(newMt);
            em.flush();
            System.err.println("FSB: addMtToAc " + "mt in database " + newMt.toString());

            mtTemp.add(newMt);
            ac.setMaintenanceList(mtTemp);

            switch (obj.charAt(0)) {
                case 'A': {
                    if (!hasMtAfterThis(ac, newMt)) {
                        ac.setAcycleFM(0);
                        ac.setAcycleFC(0);
                    }
                    break;
                }
                case 'B': {
                    if (!hasMtAfterThis(ac, newMt)) {
                        ac.setBcycleFM(0);
                        ac.setBcycleFC(0);
                    }
                    break;
                }
                case 'C': {
                    if (!hasMtAfterThis(ac, newMt)) {
                        ac.setCcycleFM(0);
                        ac.setCcycleFC(0);
                    }
                    break;
                }
                case 'D': {
                    if (!hasMtAfterThis(ac, newMt)) {
                        ac.setDcycleFM(0);
                        ac.setDcycleFC(0);
                    }
                    break;
                }
                default:
                    break;
            }
            em.merge(ac);
            em.flush();
        }
        return flag1;
    }

    public boolean hasMtAfterThis(Aircraft ac, Maintenance mt) {
        boolean has = false;
        List<Maintenance> mtTemp = ac.getMaintenanceList();
        for (Maintenance m : mtTemp) {
            if (m.getObjective().equals(mt.getObjective())) {
                if (m.getStartTime().after(mt.getStartTime())) {
                    has = true;
                }
            }
        }
        return has;
    }

    @Override
    public boolean addAcToFi(Aircraft ac, FlightInstance fi) throws ParseException {
        boolean flag = canAssign(ac, fi);
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("FSB: addAcToFi ");
        System.out.println("FSB: addAcToFi " + flag);
        if (flag) {
            flightTemp.add(fi);
            ac.setFlightInstance(flightTemp);

            // update maintenance counter
            long fiMin = getFlightAccumMinute(fi.getFlightFrequency());
            long min = ac.getAcycleFM();
            min += fiMin;
            ac.setAcycleFM(min);
            min = ac.getBcycleFM();
            min += fiMin;
            ac.setBcycleFM(min);
            min = ac.getCcycleFM();
            min += fiMin;
            ac.setCcycleFM(min);
            min = ac.getDcycleFM();
            min += fiMin;
            ac.setDcycleFM(min);
            long cycleCount = ac.getAcycleFC();
            ac.setAcycleFC(++cycleCount);
            cycleCount = ac.getBcycleFC();
            ac.setBcycleFC(++cycleCount);
            cycleCount = ac.getCcycleFC();
            ac.setCcycleFC(++cycleCount);
            cycleCount = ac.getDcycleFC();
            ac.setDcycleFC(++cycleCount);

            Collections.sort(flightTemp);
            Date lastFiEnd = (flightTemp.size() > 0) ? flightTemp.get(flightTemp.size() - 1).getStandardArrTimeDateType() : Calendar.getInstance().getTime();
            Date lastLastFiEnd = (lastFiEnd.before(fi.getStandardArrTimeDateType())) ? fi.getStandardArrTimeDateType() : lastFiEnd;
//            setCycleFlightDays(ac, "A Check", lastLastFiEnd);
//            setCycleFlightDays(ac, "B Check", lastLastFiEnd);
//            setCycleFlightDays(ac, "C Check", lastLastFiEnd);
//            setCycleFlightDays(ac, "D Check", lastLastFiEnd);

            fi.setAircraft(ac);
            em.merge(fi);
            em.merge(ac);
            System.out.print("FSB: addToFi finished!");
            em.flush();
        }
        return flag;
    }

    public boolean canAssignMt(Aircraft ac, String obj, Date startTime, Date endTime) throws Exception {
        boolean canAssign = false;
        boolean canAssignMt = false;
        List<FlightInstance> flightTempBeforeSort = ac.getFlightInstance();
        List<Maintenance> mtTempBeforeSort = ac.getMaintenanceList();
        Airport sgAirport = em.find(Airport.class, "SIN");

//        for (FlightInstance f : flightTemp) {
//            System.err.println("CHECK BEFORE SORTING *************************************** " + f.getStandardDepTimeDateType() + " ~~~ " + f.getStandardArrTimeDateType());
//        }
//        Collections.sort(flightTemp);
//         for (FlightInstance f : flightTemp) {
//            System.err.println("CHECK AFTER SORTING *************************************** " + f.getStandardDepTimeDateType() + " ~~~ " + f.getStandardArrTimeDateType());
//        }
//        Collections.sort(mtTemp);
        // Sort flightinstance list
        List<Date> listDates = new ArrayList<>();
        for (FlightInstance fitest : flightTempBeforeSort) {
            listDates.add(fitest.getStandardDepTimeDateType());
        }

        Collections.sort(listDates);

        List<FlightInstance> flightTemp = new ArrayList();
        for (int k = 0; k < listDates.size(); k++) {
            for (int j = 0; j < flightTempBeforeSort.size(); j++) {
                if (flightTempBeforeSort.get(j).getStandardDepTimeDateType().equals(listDates.get(k))) {
                    flightTemp.add(flightTempBeforeSort.get(j));
                }
            }
        }

        // Sort maintenance list
        List<Date> listDates2 = new ArrayList<>();
        for (Maintenance mttest : mtTempBeforeSort) {
            listDates2.add(mttest.getStartTime());
        }

        Collections.sort(listDates2);

        List<Maintenance> mtTemp = new ArrayList();
        for (int k = 0; k < listDates2.size(); k++) {
            for (int j = 0; j < mtTempBeforeSort.size(); j++) {
                if (mtTempBeforeSort.get(j).getStartTime().equals(listDates2.get(k))) {
                    mtTemp.add(mtTempBeforeSort.get(j));
                }
            }
        }

        for (FlightInstance f : flightTemp) {
            System.err.println("CHECK AFTER SORTING *************************************** " + f.getStandardDepTimeDateType() + " ~~~ " + f.getStandardArrTimeDateType());
        }

        boolean isAC = (obj.charAt(0) == 'A');
        System.err.println("canAssignMt(): is A check ? " + isAC);

        Date startCheck = startTime;
        Date endCheck = endTime;
        cal.setTime(startCheck);
        cal.add(Calendar.HOUR, -1);
        startCheck = cal.getTime();
        cal.setTime(endCheck);
        cal.add(Calendar.HOUR, 1);
        endCheck = cal.getTime();

        // check time conflict with maintenance
        if (mtTemp.isEmpty()) {
            canAssignMt = true;
            System.err.println("********************************* canAssignMt(): pass in CHECK 1");
        } else if (mtTemp.size() == 1) {
            if (endCheck.before(mtTemp.get(0).getStartTime()) || startCheck.after(mtTemp.get(0).getEndTime())) {
                canAssignMt = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 2");
            }
        } else {
            if (endCheck.before(mtTemp.get(0).getStartTime())) {
                canAssignMt = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 3");
            }
            for (int i = 0; i < mtTemp.size() - 2; i++) {
                Maintenance mt1 = mtTemp.get(i);
                Maintenance mt2 = mtTemp.get(i + 1);
                if (startCheck.after(mt1.getEndTime()) && endCheck.before(mt2.getStartTime())) {
                    canAssignMt = true;
                    System.err.println("********************************* canAssignMt(): pass in CHECK 4");
                }
            }
            if (startCheck.after(mtTemp.get(mtTemp.size() - 1).getEndTime())) {
                canAssignMt = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 5");

            }
        }

        // check time conflict with flight instances
        if (flightTemp.isEmpty()) {
            if (isAC || ac.getCurrentAirport().equals("SIN")) {
                canAssign = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 6");

            }
        } else if (flightTemp.size() == 1) {
            if (((isAC || flightTemp.get(0).getFlightFrequency().getRoute().getOrigin().getIATA().equals("SIN")) && endCheck.before(flightTemp.get(0).getStandardDepTimeDateType()))
                    || (isAC || flightTemp.get(0).getFlightFrequency().getRoute().getDest().getIATA().equals("SIN")) && startCheck.after(flightTemp.get(0).getStandardArrTimeDateType())) {
                canAssign = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 7");

            }
        } else {
            if (endCheck.before(flightTemp.get(0).getStandardDepTimeDateType()) && (isAC || ac.getCurrentAirport().equals("SIN"))) {
                canAssign = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 8");

            }
            for (int i = 0; i < flightTemp.size() - 2; i++) {
                FlightInstance f1 = flightTemp.get(i);
                FlightInstance f2 = flightTemp.get(i + 1);
//                if(startCheck.after(flightTemp.get(flightTemp.size()-1).getStandardArrTimeDateType()) && (isAC || flightTemp.get(flightTemp.size()-1).getFlightFrequency().getRoute().getDest().getIATA().equals("SIN"))) {}
                if (isAC || f1.getFlightFrequency().getRoute().getDest().getIATA().equals("SIN")) {
                    if (startCheck.after(f1.getStandardArrTimeDateType()) && endCheck.before(f2.getStandardDepTimeDateType())) {
                        System.err.println("************ check last inst arr time " + f1.getStandardArrTimeDateType());
                        System.err.println("************ check next inst dep time " + f2.getStandardDepTimeDateType());
                        canAssign = true;
                        System.err.println("********************************* canAssignMt(): pass in CHECK 9");

                    }
                }
            }
            if (startCheck.after(flightTemp.get(flightTemp.size() - 1).getStandardArrTimeDateType()) && (isAC || flightTemp.get(flightTemp.size() - 1).getFlightFrequency().getRoute().getDest().getIATA().equals("SIN"))) {
                canAssignMt = true;
                System.err.println("********************************* canAssignMt(): pass in CHECK 10");

            }
        }

        return (canAssign && canAssignMt);
    }

    public boolean canAssign(Aircraft ac, FlightInstance fi) {
        boolean canAssign = false;
        boolean canAssignMt = false;
        System.out.println("canAssign: fi.dep " + fi.getStandardDepTime() + " fi.arrival " + fi.getStandardArrTime());
        System.out.println("canAssign: fi.origin " + fi.getFlightFrequency().getRoute().getOrigin().getIATA() + " fi.arrival " + fi.getFlightFrequency().getRoute().getDest().getIATA());
        System.out.println("canAssign: CHECK 1");
        List<FlightInstance> flightTemp = ac.getFlightInstance();
//        Collections.sort(flightTemp);
        System.out.println("canAssign: CHECK 2 " + flightTemp.size());
        List<Maintenance> mtTempBeforeSort = ac.getMaintenanceList();
//        Collections.sort(mtTemp);

        
        List<Date> listDates2 = new ArrayList<>();
        for (Maintenance mttest : mtTempBeforeSort) {
            listDates2.add(mttest.getStartTime());
        }

        Collections.sort(listDates2);

        List<Maintenance> mtTemp = new ArrayList();
        for (int k = 0; k < listDates2.size(); k++) {
            for (int j = 0; j < mtTempBeforeSort.size(); j++) {
                if (mtTempBeforeSort.get(j).getStartTime().equals(listDates2.get(k))) {
                    mtTemp.add(mtTempBeforeSort.get(j));
                }
            }
        }

        // add 1 hour after and delete 1 hour before 
        Date depCheck = fi.getStandardDepTimeDateType();
        Date arrCheck = fi.getStandardArrTimeDateType();
        cal.setTime(depCheck);
        cal.add(Calendar.HOUR, -1);
        depCheck = cal.getTime();
        cal.setTime(arrCheck);
        cal.add(Calendar.HOUR, 1);
        arrCheck = cal.getTime();

        if (mtTemp.isEmpty()) {
            canAssignMt = true;
            System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
        } else if (mtTemp.size() == 1) {
            if (arrCheck.before(mtTemp.get(0).getStartTime()) || depCheck.after(mtTemp.get(0).getEndTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 2 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
//            if (mtTemp.get(0).getObjective().charAt(0) == 'A') {
//                if (fi.getStandardArrTimeDateType().before(mtTemp.get(0).getStartTime()) || fi.getStandardDepTimeDateType().after(mtTemp.get(0).getEndTime())) {
//                    canAssignMt = true;
//                }
//            } else {
//                if (fi.getStandardArrTimeDateType().before(mtTemp.get(0).getStartTime()) && fi.getFlightFrequency().getRoute().getDest().getIATA().equals("SIN")) {
//                    canAssignMt = true;
//                } else if (fi.getStandardDepTimeDateType().after(mtTemp.get(0).getEndTime()) && fi.getFlightFrequency().getRoute().getOrigin().getIATA().equals("SIN")) {
//                    canAssignMt = true;
//                }
//            }
        } else {
            if (arrCheck.before(mtTemp.get(0).getStartTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 3 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
            for (int i = 0; i < mtTemp.size() - 2; i++) {
                Maintenance mt1 = mtTemp.get(i);
                Maintenance mt2 = mtTemp.get(i + 1);
                if (depCheck.after(mt1.getEndTime()) && arrCheck.before(mt2.getStartTime())) {
                    canAssignMt = true;
                    System.out.println("canAssign: CHECK 4 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
                }
            }
            if (depCheck.after(mtTemp.get(mtTemp.size() - 1).getEndTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 5 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
        }

        if (flightTemp.isEmpty()) {
            if (ac.getAircraftType().equals(fi.getFlightFrequency().getRoute().getAcType()) && ac.getCurrentAirport().equals(fi.getFlightFrequency().getRoute().getOrigin().getIATA())) {
                canAssign = true;
                System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
        } else if (flightTemp.size() != 1) {
            System.out.println("canAssign: CHECK 3");

            List<Date> listDates = new ArrayList<>();
            for (FlightInstance fitest : flightTemp) {
                listDates.add(fitest.getStandardDepTimeDateType());
            }

            System.out.println("Before sorting: " + listDates);
            Collections.sort(listDates);
            System.out.println("After sorting: " + listDates);

            System.out.println("flightTemp before Sort:" + flightTemp.toString());
            List<FlightInstance> newList = new ArrayList();
            for (int k = 0; k < listDates.size(); k++) {
                for (int j = 0; j < flightTemp.size(); j++) {
                    if (flightTemp.get(j).getStandardDepTimeDateType().equals(listDates.get(k))) {
                        newList.add(flightTemp.get(j));
                    }
                }
            }
            System.out.println("flightTemp after Sort:" + newList.toString());
            System.out.println("canAssign: CHECK 4");
            depCheck = new Date();
            arrCheck = new Date();
            System.out.println("canAssign: CHECK 5");
            for (int i = 0; i < newList.size() - 1; i++) {
                depCheck = newList.get(i).getStandardArrTimeDateType();
                arrCheck = newList.get(i + 1).getStandardDepTimeDateType();
                System.out.println("canAssign: depDate" + depCheck);
                System.out.println("canAssign: arrCheck" + arrCheck);
                cal = Calendar.getInstance();
                // last arrival shoulbe be ahead of the fi's departure at least 2 hours-->1 hour
                cal.setTime(depCheck);
                cal.add(Calendar.HOUR, 1);
                depCheck = cal.getTime();
                System.out.println("Literal 1 check: " + (depCheck.before(fi.getStandardArrTimeDateType())));
                System.out.println("Literal 2 check: " + newList.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin()));
                System.out.println("Literal 2 check flightTemp : " + newList.get(i).getFlightFrequency().getRoute().getDest().getIATA());
                System.out.println("Literal 2 check fi: " + fi.getFlightFrequency().getRoute().getOrigin().getIATA());
                //if add in front of first of fiList
                if (fi.getStandardArrTimeDateType().before(newList.get(0).getStandardDepTimeDateType()) && fi.getFlightFrequency().getRoute().getDest().equals(newList.get(0).getFlightFrequency().getRoute().getOrigin())) {
                    if (ac.getCurrentAirport().equals(fi.getFlightFrequency().getRoute().getOrigin().getIATA())) {
                        System.out.println("canAssign: CHECK 2 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
                        canAssign = true;
                    }
                    //if not the first of fiList
                } else if ((depCheck.before(fi.getStandardDepTimeDateType())) && newList.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin())) {
                    System.out.println("canAssign: CHECK 6");
                    // if it is not the last in flighttemp, next departure shoulbe at least 2 hours-->1 hour later than the fi's arrival
                    if (i + 1 < newList.size()) {
                        cal.setTime(arrCheck);
                        cal.add(Calendar.HOUR, -1);
                        arrCheck = cal.getTime();
                        if ((fi.getStandardArrTimeDateType().before(arrCheck)) && newList.get(i + 1).getFlightFrequency().getRoute().getOrigin().equals(fi.getFlightFrequency().getRoute().getDest())) {
                            System.out.println("canAssign: CHECK 3 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
                            canAssign = true;
                        }
                    } else {  //it is the last in flighttemp, can anyway assign
                        System.out.println("canAssign: CHECK 4 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
                        canAssign = true;
                    }
                }
            }
        } else {   // if fiList.size=1
            if (fi.getStandardArrTimeDateType().before(flightTemp.get(0).getStandardDepTimeDateType()) && fi.getFlightFrequency().getRoute().getDest().equals(flightTemp.get(0).getFlightFrequency().getRoute().getOrigin())) {
                canAssign = true;
                System.out.println("canAssign: CHECK 5 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            } else if (fi.getStandardDepTimeDateType().after(flightTemp.get(0).getStandardArrTimeDateType()) && fi.getFlightFrequency().getRoute().getOrigin().equals(flightTemp.get(0).getFlightFrequency().getRoute().getDest())) {
                canAssign = true;
                System.out.println("canAssign: CHECK 6 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }

        }
        return (canAssign && canAssignMt);
    }

    @Override
    public void deleteAcFromFi(Aircraft ac, FlightInstance fi) {
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        // update maintenance counter
        long fiMin = getFlightAccumMinute(fi.getFlightFrequency());
        long min = ac.getAcycleFM();
        min -= fiMin;
        ac.setAcycleFM(min);
        min = ac.getBcycleFM();
        min -= fiMin;
        ac.setBcycleFM(min);
        min = ac.getCcycleFM();
        min -= fiMin;
        ac.setCcycleFM(min);
        min = ac.getDcycleFM();
        min -= fiMin;
        ac.setDcycleFM(min);
        long cycleCount = ac.getAcycleFC();
        ac.setAcycleFC(--cycleCount);
        cycleCount = ac.getBcycleFC();
        ac.setBcycleFC(--cycleCount);
        cycleCount = ac.getCcycleFC();
        ac.setCcycleFC(--cycleCount);
        cycleCount = ac.getDcycleFC();
        ac.setDcycleFC(--cycleCount);

        flightTemp.remove(fi);
        ac.setFlightInstance(flightTemp);
        Aircraft acTemp = em.find(Aircraft.class, "9V-000");
        fi.setAircraft(acTemp);
        em.merge(fi);
        em.merge(ac);
        em.flush();
    }

    @Override
    public void deleteMtFromAc(Aircraft ac, Maintenance mt) {
        Query q1 = em.createQuery("SELECT a FROM Maintenance a where a.id=:id").setParameter("id", mt.getId());
        Maintenance mtM = (Maintenance) q1.getResultList().get(0);
        List<Maintenance> mtTemp = ac.getMaintenanceList();
        mtTemp.remove(mtM);
        ac.setMaintenanceList(mtTemp);
        em.remove(mtM);
        em.merge(ac);
        em.flush();
    }

    @Override
    public List<FlightInstance> getUnassignedFlight() {
        Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:default").setParameter("default", "9V-000");
        Aircraft a = (Aircraft) q1.getResultList().get(0);
        Query q2 = em.createQuery("SELECT fi FROM FlightInstance fi where fi.aircraft=:default").setParameter("default", a);
        return (List<FlightInstance>) q2.getResultList();
    }

    public void setFirstInstDate() throws ParseException {
        System.out.println("FSB: setFirstInstDate for the first time!!!");
        List<FlightInstance> fiList = new ArrayList<FlightInstance>();
        fiList = getAllFlightInstance();
        Collections.sort(fiList);
        firstInstDate = fiList.get(0).getStandardDepTime();
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date temp = df1.parse(firstInstDate);
        Calendar c = Calendar.getInstance();
        c.setTime(temp);
        c.add(Calendar.DATE, -1);  // number of days to add
        firstInstDate = df1.format(c.getTime());
        System.out.println("FSB: firstInstDate: " + firstInstDate);
    }

    @Override
    public String getFirstInstDate() {
        System.out.println("FSB: Flag: " + flag);
        System.out.println("FSB:getFirstInstDate()" + firstInstDate);
        if (flag) {
            try {
                setFirstInstDate();
            } catch (ParseException ex) {
                Logger.getLogger(FlightSchedulingBean.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            flag = false;
        }
        return firstInstDate;
    }

    @Override
    public FlightInstance getDummyFi(String outOrIn) {
        if (outOrIn.equals("outbound")) {
            Query q1 = em.createQuery("SELECT f FROM FlightInstance f where f.id=:id").setParameter("id", 1000000);
            return (FlightInstance) q1.getSingleResult();
        } else {
            Query q1 = em.createQuery("SELECT f FROM FlightInstance f where f.id=:id").setParameter("id", 1000001);
            return (FlightInstance) q1.getSingleResult();
        }
    }

    @Override
    public long getFlightAccumMinute(FlightFrequency ff) {
        LocalTime depTime = LocalTime.parse(ff.getScheduleDepTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime arrTime = LocalTime.parse(ff.getScheduleArrTime(), DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate depDate = LocalDate.of(2000, 1, 10);
        LocalDate arrDate = LocalDate.of(2000, 1, 10).plusDays(ff.getDateAdjust());
        LocalDateTime depDateTime = LocalDateTime.of(depDate, depTime);
        LocalDateTime arrDateTime = LocalDateTime.of(arrDate, arrTime);
        long diffInMinutes = java.time.Duration.between(depDateTime, arrDateTime).toMinutes();
        long diffInHours = java.time.Duration.between(depDateTime, arrDateTime).toHours();
        System.out.println("fsb.getFlightAccumHour(): flight elapsed hour is : " + diffInHours);
        System.out.println("fsb.getFlightAccumHour(): flight elapsed minute is : " + diffInMinutes);
        return diffInMinutes;
    }

//    public void setCycleFlightDays(Aircraft ac, String checkType, Date fiEnd) throws ParseException {
//        List<Maintenance> mtList = ac.getMaintenanceList();
//        List<Maintenance> mtSelect = new ArrayList<>();
//        for (Maintenance mt : mtList) {
//            if (mt.getObjective().equalsIgnoreCase(checkType)) {
//                mtSelect.add(mt);
//            }
//        }
//        Collections.sort(mtSelect);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
//        // Convert last mt date to localdate type
//        Date firstFlyDate = df2.parse(ac.getFirstFlyDate());
//        Date last = (mtSelect.size() > 0) ? mtSelect.get(mtSelect.size() - 1).getEndTime() : firstFlyDate;
//        String lastDateString = df.format(last);
//        LocalDate lastLocalDate = LocalDate.parse(lastDateString, formatter);
//        // Convert flight end date to localdate type
//        String fiEndDateString = df.format(fiEnd);
//        LocalDate fiEndLocalDate = LocalDate.parse(fiEndDateString, formatter);
//        long days = ChronoUnit.DAYS.between(lastLocalDate, fiEndLocalDate);
//        if (checkType.charAt(0) == 'A') {
//            ac.setAcycleFD(days);
//            System.out.println("FSB.setCycleFlightDays(): " + ac.getRegistrationNo() + " A check cycle flight days passed " + days);
//        } else if (checkType.charAt(0) == 'B') {
//            ac.setBcycleFD(days);
//            System.out.println("FSB.setCycleFlightDays(): " + ac.getRegistrationNo() + " B check cycle flight days passed " + days);
//        } else if (checkType.charAt(0) == 'C') {
//            ac.setCcycleFD(days);
//            System.out.println("FSB.setCycleFlightDays(): " + ac.getRegistrationNo() + " C check cycle flight days passed " + days);
//        } else if (checkType.charAt(0) == 'D') {
//            ac.setDcycleFD(days);
//            System.out.println("FSB.setCycleFlightDays(): " + ac.getRegistrationNo() + " D check cycle flight days passed " + days);
//        }
//        em.merge(ac);
//        em.flush();
//    }
//    public boolean canAssign(Aircraft ac, FlightInstance fi) {
//        boolean canAssign = false;
//        System.out.println("canAssign: fi.dep " + fi.getStandardDepTime() + " fi.arrial " + fi.getStandardArrTime());
//        System.out.println("canAssign: fi.origin " + fi.getFlightFrequency().getRoute().getOrigin().getIATA() + " fi.arrial " + fi.getFlightFrequency().getRoute().getDest().getIATA());
//        System.out.println("canAssign: CHECK 1");
//        List<FlightInstance> flightTemp = ac.getFlightInstance();
//        System.out.println("canAssign: CHECK 2 " + flightTemp.size());
//
//        if (flightTemp.size() == 0) {
//            if (ac.getAircraftType().equals(fi.getFlightFrequency().getRoute().getAcType()) && ac.getCurrentAirport().equals(fi.getFlightFrequency().getRoute().getOrigin().getIATA())) {
//                canAssign = true;
//            }
//        } else {
//            System.out.println("canAssign: CHECK 3");
//        
//            List<Date> listDates = new ArrayList<Date>();
//            for(FlightInstance fitest: flightTemp){
//               listDates.add(fitest.getStandardDepTimeDateType());
//                
//            }
////            System.out.println("Before sorting: " + listDates);
////            Collections.sort(listDates);
////            System.out.println("After sorting: " + listDates);
////            
////            List<FlightInstance> newList=new ArrayList();
////            for(int k=0;k<listDates.size();k++){
////                for(int j=0;j<flightTemp.size();j++){
////                    if (flightTemp.get(j).getStandardDepTimeDateType().equals(listDates.get(k)))
////                        newList.add(flightTemp.get(j));
////                } 
////            }
//            
//            System.out.println("flightTemp before Sort:" + flightTemp.toString()+ " "+flightTemp.getClass().toString());
////                Collections.sort(flightTemp);
//            IndirectList iList=(IndirectList) flightTemp;
//            
//            Collections.sort((List<FlightInstance>)flightTemp, new Comparator<FlightInstance>() {
//                public int compare(FlightInstance f1, FlightInstance f2) {
//                    System.out.println("&&&&&&Sort&&&&&&&&&&&&");
//
//                    int result = f1.getStandardDepTimeDateType().compareTo(f2.getStandardDepTimeDateType());
//                    System.out.println("&&&&&&&RESTULT&&&&&&&&& " + result);
//                    return result;
//                }
//            });
//
//            System.out.println("flightTemp after Sort:" + flightTemp.toString());
//            System.out.println("canAssign: CHECK 4");
//            Date depCheck = new Date();
//            Date arrCheck = new Date();
//            System.out.println("canAssign: CHECK 5");
//            for (int i = 0; i < flightTemp.size() - 1; i++) {
//                depCheck = flightTemp.get(i).getStandardArrTimeDateType();
//                arrCheck = flightTemp.get(i + 1).getStandardDepTimeDateType();
//                System.out.println("canAssign: depDate" + depCheck);
//                System.out.println("canAssign: arrCheck" + arrCheck);
//                cal = Calendar.getInstance();
//                // last arrival shoulbe be ahead of the fi's departure at least 2 hours
//                cal.setTime(depCheck);
//                cal.add(Calendar.HOUR, 2);
//                depCheck = cal.getTime();
//                System.out.println("Literal 1 check: " + (depCheck.before(fi.getStandardArrTimeDateType())));
//                System.out.println("Literal 2 check: " + flightTemp.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin()));
//                System.out.println("Literal 2 check flightTemp : " + flightTemp.get(i).getFlightFrequency().getRoute().getDest().getIATA());
//                System.out.println("Literal 2 check fi: " + fi.getFlightFrequency().getRoute().getOrigin().getIATA());
//                //if (fi.getStandardArrTimeDateType().before(flightTemp.get(0).))
//
//                if ((depCheck.before(fi.getStandardDepTimeDateType())) && flightTemp.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin())) {
//                    System.out.println("canAssign: CHECK 6");
//                    // if it is not the last in flighttemp, next departure shoulbe at least 2 hours later than the fi's arrival
//                    if (i + 1 < flightTemp.size()) {
//                        cal.setTime(arrCheck);
//                        cal.add(Calendar.HOUR, -2);
//                        arrCheck = cal.getTime();
//                        if ((fi.getStandardArrTimeDateType().before(arrCheck)) && flightTemp.get(i + 1).getFlightFrequency().getRoute().getOrigin().equals(fi.getFlightFrequency().getRoute().getDest())) {
//                            canAssign = true;
//                        }
//                    } else {  //it is the last in flighttemp, can anyway assign
//                        System.out.println("canAssign: CHECK 7");
//                        canAssign = true;
//                    }
//                }
//            }
//        }
//        return canAssign;
//    }
}
