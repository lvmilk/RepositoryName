/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import Entity.ADS.Seat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author LI HAO
 */
@Entity
public class GDSTicket implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketID;
    private String depCity;
    private String arrCity;
    private String depTime;
    private String arrTime;
    private String flightNo;
    private boolean checkinStatus;
    private String bookSystem;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookDate;
    private Double price;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Seat seat;

    @ManyToOne
    private GDSPassenger passenger;

    @ManyToOne
    private GDSReservation rsv;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketID != null ? ticketID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GDSTicket)) {
            return false;
        }
        GDSTicket other = (GDSTicket) object;
        if ((this.ticketID == null && other.ticketID != null) || (this.ticketID != null && !this.ticketID.equals(other.ticketID))) {
            return false;
        }
        return true;
    }

    public GDSTicket() {
    }

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public String getDepCity() {
        return depCity;
    }

    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    public String getArrCity() {
        return arrCity;
    }

    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    public String getDepTime() {
        return depTime;
    }

    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    public String getArrTime() {
        return arrTime;
    }

    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public boolean isCheckinStatus() {
        return checkinStatus;
    }

    public void setCheckinStatus(boolean checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

    public String getBookSystem() {
        return bookSystem;
    }

    public void setBookSystem(String bookSystem) {
        this.bookSystem = bookSystem;
    }

    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public GDSPassenger getPassenger() {
        return passenger;
    }

    public void setPassenger(GDSPassenger passenger) {
        this.passenger = passenger;
    }

    public GDSReservation getRsv() {
        return rsv;
    }

    public void setRsv(GDSReservation rsv) {
        this.rsv = rsv;
    }

    @Override
    public String toString() {
        return "Entity.GDS.GDSTicket[ id=" + ticketID + " ]";
    }

}
