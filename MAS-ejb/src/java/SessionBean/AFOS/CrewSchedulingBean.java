/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.FlightTask;
import Entity.AFOS.GroundStaffTeam;
import Entity.AFOS.Rotation;
import Entity.AFOS.StaffLeave;
import Entity.APS.AircraftType;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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

    private Calendar cal = new GregorianCalendar();

    @Override
    public List<GroundStaff> getUngroupedGroundStaff() {
        List<GroundStaff> gsList = getAllGroundStaff();
        List<GroundStaff> gsNew = new ArrayList<>();
        for (GroundStaff g1 : gsList) {
            if (g1.getGroundStaffTeam() == null) {
                gsNew.add(g1);
            }
            if (g1.getGroundStaffTeam() != null && g1.getGroundStaffTeam().getTeamId() == 5) {
                gsNew.add(g1);
            }
        }
        return gsNew;
    }

    @Override
    public List<GroundStaff> getGroundStaffTeam(Integer id) {
        GroundStaffTeam gst = em.find(GroundStaffTeam.class, id);
        return gst.getGroundStaff();
    }

    @Override
    public void groupGroundCrew() throws Exception {
        List<GroundStaff> gsList = getAllGroundStaff();
//        System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ getallgroundstaff number is " + gsList.size());
        GroundStaffTeam gst1 = em.find(GroundStaffTeam.class, 1);
//        System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ find gst1 " + gst1);
        GroundStaffTeam gst2 = em.find(GroundStaffTeam.class, 2);
//        System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ find gst2 " + gst2);
        GroundStaffTeam gst3 = em.find(GroundStaffTeam.class, 3);
//        System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ find gst3 " + gst3);
        GroundStaffTeam gst4 = em.find(GroundStaffTeam.class, 4);
//        System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ find gst4 " + gst4);

//        GroundStaffTeam gst2 = new GroundStaffTeam();
//        gst1.create(2);
//        GroundStaffTeam gst3 = new GroundStaffTeam();
//        gst1.create(3);
//        GroundStaffTeam gst4 = new GroundStaffTeam();
//        gst1.create(4);
//        GroundStaffTeam gst5 = new GroundStaffTeam();
//        gst1.create(5);
        List<GroundStaff> g1 = gst1.getGroundStaff();
        List<GroundStaff> g2 = gst2.getGroundStaff();
        List<GroundStaff> g3 = gst3.getGroundStaff();
        List<GroundStaff> g4 = gst4.getGroundStaff();

        for (int i = 0; i < gsList.size(); i++) {
            GroundStaff thisGS = em.find(GroundStaff.class, gsList.get(i).getGrdName());
            System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ thisGS getGroundStaffTeam " + thisGS.getGroundStaffTeam());
            if (thisGS.getGroundStaffTeam() == null) {
                switch (i % 4) {
                    case 0: {
                        thisGS.setGroundStaffTeam(gst1);
                        em.merge(thisGS);
                        g1.add(thisGS);
                        break;
                    }
                    case 1: {
                        thisGS.setGroundStaffTeam(gst2);
                        em.merge(thisGS);
                        g2.add(thisGS);
                        break;
                    }
                    case 2: {
                        thisGS.setGroundStaffTeam(gst3);
                        em.merge(thisGS);
                        g3.add(thisGS);
                        break;
                    }
                    case 3: {
                        thisGS.setGroundStaffTeam(gst4);
                        em.merge(thisGS);
                        g4.add(thisGS);
                        break;
                    }
                }
            }
//            System.err.println(" $$$$$$$$$$$$$ $$$$$$$$$$$$$ thisGS getGroundStaffTeam " + thisGS.getGroundStaffTeam());
        }
        gst1.setGroundStaff(g1);
        gst2.setGroundStaff(g2);
        gst3.setGroundStaff(g3);
        gst4.setGroundStaff(g4);
        em.merge(gst1);
        em.merge(gst2);
        em.merge(gst3);
        em.merge(gst4);
        em.flush();
    }

    @Override
    public void scheduleGS(Date startDate, Date endDate) {
        GroundStaffTeam gst1 = em.find(GroundStaffTeam.class, 1);
        GroundStaffTeam gst2 = em.find(GroundStaffTeam.class, 2);
        GroundStaffTeam gst3 = em.find(GroundStaffTeam.class, 3);
        GroundStaffTeam gst4 = em.find(GroundStaffTeam.class, 4);

        List<Rotation> roForGrp1 = gst1.getRotationList();
        List<Rotation> roForGrp2 = gst2.getRotationList();
        List<Rotation> roForGrp3 = gst3.getRotationList();
        List<Rotation> roForGrp4 = gst4.getRotationList();

        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        System.err.println("TESTING: diffDays " + diffDays);
        Date newDay = new Date();

        for (int i = 0; i < diffDays; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DATE, i);
            newDay = cal.getTime();

            // check if ground crew scheduled for this period
            Rotation morning = new Rotation();
            morning.create(newDay, "morning");
            Rotation afternoon = new Rotation();
            afternoon.create(newDay, "afternoon");
            Rotation night = new Rotation();
            night.create(newDay, "night");
            Rotation standby = new Rotation();
            standby.create(newDay, "standby");

            em.persist(morning);
            em.persist(afternoon);
            em.persist(night);
            em.persist(standby);
            em.flush();

            switch (i % 12) {
                case 0: {
                    roForGrp1.add(morning);
                    roForGrp2.add(afternoon);
                    roForGrp3.add(night);
                    roForGrp4.add(standby);
                    break;
                }
                case 1: {
                    roForGrp1.add(morning);
                    roForGrp2.add(afternoon);
                    roForGrp3.add(standby);
                    roForGrp4.add(night);
                    break;
                }
                case 2: {
                    roForGrp1.add(morning);
                    roForGrp2.add(standby);
                    roForGrp3.add(afternoon);
                    roForGrp4.add(night);
                    break;
                }
                case 3: {
                    roForGrp1.add(standby);
                    roForGrp2.add(morning);
                    roForGrp3.add(afternoon);
                    roForGrp4.add(night);
                    break;
                }
                case 4: {
                    roForGrp1.add(night);
                    roForGrp2.add(morning);
                    roForGrp3.add(afternoon);
                    roForGrp4.add(standby);
                    break;
                }
                case 5: {
                    roForGrp1.add(night);
                    roForGrp2.add(morning);
                    roForGrp3.add(standby);
                    roForGrp4.add(afternoon);
                    break;
                }
                case 6: {
                    roForGrp1.add(night);
                    roForGrp2.add(standby);
                    roForGrp3.add(morning);
                    roForGrp4.add(afternoon);
                    break;
                }
                case 7: {
                    roForGrp1.add(standby);
                    roForGrp2.add(night);
                    roForGrp3.add(morning);
                    roForGrp4.add(afternoon);
                    break;
                }
                case 8: {
                    roForGrp1.add(afternoon);
                    roForGrp2.add(night);
                    roForGrp3.add(morning);
                    roForGrp4.add(standby);
                    break;
                }
                case 9: {
                    roForGrp1.add(afternoon);
                    roForGrp2.add(night);
                    roForGrp3.add(standby);
                    roForGrp4.add(morning);
                    break;
                }
                case 10: {
                    roForGrp1.add(afternoon);
                    roForGrp2.add(standby);
                    roForGrp3.add(night);
                    roForGrp4.add(morning);
                    break;
                }
                case 11: {
                    roForGrp1.add(standby);
                    roForGrp2.add(afternoon);
                    roForGrp3.add(night);
                    roForGrp4.add(morning);
                    break;
                }
            }

            gst1.setRotationList(roForGrp1);
            gst2.setRotationList(roForGrp2);
            gst3.setRotationList(roForGrp3);
            gst4.setRotationList(roForGrp4);
            em.merge(gst1);
            em.merge(gst2);
            em.merge(gst3);
            em.merge(gst4);
            em.flush();
        }
    }

    @Override
    public void deleteGSFromGroup(GroundStaff gs1, Integer teamId) {
        GroundStaff gs = em.find(GroundStaff.class, gs1.getGrdName());
        GroundStaffTeam gst = em.find(GroundStaffTeam.class, teamId);
        List<GroundStaff> g1 = gst.getGroundStaff();
        g1.remove(gs);
        gst.setGroundStaff(g1);
        GroundStaffTeam defaultT = em.find(GroundStaffTeam.class, 5);
        gs.setGroundStaffTeam(defaultT);
        em.merge(gs);
        em.merge(gst);
        em.flush();
    }

    @Override
    public void addGSToGroup(GroundStaff gs1, Integer teamId) {
        GroundStaff gs = em.find(GroundStaff.class, gs1.getGrdName());
        GroundStaffTeam gst = em.find(GroundStaffTeam.class, teamId);
        List<GroundStaff> g1 = gst.getGroundStaff();
        g1.add(gs);
        gst.setGroundStaff(g1);
        em.merge(gst);
        gs.setGroundStaffTeam(gst);
        em.merge(gs);
        em.flush();
    }

    @Override
    public GroundStaff findGSById(String gsId) {
        GroundStaff gs = em.find(GroundStaff.class, gsId);
        return gs;
    }

    @Override
    public CockpitCrew findCPById(String cpId) {
        CockpitCrew cp = em.find(CockpitCrew.class, cpId);
        return cp;
    }

    @Override
    public CabinCrew findCCById(String ccId) {
        CabinCrew cc = em.find(CabinCrew.class, ccId);
        return cc;
    }

// pre-condition: startDate should be Monday of the first week, endDate should be Sunday of the fourth week
    @Override
    public void scheduleFlightCrew(Date startDate, Date endDate) throws ParseException {
        List<FlightInstance> fiList = fsb.getSortedFiWithinPeriod(startDate, endDate);

        List<CabinCrew> cabin = getAllCabin();
        List<CabinCrew> cabinLeader = getAllCabinLeader();
        List<CockpitCrew> captain = getAllCaptain();
        List<CockpitCrew> pilot = getAllPilot();

        // arrange normal flight crew
        for (FlightInstance fi : fiList) {

            System.out.println(" (A-A)^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*********************************** schedule crew for flight instance " + fi);

            // check if reset YEAR flying hour counter
            Date startPlanning = fi.getStandardDepTimeDateType();
            Calendar cal = Calendar.getInstance();
            cal.setTime(startPlanning);
            Integer weekNo = cal.get(Calendar.WEEK_OF_YEAR);
            if (weekNo == 1 || weekNo == 2) {
                for (CabinCrew cc : getAllCabinCrew()) {
                    CabinCrew cc1 = em.find(CabinCrew.class, cc.getCbName());
                    cc1.setYearAccumMin(0);
                }
                for (CockpitCrew cp : getAllCockpitCrew()) {
                    CockpitCrew cp1 = em.find(CockpitCrew.class, cp.getCpName());
                    cp1.setYearAccumMin(0);
                }
            }

            AircraftType act = fi.getFlightFrequency().getRoute().getAcType();
            Integer captainNo = act.getCaptain();
            Integer pilotNo = act.getPilot();
            Double ccRate = act.getCabinLeader();
            System.err.println("*******&&&&&&&&&&&%%%%%%%%%%%% " + ccRate);
            Integer seatNum = act.getTotalSeatNum();
            System.err.println("*******&&&&&&&&&&&%%%%%%%%%%%% " + seatNum);
            Double temp1 = 0.0;
            temp1 = ccRate * seatNum;
            System.err.println("*******&&&&&&&&&&&%%%%%%%%%%%% cabinLeaderNo " + temp1.intValue());
            Integer cabinLeaderNo = temp1.intValue();
            temp1 = act.getCabinCrew() * act.getTotalSeatNum();
            Integer cabinCrewNo = temp1.intValue();

            long fiMin = fsb.getFlightAccumMinute(fi.getFlightFrequency());
            String lang1 = fi.getFlightFrequency().getRoute().getOrigin().getLang();
            String lang2 = fi.getFlightFrequency().getRoute().getDest().getLang();

            Integer fiCaptain = getFiCaptain(fi).size();
            Integer fiPilot = getFiPilot(fi).size();
            Integer fiCabin = getFiCabinCrew(fi).size();
            Integer fiCabinLeader = getFiCabinLeader(fi).size();

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
                        if ((cp.getYearAccumMin() + fiMin) / 60 <= 1000) {
                            if ((cp.getMonthAccumMin() + fiMin) / 60 <= 100) {
                                if ((cp.getWeekAccumMin() + fiMin) / 60 <= 36) {
                                    if (checkAvailable(cp, fi)) {
                                        FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
//                                        System.out.println("************* CockpitCrew cp: captain: fiInDB is " + fi);
                                        CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
//                                        System.out.println("************* CockpitCrew cp: captain: cpInDB is " + cpInDB);
                                        List<CockpitCrew> cps = fiInDB.getCockpitList();
//                                        System.out.println("************* CockpitCrew cp: captain: cps is " + cps);
                                        List<FlightInstance> fiTasks = cpInDB.getFiList();
//                                        System.out.println("************* CockpitCrew cp: captain: fiTasks is " + fiTasks);

                                        List<FlightTask> cpFiTasks = cpInDB.getTaskList();
                                        FlightTask cpFiT = new FlightTask();
                                        cpFiT.create(cpInDB.getCpName(), fiInDB.getFlightFrequency().getFlightNo(), fiInDB.getStandardDepTimeDateType(), "assigned");
                                        em.persist(cpFiT);
                                        cpFiTasks.add(cpFiT);
                                        cpInDB.setTaskList(cpFiTasks);

                                        cps.add(cpInDB);
                                        fiTasks.add(fiInDB);
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

            System.out.println("\n************* CockpitCrew cp: START assign >>>> pilot for " + fi);
            for (CockpitCrew cp : pilot) {
                if (cp.getLicence().equals(act.getType())) {
                    if (fiPilot < pilotNo) {
                        if ((cp.getYearAccumMin() + fiMin) / 60 <= 1000) {
                            if ((cp.getMonthAccumMin() + fiMin) / 60 <= 100) {
                                if ((cp.getWeekAccumMin() + fiMin) / 60 <= 36) {
                                    if (checkAvailable(cp, fi)) {
                                        System.out.println("************* CockpitCrew cp: CAN assign >>>> pilot " + cp);
                                        FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                        CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                        List<CockpitCrew> cps = fiInDB.getCockpitList();
                                        List<FlightInstance> fiTasks = cpInDB.getFiList();

                                        List<FlightTask> cpFiTasks = cpInDB.getTaskList();
                                        FlightTask cpFiT = new FlightTask();
                                        cpFiT.create(cpInDB.getCpName(), fiInDB.getFlightFrequency().getFlightNo(), fiInDB.getStandardDepTimeDateType(), "assigned");
                                        em.persist(cpFiT);
                                        cpFiTasks.add(cpFiT);
                                        cpInDB.setTaskList(cpFiTasks);

                                        cps.add(cpInDB);
                                        fiTasks.add(fiInDB);
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
                cabinLeader = sortCabinByLang(cabinLeader, lang1);
            }
            if (!lang2.equals("English")) {
                cabinLeader = sortCabinByLang(cabinLeader, lang2);
            }

            System.out.println("\n************* CabinCrew cc: START assign >>>> cabinLeader for " + fi);
            for (CabinCrew cc : cabinLeader) {
                if (fiCabinLeader < cabinLeaderNo) {
                    if ((cc.getYearAccumMin() + fiMin) / 60 <= 1000) {
                        if ((cc.getMonthAccumMin() + fiMin) / 60 <= 100) {
                            if ((cc.getWeekAccumMin() + fiMin) / 60 <= 36) {
                                if (checkAvailable(cc, fi)) {
                                    System.out.println("************* CabinCrew cc: CAN assign >>>> cabinLeader " + cc);
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                    List<CabinCrew> ccs = fiInDB.getCabinList();
                                    List<FlightInstance> fiTasks = ccInDB.getFiList();

                                    List<FlightTask> cpFiTasks = ccInDB.getTaskList();
                                    FlightTask cpFiT = new FlightTask();
                                    cpFiT.create(ccInDB.getCbName(), fiInDB.getFlightFrequency().getFlightNo(), fiInDB.getStandardDepTimeDateType(), "assigned");
                                    em.persist(cpFiT);
                                    cpFiTasks.add(cpFiT);
                                    ccInDB.setTaskList(cpFiTasks);

                                    ccs.add(ccInDB);
                                    fiTasks.add(fiInDB);
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

            System.out.println("\n************* CabinCrew cc: START assign >>>> cabin for " + fi);
            for (CabinCrew cc : cabin) {
                if (fiCabin < cabinCrewNo) {
                    if ((cc.getYearAccumMin() + fiMin) / 60 <= 1000) {
                        if ((cc.getMonthAccumMin() + fiMin) / 60 <= 100) {
                            if ((cc.getWeekAccumMin() + fiMin) / 60 <= 36) {
                                if (checkAvailable(cc, fi)) {
                                    System.out.println("************* CabinCrew cc: CAN assign >>>> cabin " + cc);
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                    List<CabinCrew> ccs = fiInDB.getCabinList();
                                    List<FlightInstance> fiTasks = ccInDB.getFiList();

                                    List<FlightTask> cpFiTasks = ccInDB.getTaskList();
                                    FlightTask cpFiT = new FlightTask();
                                    cpFiT.create(ccInDB.getCbName(), fiInDB.getFlightFrequency().getFlightNo(), fiInDB.getStandardDepTimeDateType(), "assigned");
                                    em.persist(cpFiT);
                                    cpFiTasks.add(cpFiT);
                                    ccInDB.setTaskList(cpFiTasks);

                                    ccs.add(ccInDB);
                                    fiTasks.add(fiInDB);
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
            System.out.println(" *************** ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^*********************************** schedule crew for flight instance " + fi);

            AircraftType act = fi.getFlightFrequency().getRoute().getAcType();
            long fiMin = fsb.getFlightAccumMinute(fi.getFlightFrequency());

            List<CockpitCrew> idleCaptain = getIdleCockpit("Captain", fi);
            List<CockpitCrew> idlePilot = getIdleCockpit("Pilot", fi);
            List<CabinCrew> idleCabin = getIdleCabin("Cabin Crew", fi);
            List<CabinCrew> idleCabinLeader = getIdleCabin("Cabin Leader", fi);
            System.out.println("FOR FI " + fi + " IDLE CAPTAIN >>>>>>>>>>>>> >>>>>>>>>>>>> >>>>>>>>>>>>> " + idleCaptain);
            System.out.println("FOR FI " + fi + " IDLE PILOT >>>>>>>>>>>>> >>>>>>>>>>>>> >>>>>>>>>>>>> " + idlePilot);
            System.out.println("FOR FI " + fi + " IDLE CABIN CREW >>>>>>>>>>>>> >>>>>>>>>>>>> >>>>>>>>>>>>> " + idleCabin);
            System.out.println("FOR FI " + fi + " IDLE CABIN LEADER >>>>>>>>>>>>> >>>>>>>>>>>>> >>>>>>>>>>>>> " + idleCabinLeader);

            String lang1 = fi.getFlightFrequency().getRoute().getOrigin().getLang();
            String lang2 = fi.getFlightFrequency().getRoute().getDest().getLang();
            if (!lang1.equals("English")) {
                sortCabinByLang(idleCabin, lang1);
                sortCabinByLang(idleCabinLeader, lang1);
            }
            if (!lang2.equals("English")) {
                sortCabinByLang(idleCabin, lang2);
                sortCabinByLang(idleCabinLeader, lang2);
            }

            Integer fiCaptain = getFiCaptainSB(fi).size();
            Integer fiPilot = getFiPilotSB(fi).size();
            Integer fiCabin = getFiCabinCrewSB(fi).size();
            Integer fiCabinLeader = getFiCabinLeaderSB(fi).size();
            System.out.println("fiCaptain initial value " + fiCaptain);
            System.out.println("fiPilot initial value " + fiPilot);
            System.out.println("fiCabin initial value " + fiCabin);
            System.out.println("fiCabinLeader initial value " + fiCabinLeader);

            for (CockpitCrew cp : idleCaptain) {
                if (fiCaptain < 2) {
                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: check COCKPIT CAPTAIN cp " + cp.getCpName());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: checkCockpitSBEmpty() " + checkCockpitSBEmpty(fi, "Captain"));
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cp.getFirstSB() " + cp.getFirstSB());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cp.getSecondSB() " + cp.getSecondSB());
                    if (!fi.getCockpitStandByList().contains(cp)) {
                        if (cp.getLicence().equals(act.getType())) {
                            if (((cp.getFirstSB() < 2) && checkCockpitSBEmpty(fi, "Captain")) || ((cp.getSecondSB() < 3) && !checkCockpitSBEmpty(fi, "Captain"))) {
//                                if ((cp.getYearAccumMin() + fiMin) / 60 <= 1000) {
//                                    if ((cp.getMonthAccumMin() + fiMin) / 60 <= 100) {
                                if (checkAvailable(cp, fi)) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                    List<CockpitCrew> cps = fiInDB.getCockpitStandByList();
                                    List<FlightInstance> fiStandBy = cpInDB.getFiStandByList();

                                    // get number of captain in the fi standby list
                                    Integer capSBNum = 0;
                                    for (CockpitCrew cp1 : cps) {
                                        if (cp1.getStfLevel().equalsIgnoreCase("Captain")) {
                                            ++capSBNum;
                                        }
                                    }

                                    cps.add(cp);
                                    fiStandBy.add(fi);
                                    fiInDB.setCockpitStandByList(cps);
                                    cpInDB.setFiStandByList(fiStandBy);

                                    // ***************************** ?? *****************************
//                                    long yr = cpInDB.getYearAccumMin() + fiMin;
//                                    long mon = cpInDB.getMonthAccumMin() + fiMin;
//                                    cpInDB.setYearAccumMin(yr);
//                                    cpInDB.setMonthAccumMin(mon);
                                    // set stand by times of the crew
                                    if (capSBNum == 0) {
                                        cpInDB.setFirstSB((cpInDB.getFirstSB() + 1));
                                        System.out.println("P_P assign crew as first standby" + cp.getCpName() + " " + fi.getId());
                                    } else {
                                        cpInDB.setSecondSB((cpInDB.getSecondSB() + 1));
                                        System.out.println("P_P assign crew as second standby" + cp.getCpName() + " " + fi.getId());
                                    }

                                    em.merge(fiInDB);
                                    em.merge(cpInDB);
                                    em.flush();
                                    fiCaptain += 1;
                                }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }

            for (CockpitCrew cp : idlePilot) {
                if (fiPilot < 2) {
                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: check COCKPIT PILOT cp " + cp.getCpName());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: checkCockpitSBEmpty() " + checkCockpitSBEmpty(fi, "Pilot"));
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cp.getFirstSB() " + cp.getFirstSB());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cp.getSecondSB() " + cp.getSecondSB());
                    if (!fi.getCockpitStandByList().contains(cp)) {
                        if (cp.getLicence().equals(act.getType())) {
                            if (((cp.getFirstSB() < 2) && checkCockpitSBEmpty(fi, "Pilot")) || ((cp.getSecondSB() < 3) && !checkCockpitSBEmpty(fi, "Pilot"))) {
//                                if ((cp.getYearAccumMin() + fiMin) / 60 <= 1000) {
//                                    if ((cp.getMonthAccumMin() + fiMin) / 60 <= 100) {
                                if (checkAvailable(cp, fi)) {
                                    FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                    CockpitCrew cpInDB = em.find(CockpitCrew.class, cp.getCpName());
                                    List<CockpitCrew> cps = fiInDB.getCockpitStandByList();
                                    List<FlightInstance> fiStandBy = cpInDB.getFiStandByList();

                                    // get number of pilot in the fi standby list
                                    Integer piSBNum = 0;
                                    for (CockpitCrew cp1 : cps) {
                                        if (cp1.getStfLevel().equalsIgnoreCase("Pilot")) {
                                            ++piSBNum;
                                        }
                                    }

                                    cps.add(cp);
                                    fiStandBy.add(fi);
                                    fiInDB.setCockpitStandByList(cps);
                                    cpInDB.setFiStandByList(fiStandBy);

//                                    long yr = cpInDB.getYearAccumMin() + fiMin;
//                                    long mon = cpInDB.getMonthAccumMin() + fiMin;
//                                    cpInDB.setYearAccumMin(yr);
//                                    cpInDB.setMonthAccumMin(mon);
                                    // set stand by times of the crew
                                    if (piSBNum == 0) {
                                        cpInDB.setFirstSB((cpInDB.getFirstSB() + 1));
                                        System.out.println("P_P assign crew as first standby" + cp.getCpName() + " " + fi.getId());
                                    } else {
                                        cpInDB.setSecondSB((cpInDB.getSecondSB() + 1));
                                        System.out.println("P_P assign crew as second standby" + cp.getCpName() + " " + fi.getId());
                                    }

                                    em.merge(fiInDB);
                                    em.merge(cpInDB);
                                    em.flush();
                                    fiPilot += 1;
                                }
//                                    }
//                                }
                            }
                        }
                    }
                }
            }

            for (CabinCrew cc : idleCabinLeader) {
//                if (fiCabinLeader >= 2) {
//                    System.err.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS fiCabinLeader " + fiCabinLeader);
//                    break;
//                } else {
                if (fiCabinLeader < 2) {
                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: check CABIN CABINLEADER cc " + cc.getCbName());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: checkCabinSBEmpty() " + checkCabinSBEmpty(fi, "Cabin Leader"));
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cc.getFirstSB() " + cc.getFirstSB());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cc.getSecondSB() " + cc.getSecondSB());
                    if (!fi.getCabinStandByList().contains(cc)) {
                        if (((cc.getFirstSB() < 2) && checkCabinSBEmpty(fi, "Cabin Leader")) || ((cc.getSecondSB() < 3) && !checkCabinSBEmpty(fi, "Cabin Leader"))) {
//                            if ((cc.getYearAccumMin() + fiMin) / 60 <= 1000) {
//                                if ((cc.getMonthAccumMin() + fiMin) / 60 <= 100) {
                            if (checkAvailable(cc, fi)) {
                                FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                List<CabinCrew> ccs = fiInDB.getCabinStandByList();
                                List<FlightInstance> fiStandBy = ccInDB.getFiStandByList();

                                // get number of cabin leader in the fi standby list
                                Integer clSBNum = 0;
                                for (CabinCrew cc1 : ccs) {
                                    if (cc1.getStfLevel().equalsIgnoreCase("Cabin Leader")) {
                                        ++clSBNum;
                                    }
                                }

                                ccs.add(ccInDB);
                                fiStandBy.add(fiInDB);
                                fiInDB.setCabinStandByList(ccs);
                                ccInDB.setFiStandByList(fiStandBy);

//                                long yr = ccInDB.getYearAccumMin() + fiMin;
//                                long mon = ccInDB.getMonthAccumMin() + fiMin;
//                                ccInDB.setYearAccumMin(yr);
//                                ccInDB.setMonthAccumMin(mon);
                                // set stand by times of the crew
                                if (clSBNum == 0) {
                                    ccInDB.setFirstSB((ccInDB.getFirstSB() + 1));
                                    System.out.println("P_P assign crew as first standby" + cc.getCbName() + " for " + fi.getId());
                                } else {
                                    ccInDB.setSecondSB((ccInDB.getSecondSB() + 1));
                                    System.out.println("P_P assign crew as second standby" + cc.getCbName() + " for " + fi.getId());
                                }

                                em.merge(fiInDB);
                                em.merge(ccInDB);
                                em.flush();
                                fiCabinLeader += 1;
                                System.err.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS fiCabinLeader after adding a sb is " + fiCabinLeader);
                            }
//                                }
//                            }
                        }
                    }
                }
            }

            for (CabinCrew cc : idleCabin) {
                if (fiCabin < 2) {
                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: check CABIN CABINCREW cc " + cc.getCbName());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: checkCabinSBEmpty() " + checkCabinSBEmpty(fi, "Cabin Crew"));
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cc.getFirstSB() " + cc.getFirstSB());
//                    System.out.println("………………*************&&&&&&&&&&&&&%%%%%%%%%%%%%% idleCabin: cc.getSecondSB() " + cc.getSecondSB());
                    if (!fi.getCabinStandByList().contains(cc)) {
                        if (((cc.getFirstSB() < 2) && checkCabinSBEmpty(fi, "Cabin Crew")) || ((cc.getSecondSB() < 3) && !checkCabinSBEmpty(fi, "Cabin Crew"))) {
//                            if ((cc.getYearAccumMin() + fiMin) / 60 <= 1000) {
//                                if ((cc.getMonthAccumMin() + fiMin) / 60 <= 100) {
                            if (checkAvailable(cc, fi)) {
                                FlightInstance fiInDB = em.find(FlightInstance.class, fi.getId());
                                CabinCrew ccInDB = em.find(CabinCrew.class, cc.getCbName());
                                List<CabinCrew> ccs = fiInDB.getCabinStandByList();
                                List<FlightInstance> fiStandBy = ccInDB.getFiStandByList();

                                // get number of cabin leader in the fi standby list
                                Integer ccSBNum = 0;
                                for (CabinCrew cc1 : ccs) {
                                    if (cc1.getStfLevel().equalsIgnoreCase("Cabin Crew")) {
                                        ++ccSBNum;
                                    }
                                }

                                ccs.add(ccInDB);
                                fiStandBy.add(fiInDB);
                                fiInDB.setCabinStandByList(ccs);
                                ccInDB.setFiStandByList(fiStandBy);

//                                long yr = ccInDB.getYearAccumMin() + fiMin;
//                                long mon = ccInDB.getMonthAccumMin() + fiMin;
//                                ccInDB.setYearAccumMin(yr);
//                                ccInDB.setMonthAccumMin(mon);
                                // set stand by times of the crew
                                if (ccSBNum == 0) {
                                    ccInDB.setFirstSB((ccInDB.getFirstSB() + 1));
                                    System.out.println("P_P assign crew as first standby" + cc.getCbName() + " " + fi.getId());
                                } else {
                                    ccInDB.setSecondSB((ccInDB.getSecondSB() + 1));
                                    System.out.println("P_P assign crew as second standby" + cc.getCbName() + " " + fi.getId());
                                }

                                em.merge(fiInDB);
                                em.merge(ccInDB);
                                em.flush();
                                fiCabin += 1;
                                System.err.println("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS fiCabin after adding a sb is  " + fiCabin);
                            }
//                                }
//                            }
                        }
                    }
                }
            }
        }

        // reset monthly counter
        for (CabinCrew cc : getAllCabinCrew()) {
            CabinCrew cc1 = em.find(CabinCrew.class, cc.getCbName());
//            cc.setFirstSB(0);
//            cc.setSecondSB(0);
            cc1.setWeekAccumMin(0);
            cc1.setMonthAccumMin(0);
            em.merge(cc1);
            em.flush();
        }
        for (CockpitCrew cc : getAllCockpitCrew()) {
            CockpitCrew cc1 = em.find(CockpitCrew.class, cc.getCpName());
//            cc.setFirstSB(0);
//            cc.setSecondSB(0);
            cc.setWeekAccumMin(0);
            cc.setMonthAccumMin(0);
            em.merge(cc1);
            em.flush();
        }
    }

    @Override
    public void removeCpFromFlight(FlightInstance fi1, CockpitCrew cp1) {
        FlightInstance fi = em.find(FlightInstance.class, fi1.getId());
        CockpitCrew cp = em.find(CockpitCrew.class, cp1.getCpName());

        List<CockpitCrew> cpList = fi.getCockpitList();
        cpList.remove(cp);
        fi.setCockpitList(cpList);

        List<FlightInstance> fiList = cp.getFiList();
        fiList.remove(fi);
        cp.setFiList(fiList);

        List<FlightTask> fiTaskList = cp.getTaskList();
        FlightTask toRemove = this.findCpFlightTask(cp, fi);
        fiTaskList.remove(toRemove);
        cp.setTaskList(fiTaskList);

        em.remove(toRemove);
        em.merge(fi);
        em.merge(cp);
        em.flush();
    }

    @Override
    public void removeCcFromFlight(FlightInstance fi1, CabinCrew cc1) {
        System.out.println("AAAAA here removeCc in session bean");
        FlightInstance fi = em.find(FlightInstance.class, fi1.getId());
        CabinCrew cc = em.find(CabinCrew.class, cc1.getCbName());

        List<CabinCrew> ccList = fi.getCabinList();
        ccList.remove(cc);
        fi.setCabinList(ccList);

        List<FlightInstance> fiList = cc.getFiList();
        fiList.remove(fi);
        cc.setFiList(fiList);

        List<FlightTask> fiTaskList = cc.getTaskList();
        FlightTask toRemove = this.findCbFlightTask(cc, fi);
        fiTaskList.remove(toRemove);
        cc.setTaskList(fiTaskList);

        em.remove(toRemove);
        em.merge(fi);
        em.merge(cc);
        em.flush();
    }

    public boolean checkCabinSBEmpty(FlightInstance fi, String level) {
        List<CabinCrew> cbSB = fi.getCabinStandByList();
        for (CabinCrew cc : cbSB) {
            if (cc.getStfLevel().equalsIgnoreCase(level)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkCockpitSBEmpty(FlightInstance fi, String level) {
        List<CockpitCrew> cpSB = fi.getCockpitStandByList();
        for (CockpitCrew cp : cpSB) {
            if (cp.getStfLevel().equalsIgnoreCase(level)) {
                return false;
            }
        }
        return true;
    }

    public boolean checkAvailable(CockpitCrew cp, FlightInstance fi) {
        boolean canFly = false;
        boolean notLeave = true;
        List<FlightInstance> fiTasks = sortFiList(cp.getFiList());
        Date fiDep = fi.getStandardDepTimeDateType();
        Date fiArr = fi.getStandardArrTimeDateType();

        // check leave
        List<StaffLeave> leaves = cp.getLeaves();
        for (StaffLeave l : leaves) {
            Date start = l.getStartDate();
            Date end = l.getEndDate();
            cal.setTime(end);
            cal.add(Calendar.HOUR, 24);
            end = cal.getTime();
            if (fiDep.after(start) && fiDep.before(end) || fiArr.after(start) && fiArr.before(end)) {
                notLeave = false;
            }
        }

        // check with other flights
        cal.setTime(fiDep);
        cal.add(Calendar.HOUR, -1);
        cal.add(Calendar.SECOND, 1);
        fiDep = cal.getTime();
        cal.setTime(fiArr);
        cal.add(Calendar.HOUR, 1);
        cal.add(Calendar.SECOND, -1);
        fiArr = cal.getTime();
        System.out.println("************************ CHECK AVAILABLE CAN FLY : CP == " + cp.getCpName() + " FI == " + fi);
        if (fiTasks.isEmpty()) {    // fiTasks is empty
            System.out.println("CHECK COCKPIT CAN FLY : PASS CHECK IN 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else if (fiArr.before(fiTasks.get(0).getStandardDepTimeDateType())
                && fi.getFlightFrequency().getRoute().getDest().equals(fiTasks.get(0).getFlightFrequency().getRoute().getOrigin())) {   // fi is before the first of fiTasks
            System.out.println("CHECK COCKPIT CAN FLY : PASS CHECK IN 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else if (fiDep.after(fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType())
                && fi.getFlightFrequency().getRoute().getOrigin().equals(fiTasks.get(fiTasks.size() - 1).getFlightFrequency().getRoute().getDest())) {   // fi is after the last of fiTasks
            System.out.println("\n\n CHECK COCKPIT CAN FLY : CHECK 3 DETAIL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            System.out.println(" ****** fiDep " + fiDep);
            System.out.println(" ****** fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType() " + fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType());
            System.out.println(" ****** fi.getFlightFrequency().getRoute().getDest() " + fi.getFlightFrequency().getRoute().getDest());
            System.out.println(" ****** fiTasks.get(0).getFlightFrequency().getRoute().getOrigin() " + fiTasks.get(0).getFlightFrequency().getRoute().getOrigin());
            System.out.println("CHECK COCKPIT CAN FLY : CHECK 3 DETAIL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> FINISH \n");
//            System.out.println("CHECK COCKPIT CAN FLY : CHECK 3 DETAIL >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println("CHECK COCKPIT CAN FLY : PASS CHECK IN 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else {
            for (int i = 0; i < fiTasks.size() - 1; i++) {
                FlightInstance f1 = fiTasks.get(i);
                FlightInstance f2 = fiTasks.get(i + 1);
                if (fiDep.after(f1.getStandardArrTimeDateType()) && fiArr.before(f2.getStandardDepTimeDateType())
                        && fi.getFlightFrequency().getRoute().getOrigin().equals(f1.getFlightFrequency().getRoute().getDest())
                        && fi.getFlightFrequency().getRoute().getDest().equals(f2.getFlightFrequency().getRoute().getOrigin())) {
                    System.out.println("CHECK COCKPIT CAN FLY : PASS CHECK IN 4 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    canFly = true;
                }
            }
        }
        return canFly && notLeave;
    }

    public boolean checkAvailable(CabinCrew cc, FlightInstance fi) {
        boolean canFly = false;
        List<FlightInstance> fiTasks = this.sortFiList(cc.getFiList());
        Date fiDep = fi.getStandardDepTimeDateType();
        Date fiArr = fi.getStandardArrTimeDateType();
        Calendar cal = new GregorianCalendar();
        cal.setTime(fiDep);
        cal.add(Calendar.HOUR, -1);
        cal.add(Calendar.SECOND, 1);
        fiDep = cal.getTime();
        cal.setTime(fiArr);
        cal.add(Calendar.HOUR, 1);
        cal.add(Calendar.SECOND, -1);
        fiArr = cal.getTime();
        System.out.println("************************ CHECK AVAILABLE CAN FLY : CC == " + cc.getCbName() + " FI == " + fi);
        if (fiTasks.isEmpty()) {    // fiTasks is empty
            System.out.println("CHECK CABIN CAN FLY : PASS CHECK IN 1 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else if (fiArr.before(fiTasks.get(0).getStandardDepTimeDateType())
                && fi.getFlightFrequency().getRoute().getDest().equals(fiTasks.get(0).getFlightFrequency().getRoute().getOrigin())) {   // fi is before the first of fiTasks
            System.out.println("CHECK CABIN CAN FLY : PASS CHECK IN 2 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else if (fiDep.after(fiTasks.get(fiTasks.size() - 1).getStandardArrTimeDateType())
                && fi.getFlightFrequency().getRoute().getOrigin().equals(fiTasks.get(fiTasks.size() - 1).getFlightFrequency().getRoute().getDest())) {   // fi is after the last of fiTasks
            System.out.println("CHECK CABIN CAN FLY : PASS CHECK IN 3 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            canFly = true;
        } else {
            for (int i = 0; i < fiTasks.size() - 1; i++) {
                FlightInstance f1 = fiTasks.get(i);
                FlightInstance f2 = fiTasks.get(i + 1);
                if (fiDep.after(f1.getStandardArrTimeDateType()) && fiArr.before(f2.getStandardDepTimeDateType())
                        && fi.getFlightFrequency().getRoute().getOrigin().equals(f1.getFlightFrequency().getRoute().getDest())
                        && fi.getFlightFrequency().getRoute().getDest().equals(f2.getFlightFrequency().getRoute().getOrigin())) {
                    System.out.println("CHECK CABIN CAN FLY : PASS CHECK IN 4 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    canFly = true;
                }
            }
        }
        return canFly;
    }

    @Override
    public List<CabinCrew> getIdleCabin(String cabinType, FlightInstance fi) {
        List<CabinCrew> cbList = new ArrayList<>();
        List<CabinCrew> idleCb = new ArrayList<>();
        if (cabinType.equalsIgnoreCase("Cabin Crew")) {
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

    @Override
    public List<CockpitCrew> getIdleCockpit(String cabinType, FlightInstance fi) {
        List<CockpitCrew> cpList = new ArrayList<>();
        List<CockpitCrew> idleCp = new ArrayList<>();
        if (cabinType.equalsIgnoreCase("Captain")) {
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
        List<CabinCrew> ccNewCopy = new ArrayList<>();
        for (CabinCrew cc : ccList) {
            ccNewCopy.add(cc);
        }
        for (CabinCrew cc : ccNewCopy) {
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

    @Override
    public List<CabinCrew> getAllCabinCrew() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a");
        List<CabinCrew> allCabin = (List<CabinCrew>) q1.getResultList();
        return allCabin;
    }

    @Override
    public List<CabinCrew> getAllCabin() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.stfLevel=:level").setParameter("level", "Cabin Crew");
        List<CabinCrew> cabinList = (List<CabinCrew>) q1.getResultList();
        return cabinList;
    }

    @Override
    public List<CabinCrew> getAllCabinLeader() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.stfLevel=:level").setParameter("level", "Cabin Leader");
        List<CabinCrew> leaderList = (List<CabinCrew>) q1.getResultList();
        return leaderList;
    }

    @Override
    public List<CockpitCrew> getAllCockpitCrew() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a");
        List<CockpitCrew> allCockpit = (List<CockpitCrew>) q1.getResultList();
        return allCockpit;
    }

    @Override
    public List<CockpitCrew> getAllCaptain() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.stfLevel=:level").setParameter("level", "Captain");
        List<CockpitCrew> captainList = (List<CockpitCrew>) q1.getResultList();
        return captainList;
    }

    @Override
    public List<CockpitCrew> getAllPilot() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.stfLevel=:level").setParameter("level", "Pilot");
        List<CockpitCrew> pilotList = (List<CockpitCrew>) q1.getResultList();
        return pilotList;
    }

    @Override
    public List<OfficeStaff> getAllOfficeStaff() {
        Query q = em.createQuery("SELECT o FROM OfficeStaff o");
        List<OfficeStaff> officeStaff = (List<OfficeStaff>) q.getResultList();
        return officeStaff;
    }

    @Override
    public List<GroundStaff> getAllGroundStaff() {
        Query q = em.createQuery("SELECT g FROM GroundStaff g");
        List<GroundStaff> groundStaff = (List<GroundStaff>) q.getResultList();
        return groundStaff;
    }

    @Override
    public List<CockpitCrew> getFiCaptain(FlightInstance fi) {
        List<CockpitCrew> cpList = fi.getCockpitList();
        List<CockpitCrew> cpCap = new ArrayList<>();
        for (CockpitCrew cp1 : cpList) {
            if (cp1.getStfLevel().equalsIgnoreCase("Captain")) {
                cpCap.add(cp1);
            }
        }
        return cpCap;
    }

    public List<CockpitCrew> getFiPilot(FlightInstance fi) {
        List<CockpitCrew> cpList = fi.getCockpitList();
        List<CockpitCrew> cpCap = new ArrayList<>();
        for (CockpitCrew cp1 : cpList) {
            if (cp1.getStfLevel().equalsIgnoreCase("Pilot")) {
                cpCap.add(cp1);
            }
        }
        return cpCap;
    }

    public List<CabinCrew> getFiCabinCrew(FlightInstance fi) {
        List<CabinCrew> ccList = fi.getCabinList();
        List<CabinCrew> ccCabin = new ArrayList<>();
        for (CabinCrew cc1 : ccList) {
            if (cc1.getStfLevel().equalsIgnoreCase("Cabin Crew")) {
                ccCabin.add(cc1);
            }
        }
        return ccCabin;
    }

    public List<CabinCrew> getFiCabinLeader(FlightInstance fi) {
        List<CabinCrew> ccList = fi.getCabinList();
        List<CabinCrew> ccCabin = new ArrayList<>();
        for (CabinCrew cc1 : ccList) {
            if (cc1.getStfLevel().equalsIgnoreCase("Cabin Leader")) {
                ccCabin.add(cc1);
            }
        }
        return ccCabin;
    }

    public List<CockpitCrew> getFiCaptainSB(FlightInstance fi) {
        List<CockpitCrew> cpList = fi.getCockpitStandByList();
        List<CockpitCrew> cpCap = new ArrayList<>();
        for (CockpitCrew cp1 : cpList) {
            if (cp1.getStfLevel().equalsIgnoreCase("Captain")) {
                cpCap.add(cp1);
            }
        }
        return cpCap;
    }

    public List<CockpitCrew> getFiPilotSB(FlightInstance fi) {
        List<CockpitCrew> cpList = fi.getCockpitStandByList();
        List<CockpitCrew> cpCap = new ArrayList<>();
        for (CockpitCrew cp1 : cpList) {
            if (cp1.getStfLevel().equalsIgnoreCase("Pilot")) {
                cpCap.add(cp1);
            }
        }
        return cpCap;
    }

    public List<CabinCrew> getFiCabinCrewSB(FlightInstance fi) {
        List<CabinCrew> ccList = fi.getCabinStandByList();
        List<CabinCrew> ccCabin = new ArrayList<>();
        for (CabinCrew cc1 : ccList) {
            if (cc1.getStfLevel().equalsIgnoreCase("Cabin Crew")) {
                ccCabin.add(cc1);
            }
        }
        return ccCabin;
    }

    public List<CabinCrew> getFiCabinLeaderSB(FlightInstance fi) {
        List<CabinCrew> ccList = fi.getCabinStandByList();
        List<CabinCrew> ccCabin = new ArrayList<>();
        for (CabinCrew cc1 : ccList) {
            if (cc1.getStfLevel().equalsIgnoreCase("Cabin Leader")) {
                ccCabin.add(cc1);
            }
        }
        return ccCabin;
    }

    @Override
    public long calCockpitTotalFlightHour(CockpitCrew cp, Date startDate, Date endDate) {
        int totalMin = 0;
        for (FlightInstance f1 : cp.getFiList()) {
            if (f1.getStandardDepTimeDateType().after(startDate) && f1.getStandardDepTimeDateType().before(endDate)) {
                totalMin += fsb.getFlightAccumMinute(f1.getFlightFrequency());
            }
        }
        return totalMin / 60;
    }

    @Override
    public long calCabinTotalFlightHour(CabinCrew cc, Date startDate, Date endDate) {
        int totalMin = 0;
        for (FlightInstance f1 : cc.getFiList()) {
            if (f1.getStandardDepTimeDateType().after(startDate) && f1.getStandardDepTimeDateType().before(endDate)) {
                totalMin += fsb.getFlightAccumMinute(f1.getFlightFrequency());
            }
        }
        return totalMin / 60;
    }

    @Override
    public Double calCaptainTotalHourPay(Date startDate, Date endDate) {
        Double totalHr = 0.0;
        for (CockpitCrew cp : getAllCaptain()) {
            totalHr += cp.getHourPay() * calCockpitTotalFlightHour(cp, startDate, endDate);
        }
        return totalHr;
    }

    @Override
    public Double calPilotTotalHourPay(Date startDate, Date endDate) {
        Double totalHr = 0.0;
        for (CockpitCrew cp : getAllPilot()) {
            totalHr += cp.getHourPay() * calCockpitTotalFlightHour(cp, startDate, endDate);
        }
        return totalHr;
    }

    @Override
    public Double calCabinCrewTotalHourPay(Date startDate, Date endDate) {
        Double totalHr = 0.0;
        for (CabinCrew cc : getAllCabin()) {
            totalHr += cc.getHourPay() * calCabinTotalFlightHour(cc, startDate, endDate);
        }
        return totalHr;
    }

    @Override
    public Double calCabinLeaderTotalHourPay(Date startDate, Date endDate) {
        Double totalHr = 0.0;
        for (CabinCrew cc : getAllCabinLeader()) {
            totalHr += cc.getHourPay() * calCabinTotalFlightHour(cc, startDate, endDate);
        }
        return totalHr;
    }

    @Override
    public List<FlightInstance> getCockpitCrewFlightForPeriod(CockpitCrew cp, Date startDate, Date endDate) {
        List<FlightInstance> fiList = cp.getFiList();
        List<FlightInstance> fiNew = new ArrayList<>();
        for (FlightInstance f1 : fiList) {
            if (f1.getStandardDepTimeDateType().after(startDate) && f1.getStandardDepTimeDateType().before(endDate)) {
                fiNew.add(f1);
            }
        }
        return fiNew;
    }

    @Override
    public List<FlightInstance> getCabinCrewFlightForPeriod(CabinCrew cc, Date startDate, Date endDate) {
        List<FlightInstance> fiList = cc.getFiList();
        List<FlightInstance> fiNew = new ArrayList<>();
        for (FlightInstance f1 : fiList) {
            if (f1.getStandardDepTimeDateType().after(startDate) && f1.getStandardDepTimeDateType().before(endDate)) {
                fiNew.add(f1);
            }
        }
        return fiNew;
    }

    @Override
    public GroundStaffTeam getGroundStaffTeamById(Integer id) {
        GroundStaffTeam gst = em.find(GroundStaffTeam.class, id);
        return gst;
    }

    @Override
    public Rotation findRotOnDate(GroundStaffTeam gst, Date day) {
        List<Rotation> rotList = gst.getRotationList();
        Rotation rot = new Rotation();
        for (Rotation r1 : rotList) {
            if (r1.getWorkDate().equals(day)) {
                rot = r1;
            }
        }
        return rot;
    }

    @Override
    public boolean checkGroundCrewScheduled(Date startDate, Date endDate) {
        Query q1 = em.createQuery("SELECT a FROM Rotation a");
        List<Rotation> rot = q1.getResultList();

        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        System.err.println("TESTING: diffDays " + diffDays);
        Date newDay = new Date();

        for (int i = 0; i < diffDays; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DATE, i);
            newDay = cal.getTime();
            boolean flag1 = false;
            for (Rotation r : rot) {
                if (r.getWorkDate().equals(newDay)) {
                    flag1 = true;
                }
            }
            if (flag1 == false) {
                return false;
            }
        }
        return true;
    }

    @Override
    public List<FlightInstance> getFlightOnDate(String date) {
        Query q1 = em.createQuery("SELECT f FROM FlightInstance f where f.date=:fdate").setParameter("fdate", date);
        List<FlightInstance> flightInst = q1.getResultList();
        return flightInst;
    }

    @Override
    public FlightTask findCpFlightTask(CockpitCrew cp, FlightInstance selectedFi) {
        Query q1 = em.createQuery("SELECT f FROM FlightTask f where f.flightNo=:flightNo and f.flightDate=:flightDate and f.crewId=:crewId").setParameter("flightNo", selectedFi.getFlightFrequency().getFlightNo()).setParameter("flightDate", selectedFi.getStandardDepTimeDateType()).setParameter("crewId", cp.getCpName());
        FlightTask ft1 = (FlightTask) q1.getResultList().get(0);
//        if (ft1 == null) {
//            throw new Exception("No flight task of cockpit crew " + cp.getCpName() + " for flight " + selectedFi.getFlightFrequency().getFlightNo() + " on " + selectedFi.getDate());
//        }
        return ft1;
    }

    @Override
    public FlightTask findCbFlightTask(CabinCrew cb, FlightInstance selectedFi) {
        Query q1 = em.createQuery("SELECT f FROM FlightTask f where f.flightNo=:flightNo and f.flightDate=:flightDate and f.crewId=:crewId").setParameter("flightNo", selectedFi.getFlightFrequency().getFlightNo()).setParameter("flightDate", selectedFi.getStandardDepTimeDateType()).setParameter("crewId", cb.getCbName());
        FlightTask ft1 = (FlightTask) q1.getResultList().get(0);
//        if (ft1 == null) {
//            throw new Exception("No flight task of cabin crew " + cb.getCbName() + " for flight " + selectedFi.getFlightFrequency().getFlightNo() + " on " + selectedFi.getDate());
//        }
        return ft1;
    }

    @Override
    public void cpSignIn(FlightInstance selectedFi, CockpitCrew cp) {
        Query q1 = em.createQuery("SELECT f FROM FlightTask f where f.flightNo=:flightNo and f.flightDate=:flightDate and f.crewId=:crewId").setParameter("flightNo", selectedFi.getFlightFrequency().getFlightNo()).setParameter("flightDate", selectedFi.getStandardDepTimeDateType()).setParameter("crewId", cp.getCpName());
        FlightTask ft1 = (FlightTask) q1.getResultList().get(0);
        ft1.setStatus("signed-in");
        em.merge(ft1);
        em.flush();
    }

    @Override
    public void cbSignIn(FlightInstance selectedFi, CabinCrew cb) {
        Query q1 = em.createQuery("SELECT f FROM FlightTask f where f.flightNo=:flightNo and f.flightDate=:flightDate and f.crewId=:crewId").setParameter("flightNo", selectedFi.getFlightFrequency().getFlightNo()).setParameter("flightDate", selectedFi.getStandardDepTimeDateType()).setParameter("crewId", cb.getCbName());
        FlightTask ft1 = (FlightTask) q1.getResultList().get(0);
        ft1.setStatus("signed-in");
        em.merge(ft1);
        em.flush();
    }

    @Override
    public String getCrewType(String id) {
        String type = "";
        Query q1 = em.createQuery("SELECT c FROM CockpitCrew c");
        List<CockpitCrew> cpList = (List<CockpitCrew>) q1.getResultList();
        for (CockpitCrew cp : cpList) {
            if (cp.getCpName().equalsIgnoreCase(id)) {
                type = "Cockpit";
                break;
            }
        }
        Query q2 = em.createQuery("SELECT c FROM CabinCrew c");
        List<CabinCrew> ccList = (List<CabinCrew>) q2.getResultList();
        for (CabinCrew cc : ccList) {
            if (cc.getCbName().equalsIgnoreCase(id)) {
                type = "Cabin";
                break;
            }
        }
        Query q3 = em.createQuery("SELECT c FROM GroundStaff c");
        List<GroundStaff> gsList = (List<GroundStaff>) q3.getResultList();
        for (GroundStaff gs : gsList) {
            if (gs.getGrdName().equalsIgnoreCase(id)) {
                type = "Ground";
                break;
            }
        }
        return type;
    }

    @Override
    public void addCPToFlight(String crewId, Long fiId) throws Exception {
        CockpitCrew cc = this.findCPById(crewId);
        FlightInstance fi = em.find(FlightInstance.class, fiId);
        List<CockpitCrew> ccList = fi.getCockpitList();
        List<CockpitCrew> captain = new ArrayList<>();
        List<CockpitCrew> pilot = new ArrayList<>();
        for (CockpitCrew cc1 : ccList) {
            if (cc1.getStfType().equalsIgnoreCase("Captain")) {
                captain.add(cc1);
            }
            if (cc1.getStfType().equalsIgnoreCase("Pilot")) {
                pilot.add(cc1);
            }
        }
        Integer captainNo = captain.size();
        System.out.println("captainNo = " + captainNo);
        Integer pilotNo = pilot.size();
        System.out.println("pilotNo = " + pilotNo);
        switch (cc.getStfLevel()) {
            case "Captain": {
                System.out.println("here = ");
                if (captainNo >= fi.getFlightFrequency().getRoute().getAcType().getCaptain()) {
                    System.out.println("here ==");
                    throw new Exception("Enough captain for this flight.");
                }
                break;
            }
            case "Pilot": {
                System.out.println("here2 = ");
                if (pilotNo >= fi.getFlightFrequency().getRoute().getAcType().getPilot()) {
                    System.out.println("here2 == ");
                    throw new Exception("Enough pilot for this flight.");
                }
                break;
            }
        }
        ccList.add(cc);
        fi.setCockpitList(ccList);
        List<FlightInstance> fiList = cc.getFiList();
        fiList.add(fi);
        cc.setFiList(fiList);

        List<FlightTask> cpFiTasks = cc.getTaskList();
        FlightTask cpFiT = new FlightTask();
        cpFiT.create(cc.getCpName(), fi.getFlightFrequency().getFlightNo(), fi.getStandardDepTimeDateType(), "assigned");
        em.persist(cpFiT);
        cpFiTasks.add(cpFiT);
        cc.setTaskList(cpFiTasks);

        em.merge(cc);
        em.merge(fi);
        em.flush();
    }

    @Override
    public void addCCToFlight(String crewId, Long fiId) throws Exception {
        CabinCrew cc = this.findCCById(crewId);
        FlightInstance fi = em.find(FlightInstance.class, fiId);
        List<CabinCrew> ccList = fi.getCabinList();
        List<CabinCrew> cabinLeader = new ArrayList<>();
        List<CabinCrew> cabinCrew = new ArrayList<>();
        for (CabinCrew cc1 : ccList) {
            if (cc1.getStfType().equalsIgnoreCase("Cabin Leader")) {
                cabinLeader.add(cc1);
            }
            if (cc1.getStfType().equalsIgnoreCase("Cabin Crew")) {
                cabinCrew.add(cc1);
            }
        }
        Integer clNo = cabinLeader.size();
        Integer ccNo = cabinCrew.size();
        Integer seatNum = fi.getFlightFrequency().getRoute().getAcType().getTotalSeatNum();
        Double temp1 = 0.0;
        temp1 = fi.getFlightFrequency().getRoute().getAcType().getCabinLeader() * seatNum;
        Integer reqClNo = temp1.intValue();
        temp1 = fi.getFlightFrequency().getRoute().getAcType().getCabinCrew() * seatNum;
        Integer reqCcNo = temp1.intValue();
        switch (cc.getStfLevel()) {
            case "Cabin Leader": {
                if (clNo >= reqClNo) {
                    throw new Exception("Enough cabin leader for this flight.");
                }
                break;
            }
            case "Cabin Crew": {
                if (ccNo >= reqCcNo) {
                    throw new Exception("Enough cabin crew for this flight.");
                }
                break;
            }
        }
        ccList.add(cc);
        fi.setCabinList(ccList);
        List<FlightInstance> fiList = cc.getFiList();
        fiList.add(fi);
        cc.setFiList(fiList);

        List<FlightTask> cpFiTasks = cc.getTaskList();
        FlightTask cpFiT = new FlightTask();
        cpFiT.create(cc.getCbName(), fi.getFlightFrequency().getFlightNo(), fi.getStandardDepTimeDateType(), "assigned");
        em.persist(cpFiT);
        cpFiTasks.add(cpFiT);
        cc.setTaskList(cpFiTasks);

        em.merge(cc);
        em.merge(fi);
        em.flush();
    }

    @Override
    public List<CockpitCrew> getIdleLicensedCockpit(String cabinType, FlightInstance fi) {
        List<CockpitCrew> cpList = new ArrayList<>();
        List<CockpitCrew> idleCp = this.getIdleCockpit(cabinType, fi);
        for (CockpitCrew cc : idleCp) {
            if (cc.getLicence().equalsIgnoreCase(fi.getFlightFrequency().getRoute().getAcType().getType())) {
                cpList.add(cc);
            }
        }
        return cpList;
    }

}
