/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DCSmanagedbean;

import Entity.ADS.Seat;
import Entity.ADS.Ticket;
import Entity.APS.FlightFrequency;
import Entity.APS.FlightInstance;
import SessionBean.DCS.DepartureControlBeanLocal;
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
    private Date date;
    private String dateString = new String();
    private List<Ticket> tickets = new ArrayList<Ticket>();
    private Ticket ticket = new Ticket();
    private List<Seat> unOccupiedSeats = new ArrayList<Seat>();
    private Seat seatSelected;
    private Integer luggageCount;
    @PostConstruct
    public void init() {
        tickets = (List<Ticket>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tickets");
        ticket = (Ticket) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ticket");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        firstName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName");
        lastName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastName");
        passportNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("passportNo");
        seatSelected= (Seat)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seat");
    }

    public void onDateChange() {
        System.out.println("CMB:OnDateChange run");
        try {
            System.out.println("CMB:OnDateChange run");
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            this.setDateString(df1.format(date));

            if (dateString != null && !dateString.equals("")) {
                ffList = dcb.getFlightList(dateString);
                System.out.println("CMB:OnDateChange run result: " + ffList.toString());
            }

        } catch (Exception ex) {
            System.out.println("CMB:OnDateChange error");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    //choose tickets within 24 hours
    public void getUnusedTicket() throws Exception {
//        try {
        if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
            FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
            for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {
                DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
                Date dateTemp = df1.parse(ticket.getDepTime());
                Calendar c1 = Calendar.getInstance();
                c1.setTime(dateTemp);
                c1.add(Calendar.DATE, -1);
                dateTemp = c1.getTime();
                if (date.after(dateTemp) && ticket.getTicketStatus().equals("Unused")) {
                    tickets.add(ticket);
                }
            }

        }
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
    }

    public void getUnoccupiedSeat(Ticket ticket) {
        try {
            unOccupiedSeats = dcb.getAllUnOccupiedSeats(ticket);

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void onGetTicketChange() throws Exception {
        try {
            this.getUnusedTicket();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void onCheckinChange(ActionEvent event) {
        try {
            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            dcb.changeCheckinStatus(ticket);
            this.getUnoccupiedSeat(ticket);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn3.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void onStandbyChange(ActionEvent event) {
        try {
            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            
            dcb.changeStandbyStatus(ticket);
            this.getUnoccupiedSeat(ticket);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./standBy.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void onSelectSeat(ActionEvent event){
       try{ 
           this.seatSelected=(Seat) event.getComponent().getAttributes().get("seat");
          dcb.selectSeat(seatSelected);
          FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat",seatSelected);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn4.xhtml");
       }catch(Exception ex){
           
       }
    }
    
    
    public boolean isOnlineCheckedIn(Ticket tkt) {
        return tkt.getTicketStatus().equals("OnlineCheckedin");
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
        System.out.println("DateString: " + dateString);
        this.dateString = dateString;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public List<Seat> getUnOccupiedSeats() {
        return unOccupiedSeats;
    }

    public void setUnOccupiedSeats(List<Seat> unOccupiedSeats) {
        this.unOccupiedSeats = unOccupiedSeats;
    }

    public Seat getSeatSelected() {
        return seatSelected;
    }

    public void setSeatSelected(Seat seatSelected) {
        this.seatSelected = seatSelected;
    }

    public Integer getLuggageCount() {
        return luggageCount;
    }

    public void setLuggageCount(Integer luggageCount) {
        this.luggageCount = luggageCount;
    }

    /**
     * Creates a new instance of CheckInManagedBean
     */
    public CheckInManagedBean() {

    }

}
