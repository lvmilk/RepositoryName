package Entity;

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
 * @author victor/ Xu/lucy
 */

@Entity
public class Aircraft_ implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String registrationNo;
    private String serialNo;
    private String status;
    private String firstFlyDate;
    private String deliveryDate;
    private String retireDate;

    private long flightLogId;
    private long maintenanceLogId;
    private long transactionLogId;
    
      @ManyToOne
      private AircraftType_ aircraftType = new AircraftType_();
      public AircraftType_ getAircraftType(){
        return aircraftType;
}
      public void setAircraftType(AircraftType_ aircraftType){
        this.aircraftType=aircraftType;
    }
  
    @OneToMany(cascade={CascadeType.ALL},mappedBy="Aircraft")
    private Collection<Flight_> flights = new ArrayList<Flight_>();
    
    public Collection<Flight_> getFlight(){
        return flights;
    }   
    
    public void setFlight(Collection<Flight_> flights){
        this.flights=flights;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Aircraft_)) {
            return false;
        }
        Aircraft_ other = (Aircraft_) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Aircraft[ id=" + id + " ]";
    }

    /**
     * @return the registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

   
    /**
     * @return the serialNo
     */
    public String getSerialNo() {
        return serialNo;
    }

    /**
     * @param serialNo the serialNo to set
     */
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the firstFlyDate
     */
    public String getFirstFlyDate() {
        return firstFlyDate;
    }

    /**
     * @param firstFlyDate the firstFlyDate to set
     */
    public void setFirstFlyDate(String firstFlyDate) {
        this.firstFlyDate = firstFlyDate;
    }

    /**
     * @return the deliveryDate
     */
    public String getDeliveryDate() {
        return deliveryDate;
    }

    /**
     * @param deliveryDate the deliveryDate to set
     */
    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    /**
     * @return the retireDate
     */
    public String getRetireDate() {
        return retireDate;
    }

    /**
     * @param retireDate the retireDate to set
     */
    public void setRetireDate(String retireDate) {
        this.retireDate = retireDate;
    }

    /**
     * @return the flightLogId
     */
    public long getFlightLogId() {
        return flightLogId;
    }

    /**
     * @param flightLogId the flightLogId to set
     */
    public void setFlightLogId(long flightLogId) {
        this.flightLogId = flightLogId;
    }

    /**
     * @return the MaintanencLogId
     */
    public long getMaintenanceLogId() {
        return maintenanceLogId;
    }

    /**
     * @param maintenanceLogId the maintenanceLogId to set
     */
    public void setMaintanencLogId(long maintenanceLogId) {
        this.maintenanceLogId = maintenanceLogId;
    }

    /**
     * @return the transactionLogId
     */
    public long getTransactionLogId() {
        return transactionLogId;
    }

    /**
     * @param transactionLogId the transactionLogId to set
     */
    public void setTransactionLogId(long transactionLogId) {
        this.transactionLogId = transactionLogId;
    }
    
}
