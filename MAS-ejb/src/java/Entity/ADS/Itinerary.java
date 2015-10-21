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
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class Itinerary implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itineraryID;
    private String depCity;
    private String arrCity;
    private String depTime;
    private String arrTime;

    private Passenger passenger = new Passenger();
    private BookingClassInstance bkInstance=new BookingClassInstance();
    private Reservation rsv=new Reservation();
    
    @OneToOne(cascade = {CascadeType.ALL})
    private Seat seat;

    public Itinerary() {
    }

    public Long getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(Long itineraryID) {
        this.itineraryID = itineraryID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (itineraryID != null ? itineraryID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the itineraryID fields are not set
        if (!(object instanceof Itinerary)) {
            return false;
        }
        Itinerary other = (Itinerary) object;
        if ((this.itineraryID == null && other.itineraryID != null) || (this.itineraryID != null && !this.itineraryID.equals(other.itineraryID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Itinerary[ id=" + itineraryID + " ]";
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

}
