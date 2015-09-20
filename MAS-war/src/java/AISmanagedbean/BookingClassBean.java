/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import java.io.IOException;
import java.util.*;

import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.ejb.EJB;
import Entity.aisEntity.*;
import SessionBean.AirlineInventory.BookingClassBeanLocal;
import java.io.Serializable;
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

    private double price_percentage;
    private double refund_percentage;
    private double change_route_percentage;
    private double change_date_percentage;
    private double change_passenger_percentage;
    private double open_jaw_percentage;
    private double earn_mile_percentage;

    private Integer min_stay;
    private Integer max_stay;
    private Integer reserve_advance; // min no. of days reservation must be made in advance to flight
    private Integer ticket_advance; //min no. of dats ticketing must be done in advance to flight

    private boolean can_standby;  // indicate whether customer can standby check-in
    private boolean dds_available; // whether tickets from this class is available in DDS
    private boolean gds_available; // whether tickets from this class is available in GDS

    private List<BookingClass> classList;
    private ArrayList<BookingClass> selectedClass;

    public List<BookingClass> getClassList() {
        classList = bcb.getAllBookingClasses();
        System.out.println("Class List size is " + classList.size());
        return classList;
    }

    public void setClassList(List<BookingClass> classList) {
        this.classList = classList;
    }

    public BookingClassBean() {

        selectedClass = new ArrayList<>();
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
//    System.out.println("annotation is："+annotation);
//      System.out.println("price percentage is："+price_percentage);
//      System.out.println("refund_percentage is："+refund_percentage);
//    System.out.println("change_route_percentage is："+change_route_percentage);
//    System.out.println("change_date_percentage is："+change_date_percentage);
//    System.out.println("change_passenger_percentage is："+change_passenger_percentage);
//    System.out.println("open_jaw_percentage is" +price_percentage);
//     System.out.println("earn_mile_percentage is" +earn_mile_percentage);

        if (!bcb.checkDuplicate(annotation)) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./bookingClassAttribute2.xhtml");
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annotation already been used: ", ""));
        }

    }

    public void editFirst() throws IOException {
        if (!(annotation.equals(annotation2))) {
            if (!bcb.checkDuplicate(annotation)) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookingClassInfo2.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Annotation already been used: ", ""));
            }

        } else {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookingClassInfo2.xhtml");
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

                FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookClassSuccess.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
        }

    }

    public void confirmDelete() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./ConfirmDeleteBookClass.xhtml");

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

    public double getPrice_percentage() {
        return price_percentage;
    }

    public void setPrice_percentage(double price_percentage) {
        this.price_percentage = price_percentage;
    }

    public double getRefund_percentage() {
        return refund_percentage;
    }

    public void setRefund_percentage(double refund_percentage) {
        this.refund_percentage = refund_percentage;
    }

    public double getChange_route_percentage() {
        return change_route_percentage;
    }

    public void setChange_route_percentage(double change_route_percentage) {
        this.change_route_percentage = change_route_percentage;
    }

    public double getChange_date_percentage() {
        return change_date_percentage;
    }

    public void setChange_date_percentage(double change_date_percentage) {
        this.change_date_percentage = change_date_percentage;
    }

    public double getChange_passenger_percentage() {
        return change_passenger_percentage;
    }

    public void setChange_passenger_percentage(double change_passenger_percentage) {
        this.change_passenger_percentage = change_passenger_percentage;
    }

    public double getOpen_jaw_percentage() {
        return open_jaw_percentage;
    }

    public void setOpen_jaw_percentage(double open_jaw_percentage) {
        this.open_jaw_percentage = open_jaw_percentage;
    }

    public double getEarn_mile_percentage() {
        return earn_mile_percentage;
    }

    public void setEarn_mile_percentage(double earn_mile_percentage) {
        this.earn_mile_percentage = earn_mile_percentage;
    }

    public boolean isCan_standby() {
        return can_standby;
    }

    public void setCan_standby(boolean can_standby) {
        this.can_standby = can_standby;
    }

    public boolean isDds_available() {
        return dds_available;
    }

    public void setDds_available(boolean dds_available) {
        this.dds_available = dds_available;
    }

    public boolean isGds_available() {
        return gds_available;
    }

    public void setGds_available(boolean gds_available) {
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

                FacesContext.getCurrentInstance().getExternalContext().redirect("./addBookClassSuccess.xhtml");
            } else {
                FacesContext.getCurrentInstance().addMessage(uIComponent.getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Resevation day cannot be later than ticketing day: ", ""));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Maxmimum days of stays must be greater than minimum days of stay: ", ""));
        }

    }

    public void EditBookClassInfo(BookingClass BookClass) throws IOException {

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

        FacesContext.getCurrentInstance().getExternalContext().redirect("./EditBookingClassInfo1.xhtml");

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
