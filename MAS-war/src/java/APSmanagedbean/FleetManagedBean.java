package APSmanagedbean;

import Entity.APS.Aircraft;
import Entity.APS.AircraftType;
import SessionBean.APS.FleetPlanningBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Xi
 */
@Named(value = "FMB")
@ViewScoped
public class FleetManagedBean implements Serializable {

    @EJB
    private FleetPlanningBeanLocal fpb;

    private List<Aircraft> aircraftList = new ArrayList<>();
    private List<AircraftType> typeList = new ArrayList<>();
    private AircraftType aircraftType = new AircraftType();
    private ArrayList<String> thisList = new ArrayList<>();
    private String[][] multiList = new String[0][0];

    private String type;
    private String manufacturer;

    private String registrationNo;
    private String status;

    private Integer typeSize;

    public FleetManagedBean() {
    }

    @PostConstruct
    public void init() {
        typeList = fpb.getAllAircraftType();
        //type = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("type");
        //aircraftType=fpb.getAircraftType(type);
        //typeSize = fpb.getTypeSize(type);
        for (int i = 0; i < typeList.size(); i++) {

            type = typeList.get(i).getType();
            manufacturer = typeList.get(i).getManufacturer();
            typeSize = fpb.getTypeSize(type);
            System.out.println("Type Size:" + typeSize);

            thisList = new ArrayList<>();
            thisList.add(type);
            thisList.add(manufacturer);
            thisList.add(typeSize.toString());
            multiList[i][i] = thisList.get(0);
            multiList[i][i + 1] = thisList.get(1);
            multiList[i][i + 2] = thisList.get(2);
        }
        System.out.println(multiList[0][0]);
        System.out.println(multiList[0][1]);
        System.out.println(multiList[0][2]);
        System.out.println(multiList[1][0]);
        System.out.println(multiList[1][1]);
        System.out.println(multiList[1][2]);
    }
//
//    public Integer thisTypeSize() {
//        for (int i = 0; i < typeList.size(); i++) {
//            if (type.equals(typeList.get(i).getType())) {
//                typeSize++;
//            } else {
//                System.out.println("Fleet Size = 0");
//            }
//        }
//        return typeSize;
//    }

    ///////////////////////////////////////////////
    public ArrayList<String> getThisList() {
        return thisList;
    }

    public void setThisList(ArrayList<String> thisList) {
        this.thisList = thisList;
    }

    public String[][] getMultiList() {
        return multiList;
    }

    public void setMultiList(String[][] multiList) {
        this.multiList = multiList;
    }

    public List<Aircraft> getAircraftList() {
        return aircraftList;
    }

    public void setAircraftList(List<Aircraft> aircraftList) {
        this.aircraftList = aircraftList;
    }

    public List<AircraftType> getTypeList() {
        return typeList;
    }

    public void setTypeList(List<AircraftType> typeList) {
        this.typeList = typeList;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AircraftType getAircraftType() {
        return aircraftType;
    }

    public void setAircraftType(AircraftType aircraftType) {
        this.aircraftType = aircraftType;
    }

    public Integer getTypeSize() {
        //size=aircraftType.getAircraft().size();
        return typeSize;
    }

    public void setTypeSize(Integer typeSize) {
        this.typeSize = typeSize;
    }

}
