/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VFCSMB")
@ViewScoped
public class ViewFlightCrewScheduleManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;
    @EJB
    private FlightSchedulingBeanLocal fsb;

    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;
    private List<FlightInstance> fiList;
    private List<CabinCrew> cabinList;
    private List<CockpitCrew> cockpitList;

    private Map<FlightInstance, String> captainMap = new HashMap<>();
    private Map<FlightInstance, String> captainSBMap = new HashMap<>();
    private Map<FlightInstance, String> pilotMap = new HashMap<>();
    private Map<FlightInstance, String> pilotSBMap = new HashMap<>();
    private Map<FlightInstance, String> cabinMap = new HashMap<>();
    private Map<FlightInstance, String> cabinSBMap = new HashMap<>();
    private Map<FlightInstance, String> cabinLeaderMap = new HashMap<>();
    private Map<FlightInstance, String> cabinLeaderSBMap = new HashMap<>();

    public ViewFlightCrewScheduleManagedBean() {
    }

    @PostConstruct
    public void init() {
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDate");
        fiList = fsb.getSortedFiWithinPeriod(startDate, endDate);
        initFCMap(fiList);
    }

    public void initFCMap(List<FlightInstance> fiList) {
        for (FlightInstance fi : fiList) {
            List<CockpitCrew> cc1 = fi.getCockpitList();
            List<CockpitCrew> cc3 = fi.getCockpitStandByList();
            String captainString = "";
            String captainSBString = "";
            String pilotString = "";
            String pilotSBString = "";
            List<CabinCrew> cc2 = fi.getCabinList();
            List<CabinCrew> cc4 = fi.getCabinStandByList();
            String cabinString = "";
            String cabinSBString = "";
            String cabinLeaderString = "";
            String cabinLeaderSBString = "";
            for (CockpitCrew cc : cc1) {
                if (cc.getStfLevel().equals("captain")) {
                    captainString += captainString + cc.getCpName() + " ";
                } else if (cc.getStfLevel().equals("pilot")) {
                    pilotString += pilotString + cc.getCpName() + " ";
                }
            }
            for (CockpitCrew cc : cc3) {
                if (cc.getStfLevel().equals("captain")) {
                    captainSBString += captainSBString + cc.getCpName() + " ";
                } else if (cc.getStfLevel().equals("pilot")) {
                    pilotSBString += pilotSBString + cc.getCpName() + " ";
                }
            }
            for (CabinCrew cc : cc2) {
                if (cc.getStfLevel().equals("cabinCrew")) {
                    cabinString += cabinString + cc.getCbName() + " ";
                } else if (cc.getStfLevel().equals("cabinLeader")) {
                    cabinLeaderString += cabinLeaderString + cc.getCbName() + " ";
                }
            }
            for (CabinCrew cc : cc4) {
                if (cc.getStfLevel().equals("cabinCrew")) {
                    cabinSBString += cabinSBString + cc.getCbName() + " ";
                } else if (cc.getStfLevel().equals("cabinLeader")) {
                    cabinLeaderSBString += cabinLeaderSBString + cc.getCbName() + " ";
                }
            }
            captainMap.put(fi, captainString);
            pilotMap.put(fi, pilotString);
            cabinMap.put(fi, cabinString);
            cabinLeaderMap.put(fi, cabinLeaderString);
            captainSBMap.put(fi, captainSBString);
            pilotSBMap.put(fi, pilotSBString);
            cabinSBMap.put(fi, cabinSBString);
            cabinLeaderSBMap.put(fi, cabinLeaderSBString);
        }
    }

    public void flightCrewSchdulingForPeriod() throws Exception {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = df.format(startDate);
        endDateString = df.format(endDate);
        csb.scheduleFlightCrew(startDate, endDate);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startDateString", startDateString);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endDateString", endDateString);
    }

    public void viewFlightCrew(FlightInstance fi) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fi", fi);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrew.xhtml");
    }

    public void viewCabinCrewSchedule() {

    }

    public void viewCockpitCrewSchedule() {

    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public List<CabinCrew> getCabinList() {
        return cabinList;
    }

    public void setCabinList(List<CabinCrew> cabinList) {
        this.cabinList = cabinList;
    }

    public List<CockpitCrew> getCockpitList() {
        return cockpitList;
    }

    public void setCockpitList(List<CockpitCrew> cockpitList) {
        this.cockpitList = cockpitList;
    }

    public Map<FlightInstance, String> getCaptainMap() {
        return captainMap;
    }

    public void setCaptainMap(Map<FlightInstance, String> captainMap) {
        this.captainMap = captainMap;
    }

    public Map<FlightInstance, String> getCaptainSBMap() {
        return captainSBMap;
    }

    public void setCaptainSBMap(Map<FlightInstance, String> captainSBMap) {
        this.captainSBMap = captainSBMap;
    }

    public Map<FlightInstance, String> getPilotMap() {
        return pilotMap;
    }

    public void setPilotMap(Map<FlightInstance, String> pilotMap) {
        this.pilotMap = pilotMap;
    }

    public Map<FlightInstance, String> getPilotSBMap() {
        return pilotSBMap;
    }

    public void setPilotSBMap(Map<FlightInstance, String> pilotSBMap) {
        this.pilotSBMap = pilotSBMap;
    }

    public Map<FlightInstance, String> getCabinMap() {
        return cabinMap;
    }

    public void setCabinMap(Map<FlightInstance, String> cabinMap) {
        this.cabinMap = cabinMap;
    }

    public Map<FlightInstance, String> getCabinSBMap() {
        return cabinSBMap;
    }

    public void setCabinSBMap(Map<FlightInstance, String> cabinSBMap) {
        this.cabinSBMap = cabinSBMap;
    }

    public Map<FlightInstance, String> getCabinLeaderMap() {
        return cabinLeaderMap;
    }

    public void setCabinLeaderMap(Map<FlightInstance, String> cabinLeaderMap) {
        this.cabinLeaderMap = cabinLeaderMap;
    }

    public Map<FlightInstance, String> getCabinLeaderSBMap() {
        return cabinLeaderSBMap;
    }

    public void setCabinLeaderSBMap(Map<FlightInstance, String> cabinLeaderSBMap) {
        this.cabinLeaderSBMap = cabinLeaderSBMap;
    }

}
