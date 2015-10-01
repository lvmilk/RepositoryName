/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import Entity.CommonInfaEntity.*;
import SessionBean.CommonInfaSB.manageAccountLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "edProfile")
@ViewScoped
public class EditProfileManagedBean implements Serializable {

    @EJB
    private manageAccountLocal mal;

    private String username;
    private String stfType;
    private String password;
    private String pswEdited;
    private String email;
    private String emailEdited;

    private String licence;

    OfficeStaff offStaff;
    GroundStaff grdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;

    @PostConstruct
    public void setProfile() {
        username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");
        stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");

        System.out.println(username);
        System.out.println(stfType);

        if (stfType.equals("officeStaff")) {
            offStaff = mal.getOfficeStaff(username);
            this.setUsername(offStaff.getOffName());
            this.setPassword(offStaff.getOffPassword());
            this.setEmail(offStaff.getEmail());
            this.setStfType(offStaff.getStfType());

        } else if (stfType.equals("groundStaff")) {
            grdStaff=mal.getGroundStaff(username);
            this.setUsername(grdStaff.getGrdName());
            this.setPassword(grdStaff.getGrdPassword());
            this.setEmail(grdStaff.getEmail());
            this.setStfType(grdStaff.getStfType());
            
        } else if (stfType.equals("cabin")) {
            cbCrew=mal.getCabinCrew(username);
            this.setUsername(cbCrew.getCbName());
            this.setPassword(cbCrew.getCbPassword());
            this.setEmail(cbCrew.getEmail());
            this.setStfType(cbCrew.getStfType());
        } else if (stfType.equals("cockpit")) {
            cpCrew=mal.getCockpitCrew(username);
            this.setUsername(cpCrew.getCpName());
            this.setPassword(cpCrew.getCpPassword());
            this.setEmail(cpCrew.getEmail());
            this.setStfType(cpCrew.getStfType());
        }

        this.setEmailEdited(email);
//        this.setPswEdited(password);
    }

    public void editStaffAcc() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editProfile.xhtml");
    }

    public void editOtrStaffAcc() throws IOException {

        if (!mal.checkEmailDuplicate(email, emailEdited)) {
            mal.editProfile(username, stfType, pswEdited, emailEdited);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
        }

    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return the pswEdited
     */
    public String getPswEdited() {
        return pswEdited;
    }

    /**
     * @param pswEdited the pswEdited to set
     */
    public void setPswEdited(String pswEdited) {
        this.pswEdited = pswEdited;
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
     * @return the emailEdited
     */
    public String getEmailEdited() {
        return emailEdited;
    }

    /**
     * @param emailEdited the emailEdited to set
     */
    public void setEmailEdited(String emailEdited) {
        this.emailEdited = emailEdited;
    }

    /**
     * @return the licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * @param licence the licence to set
     */
    public void setLicence(String licence) {
        this.licence = licence;
    }
}
