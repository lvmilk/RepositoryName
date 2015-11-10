/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.ADS;

import Entity.AIS.FlightCabin;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author wang
 */
@Entity
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String SeatNumberToPassenger;
    private int rowNo;
    private int colNo;
    private int width;
    private String status;
    private int luggageCount=2;
    
    
    @OneToOne(mappedBy="seat")
    private Ticket ticket;
    @ManyToOne
    private FlightCabin flightCabin;

    public FlightCabin getFlightCabin() {
        return flightCabin;
    }

    public void setFlightCabin(FlightCabin flightCabin) {
        this.flightCabin = flightCabin;
    }
    
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public int getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(int luggageCount) {
        this.luggageCount = luggageCount;
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
        if (!(object instanceof Seat)) {
            return false;
        }
        Seat other = (Seat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Seat[ id=" + id + " ]";
    }

    /**
     * @return the SeatNumberToPassenger
     */
    public String getSeatNumberToPassenger() {
        return SeatNumberToPassenger;
    }

    /**
     * @param SeatNumberToPassenger the SeatNumberToPassenger to set
     */
    public void setSeatNumberToPassenger(String SeatNumberToPassenger) {
        this.SeatNumberToPassenger = SeatNumberToPassenger;
    }

    public int getRowNo() {
        return rowNo;
    }

    public void setRowNo(int rowNo) {
        this.rowNo = rowNo;
    }

    public int getColNo() {
        return colNo;
    }

    public void setColNo(int colNo) {
        this.colNo = colNo;
    }

    
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

   

   


//    /**
//     * @return the flightBookingRecord
//     */
//    public FlightBookingRecord getFlightBookingRecord() {
//        return flightBookingRecord;
//    }
//
//    /**
//     * @param flightBookingRecord the flightBookingRecord to set
//     */
//    public void setFlightBookingRecord(FlightBookingRecord flightBookingRecord) {
//        this.flightBookingRecord = flightBookingRecord;
//    }

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
    
}
