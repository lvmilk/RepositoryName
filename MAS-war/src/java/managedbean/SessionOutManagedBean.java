/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;


/**
 *
 * @author LI HAO
 */
@ManagedBean(name = "sessionOutBean")
public class SessionOutManagedBean {

    public void sessionOutListener() 
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Session TimeOut", "Your session has timed out. Please login again."));
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().clear();

        try {
            String url = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/login.xhtml");
        } 
        catch(IOException ex)  
        {
            Logger.getLogger(SessionOutManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void backListener()
    {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Continue","Continue working"));
    }
}
