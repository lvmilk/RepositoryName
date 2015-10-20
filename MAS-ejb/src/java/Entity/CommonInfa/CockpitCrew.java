/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
public class CockpitCrew implements Serializable {
    @Id
    private String cpName;
    private String cpPassword;
    private String stfType;
    @Column(unique=true)
    private String email;
    private String licence;
    private Integer attempt;
    private Integer locked;
    
    @OneToOne(cascade={CascadeType.ALL})
    private UserEntity user;

    public void create(String strCpName, String strCpPassword, String email, String strStfType, String licence)
    {
        this.setCpName(strCpName);
        this.setCpPassword(strCpPassword);
        this.setEmail(email);
        this.setStfType(strStfType);
        this.setLicence(licence);
        this.setAttempt(0);
        this.setLocked(0);
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpName != null ? cpName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the stfId fields are not set
        if (!(object instanceof CockpitCrew)) {
            return false;
        }
        CockpitCrew other = (CockpitCrew) object;
        if ((this.cpName == null && other.cpName != null) || (this.cpName!= null && !this.cpName.equals(other.cpName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.CockpitCrew[ id=" + cpName + " ]";
    }

    /**
     * @return the cpName
     */
    public String getCpName() {
        return cpName;
    }

    /**
     * @param cpName the cpName to set
     */
    public void setCpName(String cpName) {
        this.cpName = cpName;
    }

    /**
     * @return the cpPassword
     */
    public String getCpPassword() {
        return cpPassword;
    }

    /**
     * @param cpPassword the cpPassword to set
     */
    public void setCpPassword(String cpPassword) {
        this.cpPassword = cpPassword;
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
     * @return the licence
     */
    public String getLicence() {
        return licence;
    }

    /**
     * @param licence the licence to set
     */
    public void setLicence(String licence) {
        this.licence = licence;
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
    
}
