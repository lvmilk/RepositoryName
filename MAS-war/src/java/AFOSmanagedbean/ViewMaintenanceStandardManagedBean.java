/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "VMSMB")
@ViewScoped
public class ViewMaintenanceStandardManagedBean implements Serializable {

    @EJB
    FleetPlanningBeanLocal fpb;

//    private AircraftType acType;
//    private String acTypeString;
//    private List<String> acTypeInfo = new ArrayList();
//
//    private Integer acInH;
//    private Integer acInD;
//    private Integer acDu;
//    private Integer acMH;
//
//    private Integer ccInM;
//    private Integer ccDu;
//    private Integer ccMH;
//
//    private Integer dcInM;
//    private Integer dcDu;
//    private Integer dcMH;
//
//    private Integer dailycDu;
//    private Integer dailycMH;
//
//    private Integer weeklycDu;
//    private Integer weeklycMH;
    List<AircraftType> acTypeList = new ArrayList<>();

    public ViewMaintenanceStandardManagedBean() {
    }

    @PostConstruct
    public void init() {
        getAcTypeList();
    }

    public void editMaintenanceStandard(AircraftType ac) throws Exception {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("acTypeMt", ac);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editMaintenanceStandard.xhtml");
    }

    public List<AircraftType> getAcTypeList() {
        List<AircraftType> act = fpb.getAllAircraftType();
        List<AircraftType> actCopy = fpb.getAllAircraftType();
        for (AircraftType a : act) {
            if (a.getType().equalsIgnoreCase("A330-200")) {
                actCopy.remove(a);
            }
        }
        return actCopy;
    }

    public void setAcTypeList(List<AircraftType> acTypeList) {
        this.acTypeList = acTypeList;
    }

}
