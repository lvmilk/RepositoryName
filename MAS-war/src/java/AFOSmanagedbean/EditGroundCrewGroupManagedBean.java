/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.CommonInfa.GroundStaff;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
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
@Named(value = "EGCGMB")
@ViewScoped
public class EditGroundCrewGroupManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    List<GroundStaff> gst1;
    List<GroundStaff> gst2;
    List<GroundStaff> gst3;
    List<GroundStaff> gst4;

    List<GroundStaff> unGroupedGS;
    GroundStaff addgs;
    String addgsId;
    GroundStaff gcToRemove;
    Integer teamId;

    public EditGroundCrewGroupManagedBean() {
    }

    @PostConstruct
    public void init() {
        gst1 = csb.getGroundStaffTeam(1);
        gst2 = csb.getGroundStaffTeam(2);
        gst3 = csb.getGroundStaffTeam(3);
        gst4 = csb.getGroundStaffTeam(4);
        unGroupedGS = csb.getUngroupedGroundStaff();
    }

    public void refresh() throws IOException {
        System.out.println("REFRESH!");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editGroundCrewGroup.xhtml");
    }

    public void setCrewInfo() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String gcToRemoveId = params.get("gcToRemoveId");
        gcToRemove = csb.findGSById(gcToRemoveId);
        System.out.println("Call this method? ");
        System.out.println("Call this method? gcToRemoveId = " + gcToRemoveId);
        System.out.println("Call this method? gcToRemove = " + gcToRemove);
    }

    public void removeCrewFromGroup(Integer teamId) {
//        gcToRemove = (GroundStaff) event.getComponent().getAttributes().get("gcToRemove");
//        teamId = (Integer) event.getComponent().getAttributes().get("teamId");
        System.out.println("gcToRemove " + gcToRemove);
        System.out.println("teamId " + teamId);
        System.out.println("teamId type " + teamId.getClass().getName());

        csb.deleteGSFromGroup(gcToRemove, teamId);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Remove from group success", "Ground crew " + gcToRemove.getGrdName() + " is successfully removed from group " + teamId));
    }

    public void addCrewToGroup(GroundStaff gs, Integer teamId) {
        csb.addGSToGroup(addgs, teamId);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add to group success", "Ground crew " + gs.getGrdName() + " is successfully added to group " + teamId));
    }

    public void addCrewToGroup(Integer teamId) {
        addgs = csb.findGSById(addgsId);
        csb.addGSToGroup(addgs, teamId);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Add to group success", "Ground crew " + addgs.getGrdName() + " is successfully added to group " + teamId));
    }

    public GroundStaff getGcToRemove() {
        return gcToRemove;
    }

    public void setGcToRemove(GroundStaff gcToRemove) {
        this.gcToRemove = gcToRemove;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public List<GroundStaff> getGst1() {
        return gst1;
    }

    public void setGst1(List<GroundStaff> gst1) {
        this.gst1 = gst1;
    }

    public List<GroundStaff> getGst2() {
        return gst2;
    }

    public void setGst2(List<GroundStaff> gst2) {
        this.gst2 = gst2;
    }

    public List<GroundStaff> getGst3() {
        return gst3;
    }

    public void setGst3(List<GroundStaff> gst3) {
        this.gst3 = gst3;
    }

    public List<GroundStaff> getGst4() {
        return gst4;
    }

    public void setGst4(List<GroundStaff> gst4) {
        this.gst4 = gst4;
    }

    public List<GroundStaff> getUnGroupedGS() {
        return unGroupedGS;
    }

    public void setUnGroupedGS(List<GroundStaff> unGroupedGS) {
        this.unGroupedGS = unGroupedGS;
    }

    public GroundStaff getAddgs() {
        return addgs;
    }

    public void setAddgs(GroundStaff addgs) {
        this.addgs = addgs;
    }

    public String getAddgsId() {
        return addgsId;
    }

    public void setAddgsId(String addgsId) {
        this.addgsId = addgsId;
    }

}
