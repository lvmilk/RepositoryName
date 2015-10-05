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
import javax.persistence.OneToOne;

/**
 *
 * @author LI HAO
 */
@Entity
public class UserEntity implements Serializable {
    @Id
    private String username;
    @Column(unique=true)
    private String comEmail;
    
    @OneToOne(mappedBy="user")
    private OfficeStaff offStaff;
    
    @OneToOne(mappedBy="user")
    private GroundStaff grdStaff;
    
    @OneToOne(mappedBy="user")
    private CabinCrew cbCrew;
    
    @OneToOne(mappedBy="user")
    private CockpitCrew cpCrew;
    
    public void create(String username,String email)
    {
        this.setUsername(username);
        this.setComEmail(email);
    }
    
    public String getComEmail() {
        return comEmail;
    }

    public void setComEmail(String comEmail) {
        this.comEmail = comEmail;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (comEmail != null ? comEmail.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the comEmail fields are not set
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
        if ((this.comEmail == null && other.comEmail != null) || (this.comEmail != null && !this.comEmail.equals(other.comEmail))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.CommonInfaEntity.UserEntity[ id=" + comEmail + " ]";
    }

    /**
     * @return the offStaff
     */
    public OfficeStaff getOffStaff() {
        return offStaff;
    }

    /**
     * @param offStaff the offStaff to set
     */
    public void setOffStaff(OfficeStaff offStaff) {
        this.offStaff = offStaff;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the grdStaff
     */
    public GroundStaff getGrdStaff() {
        return grdStaff;
    }

    /**
     * @param grdStaff the grdStaff to set
     */
    public void setGrdStaff(GroundStaff grdStaff) {
        this.grdStaff = grdStaff;
    }

    /**
     * @return the cbCrew
     */
    public CabinCrew getCbCrew() {
        return cbCrew;
    }

    /**
     * @param cbCrew the cbCrew to set
     */
    public void setCbCrew(CabinCrew cbCrew) {
        this.cbCrew = cbCrew;
    }

    /**
     * @return the cpCrew
     */
    public CockpitCrew getCpCrew() {
        return cpCrew;
    }

    /**
     * @param cpCrew the cpCrew to set
     */
    public void setCpCrew(CockpitCrew cpCrew) {
        this.cpCrew = cpCrew;
    }
    
}
