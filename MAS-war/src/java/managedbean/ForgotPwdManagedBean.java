/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import SessionBean.CommonInfa.VerifyAccountBeanLocal;
import SessionBean.CommonInfa.ManageAccountBeanLocal;
import java.io.IOException;
import java.io.Serializable;
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

@Named(value = "fPwdMB")
@SessionScoped
public class ForgotPwdManagedBean implements Serializable{
    @EJB
    private VerifyAccountBeanLocal val;

    private String username;
    private String password;
    private String pswConfirm;
    private String stfType;
    private String email;

    public void verify() throws IOException
    {
        Boolean validity;
        validity = val.validateAccInfo(username, email, stfType);
        
        if(validity)
        {
            FacesContext.getCurrentInstance().getExternalContext().redirect("resetPwd.xhtml");
        }else
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Username, email or domain incorrect"));
        }
    }
    
    public void resetPsw()throws IOException
    {
        System.out.println(username);
        System.out.println(email);
        
        if(password.equals(pswConfirm))
        {
            val.resetPwd(username, password, stfType);
            FacesContext.getCurrentInstance().getExternalContext().redirect("../login.xhtml");
        }else
        {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Password confirmation does not match with your password!"));
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
     * @return the pswConfirm
     */
    public String getPswConfirm() {
        return pswConfirm;
    }

    /**
     * @param pswConfirm the pswConfirm to set
     */
    public void setPswConfirm(String pswConfirm) {
        this.pswConfirm = pswConfirm;
    }
}
