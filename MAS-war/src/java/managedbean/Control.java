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
    private Boolean visiCabin;
    private Boolean visiGround;
    private String stfType;
    
    private Boolean visiDelOff=true;
    private Boolean visiDelGrd;
    private Boolean visiDelCb;
    private Boolean visiDelCp;

    public void valueChanged(ValueChangeEvent event) {
        Object newValue = event.getNewValue();
        System.out.println(newValue);
        if (newValue.equals("cockpit")) {
            this.setVisiCockpit(true);
            this.setVisibility(false);
            this.setVisiCabin(false);
            this.setVisiGround(false);
        }else if (newValue.equals("cabin")){
            this.setVisiCockpit(false);
            this.setVisibility(false);
            this.setVisiCabin(true);
            this.setVisiGround(false);
        } else if (newValue.equals("groundStaff")){
            this.setVisiCockpit(false);
            this.setVisibility(false);
            this.setVisiCabin(false);
            this.setVisiGround(true);
        }else if(newValue.equals("officeStaff")) {
            this.setVisibility(true);
            this.setVisiCockpit(false);
            this.setVisiCabin(false);
            this.setVisiGround(false);
        }
        
    }
    
    public void valueChangedForDelete(ValueChangeEvent event)
    {
        Object newValue = event.getNewValue();
        System.out.println(newValue);
        if (newValue.equals("cockpit")) {
            this.setVisiDelCp(true);
            this.setVisiDelCb(false);
            this.setVisiDelGrd(false);
            this.setVisiDelOff(false);
        }
        else if (newValue.equals("cabin"))
        {
            this.setVisiDelCp(false);
            this.setVisiDelCb(true);
            this.setVisiDelGrd(false);
            this.setVisiDelOff(false);
        }
        else if(newValue.equals("officeStaff"))
        {
            this.setVisiDelCp(false);
            this.setVisiDelCb(false);
            this.setVisiDelGrd(false);
            this.setVisiDelOff(true);
        }
        else if(newValue.equals("groundStaff"))
        {
            this.setVisiDelCp(false);
            this.setVisiDelCb(false);
            this.setVisiDelGrd(true);
            this.setVisiDelOff(false);
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

    /**
     * @return the visiDelOff
     */
    public Boolean getVisiDelOff() {
        return visiDelOff;
    }

    /**
     * @param visiDelOff the visiDelOff to set
     */
    public void setVisiDelOff(Boolean visiDelOff) {
        this.visiDelOff = visiDelOff;
    }

    /**
     * @return the visiDelGrd
     */
    public Boolean getVisiDelGrd() {
        return visiDelGrd;
    }

    /**
     * @param visiDelGrd the visiDelGrd to set
     */
    public void setVisiDelGrd(Boolean visiDelGrd) {
        this.visiDelGrd = visiDelGrd;
    }

    /**
     * @return the visiDelCb
     */
    public Boolean getVisiDelCb() {
        return visiDelCb;
    }

    /**
     * @param visiDelCb the visiDelCb to set
     */
    public void setVisiDelCb(Boolean visiDelCb) {
        this.visiDelCb = visiDelCb;
    }

    /**
     * @return the visiDelCp
     */
    public Boolean getVisiDelCp() {
        return visiDelCp;
    }

    /**
     * @param visiDelCp the visiDelCp to set
     */
    public void setVisiDelCp(Boolean visiDelCp) {
        this.visiDelCp = visiDelCp;
    }

    /**
     * @return the visiCabin
     */
    public Boolean getVisiCabin() {
        return visiCabin;
    }

    /**
     * @param visiCabin the visiCabin to set
     */
    public void setVisiCabin(Boolean visiCabin) {
        this.visiCabin = visiCabin;
    }

    /**
     * @return the visiGround
     */
    public Boolean getVisiGround() {
        return visiGround;
    }

    /**
     * @param visiGround the visiGround to set
     */
    public void setVisiGround(Boolean visiGround) {
        this.visiGround = visiGround;
    }

}
