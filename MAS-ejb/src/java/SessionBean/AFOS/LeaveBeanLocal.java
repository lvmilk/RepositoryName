/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.StaffLeave;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author wang
 */
@Local
public interface LeaveBeanLocal {

    public void addLeave(Date startDate, String userName) throws Exception;

    public void approveLeave(StaffLeave staffleave) throws Exception;

    public void rejectLeave(StaffLeave staffleave) throws Exception;

    public List<StaffLeave> getOneStaffLeaves(String userName) throws Exception;

    public List<StaffLeave> getAllNotReviewedLeaves() throws Exception;

    public List<StaffLeave> getAllLeaves() throws Exception;
     public void addNormalLeave (Date startDate, Date endDate, String userName)throws Exception;

    public String getStaffLevel(String userName) throws Exception;
}
