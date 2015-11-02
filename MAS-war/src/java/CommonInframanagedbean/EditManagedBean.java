/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import Entity.CommonInfa.OfficeStaff;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.CabinCrew;
import SessionBean.CommonInfra.ManageAccountBeanLocal;
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
import CommonInframanagedbean.Control;
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
    private ManageAccountBeanLocal mal;

    private String username;
    private String username_origin;
    private String usernameEdited;
    private String stfType;
    private String password;
    private String pswEdited;
    private String email;
    private String emailEdited;

    private String licence;
    private Integer attempt;
    private Integer locked;

    private String name;
    private String stfLevel;
    private Double salary;
    private Double hourPay;

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
    public void SelectEditOfficeStaff(OfficeStaff officeStaff) throws IOException {
        setUsername(officeStaff.getOffName());
//        setUsernameEdited(officeStaff.getOffName());
        setEmail(officeStaff.getEmail());
        setEmailEdited(officeStaff.getEmail());
        setStfType(officeStaff.getStfType());
        setPassword(officeStaff.getOffPassword());
        setPswEdited(officeStaff.getOffPassword());
        setName(officeStaff.getName());
        setStfLevel(officeStaff.getStfLevel());
        setSalary(officeStaff.getSalary());
        setHourPay(officeStaff.getHourPay());
        setAttempt(officeStaff.getAttempt());
        setLocked(officeStaff.getLocked());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditStaffPage.xhtml");

    }

    public void SelectedEditGroundStaff(GroundStaff grdStaff) throws IOException {
        setUsername(grdStaff.getGrdName());
        setEmail(grdStaff.getEmail());
        setEmailEdited(grdStaff.getEmail());
        setStfType(grdStaff.getStfType());
        setPassword(grdStaff.getGrdPassword());
        setPswEdited(grdStaff.getGrdPassword());
        setName(grdStaff.getName());
        setStfLevel(grdStaff.getStfLevel());
        setSalary(grdStaff.getSalary());
        setHourPay(grdStaff.getHourPay());
        setAttempt(grdStaff.getAttempt());
        setLocked(grdStaff.getLocked());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditStaffPage.xhtml");
    }

    public void SelectedEditCabinCrew(CabinCrew cbCrew) throws IOException {
        setUsername(cbCrew.getCbName());
        setEmail(cbCrew.getEmail());
        setEmailEdited(cbCrew.getEmail());
        setStfType(cbCrew.getStfType());
        setPassword(cbCrew.getCbPassword());
        setPswEdited(cbCrew.getCbPassword());
        setName(cbCrew.getName());
        setStfLevel(cbCrew.getStfLevel());
        setSalary(cbCrew.getSalary());
        setHourPay(cbCrew.getHourPay());
        setAttempt(cbCrew.getAttempt());
        setLocked(cbCrew.getLocked());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditStaffPage.xhtml");
    }

    public void SelectedEditCockpitCrew(CockpitCrew cpCrew) throws IOException {
        setUsername(cpCrew.getCpName());
        setEmail(cpCrew.getEmail());
        setEmailEdited(cpCrew.getEmail());
        setStfType(cpCrew.getStfType());
        setPassword(cpCrew.getCpPassword());
        setPswEdited(cpCrew.getCpPassword());
        setName(cpCrew.getName());
        setStfLevel(cpCrew.getStfLevel());
        setLicence(cpCrew.getLicence());
        setSalary(cpCrew.getSalary());
        setHourPay(cpCrew.getHourPay());
        setAttempt(cpCrew.getAttempt());
        setLocked(cpCrew.getLocked());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditCockpitPage.xhtml");
    }

    public void editOfStaffAcc() throws IOException {

        if (!mal.checkEmailDuplicate(email, emailEdited)) {
            mal.editStaff(username, stfType, password, pswEdited, email, emailEdited, name, stfLevel, salary, hourPay, attempt, locked);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
        }

    }

    public void editCpStaffAcc() throws IOException {
        if (!mal.checkEmailDuplicate(email, emailEdited)) {
            mal.editCpCrew(username, stfType, password, pswEdited, email, emailEdited, name, stfLevel, salary, hourPay, licence, attempt, locked);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
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
     * @return the attempt
     */
    public Integer getAttempt() {
        return attempt;
    }

    /**
     * @param attempt the attempt to set
     */
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    /**
     * @return the locked
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the stfLevel
     */
    public String getStfLevel() {
        return stfLevel;
    }

    /**
     * @param stfLevel the stfLevel to set
     */
    public void setStfLevel(String stfLevel) {
        this.stfLevel = stfLevel;
    }

    /**
     * @return the salary
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(Double salary) {
        this.salary = salary;
    }

    /**
     * @return the hourPay
     */
    public Double getHourPay() {
        return hourPay;
    }

    /**
     * @param hourPay the hourPay to set
     */
    public void setHourPay(Double hourPay) {
        this.hourPay = hourPay;
    }

}
