/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author LI HAO
 */
@Entity
public class Payment implements Serializable {

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
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date paymentDate;

    @OneToOne(cascade = {CascadeType.PERSIST})
    private Reservation reservation;

    private Double totalPrice;

    public Payment() {

    }

    public Long getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(Long paymentID) {
        this.paymentID = paymentID;
    }

    public void createPayment(Double totalPrice) {
        this.paymentDate = Calendar.getInstance().getTime();
        this.totalPrice = totalPrice;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (paymentID != null ? paymentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the paymentID fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.paymentID == null && other.paymentID != null) || (this.paymentID != null && !this.paymentID.equals(other.paymentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Payment[ id=" + paymentID + " ]";
    }

    /**
     * @return the pyMethod
     */
    public String getPyMethod() {
        return pyMethod;
    }

    /**
     * @param pyMethod the pyMethod to set
     */
    public void setPyMethod(String pyMethod) {
        this.pyMethod = pyMethod;
    }

    /**
     * @return the pyName
     */
    public String getPyName() {
        return pyName;
    }

    /**
     * @param pyName the pyName to set
     */
    public void setPyName(String pyName) {
        this.pyName = pyName;
    }

    /**
     * @return the cardNo
     */
    public String getCardNo() {
        return cardNo;
    }

    /**
     * @param cardNo the cardNo to set
     */
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    /**
     * @return the cardType
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * @param cardType the cardType to set
     */
    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    /**
     * @return the expiryDate
     */
    public String getExpiryDate() {
        return expiryDate;
    }

    /**
     * @param expiryDate the expiryDate to set
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    /**
     * @return the securityCode
     */
    public String getSecurityCode() {
        return securityCode;
    }

    /**
     * @param securityCode the securityCode to set
     */
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    /**
     * @return the billAddress
     */
    public String getBillAddress() {
        return billAddress;
    }

    /**
     * @param billAddress the billAddress to set
     */
    public void setBillAddress(String billAddress) {
        this.billAddress = billAddress;
    }

    /**
     * @return the reservation
     */
    public Reservation getReservation() {
        return reservation;
    }

    /**
     * @param reservation the reservation to set
     */
    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    
    
}
