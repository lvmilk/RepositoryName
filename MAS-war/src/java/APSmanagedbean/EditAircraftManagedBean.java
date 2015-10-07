/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
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
@Named(value = "editAMB")
@ViewScoped
public class EditAircraftManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;
    private Aircraft aircraft = new Aircraft();
    private String type;
    private String registrationNo;
    private String serialNo;
    private String status;
    private Date firstFlyDate;
    private Date deliveryDate;
    private Date retireDate;  //Lease Expiration Date
    private Long flightLogId;
    private Long maintenanceLogId;

    private List<AircraftType> typeList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();

    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    public EditAircraftManagedBean() {
    }

    @PostConstruct
    public void init() {
        typeList = fpb.getAllAircraftType();
        for (int i = 0; i < typeList.size(); i++) {
            type = typeList.get(i).getType();
            keyList.add(type);
        }
        type = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("type");
        registrationNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("registrationNo");
        serialNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serialNo");
        status = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("status");
        firstFlyDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstFlyDate");
        deliveryDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("deliveryDate");
        retireDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("retireDate");
        flightLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightLogId");
        maintenanceLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("maintenanceLogId");
    }

    public void editAircraft() throws Exception {
        try {
            if (firstFlyDate.before(deliveryDate) && retireDate.after(deliveryDate)) {
                String ffd = df.format(firstFlyDate);
                String dd = df.format(deliveryDate);
                String rd = df.format(retireDate);
                fpb.editAircraft(type, registrationNo, serialNo, status, ffd, dd, rd, flightLogId, maintenanceLogId);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./editAircraftDone.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please check the Date! (first fly date < delivery date < lease expiration date)"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editAircraftCancel() throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAircraft.xhtml");
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getFirstFlyDate() {
        return firstFlyDate;
    }

    public void setFirstFlyDate(Date firstFlyDate) {
        this.firstFlyDate = firstFlyDate;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Date getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(Date retireDate) {
        this.retireDate = retireDate;
    }

    public Long getFlightLogId() {
        return flightLogId;
    }

    public void setFlightLogId(Long flightLogId) {
        this.flightLogId = flightLogId;
    }

    public Long getMaintenanceLogId() {
        return maintenanceLogId;
    }

    public void setMaintenanceLogId(Long maintenanceLogId) {
        this.maintenanceLogId = maintenanceLogId;
    }

    public List<AircraftType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AircraftType> typeList) {
        this.typeList = typeList;
    }

    public List<String> getKeyList() {
        return keyList;
    }

    public void setKeyList(List<String> keyList) {
        this.keyList = keyList;
    }

    
}
