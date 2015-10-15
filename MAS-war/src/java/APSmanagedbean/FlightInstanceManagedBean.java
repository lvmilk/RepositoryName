package APSmanagedbean;

import Entity.APS.Aircraft;
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
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

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
    private List<FlightFrequency> selectedList;

    private Date currentDate = new Date();
    private Date flightDate;
    private String flightStatus;  // scheduled/ active/ landed/ cancelled/ diverted/ redirected

    //will be modified later
    private Date estimatedDepTime;
    private Date estimatedArrTime;
    private Integer estimatedDateAdjust;
    private Date actualDepTime;
    private Date actualArrTime;
    private Integer actualDateAdjust;

///////////
    private String flightNo;

    private Calendar cal = new GregorianCalendar();
    private Calendar currentCal = new GregorianCalendar();

    private Date startDate;
    private Date finishDate;
    private boolean onMon;
    private boolean onTue;
    private boolean onWed;
    private boolean onThu;
    private boolean onFri;
    private boolean onSat;
    private boolean onSun;

    private Date startPlanDate;
    private Date endPlanDate;

    private String firstInstDate;
    private String minDate;

    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
    DateFormat df2 = new SimpleDateFormat("HH:mm");

    public FlightInstanceManagedBean() {
    }

    @PostConstruct
    public void init() {
        currentCal = Calendar.getInstance();
        currentCal.setTime(currentDate);
        currentCal.add(Calendar.DATE, 1);
        currentDate = currentCal.getTime();

        flightFreqList = fsb.getAllFlightFrequency();
        flightInstList = fsb.getAllFlightInstance();
        aircraftList = fpb.getAllAircraft();

        for (int i = 0; i < aircraftList.size(); i++) {
            registrationList.add(aircraftList.get(i).getRegistrationNo());
        }
        selectedList = new ArrayList<FlightFrequency>();
        //aircraft = (Aircraft) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraft");
        flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightFreq");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        flightDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightDate");
        flightStatus = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightStatus");
        estimatedDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDepTime");
        estimatedArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedArrTime");
        estimatedDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("estimatedDateAdjust");
        actualDepTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDepTime");
        actualArrTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualArrTime");
        actualDateAdjust = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("actualDateAdjust");
        startDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("startDate");
        finishDate = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("finishDate");

        if (FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flightFrequency") != null) {
            flightFreq = (FlightFrequency) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flightFrequency");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This flight " + flightFreq.getFlightNo() + " fails to generate instances, because it has been already assigned with flight instances within the period from " + flightFreq.getsDate() + " to " + flightFreq.getfDate() + "!", ""));
        }
    }

    public void beforePhaseListener(PhaseEvent event) {

    }

    public void addFlightInstance() throws IOException, ParseException {
        if (selectedList.isEmpty()) {
            System.out.println("flightInstanceeManagedBean: addFlightInstance: empty selected list. ");
            FacesContext.getCurrentInstance().addMessage("dlg", new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please SELECT a flight to generate! ", ""));
        } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("selectedFlight", selectedList);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightFreq", flightFreq);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightFreq.getFlightNo());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDepTime", df2.parse(flightFreq.getScheduleDepTime()));
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedArrTime", df2.parse(flightFreq.getScheduleArrTime()));
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("estimatedDateAdjust", flightFreq.getDateAdjust());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDepTime", df2.parse(flightFreq.getScheduleDepTime()));
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualArrTime", df2.parse(flightFreq.getScheduleArrTime()));
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("actualDateAdjust", flightFreq.getDateAdjust());
            System.out.println("flightInstanceeManagedBean: addFlightInstance: flight instance list size: " + selectedList.size());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./generateFlightInstance.xhtml");
        }
    }

    public void checkSelect() throws IOException {
        RequestContext context = RequestContext.getCurrentInstance();
        if (selectedList.isEmpty()) {
            context.execute("alert('Please select a flight to generate instances! ');");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please select  a flight to generate instances. ", ""));
        } else {
            context.execute("PF('dlg').show()");
        }
    }

    public void check(SelectEvent event) {
        System.out.println("in check");
    }

    public void addFlightInstBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightInstance.xhtml");
    }

    public void generateFlightInstance(List<FlightFrequency> selectedList) throws Exception {
        for (int i = 0; i < selectedList.size(); i++) {
            Date newStartDate = startDate;
            Date newFinishDate = finishDate;
            flightFreq = selectedList.get(i);
            flightNo = selectedList.get(i).getFlightNo();
            System.out.println("flightInstanceManagedBean:generateFlightInstance: This flight frequency " + flightFreq + " with flight No. " + flightNo);

            //set default values
            estimatedDateAdjust = flightFreq.getDateAdjust();
            actualDateAdjust = flightFreq.getDateAdjust();
            flightStatus = "Scheduled";
            String ed = flightFreq.getScheduleDepTime();
            String ea = flightFreq.getScheduleArrTime();
            String ad = flightFreq.getScheduleDepTime();
            String aa = flightFreq.getScheduleArrTime();

            Boolean Mon = flightFreq.isOnMon();
            Boolean Tue = flightFreq.isOnTue();
            Boolean Wed = flightFreq.isOnWed();
            Boolean Thu = flightFreq.isOnThu();
            Boolean Fri = flightFreq.isOnFri();
            Boolean Sat = flightFreq.isOnSat();
            Boolean Sun = flightFreq.isOnSun();

//        aircraft = new Aircraft();
//        aircraft = fsb.getAircraft(registrationNo);
//        Date deliveryDate = df1.parse(aircraft.getDeliveryDate());
            //frequency scheduling start date and end date
            Date freqStartDate = df1.parse(flightFreq.getStartDate());
            Date freqEndDate = df1.parse(flightFreq.getEndDate());
            System.out.println("flightInstanceManagedBean:generateFlightInstance: This flight frequency is scheduled from " + freqStartDate + " to " + freqEndDate);
            System.out.println("flightInstanceManagedBean:generateFlightInstance: This flight Instance is scheduled from " + startDate + " to " + finishDate);
            //check data in flight frequency
            //  Date sDate = df1.parse(flightFreq.getsDate()); 
            // Date fDate = df1.parse(flightFreq.getfDate());

            try {
                if (startDate.after(new Date())) {
                    System.out.println("flightInstanceManagedBean:generateFlightInstance:Flight Instance start date " + startDate + " is later than current date");
//                if (startDate.after(deliveryDate)) {
                    if ((!startDate.before(freqStartDate)) && finishDate.before(freqEndDate)) {

                        if ((flightFreq.getsDate() == null || flightFreq.getsDate().equals("")) || (flightFreq.getfDate() == null || flightFreq.getfDate().equals(""))) {
                            System.out.println("flightInstanceManagedBean:generateFlightInstance: there is one checking date is null... setting new checking date...");
                            Long id = flightFreq.getId();
                            System.out.println("flightInstanceManagedBean:generateFlightInstance:this flight frequency ID:" + id);
                            fsb.setCheckDate(id, df1.format(startDate), df1.format(finishDate));

                            while (startDate.compareTo(finishDate) <= 0) {
                                if (checkDayOfWeek(startDate, Mon, Tue, Wed, Thu, Fri, Sat, Sun)) {
                                    String sd = df1.format(startDate);
                                    fsb.addFlightInstance(flightFreq, sd, flightStatus, ed, ea, estimatedDateAdjust, ad, aa, actualDateAdjust);
                                    System.out.println("flightInstanceManagedBean:generateFlightInstance:This flight Instance date: " + startDate);
                                }
                                cal = Calendar.getInstance();
                                cal.setTime(startDate);
                                cal.add(Calendar.DATE, 1);
                                startDate = cal.getTime();
                            }

                            if (i > 0 && selectedList.get(i - 1).getsDate() != null && selectedList.get(i - 1).getfDate() != null) {
                                if (startDate.after(df1.parse(selectedList.get(i - 1).getsDate())) && startDate.before(df1.parse(selectedList.get(i - 1).getsDate()))
                                        || startDate.after(df1.parse(selectedList.get(i - 1).getfDate())) && finishDate.before(df1.parse(selectedList.get(i - 1).getfDate()))) {
                                    System.out.println("Checking null situation...//////////////////The last fligt intance is not suitable for the period!");
                                    System.out.println("flightInstanceManagedBean:generateFlightInstance: flight " + selectedList.get(i - 1).getFlightNo() + " fails to generate instances.....");
                                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This flight " + selectedList.get(i - 1).getFlightNo()
                                            + " fails to generate instances, because it has been already assigned with flight instances within the period from "
                                            + selectedList.get(i - 1).getsDate() + " to " + selectedList.get(i - 1).getfDate() + "!", ""));
                                    FacesContext.getCurrentInstance().getExternalContext().getFlash().put("flightFrequency", flightFreq);
                                }
                            }

                            FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightInstanceConfirm.xhtml");
                        } else if (startDate.after(df1.parse(flightFreq.getfDate())) || finishDate.before(df1.parse(flightFreq.getsDate()))) {
                            System.out.println("flightInstanceManagedBean:generateFlightInstance:setting up new checking date...");
                            if (startDate.after(df1.parse(flightFreq.getfDate()))) {
                                System.out.println("flightInstanceManagedBean:generateFlightInstance:start date is after the default finish date.......");
                                Long id = flightFreq.getId();
                                fsb.setCheckDate(id, flightFreq.getsDate(), df1.format(finishDate)); //keep start date, change finish date
                                System.out.println("flightInstanceManagedBean:generateFlightInstance:new finish date has set......." + finishDate);
                            }
                            if (finishDate.before(df1.parse(flightFreq.getsDate()))) {
                                System.out.println("flightInstanceManagedBean:generateFlightInstance:finish date is before the default finish date.......");
                                Long id = flightFreq.getId();
                                fsb.setCheckDate(id, df1.format(startDate), flightFreq.getfDate()); //keep finish date, change start date
                                System.out.println("flightInstanceManagedBean:generateFlightInstance:new start date has set......." + startDate);
                            }

                            while (startDate.compareTo(finishDate) <= 0) {
                                if (checkDayOfWeek(startDate, Mon, Tue, Wed, Thu, Fri, Sat, Sun)) {
                                    String sd = df1.format(startDate);
                                    fsb.addFlightInstance(flightFreq, sd, flightStatus, ed, ea, estimatedDateAdjust, ad, aa, actualDateAdjust);
                                    System.out.println("flightInstanceManagedBean:generateFlightInstance:This flight Instance date: " + startDate);
                                }
                                cal = Calendar.getInstance();
                                cal.setTime(startDate);
                                cal.add(Calendar.DATE, 1);
                                startDate = cal.getTime();
                            }
                            System.out.println("Checking 1...//////////////////There is at least one fligt intance is suitable for the period!");
                            System.out.println("flightInstanceManagedBean:generateFlightInstance: flight " + flightNo + " success to generate instances.....");
                            FacesContext.getCurrentInstance().getExternalContext().redirect("./addFlightInstanceConfirm.xhtml");
                        } else {
                            System.out.println("Checking 2...//////////////////There is at least one fligt intance is not suitable for the period!");
                            System.out.println("flightInstanceManagedBean:generateFlightInstance: flight " + flightNo + " fails to generate instances.....");
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "This flight " + flightFreq.getFlightNo() + " fails to generate instances, because it has been already assigned with flight instances within the period from " + flightFreq.getsDate() + " to " + flightFreq.getfDate() + "!", ""));
                            FacesContext.getCurrentInstance().getExternalContext().getFlash().put("flightFrequency", flightFreq);
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "The Flight Frequency period is from " + flightFreq.getStartDate() + " to " + flightFreq.getEndDate() + ". Please select Flight Instance within the period!", ""));
                    }
//                } else {
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "This aircraft has not been delivered at this start time! Delivery Date is " + aircraft.getDeliveryDate(), ""));
//                }
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please start with a future date! ", ""));
                }
            } catch (Exception ex) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
            }
            startDate = newStartDate;
            finishDate = newFinishDate;
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

    public Integer getEstimatedDateAdjust() {
        return estimatedDateAdjust;
    }

    public void setEstimatedDateAdjust(Integer estimatedDateAdjust) {
        this.estimatedDateAdjust = estimatedDateAdjust;
    }

    public Integer getActualDateAdjust() {
        return actualDateAdjust;
    }

    public void setActualDateAdjust(Integer actualDateAdjust) {
        this.actualDateAdjust = actualDateAdjust;
    }

    public Date getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(Date currentDate) {
        this.currentDate = currentDate;
    }

    public Date getStartPlanDate() throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        startPlanDate = df1.parse(getFirstInstDate());
        return startPlanDate;
    }

    public void setStartPlanDate(Date startPlanDate) {
        System.out.println("FSMB:getStartPlanDate is " + startPlanDate.toString());

        this.startPlanDate = startPlanDate;

    }

    public Date getEndPlanDate() {
        return endPlanDate;
    }

    public void setEndPlanDate(Date endPlanDate) {
        System.out.println("FSMB: getEndPlanDate is " + endPlanDate.toString());

        this.endPlanDate = endPlanDate;
    }

    //------------------------------------------Hanyu added-------------------------------------------------
    public void scheduleAcToFi() throws ParseException, IOException, Exception {
        System.out.println("FSMB: scheduleAcToFi(): startPlanDate is " + startPlanDate.toString());
        System.out.println("FSMB: scheduleAcToFi(): endPlanDate is " + endPlanDate.toString());
        try {
//        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//        startPlanDate=df1.parse(getFirstInstDate());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startPlanDate", startPlanDate);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endPlanDate", endPlanDate);
            fsb.scheduleAcToFi(startPlanDate, endPlanDate);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("startPlanDate", startPlanDate);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("endPlanDate", endPlanDate);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./assignFlightView.xhtml");
        } catch (IOException | ParseException ex) {
            System.out.println("Oops: Error! " + ex.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "An error has occured" + ex.getMessage(), "");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }
    }

    public String getFirstInstDate() throws ParseException {
        System.out.println("FIMB: getFirstInstDate!");
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        startPlanDate = df1.parse(fsb.getFirstInstDate());
        return fsb.getFirstInstDate();
    }

    public String getMinDate() throws ParseException {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = df1.parse(fsb.getFirstInstDate());
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, 1);  // number of days to add
        minDate = df1.format(c.getTime());
        return minDate;
    }

    public List<FlightFrequency> getSelectedList() {
        return selectedList;
    }

    public void setSelectedList(List<FlightFrequency> selectedList) {
        this.selectedList = selectedList;
    }

}
