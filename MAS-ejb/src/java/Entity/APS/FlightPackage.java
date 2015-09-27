/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import java.time.LocalTime;
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
public class FlightPackage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "flightPackage")
    private List<FlightInstance> flightList = new ArrayList<>();

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
        for (FlightInstance fl : flightList) {
            result += fl.getDate() + "\t" + fl.getFlightFrequency().getFlightNo()
                    + "\t" + fl.getFlightFrequency().getRoute().getOrigin().getIATA()
                    + "\t" + fl.getFlightFrequency().getRoute().getDest().getIATA() + "\n";
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
    
    public List<FlightInstance> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightInstance> flightList) {
        this.flightList = flightList;
    }

    public void addFlight(FlightInstance fl) {
        fl.setFlightPackage(this);
        this.flightList.add(fl);
    }

//    public LocalTime getStartTime() {
//        LocalTime dt = null;
//        for (FlightInstance fl : flightList) {
//            if (dt == null) {
//                dt = new LocalTime(fl.getEstimatedDepTime());
//            } else {
//                LocalTime current = new LocalTime(fl.getEstimatedDepTime());
//                if (current.compareTo(dt) <= 0) {
//                    dt = current;
//                }
//            }
//        }
//        return dt;
//    }
//
//    public LocalTime getEndTime() {
//        LocalTime dt = null;
//        for (FlightInstance fl : flightList) {
//            if (dt == null) {
//                dt = new LocalTime(fl.getEstimatedArrTime());
//            } else {
//                LocalTime current = new LocalTime(fl.getEstimatedArrTime());
//                if (current.compareTo(dt) >= 0) {
//                    dt = current;
//                }
//            }
//        }
//        return dt;
//    }
}
