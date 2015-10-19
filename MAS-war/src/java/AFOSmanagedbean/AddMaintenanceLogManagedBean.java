/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "AMLMB")
@ViewScoped
public class AddMaintenanceLogManagedBean implements Serializable {

    private String mtDate;
    private String startTime;
    private String endTime;
    private String objective;
    private String aircraft;
    private String acType;
    private String activity;
    private String remark;
    private String mtCrew;

    public AddMaintenanceLogManagedBean() {
    }

}
