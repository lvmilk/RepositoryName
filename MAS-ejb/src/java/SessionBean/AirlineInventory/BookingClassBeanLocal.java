/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AirlineInventory;

import javax.ejb.Local;

/**
 *
 * @author LIU YUQI'
 */
@Local
public interface BookingClassBeanLocal {
    
        public boolean addBookingClass(String annotation, String cabinName, Double price_percentage, Double refund_percentage, Double change_route_percentage, 
     Double change_date_percentage, Double change_passenger_percentage, Double open_jaw_percentage, Double earn_mile_percentage, Integer min_stay, Integer max_stay, 
     Integer ticket_advance, Integer reserve_advance, boolean can_standby, boolean dds_available, boolean gds_available );
        
        public boolean checkDuplicate(String annotation);
    
}
