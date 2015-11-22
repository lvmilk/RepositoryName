/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRMClient;

import Entity.ADS.Booker;
import Entity.ADS.Reservation;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xi
 */
@Local
public interface MemberAccountBeanLocal {

    public Boolean validateMember(String memberId, String password);

    public List<Reservation> getAllReservation(Long memberId) throws Exception;

    public Booker getThisMember(Long memberId) throws Exception;

    public void editBooker(Long id, String title, String password, String passport, String address, String contact, String dob, boolean sub) throws Exception;

    
    
}
