/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AIS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author LIU YUQI'
 */
@Entity
public class BookingClass implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
   private String annotation ;
   private String cabinName;
   private Double price_percentage;
    private Double refund_percentage;
    private Double change_route_percentage;
    private Double change_date_percentage;
    private Double change_passenger_percentage;
    private Double open_jaw_percentage;
    private Double earn_mile_percentage;
    
    private Integer min_stay;   
    private Integer max_stay;
    private Integer reserve_advance; // min no. of days reservation must be made in advance to flight
    private Integer ticket_advance; //min no. of dats ticketing must be done in advance to flight
    
    private Boolean  can_standby;  // indicate whether customer can standby check-in
    private Boolean  dds_available; // whether tickets from this class is available in DDS
     private Boolean  gds_available; // whether tickets from this class is available in GDS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public Double getPrice_percentage() {
        return price_percentage;
    }

    public void setPrice_percentage(Double price_percentage) {
        this.price_percentage = price_percentage;
    }

    public Double getRefund_percentage() {
        return refund_percentage;
    }

    public void setRefund_percentage(Double refund_percentage) {
        this.refund_percentage = refund_percentage;
    }

    public Double getChange_route_percentage() {
        return change_route_percentage;
    }

    public void setChange_route_percentage(Double change_route_percentage) {
        this.change_route_percentage = change_route_percentage;
    }

    public Double getChange_date_percentage() {
        return change_date_percentage;
    }

    public void setChange_date_percentage(Double change_date_percentage) {
        this.change_date_percentage = change_date_percentage;
    }

    public Double getChange_passenger_percentage() {
        return change_passenger_percentage;
    }

    public void setChange_passenger_percentage(Double change_passenger_percentage) {
        this.change_passenger_percentage = change_passenger_percentage;
    }

    public Double getOpen_jaw_percentage() {
        return open_jaw_percentage;
    }

    public void setOpen_jaw_percentage(Double open_jaw_percentage) {
        this.open_jaw_percentage = open_jaw_percentage;
    }

    public Double getEarn_mile_percentage() {
        return earn_mile_percentage;
    }

    public void setEarn_mile_percentage(Double earn_mile_percentage) {
        this.earn_mile_percentage = earn_mile_percentage;
    }

    public Integer getMin_stay() {
        return min_stay;
    }

    public void setMin_stay(Integer min_stay) {
        this.min_stay = min_stay;
    }

    public Integer getMax_stay() {
        return max_stay;
    }

    public void setMax_stay(Integer max_stay) {
        this.max_stay = max_stay;
    }

    public Integer getReserve_advance() {
        return reserve_advance;
    }

    public void setReserve_advance(Integer reserve_advance) {
        this.reserve_advance = reserve_advance;
    }

    public Integer getTicket_advance() {
        return ticket_advance;
    }

    public void setTicket_advance(Integer ticket_advance) {
        this.ticket_advance = ticket_advance;
    }

    public Boolean getCan_standby() {
        return can_standby;
    }

    public void setCan_standby(Boolean can_standby) {
        this.can_standby = can_standby;
    }

    public Boolean getDds_available() {
        return dds_available;
    }

    public void setDds_available(Boolean dds_available) {
        this.dds_available = dds_available;
    }

    public Boolean getGds_available() {
        return gds_available;
    }

    public void setGds_available(Boolean gds_available) {
        this.gds_available = gds_available;
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
        if (!(object instanceof BookingClass)) {
            return false;
        }
        BookingClass other = (BookingClass) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.aisEntity.BookingClass[ id=" + id + " ]";
    }
    
    
    
    
}
