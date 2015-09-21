/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value="ctl")
@ViewScoped
public class Control implements Serializable{

    private Boolean visibility = true;
    private Boolean visiCockpit;
    private String stfType;

    public void valueChanged(ValueChangeEvent event) {
        Object newValue = event.getNewValue();
        System.out.println(newValue);
        if (newValue.equals("cockpit")) {
            this.setVisiCockpit(true);
            this.setVisibility(false);
        } else {
            this.setVisibility(true);
            this.setVisiCockpit(false);
        }
        
    }
    /**
     * @return the type
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param type the type to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
    }
    /**
     * @return the visibility
     */
    public Boolean getVisibility() {
        return visibility;
    }

    /**
     * @param visibility the visibility to set
     */
    public void setVisibility(Boolean visibility) {
        this.visibility = visibility;
    }

    /**
     * @return the visiCockpit
     */
    public Boolean getVisiCockpit() {
        return visiCockpit;
    }

    /**
     * @param visiCockpit the visiCockpit to set
     */
    public void setVisiCockpit(Boolean visiCockpit) {
        this.visiCockpit = visiCockpit;
    }

}
