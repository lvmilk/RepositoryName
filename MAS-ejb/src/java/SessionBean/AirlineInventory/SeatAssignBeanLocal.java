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

//    public ArrayList<Integer> computeOptimalSeat(List<BookingClassInstance> bookClassInstanceList);
    public double computeOptimalRev(List<BookingClassInstance> listInstance);
    
    public ArrayList<BookingClassInstance> listAssign (List<BookingClassInstance> bookClassInstanceList, String cabinName);
    
    public List<FlightCabin> getCabinList(String flightNo, String dateString);
    
    public void changeBookdSeatCount(BookingClassInstance bInstance);

    public void changeSeatNo(BookingClassInstance bInstance);
    
    public double computeCurrentRev(List<BookingClassInstance> bookClassInstanceList);

    public List<BookingClassInstance> computeOptimalSeat3(List<BookingClassInstance> bookClassInstanceList);

    public ArrayList<Integer> computeProtectLvl(List<BookingClassInstance> bookClassInstanceList);

    public Integer getMaxPriceIndex(List<BookingClassInstance> classList, Integer protect);

    public List<BookingClassInstance> computeOptimalSeat4(List<BookingClassInstance> classList);
}
