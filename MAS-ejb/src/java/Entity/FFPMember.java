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
public class FFPMember implements Serializable, PassengerInterface, ContactPersonInterface {
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
    
    private String gender;
    private String nationality;
    private String birthday;
    private String travelDocumentIssueCountry;
    private String travelDocumentType;
    private String travelDocumentNo;
    private String travelDocumentIssueDate;
    private String travelDocumentExpireDate;
    
    private String username;
    private String password;
    private String FFPNo;
    private String JoinDate;
    private String membershipTier;
    private long MileAccountId;

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
        if (!(object instanceof FFPMember)) {
            return false;
        }
        FFPMember other = (FFPMember) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Member[ id=" + id + " ]";
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
     * @param nationalality the nationality to set
     */
    public void setNationality(String nationalality) {
        this.nationality = nationalality;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the FFPNo
     */
    public String getFFPNo() {
        return FFPNo;
    }

    /**
     * @param FFPNo the FFPNo to set
     */
    public void setFFPNo(String FFPNo) {
        this.FFPNo = FFPNo;
    }

    /**
     * @return the JoinDate
     */
    public String getJoinDate() {
        return JoinDate;
    }

    /**
     * @param JoinDate the JoinDate to set
     */
    public void setJoinDate(String JoinDate) {
        this.JoinDate = JoinDate;
    }

    /**
     * @return the membershipTier
     */
    public String getMembershipTier() {
        return membershipTier;
    }

    /**
     * @param membershipTier the membershipTier to set
     */
    public void setMembershipTier(String membershipTier) {
        this.membershipTier = membershipTier;
    }

    /**
     * @return the MileAccountId
     */
    public long getMileAccountId() {
        return MileAccountId;
    }

    /**
     * @param MileAccountId the MileAccountId to set
     */
    public void setMileAccountId(long MileAccountId) {
        this.MileAccountId = MileAccountId;
    }
    
}
