/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.APS.Route;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface ReserveFlightBeanLocal {



    public List<FlightFrequency> getAllFlightFrequency();

    public List<FlightFrequency> getSecondFrequency(String origin);

    public List<FlightInstance> findResultInstanceList(String origin, String dest, Date departDate);

    public List<FlightFrequency> findFrequencies(String origin, String dest);
    
}
