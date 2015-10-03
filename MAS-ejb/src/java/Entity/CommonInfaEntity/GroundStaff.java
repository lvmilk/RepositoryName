/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfaEntity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author LI HAO
 */
@Entity
public class GroundStaff implements Serializable {


    @Id
    private String grdName;
    private String grdPassword;
    private String stfType;
    @Column(unique=true)
    private String email;
    private Integer attempt;
    private Integer locked;

    public void create(String strGrdName, String strGrdPassword, String email, String strType) {
        this.setGrdName(strGrdName);
        this.setGrdPassword(strGrdPassword);
        this.setEmail(email);
        this.setStfType(strType);
        this.setAttempt(0);
        this.setLocked(0);
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (grdName != null ? grdName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroundStaff)) {
            return false;
        }
        GroundStaff other = (GroundStaff) object;
        if ((this.grdName == null && other.grdName != null) || (this.grdName != null && !this.grdName.equals(other.grdName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.groudCrew[ id=" + grdName+ " ]";
    }

    /**
     * @return the grdName
     */
    public String getGrdName() {
        return grdName;
    }

    /**
     * @param grdName the grdName to set
     */
    public void setGrdName(String grdName) {
        this.grdName = grdName;
    }

    /**
     * @return the grdPassword
     */
    public String getGrdPassword() {
        return grdPassword;
    }

    /**
     * @param grdPassword the grdPassword to set
     */
    public void setGrdPassword(String grdPassword) {
        this.grdPassword = grdPassword;
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

}
