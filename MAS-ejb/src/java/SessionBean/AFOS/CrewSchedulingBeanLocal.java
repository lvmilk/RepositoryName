/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.FlightTask;
import Entity.AFOS.GroundStaffTeam;
import Entity.AFOS.Rotation;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface CrewSchedulingBeanLocal {

    public void scheduleFlightCrew(Date startDate, Date endDate) throws ParseException;

    public List<CabinCrew> getAllCabinCrew();

    public List<CockpitCrew> getAllCockpitCrew();

    public List<CabinCrew> getAllCabin();

    public List<CabinCrew> getAllCabinLeader();

    public List<CockpitCrew> getAllCaptain();

    public List<CockpitCrew> getAllPilot();

    public long calCockpitTotalFlightHour(CockpitCrew cp, Date startDate, Date endDate);

    public long calCabinTotalFlightHour(CabinCrew cc, Date startDate, Date endDate);

    public Double calCaptainTotalHourPay(Date startDate, Date endDate);

    public Double calPilotTotalHourPay(Date startDate, Date endDate);

    public Double calCabinCrewTotalHourPay(Date startDate, Date endDate);

    public Double calCabinLeaderTotalHourPay(Date startDate, Date endDate);

    public List<FlightInstance> getCockpitCrewFlightForPeriod(CockpitCrew cp, Date startDate, Date endDate);

    public List<FlightInstance> getCabinCrewFlightForPeriod(CabinCrew cc, Date startDate, Date endDate);

    public void scheduleGS(Date startDate, Date endDate);

    public void groupGroundCrew() throws Exception;

    public List<GroundStaff> getUngroupedGroundStaff();

    public List<GroundStaff> getGroundStaffTeam(Integer id);

    public List<GroundStaff> getAllGroundStaff();

    public void deleteGSFromGroup(GroundStaff gs1, Integer teamId);

    public void addGSToGroup(GroundStaff gs1, Integer teamId);

    public GroundStaffTeam getGroundStaffTeamById(Integer id);

    public Rotation findRotOnDate(GroundStaffTeam gst, Date day);

    public boolean checkGroundCrewScheduled(Date startDate, Date endDate);

    public List<OfficeStaff> getAllOfficeStaff();

    public List<FlightInstance> getFlightOnDate(String date);

    public FlightTask findCpFlightTask(CockpitCrew cp, FlightInstance selectedFi);

    public FlightTask findCbFlightTask(CabinCrew cb, FlightInstance selectedFi);

    public void cpSignIn(FlightInstance selectedFi, CockpitCrew cp);

    public void cbSignIn(FlightInstance selectedFi, CabinCrew cb);

    public List<CockpitCrew> getFiCaptain(FlightInstance fi);

    public void removeCpFromFlight(FlightInstance fi1, CockpitCrew cp1);

    public void removeCcFromFlight(FlightInstance fi1, CabinCrew cc1);

    public GroundStaff findGSById(String gsId);

    public CockpitCrew findCPById(String cpId);

    public CabinCrew findCCById(String ccId);

    public List<CabinCrew> getIdleCabin(String cabinType, FlightInstance fi);

    public List<CockpitCrew> getIdleCockpit(String cabinType, FlightInstance fi);

    public String getCrewType(String id);

    public void addCPToFlight(String crewId, Long fiId) throws Exception;

    public void addCCToFlight(String crewId, Long fiId) throws Exception;

    public List<CockpitCrew> getIdleLicensedCockpit(String cabinType, FlightInstance fi);

}
