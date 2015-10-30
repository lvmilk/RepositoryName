/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.Maintenance;
import Entity.AFOS.MaintenanceLog;
import Entity.APS.Aircraft;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "MMB")
@ViewScoped
public class MaintenanceManagedBean implements Serializable {

    Maintenance mt;
    MaintenanceLog log;

    public MaintenanceManagedBean() {
    }

    public void viewMaintenanceList(Aircraft ac) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewAcMt", ac);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceList.xhtml");
    }

    public void viewMaintenanceLog() {
        mt = (Maintenance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("addMtLog");

    }

}
