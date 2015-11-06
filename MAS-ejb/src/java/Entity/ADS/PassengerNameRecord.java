/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.ADS;

import Entity.CommonInfa.AirAlliances;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class PassengerNameRecord implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    private Collection<Passenger> psgList=new ArrayList<Passenger>();
    private Collection<Ticket> tkList=new ArrayList<Ticket>();
    
    @OneToOne(cascade={CascadeType.ALL})
    private AirAlliances alliance;
    private String contact;
    private Booker member;
    

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
        if (!(object instanceof PassengerNameRecord)) {
            return false;
        }
        PassengerNameRecord other = (PassengerNameRecord) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.ADS.PassengerNameRecord[ id=" + id + " ]";
    }

    /**
     * @return the psgList
     */
    public Collection<Passenger> getPsgList() {
        return psgList;
    }

    /**
     * @param psgList the psgList to set
     */
    public void setPsgList(Collection<Passenger> psgList) {
        this.psgList = psgList;
    }

    /**
     * @return the tkList
     */
    public Collection<Ticket> getTkList() {
        return tkList;
    }

    /**
     * @param tkList the tkList to set
     */
    public void setTkList(Collection<Ticket> tkList) {
        this.tkList = tkList;
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
     * @return the member
     */
    public Booker getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Booker member) {
        this.member = member;
    }

    /**
     * @return the alliance
     */
    public AirAlliances getAlliance() {
        return alliance;
    }

    /**
     * @param alliance the alliance to set
     */
    public void setAlliance(AirAlliances alliance) {
        this.alliance = alliance;
    }
    
}
