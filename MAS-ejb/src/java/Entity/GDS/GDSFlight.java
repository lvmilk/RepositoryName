/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import Entity.AIS.CabinClass;
import com.sun.xml.bind.CycleRecoverable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author LI HAO
 */
@Entity
public class GDSFlight implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String flightNo;

    private String flightStatus;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date depTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date arrTime;

    private String depAirport;
    private String arrAirport;
    private String depIATA;
    private String arrIATA;

    private Integer bookedSeat;
    private Integer availableSeat;
    private Integer seatQuota;

    private String companyName;
    private String cabinName;
    private Double price;

    @ManyToMany(mappedBy = "gdsFlightList")
    private List<GDSReservation> rsv = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "flight")
    @XmlTransient
    private List<GDSSeat> seats = new ArrayList<>();

    public GDSFlight() {
    }

    public void createGDSFlight(String flightNo, Date depTime, Date arrTime, String depAirport, String arrAirport, String depIATA, String arrIATA, Integer seatQuota, String companyName, String cabinName, Double price) {
        this.flightNo = flightNo;
        this.depTime = depTime;
        this.arrTime = arrTime;
        this.depAirport = depAirport;
        this.arrAirport = arrAirport;
        this.depIATA = depIATA;
        this.arrIATA = arrIATA;
        this.seatQuota = seatQuota;
        this.bookedSeat = 0;
        this.availableSeat = seatQuota - bookedSeat;
        this.companyName = companyName;
        this.cabinName = cabinName;
        this.price = price;

    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Date getDepTime() {
        return depTime;
    }

    public void setDepTime(Date depTime) {
        this.depTime = depTime;
    }

    public Date getArrTime() {
        return arrTime;
    }

    public void setArrTime(Date arrTime) {
        this.arrTime = arrTime;
    }

    public String getDepAirport() {
        return depAirport;
    }

    public void setDepAirport(String depAirport) {
        this.depAirport = depAirport;
    }

    public String getArrAirport() {
        return arrAirport;
    }

    public void setArrAirport(String arrAirport) {
        this.arrAirport = arrAirport;
    }

    public Integer getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(Integer bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

//    @XmlIDREF
    @XmlTransient
    public List<GDSSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<GDSSeat> seats) {
        this.seats = seats;
    }

//    @XmlID
//    @XmlJavaTypeAdapter(value = LongAdapter.class, type = String.class)
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
        if (!(object instanceof GDSFlight)) {
            return false;
        }
        GDSFlight other = (GDSFlight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GDS.GDSFlight[ id=" + id + " ]";
    }

    /**
     * @return the availableSeat
     */
    public Integer getAvailableSeat() {
        return availableSeat;
    }

    /**
     * @param availableSeat the availableSeat to set
     */
    public void setAvailableSeat(Integer availableSeat) {
        this.availableSeat = availableSeat;
    }

    /**
     * @return the seatQuota
     */
    public Integer getSeatQuota() {
        return seatQuota;
    }

    /**
     * @param seatQuota the seatQuota to set
     */
    public void setSeatQuota(Integer seatQuota) {
        this.seatQuota = seatQuota;
    }

    /**
     * @return the depIATA
     */
    public String getDepIATA() {
        return depIATA;
    }

    /**
     * @param depIATA the depIATA to set
     */
    public void setDepIATA(String depIATA) {
        this.depIATA = depIATA;
    }

    /**
     * @return the arrIATA
     */
    public String getArrIATA() {
        return arrIATA;
    }

    /**
     * @param arrIATA the arrIATA to set
     */
    public void setArrIATA(String arrIATA) {
        this.arrIATA = arrIATA;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public List<GDSReservation> getRsv() {
        return rsv;
    }

    public void setRsv(List<GDSReservation> rsv) {
        this.rsv = rsv;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
