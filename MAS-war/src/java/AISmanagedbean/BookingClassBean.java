/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.AIS.BookingClass;
import java.io.IOException;
import java.util.*;

import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import SessionBean.AIS.BookingClassBeanLocal;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.SelectableDataModel;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "bookingClassBean")
@SessionScoped
public class BookingClassBean implements Serializable {

    @EJB
    private BookingClassBeanLocal bcb;

    private UIComponent uIComponent;

    private String cabin;
    private String annotation;  //annotation of booking class like K,N etc
    private String annotation2;

    private Double price_percentage;
    private Double refund_percentage;
    private Double change_route_percentage;
    private Double change_date_percentage;
    private Double change_passenger_percentage;
    private Double open_jaw_percentage;
    private Double earn_mile_percentage;

    private Integer min_stay;
    private Integer max_stay;
    private Integer reserve_advance; // min no. of days reservation must be made in advance to flight
    private Integer ticket_advance; //min no. of dats ticketing must be done in advance to flight

    private Boolean can_standby;  // indicate whether customer can standby check-in
    private Boolean dds_available; // whether tickets from this class is available in DDS
    private Boolean gds_available; // whether tickets from this class is available in GDS

    private List<BookingClass> classList;
    private ArrayList<BookingClass> selectedClass;
    
    private BookingClass selectedBookClass;

    public List<BookingClass> getClassList() {
        classList = bcb.getAllBookingClasses();
        System.out.println("Class List size is " + classList.size());
        return classList;
    }

    public void setClassList(List<BookingClass> classList) {
        this.classList = classList;
    }

    public BookingClassBean() {
        selectedBookClass=null;
        selectedClass = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        classList = bcb.getAllBookingClasses();

    }

    public void intializeValues() {
        annotation = null;
        cabin = null;
        price_percentage = null;
        refund_percentage = null;
        change_route_percentage = null;
        change_date_percentage = null;
        change_passenger_percentage = null;
        open_jaw_percentage = null;
        earn_mile_percentage = null;
        min_stay = null;
        max_stay = null;
        ticket_advance = null;
        reserve_advance = null;
        can_standby = null;
        dds_available = null;
        gds_available = null;
        classList = null;
        selectedClass = null;
        selectedBookClass=null;

    }

    public void goBack() throws IOException {
//        classList = bcb.getAllBookingClasses();
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookingClass.xhtml");

    }

    public void checkCabin() throws IOException {
        if (cabin != null && !(cabin.isEmpty())) {
            System.out.println(" Cabin selected is " + cabin);
            FacesContext.getCurrentInstance().getExternalContext().redirect("./bookingClassAttribute1.xhtml");
        } else {
            System.out.println("No cabin is chosen");
        }
    }

    public void checkFirst() throws IOException {

        if (!bcb.checkDuplicate(annotation)) {
//            FacesContext.getCurrentInstance().getExternalContext().redirect("./bookingClassAttribute2.xhtml");
            if (min_stay <= max_stay) {
                System.out.println("max greater than min");

                if (reserve_advance >= ticket_advance) {

                    System.out.println("reserve earlier than ticketing");

                    bcb.addBookingClass(annotation, cabin, price_percentage, refund_percentage, change_route_percentage, change_date_percentage, change_passenger_percentage, open_jaw_percentage,
                            earn_mile_percentage, min_stay, max_stay, ticket_advance, reserve_advance, can_standby, dds_available, gds_available);

                    FacesMessage msg = new FacesMessage("BookingClass added successfully");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annotation already been used: ", ""));
        }
    }

    public void checkSecond(ActionEvent event) throws IOException {
        System.err.println("clidntID: " + uIComponent.getClientId());
        System.out.println("min stay is " + min_stay);
        System.out.println("max stay is " + max_stay);
        System.out.println("reserve day is " + reserve_advance);
        System.out.println("ticket advance is " + ticket_advance);

        if (min_stay <= max_stay) {
            System.out.println("max greater than min");

            if (reserve_advance >= ticket_advance) {

                System.out.println("reserve earlier than ticketing");

                bcb.addBookingClass(annotation, cabin, price_percentage, refund_percentage, change_route_percentage, change_date_percentage, change_passenger_percentage, open_jaw_percentage,
                        earn_mile_percentage, min_stay, max_stay, ticket_advance, reserve_advance, can_standby, dds_available, gds_available);
                this.intializeValues();

                FacesMessage msg = new FacesMessage("BookingClass added successfully!");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
        }

    }

    public void editFirst() throws IOException {
        
         selectedBookClass=(BookingClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClass");
        if (annotation.equals(annotation2) || !bcb.checkDuplicate(annotation)) {

            if (min_stay <= max_stay) {
                System.out.println("max greater than min");

                if (reserve_advance >= ticket_advance) {

                    System.out.println("reserve earlier than ticketing");

                   selectedBookClass= bcb.editBookingClass(annotation, annotation2, cabin, price_percentage, refund_percentage, change_route_percentage, change_date_percentage, change_passenger_percentage, open_jaw_percentage,
                            earn_mile_percentage, min_stay, max_stay, ticket_advance, reserve_advance, can_standby, dds_available, gds_available);

                    System.out.println("managebean: bookingclassbean: editFirst(): new price percentage is "+selectedBookClass.getPrice_percentage());
                    bcb.updateBookClassInstance(selectedBookClass);
                    
                     classList = bcb.getAllBookingClasses();
               
//               
                    FacesMessage msg = new FacesMessage("BookingClass edited successfully!");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                    this.intializeValues();

                } else {
                    FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
            }

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annotation already been used: ", ""));
        }

    }

    public void editSecond(ActionEvent event) throws IOException {
        System.err.println("clidntID: " + uIComponent.getClientId());
        System.out.println("min stay is " + min_stay);
        System.out.println("max stay is " + max_stay);
        System.out.println("reserve day is " + reserve_advance);
        System.out.println("ticket advance is " + ticket_advance);

        if (min_stay <= max_stay) {
            System.out.println("max greater than min");

            if (reserve_advance >= ticket_advance) {

                System.out.println("reserve earlier than ticketing");

                bcb.editBookingClass(annotation, annotation2, cabin, price_percentage, refund_percentage, change_route_percentage, change_date_percentage, change_passenger_percentage, open_jaw_percentage,
                        earn_mile_percentage, min_stay, max_stay, ticket_advance, reserve_advance, can_standby, dds_available, gds_available);

                BookingClass BookClass = (BookingClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("BookClass");
                for (int i = 0; i < classList.size(); i++) {
                    if (BookClass.getId().equals(classList.get(i).getId())) {
                        setBookingClassValue(classList.get(i));
                    }
                    break;
                }

                FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookClassSuccess.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
        }

    }

    public void confirmDelete() throws IOException {
        boolean check = bcb.checkGotInstance(selectedClass);
        if (!check) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./ConfirmDeleteBookClass.xhtml");
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Delete request denied: Selected bookingClass has existed booking class instances associated with flights!","");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void deleteBookClass() throws IOException {
        boolean check = bcb.deleteBookingClass(selectedClass);
        if (check) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./DeleteBookClassSuccess.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "No Booking Class selected: ", ""));
        }

    }

    public void check(SelectEvent event) {
        System.out.println("in check");
    }

    public String getCabin() {
        return cabin;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAnnotation2() {
        return annotation2;
    }

    public void setAnnotation2(String annotation2) {
        this.annotation2 = annotation2;
    }

    public Double getPrice_percentage() {
        return price_percentage;
    }

    public void setPrice_percentage(Double price_percentage) {
        this.price_percentage = price_percentage;
    }

    public Double getRefund_percentage() {
        return refund_percentage;
    }

    public void setRefund_percentage(Double refund_percentage) {
        this.refund_percentage = refund_percentage;
    }

    public Double getChange_route_percentage() {
        return change_route_percentage;
    }

    public void setChange_route_percentage(Double change_route_percentage) {
        this.change_route_percentage = change_route_percentage;
    }

    public Double getChange_date_percentage() {
        return change_date_percentage;
    }

    public void setChange_date_percentage(Double change_date_percentage) {
        this.change_date_percentage = change_date_percentage;
    }

    public Double getChange_passenger_percentage() {
        return change_passenger_percentage;
    }

    public void setChange_passenger_percentage(Double change_passenger_percentage) {
        this.change_passenger_percentage = change_passenger_percentage;
    }

    public Double getOpen_jaw_percentage() {
        return open_jaw_percentage;
    }

    public void setOpen_jaw_percentage(Double open_jaw_percentage) {
        this.open_jaw_percentage = open_jaw_percentage;
    }

    public Double getEarn_mile_percentage() {
        return earn_mile_percentage;
    }

    public void setEarn_mile_percentage(Double earn_mile_percentage) {
        this.earn_mile_percentage = earn_mile_percentage;
    }

    public Boolean getCan_standby() {
        return can_standby;
    }

    public void setCan_standby(Boolean can_standby) {
        this.can_standby = can_standby;
    }

    public Boolean getDds_available() {
        return dds_available;
    }

    public void setDds_available(Boolean dds_available) {
        this.dds_available = dds_available;
    }

    public Boolean getGds_available() {
        return gds_available;
    }

    public void setGds_available(Boolean gds_available) {
        this.gds_available = gds_available;
    }

    public Integer getMin_stay() {
        return min_stay;
    }

    public void setMin_stay(Integer min_stay) {
        this.min_stay = min_stay;
    }

    public Integer getMax_stay() {
        return max_stay;
    }

    public void setMax_stay(Integer max_stay) {
        this.max_stay = max_stay;
    }

    public Integer getReserve_advance() {
        return reserve_advance;
    }

    public void setReserve_advance(Integer reserve_advance) {
        this.reserve_advance = reserve_advance;
    }

    public Integer getTicket_advance() {
        return ticket_advance;
    }

    public void setTicket_advance(Integer ticket_advance) {
        this.ticket_advance = ticket_advance;
    }

    public ArrayList<BookingClass> getSelectedClass() {
        return selectedClass;
    }

    public void setSelectedClass(ArrayList<BookingClass> selectedClass) {
        this.selectedClass = selectedClass;
    }

    public BookingClass setBookingClassValue(BookingClass BookClass) {
        setAnnotation(BookClass.getAnnotation());
        setAnnotation2(annotation);
        setCabin(BookClass.getCabinName());
        setCan_standby(BookClass.getCan_standby());
        setChange_date_percentage(BookClass.getChange_date_percentage());
        setChange_passenger_percentage(BookClass.getChange_passenger_percentage());
        setChange_route_percentage(BookClass.getChange_route_percentage());
        setDds_available(BookClass.getDds_available());
        setEarn_mile_percentage(BookClass.getEarn_mile_percentage());
        setGds_available(BookClass.getGds_available());
        setMax_stay(BookClass.getMax_stay());
        setMin_stay(BookClass.getMin_stay());
        setOpen_jaw_percentage(BookClass.getOpen_jaw_percentage());
        setPrice_percentage(BookClass.getPrice_percentage());
        setRefund_percentage(BookClass.getRefund_percentage());
        setReserve_advance(BookClass.getReserve_advance());
        setTicket_advance(BookClass.getTicket_advance());

        System.out.println("New annotation is: " + annotation);

        return BookClass;

    }

    public void EditBookClassInfo(BookingClass BookClass) throws IOException {

        this.setBookingClassValue(BookClass);

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("BookClass", BookClass);
        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookingClassInfo1.xhtml");

        
    }

    public BookingClass getSelectedBookClass() {
        return selectedBookClass;
    }

    public void setSelectedBookClass(BookingClass selectedBookClass) {
        this.selectedBookClass = selectedBookClass;
    }
    
    
    

    /**
     * @return the uIComponent
     */
    public UIComponent getuIComponent() {
        return uIComponent;
    }

    /**
     * @param uIComponent the uIComponent to set
     */
    public void setuIComponent(UIComponent uIComponent) {
        this.uIComponent = uIComponent;
    }

}
