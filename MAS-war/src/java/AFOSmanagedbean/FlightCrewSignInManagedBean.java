/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AFOSmanagedbean;

import Entity.AFOS.FlightTask;
import Entity.APS.FlightInstance;
import Entity.CommonInfa.CabinCrew;
import Entity.CommonInfa.CockpitCrew;
import SessionBean.AFOS.CrewSchedulingBeanLocal;
import SessionBean.APS.FlightSchedulingBeanLocal;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Xu
 */
@Named(value = "FCSIMB")
@ViewScoped
public class FlightCrewSignInManagedBean implements Serializable {

    @EJB
    CrewSchedulingBeanLocal csb;

    @EJB
    FlightSchedulingBeanLocal fsb;

    private FlightInstance selectedFi;
    private List<CockpitCrew> cpList = new ArrayList<>();
    private List<CabinCrew> cbList = new ArrayList<>();

    private Map<String, String> flightTaskMap = new HashMap<>();

    public FlightCrewSignInManagedBean() {
    }

    @PostConstruct
    public void init() {
        selectedFi = (FlightInstance) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("selectedFi");
        cpList = selectedFi.getCockpitList();
        cbList = selectedFi.getCabinList();
        initFTMap();
    }

    public void initFTMap() {
        for (CockpitCrew cp : cpList) {
            FlightTask fcp = csb.findCpFlightTask(cp, selectedFi);
            flightTaskMap.put(cp.getCpName(), fcp.getStatus());
        }
        for (CabinCrew cb : cbList) {
            FlightTask fcp = csb.findCbFlightTask(cb, selectedFi);
            flightTaskMap.put(cb.getCbName(), fcp.getStatus());
        }
    }

    public void cpSignIn(CockpitCrew cp) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sign in success", "Cockpit crew " + cp.getCpName() + " is successfully signed in!"));
        csb.cpSignIn(selectedFi, cp);
    }

    public void cbSignIn(CabinCrew cb) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sign in success", "Cabin crew " + cb.getCbName() + " is successfully signed in!"));
        csb.cbSignIn(selectedFi, cb);
    }

    public void signInBack() throws IOException {
        FacesContext.getCurrentInstance().getExternalContext().redirect("./flightCrewSignInSelectFlight.xhtml");
    }

    public FlightInstance getSelectedFi() {
        return selectedFi;
    }

    public void setSelectedFi(FlightInstance selectedFi) {
        this.selectedFi = selectedFi;
    }

    public List<CockpitCrew> getCpList() {
        return cpList;
    }

    public void setCpList(List<CockpitCrew> cpList) {
        this.cpList = cpList;
    }

    public List<CabinCrew> getCbList() {
        return cbList;
    }

    public void setCbList(List<CabinCrew> cbList) {
        this.cbList = cbList;
    }

    public Map<String, String> getFlightTaskMap() {
        return flightTaskMap;
    }

    public void setFlightTaskMap(Map<String, String> flightTaskMap) {
        this.flightTaskMap = flightTaskMap;
    }

}
