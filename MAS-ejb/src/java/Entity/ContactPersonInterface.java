/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

/**
 *
 * @author victor
 */
public interface ContactPersonInterface extends GuestInterface {
    public void setMobilePhone (String mobilePhone);
    public String getMobilePhone ();
    
    public void setOfficePhone (String officePhone);
    public String getOfficePhone ();
    
    public void setEmail (String email);
    public String getEmail ();
    
    public void setLivingCountry (String livingCountry);
    public String getLivingCountry ();
    
    public void setLivingState (String livingState);
    public String getLivingState();
    
    public void setLivingCity (String livingCity);
    public String getLivingCity ();
    
    public void setLivingAddress (String livingAddress);
    public String getLivingAddress ();
    
    public void setPostalCode (String pc);
    public String getPostalCode ();
}
