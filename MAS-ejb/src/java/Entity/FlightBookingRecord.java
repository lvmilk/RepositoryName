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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class FlightBookingRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Flight flight;
    @ManyToOne
    private Ticket ticket;
    private String BookingClass;
    private Double fare;
    private Double tax;
    
    private String ffpNo;
    private Integer MileAccumulation;
    
    @OneToOne
    private Seat seatSelected;
    private Integer speacialMealIndex;
    
    private String operationStatus;
    
    @ManyToOne
    private Flight connectedPriorFlght;
    @ManyToOne
    private Flight connectedSubsequnecFlight;

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
        if (!(object instanceof FlightBookingRecord)) {
            return false;
        }
        FlightBookingRecord other = (FlightBookingRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.FlightBookingRecord[ id=" + id + " ]";
    }

    /**
     * @return the flight
     */
    public Flight getFlight() {
        return flight;
    }

    /**
     * @param flight the flight to set
     */
    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    /**
     * @return the ticket
     */
    public Ticket getTicket() {
        return ticket;
    }

    /**
     * @param ticket the ticket to set
     */
    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    /**
     * @return the BookingClass
     */
    public String getBookingClass() {
        return BookingClass;
    }

    /**
     * @param BookingClass the BookingClass to set
     */
    public void setBookingClass(String BookingClass) {
        this.BookingClass = BookingClass;
    }

    /**
     * @return the ffpNo
     */
    public String getFfpNo() {
        return ffpNo;
    }

    /**
     * @param ffpNo the ffpNo to set
     */
    public void setFfpNo(String ffpNo) {
        this.ffpNo = ffpNo;
    }

    /**
     * @return the MileAccumulation
     */
    public Integer getMileAccumulation() {
        return MileAccumulation;
    }

    /**
     * @param MileAccumulation the MileAccumulation to set
     */
    public void setMileAccumulation(Integer MileAccumulation) {
        this.MileAccumulation = MileAccumulation;
    }

    /**
     * @return the seatSelected
     */
    public Seat getSeatSelected() {
        return seatSelected;
    }

    /**
     * @param seatSelected the seatSelected to set
     */
    public void setSeatSelected(Seat seatSelected) {
        this.seatSelected = seatSelected;
    }

    /**
     * @return the speacialMealIndex
     */
    public Integer getSpeacialMealIndex() {
        return speacialMealIndex;
    }

    /**
     * @param speacialMealIndex the speacialMealIndex to set
     */
    public void setSpeacialMealIndex(Integer speacialMealIndex) {
        this.speacialMealIndex = speacialMealIndex;
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
     * @return the connectedPriorFlght
     */
    public Flight getConnectedPriorFlght() {
        return connectedPriorFlght;
    }

    /**
     * @param connectedPriorFlght the connectedPriorFlght to set
     */
    public void setConnectedPriorFlght(Flight connectedPriorFlght) {
        this.connectedPriorFlght = connectedPriorFlght;
    }

    /**
     * @return the connectedSubsequnecFlight
     */
    public Flight getConnectedSubsequnecFlight() {
        return connectedSubsequnecFlight;
    }

    /**
     * @param connectedSubsequnecFlight the connectedSubsequnecFlight to set
     */
    public void setConnectedSubsequnecFlight(Flight connectedSubsequnecFlight) {
        this.connectedSubsequnecFlight = connectedSubsequnecFlight;
    }
    
}
