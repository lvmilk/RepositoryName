/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

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

    @Override
    public List<Ticket> getAllTicket() {
        Query query = em.createQuery("SELECT t FROM Ticket t ");
        List<Ticket> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:FTB:Ticket List is empty");
        } else {
            System.out.println("AAS:FTB:Ticket List data exists");
        }
        return resultList;
    }

    @Override
    public Double totalTicketSale(String bookSystem, long year, String quarter) {
        Double total = 0.0;
        Query q1 = em.createQuery("SELECT t FROM Ticket t where t.bookSystem =:bookSystem");
        q1.setParameter("bookSystem", bookSystem);
        if (q1.getResultList().isEmpty()) {
            System.out.println("AAS:FTB: No available ticket for " + bookSystem);
            return 0.0;
        } else {
            System.out.println("AAS:FTB: Available ticket found for " + bookSystem);
        }
        List<Ticket> list = (List) q1.getResultList();

        int ticketYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            Date bookDate = list.get(i).getBookDate();
            cal.setTime(bookDate);
            ticketYear = cal.get(Calendar.YEAR);
            switch (quarter) {
                case "1": {
                    startCal.set((int) year, 1, 1);
                    endCal.set((int) year, 3, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                    break;
                }
                case "2": {
                    startCal.set((int) year, 4, 1);
                    endCal.set((int) year, 6, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                    break;
                }
                case "3": {
                    startCal.set((int) year, 7, 1);
                    endCal.set((int) year, 9, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                    break;
                }
                case "4": {
                    startCal.set((int) year, 10, 1);
                    endCal.set((int) year, 12, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                    break;
                }
                default:
                    System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                    break;
            }
            if (ticketYear == year && inPeriod) {
                total = total + list.get(i).getPrice();
            }
        }
        System.out.println("AAS:FTB: totalTicketSale: " + total);
        return total;
    }

    @Override
    public Double chargedCommission(String channel, long year, String quarter) {
        Double total = 0.0;
        int commissionYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        if (channel.equals("HOTEL")) {
            Query q1 = em.createQuery("SELECT hp FROM HotelPayment hp");
            List<HotelPayment> list = (List) q1.getResultList();
            if (list.isEmpty()) {
                System.out.println("AAS:FTB:Hotel Payment List is empty");
                return 0.0;
            } else {
                System.out.println("AAS:FTB:Hotel Payment data exists");
            }
            System.out.println(list);
            for (int i = 0; i < list.size(); i++) {
                Date paymentDate = list.get(i).getPaymentDate();
                cal.setTime(paymentDate);
                commissionYear = cal.get(Calendar.YEAR);
                switch (quarter) {
                    case "1": {
                        startCal.set((int) year, 1, 1);
                        endCal.set((int) year, 3, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "2": {
                        startCal.set((int) year, 4, 1);
                        endCal.set((int) year, 6, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "3": {
                        startCal.set((int) year, 7, 1);
                        endCal.set((int) year, 9, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "4": {
                        startCal.set((int) year, 10, 1);
                        endCal.set((int) year, 12, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    default:
                        System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                        break;
                }
                if (commissionYear == year && inPeriod) {
                    total = total + list.get(i).getPayment();
                }
            }
        }
        if (channel.equals("CAR RENTAL")) {
            Query q2 = em.createQuery("SELECT cp FROM CarPayment cp");
            List<CarPayment> list = (List) q2.getResultList();
            if (list.isEmpty()) {
                System.out.println("AAS:FTB:Car Payment List is empty");
                return 0.0;
            } else {
                System.out.println("AAS:FTB:Car Payment data exists");
            }
            for (int i = 0; i < list.size(); i++) {
                Date paymentDate = list.get(i).getPaymentDate();
                cal.setTime(paymentDate);
                commissionYear = cal.get(Calendar.YEAR);
                switch (quarter) {
                    case "1": {
                        startCal.set((int) year, 1, 1);
                        endCal.set((int) year, 3, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "2": {
                        startCal.set((int) year, 4, 1);
                        endCal.set((int) year, 6, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "3": {
                        startCal.set((int) year, 7, 1);
                        endCal.set((int) year, 9, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "4": {
                        startCal.set((int) year, 10, 1);
                        endCal.set((int) year, 12, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    default:
                        System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                        break;
                }
                if (commissionYear == year && inPeriod) {
                    total = total + list.get(i).getPayment();
                }
            }
        }
        if (channel.equals("HIGH-SPEED RAILWAY")) {
            Query q3 = em.createQuery("SELECT rp FROM RailwayPayment rp");
            List<RailwayPayment> list = (List) q3.getResultList();
            if (list.isEmpty()) {
                System.out.println("AAS:FTB:Railway Payment List is empty");
                return 0.0;
            } else {
                System.out.println("AAS:FTB:Railway Payment data exists");
            }
            for (int i = 0; i < list.size(); i++) {
                Date paymentDate = list.get(i).getPaymentDate();
                cal.setTime(paymentDate);
                commissionYear = cal.get(Calendar.YEAR);
                switch (quarter) {
                    case "1": {
                        startCal.set((int) year, 1, 1);
                        endCal.set((int) year, 3, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "2": {
                        startCal.set((int) year, 4, 1);
                        endCal.set((int) year, 6, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "3": {
                        startCal.set((int) year, 7, 1);
                        endCal.set((int) year, 9, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    case "4": {
                        startCal.set((int) year, 10, 1);
                        endCal.set((int) year, 12, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                        break;
                    }
                    default:
                        System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                        break;
                }
                if (commissionYear == year && inPeriod) {
                    total = total + list.get(i).getPayment();
                }
            }
        }
        if (channel.equals("GDS")) {
            Query q4 = em.createQuery("SELECT t FROM Ticket t where t.bookSystem =:channel");
            q4.setParameter("channel", channel);
            List<Ticket> list = (List) q4.getResultList();
            if (list.isEmpty()) {
                System.out.println("AAS:FTB: No available ticket for " + channel);
                return 0.0;
            } else {
                System.out.println("AAS:FTB: Available ticket found for " + channel);
            }
            for (int i = 0; i < list.size(); i++) {
                Date bookDate = list.get(i).getBookDate();
                cal.setTime(bookDate);
                commissionYear = cal.get(Calendar.YEAR);
                switch (quarter) {
                    case "1": {
                        startCal.set((int) year, 1, 1);
                        endCal.set((int) year, 3, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                        break;
                    }
                    case "2": {
                        startCal.set((int) year, 4, 1);
                        endCal.set((int) year, 6, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                        break;
                    }
                    case "3": {
                        startCal.set((int) year, 7, 1);
                        endCal.set((int) year, 9, 30);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                        break;
                    }
                    case "4": {
                        startCal.set((int) year, 10, 1);
                        endCal.set((int) year, 12, 31);
                        startDate = startCal.getTime();
                        endDate = endCal.getTime();
                        inPeriod = !(bookDate.before(startDate) || bookDate.after(endDate));
                        break;
                    }
                    default:
                        System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                        break;
                }
                if (commissionYear == year && inPeriod) {
                    total = total + list.get(i).getPrice();
                }
            }
        } else {
            return 0.0;
        }
        System.out.println("AAS:FTB: chargedCommission(): total commissions for " + channel + " is: " + total);
        return total;
    }

}
