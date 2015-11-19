/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.Rotation;
import Entity.CommonInfa.GroundStaff;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "PSGSMB")
@ViewScoped
public class PersonalScheduleGroundStaffManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    private String username;
    private List<Rotation> rtList = new ArrayList<>();
    private List<Rotation> viewRt = new ArrayList<>();
    private GroundStaff grd;

    public PersonalScheduleGroundStaffManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username") != null) {
            username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username");
            grd = csb.findGSById(username);
            rtList = grd.getGroundStaffTeam().getRotationList();
            Date today = new Date();
            for (Rotation r : rtList) {
                if (!r.getWorkDate().before(today)) {
                    viewRt.add(r);
                }
            }
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Rotation> getRtList() {
        return rtList;
    }

    public void setRtList(List<Rotation> rtList) {
        this.rtList = rtList;
    }

    public List<Rotation> getViewRt() {
        return viewRt;
    }

    public void setViewRt(List<Rotation> viewRt) {
        this.viewRt = viewRt;
    }

    public GroundStaff getGrd() {
        return grd;
    }

    public void setGrd(GroundStaff grd) {
        this.grd = grd;
    }

    
    
}
