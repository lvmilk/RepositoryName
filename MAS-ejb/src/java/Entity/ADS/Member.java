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
public class Member implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberID;
    
    private String title;
    private String firstName;
    private String lastName;
    
    private String email;
    private String address;
    private String contact;
    private String passport;
    
    private boolean memberStatus;
    private String dob;
    private Double miles;
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="member")
    private Collection<Passenger> psgs;
    

    
    public Member()
    {
    }
    
    public void CreateMember(String title, String firstName, String lastName, String email, String address, String contact,boolean memberStatus)
    {
        this.title=title;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.address=address;
        this.contact=contact;
        this.memberStatus=memberStatus;
    }

    public Long getMemberID() {
        return memberID;
    }

    public void setMemberID(Long memberID) {
        this.memberID = memberID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (memberID != null ? memberID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the memberID fields are not set
        if (!(object instanceof Member)) {
            return false;
        }
        Member other = (Member) object;
        if ((this.memberID == null && other.memberID != null) || (this.memberID != null && !this.memberID.equals(other.memberID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Member[ id=" + memberID + " ]";
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * @return the memberStatus
     */
    public boolean isMemberStatus() {
        return memberStatus;
    }

    /**
     * @param memberStatus the memberStatus to set
     */
    public void setMemberStatus(boolean memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return the miles
     */
    public Double getMiles() {
        return miles;
    }

    /**
     * @param miles the miles to set
     */
    public void setMiles(Double miles) {
        this.miles = miles;
    }

    /**
     * @return the psgs
     */
    public Collection<Passenger> getPsgs() {
        return psgs;
    }

    /**
     * @param psgs the psgs to set
     */
    public void setPsgs(Collection<Passenger> psgs) {
        this.psgs = psgs;
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

    
}
