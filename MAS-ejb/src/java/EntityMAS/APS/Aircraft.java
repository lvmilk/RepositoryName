package EntityMAS.APS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
public class Aircraft implements Serializable {
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
    
    
    private AircraftType aircraftType = new AircraftType();
    private List<Flight> flights = new ArrayList<Flight>();
    
    

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
        if (!(object instanceof Aircraft)) {
            return false;
        }
        Aircraft other = (Aircraft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        String st = "";
        st += this.id + "\t";
        st += this.registrationNo + "\t";
        st += this.serialNo + "\t";
        st += this.status + "\t";
        st += this.firstFlyDate + "\t";
        st += this.deliveryDate + "\t";
        st += this.retireDate + "\t";
        st += this.aircraftType.getType() + "\t";
        return st;
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
    
    @ManyToOne
    public AircraftType getAircraftType(){
        return aircraftType;
    }
    public void setAircraftType(AircraftType aircraftType){
        this.aircraftType=aircraftType;
    }
    
    @OneToMany(cascade={CascadeType.PERSIST},mappedBy="aircraft")
    public List<Flight> getFlight(){
        return flights;
    }   
    public void setFlight(List<Flight> flights){
        this.flights=flights;
    }
    public void addFlight(Flight fl){
        fl.setAircraft(this);
        if (fl.getOperationStatus().equalsIgnoreCase("PLANNED"))
            fl.setOperationStatus("SCHEDULED");
        this.flights.add(fl);
    }
    public String printFlightList(){
        String result = "Flight List:\n";
        try{
            for (Flight fl: flights){
                result += fl.toString() + "\n";
            }
        }catch(Exception e){
        }
        return result;
    }
    
}
