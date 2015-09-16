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
public class OfficeStaff implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String offName;
    private String offPassword;
    private String offType;

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
        if (!(object instanceof OfficeStaff)) {
            return false;
        }
        OfficeStaff other = (OfficeStaff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.OfficeStaff[ id=" + id + " ]";
    }

    /**
     * @return the offName
     */
    public String getOffName() {
        return offName;
    }

    /**
     * @param offName the offName to set
     */
    public void setOffName(String offName) {
        this.offName = offName;
    }

    /**
     * @return the offPassword
     */
    public String getOffPassword() {
        return offPassword;
    }

    /**
     * @param offPassword the offPassword to set
     */
    public void setOffPassword(String offPassword) {
        this.offPassword = offPassword;
    }

    /**
     * @return the offType
     */
    public String getOffType() {
        return offType;
    }

    /**
     * @param offType the offType to set
     */
    public void setOffType(String offType) {
        this.offType = offType;
    }
    
}
