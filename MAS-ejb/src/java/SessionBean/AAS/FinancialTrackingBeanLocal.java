/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AAS.Expense;
import Entity.AAS.Revenue;
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

    public Double calculateRevenue(String channel, long year, String quarter);

    public List<Revenue> getRevenueList(long year, String quarter);

    public List<Expense> getExpenseList(long year, String quarter);

    public Double calculateExpense(String category, long year, String quarter);

    public Double calculateNoDateExpense(String category, long year, String quarter);

    public Double calculateRefund(String channel, long year, String quarter);
    
}
