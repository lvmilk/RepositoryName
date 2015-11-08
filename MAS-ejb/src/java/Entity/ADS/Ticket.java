/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.AIS.BookingClassInstance;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

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
    
    private String ticketStatus;
    private Double price;
    private String bookSystem;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date bookDate;

    @ManyToOne
    private Passenger passenger;
    
    
    @ManyToOne
    private Reservation rsv;
    
    @OneToOne(cascade = {CascadeType.PERSIST})
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
        this.setBookDate(Calendar.getInstance().getTime());
        this.ticketStatus="Unused";
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
        return "Entity.ADS.Ticket[ id=" + ticketID + " ]";
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



    public Date getBookDate() {
        return bookDate;
    }

    public void setBookDate(Date bookDate) {
        this.bookDate = bookDate;
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

    public String getTicketStatus() {
        return ticketStatus;
    }

    public void setTicketStatus(String ticketStatus) {
        this.ticketStatus = ticketStatus;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
