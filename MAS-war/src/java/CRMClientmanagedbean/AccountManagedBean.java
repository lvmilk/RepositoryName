/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CRMClientmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Reservation;
import SessionBean.CRMClient.MemberAccountBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "CRMAMB")
@ViewScoped
public class AccountManagedBean implements Serializable {

    /**
     * Creates a new instance of AccountManagedBean
     */
    @EJB
    MemberAccountBeanLocal mab;

    private String password;
    private Long memberId;
    private Boolean checkMember;
    private Booker booker;
    private List<Reservation> rsvList;

    private String firstName;
    private String lastName;
    private String title;
    private String address;
    private String contact;
    private String passport;
    private String dob;
    private Date dobDateType;
    private boolean sub=true;

    public AccountManagedBean() {
    }

    @PostConstruct
    public void init() {
        if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsvList") != null && FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("memberId") != null) {
            booker = (Booker) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("booker");
            rsvList = (List<Reservation>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("rsvList");
            memberId = (Long) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("memberId");
        }
        title = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("title");
        firstName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("firstName");
        lastName = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("lastName");
        address = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("address");
        contact = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("contact");
        passport = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("passport");
        dob = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dob");
        dobDateType = (Date) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("dobDateType");
//        sub = (boolean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("sub");
    }

    public void goToLogin() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./crmLogin.xhtml");

    }

    public void getMember() {
        try {
            String memberString = memberId.toString();
            checkMember = mab.validateMember(memberString, password);
            if (checkMember) {
                booker = mab.getThisMember(memberId);
                rsvList = mab.getAllReservation(memberId);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvList", rsvList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("memberId", memberId);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("title", booker.getTitle());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("address", booker.getAddress());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contact", booker.getContact());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passport", booker.getPassport());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dob", booker.getDob());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sub", booker.isSubscribe());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", booker.getLastName());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", booker.getFirstName());
                FacesContext.getCurrentInstance().getExternalContext().redirect("./displayAccount.xhtml");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Input", "Please check your Member ID and password."));
            }
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public void editAccount(Booker booker) throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("rsvList", rsvList);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("title", booker.getTitle());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("address", booker.getAddress());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("contact", booker.getContact());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("passport", booker.getPassport());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("dob", booker.getDob());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("sub", booker.isSubscribe());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("lastName", booker.getLastName());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("firstName", booker.getFirstName());

        FacesContext.getCurrentInstance().getExternalContext().redirect("./editAccount.xhtml");
    }

    public void editAccountInfor(Booker booker) throws IOException {
        try {
            DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
            dob = df1.format(dobDateType);
            mab.editBooker(memberId, title, password, passport, address, contact, dob, sub);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Your member account has been updated successfully!", "Member ID is: " + memberId));
        } catch (Exception ex) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "An error has occurred : " + ex.getMessage(), ""));
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Boolean getCheckMember() {
        return checkMember;
    }

    public void setCheckMember(Boolean checkMember) {
        this.checkMember = checkMember;
    }

    public Booker getBooker() {
        return booker;
    }

    public void setBooker(Booker booker) {
        this.booker = booker;
    }

    public List<Reservation> getRsvList() {
        return rsvList;
    }

    public void setRsvList(List<Reservation> rsvList) {
        this.rsvList = rsvList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public Date getDobDateType() {
        return dobDateType;
    }

    public void setDobDateType(Date dobDateType) {
        this.dobDateType = dobDateType;
    }

    public boolean isSub() {
        return sub;
    }

    public void setSub(boolean sub) {
        this.sub = sub;
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

}
