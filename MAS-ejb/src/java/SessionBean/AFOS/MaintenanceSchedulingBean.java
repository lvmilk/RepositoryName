/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.Maintenance;
import Entity.AFOS.MaintenanceLog;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Xu
 */
@Stateless
public class MaintenanceSchedulingBean implements MaintenanceSchedulingBeanLocal {

    @PersistenceContext
    EntityManager em;

    private MaintenanceLog log;

    @Override
    public void addMaintenanceLog(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew) {
//        em.refresh(mt);
//        if(mt.getLog()!=null) {}
        log = new MaintenanceLog();
        log.create(mt, aircraft, acType, objective, startTime, endTime, manhour, activity, remark, mtCrew);
        em.persist(log);
        mt.setStatus("Completed");
        mt.setLog(log);
        em.merge(mt);
        em.flush();
        System.out.println("msb.addMaintenanceLog(): Maintenance Log for " + aircraft + " " + objective + "  " + startTime + " - " + endTime + " added!");
    }

}
