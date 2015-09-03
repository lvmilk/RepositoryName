/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
public class Flight implements Serializable {
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
    
    @OneToMany
    private Collection<FlightBookingRecord> booking;
    @OneToMany
    private Collection<FlightCheckInRecord> CI;
    

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
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Flight[ id=" + id + " ]";
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

    /**
     * @return the booking
     */
    public Collection<FlightBookingRecord> getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Collection<FlightBookingRecord> booking) {
        this.booking = booking;
    }

    /**
     * @return the CI
     */
    public Collection<FlightCheckInRecord> getCI() {
        return CI;
    }

    /**
     * @param CI the CI to set
     */
    public void setCI(Collection<FlightCheckInRecord> CI) {
        this.CI = CI;
    }
    
}