/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.ADS.Reservation;
import Entity.ADS.Ticket;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import SessionBean.ADS.BookerBeanLocal;
import SessionBean.ADS.DDSBookingBeanLocal;
import SessionBean.ADS.PassengerBeanLocal;
import SessionBean.ADS.RsvConfirmationBeanLocal;
import SessionBean.CRM.CRMPassengerBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Lu Xi
 */
@Named(value = "confirmBook")
@ViewScoped
public class ConfirmBookFlightManagedBean implements Serializable {

    @EJB
    private CRMPassengerBeanLocal crmpb;

    private ArrayList<BookingClassInstance> BookClassInstanceList = new ArrayList<>();
    private Long bookerId;
    private String firstName;
    private String lastName;
    private String email;

    private Booker booker = new Booker();
    private ArrayList<Passenger> passengerList = new ArrayList<>();
    private Passenger person = new Passenger();
    private Integer psgCount;

    private ArrayList<FlightInstance> departSelected = new ArrayList<>();
    private ArrayList<FlightInstance> returnSelected = new ArrayList<>();
    private Double totalPrice;

    private String origin;
    private String dest;
    private Boolean returnTrip;
    private Boolean visiMember;

    private ArrayList<Passenger> psgList;
//    private String stfType;
    private String username;
    private String bkSystem;
    private String companyName;

    private Double currentMiles;
    private Long rsvId;
    private Reservation rsv = new Reservation();
    private List<Ticket> ticketList = new ArrayList<>();
    private boolean select = true;
    private boolean selectCard = true;
    private boolean selectFTP;
    private String cardNo;
    private String code;

    @PostConstruct
    public void init() {
        try {

            booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");

            visiMember = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("visiMember");
//            stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");
            username = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("UserId");

            origin = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("origin");
            dest = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dest");
            returnTrip = (Boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnTrip");

            departSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSelected");
            returnSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSelected");
            totalPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalPrice");

            passengerList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
            setPsgCount((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson"));

            BookClassInstanceList = (ArrayList<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstanceList");
            bkSystem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("bkSystem");
            companyName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("companyName");

            currentMiles = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentMiles");
            rsvId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsvId");
            rsv = (Reservation) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsv");
            psgCount = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson");
//            selectCard= (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectCard");
//            selectFTP= (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectFTP");
            System.out.println("in the ticketManagedBean init passengerlist size is: " + passengerList.size());
            System.out.println("in the ticketManagedBean init first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void rsvConfirm() throws IOException {
        System.out.println("in the rsvConfirmation passengerlist size is: " + passengerList.size());
        System.out.println("in the first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());
        System.out.println("in rsvConfirm: BookClassIntanceList" + BookClassInstanceList);
        System.out.println("in rsvConfirm:psgCount" + psgCount);
//        if (stfType.equals("agency")) {
//            this.bkSystem = "DDS";
//        } else {
//            this.bkSystem = "ARS";
//        }

        this.bkSystem = "ARS";
        rsvId = crmpb.makeReservation(booker, passengerList, departSelected, returnSelected, BookClassInstanceList, psgCount, origin, dest, returnTrip, bkSystem, 0.0, "book", companyName);

        rsv = crmpb.getRsv(rsvId);
        int size = rsv.getTickets().size();
        ticketList = rsv.getTickets();
        System.out.println("ConfirmBookFlightManagedBean: rsvConfirm:ticketList: " + ticketList);
        currentMiles = 0.0;
        for (int i = 0; i < ticketList.size(); i++) {
            double thisMile = (ticketList.get(i).getBkInstance().getBookingClass().getEarn_mile_percentage() * ticketList.get(i).getBkInstance().getFlightCabin().getFlightInstance().getFlightFrequency().getRoute().getDistance()) * 10 + 10000;
            System.out.println("ConfirmBookFlightManagedBean: rsvConfirm: thisMile: " + thisMile);
            currentMiles = currentMiles + thisMile;
            System.out.println("ConfirmBookFlightManagedBean: rsvConfirm: booking class: " + ticketList.get(i).getBkInstance().getBookingClass());
            System.out.println("ConfirmBookFlightManagedBean: rsvConfirm: mile percenntage: " + ticketList.get(i).getBkInstance().getBookingClass().getEarn_mile_percentage());
            System.out.println("ConfirmBookFlightManagedBean: rsvConfirm: current miles: " + currentMiles);
            System.out.println("ConfirmBookFlightManagedBean: rsvConfirm: totalPrice: " + totalPrice);
        }
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsv", rsv);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvId", rsvId);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgCount", psgCount);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("psgCount", psgCount);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentMiles", currentMiles);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./BookFlight5.xhtml");
//        if (stfType.equals("agency")) {
//            ddsBkblocal.setAgency_Booker(username, booker);
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Book flight successfully."));
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsRsvSuccess.xhtml");
//
//        } else {
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Congratulations!", "Reserve flight successfully."));
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./addRsvSuccess.xhtml");
//        }
    }

    public void onSelectReturn() {
        if (isSelect() == true) {
            selectCard = true;
            selectFTP = false;
            System.out.println("ConfirmBookFlightManagedBean: onSelectReturn: SELECT CARD: selectCard: " + selectCard + " selectFTP: " + selectFTP);
        } else if (isSelect() == false) {
            selectFTP = true;
            selectCard = false;
            System.out.println("ConfirmBookFlightManagedBean: onSelectReturn: SELECT TFP: selectCard: " + selectCard + " selectFTP: " + selectFTP);
        }
    }

    public void makePayment() throws IOException, Exception {
        System.out.println("ConfirmBookFlightManagedBean: makePayment(): selectCard: " + selectCard + " selectFTP: " + selectFTP);
        if (selectCard == true) {
            System.out.println("ConfirmBookFlightManagedBean: makePayment: Card Payement!");
            if (cardNo.length() != 16 && code.length() != 3) {
                System.out.println("ConfirmBookFlightManagedBean: makePayment: cardNo should be 16 digits and security code shoud be 2 digits!");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "invalid input", "cardNo should be 16 digits and security code shoud be 2 digits!"));
            } else {
                crmpb.makeRsvPayment(rsv, psgCount, totalPrice, "book", cardNo, code);
                System.out.println("ConfirmBookFlightManagedBean: makePayment: Payment Successful!");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Congratulations!", "Reserve flight successfully."));
                FacesContext.getCurrentInstance().getExternalContext().redirect("./reserveSuccess.xhtml");
            }
        } else if (selectFTP == true) {
            if (booker.getId() != null) {
                crmpb.deductMiles(booker.getId(), rsv);
                System.out.println("ConfirmBookFlightManagedBean: makePayment: TFP Miles Payement Successful!");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Congratulations!", "Reserve flight successfully."));
                FacesContext.getCurrentInstance().getExternalContext().redirect("./reserveSuccess.xhtml");
            } else {
                System.out.println("ConfirmBookFlightManagedBean: makePayment: TFP Miles Payement --->>> No Booker");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No such a booker ", "Please register first!"));
            }
        }

    }

    /**
     * @return the passengerList
     */
    public ArrayList<Passenger> getPassengerList() {
        return passengerList;
    }

    /**
     * @param passengerList the passengerList to set
     */
    public void setPassengerList(ArrayList<Passenger> passengerList) {
        this.passengerList = passengerList;
    }

    /**
     * @return the person
     */
    public Passenger getPerson() {
        return person;
    }

    /**
     * @param person the person to set
     */
    public void setPerson(Passenger person) {
        this.person = person;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the booker
     */
    public Booker getBooker() {
        return booker;
    }

    /**
     * @param member the booker to set
     */
    public void setBooker(Booker member) {
        this.booker = member;
    }

    /**
     * @return the psgCount
     */
    public Integer getPsgCount() {
        return psgCount;
    }

    /**
     * @param psgCount the psgCount to set
     */
    public void setPsgCount(Integer psgCount) {
        this.psgCount = psgCount;
    }

    public ArrayList<FlightInstance> getDepartSelected() {
        return departSelected;
    }

    public void setDepartSelected(ArrayList<FlightInstance> departSelected) {
        this.departSelected = departSelected;
    }

    public ArrayList<FlightInstance> getReturnSelected() {
        return returnSelected;
    }

    public void setReturnSelected(ArrayList<FlightInstance> returnSelected) {
        this.returnSelected = returnSelected;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * // * @return the stfType //
     */
//    public String getStfType() {
//        return stfType;
//    }
//
//    /**
//     * @param stfType the stfType to set
//     */
//    public void setStfType(String stfType) {
//        this.stfType = stfType;
//    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the bkSystem
     */
    public String getBkSystem() {
        return bkSystem;
    }

    /**
     * @param bkSystem the bkSystem to set
     */
    public void setBkSystem(String bkSystem) {
        this.bkSystem = bkSystem;
    }

    /**
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Double getCurrentMiles() {
        return currentMiles;
    }

    public void setCurrentMiles(Double currentMiles) {
        this.currentMiles = currentMiles;
    }

    public Long getRsvId() {
        return rsvId;
    }

    public void setRsvId(Long rsvId) {
        this.rsvId = rsvId;
    }

    public Reservation getRsv() {
        return rsv;
    }

    public void setRsv(Reservation rsv) {
        this.rsv = rsv;
    }

    public List<Ticket> getTicketList() {
        return ticketList;
    }

    public void setTicketList(List<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    public boolean isSelectCard() {
        return selectCard;
    }

    public void setSelectCard(boolean selectCard) {
        this.selectCard = selectCard;
    }

    public boolean isSelectFTP() {
        return selectFTP;
    }

    public void setSelectFTP(boolean selectFTP) {
        this.selectFTP = selectFTP;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ArrayList<BookingClassInstance> getBookClassInstanceList() {
        return BookClassInstanceList;
    }

    public void setBookClassInstanceList(ArrayList<BookingClassInstance> BookClassInstanceList) {
        this.BookClassInstanceList = BookClassInstanceList;
    }

    public Long getBookerId() {
        return bookerId;
    }

    public void setBookerId(Long bookerId) {
        this.bookerId = bookerId;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public Boolean getReturnTrip() {
        return returnTrip;
    }

    public void setReturnTrip(Boolean returnTrip) {
        this.returnTrip = returnTrip;
    }

    public Boolean getVisiMember() {
        return visiMember;
    }

    public void setVisiMember(Boolean visiMember) {
        this.visiMember = visiMember;
    }

    public ArrayList<Passenger> getPsgList() {
        return psgList;
    }

    public void setPsgList(ArrayList<Passenger> psgList) {
        this.psgList = psgList;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

}
