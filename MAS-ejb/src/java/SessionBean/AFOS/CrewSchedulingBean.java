/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.FlightCrewTeam;
import Entity.APS.AircraftType;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateless
public class CrewSchedulingBean implements CrewSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    @EJB
    FleetPlanningBeanLocal fpb;
    @EJB
    FlightSchedulingBeanLocal fsb;

    public void groupFlightCrew() throws Exception {
        List<AircraftType> acTypes = fpb.getAllAircraftType();
        HashMap<String, Integer> groupNoMap = new HashMap<String, Integer>();

        List<CabinCrew> cabin = getAllCabin();
        List<CabinCrew> cabinLeader = getAllCabinLeader();
        List<CockpitCrew> captain = getAllCaptain();
        List<CockpitCrew> pilot = getAllPilot();

        List<CabinCrew> cabinLeft = getAllCabin();
        List<CabinCrew> cabinLeaderLeft = getAllCabinLeader();
        List<CockpitCrew> captainLeft = getAllCaptain();
        List<CockpitCrew> pilotLeft = getAllPilot();

        for (AircraftType act : acTypes) {
            groupNoMap.put(act.getType(), fpb.getThisTypeAircraft(act.getType()).size() * 3);
        }

        List<FlightCrewTeam> flightTeams = new ArrayList<>();
        for (AircraftType act : acTypes) {

            Integer captainNo = act.getCaptain();
            Integer pilotNo = act.getPilot();
            Double temp1 = act.getCabinLeader() * act.getTotalSeatNum();
            Integer cabinLeaderNo = temp1.intValue();
            temp1 = act.getCabinCrew() * act.getTotalSeatNum();
            Integer cabinCrewNo = temp1.intValue();

            for (int i = 0; i < groupNoMap.get(act.getType()); i++) {

                System.out.println("CSB.groupFlightCrew(): Assign flight crew members to " + act.getType() + " === Group " + i);
                System.out.println("Require captain --- " + captainNo + " pilot --- " + pilotNo + " cabinLeader --- " + cabinLeaderNo + " --- cabinCrew " + cabinCrewNo);

                Integer cpNo = captainNo;
                Integer plNo = pilotNo;
                Integer clNo = cabinLeaderNo;
                Integer ccNo = cabinCrewNo;

                if (cabinLeft.isEmpty() || cabinLeaderLeft.isEmpty() || captainLeft.isEmpty() || pilotLeft.isEmpty()) {
                    if (cabinLeft.isEmpty()) {
                        System.out.println("CSB.groupFlightCrew(): not enough " + " cabin crew for grouping for " + act.getType() + " group number " + i);
                    }
                    if (cabinLeaderLeft.isEmpty()) {
                        System.out.println("CSB.groupFlightCrew(): not enough " + " cabin leader for grouping for " + act.getType() + " group number " + i);
                    }
                    if (captainLeft.isEmpty()) {
                        System.out.println("CSB.groupFlightCrew(): not enough " + " captain for grouping for " + act.getType() + " group number " + i);
                    }
                    if (pilotLeft.isEmpty()) {
                        System.out.println("CSB.groupFlightCrew(): not enough " + " pilot for grouping for " + act.getType() + " group number " + i);
                    }
                    // throw new Exception("CSB.groupFlightCrew(): not enough " + " ? " + " crew for grouping.");
                } else {
                    List<CabinCrew> ccList = new ArrayList<>();
                    List<CockpitCrew> cpList = new ArrayList<>();

                    for (CabinCrew cc : cabin) {
                        if (ccNo > 0) {
                            ccList.add(cc);
                            cabinLeft.remove(cc);
                            ccNo--;
                        } else {
                            break;
                        }
                    }

                    for (CabinCrew cc : cabinLeader) {
                        if (clNo > 0) {
                            ccList.add(cc);
                            cabinLeaderLeft.remove(cc);
                            clNo--;
                        } else {
                            break;
                        }
                    }

                    for (CockpitCrew cp : captain) {
                        if (cpNo > 0) {
                            cpList.add(cp);
                            captainLeft.remove(cp);
                            cpNo--;
                        } else {
                            break;
                        }
                    }

                    for (CockpitCrew cp : pilot) {
                        if (plNo > 0) {
                            cpList.add(cp);
                            captainLeft.remove(cp);
                            plNo--;
                        } else {
                            break;
                        }
                    }

                    cabin = cabinLeft;
                    cabinLeader = cabinLeaderLeft;
                    captain = captainLeft;
                    pilot = pilotLeft;

                    FlightCrewTeam oneTeam = new FlightCrewTeam();
                    String teamId = act.getType() + "_" + i;
                    oneTeam.create(teamId);
                    oneTeam.setAct(act.getType());
                    oneTeam.setCabinCrew(ccList);
                    oneTeam.setCockpitCrew(cpList);
                    em.persist(oneTeam);
                    em.flush();

                    for (CabinCrew cc : ccList) {
                        CabinCrew temp = em.find(CabinCrew.class, cc.getCbName());
                        temp.setFlightTeam(oneTeam);
                        em.merge(temp);
                    }
                    for (CockpitCrew cp : cpList) {
                        CockpitCrew temp = em.find(CockpitCrew.class, cp.getCpName());
                        temp.setFlightTeam(oneTeam);
                        em.merge(temp);
                    }
                    em.flush();
                }
            }
        }
    }

//    public void scheduleFlightCrewGroup(Date startDate, Date endDate) {
//        List<FlightInstance> fiList = fsb.getSortedFiWithinPeriod(startDate, endDate);
//    }
// pre-condition: startDate should be Monday of the first week, endDate should be Sunday of the fourth week
    public void scheduleFlightCrew(Date startDate, Date endDate) throws ParseException {
        List<FlightInstance> fiList = fsb.getSortedFiWithinPeriod(startDate, endDate);

        List<CabinCrew> cabin = getAllCabin();
        List<CabinCrew> cabinLeader = getAllCabinLeader();
        List<CockpitCrew> captain = getAllCaptain();
        List<CockpitCrew> pilot = getAllPilot();

        // arrange normal flight crew
        for (FlightInstance fi : fiList) {
            AircraftType act = fi.getFlightFrequency().getRoute().getAcType();
            Integer captainNo = act.getCaptain();
            Integer pilotNo = act.getPilot();
            Double temp1 = act.getCabinLeader() * act.getTotalSeatNum();
            Integer cabinLeaderNo = temp1.intValue();
            temp1 = act.getCabinCrew() * act.getTotalSeatNum();
            Integer cabinCrewNo = temp1.intValue();

            long fiMin = fsb.getFlightAccumMinute(fi.getFlightFrequency());
            String lang1 = fi.getFlightFrequency().getRoute().getOrigin().getLang();
            String lang2 = fi.getFlightFrequency().getRoute().getDest().getLang();

            Integer fiCaptain = 0;
            Integer fiPilot = 0;
            Integer fiCabin = 0;
            Integer fiCabinLeader = 0;

            // Reset weekly accum date count
            SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = myFormat.parse(fi.getDate());
            long dayDiff = Math.abs(date1.getTime() - startDate.getTime());
            if (dayDiff % 7 == 0) {
                for (CabinCrew cc : getAllCabinCrew()) {
                    cc.setWeekAccumMin(0);
                }
                for (CockpitCrew cc : getAllCockpitCrew()) {
                    cc.setWeekAccumMin(0);
                }
            }

            for (CockpitCrew cp : captain) {
                if (cp.getLicence().equals(act.getType())) {
                    if (fiCaptain < captainNo) {
                        if ((cp.getYearAccumMin() + fiMin) / 60 < 1000) {
                            if ((cp.getMonthAccumMin() + fiMin) / 60 < 100) {
                                if ((cp.getWeekAccumMin() + fiMin) / 60 < 36) {
                                    if (checkAvailable(cp, fi)) {
                                        FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                        CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                        List<CockpitCrew> cps = fiInDB.getCockpitList();
                                        List<FlightInstance> fiTasks = cpInDB.getFiList();
                                        cps.add(cp);
                                        fiTasks.add(fi);
                                        fiInDB.setCockpitList(cps);
                                        cpInDB.setFiList(fiTasks);

                                        long yr = cpInDB.getYearAccumMin() + fiMin;
                                        long mon = cpInDB.getMonthAccumMin() + fiMin;
                                        long week = cpInDB.getWeekAccumMin() + fiMin;
                                        cpInDB.setYearAccumMin(yr);
                                        cpInDB.setMonthAccumMin(mon);
                                        cpInDB.setWeekAccumMin(week);

                                        em.merge(fiInDB);
                                        em.merge(cpInDB);
                                        em.flush();
                                        fiCaptain++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            for (CockpitCrew cp : pilot) {
                if (cp.getLicence().equals(act.getType())) {
                    if (fiPilot < pilotNo) {
                        if ((cp.getYearAccumMin() + fiMin) / 60 < 1000) {
                            if ((cp.getMonthAccumMin() + fiMin) / 60 < 100) {
                                if ((cp.getWeekAccumMin() + fiMin) / 60 < 36) {
                                    if (checkAvailable(cp, fi)) {
                                        FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                        CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                        List<CockpitCrew> cps = fiInDB.getCockpitList();
                                        List<FlightInstance> fiTasks = cpInDB.getFiList();
                                        cps.add(cp);
                                        fiTasks.add(fi);
                                        fiInDB.setCockpitList(cps);
                                        cpInDB.setFiList(fiTasks);

                                        long yr = cpInDB.getYearAccumMin() + fiMin;
                                        long mon = cpInDB.getMonthAccumMin() + fiMin;
                                        long week = cpInDB.getWeekAccumMin() + fiMin;
                                        cpInDB.setYearAccumMin(yr);
                                        cpInDB.setMonthAccumMin(mon);
                                        cpInDB.setWeekAccumMin(week);

                                        em.merge(fiInDB);
                                        em.merge(cpInDB);
                                        em.flush();
                                        fiPilot++;
                                    }
                                }
                            }
                        }
                    }
                }
            }

//            boolean hasLang1 = false;
//            boolean hasLang2 = false;
            if (!lang1.equals("English")) {
                sortCabinByLang(cabinLeader, lang1);
            }
            if (!lang2.equals("English")) {
                sortCabinByLang(cabinLeader, lang2);
            }

            for (CabinCrew cc : cabinLeader) {
                if (fiCabinLeader < cabinLeaderNo) {
                    if ((cc.getYearAccumMin() + fiMin) / 60 < 1000) {
                        if ((cc.getMonthAccumMin() + fiMin) / 60 < 100) {
                            if ((cc.getWeekAccumMin() + fiMin) / 60 < 36) {
                                if (checkAvailable(cc, fi)) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                    List<CabinCrew> ccs = fiInDB.getCabinList();
                                    List<FlightInstance> fiTasks = ccInDB.getFiList();
                                    ccs.add(cc);
                                    fiTasks.add(fi);
                                    fiInDB.setCabinList(ccs);
                                    ccInDB.setFiList(fiTasks);

                                    long yr = ccInDB.getYearAccumMin() + fiMin;
                                    long mon = ccInDB.getMonthAccumMin() + fiMin;
                                    long week = ccInDB.getWeekAccumMin() + fiMin;
                                    ccInDB.setYearAccumMin(yr);
                                    ccInDB.setMonthAccumMin(mon);
                                    ccInDB.setWeekAccumMin(week);

                                    em.merge(fiInDB);
                                    em.merge(ccInDB);
                                    em.flush();
                                    fiCabinLeader++;
                                }
                            }
                        }
                    }
                }
            }

            if (!lang1.equals("English")) {
                sortCabinByLang(cabin, lang1);
            }
            if (!lang2.equals("English")) {
                sortCabinByLang(cabin, lang2);
            }

            for (CabinCrew cc : cabin) {
                if (fiCabin < cabinCrewNo) {
                    if ((cc.getYearAccumMin() + fiMin) / 60 < 1000) {
                        if ((cc.getMonthAccumMin() + fiMin) / 60 < 100) {
                            if ((cc.getWeekAccumMin() + fiMin) / 60 < 36) {
                                if (checkAvailable(cc, fi)) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                    List<CabinCrew> ccs = fiInDB.getCabinList();
                                    List<FlightInstance> fiTasks = ccInDB.getFiList();
                                    ccs.add(cc);
                                    fiTasks.add(fi);
                                    fiInDB.setCabinList(ccs);
                                    ccInDB.setFiList(fiTasks);

                                    long yr = ccInDB.getYearAccumMin() + fiMin;
                                    long mon = ccInDB.getMonthAccumMin() + fiMin;
                                    long week = ccInDB.getWeekAccumMin() + fiMin;
                                    ccInDB.setYearAccumMin(yr);
                                    ccInDB.setMonthAccumMin(mon);
                                    ccInDB.setWeekAccumMin(week);

                                    em.merge(fiInDB);
                                    em.merge(ccInDB);
                                    em.flush();
                                    fiCabin++;
                                }
                            }
                        }
                    }
                }
            }
        }

        // arrange stand-by crew
        for (FlightInstance fi : fiList) {
            AircraftType act = fi.getFlightFrequency().getRoute().getAcType();
            long fiMin = fsb.getFlightAccumMinute(fi.getFlightFrequency());

            List<CockpitCrew> idleCaptain = getIdleCockpit("captain", fi);
            List<CockpitCrew> idlePilot = getIdleCockpit("pilot", fi);
            List<CabinCrew> idleCabin = getIdleCabin("cabin", fi);
            List<CabinCrew> idleCabinLeader = getIdleCabin("cabinLeader", fi);

            String lang1 = fi.getFlightFrequency().getRoute().getOrigin().getLang();
            String lang2 = fi.getFlightFrequency().getRoute().getDest().getLang();
            if (!lang1.equals("English")) {
                sortCabinByLang(idleCabin, lang1);
                sortCabinByLang(idleCabinLeader, lang1);
            }
            if (!lang2.equals("English")) {
                sortCabinByLang(idleCabin, lang2);
                sortCabinByLang(idleCabinLeader, lang1);
            }

            Integer fiCaptain = 0;
            Integer fiPilot = 0;
            Integer fiCabin = 0;
            Integer fiCabinLeader = 0;

            for (CockpitCrew cp : idleCaptain) {
                if (fiCaptain == 2) {
                    break;
                } else {
                    if (cp.getLicence().equals(act.getType())) {
                        if (((cp.getFirstSB() < 2) && checkCockpitSBEmpty(fi, "captain")) || ((cp.getSecondSB() < 3) && !checkCockpitSBEmpty(fi, "captain"))) {
                            if ((cp.getYearAccumMin() + fiMin) / 60 < 1000) {
                                if ((cp.getMonthAccumMin() + fiMin) / 60 < 100) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                    List<CockpitCrew> cps = fiInDB.getCockpitStandByList();
                                    List<FlightInstance> fiStandBy = cpInDB.getFiStandByList();
                                    cps.add(cp);
                                    fiStandBy.add(fi);
                                    fiInDB.setCockpitStandByList(cps);
                                    cpInDB.setFiStandByList(fiStandBy);

                                    // ***************************** ?? *****************************
                                    long yr = cpInDB.getYearAccumMin() + fiMin;
                                    long mon = cpInDB.getMonthAccumMin() + fiMin;
                                    cpInDB.setYearAccumMin(yr);
                                    cpInDB.setMonthAccumMin(mon);

                                    // set stand by times of the crew
                                    if (fiStandBy.isEmpty()) {
                                        cpInDB.setFirstSB((cpInDB.getFirstSB() + 1));
                                    } else {
                                        cpInDB.setSecondSB((cpInDB.getSecondSB() + 1));
                                    }

                                    em.merge(fiInDB);
                                    em.merge(cpInDB);
                                    em.flush();
                                    fiCaptain++;
                                }
                            }
                        }
                    }
                }
            }

            for (CockpitCrew cp : idlePilot) {
                if (fiPilot == 2) {
                    break;
                } else {
                    if (cp.getLicence().equals(act.getType())) {
                        if (((cp.getFirstSB() < 2) && checkCockpitSBEmpty(fi, "pilot")) || ((cp.getSecondSB() < 3) && !checkCockpitSBEmpty(fi, "pilot"))) {
                            if ((cp.getYearAccumMin() + fiMin) / 60 < 1000) {
                                if ((cp.getMonthAccumMin() + fiMin) / 60 < 100) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                    List<CockpitCrew> cps = fiInDB.getCockpitStandByList();
                                    List<FlightInstance> fiStandBy = cpInDB.getFiStandByList();
                                    cps.add(cp);
                                    fiStandBy.add(fi);
                                    fiInDB.setCockpitStandByList(cps);
                                    cpInDB.setFiStandByList(fiStandBy);

                                    long yr = cpInDB.getYearAccumMin() + fiMin;
                                    long mon = cpInDB.getMonthAccumMin() + fiMin;
                                    cpInDB.setYearAccumMin(yr);
                                    cpInDB.setMonthAccumMin(mon);

                                    // set stand by times of the crew
                                    if (fiStandBy.isEmpty()) {
                                        cpInDB.setFirstSB((cpInDB.getFirstSB() + 1));
                                    } else {
                                        cpInDB.setSecondSB((cpInDB.getSecondSB() + 1));
                                    }

                                    em.merge(fiInDB);
                                    em.merge(cpInDB);
                                    em.flush();
                                    fiPilot++;
                                }
                            }
                        }
                    }
                }
            }

            for (CabinCrew cc : idleCabinLeader) {
                if (fiCabinLeader == 2) {
                    break;
                } else {
                    if (((cc.getFirstSB() < 2) && checkCabinSBEmpty(fi, "cabinLeader")) || ((cc.getSecondSB() < 3) && !checkCabinSBEmpty(fi, "cabinLeader"))) {
                        if ((cc.getYearAccumMin() + fiMin) / 60 < 1000) {
                            if ((cc.getMonthAccumMin() + fiMin) / 60 < 100) {
                                FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                List<CabinCrew> ccs = fiInDB.getCabinStandByList();
                                List<FlightInstance> fiStandBy = ccInDB.getFiStandByList();
                                ccs.add(cc);
                                fiStandBy.add(fi);
                                fiInDB.setCabinStandByList(ccs);
                                ccInDB.setFiStandByList(fiStandBy);

                                long yr = ccInDB.getYearAccumMin() + fiMin;
                                long mon = ccInDB.getMonthAccumMin() + fiMin;
                                ccInDB.setYearAccumMin(yr);
                                ccInDB.setMonthAccumMin(mon);

                                // set stand by times of the crew
                                if (fiStandBy.isEmpty()) {
                                    ccInDB.setFirstSB((ccInDB.getFirstSB() + 1));
                                } else {
                                    ccInDB.setSecondSB((ccInDB.getSecondSB() + 1));
                                }

                                em.merge(fiInDB);
                                em.merge(ccInDB);
                                em.flush();
                                fiCabinLeader++;
                            }
                        }
                    }
                }
            }

            for (CabinCrew cc : idleCabin) {
                if (fiCabin == 2) {
                    break;
                } else {
                    if (((cc.getFirstSB() < 2) && checkCabinSBEmpty(fi, "cabinCrew")) || ((cc.getSecondSB() < 3) && !checkCabinSBEmpty(fi, "cabinCrew"))) {
                        if ((cc.getYearAccumMin() + fiMin) / 60 < 1000) {
                            if ((cc.getMonthAccumMin() + fiMin) / 60 < 100) {
                                FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                List<CabinCrew> ccs = fiInDB.getCabinStandByList();
                                List<FlightInstance> fiStandBy = ccInDB.getFiStandByList();
                                ccs.add(cc);
                                fiStandBy.add(fi);
                                fiInDB.setCabinStandByList(ccs);
                                ccInDB.setFiStandByList(fiStandBy);

                                long yr = ccInDB.getYearAccumMin() + fiMin;
                                long mon = ccInDB.getMonthAccumMin() + fiMin;
                                ccInDB.setYearAccumMin(yr);
                                ccInDB.setMonthAccumMin(mon);

                                // set stand by times of the crew
                                if (fiStandBy.isEmpty()) {
                                    ccInDB.setFirstSB((ccInDB.getFirstSB() + 1));
                                } else {
                                    ccInDB.setSecondSB((ccInDB.getSecondSB() + 1));
                                }

                                em.merge(fiInDB);
                                em.merge(ccInDB);
                                em.flush();
                                fiCabin++;
                            }
                        }
                    }
                }
            }
        }

        // reset monthly counter
        for (CabinCrew cc : getAllCabinCrew()) {
            cc.setFirstSB(0);
            cc.setSecondSB(0);
            cc.setWeekAccumMin(0);
            cc.setMonthAccumMin(0);
        }
        for (CockpitCrew cc : getAllCockpitCrew()) {
            cc.setFirstSB(0);
            cc.setSecondSB(0);
            cc.setWeekAccumMin(0);
            cc.setMonthAccumMin(0);
        }
        
    }

    public boolean checkCabinSBEmpty(FlightInstance fi, String level) {
        List<CabinCrew> cbSB = fi.getCabinStandByList();
        for (CabinCrew cc : cbSB) {
            if (cc.getStfLevel().equals(level)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkCockpitSBEmpty(FlightInstance fi, String level) {
        List<CockpitCrew> cpSB = fi.getCockpitStandByList();
        for (CockpitCrew cp : cpSB) {
            if (cp.getStfLevel().equals(level)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkAvailable(CockpitCrew cp, FlightInstance fi) {
        boolean canFly = false;
        List<FlightInstance> fiTasks = sortFiList(cp.getFiList());
        Date fiDep = fi.getStandardDepTimeDateType();
        Date fiArr = fi.getStandardArrTimeDateType();
        Calendar cal = new GregorianCalendar();
        cal.setTime(fiDep);
        cal.add(Calendar.HOUR, -1);
        fiDep = cal.getTime();
        cal.setTime(fiArr);
        cal.add(Calendar.HOUR, 1);
        fiArr = cal.getTime();

        if (fiTasks.isEmpty()) {    // fiTasks is empty
            canFly = true;
        } else if (fiArr.before(fiTasks.get(0).getStandardDepTimeDateType())
                && fi.getFlightFrequency().getRoute().getDest().equals(fiTasks.get(0).getFlightFrequency().getRoute().getOrigin())) {   // fi is before the first of fiTasks
            canFly = true;
        } else if (fiDep.after(fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType())
                && fi.getFlightFrequency().getRoute().getOrigin().equals(fiTasks.get(0).getFlightFrequency().getRoute().getDest())) {   // fi is after the last of fiTasks
            canFly = true;
        } else {
            for (int i = 0; i < fiTasks.size() - 1; i++) {
                FlightInstance f1 = fiTasks.get(i);
                FlightInstance f2 = fiTasks.get(i + 1);
                if (fiDep.after(f1.getStandardArrTimeDateType()) && fiArr.before(f2.getStandardDepTimeDateType())
                        && fi.getFlightFrequency().getRoute().getOrigin().equals(f1.getFlightFrequency().getRoute().getDest())
                        && fi.getFlightFrequency().getRoute().getDest().equals(f2.getFlightFrequency().getRoute().getOrigin())) {
                    canFly = true;
                }
            }
        }
        return canFly;
    }

    public boolean checkAvailable(CabinCrew cc, FlightInstance fi) {
        boolean canFly = false;
        List<FlightInstance> fiTasks = sortFiList(cc.getFiList());
        Date fiDep = fi.getStandardDepTimeDateType();
        Date fiArr = fi.getStandardArrTimeDateType();
        Calendar cal = new GregorianCalendar();
        cal.setTime(fiDep);
        cal.add(Calendar.HOUR, -1);
        fiDep = cal.getTime();
        cal.setTime(fiArr);
        cal.add(Calendar.HOUR, 1);
        fiArr = cal.getTime();

        if (fiTasks.isEmpty()) {    // fiTasks is empty
            canFly = true;
        } else if (fiArr.before(fiTasks.get(0).getStandardDepTimeDateType())
                && fi.getFlightFrequency().getRoute().getDest().equals(fiTasks.get(0).getFlightFrequency().getRoute().getOrigin())) {   // fi is before the first of fiTasks
            canFly = true;
        } else if (fiDep.after(fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType())
                && fi.getFlightFrequency().getRoute().getOrigin().equals(fiTasks.get(0).getFlightFrequency().getRoute().getDest())) {   // fi is after the last of fiTasks
            canFly = true;
        } else {
            for (int i = 0; i < fiTasks.size() - 1; i++) {
                FlightInstance f1 = fiTasks.get(i);
                FlightInstance f2 = fiTasks.get(i + 1);
                if (fiDep.after(f1.getStandardArrTimeDateType()) && fiArr.before(f2.getStandardDepTimeDateType())
                        && fi.getFlightFrequency().getRoute().getOrigin().equals(f1.getFlightFrequency().getRoute().getDest())
                        && fi.getFlightFrequency().getRoute().getDest().equals(f2.getFlightFrequency().getRoute().getOrigin())) {
                    canFly = true;
                }
            }
        }
        return canFly;
    }

    public List<CabinCrew> getIdleCabin(String cabinType, FlightInstance fi) {
        List<CabinCrew> cbList = new ArrayList<>();
        List<CabinCrew> idleCb = new ArrayList<>();
        if (cabinType.equals("cabinCrew")) {
            cbList = getAllCabin();
        } else {
            cbList = getAllCabinLeader();
        }
        for (CabinCrew cc : cbList) {
            if (checkAvailable(cc, fi)) {
                idleCb.add(cc);
            }
        }
        return idleCb;
    }

    public List<CockpitCrew> getIdleCockpit(String cabinType, FlightInstance fi) {
        List<CockpitCrew> cpList = new ArrayList<>();
        List<CockpitCrew> idleCp = new ArrayList<>();
        if (cabinType.equals("captain")) {
            cpList = getAllCaptain();
        } else {
            cpList = getAllPilot();
        }
        for (CockpitCrew cc : cpList) {
            if (checkAvailable(cc, fi)) {
                idleCp.add(cc);
            }
        }
        return idleCp;
    }

    public List<CabinCrew> sortCabinByLang(List<CabinCrew> ccList, String lang) {
        List<CabinCrew> ccNew = ccList;
        for (CabinCrew cc : ccList) {
            if (cc.getSecondLang().equals(lang)) {
                ccNew.remove(cc);
                ccNew.add(0, cc);
            }
        }
        return ccNew;
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

    public List<CabinCrew> getAllCabinCrew() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a");
        List<CabinCrew> allCabin = (ArrayList<CabinCrew>) q1.getResultList();
        return allCabin;
    }

    public List<CabinCrew> getAllCabin() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.stfLevel=:level").setParameter("level", "cabinCrew");
        List<CabinCrew> cabinList = (ArrayList<CabinCrew>) q1.getResultList();
        return cabinList;
    }

    public List<CabinCrew> getAllCabinLeader() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.stfLevel=:level").setParameter("level", "cabinLeader");
        List<CabinCrew> leaderList = (ArrayList<CabinCrew>) q1.getResultList();
        return leaderList;
    }

    public List<CockpitCrew> getAllCockpitCrew() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a");
        List<CockpitCrew> allCockpit = (ArrayList<CockpitCrew>) q1.getResultList();
        return allCockpit;
    }

    public List<CockpitCrew> getAllCaptain() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.stfLevel=:level").setParameter("level", "captain");
        List<CockpitCrew> captainList = (ArrayList<CockpitCrew>) q1.getResultList();
        return captainList;
    }

    public List<CockpitCrew> getAllPilot() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.stfLevel=:level").setParameter("level", "pilot");
        List<CockpitCrew> pilotList = (ArrayList<CockpitCrew>) q1.getResultList();
        return pilotList;
    }

}
