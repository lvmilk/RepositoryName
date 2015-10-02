/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Xu
 */
@Entity
public class Route implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    not in session bean  private String originIATA;
//    not in session bean  private String destIATA;
    private Double distance;
    private Double blockhour;
    private Double basicScFare;
    private Double basicFcFare;
    private Double basicBcFare;
    private Double basicPecFare;
    private Double basicEcFare;
    // private boolean canEBP;
    private String status;

    @ManyToOne
    private AircraftType acType;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Airport origin;
    @ManyToOne(cascade = {CascadeType.PERSIST})
    private Airport dest;

    public void create(Airport origin, Airport dest, Double distance) {
        // Double basicFcFare, Double basicBcFare, Double basicPecFare, Double basicEcFare
        this.status = "Pending";
        this.origin = origin;
        this.dest = dest;
        this.distance = distance;
//        this.blockhour = blockhour;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getOriginIATA() {
//        return originIATA;
//    }
//
//    public void setOriginIATA(String originIATA) {
//        this.originIATA = originIATA;
//    }
//
//    public String getDestIATA() {
//        return destIATA;
//    }
//
//    public void setDestIATA(String destIATA) {
//        this.destIATA = destIATA;
//    }
    
    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getBlockhour() {
        return blockhour;
    }

    public void setBlockhour(Double blockhour) {
        this.blockhour = blockhour;
    }

    public Double getBasicScFare() {
        return basicScFare;
    }

    public void setBasicScFare(Double basicScFare) {
        this.basicScFare = basicScFare;
    }

    public Double getBasicFcFare() {
        return basicFcFare;
    }

    public void setBasicFcFare(Double basicFcFare) {
        this.basicFcFare = basicFcFare;
    }

    public Double getBasicBcFare() {
        return basicBcFare;
    }

    public void setBasicBcFare(Double basicBcFare) {
        this.basicBcFare = basicBcFare;
    }

    public Double getBasicPecFare() {
        return basicPecFare;
    }

    public void setBasicPecFare(Double basicPecFare) {
        this.basicPecFare = basicPecFare;
    }

    public Double getBasicEcFare() {
        return basicEcFare;
    }

    public void setBasicEcFare(Double basicEcFare) {
        this.basicEcFare = basicEcFare;
    }

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

    public Airport getOrigin() {
        return origin;
    }

    public void setOrigin(Airport origin) {
        this.origin = origin;
    }

    public Airport getDest() {
        return dest;
    }

    public void setDest(Airport dest) {
        this.dest = dest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

//     public boolean getCanEBP() {
//        return canEBP;
//    }
//
//    public void setCanEBP(boolean canEBP) {
//        this.canEBP = canEBP;
//    }
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Route)) {
            return false;
        }
        Route other = (Route) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.getOrigin().getIATA() + " - " + this.getDest().getIATA();
    }

}
