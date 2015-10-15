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
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//        LocalTime depTime = LocalTime.parse(depTimeString, DateTimeFormatter.ofPattern("HH:mm"));
//        LocalTime arrTime = LocalTime.parse(arrTimeString, DateTimeFormatter.ofPattern("HH:mm"));
//        LocalDate depDate = LocalDate.of(2000, 1, 10);
//        LocalDate arrDate = LocalDate.of(2000, 1, 10).plusDays(dateAdjust);
//        LocalDateTime depDateTime = LocalDateTime.of(depDate, depTime);
//        LocalDateTime arrDateTime = LocalDateTime.of(arrDate, arrTime);
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

//        if (depDateTime.isAfter(arrDateTime)) {
//            System.out.println("Departure time should before arrival time.");
//            throw new Exception("Departure time should before arrival time.");
//        }
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
        Long estimatedDiff = (ea.getTime() +(60 * 60 * 1000*24*estimatedDateAdjust)- ed.getTime()) / (60 * 60 * 1000) % 24; //hours differrence
        Long actualDiff = (aa.getTime() +(60 * 60 * 1000*24*actualDateAdjust)- ad.getTime()) / (60 * 60 * 1000) % 24;
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
            System.out.println("FSB: getUnplannedFlightInstance(): tempInfo: " + temp.getFlightFrequency().getFlightNo() + " " + temp.getDate());
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 1 :" + temp.getAircraft().getRegistrationNo().equals("9V-000"));
//            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :"+(temp.getAircraft() != null));
            System.out.println("FSB: getUnplannedFlightInstance(): Check boolean 2 :" + (temp.getFlightFrequency().getRoute().getAcType().equals(ac.getAircraftType())));
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
            c.add(Calendar.DATE, 1);  // number of days to add
            firstInstDate = df1.format(c.getTime());
            System.out.println("EDIT firstInstDate to " + firstInstDate);
        }

        for (Aircraft acTemp : getAllAircraft()) {
            System.out.println("FSB: acTemp is " + acTemp.getRegistrationNo() + " " + acTemp.getAircraftType().getType());
            Date currentTime = startDate;    //the current available time of the aircraft
            Airport currentAirport = em.find(Airport.class, acTemp.getCurrentAirport());//need to add the new attribute:  currentAirport
            System.out.println("FSB: currentAirport: " + currentAirport.getIATA());
            List<FlightInstance> unplannedFi = getUnplannedFlightInstance(acTemp);
            
            Collections.sort(unplannedFi);

            System.out.println(
                    "FSB:Sorted unplannedFi : " + unplannedFi.toString());
            for (FlightInstance fiTemp : unplannedFi) {
                System.out.println("FSB: scheduleAcToFi(): currentTime is " + currentTime.toString());
                System.out.println("FSB: scheduleAcToFi(): endTime is " + endDate.toString());
                System.out.println("FSB: scheduleAcToFi(): flightInstance scheduled time is " + df1.parse(fiTemp.getStandardDepTime()).toString());
                System.out.println("FSB: scheduleAcToFi(): Check Boolean1 " + currentTime.after(endDate));
                System.out.println("FSB: scheduleAcToFi(): Check Boolean2 " + df1.parse(fiTemp.getStandardDepTime()).after(endDate));
                if (currentTime.after(endDate) || df1.parse(fiTemp.getStandardDepTime()).after(endDate)) {
                    System.out.println("FSB: scheduleAcToFi(): Break! ");
                    break;
                } else {
                    System.out.println("FSB: scheduleAcToFi(): Check Boolean3 " + currentTime.before(df1.parse(fiTemp.getStandardDepTime())));
                    System.out.println("FSB: scheduleAcToFi(): Check Boolean4 " + currentAirport.equals(fiTemp.getFlightFrequency().getRoute().getOrigin()));
                    //check whether currenTime is at least two hours earlier that the next departure
                    Date temp = currentTime;
                    Calendar c = Calendar.getInstance();
                    c.setTime(temp);
                    c.add(Calendar.HOUR, 2);  // number of days to add
                    temp = c.getTime();
                    System.out.println("FSB: scheduleAcToFi(): 2 hours later? " + temp.toString());
                    if (temp.before(df1.parse(fiTemp.getStandardDepTime())) && currentAirport.equals(fiTemp.getFlightFrequency().getRoute().getOrigin())) {
                        System.out.println("FSB: Enter assignment process " + fiTemp.getFlightFrequency().getFlightNo() + " " + fiTemp.getDate());
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

    @Override
    public boolean addAcToFi(Aircraft ac, FlightInstance fi) {
        //------------------need check time conflict------------------
        boolean flag = canAssign(ac, fi);
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("FSB: addAcToFi ");
        System.out.println("FSB: addAcToFi " + flag);
        if (flag) {
            flightTemp.add(fi);
            ac.setFlightInstance(flightTemp);
            fi.setAircraft(ac);
            em.merge(fi);
            em.merge(ac);
            System.out.print("FSB: addToFi finished!");
            em.flush();
        }
        return flag;
    }

    public boolean canAssign(Aircraft ac, FlightInstance fi) {
        boolean canAssign = false;
        System.out.println("canAssign: fi.dep " + fi.getStandardDepTime() + " fi.arrial " + fi.getStandardArrTime());
        System.out.println("canAssign: fi.origin " + fi.getFlightFrequency().getRoute().getOrigin().getIATA() + " fi.arrial " + fi.getFlightFrequency().getRoute().getDest().getIATA());
        System.out.println("canAssign: CHECK 1");
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        System.out.println("canAssign: CHECK 2 " + flightTemp.size());

        if (flightTemp.size() == 0) {
            if (ac.getAircraftType().equals(fi.getFlightFrequency().getRoute().getAcType()) && ac.getCurrentAirport().equals(fi.getFlightFrequency().getRoute().getOrigin().getIATA())) {
                canAssign = true;
            }
        } else {
            System.out.println("canAssign: CHECK 3");
        
            List<Date> listDates = new ArrayList<Date>();
            for(FlightInstance fitest: flightTemp){
               listDates.add(fitest.getStandardDepTimeDateType());
                
            }
            System.out.println("Before sorting: " + listDates);
            Collections.sort(listDates);
            System.out.println("After sorting: " + listDates);
            
            
            System.out.println("flightTemp before Sort:" + flightTemp.toString());
            List<FlightInstance> newList=new ArrayList();
            for(int k=0;k<listDates.size();k++){
                for(int j=0;j<flightTemp.size();j++){
                    if (flightTemp.get(j).getStandardDepTimeDateType().equals(listDates.get(k)))
                        newList.add(flightTemp.get(j));
                } 
            }
            System.out.println("flightTemp after Sort:" + newList.toString());
            System.out.println("canAssign: CHECK 4");
            Date depCheck = new Date();
            Date arrCheck = new Date();
            System.out.println("canAssign: CHECK 5");
            for (int i = 0; i < newList.size() - 1; i++) {
                depCheck = newList.get(i).getStandardArrTimeDateType();
                arrCheck = newList.get(i + 1).getStandardDepTimeDateType();
                System.out.println("canAssign: depDate" + depCheck);
                System.out.println("canAssign: arrCheck" + arrCheck);
                cal = Calendar.getInstance();
                // last arrival shoulbe be ahead of the fi's departure at least 2 hours
                cal.setTime(depCheck);
                cal.add(Calendar.HOUR, 2);
                depCheck = cal.getTime();
                System.out.println("Literal 1 check: " + (depCheck.before(fi.getStandardArrTimeDateType())));
                System.out.println("Literal 2 check: " + newList.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin()));
                System.out.println("Literal 2 check flightTemp : " + newList.get(i).getFlightFrequency().getRoute().getDest().getIATA());
                System.out.println("Literal 2 check fi: " + fi.getFlightFrequency().getRoute().getOrigin().getIATA());
                //if (fi.getStandardArrTimeDateType().before(flightTemp.get(0).))

                if ((depCheck.before(fi.getStandardDepTimeDateType())) && newList.get(i).getFlightFrequency().getRoute().getDest().equals(fi.getFlightFrequency().getRoute().getOrigin())) {
                    System.out.println("canAssign: CHECK 6");
                    // if it is not the last in flighttemp, next departure shoulbe at least 2 hours later than the fi's arrival
                    if (i + 1 < newList.size()) {
                        cal.setTime(arrCheck);
                        cal.add(Calendar.HOUR, -2);
                        arrCheck = cal.getTime();
                        if ((fi.getStandardArrTimeDateType().before(arrCheck)) && newList.get(i + 1).getFlightFrequency().getRoute().getOrigin().equals(fi.getFlightFrequency().getRoute().getDest())) {
                            canAssign = true;
                        }
                    } else {  //it is the last in flighttemp, can anyway assign
                        System.out.println("canAssign: CHECK 7");
                        canAssign = true;
                    }
                }
            }
        }
        return canAssign;
    }
    
    
    
    
    
    
    
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

    @Override
    public void deleteAcFromFi(Aircraft ac, FlightInstance fi) {
        List<FlightInstance> flightTemp = ac.getFlightInstance();
        flightTemp.remove(fi);
        ac.setFlightInstance(flightTemp);
        Aircraft acTemp = em.find(Aircraft.class, "9V-000");
        fi.setAircraft(acTemp);
        em.merge(fi);
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
    public FlightInstance getDummyFi() {
        Query q1 = em.createQuery("SELECT f FROM FlightInstance f where f.id=:id").setParameter("id", 1000000);
//        FlightInstance fi = em.find(FlightInstance.class, 1000000);
        return (FlightInstance) q1.getSingleResult();
    }

}
