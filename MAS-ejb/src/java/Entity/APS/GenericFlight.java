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
import javax.persistence.*;

/**
 *
 * @author victor
 */
@Entity
public class GenericFlight implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String flightNo;
    private int StopoverSequenceNo;
    
    @ManyToOne
    private Route route = new Route();
    @ManyToOne
    private AircraftType aircraftType = new AircraftType();
    
    private String scheduledDepartureTime;
    private String scheduledArrivalTime;
    private int dateAdjust;
    
    /*To indicate the frequency of this flight*/
    private String startDate;
    private String endDate;
    private boolean Mon;
    private boolean Tue;
    private boolean Wed;
    private boolean Thu;
    private boolean Fri;
    private boolean Sat;
    private boolean Sun;
    
    private String Operator;
    private ArrayList<String> codeshare;
    private List<Flight> flightList = new ArrayList<Flight>();

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
        if (!(object instanceof GenericFlight)) {
            return false;
        }
        GenericFlight other = (GenericFlight) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        String st = "";
        st += this.id + "\t";
        st += this.flightNo + "\t";
        st += this.StopoverSequenceNo + "\t";
        st += this.getRoute().getOrigin().getIATA() + "\t";
        st += this.getRoute().getDestination().getIATA() + "\t";
        st += this.scheduledDepartureTime + "\t";
        st += this.scheduledArrivalTime + "\t";

        return st;
    }

    /**
     * @return the flightNo
     */
    public String getFlightNo() {
        return flightNo;
    }

    /**
     * @param flightNo the flightNo to set
     */
    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * @return the StopoverSequenceNo
     */
    public int getStopoverSequenceNo() {
        return StopoverSequenceNo;
    }

    /**
     * @param StopoverSequenceNo the StopoverSequenceNo to set
     */
    public void setStopoverSequenceNo(int StopoverSequenceNo) {
        this.StopoverSequenceNo = StopoverSequenceNo;
    }

    /**
     * @return the route
     */
    public Route getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(Route route) {
        this.route = route;
    }

    /**
     * @return the ScheduledDepartureTime
     */
    public String getScheduledDepartureTime() {
        return scheduledDepartureTime;
    }

    /**
     * @param ScheduledDepartureTime the ScheduledDepartureTime to set
     */
    public void setScheduledDepartureTime(String ScheduledDepartureTime) {
        this.scheduledDepartureTime = ScheduledDepartureTime;
    }

    /**
     * @return the ShceduledArrivalTime
     */
    public String getScheduledArrivalTime() {
        return scheduledArrivalTime;
    }

    /**
     * @param ShceduledArrivalTime the ShceduledArrivalTime to set
     */
    public void setScheduledArrivalTime(String ShceduledArrivalTime) {
        this.scheduledArrivalTime = ShceduledArrivalTime;
    }

    /**
     * @return the dateAdjust
     */
    public int getDateAdjust() {
        return dateAdjust;
    }

    /**
     * @param dateAdjust the dateAdjust to set
     */
    public void setDateAdjust(int dateAdjust) {
        this.dateAdjust = dateAdjust;
    }

    /**
     * @return the Mon
     */
    public boolean isMon() {
        return Mon;
    }

    /**
     * @param Mon the Mon to set
     */
    public void setMon(boolean Mon) {
        this.Mon = Mon;
    }

    /**
     * @return the Tue
     */
    public boolean isTue() {
        return Tue;
    }

    /**
     * @param Tue the Tue to set
     */
    public void setTue(boolean Tue) {
        this.Tue = Tue;
    }

    /**
     * @return the Wed
     */
    public boolean isWed() {
        return Wed;
    }

    /**
     * @param Wed the Wed to set
     */
    public void setWed(boolean Wed) {
        this.Wed = Wed;
    }

    /**
     * @return the Thu
     */
    public boolean isThu() {
        return Thu;
    }

    /**
     * @param Thu the Thu to set
     */
    public void setThu(boolean Thu) {
        this.Thu = Thu;
    }

    /**
     * @return the Fri
     */
    public boolean isFri() {
        return Fri;
    }

    /**
     * @param Fri the Fri to set
     */
    public void setFri(boolean Fri) {
        this.Fri = Fri;
    }

    /**
     * @return the Sat
     */
    public boolean isSat() {
        return Sat;
    }

    /**
     * @param Sat the Sat to set
     */
    public void setSat(boolean Sat) {
        this.Sat = Sat;
    }

    /**
     * @return the Sun
     */
    public boolean isSun() {
        return Sun;
    }

    /**
     * @param Sun the Sun to set
     */
    public void setSun(boolean Sun) {
        this.Sun = Sun;
    }

    /**
     * @return the Operator
     */
    public String getOperator() {
        return Operator;
    }

    /**
     * @param Operator the Operator to set
     */
    public void setOperator(String Operator) {
        this.Operator = Operator;
    }

    /**
     * @return the codeshare
     */
    public ArrayList<String> getCodeshare() {
        return codeshare;
    }

    /**
     * @param codeshare the codeshare to set
     */
    public void setCodeshare(ArrayList<String> codeshare) {
        this.codeshare = codeshare;
    }

    /**
     * @return the flightList
     */
    @OneToMany(cascade={CascadeType.PERSIST}, mappedBy ="genericFlight")
    public List<Flight> getFlightList() {
        return flightList;
    }
    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
    }
    public void addFlight(Flight fl){
        fl.setGenericFlight(this);
        this.flightList.add(fl);
    }

    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public String getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the aircraftType
     */
    public AircraftType getAircraftType() {
        return aircraftType;
    }

    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }
    
}
