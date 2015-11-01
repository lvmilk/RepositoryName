/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.Maintenance;
import Entity.APS.Aircraft;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VMLMB")
@ViewScoped
public class ViewMaintenanceListManagedBean implements Serializable {

    Aircraft viewAcMt;
    List<Maintenance> viewMt;

    public ViewMaintenanceListManagedBean() {
    }

    @PostConstruct
    public void init() {
        viewAcMt = (Aircraft) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("viewAcMt");
        viewMt = viewAcMt.getMaintenanceList();
    }

    public void addMtLog(Maintenance mt) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("addMtLog", mt);
        System.out.println("VMLMB *************** MT ************* " + mt);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./addMaintenanceLog.xhtml");
    }

    public void editMtLog(Maintenance mt) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("editMtLog", mt);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editMaintenanceLog.xhtml");
    }

    public void viewMtLog(Maintenance mt) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewMtLog", mt);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceLog.xhtml");
    }

    public Aircraft getViewAcMt() {
        return viewAcMt;
    }

    public void setViewAcMt(Aircraft viewAcMt) {
        this.viewAcMt = viewAcMt;
    }

    public List<Maintenance> getViewMt() {
        return viewMt;
    }

    public void setViewMt(List<Maintenance> viewMt) {
        this.viewMt = viewMt;
    }

}
