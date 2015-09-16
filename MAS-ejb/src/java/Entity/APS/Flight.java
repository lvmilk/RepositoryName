package Entity.APS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lucy
 */
@Entity
public class Flight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String flightNo;
    private Integer stopoverNo;
    
    //ER with aircraft
    @ManyToOne
      private Aircraft aircraft = new Aircraft();
      public Aircraft getAircraft(){
        return aircraft;
    }
      public void setAircraft(Aircraft aircraft){
        this.aircraft=aircraft;
    }
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Integer getStopoverNo() {
        return stopoverNo;
    }

    public void setStopoverNo(Integer stopoverNo) {
        this.stopoverNo = stopoverNo;
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
        return "Entity.APS.Flight[ id=" + id + " ]";
    }
    
}
