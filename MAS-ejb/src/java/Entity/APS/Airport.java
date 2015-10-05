/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity.APS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author Xu
 */
@Entity
public class Airport implements Serializable {

    @Id
    private String IATA;
    private String airportName;
    private String cityName;
    private String countryName;
    private String spec;
    private String timeZone;
    private String opStatus;
    private String strategicLevel;
    private String airspace;

//    private Collection<Route> routeList = new ArrayList<Route>();
    public void create(String IATA, String airportName, String cityName, String countryName, String spec, String timeZone, String opStatus, String strategicLevel, String airspace) {
        this.IATA = IATA;
        this.airportName = airportName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.spec = spec;
        this.timeZone = timeZone;
        this.opStatus = opStatus;
        this.strategicLevel = strategicLevel;
        this.airspace = airspace;
    }

    public String getIATA() {
        return IATA;
    }

    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getOpStatus() {
        return opStatus;
    }

    public void setOpStatus(String opStatus) {
        this.opStatus = opStatus;
    }

    public String getStrategicLevel() {
        return strategicLevel;
    }

    public void setStrategicLevel(String strategicLevel) {
        this.strategicLevel = strategicLevel;
    }

    public String getAirspace() {
        return airspace;
    }

    public void setAirspace(String airspace) {
        this.airspace = airspace;
    }

//    public Collection<Route> getRouteList() {
//        return routeList;
//    }
//
//    public void setRouteList(Collection<Route> routeList) {
//        this.routeList = routeList;
//}

@Override
        public int hashCode() {
        int hash = 0;
        hash += (IATA != null ? IATA.hashCode() : 0);
        return hash;
    }

    @Override
        public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.IATA == null && other.IATA != null) || (this.IATA != null && !this.IATA.equals(other.IATA))) {
            return false;
        }
        return true;
    }

    @Override
        public String toString() {
        return IATA + " - " + cityName + ", " + countryName;
    }

}
