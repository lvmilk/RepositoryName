/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.StaffLeave;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author wang
 */
@Stateless
public class LeaveBean implements LeaveBeanLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void addLeave(Date startDate, String userName) throws Exception {
        if (startDate == null) {
            throw new Exception("Date Invalid");
        } else {
            System.out.println("Leavebean:Detecte staff! " + userName);
            StaffLeave staffLeave = new StaffLeave();
            if (userName.charAt(0) == 'G') {
                System.out.println("Leavebean:Detecte as ground staff! ");
                Query query = em.createQuery("SELECT g FROM GroundStaff g where g.grdName=:gname");
                query.setParameter("gname", userName);
                if (query.getResultList().isEmpty()) {
                    throw new Exception("Username Invalid");
                } else {
                    GroundStaff gStaff = (GroundStaff) query.getSingleResult();
                    staffLeave.setGroundStaff(gStaff);
                    staffLeave.setStartDate(startDate);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(startDate);
                    c1.add(Calendar.DATE, 7);
                    Date endDate = c1.getTime();
                    staffLeave.setEndDate(endDate);
                    staffLeave.setLength(7);
                    staffLeave.setStaffType("GroundStaff");
                    staffLeave.setStatus("Not reviewed");
                    staffLeave.setUserName(userName);
                    em.persist(staffLeave);
                    em.flush();
                    List<StaffLeave> newList = gStaff.getLeaves();
                    newList.add(staffLeave);
                    gStaff.setLeaves(newList);
                    em.merge(gStaff);
                    em.flush();

                }

            } else if (userName.subSequence(0, 1).equals("CP")) {
                System.out.println("Leavebean:Detecte as CockpitCrew! ");
                Query query = em.createQuery("SELECT c FROM CockpitCrew c  where c.cpName =:cpname");
                query.setParameter("cpname", userName);
                if (query.getResultList().isEmpty()) {
                    throw new Exception("Username Invalid");
                } else {
                    CockpitCrew cpCrew = (CockpitCrew) query.getSingleResult();
                    staffLeave.setCockpitCrew(cpCrew);
                    staffLeave.setStartDate(startDate);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(startDate);
                    c1.add(Calendar.DATE, 7);
                    Date endDate = c1.getTime();
                    staffLeave.setEndDate(endDate);
                    staffLeave.setLength(7);
                    staffLeave.setStaffType("CockpitCrew");
                    staffLeave.setStatus("Not reviewed");
                    staffLeave.setUserName(userName);
                    em.persist(staffLeave);
                    em.flush();
                    List<StaffLeave> newList = cpCrew.getLeaves();
                    newList.add(staffLeave);
                    cpCrew.setLeaves(newList);
                    em.merge(cpCrew);
                    em.flush();

                }
            } else if (userName.subSequence(0, 1).equals("CB")) {
                System.out.println("Leavebean:Detecte as CabinCrew! ");
                Query query = em.createQuery("SELECT c FROM CabinCrew c where c.cbName =:cbname");
                query.setParameter("cbname", userName);
                if (query.getResultList().isEmpty()) {
                    throw new Exception("Username Invalid");
                } else {
                    CabinCrew cbCrew = (CabinCrew) query.getSingleResult();
                    staffLeave.setCabinCrew(cbCrew);
                    staffLeave.setStartDate(startDate);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(startDate);
                    c1.add(Calendar.DATE, 7);
                    Date endDate = c1.getTime();
                    staffLeave.setEndDate(endDate);
                    staffLeave.setLength(7);
                    staffLeave.setStaffType("CabinCrew");
                    staffLeave.setStatus("Not reviewed");
                    staffLeave.setUserName(userName);
                    em.persist(staffLeave);
                    em.flush();

                    List<StaffLeave> newList = cbCrew.getLeaves();
                    newList.add(staffLeave);
                    cbCrew.setLeaves(newList);
                    em.merge(cbCrew);
                    em.flush();

                }

            } else if (userName.charAt(0) == 'O') {
                System.out.println("Leavebean:Detect as OfficeStaff! ");
                Query query = em.createQuery("SELECT o FROM OfficeStaff o  where o.offName =:oname");
                query.setParameter("oname", userName);
                if (query.getResultList().isEmpty()) {
                    throw new Exception("Username Invalid");
                } else {

                    OfficeStaff oStaff = (OfficeStaff) query.getSingleResult();
                    System.out.println("Leavebean:Detect as OfficeStaff is " + oStaff.getFirstName());
                    staffLeave.setStartDate(startDate);
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(startDate);
                    c1.add(Calendar.DATE, 7);
                    Date endDate = c1.getTime();
                    staffLeave.setEndDate(endDate);
                    staffLeave.setLength(7);
                    staffLeave.setStaffType("OfficeStaff");
                    staffLeave.setStatus("Not reviewed");
                    staffLeave.setUserName(userName);
                    em.persist(staffLeave);
                    em.flush();
                    List<StaffLeave> newList = oStaff.getLeaves();
                    newList.add(staffLeave);
                    oStaff.setLeaves(newList);
                    em.merge(oStaff);
                    em.flush();
                    staffLeave.setOfficeStaff(oStaff);
                    em.merge(staffLeave);
                    em.flush();

                }

            } else {
                throw new Exception("Username Invalid");
            }

        }

    }

    @Override
    public void approveLeave(StaffLeave staffleave) throws Exception {
        if (em.find(StaffLeave.class, staffleave.getId()) != null) {
            staffleave.setStatus("Approved");
            em.merge(staffleave);
        } else {
            throw new Exception("Leave Application Not Found");
        }
    }

    @Override
    public void rejectLeave(StaffLeave staffleave) throws Exception {
        if (em.find(StaffLeave.class, staffleave.getId()) != null) {
            staffleave.setCabinCrew(null);
            staffleave.setCockpitCrew(null);
            staffleave.setGroundStaff(null);
            staffleave.setOfficeStaff(null);
            staffleave.setRemark("Deleted");
            em.merge(staffleave);
            // em.remove(staffleave);
        } else {
            throw new Exception("Leave Application Not Found");
        }
    }

    public List<StaffLeave> getAllLeaves() throws Exception {
        Query query = em.createQuery("SELECT l FROM StaffLeave l");
        if (query.getResultList().isEmpty()) {
            throw new Exception("Leave Not Found");
        } else {
            List<StaffLeave> newList = new ArrayList<>();
            for (StaffLeave temp : (List<StaffLeave>) query.getResultList()) {
                if (!temp.getRemark().equals("Deleted")) {
                    newList.add(temp);
                }
            }
            return newList;
        }
    }

    public List<StaffLeave> getAllNotReviewedLeaves() throws Exception {
        Query query = em.createQuery("SELECT l FROM StaffLeave l where l.status=:lstatus");
        query.setParameter("lstatus", "Not reviewed");

        if (query.getResultList().isEmpty()) {
            throw new Exception("Leave Not Found");
        } else {
            return (List<StaffLeave>) query.getResultList();
        }
    }

    @Override
    public List<StaffLeave> getOneStaffLeaves(String userName) throws Exception {
        List<StaffLeave> allLeaves = this.getAllLeaves();
        List<StaffLeave> oneStaffLeaves = new ArrayList<>();
        for (StaffLeave temp : allLeaves) {
            if (temp.getUserName().equals(userName)) {
                oneStaffLeaves.add(temp);
            }
        }

        if (oneStaffLeaves.isEmpty()) {
            throw new Exception("Leave Not Found");
        } else {
            return oneStaffLeaves;
        }
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
