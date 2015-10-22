package Entity.APS;

import Entity.AFOS.Maintenance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Lucy
 */
@Entity
public class Aircraft implements Serializable {

    @Id
    private String registrationNo;
    private String serialNo;
    private String status;
    private String firstFlyDate;
    private String deliveryDate;
    private String retireDate;   //Lease Expiration Date
    private Long flightLogId;
    private Long maintenanceLogId;
    private String currentAirport;

    private long accumFlyMinutes;
    private long acycleFH = 0;
    private long acycleFD = 0;
    private long ccycleFH = 0;
    private long ccycleFD = 0;
    private long dcycleFH = 0;
    private long dcycleFD = 0;

    @ManyToOne
    private AircraftType aircraftType = new AircraftType();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "aircraft")
    private List<FlightInstance> flightInstance = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "aircraft")
    private List<Maintenance> maintenanceList = new ArrayList<>();

    public void create(String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate) {
        this.setRegistrationNo(registrationNo);
//        this.setSerialNo(serialNo);
        this.setStatus(status);
        this.setFirstFlyDate(firstFlyDate);
        this.setDeliveryDate(deliveryDate);
        this.setRetireDate(retireDate);
//        this.setFlightLogId(flightLogId);
//        this.setMaintenanceLogId(maintenanceLogId);
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

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public List<FlightInstance> getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(List<FlightInstance> flightInstance) {
        this.flightInstance = flightInstance;
    }

    public String getCurrentAirport() {
        return currentAirport;
    }

    public void setCurrentAirport(String currentAirport) {
        this.currentAirport = currentAirport;
    }

    public long getAccumFlyMinutes() {
        return accumFlyMinutes;
    }

    public void setAccumFlyMinutes(long accumFlyMinutes) {
        this.accumFlyMinutes = accumFlyMinutes;
    }

    public List<Maintenance> getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public long getAcycleFH() {
        return acycleFH;
    }

    public void setAcycleFH(long acycleFH) {
        this.acycleFH = acycleFH;
    }

    public long getAcycleFD() {
        return acycleFD;
    }

    public void setAcycleFD(long acycleFD) {
        this.acycleFD = acycleFD;
    }

    public long getCcycleFH() {
        return ccycleFH;
    }

    public void setCcycleFH(long ccycleFH) {
        this.ccycleFH = ccycleFH;
    }

    public long getCcycleFD() {
        return ccycleFD;
    }

    public void setCcycleFD(long ccycleFD) {
        this.ccycleFD = ccycleFD;
    }

    public long getDcycleFH() {
        return dcycleFH;
    }

    public void setDcycleFH(long dcycleFH) {
        this.dcycleFH = dcycleFH;
    }

    public long getDcycleFD() {
        return dcycleFD;
    }

    public void setDcycleFD(long dcycleFD) {
        this.dcycleFD = dcycleFD;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (registrationNo != null ? registrationNo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aircraft)) {
            return false;
        }
        Aircraft other = (Aircraft) object;
        if ((this.registrationNo == null && other.registrationNo != null) || (this.registrationNo != null && !this.registrationNo.equals(other.registrationNo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.APS.Aircraft[ id=" + registrationNo + " ]";
    }

}
