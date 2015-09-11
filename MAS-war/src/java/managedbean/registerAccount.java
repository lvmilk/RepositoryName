/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package managedbean;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import SessionBean.manageAccountLocal;
import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "register")
@RequestScoped
public class registerAccount {
  @EJB
private manageAccountLocal mal;
     
    private String username;
    private String password;
  
    public registerAccount() {
   
    
    
    
    }
    
}
