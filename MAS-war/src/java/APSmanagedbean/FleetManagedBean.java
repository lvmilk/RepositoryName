package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Xi
 */
@Named(value = "FMB")
@ViewScoped
public class FleetManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<Aircraft> aircraftList = new ArrayList<>();
    private List<AircraftType> typeList = new ArrayList<>();
    private AircraftType aircraftType = new AircraftType();
    private Aircraft aircraft = new Aircraft();

    private String type;
    private String manufacturer;

    private String registrationNo;
    private String status;

    private Integer numAircraft;

    private Map<String, Integer> sizeMap = new HashMap<String, Integer>();
    private Map<String, String> manufacturerMap = new HashMap<String, String>();
    private Map<String, List<String>> registrationMap = new HashMap<String, List<String>>();
    private List<String> keyList = new ArrayList<String>();

    public FleetManagedBean() {
    }

    @PostConstruct
    public void init() {
        typeList = fpb.getAllAircraftType();
        numAircraft = fpb.getAllAircraft().size();
        for (int i = 0; i < typeList.size(); i++) {
            type = typeList.get(i).getType();
            keyList.add(type);
            sizeMap = fpb.getAllSize(type);
            manufacturerMap = fpb.getAllManufacturer(type);

            registrationMap = fpb.getAllNum(type);

        }
    }

    public Map<String, List<String>> getRegistrationMap() {
        return registrationMap;
    }

    public void setRegistrationMap(Map<String, List<String>> registrationMap) {
        this.registrationMap = registrationMap;
    }

    public Map<String, String> getManufacturerMap() {
        return manufacturerMap;
    }

    public void setManufacturerMap(Map<String, String> manufacturerMap) {
        this.manufacturerMap = manufacturerMap;
    }

    public Map<String, Integer> getSizeMap() {
        return sizeMap;
    }

    public void setSizeMap(Map<String, Integer> sizeMap) {
        this.sizeMap = sizeMap;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public List<AircraftType> getTypeList() {
        return typeList;
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

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public Integer getNumAircraft() {
        return numAircraft;
    }

    public void setNumAircraft(Integer numAircraft) {
        this.numAircraft = numAircraft;
    }

}
