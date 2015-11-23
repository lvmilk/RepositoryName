/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LI HAO
 */
@Entity
public class GDSPayment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long paymentID;
    private String pyMethod;
    private String pyName;
    private String cardNo;
    private String cardType;
    private String expiryDate;
    private String securityCode;
    private String billAddress;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date paymentDate;

    private Double totalPrice;

    @OneToOne
    @XmlTransient
    private GDSReservation reservation;

    public GDSPayment() {
    }

    public void createPayment(Double totalPrice) {
        this.paymentDate = Calendar.getInstance().getTime();
        this.totalPrice = totalPrice;
    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public String getPyMethod() {
        return pyMethod;
    }

    public void setPyMethod(String pyMethod) {
        this.pyMethod = pyMethod;
    }

    public String getPyName() {
        return pyName;
    }

    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public String getBillAddress() {
        return billAddress;
    }

    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    @XmlTransient
    public GDSReservation getReservation() {
        return reservation;
    }

    public void setReservation(GDSReservation reservation) {
        this.reservation = reservation;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.paymentID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GDSPayment other = (GDSPayment) obj;
        if (!Objects.equals(this.paymentID, other.paymentID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GDSPayment{" + "paymentID=" + paymentID + ", pyName=" + pyName + '}';
    }

}
