/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Xu
 */
@Stateless
public class FlightSchedulingBean implements FlightSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;
    
    FlightFrequency flightFreq;
    FlightInstance flightInst;
    
    public FlightSchedulingBean() {
    }
    
    public void addFlightFrequency() {
    
        }
}
