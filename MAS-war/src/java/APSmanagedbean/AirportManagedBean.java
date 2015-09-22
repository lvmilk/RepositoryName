package APSmanagedbean;

import Entity.APS.Airport;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Xu
 */
@Named(value = "AirportManagedBean")
@SessionScoped
public class AirportManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private UIComponent uIComponent;

    private String IATA;
    private String airportName;
    private String cityName;
    private String countryCode;
    private String spec;
    private String timeZone;
    private String opStatus;
    private String strategicLevel;
    private String airspace;

    private List<Airport> selectedAirport = new ArrayList<>();
    private List<Airport> airportList = new ArrayList<>();

    public AirportManagedBean() {
    }

    public void addAirport() throws Exception {
        try {
            rpb.addAirport(IATA, airportName, cityName, countryCode, spec, timeZone, opStatus, strategicLevel, airspace);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addAirportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void confirmDeleteAirport() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./confirmDeleteAirport.xhtml");
    }

    public void deleteAirport() throws IOException {
        try {
            boolean check = rpb.deleteAirportList(selectedAirport);
            if (check) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteAirportSuccess.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No airport selected. ", ""));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void check(SelectEvent event) {
        System.out.println("amb.check(): In check");
    }

    public void setAirportList(List<Airport> airportList) {
        this.airportList = airportList;
    }

    public List<Airport> getAirportList() {
        airportList = rpb.viewAllAirport();
        System.out.println("amb.getAirportList(): Airport list size is " + airportList.size());
        return airportList;
    }

    public void viewAirport() {

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

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public List<Airport> getSelectedAirport() {
        return selectedAirport;
    }

    public void setSelectedAirport(List<Airport> selectedAirport) {
        this.selectedAirport = selectedAirport;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

}