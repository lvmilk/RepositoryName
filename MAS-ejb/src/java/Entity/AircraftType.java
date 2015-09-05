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
 * @author victor/lucy
 */
@Entity
public class AircraftType implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String aircraftType;

    private String manufacturerName;
    private Double maxDistance;
    private Double cruiseSpeed;
    private Double cruiseAltitude;
    private Double aircraftLength;
    private Double wingspan;    

    private int fcSeatNo;               // for display aircraft information only
    private int bcSeatNo;
    private int pecSeatNo;
    private int ecSeatNo;
   
    @OneToMany(cascade={CascadeType.ALL},mappedBy="AircraftType")
    private Collection<Aircraft> aircrafts=new ArrayList<Aircraft>();
    
    public Collection<Aircraft> getAircraft(){
            return aircrafts;
            }
    
        public void setAircraft(Collection<Aircraft> aircrafts){
        this.aircrafts=aircrafts;
    }
    
    @OneToMany
    private ArrayList<Seat> seatList;
    private long seatMapId;
    
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
        if (!(object instanceof AircraftType)) {
            return false;
        }
        AircraftType other = (AircraftType) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AircraftType[ id=" + id + " ]";
    }

    /**
     * @return the manufacturerName
     */
    public String getManufacturerName() {
        return manufacturerName;
    }

    /**
     * @param manufacturerName the manufacturerName to set
     */
    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
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
    public Double getMaxDistance() {
        return maxDistance;
    }

    /**
     * @param maxDistance the maxDistance to set
     */
    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    /**
     * @return the cruiseSpeed
     */
    public Double getCruiseSpeed() {
        return cruiseSpeed;
    }

    /**
     * @param cruiseSpeed the cruiseSpeed to set
     */
    public void setCruiseSpeed(Double cruiseSpeed) {
        this.cruiseSpeed = cruiseSpeed;
    }

    /**
     * @return the cruiseAltitude
     */
    public Double getCruiseAltitude() {
        return cruiseAltitude;
    }

    /**
     * @param cruiseAltitude the cruiseAltitude to set
     */
    public void setCruiseAltitude(Double cruiseAltitude) {
        this.cruiseAltitude = cruiseAltitude;
    }

    /**
     * @return the aircraftLength
     */
    public Double getAircraftLength() {
        return aircraftLength;
    }

    /**
     * @param aircraftLength the aircraftLength to set
     */
    public void setAircraftLength(Double aircraftLength) {
        this.aircraftLength = aircraftLength;
    }

    /**
     * @return the wingspan
     */
    public Double getWingspan() {
        return wingspan;
    }

    /**
     * @param wingspan the wingspan to set
     */
    public void setWingspan(Double wingspan) {
        this.wingspan = wingspan;
    }

    /**
     * @return the fcSeatNo
     */
    public Integer getFcSeatNo() {
        return fcSeatNo;
    }

    /**
     * @param fcSeatNo the fcSeatNo to set
     */
    public void setFcSeatNo(Integer fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    /**
     * @return the bcSeatNo
     */
    public Integer getBcSeatNo() {
        return bcSeatNo;
    }

    /**
     * @param bcSeatNo the bcSeatNo to set
     */
    public void setBcSeatNo(Integer bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    /**
     * @return the pecSeatNo
     */
    public Integer getPecSeatNo() {
        return pecSeatNo;
    }

    /**
     * @param pecSeatNo the pecSeatNo to set
     */
    public void setPecSeatNo(Integer pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    /**
     * @return the ecSeatNo
     */
    public Integer getEcSeatNo() {
        return ecSeatNo;
    }

    /**
     * @param ecSeatNo the ecSeatNo to set
     */
    public void setEcSeatNo(Integer ecSeatNo) {
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
    
}
