/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.GDS;

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
 * @author LI HAO
 */
@Entity
public class Airline implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String name;
    private String IATA;

    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "airline")
    private List<GDSFlight> flightInstances=new ArrayList<>();

    @OneToMany(cascade = {CascadeType.PERSIST},mappedBy = "airline")
    private List<GDSReservation> reservations=new ArrayList<>();

    public Airline() {
    }

  
    public void createAirline(String name, String IATA, String email)
    {
        this.name=name;
        this.IATA=IATA;
        this.email=email;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public List<GDSFlight> getFlightInstances() {
        return flightInstances;
    }

    public void setFlightInstances(List<GDSFlight> flightInstances) {
        this.flightInstances = flightInstances;
    }

    public List<GDSReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<GDSReservation> reservations) {
        this.reservations = reservations;
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
        if (!(object instanceof Airline)) {
            return false;
        }
        Airline other = (Airline) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Entity.GDS.Airline[ id=" + id + " ]";
    }

}
