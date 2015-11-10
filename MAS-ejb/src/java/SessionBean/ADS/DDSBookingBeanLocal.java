/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Booker;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface DDSBookingBeanLocal {
    public boolean setAgency_Booker(String username,Booker booker);
}
