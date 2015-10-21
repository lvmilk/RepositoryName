/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author LI HAO
 */
@Entity
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airlineRsvCode;

    public Long getAirlineRsvCode() {
        return airlineRsvCode;
    }

    public void setAirlineRsvCode(Long airlineRsvCode) {
        this.airlineRsvCode = airlineRsvCode;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airlineRsvCode != null ? airlineRsvCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the airlineRsvCode fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.airlineRsvCode == null && other.airlineRsvCode != null) || (this.airlineRsvCode != null && !this.airlineRsvCode.equals(other.airlineRsvCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Reservation[ id=" + airlineRsvCode + " ]";
    }
    
}
