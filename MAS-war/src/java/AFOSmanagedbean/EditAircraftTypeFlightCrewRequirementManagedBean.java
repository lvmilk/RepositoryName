/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.APS.AircraftType;
import Entity.aisEntity.BookingClassInstance;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Xu
 */
@Named(value = "EATFCRMB")
@ViewScoped
public class EditAircraftTypeFlightCrewRequirementManagedBean implements Serializable {

    private List<AircraftType> actList;
    
    
    public EditAircraftTypeFlightCrewRequirementManagedBean() {
    }
//
//    public void onRowEdit(RowEditEvent event) {
//        AircraftType act = (AircraftType) event.getObject();
//        mpb.editPrice(bki, price);
//        FacesMessage msg = new FacesMessage("Fare Edited", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class  Edited");
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Flight Crew Requirement Edited", ""));
//
//    }
//
//    public void onRowCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Edit Cancelled", ((BookingClassInstance) event.getObject()).getBookingClass().getAnnotation() + " Booking Class Edit Cancelled");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }

}
