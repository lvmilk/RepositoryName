package Entity.APS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Lucy
 */
@Entity
public class Aircraft implements Serializable {
    //private static final long serialVersionUID = 1L;
    @Id
    private String registrationNo;
    private String serialNo;
    private String status;
    private String firstFlyDate;
    private String deliveryDate;
    private String retireDate;
    private Long flightLogId;
    private Long maintenanceLogId;
    private Long transactionLogId;
    
    @ManyToOne
    private AircraftType aircraftType = new AircraftType();
    public AircraftType getAircraftType(){
        return aircraftType;
    }
    public void setAircraftType(AircraftType aircraftType){
        this.aircraftType=aircraftType;
    }
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="Aircraft")
    private Collection<FlightSchedule> flightSchedule = new ArrayList<FlightSchedule>();
    
    public Collection<FlightSchedule> getFlightSchedule(){
        return flightSchedule;
    }   
    public void setFlightSchedule(Collection<FlightSchedule> flightSchedule){
        this.flightSchedule=flightSchedule;
    }
    
    public void create(String registrationNo,String serialNo,String status,String firstFlyDate,String deliveryDate,String retireDate,Long flightLogId,Long maintenanceLogId,Long transactionLogId){
        this.setRegistrationNo(registrationNo);
        this.setSerialNo(serialNo);
        this.setStatus(status);
        this.setFirstFlyDate(firstFlyDate);
        this.setDeliveryDate(deliveryDate);
        this.setRetireDate(retireDate);
        this.setFlightLogId(flightLogId);
        this.setMaintenanceLogId(maintenanceLogId);
        this.setTransactionLogId(transactionLogId);
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

    public Long getTransactionLogId() {
        return transactionLogId;
    }

    public void setTransactionLogId(Long transactionLogId) {
        this.transactionLogId = transactionLogId;
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
