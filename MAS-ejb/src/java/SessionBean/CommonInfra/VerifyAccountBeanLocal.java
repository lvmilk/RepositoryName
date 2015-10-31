/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CommonInfra;

import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface VerifyAccountBeanLocal {
    public boolean validateAccInfo(String username,String email,String stfType);
    public void resetPwd(String username,String password, String stfType);
}
