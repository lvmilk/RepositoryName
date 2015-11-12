/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DDSmanagedBean;

import CommonInframanagedbean.SessionUtil;
import SessionBean.CommonInfra.ManageAccountBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author LI HAO
 */
@Named(value = "ddslogin")
@ViewScoped
public class DDS_LoginManagedBean implements Serializable {

    @EJB
    private ManageAccountBeanLocal mal;
    private String username;
    private String password;
    private String stfType = "agency";

    public void logIn() throws IOException {
        Boolean validity;
        HttpSession session = SessionUtil.getSession();
        session.setAttribute("username", username);
        session.setAttribute("stfType", stfType);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("UserId", username);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("StaffType", stfType);
        validity = mal.validateDDSLogin(username, password);

        System.out.println(validity);
        if (validity) {
            System.out.println("~~~~~~~DDS_Login: Account exists");
            FacesContext.getCurrentInstance().getExternalContext().redirect("ddsWorkspace.xhtml");
        }else{
            System.out.println("Username or password incorrect");
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage("Username or password incorrect"));
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
}
