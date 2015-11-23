/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AIS;

import Entity.APS.FlightFrequency;
import Entity.AIS.BookingClassInstance;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author wang
 */
@Remote
public interface ViewBookingClassPriceBeanRemote {

    public List<FlightFrequency> getFlightList(String dateString);

    public List<BookingClassInstance> getBkiList(String flightNo, String date);

    public void editPrice(BookingClassInstance bki, Double price);
    
}
