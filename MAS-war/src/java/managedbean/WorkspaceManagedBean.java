/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

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
        
    public void direction() throws IOException
    {
        String stfName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
        if (stfName.equals("admin"))
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/sAdmWorkspace.xhtml");
        }else{
            FacesContext.getCurrentInstance().getExternalContext().redirect("/MAS-war/staffWorkspace.xhtml");
        }
    }
}
