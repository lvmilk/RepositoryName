/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Member;
import SessionBean.ADS.MemberSessionBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "etMemberGuestMB")
@ViewScoped
public class EditMemberGuestManagedBean implements Serializable{
    
    @EJB
    private MemberSessionBeanLocal mbsbl;
    
    private List<Member> memberList;
    
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
    
    
    
    public void SelectEditMember(Member member) throws IOException {
        setMemberId(member.getMemberID());
        this.title=member.getTitle();
        this.firstName=member.getFirstName();
        this.lastName=member.getLastName();
        this.address=member.getAddress();
        this.email=member.getEmail();
        this.contactNo=member.getContact();
        this.dob=member.getDob();
        this.miles=member.getMiles();
        this.passport=member.getPassport();
        this.memberStatus=member.isMemberStatus();
        FacesContext.getCurrentInstance().getExternalContext().redirect("./adsEditMGpage.xhtml");
        
        
    }

    public List<Member> getMemberList() {
        memberList = mbsbl.getAllMember();
        System.out.println("Member List size is " + memberList.size());
        for (Integer i = 0; i < memberList.size(); i++) {
            System.out.println(memberList.get(i).getMemberID());
        }

        return memberList;
    }
    

    /**
     * @param memberList the memberList to set
     */
    public void setMemberList(List<Member> memberList) {
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
    
}
