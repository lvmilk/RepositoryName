/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author LI HAO
 */
@Entity
public class GDSPassenger implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String passport;
    private String title;
    private String givenName;
    private String surname;
    private String ffpName;
    private String ffpNo;

    @OneToMany(cascade = {CascadeType.PERSIST})
    @XmlTransient
    private List<GDSTicket> tickets = new ArrayList<>();

    public GDSPassenger() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFfpName() {
        return ffpName;
    }

    public void setFfpName(String ffpName) {
        this.ffpName = ffpName;
    }

    public String getFfpNo() {
        return ffpNo;
    }

    public void setFfpNo(String ffpNo) {
        this.ffpNo = ffpNo;
    }

    @XmlTransient
    public List<GDSTicket> getTickets() {
        return tickets;
    }

    public void setTickets(List<GDSTicket> tickets) {
        this.tickets = tickets;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
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
        final GDSPassenger other = (GDSPassenger) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GDS.GDSPassenger[ id=" + id + " ]";
    }

}
