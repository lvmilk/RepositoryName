/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity.APS;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author victor
 */
@Entity(name="Airport")
public class Airport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String IATA;
    private String airportName;
    private String cityName;
    private String spec;
    private String countryCode;
    private String timeZone;
    private String OoperationalStatus;
    private String StrategicLevel;
    
    //new added
    private String Airspace;

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
        if (!(object instanceof Airport)) {
            return false;
        }
        Airport other = (Airport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    
//        private Long id;
//    
//    private String IATA;
//    private String AirportName;
//    private String CityName;
//    private String Spec;
//    private String CountryCode;
//    private String TimeZone;
//    private String OperationalStatus;
//    private String StrategicLevel;
//    
//    //new added
//    private String Airspace;
    @Override
    public String toString() {
        String st = "";
        st += this.id + "\t";
        st += this.IATA + "\t";
        st += this.airportName + "\t";
        st += this.cityName + "\t";
        st += this.spec + "\t";
        st += this.countryCode + "\t";
        st += this.timeZone + "\t";
        st += this.OoperationalStatus + "\t";
        st += this.StrategicLevel + "\t";
        st += this.Airspace + "\t";
        return st;

//return "Entity.Airport[ id=" + id + " ]";
    }

    /**
     * @return the IATA
     */
    public String getIATA() {
        return IATA;
    }

    /**
     * @param IATA the IATA to set
     */
    public void setIATA(String IATA) {
        this.IATA = IATA;
    }

    /**
     * @return the AirportName
     */
    public String getAirportName() {
        return airportName;
    }

    /**
     * @param AirportName the AirportName to set
     */
    public void setAirportName(String AirportName) {
        this.airportName = AirportName;
    }

    /**
     * @return the CityName
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * @param CityName the CityName to set
     */
    public void setCityName(String CityName) {
        this.cityName = CityName;
    }

    /**
     * @return the CountryCode
     */
    public String getCountryCode() {
        return countryCode;
    }

    /**
     * @param CountryCode the CountryCode to set
     */
    public void setCountryCode(String CountryCode) {
        this.countryCode = CountryCode;
    }

    /**
     * @return the TimeZone
     */
    public String getTimeZone() {
        return timeZone;
    }

    /**
     * @param TimeZone the TimeZone to set
     */
    public void setTimeZone(String TimeZone) {
        this.timeZone = TimeZone;
    }

    /**
     * @return the OperationalStatus
     */
    public String getOperationalStatus() {
        return OoperationalStatus;
    }

    /**
     * @param OperationalStatus the OperationalStatus to set
     */
    public void setOperationalStatus(String OperationalStatus) {
        this.OoperationalStatus = OperationalStatus;
    }

    /**
     * @return the StratigicLevel
     */
    public String getStrategicLevel() {
        return StrategicLevel;
    }

    /**
     * @param StratigicLevel the StratigicLevel to set
     */
    public void setStrategicLevel(String StratigicLevel) {
        this.StrategicLevel = StratigicLevel;
    }

    /**
     * @return the Spec
     */
    public String getSpec() {
        return spec;
    }

    /**
     * @param Spec the Spec to set
     */
    public void setSpec(String Spec) {
        this.spec = Spec;
    }

    /**
     * @return the Airspace
     */
    public String getAirspace() {
        return Airspace;
    }

    /**
     * @param Airspace the Airspace to set
     */
    public void setAirspace(String Airspace) {
        this.Airspace = Airspace;
    }
    
}
