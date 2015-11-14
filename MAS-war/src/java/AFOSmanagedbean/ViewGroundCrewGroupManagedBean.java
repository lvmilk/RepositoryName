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
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VGCGMB")
@ViewScoped
public class ViewGroundCrewGroupManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    List<GroundStaff> gst1;
    List<GroundStaff> gst2;
    List<GroundStaff> gst3;
    List<GroundStaff> gst4;
    List<GroundStaff> gst;

    public ViewGroundCrewGroupManagedBean() {
    }

    @PostConstruct
    public void init() {
        gst1 = csb.getGroundStaffTeam(1);
        gst2 = csb.getGroundStaffTeam(2);
        gst3 = csb.getGroundStaffTeam(3);
        gst4 = csb.getGroundStaffTeam(4);
        gst = csb.getAllGroundStaff();
    }

//    public void viewGroundCrewGroup() throws IOException {
//        
//    }
    
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

    public List<GroundStaff> getGst() {
        return gst;
    }

    public void setGst(List<GroundStaff> gst) {
        this.gst = gst;
    }
    
    
}
