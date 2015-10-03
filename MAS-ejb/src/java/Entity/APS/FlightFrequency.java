package Entity.APS;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Xu
 */
@Entity
public class FlightFrequency implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Route route = new Route();
   
    private String flightNo;
    
//    @ManyToOne
//    private AircraftType aircraftType = new AircraftType();

    private LocalTime scheduleDepTime;
    private LocalTime scheduleArrTime;
    private Integer dateAdjust;

    // frequency of the flight
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    // for code share flights
    private String operator;
    private ArrayList<String> codeshare;
    
//    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "flightFrequency")
//    private List<FlightInstance> flightList = new ArrayList<>();
    
    public void create(Route route, String flightNo, LocalTime depTime, LocalTime arrTime, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, LocalDate startDate, LocalDate endDate) {
        this.flightNo = flightNo;
        this.route = route;
        this.scheduleDepTime = depTime;
        this.scheduleArrTime = arrTime;
        this.dateAdjust = dateAdjust;
        this.onMon = onMon;
        this.onTue = onTue;
        this.onWed = onWed;
        this.onThu = onThu;
        this.onFri = onFri;
        this.onSat = onSat;
        this.onSun = onSun;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

//
//    public AircraftType getAircraftType() {
//        return aircraftType;
//    }
//
//    public void setAircraftType(AircraftType aircraftType) {
//        this.aircraftType = aircraftType;
//    }

    public LocalTime getScheduleDepTime() {
        return scheduleDepTime;
    }

    public void setScheduleDepTime(LocalTime scheduleDepTime) {
        this.scheduleDepTime = scheduleDepTime;
    }

    public LocalTime getScheduleArrTime() {
        return scheduleArrTime;
    }

    public void setScheduleArrTime(LocalTime scheduleArrTime) {
        this.scheduleArrTime = scheduleArrTime;
    }


    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public boolean isOnMon() {
        return onMon;
    }

    public void setOnMon(boolean onMon) {
        this.onMon = onMon;
    }

    public boolean isOnTue() {
        return onTue;
    }

    public void setOnTue(boolean onTue) {
        this.onTue = onTue;
    }

    public boolean isOnWed() {
        return onWed;
    }

    public void setOnWed(boolean onWed) {
        this.onWed = onWed;
    }

    public boolean isOnThu() {
        return onThu;
    }

    public void setOnThu(boolean onThu) {
        this.onThu = onThu;
    }

    public boolean isOnFri() {
        return onFri;
    }

    public void setOnFri(boolean onFri) {
        this.onFri = onFri;
    }

    public boolean isOnSat() {
        return onSat;
    }

    public void setOnSat(boolean onSat) {
        this.onSat = onSat;
    }

    public boolean isOnSun() {
        return onSun;
    }

    public void setOnSun(boolean onSun) {
        this.onSun = onSun;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ArrayList<String> getCodeshare() {
        return codeshare;
    }

    public void setCodeshare(ArrayList<String> codeshare) {
        this.codeshare = codeshare;
    }
//
//    public List<FlightInstance> getFlightList() {
//        return flightList;
//    }
//
//    public void setFlightList(List<FlightInstance> flightList) {
//        this.flightList = flightList;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlightFrequency)) {
            return false;
        }
        FlightFrequency other = (FlightFrequency) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.APS.Flight[ id=" + id + " ]";       
//        String st = "";
//        st += this.id + "\t";
//        st += this.flightNo + "\t";
//        st += this.StopoverSequenceNo + "\t";
//        st += this.getRoute().getOrigin().getIATA() + "\t";
//        st += this.getRoute().getDest().getIATA() + "\t";
//        st += this.scheduledDepartureTime + "\t";
//        st += this.scheduledArrivalTime + "\t";
//        return st;
    }
    
}
