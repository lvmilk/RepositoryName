/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import Entity.aisEntity.BookingClassInstance;
import Entity.aisEntity.FlightCabin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface SeatAssignBeanLocal {
    public List<FlightFrequency> getFlightList(String dateString);

    public List<BookingClassInstance> getBkiList( String cabinName,  FlightInstance flightInstance);
    
   public FlightInstance findFlightInstance(String flightNo, String dateString);

    public void editDemandInfo(BookingClassInstance bInstance);

    public List<BookingClassInstance> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList);
    public double computeOptimalRev(List<BookingClassInstance> listInstance);
    
    public ArrayList<BookingClassInstance> listAssign (List<BookingClassInstance> bookClassInstanceList, String cabinName);
    
    public List<FlightCabin> getCabinList(String flightNo);
}
