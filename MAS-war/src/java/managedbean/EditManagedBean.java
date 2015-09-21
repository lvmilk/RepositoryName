/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import SessionBean.CommonInfaSB.manageAccountLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import managedbean.Control;
import Entity.CommonInfaEntity.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author LI HAO
 */
@Named(value = "etManagedBean")
@SessionScoped
public class EditManagedBean implements Serializable {

    @EJB
    private manageAccountLocal mal;

    private String username;
    private String username_origin;
    private String usernameEdited;
    private String stfType;
    private String password;
    private String email;
    private String emailEdited;

    private String licence;

    private List<OfficeStaff> selectedOffStf;
    private List<OfficeStaff> offStfList;
    private List<GroundStaff> grdStfList;
    private List<CabinCrew> cbCrewList;
    private List<CockpitCrew> cpCrewList;
    
    

    public EditManagedBean() {
        selectedOffStf = new ArrayList();
    }

    /**
     * @return the username
     */
    public void SelectEditOfficeStaff(OfficeStaff officeStaff) throws IOException{
        setUsername(officeStaff.getOffName()); 
        setUsernameEdited(officeStaff.getOffName());
        setEmail(officeStaff.getEmail());
        setEmailEdited(officeStaff.getEmail());
        setStfType(officeStaff.getStfType());
        setPassword(officeStaff.getOffPassword());
       FacesContext.getCurrentInstance().getExternalContext().redirect("./EditOfStaffPage.xhtml");
    
    }
    
    public void editOfStaffAcc() throws IOException {
        
        if (!mal.checkNameDuplicate(username, usernameEdited)){

            if (!mal.checkEmailDuplicate(email, emailEdited)){
                mal.editOfficeStaff(usernameEdited, stfType, password, emailEdited);
                 FacesContext.getCurrentInstance().getExternalContext().redirect("./EditStaffSuccess.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username has already been used ", ""));
        }

    }

 
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername_origin() {
        return username_origin;
    }

    public void setUsername_origin(String username_origin) {
        this.username_origin = username_origin;
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

    public List<OfficeStaff> getOffStfList() {
        offStfList = mal.getAllOfficeStaff();
        System.out.println("OfficeStaff List size is " + offStfList.size());
        for (Integer i = 0; i < offStfList.size(); i++) {
            System.out.println(offStfList.get(i).getOffName());
        }

        return offStfList;
    }

    /**
     * @param offStfList the offStfList to set
     */
    public void setOffStfList(List<OfficeStaff> offStfList) {
        this.offStfList = offStfList;
    }

    /**
     * @return the grdStfList
     */
    public List<GroundStaff> getGrdStfList() {
        grdStfList = mal.getAllGoundStaff();
        System.out.println("GroundStaff List size is " + offStfList.size());
        return grdStfList;
    }

    /**
     * @param grdStfList the grdStfList to set
     */
    public void setGrdStfList(List<GroundStaff> grdStfList) {
        this.grdStfList = grdStfList;
    }

    /**
     * @return the cbCrewList
     */
    public List<CabinCrew> getCbCrewList() {
        cbCrewList = mal.getAllCabinCrew();
        System.out.println("CabinCrew List size is " + cbCrewList.size());
        return cbCrewList;
    }

    /**
     * @param cbCrewList the cbCrewList to set
     */
    public void setCbCrewList(List<CabinCrew> cbCrewList) {
        this.cbCrewList = cbCrewList;
    }

    /**
     * @return the cpCrewList
     */
    public List<CockpitCrew> getCpCrewList() {
        cpCrewList = mal.getAllCockpitCrew();
        System.out.println("CockpitCrew List size is " + offStfList.size());
        return cpCrewList;
    }

    /**
     * @param cpCrewList the cpCrewList to set
     */
    public void setCpCrewList(List<CockpitCrew> cpCrewList) {
        this.cpCrewList = cpCrewList;
    }

    public List<OfficeStaff> getSelectedOffStf() {
        return selectedOffStf;
    }

    public void setSelectedOffStf(List<OfficeStaff> selectedOffStf) {
        this.selectedOffStf = selectedOffStf;
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

    public String getUsernameEdited() {
        return usernameEdited;
    }

    public void setUsernameEdited(String usernameEdited) {
        this.usernameEdited = usernameEdited;
    }

    public String getEmailEdited() {
        return emailEdited;
    }

    public void setEmailEdited(String emailEdited) {
        this.emailEdited = emailEdited;
    }
    
    

}
