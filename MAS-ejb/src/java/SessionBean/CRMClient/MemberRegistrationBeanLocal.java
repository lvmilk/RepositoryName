/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRMClient;

import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface MemberRegistrationBeanLocal {


    public Long createMember(String title, String password, String firstName, String lastName, String passport, String email, String address, String contact, String dob) throws Exception;
    
}
