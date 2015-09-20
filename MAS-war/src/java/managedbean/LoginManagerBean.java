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

@Named(value = "login")
@ViewScoped
public class LoginManagerBean implements Serializable {

    @EJB
    private manageAccountLocal mal;

    private String username;
    private String password;
    private String stfType = "officeStaff";
    private String email;

    private String licence;

    public void logIn() throws IOException {

        Boolean validity;
        validity = mal.validateLogin(username, password, stfType);

        System.out.println(stfType);

        if (validity) {
            System.out.println("User exists.");
            if (stfType.equals("administrator")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sAdmWorkspace.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("staffWorkspace.xhtml");
            }
        } else {

            System.out.println("User not found");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Username or password incorrect"));

        }
    }

    public void createAcc() {
        boolean blCreateAcc;

        blCreateAcc = mal.checkAccDuplicate(username, stfType);

        if (!blCreateAcc) {
            System.out.println("Account exists");
            System.out.println(username);
            System.out.println(password);
            System.out.println(email);
            System.out.println(stfType);
            System.out.println("We are in createAcc managed bean");
            mal.addAccount(username, password, email, stfType);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Created Successfully"));
            
        } else {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
        }

    }

    public void createCockpitAcc() {
        boolean blCreateAcc;
        blCreateAcc = mal.checkAccDuplicate(username, stfType);
        if (!blCreateAcc) {
            System.out.println("We are in createCockpitAcc managed bean");
            mal.addCocpitAcc(username, password, email, stfType, licence);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Created Successfully"));
        } else {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
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
}
