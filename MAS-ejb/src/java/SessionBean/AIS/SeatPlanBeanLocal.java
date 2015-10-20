/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.APS.AircraftType;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface SeatPlanBeanLocal {
      public boolean checkAirTypeEmpty();
     public AircraftType findType(String type);
}
