/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AIS;

import Entity.ADS.Ticket;
import Entity.ADS.Reservation;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author wang
 */
@Entity
public class BookingClassInstance implements Serializable, Comparable<BookingClassInstance> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private FlightCabin flightCabin;
    private Double price;
    private Integer seatNo;
    private Integer optimalSeatNo;
    private Integer bookedSeatNo = 0; //set default value =0
    private Integer avgDemand;
    private Integer std;

    @ManyToOne
    private BookingClass bookingClass;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "bkInstance")
    private Collection<Ticket> itinaryList;
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="bkclassInstance")
    private Collection<Reservation> reservation;

    public Integer getBookedSeatNo() {
        return bookedSeatNo;
    }

    public void setBookedSeatNo(Integer bookedSeatNo) {
        this.bookedSeatNo = bookedSeatNo;
    }

    public BookingClass getBookingClass() {
        return bookingClass;
    }

    public void setBookingClass(BookingClass bookingClass) {
        this.bookingClass = bookingClass;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        System.out.println("BKI Entity class :setPrice" + price);
        this.price = price;
    }

    public FlightCabin getFlightCabin() {
        return flightCabin;
    }

    public void setFlightCabin(FlightCabin flightCabin) {
        this.flightCabin = flightCabin;
    }

    public Integer getAvgDemand() {
        return avgDemand;
    }

    public void setAvgDemand(Integer avgDemand) {
        this.avgDemand = avgDemand;
    }

    public Integer getStd() {
        return std;
    }

    public void setStd(Integer std) {
        this.std = std;
    }

    public Integer getOptimalSeatNo() {
        return optimalSeatNo;
    }

    public void setOptimalSeatNo(Integer optimalSeatNo) {
        this.optimalSeatNo = optimalSeatNo;
    }

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
        if (!(object instanceof BookingClassInstance)) {
            return false;
        }
        BookingClassInstance other = (BookingClassInstance) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AIS.BookingClassInstance[ id=" + id + " ]";
    }

    @Override
    public int compareTo(BookingClassInstance Bi) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        return this.price.compareTo(Bi.getPrice());

    }

    /**
     * @return the itinaryList
     */
    public Collection<Ticket> getItinaryList() {
        return itinaryList;
    }

    /**
     * @param itinaryList the itinaryList to set
     */
    public void setItinaryList(Collection<Ticket> itinaryList) {
        this.itinaryList = itinaryList;
    }

    /**
     * @return the reservation
     */
    public Collection<Reservation> getReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(Collection<Reservation> reservation) {
        this.reservation = reservation;
    }

}
