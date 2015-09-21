package Entity.APS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.*;
import org.joda.time.DateTime;

/**
 *
 * @author victor/lucy
 */
@Entity
public class Flight implements Serializable, Comparable<Flight> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private GenericFlight genericFlight;

    private String date;

    private String operationStatus;
    private String flightStatus;
    private String estimatedDepartureTime;
    private String estimatedArrivalTime;
    private String actualDepartureTime;
    private String actualArrivalTime;

    private Aircraft aircraft;
//    private List<FlightBookingRecord> booking = new ArrayList<FlightBookingRecord>();
//    private List<FlightCheckInRecord> CI = new ArrayList<FlightCheckInRecord>();

    private FlightPackage flightPackage;

    public Flight() {
        //this.genericFlight = new GenericFlight();
    }

    @Id
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
        if (!(object instanceof Flight)) {
            return false;
        }
        Flight other = (Flight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String st = "";
        st += this.id + "\t";
        st += this.date.substring(0, 10) + "\t";
        st += this.getGenericFlight().getFlightNo() + "\t";
        st += this.getGenericFlight().getRoute().getOrigin().getIATA() + "\t";
        st += this.getEstimatedDepartureTime() + "\t";
//        st += this.getGenericFlight().getRoute().getDestination().getIATA() + "\t";
        st += this.getGenericFlight().getRoute().getDest().getIATA() + "\t";
        st += this.getEstimatedArrivalTime() + "\t";
        st += this.getOperationStatus() + "\t";
//        try{
//            st += "\nConnected Flights:\n" + this.getFlightPackage().toString();
//        }catch (Exception e){
//            st += "No PK Found";
//        //st += this.getAircraft().getRegistrationNo() + "\t";
//        }
        return st;
    }

//    /**
//     * @return the booking
//     */
//    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy = "flight")
//    public Collection<FlightBookingRecord> getBooking() {
//        return booking;
//    }
//
//    /**
//     * @param booking the booking to set
//     */
//    public void setBooking(List<FlightBookingRecord> booking) {
//        this.booking = booking;
//    }
//
//    /**
//     * @return the CI
//     */
//    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy = "flight")
//    public List<FlightCheckInRecord> getCI() {
//        return CI;
//    }
//
//    /**
//     * @param CI the CI to set
//     */
//    public void setCI(List<FlightCheckInRecord> CI) {
//        this.CI = CI;
//    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    public GenericFlight getGenericFlight() {
        return genericFlight;
    }

    public void setGenericFlight(GenericFlight genericFlight) {
        this.genericFlight = genericFlight;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST})
    public FlightPackage getFlightPackage() {
        return flightPackage;
    }

    public void setFlightPackage(FlightPackage flightPackage) {
        this.flightPackage = flightPackage;
    }

//    @ManyToOne(cascade={CascadeType.PERSIST})
//    public FlightPackage getFlightPackage() {
//        return flightPackage;
//    }
//    public void setFlightPackage(FlightPackage pk) {
//        this.flightPackage = pk;
//    }
    @ManyToOne(cascade = {CascadeType.PERSIST})
    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * @return the operationStatus
     */
    public String getOperationStatus() {
        return operationStatus;
    }

    /**
     * @param operationStatus the operationStatus to set
     */
    public void setOperationStatus(String operationStatus) {
        this.operationStatus = operationStatus;
    }

    /**
     * @return the flightStatus
     */
    public String getFlightStatus() {
        return flightStatus;
    }

    /**
     * @param flightStatus the flightStatus to set
     */
    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    /**
     * @return the EstimatedDepartureTime
     */
    public String getEstimatedDepartureTime() {
        return estimatedDepartureTime;
    }

    /**
     * @param EstimatedDepartureTime the EstimatedDepartureTime to set
     */
    public void setEstimatedDepartureTime(String EstimatedDepartureTime) {
        this.estimatedDepartureTime = EstimatedDepartureTime;
    }

    /**
     * @return the EstimatedArrivalTime
     */
    public String getEstimatedArrivalTime() {
        return estimatedArrivalTime;
    }

    /**
     * @param EstimatedArrivalTime the EstimatedArrivalTime to set
     */
    public void setEstimatedArrivalTime(String EstimatedArrivalTime) {
        this.estimatedArrivalTime = EstimatedArrivalTime;
    }

    /**
     * @return the ActualDepartureTime
     */
    public String getActualDepartureTime() {
        return actualDepartureTime;
    }

    /**
     * @param ActualDepartureTime the ActualDepartureTime to set
     */
    public void setActualDepartureTime(String ActualDepartureTime) {
        this.actualDepartureTime = ActualDepartureTime;
    }

    /**
     * @return the ActualArrivalTime
     */
    public String getActualArrivalTime() {
        return actualArrivalTime;
    }

    /**
     * @param ActualArrivalTime the ActualArrivalTime to set
     */
    public void setActualArrivalTime(String ActualArrivalTime) {
        this.actualArrivalTime = ActualArrivalTime;
    }

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    public int compareTo(Flight fl) {
        DateTime thisTime = new DateTime(this.estimatedDepartureTime);
        DateTime flTime = new DateTime(fl.estimatedDepartureTime);
        return thisTime.compareTo(flTime);
    }

}
