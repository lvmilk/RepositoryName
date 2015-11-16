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
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
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

    @EJB
    CrewSchedulingBeanLocal csb;

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
            String sDate, String fDate, String depTerminal, String arrTerminal) throws Exception {
//        LocalDate startDate = startDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate endDate = endDateString.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        checkScheduleTime(depTimeString, arrTimeString, dateAdjust);
        checkOperationDate(startDateString, endDateString);

        flightFreq = new FlightFrequency();
        flightFreq.create(route, flightNo, depTimeString, arrTimeString, dateAdjust, onMon, onTue, onWed, onThu, onFri, onSat, onSun, startDateString, endDateString, sDate, fDate, depTerminal, arrTerminal);
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
            boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate, String depTerminal, String arrTerminal) throws Exception {
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
        flightFreq.setDepTerminal(depTerminal);
        flightFreq.setArrTerminal(arrTerminal);

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
//        Query q = em.createQuery("SELECT fi FROM FlightInstance fi where fi.date =:date");
//        q.setParameter("date", date);
//        if (!q.getResultList().isEmpty()) {
//            throw new Exception("add flight instance: Fight Instance has already existed!");
//        }
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
            String actualDepTime, String actualArrTime, Integer actualDateAdjust, String depGate) throws Exception {
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
                    flightInst.setDepGate(depGate);
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
    public void setCheckDate(Long id, String sDate, String fDate) throws Exception {

        flightFreq = em.find(FlightFrequency.class, id);
        if (flightFreq == null) {
            throw new Exception("!!!!!!!!!!!!!!!!!!!Check Date No Frequency");
        } else {
            flightFreq.setsDate(sDate);
            flightFreq.setfDate(fDate);
        }
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
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 1 :" + temp.getAircraft().getRegistrationNo().equals("9V-000"));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType())));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency()));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency().getRoute()));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency().getRoute().getAcType()));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (ac.getAircraftType()));

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

    public List<FlightInstance> sortFiList(List<FlightInstance> fi) {
        List<FlightInstance> flightTempBeforeSort = fi;
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
        return flightTemp;
    }

    public List<Maintenance> sortMtList(List<Maintenance> mt) {
        List<Maintenance> mtTempBeforeSort = mt;
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
        return mtTemp;
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
                fiList0 = sortFiList(fiList0);
                List<Maintenance> mtList0 = acTemp.getMaintenanceList();
                mtList0 = sortMtList(mtList0);
                Date lastFiEnd0 = (fiList0.size() > 0) ? fiList0.get(fiList0.size() - 1).getStandardArrTimeDateType() : Calendar.getInstance().getTime();
                Date lastMtEnd0 = (mtList0.size() > 0) ? mtList0.get(mtList0.size() - 1).getEndTime() : Calendar.getInstance().getTime();
                Date nextFree0 = (lastFiEnd0.after(lastMtEnd0)) ? lastFiEnd0 : lastMtEnd0;
                Date currentTime = (nextFree0.after(startDate)) ? nextFree0 : startDate;    //the current available time of the aircraft

                Airport currentAirport = em.find(Airport.class, acTemp.getCurrentAirport());//need to add the new attribute:  currentAirport
                System.out.println("FSB: currentAirport: " + currentAirport.getIATA());
                List<FlightInstance> unplannedFi = getUnplannedFlightInstance(acTemp);
                AircraftType acTempType = acTemp.getAircraftType();
                Airport sgAirport = em.find(Airport.class, "SIN");

                unplannedFi = sortFiList(unplannedFi);

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
                    fiList = sortFiList(fiList);
                    List<Maintenance> mtList = acTemp.getMaintenanceList();
                    mtList = sortMtList(mtList);
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
                        Integer acMH = acTemp.getAircraftType().getAcMH();
                        mta.create(nextFree, thisMtEnd, acMH, "A Check");
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
                            Integer bcMH = acTemp.getAircraftType().getBcMH();
                            mtb.create(nextFree, thisMtEnd, bcMH, "B Check");
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
                            Integer ccMH = acTemp.getAircraftType().getCcMH();
                            mtc.create(nextFree, thisMtEnd, ccMH, "C Check");
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
                            Integer dcMH = acTemp.getAircraftType().getDcMH();
                            mtd.create(nextFree, thisMtEnd, dcMH, "D Check");
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
    public boolean addMtToAc(Aircraft act, String obj, Date mtStart, Date mtEnd, Integer manhour) throws Exception {
        Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:default").setParameter("default", act.getRegistrationNo());
        Aircraft ac = (Aircraft) q1.getResultList().get(0);
        boolean flag1 = canAssignMt(ac, obj, mtStart, mtEnd);
        List<Maintenance> mtTemp = ac.getMaintenanceList();
        System.out.println("FSB: addMtToAc ");
        System.out.println("FSB: addMtToAc " + flag1);
        if (flag1) {
            Maintenance newMt = new Maintenance();
            newMt.create(mtStart, mtEnd, manhour, obj);
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

    public boolean addAcToFi(Aircraft ac, FlightInstance fi) {
//        boolean flag = canAssign(ac, fi);
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("FSB: addAcToFi ");
        System.out.println("FSB: addAcToFi " + flag);
        if (flag) {
            flightTemp.add(fi);
            ac.setFlightInstance(flightTemp);

            // update maintenance counter
            List<Maintenance> mtList = ac.getMaintenanceList();
            List<Maintenance> amtList = new ArrayList();
            List<Maintenance> bmtList = new ArrayList();
            List<Maintenance> cmtList = new ArrayList();
            List<Maintenance> dmtList = new ArrayList();

            for (Maintenance mt : mtList) {
                switch (mt.getObjective().charAt(0)) {
                    case 'A':
                    case 'a':
                        amtList.add(mt);
                        break;
                    case 'B':
                    case 'b':
                        bmtList.add(mt);
                        break;
                    case 'C':
                    case 'c':
                        cmtList.add(mt);
                        break;
                    case 'D':
                    case 'd':
                        dmtList.add(mt);
                        break;
                    default:
                        break;
                }
            }
            amtList = sortMtList(amtList);
            bmtList = sortMtList(bmtList);
            cmtList = sortMtList(cmtList);
            dmtList = sortMtList(dmtList);
            Date amtLast = new Date();
            Date bmtLast = new Date();
            Date cmtLast = new Date();
            Date dmtLast = new Date();
            if (!amtList.isEmpty()) {
                amtLast = mtList.get(amtList.size() - 1).getStartTime();
            }
            if (!bmtList.isEmpty()) {
                bmtLast = mtList.get(bmtList.size() - 1).getStartTime();
            }
            if (!cmtList.isEmpty()) {
                cmtLast = mtList.get(cmtList.size() - 1).getStartTime();
            }
            if (!dmtList.isEmpty()) {
                dmtLast = mtList.get(dmtList.size() - 1).getStartTime();
            }
            long fiMin = getFlightAccumMinute(fi.getFlightFrequency());
            long min = 0;
            long cycleCount = 0;
            if (amtList.isEmpty() || fi.getStandardDepTimeDateType().after(amtLast)) {
                min = ac.getAcycleFM();
                min += fiMin;
                ac.setAcycleFM(min);
                cycleCount = ac.getAcycleFC();
                ac.setAcycleFC(++cycleCount);
            }
            if (bmtList.isEmpty() || fi.getStandardDepTimeDateType().after(bmtLast)) {
                min = ac.getBcycleFM();
                min += fiMin;
                ac.setBcycleFM(min);
                cycleCount = ac.getBcycleFC();
                ac.setBcycleFC(++cycleCount);
            }
            if (cmtList.isEmpty() || fi.getStandardDepTimeDateType().after(cmtLast)) {
                min = ac.getCcycleFM();
                min += fiMin;
                ac.setCcycleFM(min);
                cycleCount = ac.getCcycleFC();
                ac.setCcycleFC(++cycleCount);

            }
            if (dmtList.isEmpty() || fi.getStandardDepTimeDateType().after(dmtLast)) {
                min = ac.getDcycleFM();
                min += fiMin;
                ac.setDcycleFM(min);
                cycleCount = ac.getDcycleFC();
                ac.setDcycleFC(++cycleCount);
            }

            flightTemp = sortFiList(flightTemp);
            Date lastFiEnd = (flightTemp.size() > 0) ? flightTemp.get(flightTemp.size() - 1).getStandardArrTimeDateType() : Calendar.getInstance().getTime();
            Date lastLastFiEnd = (lastFiEnd.before(fi.getStandardArrTimeDateType())) ? fi.getStandardArrTimeDateType() : lastFiEnd;

            fi.setAircraft(ac);
            em.merge(fi);
            em.merge(ac);
            System.out.print("FSB: addToFi finished!");
            em.flush();
        }
        return flag;
    }

    @Override
    public boolean addAcToFi(Aircraft ac, List<Long> fiId) throws Exception {
        System.out.println(" IMPTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTESTING " + fiId.get(0).getClass().getSimpleName());
        String flag = canAssign(ac, fiId);
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("FSB: addAcToFi ");
        System.out.println("FSB: addAcToFi " + flag);

        if (flag.equalsIgnoreCase("canAssign")) {
            List<FlightInstance> fiToAdd = new ArrayList<>();
            for (Long id : fiId) {

                FlightInstance f1 = em.find(FlightInstance.class, id);
                fiToAdd.add(f1);
                flightTemp.add(f1);
            }
            fiToAdd = this.sortFiList(fiToAdd);
            Date fiStart = fiToAdd.get(0).getStandardDepTimeDateType();
            ac.setFlightInstance(flightTemp);

            // update maintenance counter
            List<Maintenance> mtList = ac.getMaintenanceList();
            List<Maintenance> amtList = new ArrayList();
            List<Maintenance> bmtList = new ArrayList();
            List<Maintenance> cmtList = new ArrayList();
            List<Maintenance> dmtList = new ArrayList();

            for (Maintenance mt : mtList) {
                switch (mt.getObjective().charAt(0)) {
                    case 'A':
                    case 'a':
                        amtList.add(mt);
                        break;
                    case 'B':
                    case 'b':
                        bmtList.add(mt);
                        break;
                    case 'C':
                    case 'c':
                        cmtList.add(mt);
                        break;
                    case 'D':
                    case 'd':
                        dmtList.add(mt);
                        break;
                    default:
                        break;
                }
            }
            amtList = sortMtList(amtList);
            bmtList = sortMtList(bmtList);
            cmtList = sortMtList(cmtList);
            dmtList = sortMtList(dmtList);
            Date amtLast = new Date();
            Date bmtLast = new Date();
            Date cmtLast = new Date();
            Date dmtLast = new Date();
            if (!amtList.isEmpty()) {
                amtLast = mtList.get(amtList.size() - 1).getStartTime();
            }
            if (!bmtList.isEmpty()) {
                bmtLast = mtList.get(bmtList.size() - 1).getStartTime();
            }
            if (!cmtList.isEmpty()) {
                cmtLast = mtList.get(cmtList.size() - 1).getStartTime();
            }
            if (!dmtList.isEmpty()) {
                dmtLast = mtList.get(dmtList.size() - 1).getStartTime();
            }

            long fiMinA = 0;
            long fiMinB = 0;
            long fiMinC = 0;
            long fiMinD = 0;
            long cyclePlusA = 0;
            long cyclePlusB = 0;
            long cyclePlusC = 0;
            long cyclePlusD = 0;

            for (FlightInstance f1 : fiToAdd) {
                FlightFrequency ff1 = f1.getFlightFrequency();
                if (f1.getStandardDepTimeDateType().after(amtLast)) {
                    fiMinA += getFlightAccumMinute(ff1);
                    ++cyclePlusA;
                }
                if (f1.getStandardDepTimeDateType().after(bmtLast)) {
                    fiMinB += getFlightAccumMinute(ff1);
                    ++cyclePlusB;
                }
                if (f1.getStandardDepTimeDateType().after(cmtLast)) {
                    fiMinC += getFlightAccumMinute(ff1);
                    ++cyclePlusC;
                }
                if (f1.getStandardDepTimeDateType().after(dmtLast)) {
                    fiMinD += getFlightAccumMinute(ff1);
                    ++cyclePlusD;
                }
            }

            long min = 0;
            long cycleCount = 0;

            min = ac.getAcycleFM();
            min += fiMinA;
            ac.setAcycleFM(min);
            cycleCount = ac.getAcycleFC();
            cycleCount += cyclePlusA;
            ac.setAcycleFC(cycleCount);

            min = ac.getBcycleFM();
            min += fiMinB;
            ac.setBcycleFM(min);
            cycleCount = ac.getBcycleFC();
            cycleCount += cyclePlusB;
            ac.setBcycleFC(cycleCount);

            min = ac.getCcycleFM();
            min += fiMinC;
            ac.setCcycleFM(min);
            cycleCount = ac.getCcycleFC();
            cycleCount += cyclePlusC;
            ac.setCcycleFC(cycleCount);

            min = ac.getDcycleFM();
            min += fiMinD;
            ac.setDcycleFM(min);
            cycleCount = ac.getDcycleFC();
            cycleCount += cyclePlusD;
            ac.setDcycleFC(cycleCount);

            for (FlightInstance fi : fiToAdd) {
                fi.setAircraft(ac);
                em.merge(fi);
            }
            em.merge(ac);
            System.out.print("FSB: addToFi finished!");
            em.flush();
        } else {
            throw new Exception(flag);
        }
        return true;
    }

    public boolean canAssignMt(Aircraft ac, String obj, Date startTime, Date endTime) throws Exception {
        boolean canAssign = false;
        boolean canAssignMt = false;
        List<FlightInstance> flightTempBeforeSort = ac.getFlightInstance();
        List<Maintenance> mtTempBeforeSort = ac.getMaintenanceList();
        Airport sgAirport = em.find(Airport.class, "SIN");
        List<FlightInstance> flightTemp = this.sortFiList(flightTempBeforeSort);
        List<Maintenance> mtTemp = this.sortMtList(mtTempBeforeSort);

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

    public String canAssign(Aircraft ac, List<Long> fiId) {
//        boolean canAssign = false;
//        boolean canAssign2 = false;
//        boolean canAssignMt = false;

        List<FlightInstance> fiTempBeforeSort = ac.getFlightInstance();
        List<FlightInstance> flightTemp = this.sortFiList(fiTempBeforeSort);
        System.out.println(flightTemp);
        List<Maintenance> mtTempBeforeSort = ac.getMaintenanceList();
        List<Maintenance> mtTemp = this.sortMtList(mtTempBeforeSort);

        List<FlightInstance> fiToAdd = new ArrayList<>();
        for (Long id : fiId) {
            FlightInstance f1 = em.find(FlightInstance.class, id);
            fiToAdd.add(f1);
        }
        fiToAdd = this.sortFiList(fiToAdd);

        // ********************* check whether these flights are consecutive
        // by time
        // check 1: if these flights are overlapping
        for (int i = 0; i < fiToAdd.size() - 1; i++) {
            FlightInstance f1 = fiToAdd.get(i);
            FlightInstance f2 = fiToAdd.get(i + 1);

            Date depCheck = f1.getStandardDepTimeDateType();
            Date arrCheck = f1.getStandardArrTimeDateType();
            cal.setTime(depCheck);
            cal.add(Calendar.HOUR, -1);
            depCheck = cal.getTime();
            cal.setTime(arrCheck);
            cal.add(Calendar.HOUR, 1);
            arrCheck = cal.getTime();
            Date depCheck2 = f2.getStandardDepTimeDateType();
            Date arrCheck2 = f2.getStandardArrTimeDateType();
            cal.setTime(depCheck2);
            cal.add(Calendar.HOUR, -1);
            depCheck2 = cal.getTime();
            cal.setTime(arrCheck2);
            cal.add(Calendar.HOUR, 1);
            arrCheck2 = cal.getTime();

            if (depCheck.after(depCheck2) && depCheck.before(arrCheck2) || depCheck2.after(depCheck) && depCheck2.before(arrCheck)) {
                System.out.println("FIRST CHECK ***********************************");
                System.out.println("Cannot assign 01: overlapping time of selected tasks");
                return "Cannot assign : overlapping time of selected tasks";
            }
        }

        // check 2: if these flights has existing tasks in between
        List<FlightInstance> fiListAdd = new ArrayList<>();
        for (FlightInstance f : flightTemp) {
            fiListAdd.add(f);
        }
        for (FlightInstance f1 : fiToAdd) {
            fiListAdd.add(f1);
        }
        fiListAdd = this.sortFiList(fiListAdd);
        Integer findFirst = fiListAdd.indexOf(fiToAdd.get(0));
        Integer findLast = fiListAdd.indexOf(fiToAdd.get(fiToAdd.size() - 1));
        if ((findLast - findFirst) != (fiToAdd.size() - 1)) {
            System.out.println("FIRST CHECK ***********************************");
            System.out.println("Cannot assign 02: selected flights are not consecutive (have existing flights inbetween).");
            return "Cannot assign : selected flights are not consecutive (have existing flights inbetween).";
        }

        // by location
        for (int i = 0; i < fiToAdd.size() - 1; i++) {
            FlightInstance f1 = fiToAdd.get(i);
            FlightInstance f2 = fiToAdd.get(i + 1);
//            String dep1 = f1.getFlightFrequency().getRoute().getOrigin().getIATA();
            String arr1 = f1.getFlightFrequency().getRoute().getDest().getIATA();
            String dep2 = f2.getFlightFrequency().getRoute().getOrigin().getIATA();
//            String arr2 = f2.getFlightFrequency().getRoute().getDest().getIATA();
            if (!arr1.equals(dep2)) {
                System.out.println("FIRST CHECK ***********************************");
                System.out.println("Cannot assign 03: non-connecting location of selected tasks");
                return "Cannot assign : non-connecting location of selected tasks";
            }
        }

        // ********************* ready to check time and location conflict
        Date depCheck = fiToAdd.get(0).getStandardDepTimeDateType();
        Date arrCheck = fiToAdd.get(0).getStandardArrTimeDateType();
        cal.setTime(depCheck);
        cal.add(Calendar.HOUR, -1);
        cal.add(Calendar.SECOND, 1);
        depCheck = cal.getTime();
        cal.setTime(arrCheck);
        cal.add(Calendar.HOUR, 1);
        arrCheck = cal.getTime();
        Date depCheck2 = fiToAdd.get(fiToAdd.size() - 1).getStandardDepTimeDateType();
        cal.setTime(depCheck2);
        cal.add(Calendar.HOUR, -1);
        depCheck2 = cal.getTime();
        Date arrCheck2 = fiToAdd.get(fiToAdd.size() - 1).getStandardArrTimeDateType();
        cal.setTime(arrCheck2);
        cal.add(Calendar.HOUR, 1);
        cal.add(Calendar.SECOND, -1);
        arrCheck2 = cal.getTime();
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>888888888888888888888888888888888888 BEFORE CHECK: depCheck " + depCheck);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>888888888888888888888888888888888888 BEFORE CHECK: arrCheck " + arrCheck);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>888888888888888888888888888888888888 BEFORE CHECK: depCheck2 " + depCheck2);
        System.out.println(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>888888888888888888888888888888888888 BEFORE CHECK: arrCheck2 " + arrCheck2);

        // check maintenance
        List<Maintenance> checkMt = new ArrayList<>();
        for (Maintenance mt : mtTemp) {
            if (mt.getEndTime().after(depCheck) || mt.getStartTime().before(arrCheck2)) {
                checkMt.add(mt);
            }
        }
        if (checkMt.isEmpty()) {
//            canAssignMt = true;
            System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
        } else {
            // check time conflict
            for (int i = 0; i < checkMt.size(); i++) {
                Date st = checkMt.get(i).getStartTime();
                Date nd = checkMt.get(i).getEndTime();
                for (int j = 0; j < fiToAdd.size(); j++) {
                    Date fiSt = fiToAdd.get(j).getStandardDepTimeDateType();
                    Date fiNd = fiToAdd.get(j).getStandardArrTimeDateType();
                    cal.setTime(fiSt);
                    cal.add(Calendar.HOUR, -1);
                    fiSt = cal.getTime();
                    cal.setTime(fiNd);
                    cal.add(Calendar.HOUR, 1);
                    fiNd = cal.getTime();

                    // check time conflict with maintenances
                    // have conflict: return false directly
                    if (st.after(fiSt) && st.before(fiNd) || nd.after(fiSt) && nd.before(fiNd)) {
                        System.out.println("SECOND CHECK ***********************************");
                        System.out.println("Cannot assign 04: has maintenance inbetween these flights");
                        return "Cannot assign : has maintenance during these flights";

                    }
                }
            }
            // check location conflict
            for (int i = 0; i < checkMt.size(); i++) {
                Date st = checkMt.get(i).getStartTime();
                Date nd = checkMt.get(i).getEndTime();
                boolean isAC = (checkMt.get(i).getObjective().charAt(0) == 'A');
                if (!isAC) {
                    List<FlightInstance> fiBefMt = new ArrayList<>();
                    for (int j = 0; i < fiToAdd.size(); j++) {
                        Date fiNd = fiToAdd.get(j).getStandardArrTimeDateType();
                        cal.setTime(fiNd);
                        cal.add(Calendar.HOUR, 1);
                        fiNd = cal.getTime();
                        if (fiNd.before(st)) {
                            fiBefMt.add(fiToAdd.get(j));
                        }
                        fiBefMt = this.sortFiList(fiBefMt);
                        if (!fiBefMt.get(fiBefMt.size() - 1).getFlightFrequency().getRoute().getDest().getIATA().equalsIgnoreCase("SIN")) {
                            System.out.println("SECOND CHECK ***********************************");
                            System.out.println("Cannot assign 05: flight instance change the location of an existing maintenance " + checkMt.get(i).getObjective() + " from " + checkMt.get(i).getStartTime() + " to " + checkMt.get(i).getEndTime());
                            return "Cannot assign : flight instance change the location of an existing maintenance " + checkMt.get(i).getObjective() + " from " + checkMt.get(i).getStartTime() + " to " + checkMt.get(i).getEndTime();

                        }
                    }
                }
            }
        }

        // check flight instances
        // check aircraft type is correct
        for (FlightInstance f1 : fiToAdd) {
            if (!f1.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType())) {
                System.out.println("THIRD CHECK ***********************************");
                System.out.println("Cannot assign 06: aircraft " + ac.getRegistrationNo() + " / " + ac.getAircraftType().getType() + " does not have suitable aircraft type for flight task " + f1.getFlightFrequency().getFlightNo());
                return "Cannot assign : aircraft " + ac.getRegistrationNo() + " / " + ac.getAircraftType().getType() + " does not have suitable aircraft type for flight task " + f1.getFlightFrequency().getFlightNo();
            }
        }

        // check time
        System.out.println("AAAAAAAAA  --  " + flightTemp);
        for (FlightInstance f1 : flightTemp) {
            Date dep1 = f1.getStandardDepTimeDateType();
            Date arr1 = f1.getStandardArrTimeDateType();

            if ((dep1.after(depCheck) && dep1.before(arrCheck2)) || (arr1.before(arrCheck2) && arr1.after(depCheck))) {
                System.out.println("THIRD CHECK ***********************************");
                System.out.println("THIRD CHECK *********************************** dep 1 " + f1.getStandardDepTimeDateType());
                System.out.println("THIRD CHECK *********************************** arr 1 " + f1.getStandardArrTimeDateType());

                System.out.println("THIRD CHECK *********************************** boolean 1 " + (dep1.after(depCheck) && dep1.before(arrCheck2)));
                System.out.println("THIRD CHECK *********************************** boolean 2 " + (arr1.before(arrCheck2) && arr1.after(depCheck)));

                System.out.println("Cannot assign 07: selected tasks have time conflict with existing flight " + f1 + " for " + ac.getRegistrationNo());
                return "Cannot assign : selected tasks have time conflict with existing flight " + f1 + " for " + ac.getRegistrationNo();
            }
        }

        // check location
        List<FlightInstance> fiBefore = new ArrayList<>();
        List<FlightInstance> fiAfter = new ArrayList<>();
        for (FlightInstance f1 : flightTemp) {
            System.out.println("（）（）（）DEBUGING f1 dep time " + f1.getStandardDepTime());
            System.out.println("（）（）（）DEBUGING f1 arr time " + f1.getStandardArrTime());
//            if (f1.getStandardArrTimeDateType().before(depCheck) || f1.getStandardArrTimeDateType().equals(depCheck)) {
            if (f1.getStandardArrTimeDateType().before(depCheck)) {
                fiBefore.add(f1);
            } else if (f1.getStandardDepTimeDateType().after(arrCheck2)) {
                fiAfter.add(f1);
            }
        }
        fiBefore = this.sortFiList(fiBefore);
        System.out.println("（）（）（）################# ################ fiBefore " + fiBefore);
        fiAfter = this.sortFiList(fiAfter);
        System.out.println("（）（）（）################# ################ fiAfter " + fiAfter);

        if (!fiBefore.isEmpty()) {
            if (!fiBefore.get(fiBefore.size() - 1).getFlightFrequency().getRoute().getDest().equals(fiToAdd.get(0).getFlightFrequency().getRoute().getOrigin())) {
                System.out.println("THIRD CHECK ***********************************");
                System.out.println("Cannot assign 08: selected tasks have location conflict with existing flights for " + ac.getRegistrationNo());
                return "Cannot assign : selected tasks have location conflict with existing flights for " + ac.getRegistrationNo();
            }
        }

        if (!fiAfter.isEmpty()) {
            if (!fiAfter.get(0).getFlightFrequency().getRoute().getOrigin().equals(fiToAdd.get(fiToAdd.size() - 1).getFlightFrequency().getRoute().getDest())) {
                System.out.println("THIRD CHECK ***********************************");
                System.out.println("THIRD CHECK *********************************** one location " + fiToAdd.get(fiToAdd.size() - 1).getFlightFrequency().getRoute().getDest());
                System.out.println("THIRD CHECK *********************************** the other location " + fiAfter.get(0).getFlightFrequency().getRoute().getOrigin());
                System.out.println("Cannot assign 09: selected tasks have location conflict with existing flights for " + ac.getRegistrationNo());
                return "Cannot assign : selected tasks have location conflict with existing flights for " + ac.getRegistrationNo();
            }
        }
        return "canAssign";
    }

    public boolean canAssign(Aircraft ac, FlightInstance fi, FlightInstance fiSec) {
        boolean canAssign = false;
        boolean canAssignMt = false;
        boolean canAssignMt2 = false;
        boolean canAssignMt3 = false;
        System.out.println("canAssign: fi.dep " + fi.getStandardDepTime() + " fi.arrival " + fi.getStandardArrTime());
        System.out.println("canAssign: fi.origin " + fi.getFlightFrequency().getRoute().getOrigin().getIATA() + " fi.arrival " + fi.getFlightFrequency().getRoute().getDest().getIATA());
        System.out.println("canAssign: CHECK 1");
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("canAssign: CHECK 2 " + flightTemp.size());
        List<Maintenance> mtTempBeforeSort = ac.getMaintenanceList();
        List<Maintenance> mtTemp = this.sortMtList(mtTempBeforeSort);

        Date depCheck = fi.getStandardDepTimeDateType();
        Date arrCheck = fi.getStandardArrTimeDateType();
        cal.setTime(depCheck);
        cal.add(Calendar.HOUR, -1);
        depCheck = cal.getTime();
        cal.setTime(arrCheck);
        cal.add(Calendar.HOUR, 1);
        arrCheck = cal.getTime();
        Date depCheck2 = fi.getStandardDepTimeDateType();
        Date arrCheck2 = fi.getStandardArrTimeDateType();
        cal.setTime(depCheck2);
        cal.add(Calendar.HOUR, -1);
        depCheck2 = cal.getTime();
        cal.setTime(arrCheck2);
        cal.add(Calendar.HOUR, 1);

        if (depCheck.after(depCheck2) && depCheck.before(arrCheck2) || depCheck2.after(depCheck) && depCheck2.before(arrCheck)) {
            System.out.println("FIRST CHECK ***********************************");
            System.out.println("Cannot assign : overlapping time of selected tasks");
            return false;
        }

        FlightInstance first = (depCheck.before(depCheck2)) ? fi : fiSec;
        FlightInstance sec = (depCheck.before(depCheck2)) ? fiSec : fi;
        fi = first;
        fiSec = sec;

        String dep1 = fi.getFlightFrequency().getRoute().getOrigin().getIATA();
        String arr1 = fi.getFlightFrequency().getRoute().getDest().getIATA();
        String dep2 = fiSec.getFlightFrequency().getRoute().getOrigin().getIATA();
        String arr2 = fi.getFlightFrequency().getRoute().getDest().getIATA();

        List<FlightInstance> fiListAdd = flightTemp;
        fiListAdd.add(fi);
        fiListAdd.add(fiSec);
        fiListAdd = this.sortFiList(fiListAdd);

        if ((fiListAdd.indexOf(fiSec) - fiListAdd.indexOf(fi)) == 1) {
            if (!arr1.equalsIgnoreCase(dep2)) {
                System.out.println("FIRST CHECK ***********************************");
                System.out.println("Cannot assign : different location of two consecutive flights");
                return false;
            }
        }

        // Check maintenance one
        if (mtTemp.isEmpty()) {
            canAssignMt = true;
            System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
        } else if (mtTemp.size() == 1) {
            if (arrCheck.before(mtTemp.get(0).getStartTime()) || depCheck.after(mtTemp.get(0).getEndTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 2 >>>>>>>>>>>>Check Mt>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
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

        // Check maintenance two
        if (mtTemp.isEmpty()) {
            canAssignMt2 = true;
            System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
        } else if (mtTemp.size() == 1) {
            if (arrCheck2.before(mtTemp.get(0).getStartTime()) || depCheck2.after(mtTemp.get(0).getEndTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 2 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
        } else {
            if (arrCheck2.before(mtTemp.get(0).getStartTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 3 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
            for (int i = 0; i < mtTemp.size() - 2; i++) {
                Maintenance mt1 = mtTemp.get(i);
                Maintenance mt2 = mtTemp.get(i + 1);
                if (depCheck2.after(mt1.getEndTime()) && arrCheck2.before(mt2.getStartTime())) {
                    canAssignMt = true;
                    System.out.println("canAssign: CHECK 4 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
                }
            }
            if (depCheck2.after(mtTemp.get(mtTemp.size() - 1).getEndTime())) {
                canAssignMt = true;
                System.out.println("canAssign: CHECK 5 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
        }

        // Check maintenance three: if the maintenance is in between two flights
        if (mtTemp.isEmpty()) {
            canAssignMt3 = true;
            System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Mt 2>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
        } else if (mtTemp.size() == 1) {
            if ((fiListAdd.indexOf(fiSec) - fiListAdd.indexOf(fi)) == 1) {
                boolean flagt1 = true;
                for (Maintenance mt : mtTemp) {
                    if (mt.getStartTime().after(arrCheck) && mt.getEndTime().before(depCheck2)) {
                        if (mt.getObjective().charAt(0) != 'A') {
                            if (!fi.getFlightFrequency().getRoute().getDest().getIATA().equalsIgnoreCase("SIN")) {
                                flagt1 = false;
                            }
                        }
                    }
                }
                canAssignMt3 = flagt1;
            } else {
                canAssignMt3 = true;
            }
        }

        if (flightTemp.isEmpty()) {
            if (ac.getAircraftType().equals(fi.getFlightFrequency().getRoute().getAcType()) && ac.getCurrentAirport().equals(fi.getFlightFrequency().getRoute().getOrigin().getIATA())) {
                canAssign = true;
                System.out.println("canAssign: CHECK 1 >>>>>>>>>>>>Check Fi>>>>>>>>>>>>>>>>>>>>>>>Can assign!");
            }
        } else if (flightTemp.size() != 1) {
            System.out.println("canAssign: CHECK 3");
            List<FlightInstance> newList = this.sortFiList(flightTemp);

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
        List<Maintenance> mtList = ac.getMaintenanceList();
        List<Maintenance> amtList = new ArrayList();
        List<Maintenance> bmtList = new ArrayList();
        List<Maintenance> cmtList = new ArrayList();
        List<Maintenance> dmtList = new ArrayList();

        for (Maintenance mt : mtList) {
            switch (mt.getObjective().charAt(0)) {
                case 'A':
                case 'a':
                    amtList.add(mt);
                    break;
                case 'B':
                case 'b':
                    bmtList.add(mt);
                    break;
                case 'C':
                case 'c':
                    cmtList.add(mt);
                    break;
                case 'D':
                case 'd':
                    dmtList.add(mt);
                    break;
                default:
                    break;
            }
        }
        amtList = sortMtList(amtList);
        bmtList = sortMtList(bmtList);
        cmtList = sortMtList(cmtList);
        dmtList = sortMtList(dmtList);
        Date amtLast = new Date();
        Date bmtLast = new Date();
        Date cmtLast = new Date();
        Date dmtLast = new Date();
        if (!amtList.isEmpty()) {
            amtLast = mtList.get(amtList.size() - 1).getStartTime();
        }
        if (!bmtList.isEmpty()) {
            bmtLast = mtList.get(bmtList.size() - 1).getStartTime();
        }
        if (!cmtList.isEmpty()) {
            cmtLast = mtList.get(cmtList.size() - 1).getStartTime();
        }
        if (!dmtList.isEmpty()) {
            dmtLast = mtList.get(dmtList.size() - 1).getStartTime();
        }
        long fiMin = getFlightAccumMinute(fi.getFlightFrequency());
        long min = 0;
        long cycleCount = 0;
        if (amtList.isEmpty() || fi.getStandardDepTimeDateType().after(amtLast)) {
            min = ac.getAcycleFM();
            min -= fiMin;
            ac.setAcycleFM(min);
            cycleCount = ac.getAcycleFC();
            ac.setAcycleFC(--cycleCount);
        }
        if (bmtList.isEmpty() || fi.getStandardDepTimeDateType().after(bmtLast)) {
            min = ac.getBcycleFM();
            min -= fiMin;
            ac.setBcycleFM(min);
            cycleCount = ac.getBcycleFC();
            ac.setBcycleFC(--cycleCount);
        }
        if (cmtList.isEmpty() || fi.getStandardDepTimeDateType().after(cmtLast)) {
            min = ac.getCcycleFM();
            min -= fiMin;
            ac.setCcycleFM(min);
            cycleCount = ac.getCcycleFC();
            ac.setCcycleFC(--cycleCount);

        }
        if (dmtList.isEmpty() || fi.getStandardDepTimeDateType().after(dmtLast)) {
            min = ac.getDcycleFM();
            min -= fiMin;
            ac.setDcycleFM(min);
            cycleCount = ac.getDcycleFC();
            ac.setDcycleFC(--cycleCount);
        }

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
        fiList = sortFiList(fiList);
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

    @Override
    public List<FlightInstance> getSortedFiWithinPeriod(Date startDate, Date endDate) {
        List<FlightInstance> newFlightInstList = new ArrayList<FlightInstance>();
        for (FlightInstance temp : this.getAllFlightInstance()) {
            if (temp.getStandardDepTimeDateType().after(startDate) && temp.getStandardArrTimeDateType().before(endDate)) {
                if (!temp.getFlightStatus().equals("Cancelled")) {
                    newFlightInstList.add(temp);
                }
            }
        }
        return sortFiList(newFlightInstList);
    }

    public List<FlightInstance> getAllFiWithinPeriod(Date startDate, Date endDate) {
        List<FlightInstance> newFlightInstList = new ArrayList<FlightInstance>();
        for (FlightInstance temp : this.getAllFlightInstance()) {
            if (temp.getStandardDepTimeDateType().after(startDate) && temp.getStandardArrTimeDateType().before(endDate)) {
                if (!temp.getFlightStatus().equals("Cancelled")) {
                    newFlightInstList.add(temp);
                }
            }
        }
        return newFlightInstList;
    }

    public List<Maintenance> getAllMtWithinPeriod(Date startDate, Date endDate) {
        List<Maintenance> newMtList = new ArrayList<Maintenance>();
        Query q1 = em.createQuery("SELECT a FROM Maintenance a");
        List<Maintenance> mtList = q1.getResultList();
        for (Maintenance temp : mtList) {
            if (temp.getStartTime().after(startDate) && temp.getStartTime().before(endDate)) {
//                if (!temp.getFlightStatus().equals("Cancelled")) {
                newMtList.add(temp);
//                }
            }
        }
        return newMtList;
    }

    @Override
    public long calPeriodTotalFlightHour(Date startDate, Date endDate) {
        long totalMin = 0;
        for (FlightInstance f1 : getAllFiWithinPeriod(startDate, endDate)) {
            totalMin += getFlightAccumMinute(f1.getFlightFrequency());
        }
        System.out.println("FSB.calPeriodTotalFlightHour() : total flight hour from " + startDate + " to " + endDate + " is " + totalMin / 60);
        return totalMin / 60;
    }

    @Override
    public long calPeriodTotalMtManHour(Date startDate, Date endDate) {
        long totalHr = 0;
        for (Maintenance m1 : getAllMtWithinPeriod(startDate, endDate)) {
            totalHr += m1.getManhour();
        }
        System.out.println("FSB.calPeriodTotalMtHour() : total maintenance hour from " + startDate + " to " + endDate + " is " + totalHr);
        return totalHr;
    }

}
