/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CRM;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;

/**
 *
 * @author Xi
 */
@Entity
public class CarPayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double payment;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date paymentDate;
    
    @ManyToOne
    private CarRental carRental = new CarRental();
    
    public void create(Long id, Double payment, Date paymentDate) {
        this.setId(id);
        this.setPayment(payment);
        this.setPaymentDate(paymentDate);
    }
    
    public Date getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public CarRental getCarRental() {
        return carRental;
    }
    
    public void setCarRental(CarRental carRental) {
        this.carRental = carRental;
    }
    
    public Double getPayment() {
        return payment;
    }
    
    public void setPayment(Double payment) {
        this.payment = payment;
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
        if (!(object instanceof CarPayment)) {
            return false;
        }
        CarPayment other = (CarPayment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Entity.CRM.CarPayment[ id=" + id + " ]";
    }
    
}
