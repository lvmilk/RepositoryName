/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import SessionBean.AFOS.MaintenanceSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "EMLMB")
@ViewScoped
public class EditMaintenanceLogManagedBean implements Serializable {

    @EJB
    MaintenanceSchedulingBeanLocal msb;

    private Maintenance mt;
    private Aircraft ac;
    private Date schStart;
    private Date schEnd;
    private Date actStart;
    private Date actEnd;
    private String startTime;
    private String endTime;
    private String obj;
    private String activity;
    private String remark;
    private String mtCrew;
    private Integer manhour;
    private boolean checkSign;
    private Integer mtdu;

    public EditMaintenanceLogManagedBean() {
    }

    @PostConstruct
    public void init() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mt = (Maintenance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("editMtLog");
        ac = mt.getAircraft();
        obj = mt.getObjective();
        activity = mt.getLog().getActivity();
//        schStart = mt.getStartTime();
//        schEnd = mt.getEndTime();
        actStart = mt.getLog().getStartTime();
        System.out.println("***************************** MT START TIME ******************" + actStart);
        startTime = df.format(actStart);
        System.out.println("***************************** MT ST TIME ******************" + startTime);
        actEnd = mt.getLog().getEndTime();
        System.out.println("***************************** MT END TIME ******************" + actEnd);
        endTime = df.format(actEnd);
        System.out.println("***************************** MT ED TIME ******************" + endTime);
        manhour = mt.getLog().getManhour();
        mtCrew = mt.getLog().getMtCrew();
        remark = mt.getLog().getRemark();
        checkSign = true;
    }

    public void editMaintenanceLog() throws IOException {
//        long startDiffHour = Math.abs(actStart.getTime() - schStart.getTime()) / (60 * 60 * 1000);
//        long endDiffHour = Math.abs(actEnd.getTime() - schEnd.getTime()) / (60 * 60 * 1000);
        switch (obj.charAt(0)) {
            case 'A':
                mtdu = ac.getAircraftType().getAcMH();
                break;
            case 'B':
                mtdu = ac.getAircraftType().getBcMH();
                break;
            case 'C':
                mtdu = ac.getAircraftType().getCcMH();
                break;
            case 'D':
                mtdu = ac.getAircraftType().getDcMH();
                break;
            default:
                mtdu = 0;
                break;
        }
        if (actEnd.before(actStart)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error: Maintenance end time should be after start time.", ""));
        } else if (mtdu != 0 && (manhour > mtdu * 1.3 || manhour < mtdu * 0.7)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please check total man hour. Expected manhour is " + mtdu, ""));
        } else if (!checkSign) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please sign to agree the maintenance standard.", ""));
        } else {
            msb.editMaintenanceLog(mt, ac.getRegistrationNo(), ac.getAircraftType().getType(), obj, actStart, actEnd, manhour, activity, remark, mtCrew);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("mt", mt);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editMaintenanceLogSuccess.xhtml");
        }
    }

    public void editMaintenanceLogBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceList.xhtml");
    }

    public Maintenance getMt() {
        return mt;
    }

    public void setMt(Maintenance mt) {
        this.mt = mt;
    }

    public Aircraft getAc() {
        return ac;
    }

    public void setAc(Aircraft ac) {
        this.ac = ac;
    }

    public Date getSchStart() {
        return schStart;
    }

    public void setSchStart(Date schStart) {
        this.schStart = schStart;
    }

    public Date getSchEnd() {
        return schEnd;
    }

    public void setSchEnd(Date schEnd) {
        this.schEnd = schEnd;
    }

    public Date getActStart() {
        return actStart;
    }

    public void setActStart(Date actStart) {
        this.actStart = actStart;
    }

    public Date getActEnd() {
        return actEnd;
    }

    public void setActEnd(Date actEnd) {
        this.actEnd = actEnd;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getMtCrew() {
        return mtCrew;
    }

    public void setMtCrew(String mtCrew) {
        this.mtCrew = mtCrew;
    }

    public Integer getManhour() {
        return manhour;
    }

    public void setManhour(Integer manhour) {
        this.manhour = manhour;
    }

    public boolean isCheckSign() {
        return checkSign;
    }

    public void setCheckSign(boolean checkSign) {
        this.checkSign = checkSign;
    }

    public Integer getMtdu() {
        return mtdu;
    }

    public void setMtdu(Integer mtdu) {
        this.mtdu = mtdu;
    }

}
