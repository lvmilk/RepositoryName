/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import SessionBean.CommonInfra.ManageAccountBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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
    
    @EJB
    private ManageAccountBeanLocal mab;
    
    private String stfType;
    private String username;
private String name;
    
    @PostConstruct
    public void init()
    {
        setStfType((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType"));
        setUsername((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
        if(FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType")!=null &&FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username")!=null){
       name = mab.getStaffName(username, stfType);
        }
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    
}
