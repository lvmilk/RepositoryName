/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.ADS;

import Entity.ADS.Passenger;
import Entity.APS.FlightInstance;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author LI HAO
 */
@Local
public interface RsvConfirmationBeanLocal {
    public void setupPsg_Ticket(ArrayList<FlightInstance> departSelected,ArrayList<FlightInstance> returnSelected,ArrayList<Passenger> passengerList);
}
