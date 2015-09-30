package APSmanagedbean;

import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "editATMB")
@ViewScoped
public class EditAircraftTypeManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;
    private AircraftType aircraftType = new AircraftType();
    private String type;
    private String manufacturer;
    private Double maxDistance;
    private Double aircraftLength;
    private Double leaseCost;
    private Double fuelCost;
    private Double wingspan;
    private String minAirspace;

    private Integer suiteNo;                //number of seat in suite
    private Integer fcSeatNo;               //number of seat in first class
    private Integer bcSeatNo;               //number of seat in business class
    private Integer pecSeatNo;              //number of seat in premium economy class
    private Integer ecSeatNo;               //number of seat in economy class

    public EditAircraftTypeManagedBean() {
    }

    @PostConstruct
    public void init() {
        type = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("type");
        manufacturer = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("manufacturer");
        maxDistance = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("maxDistance");
        aircraftLength = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraftLength");
        leaseCost = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("leaseCost");
        fuelCost = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fuelCost");
        wingspan = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("wingspan");
        minAirspace = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("minAirspace");

        suiteNo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("suiteNo");
        fcSeatNo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("fcSeatNo");
        bcSeatNo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bcSeatNo");
        pecSeatNo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("pecSeatNo");
        ecSeatNo = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ecSeatNo");
    }

    public void editAircraftType() throws Exception {
        try {
            fpb.editAircraftType(type, manufacturer, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./EditAircraftTypeDone.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }
    public void editAircraftTypeCancel() throws Exception{
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditAircraftType.xhtml");
    }
    
    //////////////////////////////////////////

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public Double getAircraftLength() {
        return aircraftLength;
    }

    public void setAircraftLength(Double aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    public Double getLeaseCost() {
        return leaseCost;
    }

    public void setLeaseCost(Double leaseCost) {
        this.leaseCost = leaseCost;
    }

    public Double getFuelCost() {
        return fuelCost;
    }

    public void setFuelCost(Double fuelCost) {
        this.fuelCost = fuelCost;
    }

    public Double getWingspan() {
        return wingspan;
    }

    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    public String getMinAirspace() {
        return minAirspace;
    }

    public void setMinAirspace(String minAirspace) {
        this.minAirspace = minAirspace;
    }

    public Integer getSuiteNo() {
        return suiteNo;
    }

    public void setSuiteNo(Integer suiteNo) {
        this.suiteNo = suiteNo;
    }

    public Integer getFcSeatNo() {
        return fcSeatNo;
    }

    public void setFcSeatNo(Integer fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    public Integer getBcSeatNo() {
        return bcSeatNo;
    }

    public void setBcSeatNo(Integer bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    public Integer getPecSeatNo() {
        return pecSeatNo;
    }

    public void setPecSeatNo(Integer pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    public Integer getEcSeatNo() {
        return ecSeatNo;
    }

    public void setEcSeatNo(Integer ecSeatNo) {
        this.ecSeatNo = ecSeatNo;
    }
    

}
