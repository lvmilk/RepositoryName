/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import SessionBean.CommonInfaSB.manageAccountLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

@Named(value = "login")
@RequestScoped
public class LoginManagerBean {

    @EJB
    private manageAccountLocal mal;

    private String username;
    private String password;
    private String stfType;
    private String email;
    private Boolean visibility = true;
    private Boolean visiCockpit;

    private String licence;

    public void logIn() throws IOException {

        Boolean validity;
        validity = mal.validateLogin(username, password, stfType);
        
        System.out.println(stfType);
        
        if (validity) {
            System.out.println("User exists.");
            if (stfType.equals("administrator")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sAdmWorkspace.xhtml");
            }
            else
            {
                FacesContext.getCurrentInstance().getExternalContext().redirect("staffWorkspace.xhtml");
            }
        } else {

            System.out.println("User not found");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Username or password incorrect"));

        }
    }

    public void register() {

        mal.addAccount(username, password, stfType);

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
    
    public void valueChanged(ValueChangeEvent event)
    {
        Object newValue=event.getNewValue();
        System.out.println(newValue);
        if (newValue.equals("cockpit"))
        {       
            this.setVisiCockpit(true);
            this.setVisibility(false);
        }
        else
        {
            this.setVisibility(true);
            this.setVisiCockpit(false);
        }
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
