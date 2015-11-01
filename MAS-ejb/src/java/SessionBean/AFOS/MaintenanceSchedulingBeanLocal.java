/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.Maintenance;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface MaintenanceSchedulingBeanLocal {

    public void addMaintenanceLog(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew);

    public void editMaintenanceLog(Maintenance mt, String aircraft, String acType, String objective, Date startTime, Date endTime, Integer manhour, String activity, String remark, String mtCrew);
    
}
