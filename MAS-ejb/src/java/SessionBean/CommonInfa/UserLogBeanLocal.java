/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfa;

import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface UserLogBeanLocal {
    public void createLog(String username, String description);
}