/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import SessionBean.CommonInfa.ManageAccountBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "cPartnerMB")
@ViewScoped
public class CreatePartnerManagedBean implements Serializable {

    @EJB
    private ManageAccountBeanLocal mal;
    private String partnerID;
    private String password;
    private String stfType="agency";
    private String email;
    private String cmsLvl;
    
    private Boolean visiAg = true;
    private Boolean visiAl;

    public void valueChanged(ValueChangeEvent event) {
        Object newValue = event.getNewValue();
        System.out.println(newValue);
        if (newValue.equals("alliance")) {
            this.setVisiAl(true);
            this.setVisiAg(false);
        } else {
            
            this.setVisiAg(true);
            this.setVisiAl(false);
        }

    }

    public void createPartnerAcc() {
        boolean blCreateParID, blCreateParEmail;

        blCreateParID = mal.checkPartenrIDDuplicate(partnerID, stfType);
        blCreateParEmail = mal.checkPartnerEmailDuplicate(email);

        System.out.println("!!! Create Partner email: " + blCreateParEmail);
        if (!blCreateParID && !blCreateParEmail) {
            System.out.println(partnerID);
            System.out.println(password);
            System.out.println(email);
            System.out.println(stfType);
            System.out.println("We are in create partner account managed bean");
            mal.addPartnerAcc(partnerID, password, email, stfType);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Created Successfully"));

        } else if (!blCreateParEmail && blCreateParID) {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
        } else if (!blCreateParID && blCreateParEmail) {
            System.out.println("Email exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Email exists"));
        } else {
            System.out.println("Email & Account exist");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Email & Account exist"));
        }
    }

    /**
     * @return the partnerAcc
     */
    public String getPartnerID() {
        return partnerID;
    }

    /**
     * @param partnerAcc the partnerAcc to set
     */
    public void setPartnerID(String partnerID) {
        this.partnerID = partnerID;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
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

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the cmsLvl
     */
    public String getCmsLvl() {
        return cmsLvl;
    }

    /**
     * @param cmsLvl the cmsLvl to set
     */
    public void setCmsLvl(String cmsLvl) {
        this.cmsLvl = cmsLvl;
    }

    /**
     * @return the visiAg
     */
    public Boolean getVisiAg() {
        return visiAg;
    }

    /**
     * @param visiAg the visiAg to set
     */
    public void setVisiAg(Boolean visiAg) {
        this.visiAg = visiAg;
    }

    /**
     * @return the visiAl
     */
    public Boolean getVisiAl() {
        return visiAl;
    }

    /**
     * @param visiAl the visiAl to set
     */
    public void setVisiAl(Boolean visiAl) {
        this.visiAl = visiAl;
    }
}
