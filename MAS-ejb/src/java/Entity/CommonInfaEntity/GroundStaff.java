/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.CommonInfaEntity;

import java.io.Serializable;
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

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String grdName;
    private String grdPassword;
    private String stfType;
    private String email;

    public void create(String strGrdName, String strGrdPassword, String email, String strType) {
        this.setGrdName(strGrdName);
        this.setGrdPassword(strGrdPassword);
        this.setEmail(email);
        this.setStfType(strType);
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
        if (!(object instanceof GroundStaff)) {
            return false;
        }
        GroundStaff other = (GroundStaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.groudCrew[ id=" + id + " ]";
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

}
