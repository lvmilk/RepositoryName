/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Member;
import Entity.ADS.Passenger;
import Entity.APS.FlightInstance;
import SessionBean.ADS.MemberBeanLocal;
import SessionBean.ADS.PassengerBeanLocal;
import SessionBean.ADS.RsvConfirmationBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "tkMB")
@ViewScoped
public class TicketManagedBean implements Serializable {

    @EJB
    private PassengerBeanLocal psgSBlocal;
    @EJB
    private MemberBeanLocal msblocal;
    @EJB
    private RsvConfirmationBeanLocal rsvCflocal;

    private Long memberId;
    private String firstName;
    private String lastName;
    private String email;

    private Member member = new Member();
    private ArrayList<Passenger> passengerList = new ArrayList<>();
    private Passenger person = new Passenger();
    private Integer psgCount;

    private ArrayList<FlightInstance> departSelected = new ArrayList<>();
    private ArrayList<FlightInstance> returnSelected = new ArrayList<>();
    private Double totalPrice;

    @PostConstruct
    public void init() {
        try {

            departSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSelected");
            returnSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSelected");
            totalPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalPrice");

            memberId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("RsvMemberId");
            passengerList = (ArrayList<Passenger>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("PsgList");
            setPsgCount((Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson"));

            member = msblocal.retrieveMember(memberId);
            this.setFirstName(member.getFirstName());
            this.setLastName(member.getLastName());
            this.setEmail(member.getEmail());

            System.out.println("in the ticketManagedBean init passengerlist size is: " + passengerList.size());
            System.out.println("in the ticketManagedBean init first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void rsvConfirm() {
        System.out.println("in the rsvConfirmation passengerlist size is: " + passengerList.size());
        System.out.println("in the first rsvConfirmation passenge ID is: " + passengerList.get(0).getId());
        rsvCflocal.setupPsg_Ticket(departSelected, returnSelected, passengerList,memberId);
//        rsvCflocal.setupTicket_Reservation(firstName,lastName,email);
    }

    /**
     * @return the memberId
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId the memberId to set
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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
     * @return the member
     */
    public Member getMember() {
        return member;
    }

    /**
     * @param member the member to set
     */
    public void setMember(Member member) {
        this.member = member;
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

}
