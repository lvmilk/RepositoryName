/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.aisEntity;

import Entity.APS.CabinClass;
import Entity.APS.FlightInstance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author wang
 */
@Entity
public class FlightCabin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    Integer bookedSeat;

    
    @ManyToOne
    private FlightInstance flightInstance; 
    
    @ManyToOne
    private CabinClass cabinClass;
    
    @OneToMany(mappedBy="flightCabin")
    private List<BookingClassInstance> bookingClassInstances=new ArrayList<BookingClassInstance>();

    public List<BookingClassInstance> getBookingClassInstances() {
        return bookingClassInstances;
    }

    public void setBookingClassInstances(List<BookingClassInstance> bookingClassInstances) {
        this.bookingClassInstances = bookingClassInstances;
    }

    
    public CabinClass getCabinClass() {
        return cabinClass;
    }

    public void setCabinClass(CabinClass cabinClass) {
        this.cabinClass = cabinClass;
    }
       
    public FlightInstance getFlightInstance() {
        return flightInstance;
    }

    public void setFlightInstance(FlightInstance flightInstance) {
        this.flightInstance = flightInstance;
    }

    public Integer getBookedSeat() {
        return bookedSeat;
    }

    public void setBookedSeat(Integer bookedSeat) {
        this.bookedSeat = bookedSeat;
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
        if (!(object instanceof FlightCabin)) {
            return false;
        }
        FlightCabin other = (FlightCabin) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.aisEntity.FlightCabin[ id=" + id + " ]";
    }
    
}
