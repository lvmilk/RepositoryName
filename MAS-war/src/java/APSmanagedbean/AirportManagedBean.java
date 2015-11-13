package APSmanagedbean;

import Entity.APS.Airport;
import Entity.APS.Route;
import SessionBean.APS.RoutePlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Xu
 */
@Named(value = "AirportManagedBean")
@ViewScoped
public class AirportManagedBean implements Serializable {

    @EJB
    private RoutePlanningBeanLocal rpb;

    private UIComponent uIComponent;

    private Airport viewAp = new Airport();
    private String IATA;
    private String airportName;
    private String cityName;
    private String countryName;
    private String spec;
    private String timeZone;
    private String opStatus;
    private String strategicLevel;
    private String airspace;
    private List<String> utcList;
    private List<Route> apOriginRouteList;
    private List<Route> apDestRouteList;
    private String lang;

    private Double latitude;
    private Double longitude;

    private List<Airport> selectedAirport = new ArrayList<>();
    private List<Airport> airportList = new ArrayList<>();
    private List<Airport> deletedAirport = new ArrayList<>();
    private List<Airport> canDeleteAirport = new ArrayList<>();
    private List<Airport> cannotDeleteAirport = new ArrayList<>();
    private List<String>  timezoneList= (List<String>)Arrays.asList(TimeZone.getAvailableIDs());
    public AirportManagedBean() {
    }

    @PostConstruct
    public void init() {
        airportList = rpb.viewAllAirport();
        canDeleteAirport = rpb.canDeleteAirportList();
    }

    public void addAirport() throws Exception {
        try {
            rpb.addAirport(IATA, airportName, cityName, countryName, spec, lang, timeZone, opStatus, strategicLevel, airspace, latitude, longitude);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("IATA", IATA);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./addAirportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void viewAirport(Airport airport) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("IATA", airport.getIATA());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("airportName", airport.getAirportName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cityName", airport.getCityName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryName", airport.getCountryName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("spec", airport.getSpec());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lang", airport.getLang());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("timeZone", airport.getTimeZone());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("opStatus", airport.getOpStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("strategicLevel", airport.getStrategicLevel());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("airspace", airport.getAirspace());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("latitude", airport.getLat());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("longitude", airport.getLon());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("apOriginRouteList", getApOriginRouteList(airport));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("apDestRouteList", getApDestRouteList(airport));
        System.out.println("amb.viewAiport(): Airport " + airport.getIATA() + " detail is displayed. ");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAirportDetail.xhtml");
    }

    public void viewAirportBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAirport.xhtml");
    }

    public void editAirport(Airport airport) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("IATA", airport.getIATA());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("airportName", airport.getAirportName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cityName", airport.getCityName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countryName", airport.getCountryName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("spec", airport.getSpec());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lang", airport.getLang());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("timeZone", airport.getTimeZone());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("opStatus", airport.getOpStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("strategicLevel", airport.getStrategicLevel());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("airspace", airport.getAirspace());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lat", airport.getLat());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lon", airport.getLon());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("apOriginRouteList", getApOriginRouteList(airport));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("apDestRouteList", getApDestRouteList(airport));
        System.out.println("amb.editAiport(): Airport " + airport.getIATA() + " information will be updated. ");
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAirportDetail.xhtml");
    }

    public void editAirportDetail() throws Exception {
        try {
            rpb.editAirport(IATA, airportName, cityName, countryName, spec, lang, timeZone, opStatus, strategicLevel, airspace);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editAirportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void tryDeleteAirport() throws Exception {
        if (selectedAirport.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please select airport(s) to be deleted.", ""));
        } else {
            try {
                System.out.println("amb.tryDeleteAirport(): before check");
                rpb.tryDeleteAirportList(selectedAirport);
                System.out.println("amb.tryDeleteAirport(): pass check");
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deleteConditionFlag", true);
                System.out.println("amb.tryDeleteAirport(): send flag back");
//                FacesContext.getCurrentInstance().getExternalContext().redirect("./confirmDeleteAirport.xhtml");
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deleteConditionFlag", false);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
            }
        }
    }

    public void toDeleteAirport() throws Exception {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedAirport.isEmpty()) {
            System.out.println("a");
            context.execute("alert('Please select the airport(s) to be deleted.');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select the airport(s) to be deleted. ", ""));
        } else {
            System.out.println("b");
            context.execute("PF('dlg').show()");
        }
    }

    public void confirmDeleteAirport() throws Exception {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedAirport", selectedAirport);
            rpb.deleteAirportList(selectedAirport);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteAirportSuccess.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void deleteAirportBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteAirport.xhtml");
    }

    public void check(SelectEvent event) {
        System.out.println("amb.check(): In check");
    }

    public void setAirportList(List<Airport> airportList) {
        this.airportList = airportList;
    }

    public List<Airport> getAirportList() {
//        airportList = rpb.viewAllAirport();
        System.out.println("amb.getAirportList(): Airport list size is " + airportList.size());
        return airportList;
    }

    public List<Airport> getCanDeleteAirport() {
        return canDeleteAirport;
    }

    public void setCanDeleteAirport(List<Airport> canDeleteAirport) {
        this.canDeleteAirport = canDeleteAirport;
    }

    public List<Airport> getCannotDeleteAirport() {
        return cannotDeleteAirport;
    }

    public void setCannotDeleteAirport(List<Airport> cannotDeleteAirport) {
        this.cannotDeleteAirport = cannotDeleteAirport;
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

    public List<Airport> getSelectedAirport() {
        return selectedAirport;
    }

    public void setSelectedAirport(List<Airport> selectedAirport) {
        this.selectedAirport = selectedAirport;
    }

    public List<Airport> getDeletedAirport() {
        return deletedAirport;
    }

    public void setDeletedAirport(List<Airport> deletedAirport) {
        this.deletedAirport = deletedAirport;
    }

    public List<Route> getApOriginRouteList(Airport airport) {
        return rpb.viewApAsOriginRoute(airport);
    }

//    public void setApOriginRouteList(List<Route> apOriginRouteList) {
//        this.apOriginRouteList = apOriginRouteList;
//    }
    public List<Route> getApDestRouteList(Airport airport) {
        return rpb.viewApAsDestRoute(airport);
    }

//    public void setApDestRouteList(List<Route> apDestRouteList) {
//        this.apDestRouteList = apDestRouteList;
//    }
    public Airport getViewAp() {
        return viewAp;
    }

    public void setViewAp(Airport viewAp) {
        this.viewAp = viewAp;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
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

    public List<String> getUtcList() {
        List<String> utc = new ArrayList<String>();
        utc.add("UTC-12:00");
        utc.add("UTC-11:00");
        utc.add("UTC-10:00");
        utc.add("UTC-09:30");
        utc.add("UTC-09:00");
        utc.add("UTC-08:00");
        utc.add("UTC-07:00");
        utc.add("UTC-06:00");
        utc.add("UTC-05:00");
        utc.add("UTC-04:30");
        utc.add("UTC-04:00");
        utc.add("UTC-03:30");
        utc.add("UTC-03:00");
        utc.add("UTC-02:00");
        utc.add("UTC-01:00");
        utc.add("UTC+00:00");
        utc.add("UTC+01:00");
        utc.add("UTC+02:00");
        utc.add("UTC+03:00");
        utc.add("UTC+03:30");
        utc.add("UTC+04:00");
        utc.add("UTC+04:30");
        utc.add("UTC+05:00");
        utc.add("UTC+05:30");
        utc.add("UTC+05:45");
        utc.add("UTC+06:00");
        utc.add("UTC+06:30");
        utc.add("UTC+07:00");
        utc.add("UTC+08:00");
        utc.add("UTC+08:30");
        utc.add("UTC+08:45");
        utc.add("UTC+09:00");
        utc.add("UTC+09:30");
        utc.add("UTC+10:00");
        utc.add("UTC+10:30");
        utc.add("UTC+11:00");
        utc.add("UTC+12:00");
        utc.add("UTC+12:45");
        utc.add("UTC+13:00");
        utc.add("UTC+14:00");

        return utc;
    }

    public void setUtcList(List<String> utcList) {
        this.utcList = utcList;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public List<String> getTimezoneList() {
        return timezoneList;
    }

    public void setTimezoneList(List<String> timezoneList) {
        this.timezoneList = timezoneList;
    }
}
