/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import java.util.ArrayList;
import javax.ejb.Stateless;
import Entity.ADS.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author LI HAO
 */
@Stateless
public class PassengerSessionBean implements PassengerSessionBeanLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void makeReservation(ArrayList passengerList, String email, String contactNo) {

    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
