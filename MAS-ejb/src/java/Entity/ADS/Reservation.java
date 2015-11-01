/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.AIS.BookingClassInstance;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author LI HAO
 */
@Entity
public class Reservation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airlineRsvCode;

    private String bkFirstName;
    private String bkLastName;
    private String bkEmail;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rsvDate;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "rsv")
    private Collection<Ticket> tickets;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(name = "RSV_BKCLASSINSTANCE")
    private Collection<BookingClassInstance> bkclassInstance = new ArrayList<BookingClassInstance>();

    @OneToOne(mappedBy = "reservation")
    private Payment payment;

    public Reservation() {

    }

    public void createReservation(String bkFirstName, String bkLastName, String bkEmail,Date rsvDate) {
        this.bkFirstName = bkFirstName;
        this.bkLastName = bkLastName;
        this.bkEmail = bkEmail;
        this.rsvDate = rsvDate;
    }

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

    /**
     * @return the tickets
     */
    public Collection<Ticket> getTickets() {
        return tickets;
    }

    /**
     * @param tickets the tickets to set
     */
    public void setTickets(Collection<Ticket> tickets) {
        this.tickets = tickets;
    }

    /**
     * @return the bkclassInstance
     */
    public Collection<BookingClassInstance> getBkclassInstance() {
        return bkclassInstance;
    }

    /**
     * @param bkclassInstance the bkclassInstance to set
     */
    public void setBkclassInstance(Collection<BookingClassInstance> bkclassInstance) {
        this.bkclassInstance = bkclassInstance;
    }

    /**
     * @return the payment
     */
    public Payment getPayment() {
        return payment;
    }

    /**
     * @param payment the payment to set
     */
    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    /**
     * @return the bkFirstName
     */
    public String getBkFirstName() {
        return bkFirstName;
    }

    /**
     * @param bkFirstName the bkFirstName to set
     */
    public void setBkFirstName(String bkFirstName) {
        this.bkFirstName = bkFirstName;
    }

    /**
     * @return the bkLastName
     */
    public String getBkLastName() {
        return bkLastName;
    }

    /**
     * @param bkLastName the bkLastName to set
     */
    public void setBkLastName(String bkLastName) {
        this.bkLastName = bkLastName;
    }

    /**
     * @return the bkEmail
     */
    public String getBkEmail() {
        return bkEmail;
    }

    /**
     * @param bkEmail the bkEmail to set
     */
    public void setBkEmail(String bkEmail) {
        this.bkEmail = bkEmail;
    }

    public Date getRsvDate() {
        return rsvDate;
    }

    public void setRsvDate(Date rsvDate) {
        this.rsvDate = rsvDate;
    }



}
