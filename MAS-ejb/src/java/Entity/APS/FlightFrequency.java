package Entity.APS;

import java.io.Serializable;
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
//    private LocalTime scheduleDepTime;
//    private LocalTime scheduleArrTime;
    private Integer dateAdjust;
    private String scheduleDepTime;
    private String scheduleArrTime;
    private String startDate;
    private String endDate;
//    private LocalDate startDate;
//    private LocalDate endDate;
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
//    private String status;

    //new added by lucy --> when generate flight instance, check the available date interval of this fligh schedule
    private String sDate="1900-01-01";
    private String fDate="1900-01-01";

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "flightFrequency")
    private List<FlightInstance> flightList = new ArrayList<>();

    public void create(Route route, String flightNo, String depTime, String arrTime, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun, String startDate, String endDate, String sDate, String fDate) {
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
//        this.status = "Pending";
        this.sDate = sDate;
        this.fDate = fDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
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

//
//    public AircraftType getAircraftType() {
//        return aircraftType;
//    }
//
//    public void setAircraftType(AircraftType aircraftType) {
//        this.aircraftType = aircraftType;
//    }
//    public LocalTime getScheduleDepTime() {
//        return scheduleDepTime;
//    }
//
//    public void setScheduleDepTime(LocalTime scheduleDepTime) {
//        this.scheduleDepTime = scheduleDepTime;
//    }
//
//    public LocalTime getScheduleArrTime() {
//        return scheduleArrTime;
//    }
//
//    public void setScheduleArrTime(LocalTime scheduleArrTime) {
//        this.scheduleArrTime = scheduleArrTime;
//    }
//
//    public LocalDate getStartDate() {
//        return startDate;
//    }
//
//    public void setStartDate(LocalDate startDate) {
//        this.startDate = startDate;
//    }
//
//    public LocalDate getEndDate() {
//        return endDate;
//    }
//
//    public void setEndDate(LocalDate endDate) {
//        this.endDate = endDate;
//    }
    public Integer getDateAdjust() {
        return dateAdjust;
    }

    public void setDateAdjust(Integer dateAdjust) {
        this.dateAdjust = dateAdjust;
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

    public List<FlightInstance> getFlightList() {
        return flightList;
    }

    public void setFlightList(List<FlightInstance> flightList) {
        this.flightList = flightList;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public String getfDate() {
        return fDate;
    }

    public void setfDate(String fDate) {
        this.fDate = fDate;
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
        return "" + flightNo + route.toString();
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
