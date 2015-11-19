/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.CRM;

import Entity.ADS.Booker;
import Entity.ADS.Payment;
import Entity.ADS.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface CustomerRelationshipBeanLocal {

    public List<Booker> getCurrentMonthBabies(Date currentDate) throws Exception;

    public List<Booker> getAllMembers() throws Exception;

    public List<Reservation> getReservation(Long memberID);

    public List<Payment> getPayment();

//    public Booker getBooker(Long bookerID);
    
}
