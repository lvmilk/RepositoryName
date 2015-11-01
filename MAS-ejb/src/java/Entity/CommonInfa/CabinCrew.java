/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import Entity.AFOS.FlightCrewTeam;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class CabinCrew implements Serializable {

    @Id
    private String cbName;
    private String cbPassword;
    private String stfType;
    @Column(unique = true)
    private String email;
    private Integer attempt;
    private Integer locked;

    @OneToOne(cascade = {CascadeType.ALL})
    private UserEntity user;

//    @ManyToOne(cascade = {CascadeType.PERSIST})
//    private FlightCrewTeam flightTeam;

    public void create(String strCbName, String strCbPassword, String email, String strStfType) {
        this.setCbName(strCbName);
        this.setCbPassword(strCbPassword);
        this.setEmail(email);
        this.setStfType(strStfType);
        this.setAttempt(0);
        this.setLocked(0);
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbName != null ? cbName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CabinCrew)) {
            return false;
        }
        CabinCrew other = (CabinCrew) object;
        if ((this.cbName == null && other.cbName != null) || (this.cbName != null && !this.cbName.equals(other.cbName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.CabinCrew[ id=" + cbName + " ]";
    }

    /**
     * @return the cbName
     */
    public String getCbName() {
        return cbName;
    }

    /**
     * @param cbName the cbName to set
     */
    public void setCbName(String cbName) {
        this.cbName = cbName;
    }

    /**
     * @return the cbPassword
     */
    public String getCbPassword() {
        return cbPassword;
    }

    /**
     * @param cbPassword the cbPassword to set
     */
    public void setCbPassword(String cbPassword) {
        this.cbPassword = cbPassword;
    }

    /**
     * @return the stfType
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param stfType the stfType to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
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
     * @return the attempt
     */
    public Integer getAttempt() {
        return attempt;
    }

    /**
     * @param attempt the attempt to set
     */
    public void setAttempt(Integer attempt) {
        this.attempt = attempt;
    }

    /**
     * @return the locked
     */
    public Integer getLocked() {
        return locked;
    }

    /**
     * @param locked the locked to set
     */
    public void setLocked(Integer locked) {
        this.locked = locked;
    }

    /**
     * @return the user
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

//    public FlightCrewTeam getFlightTeam() {
//        return flightTeam;
//    }
//
//    public void setFlightTeam(FlightCrewTeam flightTeam) {
//        this.flightTeam = flightTeam;
//    }

}
