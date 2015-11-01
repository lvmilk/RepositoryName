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
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VMTLMB")
@ViewScoped
public class ViewMaintenanceLogManagedBean implements Serializable {

    @EJB
    MaintenanceSchedulingBeanLocal msb;

    private Maintenance mt;
    private Aircraft ac;
    private Date actStart;
    private Date actEnd;
    private String startTime;
    private String endTime;
    private String obj;
    private String activity;
    private String remark;
    private String mtCrew;
    private Integer manhour;

    public ViewMaintenanceLogManagedBean() {
    }

    @PostConstruct
    public void init() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        mt = (Maintenance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("viewMtLog");
        ac = mt.getAircraft();
        obj = mt.getObjective();
        activity = mt.getLog().getActivity();
        actStart = mt.getLog().getStartTime();
        startTime = df.format(actStart);
        actEnd = mt.getLog().getEndTime();
        endTime = df.format(actEnd);
        manhour = mt.getLog().getManhour();
        mtCrew = mt.getLog().getMtCrew();
        remark = mt.getLog().getRemark();
    }

    public void viewMaintenanceLogBack() throws IOException {
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

}
