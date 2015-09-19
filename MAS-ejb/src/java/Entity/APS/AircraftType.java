package Entity.APS;

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
    private String type;

    private String manufacturerName;
    private Double maxDistance;
    private Double cruiseSpeed;
    private Double cruiseAltitude;
    private Double aircraftLength;
    private Double wingspan;
    private String minAirspaceClassReq;

    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Aircraft> aircrafts ;
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Seat> SuiteSeatList = new ArrayList<Seat>();
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Seat> fcSeatList = new ArrayList<Seat>();
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Seat> bcSeatList = new ArrayList<Seat>();
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Seat> pecSeatList = new ArrayList<Seat>();
    @OneToMany(cascade = {CascadeType.ALL})
    private Collection<Seat> ecSeatList = new ArrayList<Seat>();

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
        String st = "";
        st += this.id + "\t";
        st += this.type + "\t";
        st += this.manufacturerName + "\t";
        st += this.maxDistance + "\t";
        st += this.cruiseSpeed + "\t";
        st += this.cruiseAltitude + "\t";
        st += this.aircraftLength + "\t";
        st += this.wingspan + "\t";
        return st;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
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

    public Collection<Aircraft> getAircraft() {
        return aircrafts;
    }

    public void setAircraft(Collection<Aircraft> aircrafts) {
        this.aircrafts = aircrafts;
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

//    /**
//     * @return the seatList
//     */
//    public Collection<Seat> getSeatList() {
//        return seatList;
//    }
//
//    /**
//     * @param seatList the seatList to set
//     */
//    public void setSeatList(Collection<Seat> seatList) {
//        this.seatList = seatList;
//    }
    /**
     * @return the SuiteSeatList
     */
    public Collection<Seat> getSuiteSeatList() {
        return SuiteSeatList;
    }

    /**
     * @param SuiteSeatList the SuiteSeatList to set
     */
    public void setSuiteSeatList(Collection<Seat> SuiteSeatList) {
        this.SuiteSeatList = SuiteSeatList;
    }

    /**
     * @return the fcSeatList
     */
    public Collection<Seat> getFcSeatList() {
        return fcSeatList;
    }

    /**
     * @param fcSeatList the fcSeatList to set
     */
    public void setFcSeatList(Collection<Seat> fcSeatList) {
        this.fcSeatList = fcSeatList;
    }

    /**
     * @return the bcSeatList
     */
    public Collection<Seat> getBcSeatList() {
        return bcSeatList;
    }

    /**
     * @param bcSeatList the bcSeatList to set
     */
    public void setBcSeatList(Collection<Seat> bcSeatList) {
        this.bcSeatList = bcSeatList;
    }

    /**
     * @return the pecSeatList
     */
    public Collection<Seat> getPecSeatList() {
        return pecSeatList;
    }

    /**
     * @param pecSeatList the pecSeatList to set
     */
    public void setPecSeatList(Collection<Seat> pecSeatList) {
        this.pecSeatList = pecSeatList;
    }

    /**
     * @return the ecSeatList
     */
    public Collection<Seat> getEcSeatList() {
        return ecSeatList;
    }

    /**
     * @param ecSeatList the ecSeatList to set
     */
    public void setEcSeatList(Collection<Seat> ecSeatList) {
        this.ecSeatList = ecSeatList;
    }

    /**
     * @return the minAirspaceClassReq
     */
    public String getMinAirspaceClassReq() {
        return minAirspaceClassReq;
    }

    /**
     * @param minAirspaceClassReq the minAirspaceClassReq to set
     */
    public void setMinAirspaceClassReq(String minAirspaceClassReq) {
        this.minAirspaceClassReq = minAirspaceClassReq;
    }

}
