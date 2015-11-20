/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

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
@Named(value = "ocmb")
@ViewScoped
public class OnlineCheckinManagedBean implements Serializable {
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
    private Boolean loungeEligibility;
    private Date boardingTime;
    private List<Seat> allSeats = new ArrayList<Seat>();
    private Date minDate = new Date();
    private Date maxDate=new Date();

    @PostConstruct
    public void init() {

        tickets = (List<Ticket>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("tickets");
        ticket = (Ticket) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ticket");
        dateString = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dateString");
        firstName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName");
        lastName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastName");
        passportNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("passportNo");
        seatSelected = (Seat) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("seat");
        flightNo = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("flightNo");
        luggageCount = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("luggageCount");
        loungeEligibility = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LoungeEligibility");
        date = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("date");
        unOccupiedSeats = (List<Seat>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("unOccupiedSeats");
        boardingTime = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("boardingTime");
        allSeats = (List<Seat>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("allSeats");
        minDate = new Date();
        Calendar c1 = Calendar.getInstance();
        c1.setTime(minDate);
        c1.add(Calendar.DATE, 1);
        maxDate = c1.getTime();
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

//    //choose tickets within 24 hours
//    public void getUnusedTicket() throws Exception {
////        try {
//        List<Ticket> newList = new ArrayList<Ticket>();
//        if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
//            System.out.println("cmb:getUnusedTicket(): check1");
//            FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
//            System.out.println("cmb:getUnusedTicket(): check2 get requested flight instance: " + requestedFi.getDate());
//            System.out.println("cmb:no of tickets found: " + dcb.getAllTicket(passportNo, firstName, lastName).size());
//            for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {
//
//                Date dateTemp = ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType();
//                Calendar c1 = Calendar.getInstance();
//                c1.setTime(dateTemp);
//                c1.add(Calendar.DATE, -1);
//                dateTemp = c1.getTime();
//                System.out.println("cmb: dateTemp " + dateTemp);
//                System.out.println("cmb: date " + date);
//
//                if (date.after(dateTemp) && ticket.getTicketStatus().equals("Unused")) {
//                    System.out.println("cmb: One ticket to be added!");
//                    newList.add(ticket);
//                    System.out.println("cmb: One ticket added!");
//                }
//            }
//            this.setTickets(newList);
//            System.out.println("cmb:Final no of tickets found: " + tickets.size());
//
//        }
//
//    }
        public void getUnusedTicket2() throws Exception {
//        try {
        List<Ticket> newList = new ArrayList<Ticket>();
        if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
            System.out.println("cmb:getUnusedTicket(): check1");
            FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
            System.out.println("cmb:getUnusedTicket(): check2 get requested flight instance: " + requestedFi.getDate());
            System.out.println("cmb:no of tickets found: " + dcb.getAllTicket(passportNo, firstName, lastName).size());
            for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {

                Date dateTemp = ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(dateTemp);
                c1.add(Calendar.DATE, -1);
                dateTemp = c1.getTime();
                System.out.println("cmb: dateTemp " + dateTemp);
                System.out.println("cmb: date " + date);

                if (date.after(dateTemp) &&ticket.getBkInstance().getFlightCabin().getFlightInstance().getFlightFrequency().getFlightNo().equals(flightNo)&&ticket.getTicketStatus().equals("Unused")) {
                    System.out.println("cmb: One ticket to be added!");
                    newList.add(ticket);
                    System.out.println("cmb: One ticket added!");
                }
            }
            this.setTickets(newList);
            System.out.println("cmb:Final no of tickets found: " + tickets.size());

        }

    }

    //choose tickets within 24 hours
    public void getOnlineCheckedinTicket() throws Exception {
        List<Ticket> newList = new ArrayList<Ticket>();
        if (flightNo != null && !flightNo.equals("") && !dateString.equals("")) {
            System.out.println("cmb:getUnusedTicket(): check1");
            FlightInstance requestedFi = dcb.getRequestFlight(flightNo, dateString);
            System.out.println("cmb:getUnusedTicket(): check2 get requested flight instance: " + requestedFi.getDate());
            System.out.println("cmb:no of tickets found: " + dcb.getAllTicket(passportNo, firstName, lastName).size());
            for (Ticket ticket : dcb.getAllTicket(passportNo, firstName, lastName)) {

                Date dateTemp = ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType();
                Calendar c1 = Calendar.getInstance();
                c1.setTime(dateTemp);
                c1.add(Calendar.DATE, -1);
                dateTemp = c1.getTime();
                System.out.println("cmb: dateTemp " + dateTemp);
                System.out.println("cmb: date " + date);

                if (date.after(dateTemp) && ticket.getTicketStatus().equals("OnlineCheckedIn")) {
                    System.out.println("cmb: One ticket to be added!");
                    newList.add(ticket);
                    System.out.println("cmb: One ticket added!");
                }
            }
            this.setTickets(newList);
            System.out.println("cmb:Final no of tickets found: " + tickets.size());

        }

    }

    public void getUnoccupiedSeat(Ticket ticket) {
        try {
            this.setUnOccupiedSeats(dcb.getAllUnOccupiedSeats(ticket));

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

//    public void onGetTicketChange() throws Exception {
//        try {
//            this.getUnusedTicket();
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn2.xhtml");
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//
//    }
      public void onGetTicketChange2() throws Exception {
        try {
            this.getUnusedTicket2();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckin2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

//    public void onGetOnlineCheckedinTicketChange() throws Exception {
//        try {
//            this.getOnlineCheckedinTicket();
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./luggageCheckIn2.xhtml");
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//
//    }

    public void onCheckinChange(ActionEvent event) {
        try {
            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            if (!ticket.getTicketStatus().equals("Unused")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "Cannot check in this ticket", ""));
            } else {
                this.getUnoccupiedSeat(ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", unOccupiedSeats);
                FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckin3.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

//    public void onCheckinLuggageChange(ActionEvent event) {
//        try {
//            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
//            seatSelected = dcb.getSeat(ticket);
//            if (seatSelected.getLuggageCount() != 0) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "Luggage already checked in", ""));
//            } else {
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
//
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", seatSelected);
//
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
//
//                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", unOccupiedSeats);
//                FacesContext.getCurrentInstance().getExternalContext().redirect("./luggageCheckIn3.xhtml");
//            }
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//    }

    public void onStandbyChange(ActionEvent event) {
        try {

            ticket = (Ticket) event.getComponent().getAttributes().get("tkt");
            dcb.checkLoungeEligibility(ticket);
//            this.calculateBoardingTime(ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType());
            this.loungeEligibility = dcb.checkLoungeEligibility(ticket);
            if (!ticket.getTicketStatus().equals("Unused")) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + "Cannot check in this ticket", ""));

            } else {
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", unOccupiedSeats);

                FacesContext.getCurrentInstance().getExternalContext().redirect("./standBy.xhtml");
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void onSelectSeat(ActionEvent event) {
        try {
            this.seatSelected = (Seat) event.getComponent().getAttributes().get("seat");
            this.previewBoardingPass();
            
//            
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
//
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", seatSelected);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", unOccupiedSeats);
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineChecin4.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));

        }
    }

    public void calculateBoardingTime(Date date) {
        Calendar c1 = Calendar.getInstance();
        c1.setTime(date);
        System.out.println("calculateboardingtime() " + c1.getTime());
        c1.add(Calendar.MINUTE, -30);
        System.out.println("calculateboardingtime() " + c1.getTime());

        this.setBoardingTime(c1.getTime());

    }

    public void previewBoardingPass() {
        try {

            System.out.println("get time " + ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType());

            this.calculateBoardingTime(ticket.getBkInstance().getFlightCabin().getFlightInstance().getStandardDepTimeDateType());
            System.out.println("cmb:previewBoardingPass:" + boardingTime);
            this.loungeEligibility = dcb.checkLoungeEligibility(ticket);
            System.out.println("cmb:Eligibility:" + loungeEligibility);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", seatSelected);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", luggageCount);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", loungeEligibility);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", boardingTime);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", unOccupiedSeats);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckin4.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void goLast() {
        try {
            dcb.changeOnlineCheckinStatus(ticket);
            dcb.selectSeat(seatSelected, ticket);
            dcb.accumulateMiles(ticket);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());

            FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckinSuccess.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

//    public void goStandbyLast() {
//        try {
//            dcb.changeStandbyStatus(ticket);
//            dcb.accumulateMiles(ticket);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./standBySuccess.xhtml");
//
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//
//    }

//    public void goLuggageCheckinLast() {
//        try {
//            dcb.updateLuggageCount(seatSelected, luggageCount);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./luggageCheckInSuccess.xhtml");
//
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//
//    }

//    public void goBack() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn.xhtml");
//
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//    }
      public void goBackOnlineCheckin() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", new Ticket());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());
            FacesContext.getCurrentInstance().getExternalContext().redirect("./onlineCheckin1.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }


//    public void goBacktocheckin2() {
//        try {
//            this.getUnusedTicket();
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("date", date);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", firstName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", lastName);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passportNo", passportNo);
////            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("ticket", ticket);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("seat", new Seat());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", "");
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("luggageCount", 0);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("loungeEligibility", false);
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("boardingTime", new Date());
//            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("unOccupiedSeats", new ArrayList<Seat>());
//
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkIn2.xhtml");
//
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//    }

//    public void luggageCheckIngoBack() {
//        try {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./luggageCheckIn.xhtml");
//
//        } catch (Exception ex) {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
//        }
//    }

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
        System.err.println("Get Ticket List!" + tickets.size());
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

    public Boolean getLoungeEligibility() {
        return loungeEligibility;
    }

    public void setLoungeEligibility(Boolean loungeEligibility) {
        this.loungeEligibility = loungeEligibility;
    }

    public Date getBoardingTime() {
        return boardingTime;
    }

    public void setBoardingTime(Date boardingTime) {
        this.boardingTime = boardingTime;
    }

    public List<Seat> getAllSeats() {
        return allSeats;
    }

    public void setAllSeats(List<Seat> allSeats) {
        this.allSeats = allSeats;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    public void getSeatList() throws Exception {
        List<Seat> newList = new ArrayList<Seat>();
        newList = dcb.getAllSeats(flightNo, dateString);
        if (!newList.isEmpty()) {
            this.setAllSeats(newList);
        }

    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public void onGetSeatList() {
        try {
            this.getSeatList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allSeats", allSeats);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./checkSeatAvailability2.xhtml");
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
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("allSeats", new ArrayList<Seat>());

            FacesContext.getCurrentInstance().getExternalContext().redirect("./DCSworkspace.xhtml");

        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void getUncheckedinList() throws Exception {
        List<Ticket> newList = new ArrayList<Ticket>();
        newList = dcb.getAllUnchekedinTicket(flightNo, dateString);
        if (!newList.isEmpty()) {
            this.setTickets(newList);
        }

    }

    public void onGetUncheckedinListChange() {
        try {
            this.getUncheckedinList();
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("flightNo", flightNo);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dateString", dateString);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("tickets", tickets);

            FacesContext.getCurrentInstance().getExternalContext().redirect("./viewUncheckedIn2.xhtml");
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }

    }

    /**
     * Creates a new instance of CheckInManagedBean
     */
    /**
     * Creates a new instance of OnlineCheckinManagedBean
     */
    public OnlineCheckinManagedBean() {
    }
    
}
