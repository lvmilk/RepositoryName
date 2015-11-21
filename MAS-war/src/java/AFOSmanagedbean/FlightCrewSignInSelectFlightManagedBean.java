/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.FlightInstance;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "FCSISFMB")
@ViewScoped
public class FlightCrewSignInSelectFlightManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    @EJB
    FlightSchedulingBeanLocal fsb;

    private List<FlightInstance> fiList = new ArrayList<>();
    private Date dateSelected;
    private String dateString;
    private String flightNo;
    private FlightInstance selectedFi;
    private Date today;

    public FlightCrewSignInSelectFlightManagedBean() {
    }

    public void onDateChange() {
        System.out.println("CMB:OnDateChange run");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        this.setDateString(df1.format(dateSelected));

        if (dateString != null && !dateString.equals("")) {
            fiList = csb.getFlightOnDate(dateString);
            System.out.println("CMB:OnDateChange run result: " + fiList.toString());
        }
    }

    public void getCrewList() throws Exception {
        try {
            selectedFi = fsb.findFlight(flightNo, dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFi", selectedFi);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./flightCrewSignIn.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

//    public void enterDebrief() throws Exception {
//        selectedFi = fsb.findFlight(flightNo, dateString);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFi", selectedFi);
//        FacesContext.getCurrentInstance().getExternalContext().redirect("./addDebriefingReport.xhtml");
//    }
    public FlightInstance getSelectedFi() {
        return selectedFi;
    }

    public void setSelectedFi(FlightInstance selectedFi) {
        this.selectedFi = selectedFi;
    }

    public List<FlightInstance> getFiList() {
        return fiList;
    }

    public void setFiList(List<FlightInstance> fiList) {
        this.fiList = fiList;
    }

    public Date getDateSelected() {
        return dateSelected;
    }

    public void setDateSelected(Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getToday() {
        Date today1 = new Date();
        return today1;
    }

    public void setToday(Date today) {
        this.today = new Date();
    }

}
