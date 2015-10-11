package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.Airport;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import SessionBean.APS.FlightSchedulingBeanLocal;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Xi
 */
@Named(value = "FIMB")
@ViewScoped
public class FlightInstanceManagedBean implements Serializable {

    @EJB
    private FlightSchedulingBeanLocal fsb;
    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<FlightFrequency> flightFreqList;
    private List<FlightInstance> flightInstList;
    private List<Aircraft> aircraftList;
    private FlightFrequency flightFreq;
    private Aircraft aircraft;
    private List<String> registrationList = new ArrayList<String>();
    private String registrationNo;
    private Long id; //flight instance ID

    private Date flightDate;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected

    //will be modified later
    private Date estimatedDepTime;
    private Date estimatedArrTime;
    private Date actualDepTime;
    private Date actualArrTime;

    private String flightNo;

    private Calendar cal = new GregorianCalendar();

    private Date startDate;
    private Date finishDate;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df2 = new SimpleDateFormat("HH:mm");

    public FlightInstanceManagedBean() {
    }

    @PostConstruct
    public void init() {
        flightFreqList = fsb.getAllFlightFrequency();
        flightInstList = fsb.getAllFlightInstance();
        aircraftList = fpb.getAllAircraft();
        for (int i = 0; i < aircraftList.size(); i++) {
            registrationList.add(aircraftList.get(i).getRegistrationNo());
        }
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightFreq");
        aircraft = (Aircraft) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraft");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        flightDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDate");
        flightStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightStatus");
        estimatedDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDepTime");
        estimatedArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedArrTime");
        actualDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDepTime");
        actualArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualArrTime");
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        finishDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("finishDate");
    }

    public void addFlightInstance(FlightFrequency flightFreq) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightFreq", flightFreq);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightFreq.getFlightNo());
        System.out.println(flightFreq.getFlightNo());
        FacesContext.getCurrentInstance().getExternalContext().redirect("./generateFlightInstance.xhtml");
    }

    public void generateFlightInstance() throws Exception {
        //String d = df1.format(flightDate);
        String ed = df2.format(estimatedDepTime);
        String ea = df2.format(estimatedArrTime);
        String ad = df2.format(actualDepTime);
        String aa = df2.format(actualArrTime);
        System.out.println("This flight frequency " + flightFreq + " is with aircraft " + registrationNo);
        System.out.println("flight time(*4):" + ed + " " + ea + " " + ad + " " + aa);
        Boolean Mon = flightFreq.isOnMon();
        Boolean Tue = flightFreq.isOnTue();
        Boolean Wed = flightFreq.isOnWed();
        Boolean Thu = flightFreq.isOnThu();
        Boolean Fri = flightFreq.isOnFri();
        Boolean Sat = flightFreq.isOnSat();
        Boolean Sun = flightFreq.isOnSun();
        //bug unknown aircraft
        Date deliveryDate = df1.parse(aircraft.getDeliveryDate());
        System.out.println("Flight Instance: this aircraft delivery date is " + deliveryDate);

        try {
            if (!startDate.before(new Date())) {
                System.out.println("Flight Instance start date is later than current date");
                if (startDate.after(deliveryDate)) {
                    System.out.println("Flight Instance start date is later than aircraft delivery date");
                    while (startDate.compareTo(finishDate) <= 0) {
                        if (checkDayOfWeek(startDate, Mon, Tue, Wed, Thu, Fri, Sat, Sun)) {
                            String d = df1.format(startDate);
                            fsb.addFlightInstance(flightFreq, registrationNo, d, flightStatus, ed, ea, ad, aa);
                            System.out.println("Flight Instance current start date: " + startDate);
                        }
                        cal = cal = Calendar.getInstance();
                        cal.setTime(startDate);
                        cal.add(Calendar.DATE, 1);
                        startDate = cal.getTime();
                    }
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightInstanceConfirm.xhtml");
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This aircraft has not been delivered!", ""));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please start with a future date! ", ""));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public boolean checkDayOfWeek(Date currentDate, boolean Mon, boolean Tue, boolean Wed, boolean Thu, boolean Fri, boolean Sat, boolean Sun) throws IOException {
        cal = Calendar.getInstance();
        cal.setTime(currentDate);
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        return (dayOfWeek == 1 && Sun)
                || (dayOfWeek == 2 && Mon)
                || (dayOfWeek == 3 && Tue)
                || (dayOfWeek == 4 && Wed)
                || (dayOfWeek == 5 && Thu)
                || (dayOfWeek == 6 && Fri)
                || (dayOfWeek == 7 && Sat);
    }

    public List<FlightFrequency> getFlightFreqList() {
        return flightFreqList;
    }

    public void setFlightFreqList(List<FlightFrequency> flightFreqList) {
        this.flightFreqList = flightFreqList;
    }

    public List<FlightInstance> getFlightInstList() {
        return flightInstList;
    }

    public void setFlightInstList(List<FlightInstance> flightInstList) {
        this.flightInstList = flightInstList;
    }

    public FlightFrequency getFlightFreq() {
        return flightFreq;
    }

    public void setFlightFreq(FlightFrequency flightFreq) {
        this.flightFreq = flightFreq;
    }

    public Date getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(Date flightDate) {
        this.flightDate = flightDate;
    }

    public String getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(String flightStatus) {
        this.flightStatus = flightStatus;
    }

    public Date getEstimatedDepTime() {
        return estimatedDepTime;
    }

    public void setEstimatedDepTime(Date estimatedDepTime) {
        this.estimatedDepTime = estimatedDepTime;
    }

    public Date getEstimatedArrTime() {
        return estimatedArrTime;
    }

    public void setEstimatedArrTime(Date estimatedArrTime) {
        this.estimatedArrTime = estimatedArrTime;
    }

    public Date getActualDepTime() {
        return actualDepTime;
    }

    public void setActualDepTime(Date actualDepTime) {
        this.actualDepTime = actualDepTime;
    }

    public Date getActualArrTime() {
        return actualArrTime;
    }

    public void setActualArrTime(Date actualArrTime) {
        this.actualArrTime = actualArrTime;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public Aircraft getAircraft() {
        return aircraft;
    }

    public void setAircraft(Aircraft aircraft) {
        this.aircraft = aircraft;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<String> getRegistrationList() {
        return registrationList;
    }

    public void setRegistrationList(List<String> registrationList) {
        this.registrationList = registrationList;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
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

    //Hanyu added
    public void scheduleAcToFi(Date startDate, Date endDate) throws ParseException {
      
       // fsb.scheduleAcToFi(startDate, endDate);
        
    }

}
