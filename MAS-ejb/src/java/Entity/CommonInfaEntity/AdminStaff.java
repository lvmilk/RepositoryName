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
public class AdminStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String admName;
    private String admPasswprd;

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
        if (!(object instanceof AdminStaff)) {
            return false;
        }
        AdminStaff other = (AdminStaff) object;
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
     * @return the admName
     */
    public String getAdmName() {
        return admName;
    }

    /**
     * @param admName the admName to set
     */
    public void setAdmName(String admName) {
        this.admName = admName;
    }

    /**
     * @return the admPasswprd
     */
    public String getAdmPasswprd() {
        return admPasswprd;
    }

    /**
     * @param admPasswprd the admPasswprd to set
     */
    public void setAdmPasswprd(String admPasswprd) {
        this.admPasswprd = admPasswprd;
    }
    
}
