/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.FlightFrequency;
import Entity.aisEntity.BookingClassInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface SeatAssignBeanLocal {
    public List<FlightFrequency> getFlightList(String dateString);

    public List<BookingClassInstance> getBkiList(String flightNo, String date);
}
