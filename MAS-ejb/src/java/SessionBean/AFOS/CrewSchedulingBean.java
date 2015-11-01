/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.FlightCrewTeam;
import Entity.APS.AircraftType;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.util.ArrayList;
import java.util.Date;
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
    
    public List<CabinCrew> getAllCabinCrew() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a");
        List<CabinCrew> allCabin = (ArrayList<CabinCrew>) q1.getResultList();
        return allCabin;
    }

    public List<CabinCrew> getAllCabin() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.level=:level").setParameter("level", "cabin");
        List<CabinCrew> cabinList = (ArrayList<CabinCrew>) q1.getResultList();
        return cabinList;
    }

    public List<CabinCrew> getAllCabinLeader() {
        Query q1 = em.createQuery("SELECT a FROM CabinCrew a where a.level=:level").setParameter("level", "leader");
        List<CabinCrew> leaderList = (ArrayList<CabinCrew>) q1.getResultList();
        return leaderList;
    }

    public List<CockpitCrew> getAllCaptain() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.level=:level").setParameter("level", "captain");
        List<CockpitCrew> captainList = (ArrayList<CockpitCrew>) q1.getResultList();
        return captainList;
    }

    public List<CockpitCrew> getAllPilot() {
        Query q1 = em.createQuery("SELECT a FROM CockpitCrew a where a.level=:level").setParameter("level", "pilot");
        List<CockpitCrew> pilotList = (ArrayList<CockpitCrew>) q1.getResultList();
        return pilotList;
    }

    public void scheduleFlightCrewGroup(Date startDate, Date endDate) {
    
        
    }
    
}
