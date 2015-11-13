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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
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
//    private String serialNo;
    private String status;
    private Date firstFlyDate;
    private Date deliveryDate;
    private Date retireDate;  //Lease Expiration Date
    private Date oldRetireDate;
    private Double purchaseCost;
    String ffd;
    String dd;
    String rd;
//    private Long flightLogId;
//    private Long maintenanceLogId;

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
        aircraft = fpb.getAircraft(registrationNo);
//        serialNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("serialNo");
        status = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("status");
        firstFlyDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstFlyDate");
        deliveryDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("deliveryDate");
        retireDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("retireDate");
        purchaseCost = ((Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("purchaseCost"));
        ffd = df.format(firstFlyDate);
        dd = df.format(deliveryDate);
        rd = df.format(retireDate);
        try {
            firstFlyDate = df.parse(ffd);
            deliveryDate = df.parse(dd);
            retireDate = df.parse(rd);
        } catch (ParseException ex) {
            Logger.getLogger(EditAircraftManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        oldRetireDate = retireDate;
//        flightLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightLogId");
//        maintenanceLogId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("maintenanceLogId");
    }

    public void editAircraft() throws Exception {
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(firstFlyDate);
            c.add(Calendar.DATE, 100);
            Date deliveryCheck = c.getTime();
            c.setTime(deliveryDate);
            c.add(Calendar.YEAR, 5);
            Date retireCheck = c.getTime();
            if (deliveryDate.after(deliveryCheck) && retireDate.after(retireCheck)) {
                if (firstFlyDate.before(deliveryDate) && retireDate.after(deliveryDate)) {
                    purchaseCost = purchaseCost * 1000000;
                    if(status.equals("Retired")){
                        retireDate = new Date();
                    }
                    ffd = df.format(firstFlyDate);
                    dd = df.format(deliveryDate);
                    rd = df.format(retireDate);
                    fpb.editAircraft(type, registrationNo, status, ffd, dd, rd, purchaseCost);
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./editAircraftDone.xhtml");

                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please check the Date! (first fly date < delivery date < retire date)"));
                }
            } else {
                 FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Invalid Date","Please check the Date! (deliveryDate is at least 100 days after firstFlyDate, and retireDate is at least 5 years after deliveryDate)"));
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

//    public String getSerialNo() {
//        return serialNo;
//    }
//
//    public void setSerialNo(String serialNo) {
//        this.serialNo = serialNo;
//    }
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

    public Double getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(Double purchaseCost) {
        this.purchaseCost = purchaseCost;
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

    public Date getOldRetireDate() {
        return oldRetireDate;
    }

    public void setOldRetireDate(Date oldRetireDate) {
        this.oldRetireDate = oldRetireDate;
    }

    public String getFfd() {
        return ffd;
    }

    public void setFfd(String ffd) {
        this.ffd = ffd;
    }

    public String getDd() {
        return dd;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }

    public String getRd() {
        return rd;
    }

    public void setRd(String rd) {
        this.rd = rd;
    }

}
