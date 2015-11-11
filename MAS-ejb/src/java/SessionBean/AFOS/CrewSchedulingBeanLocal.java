/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
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

}
