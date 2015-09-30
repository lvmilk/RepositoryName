/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APSmanagedbean;

import Entity.APS.Aircraft;
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
    private String firstFlyDate;
    private String deliveryDate;
    private String retireDate;
    private Long flightLogId;
    private Long maintenanceLogId;

    public EditAircraftManagedBean() {
    }

    @PostConstruct
    public void init() {
        type = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("type");
        registrationNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("registrationNo");
        serialNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serialNo");
        status = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("status");
        firstFlyDate = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstFlyDate");
        deliveryDate = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("deliveryDate");
        retireDate = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("retireDate");
        flightLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightLogId");
        maintenanceLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("maintenanceLogId");
    }

    public void editAircraft() throws Exception {
        try {
            fpb.editAircraft(type, registrationNo, serialNo, status, firstFlyDate, deliveryDate, retireDate, flightLogId, maintenanceLogId);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./editAircraftDone.xhtml");
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

    public String getFirstFlyDate() {
        return firstFlyDate;
    }

    public void setFirstFlyDate(String firstFlyDate) {
        this.firstFlyDate = firstFlyDate;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getRetireDate() {
        return retireDate;
    }

    public void setRetireDate(String retireDate) {
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

}
