/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Airport;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "editAirportManagedBean")
@ViewScoped
public class EditAirportManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private Airport airport = new Airport();
    private String IATA;
    private String airportName;
    private String cityName;
    private String countryName;
    private String spec;
    private String timeZone;
    private String opStatus;
    private String strategicLevel;
    private String airspace;

    private Double latitude;
    private Double longitude;
    private boolean isHub;

    public EditAirportManagedBean() {
    }

    @PostConstruct
    public void init() {
        IATA = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("IATA");
        airportName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("airportName");
        cityName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cityName");
        countryName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countryName");
        spec = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("spec");
        timeZone = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("timeZone");
        opStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("opStatus");
        strategicLevel = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("strategicLevel");
        airspace = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("airspace");
        latitude = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lat");
        longitude = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lon");
        isHub = strategicLevel.equalsIgnoreCase("Hub");
        airport = (Airport) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("airport");

        if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("airport") != null) {
            airport = (Airport) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("airport");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This airport " + airport.getIATA() + " has already been linked with flights. Please handle affected flight frequency! ", ""));
        }
    }

    public void beforePhaseListener(PhaseEvent event) {

    }

    public void editAirportDetail() throws Exception {
        try {
            airport = rpb.findAirport(IATA);
            if (rpb.airportHasFlight(airport) && opStatus.equals("Closed")) {
                System.out.println("editAirportManagedBean: editAirportDetail: airport " + airport + " opStatus: WARNING!!! " + opStatus);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This airport " + airport.getIATA() + " has already been linked with flights. Please handle affected flight frequency! ", ""));
                FacesContext.getCurrentInstance().getExternalContext().getFlash().put("airport", airport);
            }

            rpb.editAirport(IATA, airportName, cityName, countryName, spec, timeZone, opStatus, strategicLevel, airspace);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editAirportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editAirportCancel() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAirport.xhtml");
    }

    public void editAirportBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAirport.xhtml");
    }

    public Airport getAirport() {
        return airport;
    }

    public void setAirport(Airport airport) {
        this.airport = airport;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    public String getStrategicLevel() {
        return strategicLevel;
    }

    public void setStrategicLevel(String strategicLevel) {
        this.strategicLevel = strategicLevel;
    }

    public String getAirspace() {
        return airspace;
    }

    public void setAirspace(String airspace) {
        this.airspace = airspace;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public boolean isIsHub() {
        return strategicLevel.equalsIgnoreCase("Hub");
    }

    public void setIsHub(boolean isHub) {
        this.isHub = isHub;
    }

}
