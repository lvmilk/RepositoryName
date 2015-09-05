package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author victor/ Xu/lucy
 */

@Entity
public class Aircraft implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(cascade={CascadeType.ALL},mappedBy="Aircraft")
    private Collection<Flight> flights=new ArrayList<Flight>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String registrationNo;
    private String manufacturerName;
    private String serialNo;
    private String status;
    private String firstFlyDate;
    private String deliveryDate;
    private String retireDate;
    private String aircraftType;
    private float maxDistance;
    private float cruiseSpeed;
    private float cruiseAltitude;
    private float aircraftLength;
    private float wingspan;
    
    private int fcSeatNo;               // for display aircraft information only
    private int bcSeatNo;
    private int pecSeatNo;
    private int ecSeatNo;
    
    @OneToMany
    private ArrayList<Seat> seatList;
    private long seatMapId;
    private long flightLogId;
    private long maintenanceLogId;
    private long transactionLogId;
    
    public void create(String registrationNo, String SerialNo, String Status,  String AirCraftType){
        this.setRegistrationNo(registrationNo);
        this.setSerialNo(SerialNo);
        this.setStatus(Status);
        this.setAircraftType(aircraftType);
    }
        
         public Collection<Flight> getFlight(){
        return flights;
    }   
    
    public void setFlight(Collection<Flight> flights){
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
     * @return the MnufacturerName
     */
    public String getMnufacturerName() {
        return manufacturerName;
    }

    /**
     * @param manufacturerName the manufacturerName to set
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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
     * @return the aircraftType
     */
    public String getAircraftType() {
        return aircraftType;
    }

    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(String aircraftType) {
        this.aircraftType = aircraftType;
    }

    /**
     * @return the maxDistance
     */
    public float getMaxDistance() {
        return maxDistance;
    }

    /**
     * @param maxDistance the maxDistance to set
     */
    public void setMaxDistance(float maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * @return the cruiseSpeed
     */
    public float getCruiseSpeed() {
        return cruiseSpeed;
    }

    /**
     * @param cruiseSpeed the cruiseSpeed to set
     */
    public void setCruiseSpeed(float cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    /**
     * @return the cruiseAltitude
     */
    public float getCruiseAltitude() {
        return cruiseAltitude;
    }

    /**
     * @param cruiseAltitude the cruiseAltitude to set
     */
    public void setCruiseAltitude(float cruiseAltitude) {
        this.cruiseAltitude = cruiseAltitude;
    }

    /**
     * @return the aircraftLength
     */
    public float getAircraftLength() {
        return aircraftLength;
    }

    /**
     * @param aircraftLength the aircraftLength to set
     */
    public void setAircraftLength(float aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    /**
     * @return the wingspan
     */
    public float getWingspan() {
        return wingspan;
    }

    /**
     * @param wingspan the wingspan to set
     */
    public void setWingspan(float wingspan) {
        this.wingspan = wingspan;
    }

    /**
     * @return the fcSeatNo
     */
    public int getFcSeatNo() {
        return fcSeatNo;
    }

    /**
     * @param fcSeatNo the fcSeatNo to set
     */
    public void setFcSeatNo(int fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    /**
     * @return the bcSeatNo
     */
    public int getBcSeatNo() {
        return bcSeatNo;
    }

    /**
     * @param bcSeatNo the bcSeatNo to set
     */
    public void setBcSeatNo(int bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    /**
     * @return the pecSeatNo
     */
    public int getPecSeatNo() {
        return pecSeatNo;
    }

    /**
     * @param pecSeatNo the pecSeatNo to set
     */
    public void setPecSeatNo(int pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    /**
     * @return the ecSeatNo
     */
    public int getEcSeatNo() {
        return ecSeatNo;
    }

    /**
     * @param ecSeatNo the ecSeatNo to set
     */
    public void setEcSeatNo(int ecSeatNo) {
        this.ecSeatNo = ecSeatNo;
    }

    /**
     * @return the seatList
     */
    public ArrayList<Seat> getSeatList() {
        return seatList;
    }

    /**
     * @param seatList the seatList to set
     */
    public void setSeatList(ArrayList<Seat> seatList) {
        this.seatList = seatList;
    }

    /**
     * @return the seatMapId
     */
    public long getSeatMapId() {
        return seatMapId;
    }

    /**
     * @param seatMapId the seatMapId to set
     */
    public void setSeatMapId(long seatMapId) {
        this.seatMapId = seatMapId;
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
