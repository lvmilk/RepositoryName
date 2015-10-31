/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.AIS.BookingClassInstance;
import Entity.APS.Seat;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class Ticket implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ticketID;
    private String depCity;
    private String arrCity;
    private String depTime;
    private String arrTime;
    private String flightNo;
    private String bookSystem;
    private boolean checkinStatus;

    @ManyToOne
    private Passenger passenger;
    
    private BookingClassInstance bkInstance=new BookingClassInstance();
    private Reservation rsv=new Reservation();
    
    @OneToOne(cascade = {CascadeType.ALL})
    private Seat seat;

    public Ticket() {
    }
    
    public void createTicket(String depCity, String arrCity, String depTime, String arrTime,String flightNo,String bookSystem)
    {
        this.depCity=depCity;
        this.arrCity=arrCity;
        this.depTime=depTime;
        this.arrTime=arrTime;
        this.flightNo=flightNo;
        this.bookSystem=bookSystem;
        this.checkinStatus=false;
    }

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ticketID != null ? ticketID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the ticketID fields are not set
        if (!(object instanceof Ticket)) {
            return false;
        }
        Ticket other = (Ticket) object;
        if ((this.ticketID == null && other.ticketID != null) || (this.ticketID != null && !this.ticketID.equals(other.ticketID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Itinerary[ id=" + ticketID + " ]";
    }

    /**
     * @return the depCity
     */
    public String getDepCity() {
        return depCity;
    }

    /**
     * @param depCity the depCity to set
     */
    public void setDepCity(String depCity) {
        this.depCity = depCity;
    }

    /**
     * @return the arrCity
     */
    public String getArrCity() {
        return arrCity;
    }

    /**
     * @param arrCity the arrCity to set
     */
    public void setArrCity(String arrCity) {
        this.arrCity = arrCity;
    }

    /**
     * @return the depTime
     */
    public String getDepTime() {
        return depTime;
    }

    /**
     * @param depTime the depTime to set
     */
    public void setDepTime(String depTime) {
        this.depTime = depTime;
    }

    /**
     * @return the arrTime
     */
    public String getArrTime() {
        return arrTime;
    }

    /**
     * @param arrTime the arrTime to set
     */
    public void setArrTime(String arrTime) {
        this.arrTime = arrTime;
    }

    /**
     * @return the passenger
     */
    public Passenger getPassenger() {
        return passenger;
    }

    /**
     * @param passenger the passenger to set
     */
    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    /**
     * @return the seat
     */
    public Seat getSeat() {
        return seat;
    }

    /**
     * @param seat the seat to set
     */
    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    /**
     * @return the bkInstance
     */
    public BookingClassInstance getBkInstance() {
        return bkInstance;
    }

    /**
     * @param bkInstance the bkInstance to set
     */
    public void setBkInstance(BookingClassInstance bkInstance) {
        this.bkInstance = bkInstance;
    }

    /**
     * @return the rsv
     */
    public Reservation getRsv() {
        return rsv;
    }

    /**
     * @param rsv the rsv to set
     */
    public void setRsv(Reservation rsv) {
        this.rsv = rsv;
    }

    /**
     * @return the flightNo
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * @param flightNo the flightNo to set
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * @return the bookSystem
     */
    public String getBookSystem() {
        return bookSystem;
    }

    /**
     * @param bookSystem the bookSystem to set
     */
    public void setBookSystem(String bookSystem) {
        this.bookSystem = bookSystem;
    }

    /**
     * @return the checkinStatus
     */
    public boolean isCheckinStatus() {
        return checkinStatus;
    }

    /**
     * @param checkinStatus the checkinStatus to set
     */
    public void setCheckinStatus(boolean checkinStatus) {
        this.checkinStatus = checkinStatus;
    }

}
