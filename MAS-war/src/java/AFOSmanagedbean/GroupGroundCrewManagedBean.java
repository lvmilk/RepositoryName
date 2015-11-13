/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.GroundStaffTeam;
import Entity.CommonInfa.GroundStaff;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "GGCMB")
@ViewScoped
public class GroupGroundCrewManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    List<GroundStaff> unGroupedGS = new ArrayList<>();
    boolean gsAllGrouped;

    public GroupGroundCrewManagedBean() {
    }

    @PostConstruct
    public void init() {
        unGroupedGS = csb.getUngroupedGroundStaff();
        gsAllGrouped = unGroupedGS.isEmpty();
    }

    public void groupExistingGC() throws Exception {
        csb.groupGroundCrew();
//        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ground crew are successfully assigned to groups!", ""));
        FacesContext.getCurrentInstance().getExternalContext().redirect("./generateGroundCrewGroupSuccess.xhtml");
    }

    public void viewGroundCrewGroup() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewGroundCrewGroup.xhtml");

    }

    public List<GroundStaff> getUnGroupedGS() {
        return unGroupedGS;
    }

    public void setUnGroupedGS(List<GroundStaff> unGroupedGS) {
        this.unGroupedGS = unGroupedGS;
    }

    public boolean isGsAllGrouped() {
        return gsAllGrouped;
    }

    public void setGsAllGrouped(boolean gsAllGrouped) {
        this.gsAllGrouped = gsAllGrouped;
    }

}
