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
public class GuestPassenger implements Serializable, GuestInterface, PassengerInterface {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String title;
    private String surname;
    private String givenName;
    
    private String gender;
    private String nationality;
    private String birthday;
    private String travelDocumentIssueCountry;
    private String travelDocumentType;
    private String travelDocumentNo;
    private String travelDocumentIssueDate;
    private String travelDocumentExpireDate;

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
        if (!(object instanceof GuestPassenger)) {
            return false;
        }
        GuestPassenger other = (GuestPassenger) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.GuestPassenger[ id=" + id + " ]";
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
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return the nationality
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * @param nationality the nationality to set
     */
    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    /**
     * @return the birthday
     */
    public String getBirthday() {
        return birthday;
    }

    /**
     * @param birthday the birthday to set
     */
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    /**
     * @return the travelDocumentIssueCountry
     */
    public String getTravelDocumentIssueCountry() {
        return travelDocumentIssueCountry;
    }

    /**
     * @param travelDocumentIssueCountry the travelDocumentIssueCountry to set
     */
    public void setTravelDocumentIssueCountry(String travelDocumentIssueCountry) {
        this.travelDocumentIssueCountry = travelDocumentIssueCountry;
    }

    /**
     * @return the travelDocumentType
     */
    public String getTravelDocumentType() {
        return travelDocumentType;
    }

    /**
     * @param travelDocumentType the travelDocumentType to set
     */
    public void setTravelDocumentType(String travelDocumentType) {
        this.travelDocumentType = travelDocumentType;
    }

    /**
     * @return the travelDocumentNo
     */
    public String getTravelDocumentNo() {
        return travelDocumentNo;
    }

    /**
     * @param travelDocumentNo the travelDocumentNo to set
     */
    public void setTravelDocumentNo(String travelDocumentNo) {
        this.travelDocumentNo = travelDocumentNo;
    }

    /**
     * @return the travelDocumentIssueDate
     */
    public String getTravelDocumentIssueDate() {
        return travelDocumentIssueDate;
    }

    /**
     * @param travelDocumentIssueDate the travelDocumentIssueDate to set
     */
    public void setTravelDocumentIssueDate(String travelDocumentIssueDate) {
        this.travelDocumentIssueDate = travelDocumentIssueDate;
    }

    /**
     * @return the travelDocumentExpireDate
     */
    public String getTravelDocumentExpireDate() {
        return travelDocumentExpireDate;
    }

    /**
     * @param travelDocumentExpireDate the travelDocumentExpireDate to set
     */
    public void setTravelDocumentExpireDate(String travelDocumentExpireDate) {
        this.travelDocumentExpireDate = travelDocumentExpireDate;
    }
    
}
