/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

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
import javax.servlet.http.HttpSession;
import CommonInframanagedbean.Control;
import CommonInframanagedbean.SessionUtil;
import CommonInframanagedbean.EditProfileManagedBean;

@Named(value = "login")
@ViewScoped
public class LoginManagerBean implements Serializable {

    @EJB
    private ManageAccountBeanLocal mal;

    private String username;
    private String password;
    private String stfType = "officeStaff";
    private String email;

    private String licence;
    private String name;
    private String stfLevel;
    private Double salary;

    public void logIn() throws IOException {

        Boolean validity;
        int status;
        validity = mal.validateLogin(username, password, stfType);

        System.out.println(stfType);

        if (validity) {
            System.out.println("User exists.");
            HttpSession session = SessionUtil.getSession();
            session.setAttribute("username", username);
            session.setAttribute("stfType", stfType);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UserId", username);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("StaffType", stfType);
            if (stfType.equals("administrator")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("sAdmWorkspace.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("staffWorkspace.xhtml");
            }
        } else {
            status = mal.getLockedOutStatus(username, stfType);
            if (status == 1) {
                System.out.println("Your Account has been locked out");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Your Account has been locked out, contact administrator to unlock account for you"));
            } else {
                System.out.println("Username or password incorrect");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Username or password incorrect"));
            }
        }
    }


    public void foget() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./CMIpages/forgetPwd.xhtml");
    }

    public void createAdmin() {
        boolean blCreateAcc;
        blCreateAcc = mal.checkAccDuplicate("admin", "administrator");
        if (!blCreateAcc) {
            mal.addAdmin("admin", "admin", "administrator");
        } else {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
        }
    }

    public void createAcc() {
        boolean blCreateAcc, blCreateEmail;

        blCreateAcc = mal.checkAccDuplicate(username, stfType);
        blCreateEmail = mal.checkEmailExists(email);

        System.out.println("!!! Create Acc email: " + blCreateEmail);
        if (!blCreateAcc && !blCreateEmail) {
            System.out.println(username);
            System.out.println(password);
            System.out.println(email);
            System.out.println(stfType);
            System.out.println("We are in createAcc managed bean");
            mal.addAccount(username, password, email, stfType, name, stfLevel,salary);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Created Successfully"));

        } else if (!blCreateEmail && blCreateAcc) {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
        } else if (!blCreateAcc && blCreateEmail) {
            System.out.println("Email exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Email exists"));
        } else {
            System.out.println("Email & Account exist");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Email & Account exist"));
        }

    }

    public void createCockpitAcc() {
        boolean blCreateAcc, blCreateEmail;
        blCreateAcc = mal.checkAccDuplicate(username, stfType);
        blCreateEmail = mal.checkEmailExists(email);
        System.out.println("!!!Create Cockpit email: " + blCreateEmail);
        if (!blCreateAcc && !blCreateEmail) {
            System.out.println("We are in createCockpitAcc managed bean");
            mal.addCocpitAcc(username, password, email, stfType, name, stfLevel,salary,licence);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Created Successfully"));
        } else if (!blCreateEmail && blCreateAcc) {
            System.out.println("Account exists");
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account exists"));
        } else if (!blCreateAcc && blCreateEmail) {
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
}
