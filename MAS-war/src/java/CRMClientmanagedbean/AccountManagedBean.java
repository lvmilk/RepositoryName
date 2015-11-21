/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "CRMAMB")
@ViewScoped
public class AccountManagedBean implements Serializable{

    /**
     * Creates a new instance of AccountManagedBean
     */
        private String title;
    private String password;
    private String email;
    private String lastName;
    private String firstName;
    private String address;
    private String contact;
    private String passport;
    private String dob;
    private Date dobDateType;
    
    
    public AccountManagedBean() {
    }
    
     public void goToLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./crmLogin.xhtml");

    }
    
    
    
}
