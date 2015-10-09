package APSmanagedbean;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.IOException;
import java.util.*;
import javax.faces.context.FacesContext;
import Entity.APS.*;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author Lucy
 */
@Named(value = "AMB")
@ViewScoped
public class AircraftManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;
    private String type;
    private String registrationNo;
    private String serialNo;
    private String status;
    private Date firstFlyDate;
    private Date deliveryDate;
    private Date retireDate;  //Lease Expiration Date
    private Long flightLogId;
    private Long maintenanceLogId;
    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

    private Aircraft newAircraft;
    private List<Aircraft> aircraftList;
    private ArrayList<Aircraft> selectedList;
    private List<AircraftType> typeList = new ArrayList<>();
    private List<String> keyList = new ArrayList<>();

    private UIComponent component;

    public AircraftManagedBean() {
    }

    @PostConstruct
    public void init() {
        aircraftList = fpb.getAllAircraft();
        selectedList = new ArrayList<Aircraft>();
        typeList = fpb.getAllAircraftType();
        for (int i = 0; i < typeList.size(); i++) {
            type = typeList.get(i).getType();
            keyList.add(type);
        }

    }

    public void addAircraft() throws Exception {
        try {
            if (firstFlyDate.before(deliveryDate) && retireDate.after(deliveryDate)) {
                String ffd = df.format(firstFlyDate);
                String dd = df.format(deliveryDate);
                String rd = df.format(retireDate);
                fpb.addAircraft(type, registrationNo, serialNo, status, ffd, dd, rd, flightLogId, maintenanceLogId);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./addAircraftConfirm.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please check the Date! (first fly date < delivery date < lease expiration date)"));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void viewAircraft(Aircraft aircraft) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registrationNo", aircraft.getRegistrationNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("type", aircraft.getAircraftType().getType());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serialNo", aircraft.getSerialNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("status", aircraft.getStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstFlyDate", aircraft.getFirstFlyDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deliveryDate", aircraft.getDeliveryDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("retireDate", aircraft.getRetireDate());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightLogId", aircraft.getFlightLogId());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("maintenanceLogId", aircraft.getMaintenanceLogId());
        System.out.println("Type got? : " + aircraft.getAircraftType().getType());
        System.out.println("Which aircraft is displayed? : " + aircraft.getRegistrationNo());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAircraftInfo.xhtml");
    }

    public void viewAircraftTypeConfirm() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./viewAircraft.xhtml");
    }

    public void editAircraft(Aircraft aircraft) throws IOException, ParseException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("registrationNo", aircraft.getRegistrationNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("type", aircraft.getAircraftType().getType());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("serialNo", aircraft.getSerialNo());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("status", aircraft.getStatus());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstFlyDate", df.parse(aircraft.getFirstFlyDate()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deliveryDate", df.parse(aircraft.getDeliveryDate()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("retireDate", df.parse(aircraft.getRetireDate()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightLogId", aircraft.getFlightLogId());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("maintenanceLogId", aircraft.getMaintenanceLogId());
        System.out.println("Which aircraft is edited? : " + aircraft.getRegistrationNo());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAircraftInfo.xhtml");
    }

    public void checkSelect() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedList.isEmpty()) {
            context.execute("alert('Please select an aircraft to delete. ');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select an aircraft to delete. ", ""));
        } else {
            context.execute("PF('dlg').show()");
        }
    }
    
    public void confirmDeleteAircraft() throws Exception {
        try {
            if (selectedList.isEmpty()) {
                System.out.println("empty selected list. ");
                FacesContext.getCurrentInstance().addMessage("dlg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please SELECT an aircraft to delete! ", ""));
            } else {
                System.out.println("get the selected list!" + selectedList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("deletedAircraft", selectedList);
                fpb.deleteAircraft(selectedList);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteAircraftDone.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void deleteBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./deleteAircraft.xhtml");
    }

    public void check(SelectEvent event) {
        System.out.println("in check");
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

    public Aircraft getNewAircraft() {
        return newAircraft;
    }

    public void setNewAircraft(Aircraft newAircraft) {
        this.newAircraft = newAircraft;
    }

    public List<Aircraft> getAircraftList() {
//        aircraftList = fpb.getAllAircraft();
//        System.out.println("Aircraft List size is " + aircraftList.size());
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public ArrayList<Aircraft> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(ArrayList<Aircraft> selectedList) {
        this.selectedList = selectedList;
    }

    public UIComponent getComponent() {
        return component;
    }

    public void setComponent(UIComponent component) {
        this.component = component;
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
