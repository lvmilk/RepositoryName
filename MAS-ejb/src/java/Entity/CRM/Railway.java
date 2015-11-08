/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CRM;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Xi
 */
@Entity
public class Railway implements Serializable {
    @Id
    private String railwayName;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "railway")
    private List<RailwayPayment> railwayPayment = new ArrayList<>();

    public void create(String name) {
        this.setRailwayName(railwayName);
    }

    public List<RailwayPayment> getRailwayPayment() {
        return railwayPayment;
    }

    public void setRailwayPayment(List<RailwayPayment> railwayPayment) {
        this.railwayPayment = railwayPayment;
    }

    public String getRailwayName() {
        return railwayName;
    }

    public void setRailwayName(String railwayName) {
        this.railwayName = railwayName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (railwayName != null ? railwayName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the railwayName fields are not set
        if (!(object instanceof Railway)) {
            return false;
        }
        Railway other = (Railway) object;
        if ((this.railwayName == null && other.railwayName != null) || (this.railwayName != null && !this.railwayName.equals(other.railwayName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CRM.Raiway[ id=" + railwayName + " ]";
    }

}
