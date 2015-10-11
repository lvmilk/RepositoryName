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
import SessionBean.CommonInfaSB.UserLogSessionBeanLocal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.primefaces.context.RequestContext;

/**
 *
 * @author LI HAO
 */
@Named(value = "dlManagedBean")
@ViewScoped
public class DeletionManagedBean implements Serializable {

    @EJB
    private manageAccountLocal mal;


    private String username;
    private String stfType;
    private String password;
    private String email;

    private String licence;

    private List<OfficeStaff> selectedOffStf = new ArrayList();
    private List<OfficeStaff> offStfList;
    private List<GroundStaff> grdStfList;
    private List<CabinCrew> cbCrewList;
    private List<CockpitCrew> cpCrewList;

    private List<GroundStaff> selectedGrdList = new ArrayList();
    private List<CabinCrew> selectedCbCrewList = new ArrayList();
    private List<CockpitCrew> selectedCpCrewList = new ArrayList();
    
    


    public DeletionManagedBean() {

    }

    @PostConstruct
    public void init() {
        offStfList = mal.getAllOfficeStaff();
        grdStfList = mal.getAllGoundStaff();
        cbCrewList = mal.getAllCabinCrew();
        cpCrewList = mal.getAllCockpitCrew();

    }

    /**
     * @return the username
     */
    public void deleteAccount() throws IOException {

        boolean check = false;
        if (!selectedOffStf.isEmpty()) {
            System.out.println("hahahaha");
            System.out.println(selectedOffStf);
            check = mal.delAcc(selectedOffStf);
            System.out.println(check);
        } else if (!selectedGrdList.isEmpty()) {
            check = mal.delGrdAcc(selectedGrdList);
        } else if (!selectedCbCrewList.isEmpty()) {
            check = mal.delCabinAcc(selectedCbCrewList);
        } else if (!selectedCpCrewList.isEmpty()) {
            check = mal.delCockpitAcc(selectedCpCrewList);
        }

        if (check) {
            System.out.println("Delete Successful!");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Deleted Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Booking Class selected: ", ""));
        }

    }

    public void toDeleteAcc() {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedOffStf.isEmpty() && selectedGrdList.isEmpty() && selectedCbCrewList.isEmpty() && selectedCpCrewList.isEmpty()) {
            
            context.execute("alert('Please select staff(s) first.');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select staff(s) first. ", ""));
        } else {
            if (!selectedOffStf.isEmpty())
            {
                context.execute("PF('dlgOffstaff').show()");
            }else if (!selectedGrdList.isEmpty()) {
                context.execute("PF('dlgGrd').show()");
            }else if (!selectedCbCrewList.isEmpty()) {
                context.execute("PF('dlgCb').show()");
            }else if (!selectedCpCrewList.isEmpty()) {
                context.execute("PF('dlgCp').show()");
            }
            
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

    /**
     * @return the selectedGrdList
     */
    public List<GroundStaff> getSelectedGrdList() {
        return selectedGrdList;
    }

    /**
     * @param selectedGrdList the selectedGrdList to set
     */
    public void setSelectedGrdList(List<GroundStaff> selectedGrdList) {
        this.selectedGrdList = selectedGrdList;
    }

    /**
     * @return the selectedCbCrewList
     */
    public List<CabinCrew> getSelectedCbCrewList() {
        return selectedCbCrewList;
    }

    /**
     * @param selectedCbCrewList the selectedCbCrewList to set
     */
    public void setSelectedCbCrewList(List<CabinCrew> selectedCbCrewList) {
        this.selectedCbCrewList = selectedCbCrewList;
    }

    /**
     * @return the selectedCpCrewList
     */
    public List<CockpitCrew> getSelectedCpCrewList() {
        return selectedCpCrewList;
    }

    /**
     * @param selectedCpCrewList the selectedCpCrewList to set
     */
    public void setSelectedCpCrewList(List<CockpitCrew> selectedCpCrewList) {
        this.selectedCpCrewList = selectedCpCrewList;
    }

}
