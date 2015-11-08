/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DCSmanagedbean;

import Entity.ADS.Ticket;
import Entity.APS.FlightFrequency;
import static Entity.APS.FlightFrequency_.flightList;
import Entity.APS.FlightInstance;
import Session.DCS.DepartureControlBeanLocal;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author wang
 */
@Named(value = "cmb")
@ViewScoped
public class CheckInManagedBean implements Serializable {

    @EJB
    DepartureControlBeanLocal dcb;

    private String passportNo;
    private String firstName;
    private String lastName;
    private String flightNo;
    private List<FlightFrequency> ffList = new ArrayList<FlightFrequency>();
    ;
    private Date date;
    private String dateString = new String();
    private List<Ticket> tickets = new ArrayList<Ticket>();

    
    @PostConstruct
    public void init() {
        tickets = (List<Ticket>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");

    }

//      public void onDateChange() {
//        System.out.println("MBPB:OnDateChange run");
//        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//        dateString = df1.format(date);
//
//        if (dateString != null && !dateString.equals("")) {
//            try {
//                ffList = dcb.getFlightList(dateString);
//                System.out.println("MB:OnDateChange run result: " + flightList.toString());
//            } catch (Exception ex) {
//                Logger.getLogger(CheckInManagedBean.class.getName()).log(Level.SEVERE, null, ex);
//                 
//            }
//        } else {
//            ffList = new ArrayList<FlightFrequency>();
//        }
//    }

    
    
    public void onDateChange(ActionEvent e) throws Exception {
        System.out.println("CMB:OnDateChange run");
        try {
            System.out.println("CMB:OnDateChange run");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            this.setDateString(df1.format(date));

            if (dateString != null && !dateString.equals("")) {
                ffList = dcb.getFlightList(dateString);
                System.out.println("CMB:OnDateChange run result: " + flightList.toString());
            }

        } catch (Exception ex) {
            System.out.println("CMB:OnDateChange error");

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }
    
     //choose tickets within 24 hours
    public void getUnusedTicket() {

        try {

            if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
                FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
                for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {
                    DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateTemp = df1.parse(ticket.getDepTime());
                    Calendar c1 = Calendar.getInstance();
                    c1.setTime(dateTemp);
                    c1.add(Calendar.DATE, -1);
                    dateTemp = c1.getTime();
                    if (date.after(dateTemp)) {
                        tickets.add(ticket);
                    }
                }

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn2.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public List<FlightFrequency> getFfList() {
        return ffList;
    }

    public void setFfList(List<FlightFrequency> ffList) {
        this.ffList = ffList;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        System.out.println("Date: " + date);
        this.date = date;
//         System.out.println("----------------check1");
//
//        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
//                 System.out.println("----------------check2");
//         System.out.println("----------------check3"+df1.format(date));
//
//        this.setDateString(df1.format(date));
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        System.out.println("First name: " + firstName);
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        System.out.println("Last name: " + lastName);
        this.lastName = lastName;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        System.out.println("DateString: "+dateString);
        this.dateString = dateString;
    }

    /**
     * Creates a new instance of CheckInManagedBean
     */
    public CheckInManagedBean() {

    }

}
