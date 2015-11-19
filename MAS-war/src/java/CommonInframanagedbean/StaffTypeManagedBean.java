/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

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
    private String username;
    private String  firstName;
    private String lastName;
    
    @PostConstruct
    public void init()
    {
        setStfType((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType"));
        setUsername((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("username"));
        setFirstName((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName"));
        setLastName((String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastName"));
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
}
