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
 @Named(value = "VFFMB")
@ViewScoped
public class ViewFlightFrequencyManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;

    private List<FlightFrequency> flightFreqList;
    private List<FlightFrequency> filteredFlightFreqList;

    public ViewFlightFrequencyManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
    }

    public void viewFlightFrequency(FlightFrequency flightFreq) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("viewFlightFreq", flightFreq);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightFrequencyDetail.xhtml");
    }

    public void viewFlightFrequencyBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewFlightFrequency.xhtml");
    }

    public List<FlightFrequency> getFlightFreqList() {
        return flightFreqList;
    }

    public void setFlightFreqList(List<FlightFrequency> flightFreqList) {
        this.flightFreqList = flightFreqList;
    }

    public List<FlightFrequency> getFilteredFlightFreqList() {
        return filteredFlightFreqList;
    }

    public void setFilteredFlightFreqList(List<FlightFrequency> filteredFlightFreqList) {
        this.filteredFlightFreqList = filteredFlightFreqList;
    }

}
