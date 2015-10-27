/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "EMSMB")
@ViewScoped
public class EditMaintenanceStandardManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    private AircraftType acType;
//    private String acTypeString;
//    private List<String> acTypeInfo = new ArrayList();

    private Integer acInH;
    private Integer acInD;
    private Integer acDu;
    private Integer acMH;

    private Integer bcInH;
    private Integer bcInD;
    private Integer bcDu;
    private Integer bcMH;

    private Integer ccInH;
    private Integer ccInD;
    private Integer ccDu;
    private Integer ccMH;

    private Integer dcInH;
    private Integer dcInD;
    private Integer dcDu;
    private Integer dcMH;


    public EditMaintenanceStandardManagedBean() {
    }

    @PostConstruct
    public void init() {
        acType = (AircraftType) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("acTypeMt");
//        dailycDu = acType.getDailycDu();
//        dailycMH = acType.getDailycMH();
//        weeklycDu = acType.getWeeklycDu();
//        weeklycMH = acType.getWeeklycMH();
        acInH = acType.getAcInH();
        acInD = acType.getAcInD();
        acDu = acType.getAcDu();
        acMH = acType.getAcMH();
        bcInH = acType.getBcInH();
        bcInD = acType.getBcInD();
        bcDu = acType.getBcDu();
        bcMH = acType.getBcMH();
        ccInH = acType.getCcInH();
        ccInD = acType.getCcInD();
        ccDu = acType.getCcDu();
        ccMH = acType.getCcMH();
        dcInH = acType.getDcInH();
        dcInD = acType.getDcInD();
        dcDu = acType.getDcDu();
        dcMH = acType.getDcMH();
    }

    public void editMaintenanceStandard(ActionEvent e) throws Exception {
        fpb.editMtStandard(acType, acInH, acInD, acDu, acMH, bcInH, bcInD, bcDu, bcMH, ccInH, ccInD, ccDu, ccMH, dcInH, dcInD, dcDu, dcMH);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("acTypeStringMt", acType.getType());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editMaintenanceStandardSuccess.xhtml");
    }

    public void editMaintenanceStandardCancel(ActionEvent e) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceStandard.xhtml");
    }

    public void editMaintenanceStandardBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewMaintenanceStandard.xhtml");
    }

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

//    public String getAcTypeString() {
//        return acTypeString;
//    }
//
//    public void setAcTypeString(String acTypeString) {
//        this.acTypeString = acTypeString;
//    }
//
//    public List<String> getAcTypeInfo() {
//        List<AircraftType> typeList = fpb.getAllAircraftType();
//        List<String> typeInfo = new ArrayList<>();
//        for (AircraftType a : typeList) {
//            typeInfo.add(a.getType());
//        }
//        return typeInfo;
//    }
//
//    public void setAcTypeInfo(List<String> acTypeInfo) {
//        this.acTypeInfo = acTypeInfo;
//    }
    public Integer getAcInH() {
        return acInH;
    }

    public void setAcInH(Integer acInH) {
        this.acInH = acInH;
    }

    public Integer getAcInD() {
        return acInD;
    }

    public void setAcInD(Integer acInD) {
        this.acInD = acInD;
    }

    public Integer getAcDu() {
        return acDu;
    }

    public void setAcDu(Integer acDu) {
        this.acDu = acDu;
    }

    public Integer getAcMH() {
        return acMH;
    }

    public void setAcMH(Integer acMH) {
        this.acMH = acMH;
    }

    public Integer getBcInH() {
        return bcInH;
    }

    public void setBcInH(Integer bcInH) {
        this.bcInH = bcInH;
    }

    public Integer getBcInD() {
        return bcInD;
    }

    public void setBcInD(Integer bcInD) {
        this.bcInD = bcInD;
    }

    public Integer getBcDu() {
        return bcDu;
    }

    public void setBcDu(Integer bcDu) {
        this.bcDu = bcDu;
    }

    public Integer getBcMH() {
        return bcMH;
    }

    public void setBcMH(Integer bcMH) {
        this.bcMH = bcMH;
    }

    public Integer getCcDu() {
        return ccDu;
    }

    public void setCcDu(Integer ccDu) {
        this.ccDu = ccDu;
    }

    public Integer getCcMH() {
        return ccMH;
    }

    public void setCcMH(Integer ccMH) {
        this.ccMH = ccMH;
    }

    public Integer getCcInH() {
        return ccInH;
    }

    public void setCcInH(Integer ccInH) {
        this.ccInH = ccInH;
    }

    public Integer getCcInD() {
        return ccInD;
    }

    public void setCcInD(Integer ccInD) {
        this.ccInD = ccInD;
    }

    public Integer getDcInH() {
        return dcInH;
    }

    public void setDcInH(Integer dcInH) {
        this.dcInH = dcInH;
    }

    public Integer getDcInD() {
        return dcInD;
    }

    public void setDcInD(Integer dcInD) {
        this.dcInD = dcInD;
    }

    public Integer getDcDu() {
        return dcDu;
    }

    public void setDcDu(Integer dcDu) {
        this.dcDu = dcDu;
    }

    public Integer getDcMH() {
        return dcMH;
    }

    public void setDcMH(Integer dcMH) {
        this.dcMH = dcMH;
    }

//    public Integer getDailycDu() {
//        return dailycDu;
//    }
//
//    public void setDailycDu(Integer dailycDu) {
//        this.dailycDu = dailycDu;
//    }
//
//    public Integer getDailycMH() {
//        return dailycMH;
//    }
//
//    public void setDailycMH(Integer dailycMH) {
//        this.dailycMH = dailycMH;
//    }
//
//    public Integer getWeeklycDu() {
//        return weeklycDu;
//    }
//
//    public void setWeeklycDu(Integer weeklycDu) {
//        this.weeklycDu = weeklycDu;
//    }
//
//    public Integer getWeeklycMH() {
//        return weeklycMH;
//    }
//
//    public void setWeeklycMH(Integer weeklycMH) {
//        this.weeklycMH = weeklycMH;
//    }

}
