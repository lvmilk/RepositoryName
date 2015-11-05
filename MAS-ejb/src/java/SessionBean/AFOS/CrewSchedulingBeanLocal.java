/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import java.text.ParseException;
import java.util.Date;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface CrewSchedulingBeanLocal {

    public void scheduleFlightCrew(Date startDate, Date endDate) throws ParseException;
    
}
