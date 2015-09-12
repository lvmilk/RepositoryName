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

@Named(value = "login")
@RequestScoped
public class LoginManagerBean {

    @EJB
    private manageAccountLocal mal;

    private String username;
    private String password;
    private String type;

//    private String title;
//    private String surname;
//    private String givenName;

//    private String mobilePhone;
//    private String officePhone;
//    private String email;
//    private String livingCountry;
//    private String livingState;
//    private String livingCity;
//    private String livingAddress;
//    private String postalCode;
//
//    private String gender;
//    private String nationality;
//    private String birthday;
//    private String travelDocumentIssueCountry;
//    private String travelDocumentType;
//    private String travelDocumentNo;
//    private String travelDocumentIssueDate;
//    private String travelDocumentExpireDate;
//
//    private String FFPNo;
//    private String JoinDate;
//    private String membershipTier;
//    private long MileAccountId;

    public void logIn() throws IOException {

        Boolean validity;
        validity = mal.validateLogin(username, password, type);

        if (validity) {
            System.out.println("User exists.");
            FacesContext.getCurrentInstance().getExternalContext().redirect("loginSuccess.xhtml");
        } else {

            System.out.println("User not found");

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Username or password incorrect"));

        }
    }

    public void register() {

        mal.addAccount(username, password,type);

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
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}
