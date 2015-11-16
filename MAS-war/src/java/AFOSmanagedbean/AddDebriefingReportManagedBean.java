/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.AFOS.FlightCycleBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "ADRMB")
@ViewScoped
public class AddDebriefingReportManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    @EJB
    FlightCycleBeanLocal fcb;

    private FlightInstance fi;
    private String flightNo;
    private String acReg;
    private String acType;
    private String captainId;
    private List<CockpitCrew> captain;
    private String depTimeString;
    private String arrTimeString;
    private String origin;
    private String dest;
    private String issueCategory;
    private String issue;
    private String remark;

    private List<String> issueCats = new ArrayList<>();

    public AddDebriefingReportManagedBean() {
    }

    @PostConstruct
    public void init() {
        fi = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFi");
        flightNo = fi.getFlightFrequency().getFlightNo();
        acReg = fi.getAircraft().getRegistrationNo();
        acType = fi.getAircraft().getAircraftType().getType();
        captain = csb.getFiCaptain(fi);
        captainId = captain.isEmpty() ? "" : captain.get(0).getCpName();
        origin = fi.getFlightFrequency().getRoute().getOrigin().toString();
        dest = fi.getFlightFrequency().getRoute().getDest().toString();
        depTimeString = fi.getActualDepTime();
        arrTimeString = fi.getActualArrTime();
        initIssueCats();
    }

    public void initIssueCats() {
        issueCats.add("Mechanical failure");
        issueCats.add("Fuel dumping");
        issueCats.add("Injury of crew");
        issueCats.add("Injury of passenger");
        issueCats.add("Passenger misconduct");
        issueCats.add("Overweight landing");
        issueCats.add("HAZMAT issue");
        issueCats.add("Diversion");
        issueCats.add("High-speed abort");
        issueCats.add("Lightning strike");
        issueCats.add("Near midair collision");
        issueCats.add("Other");
    }

    public void addDebriefingReport() throws IOException {
        try {
            fcb.addDebriefingReport(fi, captainId, flightNo, acReg, acType, origin, dest, depTimeString, arrTimeString, issueCategory, issue, remark);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("debriefFlightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("debriefFlightDate", fi.getDate());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addDebriefingReportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public FlightInstance getFi() {
        return fi;
    }

    public void setFi(FlightInstance fi) {
        this.fi = fi;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getAcReg() {
        return acReg;
    }

    public void setAcReg(String acReg) {
        this.acReg = acReg;
    }

    public String getAcType() {
        return acType;
    }

    public void setAcType(String acType) {
        this.acType = acType;
    }

    public String getCaptainId() {
        return captainId;
    }

    public void setCaptainId(String captainId) {
        this.captainId = captainId;
    }

    public List<CockpitCrew> getCaptain() {
        return captain;
    }

    public void setCaptain(List<CockpitCrew> captain) {
        this.captain = captain;
    }

    public String getDepTimeString() {
        return depTimeString;
    }

    public void setDepTimeString(String depTimeString) {
        this.depTimeString = depTimeString;
    }

    public String getArrTimeString() {
        return arrTimeString;
    }

    public void setArrTimeString(String arrTimeString) {
        this.arrTimeString = arrTimeString;
    }

    public String getIssueCategory() {
        return issueCategory;
    }

    public void setIssueCategory(String issueCategory) {
        this.issueCategory = issueCategory;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<String> getIssueCats() {
        return issueCats;
    }

    public void setIssueCats(List<String> issueCats) {
        this.issueCats = issueCats;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

}
