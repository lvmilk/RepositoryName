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
    Expense expense;

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

    @Override
    public Double calculateRefund(String channel, long year, String quarter) {
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
                total = total + list.get(i).getRefund();
            }
        }
        System.out.println("AAS:FTB: totalRefund: " + total);
        return total;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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

    // Without payment date --> Fuel Cost, Depreciation, Maintenance Cost, Other Cost and Staffs
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

            int dayOfYear = startCal.get(Calendar.DAY_OF_YEAR);   //how many days in the selected year
            Date currentDate = new Date();

            if (category.equals("Depreciation")) {
                String registrationNo = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:registrationNo").setParameter("registrationNo", registrationNo);
                List<Aircraft> aircraftList = (List) q1.getResultList();
                Date checkDate = new Date();
                if (!aircraftList.isEmpty()) {
                    try {
                        checkDate = format.parse(aircraftList.get(0).getDeliveryDate());    //check the aircraft has delivered?
                    } catch (ParseException ex) {
                        Logger.getLogger(FinancialTrackingBean.class.getName()).log(Level.SEVERE, "FTB:calculateNoDateExpense: Cannot get aircraft delivery date", ex);
                    }
                    if (!checkDate.after(endDate)) {
                        //Straight Line Depreciation
                        if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                            long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                            total = total + resultList.get(i).getPayable() / dayOfYear * diff;
                        } else {
                            total = total + resultList.get(i).getPayable() / 4;
                        }
                        System.out.println("FTB:calculateNoDateExpense: Depreciation Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
                    } else {
                        System.out.println("FTB:calculateNoDateExpense: There is no depreciation during this period as the aircraft has not been delivered.");
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft is deleted.");
                }
            } else if (category.equals("Fuel Cost")) {
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (!typeList.isEmpty()) {
                    total = total + (resultList.get(i).getPayable()) * (fsb.calPeriodTotalFlightHour(startDate, endDate));
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type  is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Fuel Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Maintenance Cost")) {
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (!typeList.isEmpty()) {
                    total = total + resultList.get(i).getPayable() * fsb.calPeriodTotalMtManHour(startDate, endDate);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Maintenance Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Other Cost")) {
                String routeId = resultList.get(i).getCostSource();
                Long id = Long.valueOf(routeId);
                Route checkRoute = em.find(Route.class, id);
                if (checkRoute != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        total = total + resultList.get(i).getPayable() / dayOfYear * diff;
                    } else {
                        total = total + resultList.get(i).getPayable() / 4;
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this route is deleted.");
                }
                System.out.println("FTB:calculateNoDateExpense: Other Cost:" + total + " unit cost: " + resultList.get(i).getPayable());
            } else if (category.equals("Cabin Leader")) {
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                    } else {
                        total = total + resultList.get(i).getPayable() * 3 + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
                }
            } else if (category.equals("Cabin Crew")) {
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff + csb.calCabinCrewTotalHourPay(startDate, endDate);
                    } else {
                        total = total + resultList.get(i).getPayable() * 3 + csb.calCabinCrewTotalHourPay(startDate, endDate);
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
                }
            } else if (category.equals("Captain")) {
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff + csb.calCaptainTotalHourPay(startDate, endDate);
                    } else {
                        total = total + resultList.get(i).getPayable() * 3 + csb.calCaptainTotalHourPay(startDate, endDate);
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this captain is deleted.");
                }
            } else if (category.equals("Pilot")) {
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff + csb.calPilotTotalHourPay(startDate, endDate);
                    } else {
                        total = total + resultList.get(i).getPayable() * 3 + csb.calPilotTotalHourPay(startDate, endDate);
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this pilot is deleted.");
                }
            } else if (category.equals("Ground Staff")) {
                String name = resultList.get(i).getCostSource();
                GroundStaff groundStaff = em.find(GroundStaff.class, name);
                if (groundStaff != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff;
                    } else {
                        total = total + resultList.get(i).getPayable() * 3;
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this ground staff is deleted.");
                }
            } else {
                //office staff
                String name = resultList.get(i).getCostSource();
                OfficeStaff officeStaff = em.find(OfficeStaff.class, name);
                if (officeStaff != null) {
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        total = total + resultList.get(i).getPayable() * 3 / dayDiff * diff;
                    } else {
                        total = total + resultList.get(i).getPayable() * 3;
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this office staff is deleted.");
                }
            }
        }
        return total;
    }

    ///////////////////////////////////////////////////////////////////
    @Override
    public List<Expense> getExpenseList(long year, String quarter) {
        Query query = em.createQuery("SELECT e FROM Expense e ");
        List<Expense> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:FTB:Expense List is empty");
        } else {
            System.out.println("AAS:FTB:Expense List data exists");
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        int expenseYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Date currentDate = new Date();

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

        int dayOfYear = startCal.get(Calendar.DAY_OF_YEAR);   //how many days in the selected year

        for (int i = 0; i < resultList.size(); i++) {
            List<Expense> originalList = new ArrayList<>();
            String cate = resultList.get(i).getCategory();
            if (cate.equals("Purchase Aircraft") || cate.equals("DDS Commission")) {
                Date paymentDate = resultList.get(i).getPaymentDate();
                cal.setTime(paymentDate);
                expenseYear = cal.get(Calendar.YEAR);
                inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
                if (expenseYear == year && inPeriod) {
                    Expense e = resultList.get(i);
                    list.add(e);
                }

            } else if (cate.equals("Depreciation")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////check whether this Depreciation Cost can be presented
                String registrationNo = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:registrationNo").setParameter("registrationNo", registrationNo);
                List<Aircraft> aircraftList = (List) q1.getResultList();
                Date checkDate = new Date();
                if (!aircraftList.isEmpty()) {
                    try {
                        checkDate = format.parse(aircraftList.get(0).getDeliveryDate());
                    } catch (ParseException ex) {
                        Logger.getLogger(FinancialTrackingBean.class.getName()).log(Level.SEVERE, "FTB:calculateNoDateExpense: Cannot get aircraft delivery date", ex);
                    }
                    if (!checkDate.after(endDate)) {
                        originalList.add(e);
                        originalList.set(0, e);
                        Double payable = e.getPayable();
                        List<Expense> copyList = new ArrayList<Expense>(originalList);
                        if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                            long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                            copyList.get(0).setPayable(payable / dayOfYear * diff);
                        } else {
                            copyList.get(0).setPayable(payable / 4);
                        }
                        list.add(copyList.get(0));
                        list.set(list.size() - 1, copyList.get(0));
//                        System.out.println("@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!depreciation copylist: "+copyList.get(0).getPayable());
//                        System.out.println("@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!depreciation list: "+list.get(list.size()-1).getPayable());
//                        System.out.println("@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!depreciation result list: "+resultList.get(i).getPayable());
                        e.setPayable(copyData);
//                        System.out.println("@@@@@@@@@@!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!depreciation copylist after setting back resultList: "+copyList.get(0).getPayable());
                    } else {
                        System.out.println("FTB:calculateNoDateExpense: There is no depreciation during this period.");
                    }
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft is deleted.");
                }

            } else if (cate.equals("Other Cost")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////check whether this Other Cost can be presented
                String routeId = resultList.get(i).getCostSource();
                Long id = Long.valueOf(routeId);
                Route checkRoute = em.find(Route.class, id);
                if (checkRoute != null) {
                    originalList.add(e);
                    Double payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        originalList.get(0).setPayable(payable / dayOfYear * diff);
                    } else {
                        originalList.get(0).setPayable(payable / 4);
                    }
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this route is deleted.");
                }

            } else if (cate.equals("Fuel Cost")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Fuel Cost can be presented
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (!typeList.isEmpty()) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    originalList.get(0).setPayable(payable * fsb.calPeriodTotalFlightHour(startDate, endDate));
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type is deleted.");
                }

            } else if (cate.equals("Maintenance Cost")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Fuel Cost can be presented
                String type = resultList.get(i).getCostSource();
                Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
                List<AircraftType> typeList = (List) q1.getResultList();
                if (!typeList.isEmpty()) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    payable = payable * fsb.calPeriodTotalMtManHour(startDate, endDate);
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this aircraft type is deleted.");
                }

            } else if (cate.equals("Cabin Leader")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Cabin Leader can be presented
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                    } else {
                        payable = payable * 3 + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
                }
            } else if (cate.equals("Cabin Crew")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Cabin Crew can be presented
                String name = resultList.get(i).getCostSource();
                CabinCrew cabinCrew = em.find(CabinCrew.class, name);
                if (cabinCrew != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff + csb.calCabinCrewTotalHourPay(startDate, endDate);
                    } else {
                        payable = payable * 3 + csb.calCabinCrewTotalHourPay(startDate, endDate);
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
                }
            } else if (cate.equals("Captain")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Captain can be presented
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff + csb.calCaptainTotalHourPay(startDate, endDate);
                    } else {
                        payable = payable * 3 + csb.calCaptainTotalHourPay(startDate, endDate);
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this captain is deleted.");
                }
            } else if (cate.equals("Pilot")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Pilot can be presented
                String name = resultList.get(i).getCostSource();
                CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
                if (cockpitCrew != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff + csb.calPilotTotalHourPay(startDate, endDate);
                    } else {
                        payable = payable * 3 + csb.calPilotTotalHourPay(startDate, endDate);
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this pilot is deleted.");
                }
            } else if (cate.equals("Ground Staff")) {
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Ground Staff can be presented
                String name = resultList.get(i).getCostSource();
                GroundStaff groundStaff = em.find(GroundStaff.class, name);
                if (groundStaff != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff;
                    } else {
                        payable = payable * 3;
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this ground staff is deleted.");
                }
            } else {
                //office staff
                Expense e = new Expense();
                e = resultList.get(i);
                Double copyData = e.getPayable();
                ////////////////////check whether this Office Staff can be presented
                String name = resultList.get(i).getCostSource();
                OfficeStaff officeStaff = em.find(OfficeStaff.class, name);
                if (officeStaff != null) {
                    originalList.add(e);
                    Double payable = 0.0;
                    payable = e.getPayable();
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                        payable = payable * 3 / dayDiff * diff;
                    } else {
                        payable = payable * 3;
                    }
                    originalList.get(0).setPayable(payable);
                    list.add(originalList.get(0));
                    e.setPayable(copyData);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: this office staff is deleted.");
                }
            }

        }
        return list;
    }

    @Override
    public Double getUnitExpense(Long id, String category, long year, String quarter) {
        Double payable = 0.0;
        expense = em.find(Expense.class, id);
        int expenseYear;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Boolean inPeriod = false;//set default
        Calendar cal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

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

        Date currentDate = new Date();
        int dayOfYear = startCal.get(Calendar.DAY_OF_YEAR);   //how many days in the selected year

        if (category.equals("Purchase Aircraft") || category.equals("DDS Commission")) {
            Date paymentDate = expense.getPaymentDate();
            cal.setTime(paymentDate);
            expenseYear = cal.get(Calendar.YEAR);
            inPeriod = !(paymentDate.before(startDate) || paymentDate.after(endDate));
            if (expenseYear == year && inPeriod) {
                payable = expense.getPayable();
                System.out.println("FTB:calculateExpense: " + category + " with single payable: " + payable);
            }
        } else if (category.equals("Depreciation")) {
            String registrationNo = expense.getCostSource();
            Query q1 = em.createQuery("SELECT a FROM Aircraft a where a.registrationNo=:registrationNo").setParameter("registrationNo", registrationNo);
            List<Aircraft> aircraftList = (List) q1.getResultList();
            Date checkDate = new Date();
            if (!aircraftList.isEmpty()) {
                try {
                    checkDate = format.parse(aircraftList.get(0).getDeliveryDate());    //check the aircraft has delivered?
                } catch (ParseException ex) {
                    Logger.getLogger(FinancialTrackingBean.class.getName()).log(Level.SEVERE, "FTB:calculateNoDateExpense: Cannot get aircraft delivery date", ex);
                }
                if (!checkDate.after(endDate)) {
                    //Straight Line Depreciation
                    if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                        long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                        payable = expense.getPayable() / dayOfYear * diff;
                    } else {
                        payable = expense.getPayable() / 4;
                    }
                    System.out.println("FTB:calculateNoDateExpense: Depreciation Cost: unit cost: " + payable);
                } else {
                    System.out.println("FTB:calculateNoDateExpense: There is no depreciation during this period as the aircraft has not been delivered.");
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this aircraft is deleted.");
            }
        } else if (category.equals("Fuel Cost")) {
            String type = expense.getCostSource();
            Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
            List<AircraftType> typeList = (List) q1.getResultList();
            if (!typeList.isEmpty()) {
                payable = (expense.getPayable()) * (fsb.calPeriodTotalFlightHour(startDate, endDate));
            } else {
                System.out.println("FTB:calculateNoDateExpense: this aircraft type  is deleted.");
            }
            System.out.println("FTB:calculateNoDateExpense: Fuel Cost unit cost: " + payable);
        } else if (category.equals("Maintenance Cost")) {
            String type = expense.getCostSource();
            Query q1 = em.createQuery("SELECT a FROM AircraftType a where a.type=:type").setParameter("type", type);
            List<AircraftType> typeList = (List) q1.getResultList();
            if (!typeList.isEmpty()) {
                payable = expense.getPayable() * fsb.calPeriodTotalMtManHour(startDate, endDate);
            } else {
                System.out.println("FTB:calculateNoDateExpense: this aircraft type is deleted.");
            }
            System.out.println("FTB:calculateNoDateExpense: Maintenance Cost unit cost: " + payable);
        } else if (category.equals("Other Cost")) {
            String routeId = expense.getCostSource();
            Long idLong = Long.valueOf(routeId);
            Route checkRoute = em.find(Route.class, idLong);
            if (checkRoute != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    payable = expense.getPayable() / dayOfYear * diff;
                } else {
                    payable = expense.getPayable() / 4;
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this route is deleted.");
            }
            System.out.println("FTB:calculateNoDateExpense: Other Cost unit cost: " + payable);
        } else if (category.equals("Cabin Leader")) {
            String name = expense.getCostSource();
            CabinCrew cabinCrew = em.find(CabinCrew.class, name);
            if (cabinCrew != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                } else {
                    payable = expense.getPayable() * 3 + csb.calCabinLeaderTotalHourPay(startDate, endDate);
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
            }
        } else if (category.equals("Cabin Crew")) {
            String name = expense.getCostSource();
            CabinCrew cabinCrew = em.find(CabinCrew.class, name);
            if (cabinCrew != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff + csb.calCabinCrewTotalHourPay(startDate, endDate);
                } else {
                    payable = expense.getPayable() * 3 + csb.calCabinCrewTotalHourPay(startDate, endDate);
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this cabin crew is deleted.");
            }
        } else if (category.equals("Captain")) {
            String name = expense.getCostSource();
            CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
            if (cockpitCrew != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff + csb.calCaptainTotalHourPay(startDate, endDate);
                } else {
                    payable = expense.getPayable() * 3 + csb.calCaptainTotalHourPay(startDate, endDate);
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this captain is deleted.");
            }
        } else if (category.equals("Pilot")) {
            String name = expense.getCostSource();
            CockpitCrew cockpitCrew = em.find(CockpitCrew.class, name);
            if (cockpitCrew != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff + csb.calPilotTotalHourPay(startDate, endDate);
                } else {
                    payable = expense.getPayable() * 3 + csb.calPilotTotalHourPay(startDate, endDate);
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this pilot is deleted.");
            }
        } else if (category.equals("Ground Staff")) {
            String name = expense.getCostSource();
            GroundStaff groundStaff = em.find(GroundStaff.class, name);
            if (groundStaff != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff;
                } else {
                    payable = expense.getPayable() * 3;
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this ground staff is deleted.");
            }
        } else {
            //office staff
            String name = expense.getCostSource();
            OfficeStaff officeStaff = em.find(OfficeStaff.class, name);
            if (officeStaff != null) {
                if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
                    long diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                    long dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
                    payable = expense.getPayable() * 3 / dayDiff * diff;
                } else {
                    payable = expense.getPayable() * 3;
                }
            } else {
                System.out.println("FTB:calculateNoDateExpense: this office staff is deleted.");
            }
        }
        return payable;
    }

    //////////////////////////
    @Override
    public double getUnitOfYear(long year, String quarter) {
        Double unit = 0.0;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Date currentDate = new Date();
        double diff;

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

        int dayOfYear = startCal.get(Calendar.DAY_OF_YEAR);   //how many days in the selected year
        if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
            diff = (double) (currentDate.getTime() - startDate.getTime()) / (double) (24 * 60 * 60 * 1000);            //how many days have passed in the selected quarter
            unit = new Double(diff / (double) dayOfYear);
        } else {
            unit = 0.25;
        }

        return unit;
    }

    @Override
    public double getUnitOfMonth(long year, String quarter) {
        Double unit = 0.0;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        Calendar cal = Calendar.getInstance();
        Date currentDate = new Date();
        double diff;

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
        if (!currentDate.before(startDate) && !currentDate.after(endDate)) {
            diff = (currentDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
            double dayDiff = (endDate.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);   // how many days within the selected quarter
            unit = new Double(diff / (dayDiff / 3.0));
        } else {
            unit = 3.0;
        }

        return unit;
    }

}
