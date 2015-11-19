/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommonInframanagedbean;

import java.io.Serializable;
import java.util.Date;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xi
 */
@Named(value = "calendarManagedBean")
@ViewScoped
public class CalendarManagedBean  implements Serializable {

    /**
     * Creates a new instance of CalendarManagedBean
     */
    public CalendarManagedBean() {
    }

    private Date date = new Date();

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
