/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import org.joda.time.DateTime;

/**
 *
 * @author victor
 */
@Entity
public class FlightPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private List<Flight> flightList = new ArrayList<Flight>();
//    private Collection<Flight> flightList = new ArrayList<Flight>();

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
        if (!(object instanceof FlightPackage)) {
            return false;
        }
        FlightPackage other = (FlightPackage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String result = "";
        for (Flight fl : flightList) {
//            result += fl.getDate().substring(0, 10) + "\t" + fl.getGenericFlight().getFlightNo()
//                    + "\t" + fl.getGenericFlight().getRoute().getOrigin().getIATA()
//                    + "\t" + fl.getGenericFlight().getRoute().getDestination().getIATA() + "\n";
            result += fl.getDate().substring(0, 10) + "\t" + fl.getGenericFlight().getFlightNo()
                    + "\t" + fl.getGenericFlight().getRoute().getOrigin().getIATA()
                    + "\t" + fl.getGenericFlight().getRoute().getDest().getIATA() + "\n";
        }
        return result;
    }

//    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy ="flightPackage")
//    public Collection<Flight> getFlList() {
//        return flList;
//    }
//    public void setFlList(Collection<Flight> flList) {
//        this.flList = flList;
//    }
//    public void addFlight(Flight flight){
//        this.flList.add(flight);
//    }
    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "genericFlight")
    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }

    public void addFlight(Flight fl) {
        fl.setFlightPackage(this);
        this.flightList.add(fl);
    }

    public DateTime getStartTime() {
        DateTime dt = null;
        for (Flight fl : flightList) {
            if (dt == null) {
                dt = new DateTime(fl.getEstimatedDepartureTime());
            } else {
                DateTime current = new DateTime(fl.getEstimatedDepartureTime());
                if (current.compareTo(dt) <= 0) {
                    dt = current;
                }
            }
        }
        return dt;
    }

    public DateTime getEndTime() {
        DateTime dt = null;
        for (Flight fl : flightList) {
            if (dt == null) {
                dt = new DateTime(fl.getEstimatedArrivalTime());
            } else {
                DateTime current = new DateTime(fl.getEstimatedArrivalTime());
                if (current.compareTo(dt) >= 0) {
                    dt = current;
                }
            }
        }
        return dt;
    }
}
