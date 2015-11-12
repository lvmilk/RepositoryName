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
import java.io.IOException;
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

    private Map<CockpitCrew, Long> cockpitFHMap = new HashMap<>();
    private Map<CabinCrew, Long> cabinFHMap = new HashMap<>();

    public ViewFlightCrewScheduleManagedBean() {
    }

    @PostConstruct
    public void init() {
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startViewScheduleDate");
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endViewScheduleDate");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        startDateString = df.format(startDate);
        endDateString = df.format(endDate);
        cockpitList = csb.getAllCockpitCrew();
        cabinList = csb.getAllCabinCrew();
        initFHMap();
    }

    public void initFHMap() {
        Long totalhour;
        for (CockpitCrew cp : cockpitList) {
            totalhour = csb.calCockpitTotalFlightHour(cp, startDate, endDate);
            cockpitFHMap.put(cp, totalhour);
        }
        for (CabinCrew cc : cabinList) {
            totalhour = csb.calCabinTotalFlightHour(cc, startDate, endDate);
            cabinFHMap.put(cc, totalhour);
        }
    }

    public void viewCockpitCrewSchedule(CockpitCrew cp) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewScheduleCP", cp);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightCrewScheduleDetail.xhtml");
    }

    public void viewCabinCrewSchedule(CabinCrew cc) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewScheduleCC", cc);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCabinCrewScheduleDetail.xhtml");
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

    public Map<CockpitCrew, Long> getCockpitFHMap() {
        return cockpitFHMap;
    }

    public void setCockpitFHMap(Map<CockpitCrew, Long> cockpitFHMap) {
        this.cockpitFHMap = cockpitFHMap;
    }

    public Map<CabinCrew, Long> getCabinFHMap() {
        return cabinFHMap;
    }

    public void setCabinFHMap(Map<CabinCrew, Long> cabinFHMap) {
        this.cabinFHMap = cabinFHMap;
    }

}
