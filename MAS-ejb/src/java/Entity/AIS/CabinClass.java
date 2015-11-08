/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AIS;

import Entity.AIS.FlightCabin;
import Entity.APS.AircraftType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

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
    
    private Double seatWidth;
    private Integer rowCount;//no. of rows
    private Integer rowSeatCount; //no. of seat counts per row
    private String rowConfig; //e.g for 7 seats/row, 2-3-2
    
    private String[][] seatChart;
    
    
    @ManyToOne
    private AircraftType aircraftType=new AircraftType();
    
    
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

    public Double getSeatWidth() {
        return seatWidth;
    }

    public void setSeatWidth(Double seatWidth) {
        this.seatWidth = seatWidth;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getRowSeatCount() {
        return rowSeatCount;
    }

    public void setRowSeatCount(Integer rowSeatCount) {
        this.rowSeatCount = rowSeatCount;
    }

    public String getRowConfig() {
        return rowConfig;
    }

    public void setRowConfig(String rowConfig) {
        this.rowConfig = rowConfig;
    }

    public String[][] getSeatChart() {
        return seatChart;
    }

    public void setSeatChart(String[][] seatChart) {
        this.seatChart = seatChart;
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
