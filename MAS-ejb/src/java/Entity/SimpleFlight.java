/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class SimpleFlight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private GenericFlight genericFlight;
    @OneToOne
    private Aircraft aircraft;
    private String operationStatus;
    private String flightStatus;
    private String EstimatedDepartureTime;
    private String EstimatedArrivalTime;
    private String ActualDepartureTime;
    private String ActualArrivalTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
        /**
     * @return the genericFlight
     */
    public GenericFlight getGenericFlight() {
        return genericFlight;
    }

    /**
     * @param genericFlight the genericFlight to set
     */
    public void setGenericFlight(GenericFlight genericFlight) {
        this.genericFlight = genericFlight;
    }

    /**
     * @return the aircraft
     */
    public Aircraft getAircraft() {
        return aircraft;
    }

    /**
     * @param aircraft the aircraft to set
     */
    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * @return the operationStatus
     */
    public String getOperationStatus() {
        return operationStatus;
    }

    /**
     * @param operationStatus the operationStatus to set
     */
    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    /**
     * @return the flightStatus
     */
    public String getFlightStatus() {
        return flightStatus;
    }

    /**
     * @param flightStatus the flightStatus to set
     */
    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * @return the EstimatedDepartureTime
     */
    public String getEstimatedDepartureTime() {
        return EstimatedDepartureTime;
    }

    /**
     * @param EstimatedDepartureTime the EstimatedDepartureTime to set
     */
    public void setEstimatedDepartureTime(String EstimatedDepartureTime) {
        this.EstimatedDepartureTime = EstimatedDepartureTime;
    }

    /**
     * @return the EstimatedArrivalTime
     */
    public String getEstimatedArrivalTime() {
        return EstimatedArrivalTime;
    }

    /**
     * @param EstimatedArrivalTime the EstimatedArrivalTime to set
     */
    public void setEstimatedArrivalTime(String EstimatedArrivalTime) {
        this.EstimatedArrivalTime = EstimatedArrivalTime;
    }

    /**
     * @return the ActualDepartureTime
     */
    public String getActualDepartureTime() {
        return ActualDepartureTime;
    }

    /**
     * @param ActualDepartureTime the ActualDepartureTime to set
     */
    public void setActualDepartureTime(String ActualDepartureTime) {
        this.ActualDepartureTime = ActualDepartureTime;
    }

    /**
     * @return the ActualArrivalTime
     */
    public String getActualArrivalTime() {
        return ActualArrivalTime;
    }

    /**
     * @param ActualArrivalTime the ActualArrivalTime to set
     */
    public void setActualArrivalTime(String ActualArrivalTime) {
        this.ActualArrivalTime = ActualArrivalTime;
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
        if (!(object instanceof SimpleFlight)) {
            return false;
        }
        SimpleFlight other = (SimpleFlight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.SimpleFlight[ id=" + id + " ]";
    }
    
    public String getFlightNumber (){
        
        return genericFlight.getFlightNo();
    }
    
}
