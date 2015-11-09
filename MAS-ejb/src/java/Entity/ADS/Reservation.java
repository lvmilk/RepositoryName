/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.AIS.BookingClassInstance;
import Entity.APS.Airport;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
    private Long id;

    private String bkFirstName;
    private String bkLastName;
    private String bkEmail;
    
    private String origin;
    private String dest;
    private Boolean returnTrip;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date rsvDate;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "rsv")
    private List<Ticket> tickets=new ArrayList<>();

    @ManyToMany(cascade={CascadeType.PERSIST})
    @JoinTable(name="RESERVATION_BKCINSTANCE")
    private List<BookingClassInstance> bkcInstance=new ArrayList<BookingClassInstance>();

    @OneToOne(mappedBy = "reservation")
    private Payment payment;
    
   @ManyToOne
   private Booker booker;
    

    public Reservation() {

    }

    public void createReservation(String bkFirstName, String bkLastName, String bkEmail, String origin, String dest, Boolean returnTrip) {
        this.bkFirstName = bkFirstName;
        this.bkLastName = bkLastName;
        this.bkEmail = bkEmail;
        this.rsvDate = Calendar.getInstance().getTime();
        this.origin=origin;
        this.dest=dest;
        this.returnTrip=returnTrip;
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
        // TODO: Warning - this method won't work in the case the rsvCode fields are not set
        if (!(object instanceof Reservation)) {
            return false;
        }
        Reservation other = (Reservation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Reservation[ id=" + id + " ]";
    }

    /**
     * @return the tickets
     */
    public List<Ticket> getTickets() {
        return tickets;
    }

    /**
     * @param tickets the tickets to set
     */
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
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

    /**
     * @return the bkcInstance
     */
    public List<BookingClassInstance> getBkcInstance() {
        return bkcInstance;
    }

    /**
     * @param bkcInstance the bkcInstance to set
     */
    public void setBkcInstance(List<BookingClassInstance> bkcInstance) {
        this.bkcInstance = bkcInstance;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Boolean getReturnTrip() {
        return returnTrip;
    }

    public void setReturnTrip(Boolean returnTrip) {
        this.returnTrip = returnTrip;
    }

    public Booker getBooker() {
        return booker;
    }

    public void setBooker(Booker booker) {
        this.booker = booker;
    }




}
