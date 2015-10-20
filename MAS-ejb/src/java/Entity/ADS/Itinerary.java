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
public class Itinerary implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long itineraryID;

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
    
}
