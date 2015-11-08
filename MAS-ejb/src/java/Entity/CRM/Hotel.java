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
public class Hotel implements Serializable {
    @Id
    private String hotelName;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "hotel")
    private List<HotelPayment> hotelPayment = new ArrayList<>();
    
    public void create(String name){
        this.setHotelName(hotelName);
    }

    public List<HotelPayment> getHotelPayment() {
        return hotelPayment;
    }

    public void setHotelPayment(List<HotelPayment> hotelPayment) {
        this.hotelPayment = hotelPayment;
    }
    

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (hotelName != null ? hotelName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the hotelName fields are not set
        if (!(object instanceof Hotel)) {
            return false;
        }
        Hotel other = (Hotel) object;
        if ((this.hotelName == null && other.hotelName != null) || (this.hotelName != null && !this.hotelName.equals(other.hotelName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CRM.Hotel[ id=" + hotelName + " ]";
    }
    
}
