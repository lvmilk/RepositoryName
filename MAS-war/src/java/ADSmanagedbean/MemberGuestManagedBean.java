/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ADSmanagedbean;

import Entity.ADS.Booker;
import Entity.ADS.Passenger;
import Entity.AIS.BookingClassInstance;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.MsgSender;
import SessionBean.ADS.BookerBeanLocal;
import SessionBean.ADS.PassengerBeanLocal;
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

/**
 *
 * @author LI HAO
 */
@Named(value = "mgMB")
@ViewScoped
public class MemberGuestManagedBean implements Serializable {

    @EJB
    private PassengerBeanLocal psgSBlocal;
    @EJB
    private BookerBeanLocal msblocal;

    private String title = "Mr";

    private String firstName;
    private String lastName;
    private String ffpProgram;
    private String ffpNo;

    private Long bookerId;
    private String address;
    private String email;
    private String existEmail;
    private String contactNo;

    private boolean visiMember = true;
    private boolean visiNonMember;

    private boolean selectedOption = true;

    private ArrayList<Passenger> passengerList = new ArrayList<>();
    private Passenger person = new Passenger();
    private Booker booker = new Booker();

    private Integer repeat;
    private String stfType;

    private ArrayList<FlightInstance> departSelected = new ArrayList<>();
    private ArrayList<FlightInstance> returnSelected = new ArrayList<>();
    private Double totalPrice;
    private ArrayList<BookingClassInstance> BookClassInstanceList = new ArrayList<>();

    @PostConstruct
    public void init() {
        try {
            BookClassInstanceList = (ArrayList<BookingClassInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClassInstanceList");
            departSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("departSelected");
            returnSelected = (ArrayList<FlightInstance>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("returnSelected");
            totalPrice = (Double) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("totalPrice");
            repeat = (Integer) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("countPerson");
            stfType = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("StaffType");

            for (int i = 0; i < repeat; i++) {
                passengerList.add(person);
                person = new Passenger();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void onSelectReturn() {
        if (isSelectedOption() == true) {
            visiMember = true;
            visiNonMember = false;
        } else if (isSelectedOption() == false) {
            visiNonMember = true;
            visiMember = false;
        }
    }

    public void makeReserve() throws IOException {
        System.out.print("&&&&&&&&&&This is person: " + person.getFirstName());
        System.out.print("&&&&&&&&&&This is email: " + existEmail);
        Long temp;

        if (visiMember == true) {
            booker = psgSBlocal.checkMemberExist(bookerId, existEmail);
            if (booker != null) {
//                passengerList = psgSBlocal.makeReservation(passengerList, existEmail, bookerId);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Information filled successfully."));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", passengerList);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("visiMember", visiMember);

                System.out.println("#########This is in makeReserver and the id of passenger is:" + passengerList.get(0).getId());
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", repeat);

                if (stfType.equals("agency")) {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsConfirmReservation.xhtml");
                } else {
                    FacesContext.getCurrentInstance().getExternalContext().redirect("./confirmReservation.xhtml");
                }

            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Member Account or email is not correct ", ""));
            }
        } else if (visiNonMember == true) {

            booker = psgSBlocal.createTempBooker(title, firstName, lastName, address, email, contactNo);

//                passengerList = psgSBlocal.makeRsvGuest(passengerList, title, firstName, lastName, address, email, contactNo);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Message", "Information filled successfully."));
//            temp = msblocal.retrieveBookerID(email);
//            if (temp.equals(0)) {
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Member ID does not found ", ""));

//            } else {
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("booker", booker);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("visiMember", visiMember);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("PsgList", passengerList);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("countPerson", repeat);

            if (stfType.equals("agency")) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./ddsConfirmReservation.xhtml");
            } else {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./confirmReservation.xhtml");
            }
        }
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
     * @return the memberId
     */
    /**
     * @return the visiMember
     */
    public boolean isVisiMember() {
        return visiMember;
    }

    /**
     * @param visiMember the visiMember to set
     */
    public void setVisiMember(boolean visiMember) {
        this.visiMember = visiMember;
    }

    /**
     * @return the visiNonMember
     */
    public boolean isVisiNonMember() {
        return visiNonMember;
    }

    /**
     * @param visiNonMember the visiNonMember to set
     */
    public void setVisiNonMember(boolean visiNonMember) {
        this.visiNonMember = visiNonMember;
    }

    /**
     * @return the selectedOption
     */
    public boolean isSelectedOption() {
        return selectedOption;
    }

    /**
     * @param selectedOption the selectedOption to set
     */
    public void setSelectedOption(boolean selectedOption) {
        this.selectedOption = selectedOption;
    }

    /**
     * @return the repeat
     */
    public Integer getRepeat() {
        return repeat;
    }

    /**
     * @param repeat the repeat to set
     */
    public void setRepeat(Integer repeat) {
        this.repeat = repeat;
    }

    /**
     * @return the existEmail
     */
    public String getExistEmail() {
        return existEmail;
    }

    /**
     * @param existEmail the existEmail to set
     */
    public void setExistEmail(String existEmail) {
        this.existEmail = existEmail;
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

    public Long getBookerId() {
        return bookerId;
    }

    public void setBookerId(Long bookerId) {
        this.bookerId = bookerId;
    }

    /**
     * @return the stfType
     */
    public String getStfType() {
        return stfType;
    }

    /**
     * @param stfType the stfType to set
     */
    public void setStfType(String stfType) {
        this.stfType = stfType;
    }

}
