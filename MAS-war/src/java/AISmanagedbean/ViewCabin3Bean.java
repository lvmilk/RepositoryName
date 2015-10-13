/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.APS.CabinClass;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.APS.ManageCabinLocal;
import java.io.IOException;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "viewCabin3Bean")
@ViewScoped
public class ViewCabin3Bean implements Serializable {

    CabinClass cabinSelected;
    AircraftType acType;

    private String cabinName;
    private Integer seatCount; //total seat count
//    private Double fullFare;
    private Double seatWidth;
    private Integer rowCount;//no. of rows
    private Integer rowSeatCount; //no. of seat counts per row
    private String rowConfig; //e.g for 7 seats/row, 2-3-2

    public ViewCabin3Bean() {
    }

    @EJB
    private ManageCabinLocal mcl;

    @EJB
    private FleetPlanningBeanLocal FP;

    @PostConstruct
    public void init() {
        System.out.println("Getting here");
        cabinSelected = (CabinClass) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinClass");
        System.out.println("Cabin passed is " + cabinSelected.getCabinName());
        acType = (AircraftType) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraftType");
        if (mcl.widthExist(acType, cabinSelected.getCabinName())) {
            seatWidth = cabinSelected.getSeatWidth();
            System.out.println("SeatWidth is "+seatWidth);
        }
        if (mcl.rowCountExist(acType, cabinSelected.getCabinName())) {
            rowCount = cabinSelected.getRowCount();
            System.out.println("Row Count is "+rowCount);
        }
        if (mcl.rowSeatCount(acType, cabinSelected.getCabinName())) {
            rowSeatCount = cabinSelected.getRowSeatCount();
        }
        if (mcl.rowConfig(acType, cabinSelected.getCabinName())) {
            rowConfig = cabinSelected.getRowConfig();
        }

    }
    
    public void goBack() throws IOException{
    
    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftType",acType);
    FacesContext.getCurrentInstance().getExternalContext().redirect("./ViewCabin2.xhtml");
    
    }

//    public void saveUpdate() throws IOException {
//        if (rowCount * rowSeatCount > cabinSelected.getSeatCount()) {
//            System.out.println("exceed total seat count in cabin");
//            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "product of rows and seat per row must be less than capacity ", ""));
//        } else {
//
//            String[] parts = rowConfig.split("-");
//            String part1 = parts[0]; // 004
//            String part2 = parts[1]; // 034556
//            String part3 = parts[2];
//            Integer left = Integer.parseInt(part1);
//            Integer middle = Integer.parseInt(part2);
//            Integer right = Integer.parseInt(part3);
//            System.out.println("left is " + left);
//            System.out.println("middle is " + middle);
//            System.out.println("right is " + right);
//
//            if (left == 0 || right == 0) {
//                System.out.println("Left or right is 0");
//                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "left and right segment cannot be 0 ", ""));
//
//            } else {
//
//                if ((left + middle + right) > rowSeatCount) {
//                    System.out.println("Exceed row seat count");
//                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "seats in row config exceed no. of seats per row", ""));
//
//                } else {
//
//                    mcl.updateCabin(cabinSelected, seatWidth, rowCount, rowSeatCount, rowConfig);
//
//                    FacesContext.getCurrentInstance().getExternalContext().redirect("./EditCabinSuccess.xhtml");
//                }
//            }
//        }
//    }

    public CabinClass getCabinSelected() {
        return cabinSelected;
    }

    public void setCabinSelected(CabinClass cabinSelected) {
        this.cabinSelected = cabinSelected;
    }

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

    public String getCabinName() {
        return cabinName;
    }

    public void setCabinName(String cabinName) {
        this.cabinName = cabinName;
    }

    public Integer getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(Integer seatCount) {
        this.seatCount = seatCount;
    }

    public Double getSeatWidth() {
        return seatWidth;
    }

    public void setSeatWidth(Double seatWidth) {
        this.seatWidth = seatWidth;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getRowSeatCount() {
        return rowSeatCount;
    }

    public void setRowSeatCount(Integer rowSeatCount) {
        this.rowSeatCount = rowSeatCount;
    }

    public String getRowConfig() {
        return rowConfig;
    }

    public void setRowConfig(String rowConfig) {
        this.rowConfig = rowConfig;
    }

}
