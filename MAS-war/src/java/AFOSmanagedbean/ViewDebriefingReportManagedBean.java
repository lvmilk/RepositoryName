/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.DebriefingReport;
import SessionBean.AFOS.FlightCycleBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VDRMB")
@ViewScoped
public class ViewDebriefingReportManagedBean implements Serializable {

    @EJB
    FlightCycleBeanLocal fcb;

    private List<String> issueCats = new ArrayList<>();
    private List<DebriefingReport> drList = new ArrayList<>();

    public ViewDebriefingReportManagedBean() {
    }

    @PostConstruct
    public void init() {
        drList = fcb.getAllDR();
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

    public void viewDRDetail(DebriefingReport dr) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("drToView", dr);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewDebriefingReportDetail.xhtml");
    }

    public List<DebriefingReport> getDrList() {
        return drList;
    }

    public void setDrList(List<DebriefingReport> drList) {
        this.drList = drList;
    }

    public List<String> getIssueCats() {
        return issueCats;
    }

    public void setIssueCats(List<String> issueCats) {
        this.issueCats = issueCats;
    }

}
