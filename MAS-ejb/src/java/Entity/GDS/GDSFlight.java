/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date flightDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finishDate;
    private String flightStatus;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date estimatedDepTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date estimatedArrTime;
    private Integer estimatedDateAdjust;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date actualDepTime;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date actualArrTime;
    private Integer actualDateAdjust;
    private String depAirport;
    private String arrAirport;
    private Long bookedSeat;
    
    @ManyToOne
    private Airline airline;
    
    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "flight")
    private List<GDSSeat> seats=new ArrayList<>();

    public GDSFlight() {
    }

   

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Date getEstimatedDepTime() {
        return estimatedDepTime;
    }

    public void setEstimatedDepTime(Date estimatedDepTime) {
        this.estimatedDepTime = estimatedDepTime;
    }

    public Date getEstimatedArrTime() {
        return estimatedArrTime;
    }

    public void setEstimatedArrTime(Date estimatedArrTime) {
        this.estimatedArrTime = estimatedArrTime;
    }

    public Integer getEstimatedDateAdjust() {
        return estimatedDateAdjust;
    }

    public void setEstimatedDateAdjust(Integer estimatedDateAdjust) {
        this.estimatedDateAdjust = estimatedDateAdjust;
    }

    public Date getActualDepTime() {
        return actualDepTime;
    }

    public void setActualDepTime(Date actualDepTime) {
        this.actualDepTime = actualDepTime;
    }

    public Date getActualArrTime() {
        return actualArrTime;
    }

    public void setActualArrTime(Date actualArrTime) {
        this.actualArrTime = actualArrTime;
    }

    public Integer getActualDateAdjust() {
        return actualDateAdjust;
    }

    public void setActualDateAdjust(Integer actualDateAdjust) {
        this.actualDateAdjust = actualDateAdjust;
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

    public Long getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(Long bookedSeat) {
        this.bookedSeat = bookedSeat;
    }

    public Airline getAirline() {
        return airline;
    }

    public void setAirline(Airline airline) {
        this.airline = airline;
    }

    public List<GDSSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<GDSSeat> seats) {
        this.seats = seats;
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

}
