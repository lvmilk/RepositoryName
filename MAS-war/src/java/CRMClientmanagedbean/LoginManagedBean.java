/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author wang
 */
@Named(value = "loginManagedBean")
@ViewScoped
public class LoginManagedBean implements Serializable {

    private String TFPnumber;
    private String password;
    
    
    

    /**
     * Creates a new instance of LoginManagedBean
     */
    public LoginManagedBean() {
    }

}
