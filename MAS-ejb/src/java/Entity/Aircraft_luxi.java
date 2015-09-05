package Entity;

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

@Entity(name="Aircraft")
public class Aircraft implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(cascade={CascadeType.ALL},mappedBy="Aircraft")
    private Collection<Flight> flights=new ArrayList<Flight>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String registrationNo;
    private String serialNo;
    private String status;
    private String aircraftType;
    
    public void create(String registrationNo, String SerialNo, String Status,  String AirCraftType){
        this.setRegistrationNo(registrationNo);
        this.setSerialNo(SerialNo);
        this.setSerialNo(SerialNo);
        this.setStatus(Status);
    }
    
    
    public Collection<Flight> getFlight(){
        return flights;
    }   
    
    public void setFlight(Collection<Flight> flights){
        this.flights=flights;
    }
    
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
        if (!(object instanceof Aircraft)) {
            return false;
        }
        Aircraft other = (Aircraft) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.Aircraft[ id=" + id + " ]";
    }

    /**
     * @return the registrationNo
     */
    public String getRegistrationNo() {
        return registrationNo;
    }

    /**
     * @param registrationNo the registrationNo to set
     */
    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String SerialNo) {
        this.serialNo = SerialNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public String getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(String AirCraftType) {
        this.aircraftType = AirCraftType;
    }
    
    public GenericFlight findFlight(Integer flightNo) {
        GenericFlight genericFlight;
        List<GenericFlight> allFlights = (List) this.getFlight();
        for (int i = 0; i < allFlights.size(); i++) {
            genericFlight = (GenericFlight) allFlights.get(i);
            if (genericFlight.getFlightNo().equals(flightNo)) {
                return genericFlight;
            }
        }
        return null;
    }
    
}
