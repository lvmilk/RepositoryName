/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AAS;

import Entity.AFOS.StaffLeave;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xi
 */
@Local
public interface ResourceTrackingBeanLocal {

    public List<StaffLeave> getAllLeave(Date start, Date end);
    
}
