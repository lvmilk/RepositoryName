package APSmanagedbean;

import javax.inject.Named;

import java.io.IOException;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import Entity.APS.*;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel; 
/**
 *
 * @author Lu Xi
 */
@Named(value = "aircraftTypeManagedBean")
@ViewScoped
public class AircraftTypeManagedBean {
    @EJB
    private FleetPlanningBeanLocal fpb;
    private AircraftType newType = new AircraftType();
 
    private String type;
    private String manufacturer;
    private Double maxDistance;
    private Double cruiseSpeed;
    private Double cruiseAltitude;
    private Double aircraftLength;
    private Double wingspan;
    private String minAirspaceClassReq;    

    
    private List <AircraftType> typeList;
    private List <AircraftType> selectedList;
    
        
    public AircraftTypeManagedBean() {
    }
    
    public void addAircraftType() throws Exception {
        
    }
    
    public List<AircraftType> getTypeList() {
    typeList=fpb.getAllAircraftType();
    System.out.println("Type List size is "+typeList.size());
    return  typeList;
    }

    public void setTypeList(List<AircraftType> typeList) {
        this.typeList = typeList;
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

    public Double getCruiseSpeed() {
        return cruiseSpeed;
    }

    public void setCruiseSpeed(Double cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    public Double getCruiseAltitude() {
        return cruiseAltitude;
    }

    public void setCruiseAltitude(Double cruiseAltitude) {
        this.cruiseAltitude = cruiseAltitude;
    }

    public Double getAircraftLength() {
        return aircraftLength;
    }

    public void setAircraftLength(Double aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    public Double getWingspan() {
        return wingspan;
    }

    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    public String getMinAirspaceClassReq() {
        return minAirspaceClassReq;
    }

    public void setMinAirspaceClassReq(String minAirspaceClassReq) {
        this.minAirspaceClassReq = minAirspaceClassReq;
    }
    
}
