/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AAS.Expense;
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
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
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
    OfficeStaff staff;
    GroundStaff gdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;
    Revenue revenue;

    @EJB
    FlightSchedulingBeanLocal fsb;

    @EJB
    CrewSchedulingBeanLocal csb;

    public FinancialTrackingBean() {
    }

    @Override
    public List<Revenue> getRevenueList(long year, String quarter) {
        Query query = em.createQuery("SELECT r FROM Revenue r ");
        List<Revenue> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:FTB:Revenue List is empty");
        } else {
            System.out.println("AAS:FTB:Revenue List data exists");
        }
        List<Revenue> list = new ArrayList<>();
        int revenueYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < resultList.size(); i++) {
            Date paymentDate = resultList.get(i).getPaymentDate();
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
                list.add(resultList.get(i));
            }
        }
        return list;
    }

    @Override
    public Double calculateRevenue(String channel, long year, String quarter) {
        Double total = 0.0;
        Query q = em.createQuery("SELECT r FROM Revenue r where r.channel =:channel");
        q.setParameter("channel", channel);
        if (q.getResultList().isEmpty()) {
            System.out.println("AAS:FTB: No available channel for " + channel);
            return 0.0;
        } else {
            System.out.println("AAS:FTB: Available channel found for " + channel);
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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public List<Expense> getExpenseList(long year, String quarter) {
        Query query = em.createQuery("SELECT e FROM Expense e ");
        List<Expense> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:FTB:Expense List is empty");
        } else {
            System.out.println("AAS:FTB:Expense List data exists");
        }

        int expenseYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        switch (quarter) {
            case "1": {
                startCal.set((int) year, 0, 1);
                endCal.set((int) year, 2, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case "2": {
                startCal.set((int) year, 3, 1);
                endCal.set((int) year, 5, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case "3": {
                startCal.set((int) year, 6, 1);
                endCal.set((int) year, 8, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case "4": {
                startCal.set((int) year, 9, 1);
                endCal.set((int) year, 11, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            default:
                System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                break;
        }
        List<Expense> list = new ArrayList<>();

        for (int i = 0; i < resultList.size(); i++) {
            List<Expense> copyList = new ArrayList<>();
            String cate = resultList.get(i).getCategory();
            if (cate.equals("Purchase Aircraft")) {
                Date paymentDate = resultList.get(i).getPaymentDate();
                cal.setTime(paymentDate);
                expenseYear = cal.get(Calendar.YEAR);
                inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                if (expenseYear == year && inPeriod) {
                    Expense e = resultList.get(i);
                    list.add(e);
                }
            } else if (cate.equals("Depreciation") || cate.equals("Other Cost")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                copyList.add(e);
                //    System.out.println("AAS:FTB:getExpenseList: old copyList of expenseList 1 : " + copyList.get(0).getPayable());
                Double payable = e.getPayable();
                copyList.get(0).setPayable(payable / 4);
//                System.out.println("AAS:FTB:getExpenseList: copyList of expenseList 1 : " + copyList.get(0).getPayable());
//                System.out.println("AAS:FTB:getExpenseList: resultList of expenseList 1 : " + resultList.get(i).getPayable());
                //     System.out.println("AAS:FTB:getExpenseList: new added copyList of expenseList 1 : " + copyList.get(0).getPayable());
                list.add(copyList.get(0));
                e.setPayable(copyData);
            } else if (cate.equals("Fuel Cost")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                copyList.add(e);
                //      System.out.println("AAS:FTB:getExpenseList: old copyList of expenseList 2 : " + copyList.get(0).getPayable());
                Double payable = 0.0;
                payable = e.getPayable();
                copyList.get(0).setPayable(payable * fsb.calPeriodTotalFlightHour(startDate, endDate));
                //     System.out.println("AAS:FTB:getExpenseList: new added copyList of expenseList 2 : " + copyList.get(0).getPayable());
                list.add(copyList.get(0));
                e.setPayable(copyData);
            } else {
                ///maintenance cost
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                copyList.add(e);
                //     System.out.println("AAS:FTB:getExpenseList: old copyList of expenseList 3 : " + copyList.get(0).getPayable());
                Double payable1 = 0.0;
                payable1 = e.getPayable();
//                System.out.println("********** ^^^^^^^^  111  AAS:FTB:getExpenseList: new added copyList of expenseList 3 : payable1: " + payable1);
                payable1 = payable1 * fsb.calPeriodTotalMtManHour(startDate, endDate);
//                System.out.println("********** ^^^^^^^^  222  AAS:FTB:getExpenseList: new added copyList of expenseList 3 : payable1: " + payable1);

                copyList.get(0).setPayable(payable1);
//                System.out.println("********** AAS:FTB:getExpenseList: new added copyList of expenseList 3 : payable1: " + payable1);
//                System.out.println("********** AAS:FTB:getExpenseList: new added copyList of expenseList 3 : " + copyList.get(0).getPayable());
                list.add(copyList.get(0));
                e.setPayable(copyData);
            }
        }
        return list;
    }

    ///with payment date --> Purchase Aircraft and DDS Commission
    @Override
    public Double calculateExpense(String category, long year, String quarter) {
        Double total = 0.0;
        List<Expense> resultList = new ArrayList<>();
        Query q = em.createQuery("SELECT e FROM Expense e where e.category=:category");
        q.setParameter("category", category);
        if (q.getResultList().isEmpty()) {
            System.out.println("AAS:FTB: No available expense category for " + category);
            return 0.0;
        } else {
            System.out.println("AAS:FTB: Available expense category found for " + category);
        }
        resultList = (List) q.getResultList();
        int expenseYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < resultList.size(); i++) {
            Date paymentDate = resultList.get(i).getPaymentDate();
            cal.setTime(paymentDate);
            expenseYear = cal.get(Calendar.YEAR);
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
            if (expenseYear == year && inPeriod) {
                total = total + resultList.get(i).getPayable();
                System.out.println("FTB:calculateExpense: " + resultList.get(i).getCategory() + " with total payable: " + total);
            }
        }
        return total;
    }

    // Without payment date --> Fuel Cost, Depreciation, Maintenance Cost, Other Cost
    @Override
    public Double calculateNoDateExpense(String category, long year, String quarter) {
        Double total = 0.0;
        List<Expense> resultList = new ArrayList<>();
        Query q = em.createQuery("SELECT e FROM Expense e where e.category=:category");
        q.setParameter("category", category);
        if (q.getResultList().isEmpty()) {
            System.out.println("AAS:FTB: No available expense category for " + category);
            return 0.0;
        } else {
            System.out.println("AAS:FTB: Available expense category found for " + category);
        }
        resultList = (List) q.getResultList();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < resultList.size(); i++) {
            switch (quarter) {
                case "1": {
                    startCal.set((int) year, 0, 1);
                    endCal.set((int) year, 2, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case "2": {
                    startCal.set((int) year, 3, 1);
                    endCal.set((int) year, 5, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case "3": {
                    startCal.set((int) year, 6, 1);
                    endCal.set((int) year, 8, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case "4": {
                    startCal.set((int) year, 9, 1);
                    endCal.set((int) year, 11, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                default:
                    System.out.println("AAS:FTB: Invalid quarter input: " + quarter);
                    break;
            }

            if (category.equals("Depreciation")) {
                String registrationNo = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:registrationNo").setParameter("registrationNo", registrationNo);
                List<Aircraft> aircraftList = (List) q1.getResultList();
                Date checkDate = new Date();
                if (aircraftList.get(0) != null) {
                    try {
                        checkDate = format.parse(aircraftList.get(0).getDeliveryDate());
                    } catch (ParseException ex) {
                        Logger.getLogger(FinancialTrackingBean.class.getName()).log(Level.SEVERE, "FTB:calculateNoDateExpense: Cannot get aircraft delivery date", ex);
                    }
                    if (!checkDate.after(endDate)) {
                        total = total + resultList.get(i).getPayable() / 4;    //Straight Line Depreciation
                        System.out.println("FTB:calculateNoDateExpense: Depreciation Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
                    } else {
                        System.out.println("FTB:calculateNoDateExpense: There is no depreciation during this period.");
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type " + aircraftList.get(0).getRegistrationNo() + " is deleted.");
                }
            } else if (category.equals("Fuel Cost")) {
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (typeList.get(0) != null) {
                    total = total + (resultList.get(i).getPayable()) * (fsb.calPeriodTotalFlightHour(startDate, endDate));
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type " + typeList.get(0).getType() + " is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Fuel Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Maintenance Cost")) {
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (typeList.get(0) != null) {
                    total = total + resultList.get(i).getPayable() * fsb.calPeriodTotalMtManHour(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type " + typeList.get(0).getType() + " is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Maintenance Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Other Cost")) {
                String routeId = resultList.get(i).getCostSource();
                Long id = Long.valueOf(routeId);
                Route checkRoute = em.find(Route.class, id);
                if (checkRoute != null) {
                    total = total + resultList.get(i).getPayable() / 4;
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this route " + id + " is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Other Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Cabin Leader")) {
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    total = total + resultList.get(i).getPayable() * 4 + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew " + name + " is deleted.");
                }
            } else if (category.equals("Cabin Crew")) {
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    total = total + resultList.get(i).getPayable() * 4 + csb.calCabinCrewTotalHourPay(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew " + name + " is deleted.");
                }
            } else if (category.equals("Captain")) {
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    total = total + resultList.get(i).getPayable() * 4 + csb.calCaptainTotalHourPay(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this captain " + name + " is deleted.");
                }
            } else if (category.equals("Pilot")) {
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    total = total + resultList.get(i).getPayable() * 4 + csb.calPilotTotalHourPay(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this pilot " + name + " is deleted.");
                }
            } else if (category.equals("Ground Staff")) {
                String name = resultList.get(i).getCostSource();
                GroundStaff groundStaff = em.find(GroundStaff.class, name);
                if (groundStaff != null) {
                    total = total + resultList.get(i).getPayable() * 4;
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this ground staff " + name + " is deleted.");
                }
            } else {
                //office staff
                String name = resultList.get(i).getCostSource();
                OfficeStaff officeStaff = em.find(OfficeStaff.class, name);
                if (officeStaff != null) {
                    total = total + resultList.get(i).getPayable() * 4;
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this office staff " + name + " is deleted.");
                }
            }
        }
        return total;
    }

}
