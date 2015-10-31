package Entity.APS;

import Entity.AFOS.Maintenance;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;
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
//    private Long flightLogId;
//    private Long maintenanceLogId;
    private String currentAirport;

    //----------------------After 1st release
    private Integer yearDiff; 
 //  private long accumFlyMinutes;
    private long acycleFM = 0;
//    private long acycleFD = 0;
    private long acycleFC = 0;
    private long bcycleFM = 0;
//    private long bcycleFD = 0;
    private long bcycleFC = 0;
    private long ccycleFM = 0;
//    private long ccycleFD = 0;
    private long ccycleFC = 0;
    private long dcycleFM = 0;
//    private long dcycleFD = 0;
    private long dcycleFC = 0;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "aircraft")
    private List<Maintenance> maintenanceList= new ArrayList<>();

    @ManyToOne
    private AircraftType aircraftType = new AircraftType();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "aircraft")
    private List<FlightInstance> flightInstance = new ArrayList<>();

    public void create(String registrationNo, String status, String firstFlyDate, String deliveryDate, String retireDate) {
        this.setRegistrationNo(registrationNo);
//        this.setSerialNo(serialNo);
        this.setStatus(status);
        this.setFirstFlyDate(firstFlyDate);
        this.setDeliveryDate(deliveryDate);
        this.setRetireDate(retireDate);
        Integer start = Integer.parseInt(deliveryDate.substring(0, 4));
        Integer end = Integer.parseInt(retireDate.substring(0, 4));
        yearDiff = end - start;
        this.setYearDiff(yearDiff);
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

//    public Long getFlightLogId() {
//        return flightLogId;
//    }
//
//    public void setFlightLogId(Long flightLogId) {
//        this.flightLogId = flightLogId;
//    }
//
//    public Long getMaintenanceLogId() {
//        return maintenanceLogId;
//    }
//
//    public void setMaintenanceLogId(Long maintenanceLogId) {
//        this.maintenanceLogId = maintenanceLogId;
//    }

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

    public List<Maintenance> getMaintenanceList() {
        return maintenanceList;
    }

    public void setMaintenanceList(List<Maintenance> maintenanceList) {
        this.maintenanceList = maintenanceList;
    }

    public long getAcycleFM() {
        return acycleFM;
    }

    public void setAcycleFM(long acycleFM) {
        this.acycleFM = acycleFM;
    }

//    public long getAcycleFD() {
//        return acycleFD;
//    }
//
//    public void setAcycleFD(long acycleFD) {
//        this.acycleFD = acycleFD;
//    }

    public long getBcycleFM() {
        return bcycleFM;
    }

    public void setBcycleFM(long bcycleFM) {
        this.bcycleFM = bcycleFM;
    }

//    public long getBcycleFD() {
//        return bcycleFD;
//    }
//
//    public void setBcycleFD(long bcycleFD) {
//        this.bcycleFD = bcycleFD;
//    }

    public long getCcycleFM() {
        return ccycleFM;
    }

    public void setCcycleFM(long ccycleFM) {
        this.ccycleFM = ccycleFM;
    }

//    public long getCcycleFD() {
//        return ccycleFD;
//    }
//
//    public void setCcycleFD(long ccycleFD) {
//        this.ccycleFD = ccycleFD;
//    }

    public long getDcycleFM() {
        return dcycleFM;
    }

    public void setDcycleFM(long dcycleFM) {
        this.dcycleFM = dcycleFM;
    }

//    public long getDcycleFD() {
//        return dcycleFD;
//    }
//
//    public void setDcycleFD(long dcycleFD) {
//        this.dcycleFD = dcycleFD;
//    }

    public long getAcycleFC() {
        return acycleFC;
    }

    public void setAcycleFC(long acycleFC) {
        this.acycleFC = acycleFC;
    }

    public long getBcycleFC() {
        return bcycleFC;
    }

    public void setBcycleFC(long bcycleFC) {
        this.bcycleFC = bcycleFC;
    }

    public long getCcycleFC() {
        return ccycleFC;
    }

    public void setCcycleFC(long ccycleFC) {
        this.ccycleFC = ccycleFC;
    }

    public long getDcycleFC() {
        return dcycleFC;
    }

    public void setDcycleFC(long dcycleFC) {
        this.dcycleFC = dcycleFC;
    }

    public Integer getYearDiff() {
        return yearDiff;
    }

    public void setYearDiff(Integer yearDiff) {
        this.yearDiff = yearDiff;
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
