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
public class CarRental implements Serializable {
    @Id
    private String companyName;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "carRental")
    private List<CarPayment> carPayment = new ArrayList<>();
    
    public void create(String companyName){
        this.setCompanyName(companyName);
    }

    public List<CarPayment> getCarPayment() {
        return carPayment;
    }

    public void setCarPayment(List<CarPayment> carPayment) {
        this.carPayment = carPayment;
    }
    
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (companyName != null ? companyName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the companyName fields are not set
        if (!(object instanceof CarRental)) {
            return false;
        }
        CarRental other = (CarRental) object;
        if ((this.companyName == null && other.companyName != null) || (this.companyName != null && !this.companyName.equals(other.companyName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CRM.CarRental[ id=" + companyName + " ]";
    }
    
}
