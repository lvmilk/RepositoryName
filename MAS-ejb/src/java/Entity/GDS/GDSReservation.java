/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LI HAO
 */
@Entity
public class GDSReservation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airlineRsvCode;
    private String bkGivenName;
    private String bkSurname;
    private String bkEmail;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date rsvDate;
    private Double totalPayment;
    private String status;

    @ManyToMany
    @JoinTable(name = "GDSRESERVATION_GDSFLIGHT")
    @XmlTransient
    private List<GDSFlight> gdsFlightList = new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "rsv")
    @XmlTransient
    private List<GDSTicket> tickets = new ArrayList<>();

    @OneToOne(mappedBy = "reservation")
    @XmlTransient
    private GDSPayment payment;

    @ManyToOne
    private MasterPNR pnr;

    @ManyToOne
    @XmlTransient
    private GDSBooker gdsBooker = new GDSBooker();

    private String origin;
    private String dest;
    private Boolean returnTrip;
    private String bkSystem;

    public GDSReservation() {
    }

    public void createReservation(String firstName, String lastName, String email, String origin, String dest, Boolean returnTrip, String bkSystem) {
        this.bkGivenName = firstName;
        this.bkSurname = lastName;
        this.bkEmail = email;
        this.origin = origin;
        this.dest = dest;
        this.returnTrip = returnTrip;
        this.bkSystem = bkSystem;
        this.rsvDate=Calendar.getInstance().getTime();

    }

    public Long getAirlineRsvCode() {
        return airlineRsvCode;
    }

    public void setAirlineRsvCode(Long airlineRsvCode) {
        this.airlineRsvCode = airlineRsvCode;
    }

    public String getBkGivenName() {
        return bkGivenName;
    }

    public void setBkGivenName(String bkGivenName) {
        this.bkGivenName = bkGivenName;
    }

    public String getBkSurname() {
        return bkSurname;
    }

    public void setBkSurname(String bkSurname) {
        this.bkSurname = bkSurname;
    }

    public String getBkEmail() {
        return bkEmail;
    }

    public void setBkEmail(String bkEmail) {
        this.bkEmail = bkEmail;
    }

    public Date getRsvDate() {
        return rsvDate;
    }

    public void setRsvDate(Date rsvDate) {
        this.rsvDate = rsvDate;
    }

    public Double getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(Double totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @XmlTransient
    public List<GDSTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<GDSTicket> tickets) {
        this.tickets = tickets;
    }

    @XmlTransient
    public GDSPayment getPayment() {
        return payment;
    }

    public void setPayment(GDSPayment payment) {
        this.payment = payment;
    }

    public MasterPNR getPnr() {
        return pnr;
    }

    public void setPnr(MasterPNR pnr) {
        this.pnr = pnr;
    }

    @XmlTransient
    public List<GDSFlight> getGdsFlightList() {
        return gdsFlightList;
    }

    public void setGdsFlightList(List<GDSFlight> gdsFlightList) {
        this.gdsFlightList = gdsFlightList;
    }

    @XmlTransient
    public GDSBooker getGdsBooker() {
        return gdsBooker;
    }

    public void setGdsBooker(GDSBooker gdsBooker) {
        this.gdsBooker = gdsBooker;
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

    public String getBkSystem() {
        return bkSystem;
    }

    public void setBkSystem(String bkSystem) {
        this.bkSystem = bkSystem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (airlineRsvCode != null ? airlineRsvCode.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "Entity.GDS.GDSReservation[ id=" + airlineRsvCode + " ]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GDSReservation other = (GDSReservation) obj;
        if (!Objects.equals(this.airlineRsvCode, other.airlineRsvCode)) {
            return false;
        }
        return true;
    }

}
