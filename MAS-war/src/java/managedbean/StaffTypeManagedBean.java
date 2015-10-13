/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */

@Named(value = "stfTypeMB")
@ViewScoped
public class StaffTypeManagedBean implements Serializable{
    
    private String stfType;
    
    
    @PostConstruct
    public void ini()
    {
        setStfType((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType"));
    }

    /**
     * @return the stfType
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param stfType the stfType to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
    }
    
}
