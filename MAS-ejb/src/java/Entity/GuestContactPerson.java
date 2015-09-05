/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author victor
 */
@Entity
public class GuestContactPerson implements Serializable {
//public class GuestContactPerson implements Serializable, GuestInterface, ContactPersonInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String surname;
    private String givenName;
    
    private String mobilePhone;
    private String officePhone;
    private String email;
    private String livingCountry;
    private String livingState;
    private String livingCity;
    private String livingAddress;
    private String postalCode;

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
        if (!(object instanceof GuestContactPerson)) {
            return false;
        }
        GuestContactPerson other = (GuestContactPerson) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GuestContactPerson[ id=" + id + " ]";
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
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @param givenName the givenName to set
     */
    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    /**
     * @return the mobilePhone
     */
    public String getMobilePhone() {
        return mobilePhone;
    }

    /**
     * @param mobilePhone the mobilePhone to set
     */
    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    /**
     * @return the officePhone
     */
    public String getOfficePhone() {
        return officePhone;
    }

    /**
     * @param officePhone the officePhone to set
     */
    public void setOfficePhone(String officePhone) {
        this.officePhone = officePhone;
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
     * @return the livingCountry
     */
    public String getLivingCountry() {
        return livingCountry;
    }

    /**
     * @param livingCountry the livingCountry to set
     */
    public void setLivingCountry(String livingCountry) {
        this.livingCountry = livingCountry;
    }

    /**
     * @return the livingState
     */
    public String getLivingState() {
        return livingState;
    }

    /**
     * @param livingState the livingState to set
     */
    public void setLivingState(String livingState) {
        this.livingState = livingState;
    }

    /**
     * @return the livingCity
     */
    public String getLivingCity() {
        return livingCity;
    }

    /**
     * @param livingCity the livingCity to set
     */
    public void setLivingCity(String livingCity) {
        this.livingCity = livingCity;
    }

    /**
     * @return the livingAddress
     */
    public String getLivingAddress() {
        return livingAddress;
    }

    /**
     * @param livingAddress the livingAddress to set
     */
    public void setLivingAddress(String livingAddress) {
        this.livingAddress = livingAddress;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
}
