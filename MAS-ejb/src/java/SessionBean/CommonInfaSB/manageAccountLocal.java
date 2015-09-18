/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface manageAccountLocal {
    
    
    public boolean validateLogin (String username, String password, String staffType);
    public void addAccount(String username, String password,String stfType);
    
}
