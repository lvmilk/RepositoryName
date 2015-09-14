/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;
import java.io.IOException;
import java.util.*;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "bookingClassBean")
@RequestScoped
public class BookingClassBean {

    private String cabin;
    private String annotation;  //annotation of booking class like K,N etc

   
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
    
    private boolean  can_standby;  // indicate whether customer can standby check-in
    private boolean  dds_available; // whether tickets from this class is available in DDS
     private boolean  gds_available; // whether tickets from this class is available in GDS
    
    
    public BookingClassBean() {
        
        
    }
    
    public void checkCabin () throws IOException{
    if(cabin!=null && !(cabin.isEmpty())) {
    System.out.println(" Cabin selected is "+cabin);
     FacesContext.getCurrentInstance().getExternalContext().redirect("DisplayAircraftSeat.xhtml");
    }
    else
        System.out.println("No cabin is chosen");
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

    public double getPrice_percentage() {
        return price_percentage;
    }

    public void setPrice_percentage(double price_percentage) {
        this.price_percentage = price_percentage;
    }
    
    
    
}
