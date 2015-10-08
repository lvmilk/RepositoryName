/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.CabinClass;
import Entity.APS.FlightFrequency;
import Entity.aisEntity.BookingClassInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface SeatAllocationBeanLocal {

    public List<FlightFrequency> getFlightList(String date);

    public void editSeatNo(BookingClassInstance bki, Integer seatNo);

    public List<CabinClass> getCabinList(String flightNo);

    public List<BookingClassInstance> getBkiList(String flightNo, String date, String cabinName);
    
}
