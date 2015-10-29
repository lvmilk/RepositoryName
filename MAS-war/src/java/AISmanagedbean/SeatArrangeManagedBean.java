/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import SessionBean.APS.FleetPlanningBeanLocal;
import SessionBean.AIS.SeatPlanBeanLocal;
import SessionBean.CommonInfa.manageAccountLocal;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "SeatArrangeBean")
@SessionScoped
public class SeatArrangeManagedBean implements Serializable {

    private manageAccountLocal mal;
    private List<SelectItem> options = new ArrayList<SelectItem>();

    @EJB
    private FleetPlanningBeanLocal FP;

    @EJB
    private SeatPlanBeanLocal SP;

    private String type;
    private ArrayList<AircraftType> airTypes;
    private AircraftType airType;
    private ArrayList<CabinClass>cabinList;
    private CabinClass cabinClass;
    
    
    private ArrayList<String> cabin;
    private ArrayList<Integer> cabinSeatNo;

    private AircraftType selectedType;
    private List<AircraftType> allTypes;
    private String selectedName;
    private ArrayList<String> allNames;

    private Integer suiteNo;                //number of seat in suite
    private Integer fcSeatNo;               //number of seat in first class
    private Integer bcSeatNo;               //number of seat in business class
    private Integer pecSeatNo;              //number of seat in premium economy class
    private Integer ecSeatNo;

    public SeatArrangeManagedBean() {

    }

//    @PostConstruct
//    public void init() {
////        boolean check = SP.checkAirTypeEmpty();
//
//        this.options.add(new SelectItem( "Select One AircraftType"));
//
//         
//            allNames = (ArrayList<String>)this.getAllNames();
//            if (!allNames.isEmpty()) {
//                for (int i = 0; i < allNames.size(); i++) {
//                    this.options.add(new SelectItem(allNames.get(i)));
//
//                }
//            }
//        } 
    public void searchType() throws IOException {
            
            

        selectedType = SP.findType(selectedName);
        System.out.println("No of cabin classes is "+ selectedType.getCabinList().size());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinList",selectedType.getCabinList() );
         FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftType",selectedType);

        FacesContext.getCurrentInstance().getExternalContext().redirect("./ChooseCabin.xhtml");
    }


    public ArrayList<Integer> getCabinSeatNo() {
        return cabinSeatNo;
    }

    public void setCabinSeatNo(ArrayList<Integer> cabinSeatNo) {
        this.cabinSeatNo = cabinSeatNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<AircraftType> getAirTypes() {
        return airTypes;
    }

    public void setAirTypes(ArrayList<AircraftType> airTypes) {
        this.airTypes = airTypes;
    }

    public AircraftType getAirType() {
        return airType;
    }

    public void setAirType(AircraftType airType) {
        this.airType = airType;
    }

    public ArrayList<String> getCabin() {
        return cabin;
    }

    public void setCabin(ArrayList<String> cabin) {
        this.cabin = cabin;
    }

    public Integer getSuiteNo() {
        return suiteNo;
    }

    public void setSuiteNo(Integer suiteNo) {
        this.suiteNo = suiteNo;
    }

    public Integer getFcSeatNo() {
        return fcSeatNo;
    }

    public void setFcSeatNo(Integer fcSeatNo) {
        this.fcSeatNo = fcSeatNo;
    }

    public Integer getBcSeatNo() {
        return bcSeatNo;
    }

    public void setBcSeatNo(Integer bcSeatNo) {
        this.bcSeatNo = bcSeatNo;
    }

    public Integer getPecSeatNo() {
        return pecSeatNo;
    }

    public void setPecSeatNo(Integer pecSeatNo) {
        this.pecSeatNo = pecSeatNo;
    }

    public Integer getEcSeatNo() {
        return ecSeatNo;
    }

    public void setEcSeatNo(Integer ecSeatNo) {
        this.ecSeatNo = ecSeatNo;
    }

    public AircraftType getSelectedType() {
        return selectedType;
    }

    public void setSelectedType(AircraftType selectedType) {
        this.selectedType = selectedType;
    }

    public List<AircraftType> getAllTypes() {
        return allTypes;
    }

    public void setAllTypes(List<AircraftType> allTypes) {
        this.allTypes = allTypes;
    }

    public String getSelectedName() {
        return selectedName;
    }

    public void setSelectedName(String selectedName) {
        this.selectedName = selectedName;
    }

    public ArrayList<String> getAllNames() {
        System.out.println("get here");
        this.allTypes = FP.getAllAircraftType();
        if (!allTypes.isEmpty()) {
            allNames = new ArrayList<String>();
            for (int i = 0; i < allTypes.size(); i++) {
                String temp = (String) allTypes.get(i).getType();
                System.out.println("type is " + temp);
                this.allNames.add(temp);

            }
        }

        return allNames;
    }

    public void setAllNames(ArrayList<String> allNames) {
        this.allNames = allNames;
    }

    public List<SelectItem> getOptions() {
        options=new ArrayList<SelectItem>();
        allNames = new ArrayList<String>();
        this.options.add(new SelectItem(null,"Select One AircraftType"));

        allNames = (ArrayList<String>) this.getAllNames();

        if (!allNames.isEmpty()) {
            for (int i = 0; i < allNames.size(); i++) {
                this.options.add(new SelectItem(allNames.get(i),allNames.get(i)));

            }
        }
//        System.out.println("ABCDEFG");
//
//        this.options.add(new SelectItem(null, "Select One AircraftType"));

//        if (!SP.checkAirTypeEmpty()) {
//            allNames = getAllNames();
//            if (!allNames.isEmpty()) {
//                for (int i = 0; i < allNames.size(); i++) {
//                    this.options.add(new SelectItem(allNames.get(i), allNames.get(i)));
//
//                }
//            }
//        } else {
//            System.out.println("List is empty");
//        }
//        
        return options;
    }

    public void setOptions(List<SelectItem> options) {
        this.options = options;
    }

    public manageAccountLocal getMal() {
        return mal;
    }

    public void setMal(manageAccountLocal mal) {
        this.mal = mal;
    }

    public FleetPlanningBeanLocal getFP() {
        return FP;
    }

    public void setFP(FleetPlanningBeanLocal FP) {
        this.FP = FP;
    }

    public SeatPlanBeanLocal getSP() {
        return SP;
    }

    public void setSP(SeatPlanBeanLocal SP) {
        this.SP = SP;
    }

    public ArrayList<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(ArrayList<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }
    
    

}