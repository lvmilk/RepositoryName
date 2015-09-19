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
import javax.persistence.OneToOne;

/**
 *
 * @author victor
 */
@Entity
public class Seat implements Serializable {
//    @OneToOne(mappedBy = "seatSelected")
//    private FlightBookingRecord flightBookingRecord;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String SeatNumberToPassenger;
    private int row;
    private int col;
    private int Width;
    private String Status;


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

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the col
     */
    public int getCol() {
        return col;
    }

    /**
     * @param col the col to set
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * @return the Width
     */
    public int getWidth() {
        return Width;
    }

    /**
     * @param Width the Width to set
     */
    public void setWidth(int Width) {
        this.Width = Width;
    }

    /**
     * @return the Status
     */
    public String getStatus() {
        return Status;
    }

    /**
     * @param Status the Status to set
     */
    public void setStatus(String Status) {
        this.Status = Status;
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
    
}
