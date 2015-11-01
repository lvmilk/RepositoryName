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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author LI HAO
 */
@Entity
public class Passenger implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String passport;
    
    private String title;
    private String firstName;
    private String lastName;
    private String ffpName;
    private String ffpNo;
    
    @ManyToOne
    private Member member;
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="passenger")
    private Collection<Ticket> tickets;
    
    public Passenger()
    {
    }
    
    public void createPsg(String passport,String title, String firstName, String lastName, String ffpName, String ffpNo)
    {
        this.passport=passport;
        this.title=title;
        this.firstName=firstName;
        this.lastName=lastName;
        this.ffpName=ffpName;
        this.ffpNo=ffpNo;
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
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Passenger)) {
            return false;
        }
        Passenger other = (Passenger) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.Passenger[ id=" + id + " ]";
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
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
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
    
}
