/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfaSB;

import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface UserLogSessionBeanLocal {
    public void createLog(String username, String description);
}
