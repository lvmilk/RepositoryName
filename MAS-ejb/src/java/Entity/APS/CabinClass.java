/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author LIU YUQI'
 */
@Entity
public class CabinClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cabinID;
    
    private String cabinName;
    private Integer seatCount;
    private Double fullFare;
    
    @ManyToOne
    AircraftType aircraftType=new AircraftType();

    public Long getId() {
        return cabinID;
    }

    public void setId(Long id) {
        this.cabinID = id;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Double getFullFare() {
        return fullFare;
    }

    public void setFullFare(Double fullFare) {
        this.fullFare = fullFare;
    }

    public Long getCabinID() {
        return cabinID;
    }

    public void setCabinID(Long cabinID) {
        this.cabinID = cabinID;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    
    
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cabinID != null ? cabinID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabinClass)) {
            return false;
        }
        CabinClass other = (CabinClass) object;
        if ((this.cabinID == null && other.cabinID != null) || (this.cabinID != null && !this.cabinID.equals(other.cabinID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.APS.CabinClass[ id=" + cabinID + " ]";
    }
    
}
