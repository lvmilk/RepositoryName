/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Member;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface MemberBeanLocal {
    public List<Member> getAllMember();
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editMember();
    
}
