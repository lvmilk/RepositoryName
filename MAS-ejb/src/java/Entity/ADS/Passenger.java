/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author LI HAO
 */
@Entity
public class Passenger implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String passport;
    
    private String title;
    private String firstName;
    private String lastName;
    private String ffpName;
    private String ffpNo;
    private String email;
    private String contact;
    
    @OneToMany(cascade={CascadeType.ALL})
    private Collection<Itinerary> itineray=new ArrayList<Itinerary>();
    

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (passport != null ? passport.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the passport fields are not set
        if (!(object instanceof Passenger)) {
            return false;
        }
        Passenger other = (Passenger) object;
        if ((this.passport == null && other.passport != null) || (this.passport != null && !this.passport.equals(other.passport))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Passenger[ id=" + passport + " ]";
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the ffpName
     */
    public String getFfpName() {
        return ffpName;
    }

    /**
     * @param ffpName the ffpName to set
     */
    public void setFfpName(String ffpName) {
        this.ffpName = ffpName;
    }

    /**
     * @return the ffpNo
     */
    public String getFfpNo() {
        return ffpNo;
    }

    /**
     * @param ffpNo the ffpNo to set
     */
    public void setFfpNo(String ffpNo) {
        this.ffpNo = ffpNo;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the contact
     */
    public String getContact() {
        return contact;
    }

    /**
     * @param contact the contact to set
     */
    public void setContact(String contact) {
        this.contact = contact;
    }

    /**
     * @return the itineray
     */
    public Collection<Itinerary> getItineray() {
        return itineray;
    }

    /**
     * @param itineray the itineray to set
     */
    public void setItineray(Collection<Itinerary> itineray) {
        this.itineray = itineray;
    }
    
}
