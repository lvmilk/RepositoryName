/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;

/**
 *
 * @author Lu Xi
 */
@Named(value = "aircraftType")
@RequestScoped
public class AircraftType {

    /**
     * Creates a new instance of AircraftType
     */
    public AircraftType() {
    }
    
}
