/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Booker;
import SessionBean.ADS.BookerBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "etMemberGuestMB")
@SessionScoped
public class EditMemberGuestManagedBean implements Serializable {

    @EJB
    private BookerBeanLocal mbsbl;

    private List<Booker> memberList;

    private Long memberId;
    private String title;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String contactNo;
    private String dob;
    private Double miles;
    private String passport;
    private boolean memberStatus;

    private String emailEdited;


    public void SelectEditMember(Booker booker) throws IOException {
        setMemberId(booker.getId());
        this.title = booker.getTitle();
        this.firstName = booker.getFirstName();
        this.lastName = booker.getLastName();
        this.address = booker.getAddress();
        this.email = booker.getEmail();
        this.contactNo = booker.getContact();
        this.dob = booker.getDob();
        this.miles = booker.getMiles();
        this.passport = booker.getPassport();
        this.memberStatus = booker.isMemberStatus();
        this.setEmailEdited(email);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./adsEditMGpage.xhtml");

    }

    public void editMemberAccount() throws IOException {
        if (!mbsbl.checkEmailDuplicate(email, emailEdited)) {
            mbsbl.editBooker(memberId, title, firstName, lastName, address, email, contactNo, dob, miles, passport, memberStatus);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Account Edited Successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Email has already been used ", ""));
        }
    }

    public List<Booker> getMemberList() {
        memberList = mbsbl.getAllBooker();
        System.out.println("Member List size is " + memberList.size());
        for (Integer i = 0; i < memberList.size(); i++) {
            System.out.println(memberList.get(i).getId());
        }

        return memberList;
    }

    /**
     * @param memberList the memberList to set
     */
    public void setMemberList(List<Booker> memberList) {
        this.memberList = memberList;
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
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
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
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
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
     * @return the contactNo
     */
    public String getContactNo() {
        return contactNo;
    }

    /**
     * @param contactNo the contactNo to set
     */
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    /**
     * @return the dob
     */
    public String getDob() {
        return dob;
    }

    /**
     * @param dob the dob to set
     */
    public void setDob(String dob) {
        this.dob = dob;
    }

    /**
     * @return the miles
     */
    public Double getMiles() {
        return miles;
    }

    /**
     * @param miles the miles to set
     */
    public void setMiles(Double miles) {
        this.miles = miles;
    }

    /**
     * @return the passport
     */
    public String getPassport() {
        return passport;
    }

    /**
     * @param passport the passport to set
     */
    public void setPassport(String passport) {
        this.passport = passport;
    }

    /**
     * @return the memberStatus
     */
    public boolean isMemberStatus() {
        return memberStatus;
    }

    /**
     * @param memberStatus the memberStatus to set
     */
    public void setMemberStatus(boolean memberStatus) {
        this.memberStatus = memberStatus;
    }

    /**
     * @return the emailEdited
     */
    public String getEmailEdited() {
        return emailEdited;
    }

    /**
     * @param emailEdited the emailEdited to set
     */
    public void setEmailEdited(String emailEdited) {
        this.emailEdited = emailEdited;
    }

}
