/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.ADS.Ticket;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xi
 */
@Local
public interface FinancialTrackingBeanLocal {

    public List<Ticket> getAllTicket();

    public Double totalTicketSale(String bookSystem, long year, String quarter);

    public Double chargedCommission(String channel, long year, String quarter);
    
}
