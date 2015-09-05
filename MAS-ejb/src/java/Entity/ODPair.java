/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author victor/ Xu
 */
@Entity
public class ODPair implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Airport origin;
    @ManyToOne
    private Airport destination;
    
    private float distance;
    private float basicFirstClassFare;
    private float basicBusinessClassFare;
    private float basicPremiumEconomyClassFare;
    private float basicEconomyClassFare;
    
    private boolean canEBP;
    

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
        if (!(object instanceof ODPair)) {
            return false;
        }
        ODPair other = (ODPair) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ODPair[ id=" + id + " ]";
    }

    /**
     * @return the origin
     */
    public Airport getOrigin() {
        return origin;
    }

    /**
     * @param origin the origin to set
     */
    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    /**
     * @return the destination
     */
    public Airport getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(Airport destination) {
        this.destination = destination;
    }

    /**
     * @return the distance
     */
    public float getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(float distance) {
        this.distance = distance;
    }

    /**
     * @return the basicFirstClassFare
     */
    public float getBasicFirstClassFare() {
        return basicFirstClassFare;
    }

    /**
     * @param basicFirstClassFare the basicFirstClassFare to set
     */
    public void setBasicFirstClassFare(float basicFirstClassFare) {
        this.basicFirstClassFare = basicFirstClassFare;
    }

    /**
     * @return the basicBusinessClassFare
     */
    public float getBasicBusinessClassFare() {
        return basicBusinessClassFare;
    }

    /**
     * @param basicBusinessClassFare the basicBusinessClassFare to set
     */
    public void setBasicBusinessClassFare(float basicBusinessClassFare) {
        this.basicBusinessClassFare = basicBusinessClassFare;
    }

    /**
     * @return the basicPremiumEconomyClassFare
     */
    public float getBasicPremiumEconomyClassFare() {
        return basicPremiumEconomyClassFare;
    }

    /**
     * @param basicPremiumEconomyClassFare the basicPremiumEconomyClassFare to set
     */
    public void setBasicPremiumEconomyClassFare(float basicPremiumEconomyClassFare) {
        this.basicPremiumEconomyClassFare = basicPremiumEconomyClassFare;
    }

    /**
     * @return the basicEconomyClassFare
     */
    public float getBasicEconomyClassFare() {
        return basicEconomyClassFare;
    }

    /**
     * @param basicEconomyClassFare the basicEconomyClassFare to set
     */
    public void setBasicEconomyClassFare(float basicEconomyClassFare) {
        this.basicEconomyClassFare = basicEconomyClassFare;
    }

    /**
     * @return the canEBP
     */
    public boolean isCanEBP() {
        return canEBP;
    }

    /**
     * @param canEBP the canEBP to set
     */
    public void setCanEBP(boolean canEBP) {
        this.canEBP = canEBP;
    }
    
}
