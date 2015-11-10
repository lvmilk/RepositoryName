/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import java.io.IOException;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author LI HAO
 */
@Named(value = "wsMB")
@ViewScoped
public class WorkspaceManagedBean implements Serializable {

    public void direction() throws IOException {
        String stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");
        if (stfType.equals("admin")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/sAdmWorkspace.xhtml");
        } else if (stfType.equals("groundStaff")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/CMIpages/grdStaffWorkspace.xhtml");
        } else if (stfType.equals("cabin")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/CMIpages/cbCrewWorkspace.xhtml");
        } else if (stfType.equals("cockpit")) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/CMIpages/cpCrewWorkspace.xhtml");
        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/staffWorkspace.xhtml");
        }
    }
}
