package Entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.*;

/**
 *
 * @author victor/lucy
 */
@Entity
public class Flight extends SimpleFlight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
     @ManyToOne
      private Aircraft_ aircraft = new Aircraft_();
      public Aircraft_ getAircraft(){
        return aircraft;
}
      public void setAircraft(Aircraft_ aircraft){
        this.aircraft=aircraft;
    }
     
    @OneToMany
    private Collection<FlightBookingRecord> booking;
    @OneToMany
    private Collection<FlightCheckInRecord> CI;

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
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Flight[ id=" + id + " ]";
    }



    /**
     * @return the booking
     */
    public Collection<FlightBookingRecord> getBooking() {
        return booking;
    }

    /**
     * @param booking the booking to set
     */
    public void setBooking(Collection<FlightBookingRecord> booking) {
        this.booking = booking;
    }

    /**
     * @return the CI
     */
    public Collection<FlightCheckInRecord> getCI() {
        return CI;
    }

    /**
     * @param CI the CI to set
     */
    public void setCI(Collection<FlightCheckInRecord> CI) {
        this.CI = CI;
    }
    
    
    
}
