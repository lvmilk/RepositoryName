/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AAS.Payroll;
import Entity.AFOS.StaffLeave;
import javax.ejb.Stateless;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import Entity.CommonInfa.UserEntity;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xi
 */
@Stateless
public class ResourceTrackingBean implements ResourceTrackingBeanLocal {

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @PersistenceContext
    EntityManager em;
    OfficeStaff staff;
    GroundStaff gdStaff;
    CabinCrew cbCrew;
    CockpitCrew cpCrew;

    @EJB
    CrewSchedulingBeanLocal csb;

    public ResourceTrackingBean() {
    }

    @Override
    public String getUserName(String userName) {
        String name;
        if (userName.equals("admin")) {
            name = "admin";
        } else {
            Query query = em.createQuery("SELECT u FROM UserEntity u where u.username=:userName");
            query.setParameter("userName", userName);
            List<UserEntity> resultList = query.getResultList();
            name = resultList.get(0).getUsername();
        }
        return name;
    }

    @Override
    public List<StaffLeave> getAllLeave(Date start, Date end) {
        Query query = em.createQuery("SELECT l FROM StaffLeave l ");
        List<StaffLeave> resultList = (List) query.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:RTB: StaffLeave List is empty");
        } else {
            System.out.println("AAS:RTB: StaffLeave List data exists");
        }
        List<StaffLeave> list = new ArrayList<>();
        for (int i = 0; i < resultList.size(); i++) {
            Date startDate = resultList.get(i).getStartDate();
            Date endDate = resultList.get(i).getEndDate();
            String status = resultList.get(i).getStatus();

            if (status.equals("Approved")) {
                if ((!startDate.before(start) && !endDate.after(end)) || (startDate.before(start) && !endDate.before(start) && !endDate.after(end)) || (!startDate.before(start) && !startDate.after(end) && endDate.after(end))) {
                    list.add(resultList.get(i));
                }
            }
        }
        System.out.println("AAS:RTB:getAllLeave: list is " + list);
        return list;
    }

    @Override
    public List<Payroll> getAllPayroll() {
        Query q = em.createQuery("SELECT p FROM Payroll p");
        List<Payroll> resultList = (List) q.getResultList();
        if (resultList.isEmpty()) {
            System.out.println("AAS:RTB: Payroll List is empty");
        } else {
            System.out.println("AAS:RTB: Payroll List data exists");
        }
        return resultList;
    }

    @Override
    public Payroll getOnePayroll(String name) throws Exception {
        Query q = em.createQuery("SELECT p FROM Payroll p where p.name=:name");
        q.setParameter("name", name);
        List<Payroll> resultList = (List) q.getResultList();
        if (resultList.isEmpty()) {
            throw new Exception("The entered staff does not exist!");
        } else {
            System.out.println("AAS:RTB: Payroll List data exists");
        }
        return resultList.get(0);
    }

    @Override
    public Integer getLeaveAmount(String name, long year, int month) {
        Integer amount = 0;
        String status = "Approved";
        List<StaffLeave> resultList = new ArrayList<>();
        List<StaffLeave> list = new ArrayList<>();
        Query q = em.createQuery("SELECT l FROM StaffLeave l where l.status=:status ");
        q.setParameter("status", status);
        resultList = (List) q.getResultList();
        for (int i = 0; i < resultList.size(); i++) {
            StaffLeave thisleave = resultList.get(i);
            if (thisleave.getCabinCrew() != null) {
                if (thisleave.getCabinCrew().getCbName().equals(name)) {
                    list.add(thisleave);
                }
            } else if (thisleave.getCockpitCrew() != null) {
                if (thisleave.getCockpitCrew().getCpName().equals(name)) {
                    list.add(thisleave);
                }
            } else if (thisleave.getGroundStaff() != null) {
                if (thisleave.getGroundStaff().getGrdName().equals(name)) {
                    list.add(thisleave);
                }
            } else if (thisleave.getOfficeStaff() != null) {
                if (thisleave.getOfficeStaff().getOffName().equals(name)) {
                    list.add(thisleave);
                }
            } else {
                System.out.println("AAS:RTB: There is no leave record for this staff");
            }
        }
        System.out.println("cccccccccccccheck leave list: "+list);
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Calendar leaveEndCal = Calendar.getInstance();
        Calendar leaveStartCal = Calendar.getInstance();
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();

        for (int i = 0; i < list.size(); i++) {
            Date leaveStart = list.get(i).getStartDate();
            Date leaveEnd = list.get(i).getEndDate();
            leaveStartCal.setTime(leaveStart);
            leaveEndCal.setTime(leaveEnd);

            switch (month) {
                case 1: {
                    startCal.set((int) year, 0, 1);
                    endCal.set((int) year, 0, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 2: {
                    startCal.set((int) year, 1, 1);
                    if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                        endCal.set((int) year, 1, 28);
                    } else {
                        endCal.set((int) year, 1, 29);
                    }
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 3: {
                    startCal.set((int) year, 2, 1);
                    endCal.set((int) year, 2, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 4: {
                    startCal.set((int) year, 3, 1);
                    endCal.set((int) year, 3, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 5: {
                    startCal.set((int) year, 4, 1);
                    endCal.set((int) year, 4, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 6: {
                    startCal.set((int) year, 5, 1);
                    endCal.set((int) year, 5, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 7: {
                    startCal.set((int) year, 6, 1);
                    endCal.set((int) year, 6, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 8: {
                    startCal.set((int) year, 7, 1);
                    endCal.set((int) year, 7, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 9: {
                    startCal.set((int) year, 8, 1);
                    endCal.set((int) year, 8, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 10: {
                    startCal.set((int) year, 9, 1);
                    endCal.set((int) year, 9, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 11: {
                    startCal.set((int) year, 10, 1);
                    endCal.set((int) year, 10, 30);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                case 12: {
                    startCal.set((int) year, 11, 1);
                    endCal.set((int) year, 11, 31);
                    startDate = startCal.getTime();
                    endDate = endCal.getTime();
                    break;
                }
                default:
                    System.out.println("AAS:RTB: Invalid month input: " + month);
                    break;
            }
            if(endDate.after(new Date())){
                endDate = new Date();
            }
            /////leave period within the selected month
            if (!leaveStart.before(startDate) && !leaveEnd.after(endDate)) {
                amount = amount + list.get(i).getLength();
            } else if (leaveStart.after(startDate) && !leaveStart.after(endDate) && leaveEnd.after(endDate)) {
                long diff = (endDate.getTime() - leaveStart.getTime()) / (24 * 60 * 60 * 1000);
                amount = amount  + (int) diff;
            } else if (leaveStart.before(startDate) && !leaveEnd.before(startDate) && leaveEnd.before(endDate)) {
                long diff = (leaveEnd.getTime() - startDate.getTime()) / (24 * 60 * 60 * 1000);
                amount = amount + (int) diff;
            } else {
                System.out.println("AAS:RTB: No leave in this period: year " + year + " month " + month);
            }
        }
        return amount;
    }

    @Override
    public double getTotalBonus(String name, long year, int month) {
        Double amount = 0.0;
        Date startDate = new Date(); //set default 
        Date endDate = new Date();//set default
        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        switch (month) {
            case 1: {
                startCal.set((int) year, 0, 1);
                endCal.set((int) year, 0, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 2: {
                startCal.set((int) year, 1, 1);
                if ((year % 400 == 0) || ((year % 4 == 0) && (year % 100 != 0))) {
                    endCal.set((int) year, 1, 28);
                } else {
                    endCal.set((int) year, 1, 29);
                }
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 3: {
                startCal.set((int) year, 2, 1);
                endCal.set((int) year, 2, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 4: {
                startCal.set((int) year, 3, 1);
                endCal.set((int) year, 3, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 5: {
                startCal.set((int) year, 4, 1);
                endCal.set((int) year, 4, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 6: {
                startCal.set((int) year, 5, 1);
                endCal.set((int) year, 5, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 7: {
                startCal.set((int) year, 6, 1);
                endCal.set((int) year, 6, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 8: {
                startCal.set((int) year, 7, 1);
                endCal.set((int) year, 7, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 9: {
                startCal.set((int) year, 8, 1);
                endCal.set((int) year, 8, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 10: {
                startCal.set((int) year, 9, 1);
                endCal.set((int) year, 9, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 11: {
                startCal.set((int) year, 10, 1);
                endCal.set((int) year, 10, 30);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            case 12: {
                startCal.set((int) year, 11, 1);
                endCal.set((int) year, 11, 31);
                startDate = startCal.getTime();
                endDate = endCal.getTime();
                break;
            }
            default:
                System.out.println("AAS:RTB: Invalid month input: " + month);
                break;
        }

        List<Payroll> list = new ArrayList<>();
        Query q = em.createQuery("SELECT p FROM Payroll p where p.name=:name");
        q.setParameter("name", name);
        list = (List) q.getResultList();
        String staffId = list.get(0).getName();

        CabinCrew cabin = em.find(CabinCrew.class, staffId);
        if (cabin != null) {
            if (cabin.getStfLevel().equals("Cabin Leader")) {
                amount = csb.calCabinLeaderTotalHourPay(startDate, endDate);
//                System.out.println("11111111111111111111111"+amount);
            } else if (cabin.getStfLevel().equals("Cabin Crew")) {
                amount = csb.calCabinCrewTotalHourPay(startDate, endDate);
//                System.out.println("2222222222222222222222"+amount);
            } else {
                amount = 0.0;
                System.out.println("AAS:RTB: getTotalBonus: cannot identify the cabin crew");
            }
        }

        CockpitCrew cockpit = em.find(CockpitCrew.class, staffId);
        if (cockpit != null) {
            if (cockpit.getStfLevel().equals("Captain")) {
                amount = csb.calCaptainTotalHourPay(startDate, endDate);
//                System.out.println("3333333333333333333333333"+amount);
            } else if (cockpit.getStfLevel().equals("Pilot")) {
                amount = csb.calPilotTotalHourPay(startDate, endDate);
//                System.out.println("4444444444444444444444444"+amount);
            } else {
                amount = 0.0;
                System.out.println("AAS:RTB: getTotalBonus: cannot identify the cockpit crew");
            }
        }
        return amount;
    }

}
