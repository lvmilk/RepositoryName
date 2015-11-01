/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.Aircraft;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "VMSAMB")
@ViewScoped
public class ViewMaintenanceSelectAcManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    List<Aircraft> acList = new ArrayList<>();

    public ViewMaintenanceSelectAcManagedBean() {
    }

    @PostConstruct
    public void init() {
        List<Aircraft> acList0 = fpb.getAllAircraft();
        for (Aircraft a : acList0) {
            if (!a.getRegistrationNo().equals("9V-000")) {
                acList.add(a);
            }
        }
    }

    public void viewMaintenanceList(Aircraft ac) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewAcMt", ac);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceList.xhtml");
    }

    public List<Aircraft> getAcList() {
        return acList;
    }

    public void setAcList(List<Aircraft> acList) {
        this.acList = acList;
    }

}
