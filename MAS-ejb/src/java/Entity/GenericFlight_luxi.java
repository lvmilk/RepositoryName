package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.*;

@Entity
public class GenericFlight implements Serializable {
    private static final long serialVersionUID = 1L;
    @OneToMany(cascade={CascadeType.ALL},mappedBy="GenericFlight")
    private Collection<Flight> flights=new ArrayList<Flight>();
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer flightNo;
    private Integer StopoverSequenceNo;
    private String ScheduledDepartureTime;
    private String ShceduledArrivalTime;
    private int dateAdjust;
    
        /*To indicate the frequency of this flight*/
    private boolean Mon;
    private boolean Tue;
    private boolean Wed;
    private boolean Thu;
    private boolean Fri;
    private boolean Sat;
    private boolean Sun;
    
    private String Operator;
    private ArrayList<String> codeshare;

    public void create(Integer flightNo, Integer stopoverSNo){
        this.setFlightNo(flightNo);
        this.setStopoverSequenceNo(stopoverSNo);
    }
    
    public Collection<Flight> getFlight(){
        return flights;
    }
    
    public void setFlight(Collection<Flight> flights){
        this.flights=flights;
    }
    
//    @OneToOne
//    private ODPairs route;
   
    
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
        return "Entity.GenericFlight[ id=" + id + " ]";
    }

    /**
     * @return the flightNo
     */
    public Integer getFlightNo() {
        return flightNo;
    }

    /**
     * @param flightNo the flightNo to set
     */
    public void setFlightNo(Integer flightNo) {
        this.flightNo = flightNo;
    }

    /**
     * @return the StopoverSequenceNo
     */
    public Integer getStopoverSequenceNo() {
        return StopoverSequenceNo;
    }

    /**
     * @param StopoverSequenceNo the StopoverSequenceNo to set
     */
    public void setStopoverSequenceNo(Integer StopoverSequenceNo) {
        this.StopoverSequenceNo = StopoverSequenceNo;
    }

    /**
     * @return the route
     */
//    public ODPairs getRoute() {
//        return route;
//    }
//
//    /**
//     * @param route the route to set
//     */
//    public void setRoute(ODPairs route) {
//        this.route = route;
//    }

    /**
     * @return the ScheduledDepartureTime
     */
    public String getScheduledDepartureTime() {
        return ScheduledDepartureTime;
    }

    /**
     * @param ScheduledDepartureTime the ScheduledDepartureTime to set
     */
    public void setScheduledDepartureTime(String ScheduledDepartureTime) {
        this.ScheduledDepartureTime = ScheduledDepartureTime;
    }

    /**
     * @return the ShceduledArrivalTime
     */
    public String getShceduledArrivalTime() {
        return ShceduledArrivalTime;
    }

    /**
     * @param ShceduledArrivalTime the ShceduledArrivalTime to set
     */
    public void setShceduledArrivalTime(String ShceduledArrivalTime) {
        this.ShceduledArrivalTime = ShceduledArrivalTime;
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
    
}
