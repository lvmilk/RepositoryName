/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SessionBean.AFOS;

import Entity.AFOS.DebriefingReport;
import Entity.APS.FlightInstance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Xu
 */
@Local
public interface FlightCycleBeanLocal {

    public void addDebriefingReport(FlightInstance fi, String captainId, String flightNo, String acReg, String acType, String origin, String dest, String depTimeString, String arrTimeString, String issueCategory, String issue, String remark) throws Exception;

    public List<DebriefingReport> getAllDR();

    public DebriefingReport getDebriefingReport(FlightInstance fi) throws Exception;

    public boolean hasDebriefingReport(FlightInstance fi);
    
}
