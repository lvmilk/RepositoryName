/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.APS.AircraftType;
import javax.ejb.Remote;

/**
 *
 * @author LIU YUQI'
 */
@Remote
public interface SeatPlanBeanRemote {

    public boolean checkAirTypeEmpty();

    public AircraftType findType(String type);

    public void addCabin(AircraftType aircraftType, Integer suiteNo, Integer fcSeatNo, Integer bcSeatNo, Integer pecSeatNo, Integer ecSeatNo);
}
