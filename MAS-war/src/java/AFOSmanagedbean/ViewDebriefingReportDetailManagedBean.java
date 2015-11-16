/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.DebriefingReport;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VDRDMB")
@ViewScoped
public class ViewDebriefingReportDetailManagedBean implements Serializable {

    private DebriefingReport dr;

    public ViewDebriefingReportDetailManagedBean() {
    }

    @PostConstruct
    public void init() {
        dr = (DebriefingReport) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("drToView");
    }

    public void viewDRBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewDebriefingReport.xhtml");
    }

    public DebriefingReport getDr() {
        return dr;
    }

    public void setDr(DebriefingReport dr) {
        this.dr = dr;
    }

}
