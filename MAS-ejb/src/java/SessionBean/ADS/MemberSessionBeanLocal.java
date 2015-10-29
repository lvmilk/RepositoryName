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
public interface MemberSessionBeanLocal {
    public List<Member> getAllMember();
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editMember(Long memberId,String title,String firstName,String lastName,String address,String email,String contactNo,String dob,Double miles,String passport,boolean memberStatus);
    public boolean checkEmailExists(String email);
    public boolean checkPassportExists(String passport);
    
}
