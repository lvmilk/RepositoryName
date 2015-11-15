/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.DebriefingReport;
import Entity.APS.FlightInstance;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Xu
 */
@Stateless
public class FlightCycleBean implements FlightCycleBeanLocal {

    @PersistenceContext
    EntityManager em;

    @Override
    public void addDebriefingReport(FlightInstance fi1, String captainId, String flightNo, String acReg, String acType, String origin, String dest,
            String depTimeString, String arrTimeString, String issueCategory, String issue, String remark) throws Exception {
        FlightInstance fi = em.find(FlightInstance.class, fi1.getId());
        if (fi.getDebrief() != null) {
            throw new Exception("Debriefing report has already been added for this flight.");
        }
        Date fiDate = fi.getStandardDepTimeDateType();
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        Date dep = formatter.parse(depTimeString);
        DebriefingReport dr = new DebriefingReport();
        dr.create(captainId, fiDate, flightNo, acReg, acType, origin, dest, depTimeString, arrTimeString, issueCategory, issue, remark);
        dr.setFi(fi);
        em.persist(dr);
        em.flush();
        fi.setDebrief(dr);
        em.merge(fi);
        em.flush();
    }

    @Override
    public List<DebriefingReport> getAllDR() {
        Query q1 = em.createQuery("Select d from DebriefingReport d");
        return q1.getResultList();
    }

    @Override
    public DebriefingReport getDebriefingReport(FlightInstance fi) throws Exception {
        Query q1 = em.createQuery("Select d from DebriefingReport d where d.fi=:fi").setParameter("fi", fi);
        if (q1.getResultList().isEmpty()) {
            throw new Exception("No debriefing report has been entered for this flight.");
        }
        return (DebriefingReport) q1.getResultList().get(0);
    }

    @Override
    public boolean hasDebriefingReport(FlightInstance fi) {
        Query q1 = em.createQuery("Select d from DebriefingReport d where d.fi=:fi").setParameter("fi", fi);
        return !q1.getResultList().isEmpty();
    }

}
