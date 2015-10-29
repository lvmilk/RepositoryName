/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Passenger;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface PassengerSessionBeanLocal {
    public void makeReservation(ArrayList<Passenger> passengerList,String email, Long memberId);
    public void makeRsvGuest(ArrayList<Passenger> passengerList,String title,String firstName,String lastName,String address,String email,String contactNo);
    public boolean checkMemberExist(Long memberId, String email);
}
