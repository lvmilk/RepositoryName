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
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author wang
 */
@Named(value = "bmb")
@ViewScoped
public class BoardingManangedBean implements Serializable {

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
    private Seat seatSelected;
    private Date boardingTime;
    private String seatNo;
    private List<Seat> allAvailableSeats = new ArrayList<Seat>();
    private Date minDate = new Date();
    private Date maxDate = new Date();

    @PostConstruct
    public void init() {

        tickets = (List<Ticket>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tickets");
        ticket = (Ticket) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ticket");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        firstName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName");
        lastName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastName");
        passportNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("passportNo");
        seatSelected = (Seat) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seat");
        seatNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seatNo");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        date = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
        allAvailableSeats = (List<Seat>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allAvailableSeats");
        boardingTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("boardingTime");
        minDate = new Date();

        maxDate = new Date();
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
    public void getUnboardedTicket() throws Exception {
//        try {
        List<Ticket> newList = new ArrayList<Ticket>();
        if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
            System.out.println("bmb:getUnboardedTicket(): check1");
            FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
            System.out.println("bmb:getUnboardedTicket(): check2 get requested flight instance: " + requestedFi.getDate());
            System.out.println("bmb:no of tickets found: " + dcb.getAllTicket(passportNo, firstName, lastName).size());
            for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {
                if (ticket.getSeat() != null) {
                    String dateStringTemp = ticket.getBkInstance().getFlightCabin().getFlightInstance().getDate();
                    System.out.println("Date compare? " + dateString + " VS " + dateStringTemp);
                    System.out.println("status compare? " + ticket.getTicketStatus());
                    System.out.println("seat number  compare? " + ticket.getSeat().getSeatNumberToPassenger());

                    if (dateString.equals(dateStringTemp) && !ticket.getTicketStatus().equals("Boarded") && !ticket.getTicketStatus().equals("Standby") && ticket.getSeat().getSeatNumberToPassenger().equals(seatNo)) {
                        newList.add(ticket);
                        System.out.println("bmb: One ticket added!");
                    }
                }
            }
            this.setTickets(newList);
            System.out.println("bmb:Final no of tickets found: " + tickets.size());

        }

    }

    public void getBoardedList() throws Exception {
        List<Ticket> newList = new ArrayList<Ticket>();
        newList = dcb.getAllBoardedTicket(flightNo, dateString);
        if (!newList.isEmpty()) {
            this.setTickets(newList);
        }

    }

    public void onGetBoardedChange() {
        try {
            this.getBoardedList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewBoarded2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void getUnboardedList() throws Exception {
        List<Ticket> newList = new ArrayList<Ticket>();
        newList = dcb.getAllUnBoardedTicket(flightNo, dateString);
        if (!newList.isEmpty()) {
            this.setTickets(newList);
        }

    }

    public void onGetUnboardedChange() {
        try {
            this.getUnboardedList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewUnboarded2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void getCheckedinList() throws Exception {
        List<Ticket> newList = new ArrayList<Ticket>();
        newList = dcb.getAllCheckedInTicket(flightNo, dateString);
        if (!newList.isEmpty()) {
            this.setTickets(newList);
        }

    }

    public void onGetCheckedinListChange() {
        try {
            this.getCheckedinList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewCheckedIn2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void onGetTicketChange() throws Exception {
        try {
            this.getUnboardedTicket();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", seatNo);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./boarding2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void onBoardingChange(ActionEvent event) throws Exception {
        try {
            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            if (!dcb.changeBoardingStatus(ticket)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "The passenger is not eligible for standby!", ""));
            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", seatNo);

                FacesContext.getCurrentInstance().getExternalContext().redirect("./boardingSuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void onStandbyBoardingChange(ActionEvent event) throws Exception {
        try {
            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            if (!dcb.changeBoardingStatus(ticket)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "The passenger is not eligible for standby!", ""));
            } else {
                dcb.selectSeat(seatSelected, ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", seatNo);

                FacesContext.getCurrentInstance().getExternalContext().redirect("./standbyBoardingSuccess.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void goBack() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./boarding.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void goBackforStandbyBoarding() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./standbyBoarding.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void goBackForViewPage() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seatNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./DCSworkspace.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void getAvailableSeatList() throws Exception {
        List<Seat> newList = new ArrayList<Seat>();
        newList = dcb.getAllSeats(flightNo, dateString);
        List<Seat> newList2 = new ArrayList<Seat>();

        if (!newList.isEmpty()) {
            for (Seat st : newList) {
                if (st.getStatus().equals("Unoccupied")) {
                    newList2.add(st);
                }
            }
            this.setAllAvailableSeats(newList2);
        }

    }

    public void onAvailableGetSeatList() {
        try {
            this.getAvailableSeatList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allAvailableSeats", allAvailableSeats);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./standbyBoarding2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    public void onSelectSeat(ActionEvent event) {
        try {
            this.setSeatSelected((Seat) event.getComponent().getAttributes().get("seat"));
            this.setTickets((List<Ticket>) dcb.getAllStandbyTickets(flightNo, dateString));
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", seatSelected);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allAvailableSeats", allAvailableSeats);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./standbyBoarding3.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void goBacktocheckin2() {
        try {
            this.getAvailableSeatList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allAvailableSeats", allAvailableSeats);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./standbyBoarding2.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public List<Seat> getAllAvailableSeats() {
        return allAvailableSeats;
    }

    public void setAllAvailableSeats(List<Seat> allSeats) {
        this.allAvailableSeats = allSeats;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
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
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo;
    }

    public List<FlightFrequency> getFfList() {
        return ffList;
    }

    public void setFfList(List<FlightFrequency> ffList) {
        this.ffList = ffList;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Seat getSeatSelected() {
        return seatSelected;
    }

    public void setSeatSelected(Seat seatSelected) {
        this.seatSelected = seatSelected;
    }

    public Date getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Date boardingTime) {
        this.boardingTime = boardingTime;
    }

    /**
     * Creates a new instance of BoardingManangedBean
     */
    public BoardingManangedBean() {
    }

}
