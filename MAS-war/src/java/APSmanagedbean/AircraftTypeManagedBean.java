package APSmanagedbean;

import javax.inject.Named;

import java.io.IOException;
import java.util.*;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import Entity.APS.*;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author Lu Xi
 */
@Named(value = "ATMB")
@ViewScoped
public class AircraftTypeManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    private UIComponent uIComponent;

    private AircraftType newType = new AircraftType();
    private List<AircraftType> typeList = new ArrayList<>();
    private List<AircraftType> selectedList = new ArrayList<>();
    private List<AircraftType> deletedList = new ArrayList<>();

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

    //number of cabin crew
    private Integer stewardess; //cabin female
    private Integer steward; //cabin male
    private Integer purser; //cabin master
    //number of cockpit crew
    private Integer captain;
    private Integer pilot; //1st officer

    public AircraftTypeManagedBean() {
    }

    @PostConstruct
    public void init() {
        typeList = fpb.getAllAircraftType();
        deletedList = fpb.canDeleteTypeList();
    }

    public void addAircraftType() throws Exception {
        if (!fpb.checkDuplicate(type)) {
            System.out.println("AircraftTypeManagedBean: addAircraftType: No duplicates");
            String typeUpper = type.toUpperCase();
            String manufacturerUpper = manufacturer.toUpperCase();
            fpb.addAircraftType(typeUpper, manufacturerUpper, maxDistance, leaseCost, fuelCost, aircraftLength, wingspan, minAirspace, suiteNo, fcSeatNo, bcSeatNo, pecSeatNo, ecSeatNo, stewardess, steward, purser, captain, pilot);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ConfirmAddAircraftType.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aircraft Type has already been used! ", ""));
        }
    }

//    public void tryDeleteAircraftType() throws IOException {
//        if (selectedList.isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select an Aircraft Type to delete! ", ""));
//        } else {
//            try {
//                System.out.println("tryDeleteType: before check");
//                fpb.tryDeleteTypeList(selectedList);
//                System.out.println("tryDeleteType: pass check");
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deleteConditionFlag", true);
//                System.out.println("tryDeleteType: flag back");
//            } catch (Exception ex) {
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deleteConditionFlag", false);
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(), ""));
//            }
//        }
//    }
    public void checkSelect() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedList.isEmpty()) {
            context.execute("alert('Please select an aircraft type to delete. ');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select an aircraft type to delete. ", ""));
        } else {
            context.execute("PF('dlg').show()");
        }
    }

    public void confirmDeleteAircraftType() throws Exception {
        try {
            System.out.println("get the selected list!");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedAircraftType", selectedList);
            fpb.deleteAircraftTypeList(selectedList);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./DeleteAircraftTypeDone.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void deleteBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./DeleteAircraftType.xhtml");
    }

    public void editAircraftType(AircraftType aircraftType) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("type", aircraftType.getType());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manufacturer", aircraftType.getManufacturer());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("maxDistance", aircraftType.getMaxDistance());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaseCost", aircraftType.getLeaseCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fuelCost", aircraftType.getFuelCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftLength", aircraftType.getAircraftLength());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("wingspan", aircraftType.getWingspan());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("minAirspace", aircraftType.getMinAirspace());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("suiteNo", aircraftType.getSuiteNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fcSeatNo", aircraftType.getFcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bcSeatNo", aircraftType.getBcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaseCost", aircraftType.getLeaseCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pecSeatNo", aircraftType.getPecSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ecSeatNo", aircraftType.getEcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("stewardess", aircraftType.getStewardess());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("steward", aircraftType.getSteward());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("purser", aircraftType.getPurser());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("captain", aircraftType.getCaptain());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pilot", aircraftType.getPilot());
        System.out.println("Which type is changed? : " + aircraftType.getType());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditAircraftTypeInfo.xhtml");
    }

    public void viewAircraftType(AircraftType aircraftType) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("type", aircraftType.getType());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("manufacturer", aircraftType.getManufacturer());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("maxDistance", aircraftType.getMaxDistance());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaseCost", aircraftType.getLeaseCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fuelCost", aircraftType.getFuelCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftLength", aircraftType.getAircraftLength());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("wingspan", aircraftType.getWingspan());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("minAirspace", aircraftType.getMinAirspace());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("suiteNo", aircraftType.getSuiteNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("fcSeatNo", aircraftType.getFcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("bcSeatNo", aircraftType.getBcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("leaseCost", aircraftType.getLeaseCost());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pecSeatNo", aircraftType.getPecSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ecSeatNo", aircraftType.getEcSeatNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("stewardess", aircraftType.getStewardess());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("steward", aircraftType.getSteward());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("purser", aircraftType.getPurser());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("captain", aircraftType.getCaptain());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("pilot", aircraftType.getPilot());
        System.out.println("Which type is displayed? : " + aircraftType.getType());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewAircraftTypeConfirm.xhtml");
    }

    public void viewAircraftTypeConfirm() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewAircraftType.xhtml");
    }

    public List<AircraftType> getTypeList() {
        //typeList = fpb.getAllAircraftType();
        System.out.println("Type List size is " + typeList.size());
        return typeList;
    }

    public void setTypeList(List<AircraftType> typeList) {
        this.typeList = typeList;
    }

    public List<AircraftType> getSelectedList() {
        System.out.println("Selected List size is " + selectedList.size());
        return selectedList;
    }

    public void setSelectedList(ArrayList<AircraftType> selectedList) {
        this.selectedList = selectedList;
    }

    public List<AircraftType> getDeletedList() {
        return deletedList;
    }

    public void setDeletedList(List<AircraftType> deletedList) {
        this.deletedList = deletedList;
    }

    //////////////////////////////////
    public void check(SelectEvent event) {
        System.out.println("in check");
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

    public Integer getStewardess() {
        return stewardess;
    }

    public void setStewardess(Integer stewardess) {
        this.stewardess = stewardess;
    }

    public Integer getSteward() {
        return steward;
    }

    public void setSteward(Integer steward) {
        this.steward = steward;
    }

    public Integer getPurser() {
        return purser;
    }

    public void setPurser(Integer purser) {
        this.purser = purser;
    }

    public Integer getCaptain() {
        return captain;
    }

    public void setCaptain(Integer captain) {
        this.captain = captain;
    }

    public Integer getPilot() {
        return pilot;
    }

    public void setPilot(Integer pilot) {
        this.pilot = pilot;
    }

    public UIComponent getuIComponent() {
        return uIComponent;
    }

    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

}
