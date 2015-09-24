package Entity.APS;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author lucy /Xu
 */
@Entity
public class FlightSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Route route = new Route();
   
    private String flightNo;
    private Integer stopoverNo;
    
    @ManyToOne
    private AircraftType aircraftType = new AircraftType();

    private String scheduleDepTime;
    private String scheduleArrTime;
    private Integer dateAdjust;

    // frequency of the flight
    private String startDate;
    private String endDate;
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
    
    private List<Flight> flightList = new ArrayList<Flight>();
    
    public void create(Route route, String flightNo, String depTime, String arrTime, Integer dateAdjust, AircraftType acType, 
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun) {
        this.flightNo = flightNo;
        this.route = route;
        this.aircraftType = acType;
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

    public Integer getStopoverNo() {
        return stopoverNo;
    }

    public void setStopoverNo(Integer stopoverNo) {
        this.stopoverNo = stopoverNo;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public String getScheduleDepTime() {
        return scheduleDepTime;
    }

    public void setScheduleDepTime(String scheduleDepTime) {
        this.scheduleDepTime = scheduleDepTime;
    }

    public String getScheduleArrTime() {
        return scheduleArrTime;
    }

    public void setScheduleArrTime(String scheduleArrTime) {
        this.scheduleArrTime = scheduleArrTime;
    }

    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
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

    public List<Flight> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<Flight> flightList) {
        this.flightList = flightList;
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
        if (!(object instanceof FlightSchedule)) {
            return false;
        }
        FlightSchedule other = (FlightSchedule) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.APS.Flight[ id=" + id + " ]";
    }
    
}
