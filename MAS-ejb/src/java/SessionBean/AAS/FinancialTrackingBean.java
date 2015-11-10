/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AAS.Revenue;
import Entity.ADS.Ticket;
import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import Entity.APS.Route;
import Entity.CRM.CarPayment;
import Entity.CRM.HotelPayment;
import Entity.CRM.RailwayPayment;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xi
 */
@Stateless
public class FinancialTrackingBean implements FinancialTrackingBeanLocal {

    @PersistenceContext
    EntityManager em;
    AircraftType aircraftType;
    Aircraft aircraft;
    Route route;
    Ticket ticket;
    OfficeStaff staff;
    GroundStaff gdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;

    public FinancialTrackingBean() {
    }

//    @Override
//    public List<Ticket> getAllTicket() {
//        Query query = em.createQuery("SELECT t FROM Ticket t ");
//        List<Ticket> resultList = (List) query.getResultList();
//        if (resultList.isEmpty()) {
//            System.out.println("AAS:FTB:Ticket List is empty");
//        } else {
//            System.out.println("AAS:FTB:Ticket List data exists");
//        }
//        return resultList;
//    }

    @Override
    public Double calculateRevenue(String channel, long year, String quarter) {
        Double total = 0.0;
        Query q = em.createQuery("SELECT r FROM Revenue r where r.channel =:channel");
        q.setParameter("channel", channel);
        if (q.getResultList().isEmpty()) {
            System.out.println("AAS:FTB: No available revenue record for " + channel);
            return 0.0;
        } else {
            System.out.println("AAS:FTB: Available revenue record found for " + channel);
        }
        List<Revenue> list = (List) q.getResultList();

        int revenueYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            Date paymentDate = list.get(i).getPaymentDate();
            cal.setTime(paymentDate);
            revenueYear = cal.get(Calendar.YEAR);
            switch (quarter) {
                case "1": {
                    startCal.set((int) year, 0, 1);
                    endCal.set((int) year, 2, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                    break;
                }
                case "2": {
                    startCal.set((int) year, 3, 1);
                    endCal.set((int) year, 5, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                    break;
                }
                case "3": {
                    startCal.set((int) year, 6, 1);
                    endCal.set((int) year, 8, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                    break;
                }
                case "4": {
                    startCal.set((int) year, 9, 1);
                    endCal.set((int) year, 11, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                    break;
                }
                default:
                    System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                    break;
            }
            if (revenueYear == year && inPeriod) {
                total = total + list.get(i).getReceivable();
            }
        }
        System.out.println("AAS:FTB: totalTicketSale: " + total);
        return total;
    }

}
