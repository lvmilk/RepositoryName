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
public class Staff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String stfName;
    private String stfPasswprd;

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
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.Staff[ id=" + id + " ]";
    }

    /**
     * @return the stfName
     */
    public String getStfName() {
        return stfName;
    }

    /**
     * @param stfName the stfName to set
     */
    public void setStfName(String stfName) {
        this.stfName = stfName;
    }

    /**
     * @return the stfPasswprd
     */
    public String getStfPasswprd() {
        return stfPasswprd;
    }

    /**
     * @param stfPasswprd the stfPasswprd to set
     */
    public void setStfPasswprd(String stfPasswprd) {
        this.stfPasswprd = stfPasswprd;
    }
    
}
