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
    private String admType;
    private String email;

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
        validity = mal.validateLogin(username, password, admType);
        
        System.out.println(admType);
        
        if (validity) {
            System.out.println("User exists.");
            if (admType.equals("administrator")) {
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

        mal.addAccount(username, password, admType);

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
    public String getAdmType() {
        return admType;
    }

    /**
     * @param type the type to set
     */
    public void setAdmType(String admType) {
        this.admType = admType;
    }
    
    public void valueChanged(ValueChangeEvent event)
    {
        Object newValue=event.getNewValue();
        System.out.println(newValue);
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
}
