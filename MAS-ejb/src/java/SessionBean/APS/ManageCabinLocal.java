/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.APS.CabinClass;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface ManageCabinLocal {

    public boolean widthExist(AircraftType type, String cabin);

    public boolean rowCountExist(AircraftType type, String cabin);

    public boolean rowSeatCount(AircraftType type, String cabin);

    public boolean rowConfig(AircraftType type, String cabin);

    public void updateCabin(CabinClass cabinSelected, Double seatWidth, Integer rowCount, Integer rowSeatCount, String rowConfig);

    
    
}
