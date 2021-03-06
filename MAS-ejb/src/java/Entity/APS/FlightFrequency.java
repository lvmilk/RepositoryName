package Entity.APS;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

//    private LocalTime scheduleDepTime;
//    private LocalTime scheduleArrTime;
    private Integer dateAdjust;
    private String scheduleDepTime;   //utc time
    private String scheduleArrTime;   //utc time
    private String startDate;
    private String endDate;
    private long durationMinutes;

    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;
    private Integer weekFreq;

    // for code share flights
    private String operator;
    private ArrayList<String> codeshare;
//    private String status;

    //new added by lucy --> when generate flight instance, check the available date interval of this fligh schedule
    private String sDate;
    private String fDate;
    private String depTerminal;
    private String arrTerminal;
//    private String depGate;

    @OneToMany(cascade = {CascadeType.PERSIST}, mappedBy = "flightFrequency")
    private List<FlightInstance> flightList = new ArrayList<>();

    public FlightFrequency create(Route route, String flightNo, String depTime, String arrTime, Integer dateAdjust,
            boolean onMon, boolean onTue, boolean onWed, boolean onThu, boolean onFri, boolean onSat, boolean onSun,
            String startDate, String endDate, String sDate, String fDate, String depTerminal, String arrTerminal) {
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
        LocalTime dep = LocalTime.parse(depTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalTime arr = LocalTime.parse(arrTime, DateTimeFormatter.ofPattern("HH:mm"));
        LocalDate depDate = LocalDate.of(2000, 1, 10);
        LocalDate arrDate = LocalDate.of(2000, 1, 10).plusDays(dateAdjust);
        LocalDateTime depDateTime = LocalDateTime.of(depDate, dep);
        LocalDateTime arrDateTime = LocalDateTime.of(arrDate, arr);
        durationMinutes = java.time.Duration.between(depDateTime, arrDateTime).toMinutes();
        this.depTerminal = depTerminal;
        this.arrTerminal = arrTerminal;

        return this;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWeekFreq() {
        Integer f = 0;
        if (onMon) {
            f += 1;
        }
        if (onTue) {
            f += 1;
        }
        if (onWed) {
            f += 1;
        }
        if (onThu) {
            f += 1;
        }
        if (onFri) {
            f += 1;
        }
        if (onSat) {
            f += 1;
        }
        if (onSun) {
            f += 1;
        }
        return f;
    }

    public void setWeekFreq(Integer weekFreq) {
        this.weekFreq = weekFreq;
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

    public long getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(long durationMinutes) {
        this.durationMinutes = durationMinutes;
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

    public String getDepTerminal() {
        return depTerminal;
    }

    public void setDepTerminal(String depTerminal) {
        this.depTerminal = depTerminal;
    }

    public String getArrTerminal() {
        return arrTerminal;
    }

    public void setArrTerminal(String arrTerminal) {
        this.arrTerminal = arrTerminal;
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
