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
public class AirAlliances implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String allianceID;
    private String allPwd;
    @Column(unique=true)
    private String email;
    private String pType;

    public void createAllianceAcc(String strAlId, String strAlPwd, String strAlEmail, String strpType) {
        this.setAllianceID(strAlId);
        this.setAllPwd(strAlPwd);
        this.setEmail(strAlEmail);
        this.setpType(strpType);
    }

    public String getAllianceID() {
        return allianceID;
    }

    public void setAllianceID(String allianceID) {
        this.allianceID = allianceID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (allianceID != null ? allianceID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the allianceID fields are not set
        if (!(object instanceof AirAlliances)) {
            return false;
        }
        AirAlliances other = (AirAlliances) object;
        if ((this.allianceID == null && other.allianceID != null) || (this.allianceID != null && !this.allianceID.equals(other.allianceID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.AirAlliances[ id=" + allianceID + " ]";
    }

    /**
     * @return the allPwd
     */
    public String getAllPwd() {
        return allPwd;
    }

    /**
     * @param allPwd the allPwd to set
     */
    public void setAllPwd(String allPwd) {
        this.allPwd = allPwd;
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
     * @return the pType
     */
    public String getpType() {
        return pType;
    }

    /**
     * @param pType the pType to set
     */
    public void setpType(String pType) {
        this.pType = pType;
    }

}
