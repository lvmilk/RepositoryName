/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfa;

import Entity.ADS.Booker;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author LI HAO
 */
@Entity
public class Agency implements Serializable {

    @Id
    private String agencyID;
    private String agenPwd;
    
    //Company Name
    private String name;
    
    @Column(unique=true)
    private String email;
    
    private String pType;
    
    @OneToMany(cascade={CascadeType.ALL},mappedBy="agency")
    private List<Booker> bkList;
    
    public void createAgencyAcc(String strAgencyId, String strAgencyPwd, String companyName,String strAgEmail, String strpType)
    {
        this.setAgencyID(strAgencyId);
        this.setAgenPwd(strAgencyPwd);
        this.setName(companyName);
        this.setEmail(strAgEmail);
        this.setpType(strpType);
    }
    
    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyID != null ? agencyID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the agencyID fields are not set
        if (!(object instanceof Agency)) {
            return false;
        }
        Agency other = (Agency) object;
        if ((this.agencyID == null && other.agencyID != null) || (this.agencyID != null && !this.agencyID.equals(other.agencyID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.Agency[ id=" + agencyID + " ]";
    }

    /**
     * @return the agenPwd
     */
    public String getAgenPwd() {
        return agenPwd;
    }

    /**
     * @param agenPwd the agenPwd to set
     */
    public void setAgenPwd(String agenPwd) {
        this.agenPwd = agenPwd;
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

    /**
     * @return the bkList
     */
    public List<Booker> getBkList() {
        return bkList;
    }

    /**
     * @param bkList the bkList to set
     */
    public void setBkList(List<Booker> bkList) {
        this.bkList = bkList;
    }

}
