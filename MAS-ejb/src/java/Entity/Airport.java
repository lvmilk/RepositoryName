/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entity;

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
    private String AirportName;
    private String CityName;
    private String Spec;
    private String CountryCode;
    private String TimeZone;
    private String OperationalStatus;
    private String StratigicLevel;

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

    @Override
    public String toString() {
        return "Entity.Airport[ id=" + id + " ]";
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
        return AirportName;
    }

    /**
     * @param AirportName the AirportName to set
     */
    public void setAirportName(String AirportName) {
        this.AirportName = AirportName;
    }

    /**
     * @return the CityName
     */
    public String getCityName() {
        return CityName;
    }

    /**
     * @param CityName the CityName to set
     */
    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    /**
     * @return the CountryCode
     */
    public String getCountryCode() {
        return CountryCode;
    }

    /**
     * @param CountryCode the CountryCode to set
     */
    public void setCountryCode(String CountryCode) {
        this.CountryCode = CountryCode;
    }

    /**
     * @return the TimeZone
     */
    public String getTimeZone() {
        return TimeZone;
    }

    /**
     * @param TimeZone the TimeZone to set
     */
    public void setTimeZone(String TimeZone) {
        this.TimeZone = TimeZone;
    }

    /**
     * @return the OperationalStatus
     */
    public String getOperationalStatus() {
        return OperationalStatus;
    }

    /**
     * @param OperationalStatus the OperationalStatus to set
     */
    public void setOperationalStatus(String OperationalStatus) {
        this.OperationalStatus = OperationalStatus;
    }

    /**
     * @return the StratigicLevel
     */
    public String getStratigicLevel() {
        return StratigicLevel;
    }

    /**
     * @param StratigicLevel the StratigicLevel to set
     */
    public void setStratigicLevel(String StratigicLevel) {
        this.StratigicLevel = StratigicLevel;
    }

    /**
     * @return the Spec
     */
    public String getSpec() {
        return Spec;
    }

    /**
     * @param Spec the Spec to set
     */
    public void setSpec(String Spec) {
        this.Spec = Spec;
    }
    
}
