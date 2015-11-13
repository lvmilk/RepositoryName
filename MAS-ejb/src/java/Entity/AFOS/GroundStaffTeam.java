/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import Entity.CommonInfa.GroundStaff;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Xu
 */
@Entity
public class GroundStaffTeam implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    Integer teamId;
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "groundStaffTeam")
    private List<GroundStaff> groundStaff = new ArrayList<>();

    @OneToMany
    private List<Rotation> rotationList = new ArrayList<>();

    public GroundStaffTeam create(Integer teamId) {
        this.teamId = teamId;
        return this;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public List<GroundStaff> getGroundStaff() {
        return groundStaff;
    }

    public void setGroundStaff(List<GroundStaff> groundStaff) {
        this.groundStaff = groundStaff;
    }

    public List<Rotation> getRotationList() {
        return rotationList;
    }

    public void setRotationList(List<Rotation> rotationList) {
        this.rotationList = rotationList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teamId != null ? teamId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof GroundStaffTeam)) {
            return false;
        }
        GroundStaffTeam other = (GroundStaffTeam) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AFOS.GroundStaffTeam[ id=" + teamId + " ]";
    }

}
