/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface BookerBeanLocal {
    public List<Booker> getAllBooker();
    public boolean checkEmailDuplicate(String email, String emailEdited);
    public void editBooker(Long Id,String title,String firstName,String lastName,String address,String email,String contactNo,String dob,Double miles,String passport,boolean memberStatus);
    public boolean checkEmailExists(String email);
    public boolean checkPassportExists(String passport);
    public Long retrieveBookerID(String email);
    public Booker retrieveBooker(Long Id);

    public void editThisBooker(Booker bookPerson);
}

