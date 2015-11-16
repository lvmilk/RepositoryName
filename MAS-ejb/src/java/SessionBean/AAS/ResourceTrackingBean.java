/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AFOS.StaffLeave;
import javax.ejb.Stateless;
import Entity.CommonInfa.CockpitCrew;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.GroundStaff;
import Entity.CommonInfa.OfficeStaff;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    public ResourceTrackingBean() {
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

}
