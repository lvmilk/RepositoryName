/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Passenger;
import Entity.CommonInfa.MsgSender;
import SessionBean.ADS.PassengerSessionBeanLocal;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author LI HAO
 */
@Named(value = "mgMB")
@ViewScoped
public class MemberGuestManagedBean implements Serializable {
    @EJB
    private PassengerSessionBeanLocal psgSBlocal;

    private String title = "Mr";

    private String firstName;
    private String lastName;
    private String ffpProgram;
    private String ffpNo;

    private String email;
    private String contactNo;
    
    private ArrayList<Passenger> passengerList=new ArrayList<>();
    private Passenger person=new Passenger();
    
    @PostConstruct
    public void init() {
        try {
            passengerList.add(person);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void makeReserve()
    {
        System.out.print("&&&&&&&&&&This is person: "+person.getFirstName());
        passengerList.set(0,person);
        
        psgSBlocal.makeReservation(passengerList,email,contactNo);
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
     * @return the ffpProgram
     */
    public String getFfpProgram() {
        return ffpProgram;
    }

    /**
     * @param ffpProgram the ffpProgram to set
     */
    public void setFfpProgram(String ffpProgram) {
        this.ffpProgram = ffpProgram;
    }

    /**
     * @return the ffpNo
     */
    public String getFfpNo() {
        return ffpNo;
    }

    /**
     * @param ffpNo the ffpNo to set
     */
    public void setFfpNo(String ffpNo) {
        this.ffpNo = ffpNo;
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

}
