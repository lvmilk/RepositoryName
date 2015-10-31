/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AISmanagedbean;

import Entity.APS.AircraftType;
import Entity.AIS.CabinClass;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author LIU YUQI'
 */
@Named(value = "addCabinDetailBean")
@ViewScoped
public class AddCabinDetailManagedBean implements Serializable {

    AircraftType acType;
    Collection<CabinClass> cabinList = new ArrayList<>();
    CabinClass selectedCabin;

    public AddCabinDetailManagedBean() {
    }

    @PostConstruct
    public void init() {
        selectedCabin = new CabinClass();
//        cabinList=(ArrayList<CabinClass>)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("cabinList");
//        System.out.println("size of cabinList received is "+cabinList.size());
        acType = (AircraftType) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("aircraftType");

        cabinList = acType.getCabinList();
        System.out.println("size of cabinList received is " + cabinList.size());

    }

    public void onCancel() throws IOException {
        acType = null;
        FacesContext.getCurrentInstance().getExternalContext().redirect("./ChooseAircraftType.xhtml");

    }

    public void editDetail(CabinClass cabinClass) throws IOException {

        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinClass", cabinClass);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("aircraftType", acType);
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinName", selectedCabin.getCabinName());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinSeatCount", selectedCabin.getSeatCount());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinRowCount", selectedCabin.getRowCount());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinRowSeatCount", selectedCabin.getRowSeatCount());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinRowConfig", selectedCabin.getRowConfig());
//        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("cabinSeatWidth", selectedCabin.getSeatWidth());

        FacesContext.getCurrentInstance().getExternalContext().redirect("./setCabinConfig.xhtml");

    }

    public AircraftType getAcType() {
        return acType;
    }

    public void setAcType(AircraftType acType) {
        this.acType = acType;
    }

    public Collection<CabinClass> getCabinList() {
        return cabinList;
    }

    public void setCabinList(Collection<CabinClass> cabinList) {
        this.cabinList = cabinList;
    }

    public CabinClass getSelectedCabin() {
        return selectedCabin;
    }

    public void setSelectedCabin(CabinClass selectedCabin) {
        this.selectedCabin = selectedCabin;
    }

}
