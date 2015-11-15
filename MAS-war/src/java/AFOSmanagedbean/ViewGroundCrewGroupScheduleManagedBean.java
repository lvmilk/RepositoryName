/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.GroundStaffTeam;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "VGCGSMB")
@ViewScoped
public class ViewGroundCrewGroupScheduleManagedBean implements Serializable {

    @EJB
    private CrewSchedulingBeanLocal csb;

    private Date startDate;
    private Date endDate;
    private String startDateString;
    private String endDateString;

    private List<Date> dateList = new ArrayList<>();
    private Map<Date, Integer> morningMap = new HashMap<>();
    private Map<Date, Integer> afternoonMap = new HashMap<>();
    private Map<Date, Integer> nightMap = new HashMap<>();
    private Map<Date, Integer> standbyMap = new HashMap<>();

    private List<Date> filteredDateList = new ArrayList<>();
    private List<Integer> groupNo = Arrays.asList(1,2,3,4);

    public ViewGroundCrewGroupScheduleManagedBean() {
    }

    @PostConstruct
    public void init() {
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        endDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDate");
        startDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDateString");
        endDateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("endDateString");
        initDateList(startDate, endDate);
        initGCMap(startDate, endDate);
//        groupNo.add(1);
//        groupNo.add(2);
//        groupNo.add(3);
//        groupNo.add(4);
    }

    public void initDateList(Date startDate, Date endDate) {
        Calendar cal = new GregorianCalendar();
        long diff = endDate.getTime() - startDate.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        System.err.println("TESTING: diffDays " + diffDays);
        Date newDay = new Date();
        for (int i = 0; i < diffDays; i++) {
            cal.setTime(startDate);
            cal.add(Calendar.DATE, i);
            newDay = cal.getTime();
            dateList.add(newDay);
        }
    }

    public void initGCMap(Date sd, Date nd) {
//        Calendar cal = new GregorianCalendar();
//        long diff = endDate.getTime() - startDate.getTime();
//        long diffDays = diff / (24 * 60 * 60 * 1000);
//        System.err.println("TESTING: diffDays " + diffDays);
//        Date newDay = new Date();

        GroundStaffTeam gst1 = csb.getGroundStaffTeamById(1);
        GroundStaffTeam gst2 = csb.getGroundStaffTeamById(2);
        GroundStaffTeam gst3 = csb.getGroundStaffTeamById(3);
        GroundStaffTeam gst4 = csb.getGroundStaffTeamById(4);

        for (Date newDay : dateList) {

//        for (int i = 0; i < diffDays; i++) {
//            cal.setTime(startDate);
//            cal.add(Calendar.DATE, i);
//            newDay = cal.getTime();
            if (csb.findRotOnDate(gst1, newDay).getPeriod().equals("morning")) {
                morningMap.put(newDay, 1);
            }
            if (csb.findRotOnDate(gst2, newDay).getPeriod().equals("morning")) {
                morningMap.put(newDay, 2);
            }
            if (csb.findRotOnDate(gst3, newDay).getPeriod().equals("morning")) {
                morningMap.put(newDay, 3);
            }
            if (csb.findRotOnDate(gst4, newDay).getPeriod().equals("morning")) {
                morningMap.put(newDay, 4);
            }

            if (csb.findRotOnDate(gst1, newDay).getPeriod().equals("afternoon")) {
                afternoonMap.put(newDay, 1);
            }
            if (csb.findRotOnDate(gst2, newDay).getPeriod().equals("afternoon")) {
                afternoonMap.put(newDay, 2);
            }
            if (csb.findRotOnDate(gst3, newDay).getPeriod().equals("afternoon")) {
                afternoonMap.put(newDay, 3);
            }
            if (csb.findRotOnDate(gst4, newDay).getPeriod().equals("afternoon")) {
                afternoonMap.put(newDay, 4);
            }

            if (csb.findRotOnDate(gst1, newDay).getPeriod().equals("night")) {
                nightMap.put(newDay, 1);
            }
            if (csb.findRotOnDate(gst2, newDay).getPeriod().equals("night")) {
                nightMap.put(newDay, 2);
            }
            if (csb.findRotOnDate(gst3, newDay).getPeriod().equals("night")) {
                nightMap.put(newDay, 3);
            }
            if (csb.findRotOnDate(gst4, newDay).getPeriod().equals("night")) {
                nightMap.put(newDay, 4);
            }

            if (csb.findRotOnDate(gst1, newDay).getPeriod().equals("standby")) {
                standbyMap.put(newDay, 1);
            }
            if (csb.findRotOnDate(gst2, newDay).getPeriod().equals("standby")) {
                standbyMap.put(newDay, 2);
            }
            if (csb.findRotOnDate(gst3, newDay).getPeriod().equals("standby")) {
                standbyMap.put(newDay, 3);
            }
            if (csb.findRotOnDate(gst4, newDay).getPeriod().equals("standby")) {
                standbyMap.put(newDay, 4);
            }

        }
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

    public List<Date> getDateList() {
        return dateList;
    }

    public void setDateList(List<Date> dateList) {
        this.dateList = dateList;
    }

    public Map<Date, Integer> getMorningMap() {
        return morningMap;
    }

    public void setMorningMap(Map<Date, Integer> morningMap) {
        this.morningMap = morningMap;
    }

    public Map<Date, Integer> getAfternoonMap() {
        return afternoonMap;
    }

    public void setAfternoonMap(Map<Date, Integer> afternoonMap) {
        this.afternoonMap = afternoonMap;
    }

    public Map<Date, Integer> getNightMap() {
        return nightMap;
    }

    public void setNightMap(Map<Date, Integer> nightMap) {
        this.nightMap = nightMap;
    }

    public Map<Date, Integer> getStandbyMap() {
        return standbyMap;
    }

    public void setStandbyMap(Map<Date, Integer> standbyMap) {
        this.standbyMap = standbyMap;
    }

    public List<Date> getFilteredDateList() {
        return filteredDateList;
    }

    public void setFilteredDateList(List<Date> filteredDateList) {
        this.filteredDateList = filteredDateList;
    }

    public List<Integer> getGroupNo() {
        return groupNo;
    }

    public void setGroupNo(List<Integer> groupNo) {
        this.groupNo = groupNo;
    }

}
