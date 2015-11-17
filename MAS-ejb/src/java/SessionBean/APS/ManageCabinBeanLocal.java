/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.APS;

import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface ManageCabinBeanLocal {

    public boolean widthExist(AircraftType type, String cabin);

    public boolean rowCountExist(AircraftType type, String cabin);

    public boolean rowSeatCount(AircraftType type, String cabin);

    public boolean rowConfig(AircraftType type, String cabin);

    public void updateCabin(CabinClass cabinSelected, Double seatWidth, Integer rowCount, Integer rowSeatCount, String rowConfig);

    public boolean checkAllConfigComplete(AircraftType airType);

    public void generateAllCharts(List<CabinClass> cabinList);

    
    
}
