/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.AFOS;

import Entity.APS.AircraftType;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Xu
 */
@Entity
public class FlightCrewTeam implements Serializable {

//    private static final long serialVersionUID = 1L;
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @Id
    String teamId;
    @OneToOne(cascade = {CascadeType.PERSIST})
    private AircraftType act;
    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<CabinCrew> cabinCrew = new ArrayList<>();
    @OneToMany(cascade = {CascadeType.PERSIST})
    private List<CockpitCrew> cockpitCrew = new ArrayList<>();
    //    private long accumFlyMin;
    private String currentAirprot;

    public FlightCrewTeam create(String teamId) {
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

    public AircraftType getAct() {
        return act;
    }

    public void setAct(AircraftType act) {
        this.act = act;
    }

    public List<CabinCrew> getCabinCrew() {
        return cabinCrew;
    }

    public void setCabinCrew(List<CabinCrew> cabinCrew) {
        this.cabinCrew = cabinCrew;
    }

    public List<CockpitCrew> getCockpitCrew() {
        return cockpitCrew;
    }

    public void setCockpitCrew(List<CockpitCrew> cockpitCrew) {
        this.cockpitCrew = cockpitCrew;
    }

    public String getCurrentAirprot() {
        return currentAirprot;
    }

    public void setCurrentAirprot(String currentAirprot) {
        this.currentAirprot = currentAirprot;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
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
        if (!(object instanceof FlightCrewTeam)) {
            return false;
        }
        FlightCrewTeam other = (FlightCrewTeam) object;
        if ((this.teamId == null && other.teamId != null) || (this.teamId != null && !this.teamId.equals(other.teamId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.AFOS.FlightCrewTeam[ id=" + teamId + " ]";
    }

}
