/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.FlightFrequency;
import SessionBean.APS.FlightSchedulingBeanLocal;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Xu
 */
@Named(value = "DFFMB")
@ViewScoped
public class DeleteFlightFrequencyManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    FlightFrequency flightFreq;
    List<FlightFrequency> canDeleteFlightFreq = new ArrayList<>();
    List<FlightFrequency> selectedFlightFreq = new ArrayList<>();

    public DeleteFlightFrequencyManagedBean() {
    }

    @PostConstruct
    public void init() {
        canDeleteFlightFreq = fsb.canDeleteFlightFreqList();
    }

    public void toDeleteFlightFreq() throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedFlightFreq.isEmpty()) {
            context.execute("alert('Please select the flight frequency/ies to be deleted.');");
        } else {
            context.execute("PF('dlg').show();");
        }
    }

    public void confirmDeleteFlightFreq() throws Exception {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedFlightFreq", selectedFlightFreq);
            fsb.deleteFlightFreqList(selectedFlightFreq);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteFlightFrequencySuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void deleteFlightFreqBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteFlightFrequency.xhtml");
    }

    public FlightFrequency getFlightFreq() {
        return flightFreq;
    }

    public void setFlightFreq(FlightFrequency flightFreq) {
        this.flightFreq = flightFreq;
    }

    public List<FlightFrequency> getCanDeleteFlightFreq() {
        return fsb.canDeleteFlightFreqList();
    }

    public void setCanDeleteFlightFreq(List<FlightFrequency> canDeleteFlightFreq) {
        this.canDeleteFlightFreq = canDeleteFlightFreq;
    }

    public List<FlightFrequency> getSelectedFlightFreq() {
        return selectedFlightFreq;
    }

    public void setSelectedFlightFreq(List<FlightFrequency> selectedFlightFreq) {
        this.selectedFlightFreq = selectedFlightFreq;
    }

}
